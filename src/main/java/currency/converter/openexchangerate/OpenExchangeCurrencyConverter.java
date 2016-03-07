package currency.converter.openexchangerate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.h2.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import currency.converter.configuration.ConfigurationProperties;
import currency.converter.entity.Currency;
import currency.converter.entity.CurrencyHistory;
import currency.converter.entity.CurrencyRate;
import currency.converter.entity.repository.CurrencyHistoryRepository;
import currency.converter.entity.repository.CurrencyRateRepository;
import currency.converter.entity.repository.CurrencyRespository;
import currency.converter.login.UserService;
import currency.converter.openexchangerate.model.CurrencyRateModel;
import currency.converter.openexchangerate.model.Rates;
import currency.converter.registration.SupportedCurrencies;

/**
 * Service for retrieving lates and historical currency exchange rates from
 * <a href="https://openexchangerates.org">OpenExchangeRates</a>.
 * 
 * @author Ziaul Chowdhury (romel99@gmail.com)
 * @since 06.03.2016
 */
@Component
public class OpenExchangeCurrencyConverter {

    @Autowired
    private CurrencyHistoryRepository historyRepository;

    @Autowired
    private CurrencyRateRepository rateRepository;

    @Autowired
    private CurrencyRespository currencyRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ConfigurationProperties configuration;

    private static final Logger logger = LoggerFactory.getLogger(OpenExchangeCurrencyConverter.class);

    private static final double ZERO = 0.0d;

    private static final double MINIMUM_AMOUNT = 1.0d;

    /**
     * Retrieves latest exchange rates from <a href="https://openexchangerates.org">OpenExchangeRates</a> by using
     * REST template. Retrieved rates are stored as search history for the specific user.
     * 
     * @param baseCurrency Base currency for exchange rates
     * @param amount Amount
     * 
     * @return {@link CurrencyRateModel}
     * 
     * @throws InvalidParameterException for invalid arguments
     */
    public CurrencyRateModel getLatestCurrencyRates(String baseCurrency, double amount)
            throws InvalidParameterException {

        if (StringUtils.isNullOrEmpty(baseCurrency)) {
            baseCurrency = SupportedCurrencies.USD.name();
        }

        if (amount <= ZERO) {
            amount = MINIMUM_AMOUNT;
        }

        StringBuilder builder = new StringBuilder();
        String apiKey = configuration.getOpenExchangeApiKey();
        builder.append("https://openexchangerates.org/api/latest.json?app_id=").append(apiKey).append("&base=")
                .append(baseCurrency);

        boolean isCommercialApi = configuration.isOpenExchangeCommercialLicense();
        if (isCommercialApi) {
            builder.append(getTargetCurrencies(baseCurrency));
        }

        logger.info("Latest rate API URL : " + builder.toString());

        RestTemplate restTemplate = new RestTemplate();
        CurrencyRateModel rates = restTemplate.getForObject(builder.toString(), CurrencyRateModel.class);

        multiplyAmount(rates, amount, baseCurrency);
        logger.info(rates.toString());

        // save currency history
        String userName = userService.getLoggedInUserName();
        saveExchangeRate(rates, baseCurrency, amount, LocalDate.now(), userName);

        return rates;
    }

    /**
     * Retrieves latest exchange rates from <a href="https://openexchangerates.org">OpenExchangeRates</a> by using
     * REST template. Retrieved rates are stored as search history for the specific user.
     * 
     * @param baseCurrency Base currency for exchange rates
     * @param amount Amount
     * @param dateOfRate Date for getting the historical exchange rate
     * 
     * @return {@link CurrencyRateModel}
     * 
     * @throws InvalidParameterException for invalid date
     */
    public CurrencyRateModel getHistoricalCurrencyRates(String baseCurrency, double amount, LocalDate dateOfRate)
            throws InvalidParameterException {

        if (dateOfRate == null) {
            throw new InvalidParameterException("Date of rate is mandatory for historical currency rates!");
        }

        if (StringUtils.isNullOrEmpty(baseCurrency)) {
            baseCurrency = SupportedCurrencies.USD.name();
        }

        if (amount <= ZERO) {
            amount = MINIMUM_AMOUNT;
        }

        String apiKey = configuration.getOpenExchangeApiKey();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        StringBuilder builder = new StringBuilder();
        builder.append("https://openexchangerates.org/api/historical/").append(dateOfRate.format(formatter))
                .append(".json?app_id=").append(apiKey).append("&base=").append(baseCurrency);

        boolean isCommercialApi = configuration.isOpenExchangeCommercialLicense();
        if (isCommercialApi) {
            builder.append(getTargetCurrencies(baseCurrency));
        }

        logger.info("Historical rate API URL : " + builder.toString());

        RestTemplate restTemplate = new RestTemplate();
        CurrencyRateModel rates = restTemplate.getForObject(builder.toString(), CurrencyRateModel.class);

        multiplyAmount(rates, amount, baseCurrency);
        logger.info(rates.toString());

        // save currency history
        String userName = userService.getLoggedInUserName();
        saveExchangeRate(rates, baseCurrency, amount, dateOfRate, userName);

        return rates;
    }

    /**
     * Saves currency exchange rate history of the logged in user
     * 
     * @param rates Currency rates to be saved
     * @param baseCurrency Base currency which was used in the search
     * @param amount Amount of base currency
     * @param dateOfRate Date when the search was performed
     * @param userName Username of the user who has searched the exchange rate
     * 
     * @throws InvalidParameterException for invalid arguments
     */
    private void saveExchangeRate(CurrencyRateModel rates, String baseCurrency, double amount, LocalDate dateOfRate,
            String userName) throws InvalidParameterException {

        Currency currency = getCurrency(baseCurrency);
        Date date = Date.from(dateOfRate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        CurrencyHistory currencyHistory = new CurrencyHistory(currency.getId(), amount, date, userName);
        historyRepository.save(currencyHistory);

        // Save rates
        saveCurrencyRates(currencyHistory, rates, baseCurrency);
    }

    private Currency getCurrency(String code) throws InvalidParameterException {

        List<Currency> currencies = currencyRepository.findByCode(code);
        if (currencies.isEmpty()) {
            throw new InvalidParameterException("Currency " + code + " does not exist in the DB!");
        }

        return currencies.get(0);
    }

    private void saveCurrencyRates(CurrencyHistory currencyHistory, CurrencyRateModel rates, String baseCurrency)
            throws InvalidParameterException {
        Rates conversionRates = rates.getRates();

        if (conversionRates.getAud() > ZERO && !SupportedCurrencies.AUD.name().equals(baseCurrency)) {
            saveCurrencyRate(currencyHistory, conversionRates.getAud(), SupportedCurrencies.AUD.name());
        }

        if (conversionRates.getEur() > ZERO && !SupportedCurrencies.EUR.name().equals(baseCurrency)) {
            saveCurrencyRate(currencyHistory, conversionRates.getEur(), SupportedCurrencies.EUR.name());
        }

        if (conversionRates.getGbp() > ZERO && !SupportedCurrencies.GBP.name().equals(baseCurrency)) {
            saveCurrencyRate(currencyHistory, conversionRates.getGbp(), SupportedCurrencies.GBP.name());
        }

        if (conversionRates.getHuf() > ZERO && !SupportedCurrencies.HUF.name().equals(baseCurrency)) {
            saveCurrencyRate(currencyHistory, conversionRates.getHuf(), SupportedCurrencies.HUF.name());
        }

        if (conversionRates.getJpy() > ZERO && !SupportedCurrencies.JPY.name().equals(baseCurrency)) {
            saveCurrencyRate(currencyHistory, conversionRates.getJpy(), SupportedCurrencies.JPY.name());
        }

        if (conversionRates.getNzd() > ZERO && !SupportedCurrencies.NZD.name().equals(baseCurrency)) {
            saveCurrencyRate(currencyHistory, conversionRates.getNzd(), SupportedCurrencies.NZD.name());
        }

        if (conversionRates.getUsd() > ZERO && !SupportedCurrencies.USD.name().equals(baseCurrency)) {
            saveCurrencyRate(currencyHistory, conversionRates.getUsd(), SupportedCurrencies.USD.name());
        }
    }

    private void saveCurrencyRate(CurrencyHistory currencyHistory, double amount, String currencyCode)
            throws InvalidParameterException {

        Currency currency = getCurrency(currencyCode);
        CurrencyRate rate = new CurrencyRate(currencyHistory.getId(), currency.getId(), amount);
        rateRepository.save(rate);

    }

    /**
     * Get target currencies : Not available for free API
     * 
     * @param baseCurrency
     * @return
     */
    private String getTargetCurrencies(String baseCurrency) {

        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (SupportedCurrencies currency : SupportedCurrencies.values()) {

            if (i > 0 && i < SupportedCurrencies.values().length - 1) {
                builder.append(",");
            }

            if (currency.name().equals(baseCurrency)) {
                i++;
                continue;
            }

            builder.append(currency.name());
            i++;
        }

        return builder.toString();
    }

    private void multiplyAmount(CurrencyRateModel rates, double amount, String baseCurrency) {
        Rates conversionRates = rates.getRates();

        if (conversionRates.getAud() > ZERO && !SupportedCurrencies.AUD.name().equals(baseCurrency)) {
            conversionRates.setAud(conversionRates.getAud() * amount);
        }

        if (conversionRates.getEur() > ZERO && !SupportedCurrencies.EUR.name().equals(baseCurrency)) {
            conversionRates.setEur(conversionRates.getEur() * amount);
        }

        if (conversionRates.getGbp() > ZERO && !SupportedCurrencies.GBP.name().equals(baseCurrency)) {
            conversionRates.setGbp(conversionRates.getGbp() * amount);
        }

        if (conversionRates.getHuf() > ZERO && !SupportedCurrencies.HUF.name().equals(baseCurrency)) {
            conversionRates.setHuf(conversionRates.getHuf() * amount);
        }

        if (conversionRates.getJpy() > ZERO && !SupportedCurrencies.JPY.name().equals(baseCurrency)) {
            conversionRates.setJpy(conversionRates.getJpy() * amount);
        }

        if (conversionRates.getNzd() > ZERO && !SupportedCurrencies.NZD.name().equals(baseCurrency)) {
            conversionRates.setNzd(conversionRates.getNzd() * amount);
        }

        if (conversionRates.getUsd() > ZERO && !SupportedCurrencies.USD.name().equals(baseCurrency)) {
            conversionRates.setUsd(conversionRates.getUsd() * amount);
        }
    }
}

package currency.converter.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;

import currency.converter.configuration.ConfigurationProperties;
import currency.converter.controller.model.ExchangeHistoryModel;
import currency.converter.controller.model.HistoricalRateModel;
import currency.converter.controller.model.LatestRateModel;
import currency.converter.controller.model.RateModel;
import currency.converter.entity.CurrencyHistory;
import currency.converter.entity.CurrencyRate;
import currency.converter.entity.repository.CurrencyHistoryRepository;
import currency.converter.entity.repository.CurrencyRateRepository;
import currency.converter.login.UserService;
import currency.converter.openexchangerate.InvalidParameterException;
import currency.converter.openexchangerate.OpenExchangeCurrencyConverter;
import currency.converter.openexchangerate.model.CurrencyRateModel;
import currency.converter.openexchangerate.model.Rates;
import currency.converter.registration.SupportedCurrencies;

/**
 * Controller for retrieving exchange rates from <a href="https://openexchangerates.org">OpenExchangeRates</a> 
 * and displaying search histories for each user.
 * 
 * @author Ziaul Chowdhury (ziaul.chowdhury@tu-dortmund.de)
 * @since 06.03.2016
 */
@Controller
@RequestMapping("/userhome")
public class OpenExchangeCurrencyConverterController extends ControllerCache {

    @Autowired
    private OpenExchangeCurrencyConverter openExchangeConverter;

    @Autowired
    private CurrencyHistoryRepository historyRepository;

    @Autowired
    private CurrencyRateRepository currencyRateRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ConfigurationProperties configuration;

    private static final double ZERO = 0.0d;

    /**
     * Initializes userhome page (latest rate, historical rate and exchange history)
     * 
     * @param model UI model
     * 
     * @return userhome view
     */
    @RequestMapping(method = RequestMethod.GET)
    public String initUserHome(Model model) {

        model.addAttribute("latestRateModel", new LatestRateModel());
        model.addAttribute("historicalRateModel", new HistoricalRateModel());
        addExchangeHistory(model);
        addCurrencies(model);

        return "userhome";
    }

    /**
     * Retrieves latest exchange rates from <a href="https://openexchangerates.org">OpenExchangeRates</a>. Retrieved
     * rates are stored as search history for the specific user.
     * 
     * @param model UI model
     * @param latestRateModel {@link LatestRateModel}
     * @param bindingResult Binding result for validation
     * @param historicalRateModel {@link HistoricalRateModel}
     * 
     * @return userhome view
     */
    @RequestMapping(path = "/currency/latest", method = RequestMethod.POST)
    public String showLatestRate(Model model, @Valid LatestRateModel latestRateModel, BindingResult bindingResult,
            HistoricalRateModel historicalRateModel) {

        addCurrencies(model);
        model.addAttribute("historicalRateModel", historicalRateModel);
        model.addAttribute("latestRateModel", latestRateModel);
        addExchangeHistory(model);

        if (bindingResult.hasErrors()) {
            return "userhome";
        }

        try {
            CurrencyRateModel currencyRateModel = openExchangeConverter
                    .getLatestCurrencyRates(latestRateModel.getBaseCurrency(), latestRateModel.getAmount());

            List<RateModel> rateModels = getCurrencyRateModels(currencyRateModel, latestRateModel.getBaseCurrency());
            model.addAttribute("latestCurrencyRateModels", rateModels);

        } catch (HttpClientErrorException | InvalidParameterException exception) {
            model.addAttribute("conversionError", exception.getMessage());
        }

        return "userhome";
    }

    /**
     * Retrieves historical exchange rates (specific date given) from <a href="https://openexchangerates.org"> OpenExchangeRates</a>.
     * Retrieved rates are stored as search history for the specific user.
     * 
     * @param model UI model
     * @param historicalRateModel {@link HistoricalRateModel}
     * @param bindingResult Binding result for validation
     * @param latestRateModel {@link LatestRateModel}
     * 
     * @return userhome view
     */
    @RequestMapping(path = "/currency/historical", method = RequestMethod.POST)
    public String showHistoricalRate(Model model, @Valid HistoricalRateModel historicalRateModel,
            BindingResult bindingResult, LatestRateModel latestRateModel) {

        addCurrencies(model);
        model.addAttribute("latestRateModel", latestRateModel);
        addExchangeHistory(model);

        if (bindingResult.hasErrors()) {
            model.addAttribute("historicalRateModel", historicalRateModel);
            return "userhome";
        }

        try {
            CurrencyRateModel currencyRateModel = openExchangeConverter.getHistoricalCurrencyRates(
                    historicalRateModel.getHistoricalCurrency(), historicalRateModel.getHistoricalAmount(),
                    historicalRateModel.getDateOfRate());

            List<RateModel> rateModels = getCurrencyRateModels(currencyRateModel,
                    historicalRateModel.getHistoricalCurrency());
            model.addAttribute("historicalCurrencyResult", rateModels);

        } catch (HttpClientErrorException | InvalidParameterException ex) {
            model.addAttribute("conversionErrorHistorical", ex.getMessage());
        }

        return "userhome";
    }

    /**
     * Retrieves top 10 exchange rate search histories of the logged in user and add to the UI model.
     * 
     * @param model UI model
     */
    private void addExchangeHistory(Model model) {

        String userName = userService.getLoggedInUserName();

        Page<CurrencyHistory> currencyHistories = historyRepository.findByUserName(userName,
                new PageRequest(0, 10, Direction.DESC, "id"));

        List<ExchangeHistoryModel> historyModels = new ArrayList<>();
        for (CurrencyHistory history : currencyHistories) {
            List<CurrencyRate> currencyRates = currencyRateRepository.findByHistoryId(history.getId());
            ExchangeHistoryModel historyModel = new ExchangeHistoryModel(history, currencyRates);
            historyModels.add(historyModel);
        }

        model.addAttribute("historyModels", historyModels);

    }

    private List<RateModel> getCurrencyRateModels(CurrencyRateModel currencyRateModel, String baseCurrency) {

        Rates conversionRates = currencyRateModel.getRates();

        List<RateModel> rateModels = new ArrayList<>();

        if (conversionRates.getAud() > ZERO && !SupportedCurrencies.AUD.name().equals(baseCurrency)) {
            rateModels.add(new RateModel(SupportedCurrencies.AUD.name(), conversionRates.getAud()));
        }

        if (conversionRates.getEur() > ZERO && !SupportedCurrencies.EUR.name().equals(baseCurrency)) {
            rateModels.add(new RateModel(SupportedCurrencies.EUR.name(), conversionRates.getEur()));
        }

        if (conversionRates.getGbp() > ZERO && !SupportedCurrencies.GBP.name().equals(baseCurrency)) {
            rateModels.add(new RateModel(SupportedCurrencies.GBP.name(), conversionRates.getGbp()));
        }

        if (conversionRates.getHuf() > ZERO && !SupportedCurrencies.HUF.name().equals(baseCurrency)) {
            rateModels.add(new RateModel(SupportedCurrencies.HUF.name(), conversionRates.getHuf()));
        }

        if (conversionRates.getJpy() > ZERO && !SupportedCurrencies.JPY.name().equals(baseCurrency)) {
            rateModels.add(new RateModel(SupportedCurrencies.JPY.name(), conversionRates.getJpy()));
        }

        if (conversionRates.getNzd() > ZERO && !SupportedCurrencies.NZD.name().equals(baseCurrency)) {
            rateModels.add(new RateModel(SupportedCurrencies.NZD.name(), conversionRates.getNzd()));
        }

        if (conversionRates.getUsd() > ZERO && !SupportedCurrencies.USD.name().equals(baseCurrency)) {
            rateModels.add(new RateModel(SupportedCurrencies.USD.name(), conversionRates.getUsd()));
        }

        return rateModels;
    }

    private void addCurrencies(Model model) {

        if (configuration.isOpenExchangeCommercialLicense()) {
            model.addAttribute("currencies", getCurrencies());
        } else {
            model.addAttribute("currencies", getUsdCurrency());
        }
    }
}

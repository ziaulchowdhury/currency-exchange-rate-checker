package currency.converter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import currency.converter.entity.Country;
import currency.converter.entity.Currency;
import currency.converter.entity.repository.CountryRepository;
import currency.converter.entity.repository.CurrencyRespository;
import currency.converter.registration.SupportedCurrencies;

/**
 * Caches shared data which are not changed in the system.
 * 
 * @author Ziaul Chowdhury (ziaul.chowdhury@tu-dortmund.de)
 * @since 06.03.2016
 */
public class ControllerCache {

    @Autowired
    protected CountryRepository countryRespository;

    @Autowired
    protected CurrencyRespository currencyRespository;

    protected static volatile Iterable<Country> countries = null;

    protected static volatile Iterable<Currency> currencies = null;

    protected static volatile List<Currency> usdCurrency = null;

    /**
     * Returns all currencies
     * 
     * @return {@code Iterable<Country>}
     */
    protected Iterable<Country> getCountries() {
        if (countries == null) {
            countries = countryRespository.findAll();
        }

        return countries;
    }

    /**
     * Returns all currencies
     * 
     * @return {@code Iterable<Currency>}
     */
    protected Iterable<Currency> getCurrencies() {
        if (currencies == null) {
            currencies = currencyRespository.findAll();
        }

        return currencies;
    }

    /**
     * Returns USD currency
     * 
     * @return {@link Currency}
     */
    protected Iterable<Currency> getUsdCurrency() {
        if (usdCurrency == null) {
            usdCurrency = currencyRespository.findByCode(SupportedCurrencies.USD.name());
        }

        return usdCurrency;
    }
}

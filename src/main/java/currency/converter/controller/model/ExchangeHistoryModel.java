package currency.converter.controller.model;

import java.util.List;

import currency.converter.entity.CurrencyHistory;
import currency.converter.entity.CurrencyRate;

/**
 * Model used for showing exchange histories of the user.
 * 
 * @author Ziaul Chowdhury (ziaul.chowdhury@tu-dortmund.de)
 * @since 06.03.2016
 */
public class ExchangeHistoryModel {

    private CurrencyHistory history;

    private List<CurrencyRate> historyRates;

    public ExchangeHistoryModel(CurrencyHistory history, List<CurrencyRate> historyRates) {
        this.history = history;
        this.historyRates = historyRates;
    }

    public CurrencyHistory getHistory() {
        return history;
    }

    public void setHistory(CurrencyHistory history) {
        this.history = history;
    }

    public List<CurrencyRate> getHistoryRates() {
        return historyRates;
    }

    public void setHistoryRates(List<CurrencyRate> historyRates) {
        this.historyRates = historyRates;
    }
}

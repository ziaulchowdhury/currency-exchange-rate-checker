package currency.converter.controller.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Model used for showing latest exchange rates.
 * 
 * @author Ziaul Chowdhury (ziaul.chowdhury@tu-dortmund.de)
 * @since 06.03.2016
 */
public class LatestRateModel {

    @Min(value = 1)
    protected double amount;

    @NotBlank
    @Size(min = 1)
    protected String baseCurrency;

    public LatestRateModel() {
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }
}

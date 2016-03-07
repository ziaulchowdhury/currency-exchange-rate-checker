package currency.converter.controller.model;

/**
 * Model used for showing rate and amount.
 * 
 * @author Ziaul Chowdhury (ziaul.chowdhury@tu-dortmund.de)
 * @since 06.03.2016
 */
public class RateModel {

	private String currencyCode;

	private double amount;

	public RateModel(String currencyCode, double amount) {
		this.currencyCode = currencyCode;
		this.amount = amount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}

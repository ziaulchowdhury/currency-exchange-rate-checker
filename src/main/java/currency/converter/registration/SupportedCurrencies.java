package currency.converter.registration;

/**
 * Supported currencies
 * 
 * @author Ziaul Chowdhury (ziaul.chowdhury@tu-dortmund.de)
 * @since 06.03.2016
 */
public enum SupportedCurrencies {

	AUD("Australian Dollar"),

	EUR("Euro"),

	GBP("British Pound Sterling"),

	HUF("Hungarian Forint"),

	JPY("Japanese Yen"),

	NZD("New Zealand Dollar"),

	USD("United States Dollar");

	private SupportedCurrencies(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	private String name;
}

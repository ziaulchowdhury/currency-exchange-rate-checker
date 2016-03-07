package currency.converter.openexchangerate.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON model for unmashalling currency rates.
 * 
 * @author Ziaul Chowdhury (ziaul.chowdhury@tu-dortmund.de)
 * @since  06.03.2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyRateModel {

	@JsonProperty("disclaimer")
	private String disclaimer;
	
	@JsonProperty("license")
	private String license;
	
	@JsonProperty("timestamp")
	private long timeStamp;
	
	@JsonProperty("base")
	private String base;
	
	@JsonProperty("rates")
	private Rates rates;
	
	public CurrencyRateModel() { }

	public String getDisclaimer() {
		return disclaimer;
	}

	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public Rates getRates() {
		return rates;
	}

	public void setRates(Rates rates) {
		this.rates = rates;
	}

	@Override
	public String toString() {
		return "CurrencyRateModel [disclaimer=" + disclaimer + ", license=" + license + ", timeStamp=" + timeStamp
				+ ", base=" + base + ", rates=" + rates + "]";
	}
}

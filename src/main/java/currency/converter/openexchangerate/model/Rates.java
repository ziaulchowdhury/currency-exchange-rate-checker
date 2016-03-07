package currency.converter.openexchangerate.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON model for unmashalling rates.
 * 
 * @author Ziaul Chowdhury (ziaul.chowdhury@tu-dortmund.de)
 * @since  06.03.2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rates {

	@JsonProperty("AUD")
	private double aud; 	// Australian Dollar
	
	@JsonProperty("EUR")
	private double eur; 	// Euro

	@JsonProperty("GBP")
	private double gbp;  	// British Pound Sterling
	
	@JsonProperty("HUF")
	private double huf; 	// Hungarian Forint
	
	@JsonProperty("JPY")
	private double jpy; 	// Japanese Yen
	
	@JsonProperty("NZD")
	private double nzd;		// New Zealand Dollar
	
	@JsonProperty("USD")
	private double usd;		// United States Dollar
	
	public Rates() { }

	public double getAud() {
		return aud;
	}

	public void setAud(double aud) {
		this.aud = aud;
	}

	public double getEur() {
		return eur;
	}

	public void setEur(double eur) {
		this.eur = eur;
	}

	public double getGbp() {
		return gbp;
	}

	public void setGbp(double gbp) {
		this.gbp = gbp;
	}

	public double getHuf() {
		return huf;
	}

	public void setHuf(double huf) {
		this.huf = huf;
	}

	public double getJpy() {
		return jpy;
	}

	public void setJpy(double jpy) {
		this.jpy = jpy;
	}

	public double getNzd() {
		return nzd;
	}

	public void setNzd(double nzd) {
		this.nzd = nzd;
	}

	public double getUsd() {
		return usd;
	}

	public void setUsd(double usd) {
		this.usd = usd;
	}

	@Override
	public String toString() {
		return "Currencies [aud=" + aud + ", eur=" + eur + ", gbp=" + gbp + ", huf=" + huf + ", jpy=" + jpy + ", nzd="
				+ nzd + ", usd=" + usd + "]";
	}
}

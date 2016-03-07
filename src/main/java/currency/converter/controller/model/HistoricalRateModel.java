package currency.converter.controller.model;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * Model used for showing historical exchange rates.
 * 
 * @author Ziaul Chowdhury (ziaul.chowdhury@tu-dortmund.de)
 * @since 06.03.2016
 */
public class HistoricalRateModel {

	@DateTimeFormat(iso = ISO.DATE, pattern = "yyyy-MM-dd")
	private LocalDate dateOfRate;

	@Min(value = 1)
	protected double historicalAmount;

	@NotBlank
	@Size(min = 1)
	protected String historicalCurrency;

	public HistoricalRateModel() {
	}

	public LocalDate getDateOfRate() {
		return dateOfRate;
	}

	public void setDateOfRate(LocalDate dateOfRate) {
		this.dateOfRate = dateOfRate;
	}

	public double getHistoricalAmount() {
		return historicalAmount;
	}

	public void setHistoricalAmount(double historicalAmount) {
		this.historicalAmount = historicalAmount;
	}

	public String getHistoricalCurrency() {
		return historicalCurrency;
	}

	public void setHistoricalCurrency(String historicalCurrency) {
		this.historicalCurrency = historicalCurrency;
	}

	@Override
	public String toString() {
		return "HistoricalRateModel [dateOfRate=" + dateOfRate + ", historicalAmount=" + historicalAmount
				+ ", historicalCurrency=" + historicalCurrency + "]";
	}
}

package currency.converter.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * CurrencyRate JPA entity
 * 
 * @author Ziaul Chowdhury (ziaul.chowdhury@tu-dortmund.de)
 * @since  06.03.2016
 */
@Entity
@Table(name="currency_rate")
public class CurrencyRate {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	
	@Column(name = "history_id")
	private long historyId;
	
	private long currency;
	
	private double rate;
	
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name = "history_id", referencedColumnName= "id", insertable = false, updatable = false)
    private CurrencyHistory currencyHistory;
	
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name = "currency", referencedColumnName= "id", insertable = false, updatable = false)
    private Currency currencyObj;
    
    protected CurrencyRate() { }

    public CurrencyRate(long historyId, long currency, double rate) {
        this.historyId = historyId;
        this.currency = currency;
        this.rate = rate;
    }

	public long getHistoryId() {
		return historyId;
	}

	public void setHistoryId(long historyId) {
		this.historyId = historyId;
	}

	public long getCurrency() {
		return currency;
	}

	public void setCurrency(long currency) {
		this.currency = currency;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public long getId() {
		return id;
	}

	public CurrencyHistory getCurrencyHistory() {
		return currencyHistory;
	}
	
	public void setCurrencyHistory(CurrencyHistory currencyHistory) {
		this.currencyHistory = currencyHistory;
	}

	public void setCurrencyObj(Currency currencyObj) {
		this.currencyObj = currencyObj;
	}

	public Currency getCurrencyObj() {
		return currencyObj;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (currency ^ (currency >>> 32));
		result = prime * result + (int) (historyId ^ (historyId >>> 32));
		result = prime * result + (int) (id ^ (id >>> 32));
		long temp;
		temp = Double.doubleToLongBits(rate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CurrencyRate other = (CurrencyRate) obj;
		if (currency != other.currency)
			return false;
		if (historyId != other.historyId)
			return false;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(rate) != Double.doubleToLongBits(other.rate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CurrencyRate [id=" + id + ", historyId=" + historyId + ", currency=" + currency + ", rate=" + rate
				+ ", currencyHistory=" + currencyHistory + ", currencyObj=" + currencyObj + "]";
	}
}

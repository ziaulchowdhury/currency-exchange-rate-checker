package currency.converter.entity;

import java.util.Date;

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
 * CurrencyHistory JPA entity
 * 
 * @author Ziaul Chowdhury (ziaul.chowdhury@tu-dortmund.de)
 * @since 06.03.2016
 */
@Entity
@Table(name = "currency_history")
public class CurrencyHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "source_currency")
	private long sourceCurrency;

	private double amount;

	@Column(name = "rate_date")
	private Date rateDate;

	@Column(name = "username")
	private String userName;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "source_currency", referencedColumnName = "id", insertable = false, updatable = false)
	private Currency currency;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
	private User user;

	protected CurrencyHistory() {
	}

	public CurrencyHistory(long sourceCurrency, double amount, Date rateDate, String userName) {
		this.sourceCurrency = sourceCurrency;
		this.amount = amount;
		this.rateDate = rateDate;
		this.userName = userName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSourceCurrency() {
		return sourceCurrency;
	}

	public void setSourceCurrency(long sourceCurrency) {
		this.sourceCurrency = sourceCurrency;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getRateDate() {
		return rateDate;
	}

	public void setRateDate(Date rateDate) {
		this.rateDate = rateDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((rateDate == null) ? 0 : rateDate.hashCode());
		result = prime * result + (int) (sourceCurrency ^ (sourceCurrency >>> 32));
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
		CurrencyHistory other = (CurrencyHistory) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (id != other.id)
			return false;
		if (rateDate == null) {
			if (other.rateDate != null)
				return false;
		} else if (!rateDate.equals(other.rateDate))
			return false;
		if (sourceCurrency != other.sourceCurrency)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CurrencyHistory [id=" + id + ", sourceCurrency=" + sourceCurrency + ", amount=" + amount + ", rateDate="
				+ rateDate + ", userName=" + userName + ", currency=" + currency + ", user=" + user + "]";
	}
}

package b_Money;

public class Currency {
	private String name;
	private Double rate;

	Currency (String name, Double rate) {
		this.name = name;
		this.rate = rate;
	}

	//	1 universal = rate  of this currency
	public Integer universalValue(Integer amount) {
		return (int)(amount/rate);
	}

	public String getName() {
		return name;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	//amount of otherCurrency convert to this currency
	public Integer valueInThisCurrency(Integer amount, Currency otherCurrency) {
		/*
			--alternative way of calculations
		int universalAmount = otherCurrency.universalValue(amount);
		return (int)(universalAmount*rate);

		 */
		return (int) (amount*rate/ otherCurrency.rate);
	}
}

package b_Money;

public class Money implements Comparable {
	private int amount;
	private Currency currency;

	Money (Integer amount, Currency currency) {
		this.amount = amount;
		this.currency = currency;
	}

	public Integer getAmount() {
		return amount;
	}

	public Currency getCurrency() {
		return currency;
	}

	// form of "10.5 SEK" (amount == 1050)
	public String toString() {
		return "" + (double)amount/100 + " " + currency.getName();
	}

	public Integer universalValue() {
		return currency.universalValue(amount);
	}

	public Boolean equals(Money other) {
		return amount == currency.valueInThisCurrency(other.amount, other.currency);
	}

	public Money add(Money other) {
		int otherMoneyInThisMoney = currency.valueInThisCurrency(other.amount, other.currency);
		Money newMoney = new Money(amount+otherMoneyInThisMoney, currency);
		return newMoney;
	}

	public Money sub(Money other) {
		int otherMoneyInThisMoney = currency.valueInThisCurrency(other.amount, other.currency);
		Money newMoney = new Money(amount-otherMoneyInThisMoney, currency);
		return newMoney;
	}

	public Boolean isZero() {
		return amount == 0;
	}

	public Money negate() {
		return new Money(amount*(-1), currency);
	}

	public int compareTo(Object other) {
		int otherMoneyInThis = currency.valueInThisCurrency(((Money)other).amount, ((Money)other).currency);;
		return Integer.compare(amount, otherMoneyInThis);
	}
}
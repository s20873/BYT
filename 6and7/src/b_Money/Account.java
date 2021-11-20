package b_Money;

import java.util.Hashtable;

public class Account {
	private String name; // was added
	private Money content;
	private Hashtable<String, TimedPayment> timedPayments;

	Account(String name, Currency currency) {
		this.name = name; // was added
		this.content = new Money(0, currency);
		this.timedPayments = new Hashtable<String, TimedPayment>();
	}

	public void addTimedPayment(String id, Integer interval, Integer next, Money amount, Bank toBank, String toAccount) {
		TimedPayment tp = new TimedPayment(interval, next, amount, this, toBank, toAccount);
		timedPayments.put(id, tp);
	}

	public void removeTimedPayment(String id) {
		if(timedPaymentExists(id)) { //check up was added
			timedPayments.remove(id);
		}
	}

	public boolean timedPaymentExists(String id) {
		return timedPayments.containsKey(id);
	}

	public void tick() {
		for (TimedPayment tp : timedPayments.values()) {
			tp.tick(); // tp.tick() once?
		}
	}

	public void deposit(Money money) {
		content = content.add(money);
	}

	public void withdraw(Money money) {
		content = content.sub(money);
	}

	// was changed. Method should return Amount of Money, not Money itself
	public Integer getBalance() {
		return content.getAmount();
	}

	/* Everything below belongs to the private inner class, TimedPayment */
	private class TimedPayment {
		private int interval, next;
		private Account fromAccount;
		private Money amount;
		private Bank toBank;
		private String toAccount;
		
		TimedPayment(Integer interval, Integer next, Money amount, Account fromAccount, Bank toBank, String toAccount) {
			this.interval = interval;
			this.next = next;
			this.amount = amount;
			this.fromAccount = fromAccount;
			this.toBank = toBank;
			this.toAccount = toAccount;
		}

		/* Return value indicates whether or not a transfer was initiated */
		public Boolean tick() {
			if (next == 0) {
				next = interval;

				fromAccount.withdraw(amount);
				try {
					toBank.deposit(toAccount, amount);
				}
				catch (AccountDoesNotExistException e) {
					/* Revert transfer.*/
					fromAccount.deposit(amount);
				}
				return true;
			}
			else {
				next--;
				return false;
			}
		}
	}

}

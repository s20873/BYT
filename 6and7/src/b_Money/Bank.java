package b_Money;

import java.util.Hashtable;

public class Bank {
	private Hashtable<String, Account> accountList;
	private String name;
	private Currency currency;


	public Bank(String name, Currency currency) {
		this.name = name;
		this.currency = currency;
		this.accountList = new Hashtable<String, Account>();
	}

	public String getName() {
		return name;
	}

	public Currency getCurrency() {
		return currency;
	}

	// was added additionally to check the correctness of some methods
	public int getAccountsNum(){
		return accountList.size();
	}

	// was added additionally to check the correctness of some methods
	public Account getAccount(String accountId) throws AccountDoesNotExistException {
		if (!accountList.containsKey(accountId)) {
			throw new AccountDoesNotExistException();
		}
		return accountList.get(accountId);
	}

	public void openAccount(String accountId) throws AccountExistsException {
		//after the first testing, it was found that no accounts are opened because of wrong code in else part
		if (accountList.containsKey(accountId)) {
			throw new AccountExistsException();
		}
		else {
			accountList.put(accountId, new Account(accountId, currency)); // was changed
		}
	}

	public void deposit(String accountId, Money money) throws AccountDoesNotExistException {
		if (!accountList.containsKey(accountId)) { //was changed. We check if it DOES NOT contain such a key
			throw new AccountDoesNotExistException();
		}
		else {
			Account account = accountList.get(accountId);
			account.deposit(money);
		}
	}

	public void withdraw(String accountId, Money money) throws AccountDoesNotExistException {
		if (!accountList.containsKey(accountId)) {
			throw new AccountDoesNotExistException();
		}
		else {
			Account account = accountList.get(accountId);
			account.withdraw(money); // was changed from deposit() to withdraw()
		}
	}


	public Integer getBalance(String accountId) throws AccountDoesNotExistException {
		if (!accountList.containsKey(accountId)) {
			throw new AccountDoesNotExistException();
		}
		else {
			// getBalance() already returns an Amount, no need to write .getBalance().getAmount()
			return accountList.get(accountId).getBalance(); // was changed
		}
	}


	public void transfer(String fromAccount, Bank toBank, String toAccount, Money amount) throws AccountDoesNotExistException {
		if (!accountList.containsKey(fromAccount) || !toBank.accountList.containsKey(toAccount)) {
			throw new AccountDoesNotExistException();
		}
		else {
			//methods were changed
			withdraw(fromAccount, amount);
			toBank.deposit(toAccount, amount);
		}		
	}


	public void transfer(String fromAccount, String toAccount, Money amount) throws AccountDoesNotExistException {
		//arguments are wrong
		transfer(fromAccount, this, toAccount, amount); //was changed
	}


	public void addTimedPayment(String accountId, String payId, Integer interval, Integer next, Money amount, Bank toBank, String toAccount) throws AccountDoesNotExistException{
		if (!accountList.containsKey(accountId) || !toBank.accountList.containsKey(toAccount)) { //check up was added
			throw new AccountDoesNotExistException();
		}
		else {
			Account account = accountList.get(accountId);
			account.addTimedPayment(payId, interval, next, amount, toBank, toAccount);
		}
	}


	public void removeTimedPayment(String accountId, String id) throws AccountDoesNotExistException{
		if (!accountList.containsKey(accountId)) { // check up was added
			throw new AccountDoesNotExistException();
		}
		else {
			Account account = accountList.get(accountId);
			account.removeTimedPayment(id);
		}
	}


	public void tick(){
		for (Account account : accountList.values()) {
			account.tick();
		}
	}	
}

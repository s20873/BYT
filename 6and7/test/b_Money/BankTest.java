package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);

		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);

		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		assertEquals("SweBank", SweBank.getName());
		assertEquals("Nordea", Nordea.getName());
		assertEquals("DanskeBank", DanskeBank.getName());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SweBank.getCurrency());
		assertEquals(SEK, Nordea.getCurrency());
		assertEquals(DKK, DanskeBank.getCurrency());
	}

	@Test
	public void testOpenAccount() throws AccountExistsException{
		//check how many accounts are there after the setUp
		assertEquals(2, SweBank.getAccountsNum());
		assertEquals(1, Nordea.getAccountsNum());
		assertEquals(1, DanskeBank.getAccountsNum());

		Nordea.openAccount("Bob2");
		SweBank.openAccount("Alisa");
		assertEquals(2, Nordea.getAccountsNum());
		assertEquals(3, SweBank.getAccountsNum());
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		// check balance of some accounts
		assertEquals(0, SweBank.getBalance("Bob").intValue());
		assertEquals(0, Nordea.getBalance("Bob").intValue());

		// add some money to those accounts
		SweBank.deposit("Bob", new Money(100, SEK));
		Nordea.deposit("Bob", new Money(20, SEK));
		Nordea.deposit("Bob", new Money(100, DKK)); //money is converted to SEK and added

		// check if money was added
		assertEquals(100, SweBank.getBalance("Bob").intValue());
		assertEquals(95, Nordea.getBalance("Bob").intValue());
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		// check balance of some accounts
		assertEquals(0, DanskeBank.getBalance("Gertrud").intValue());
		assertEquals(0, Nordea.getBalance("Bob").intValue());

		// add some money to those account
		SweBank.deposit("Bob", new Money(100, SEK));
		DanskeBank.deposit("Gertrud", new Money(20, DKK));

		// withdraw money to those account
		SweBank.withdraw("Bob", new Money(20, SEK));
		DanskeBank.withdraw("Gertrud", new Money(230, DKK));
		DanskeBank.withdraw("Gertrud", new Money(40, DKK));

		// check if money was subtracted
		assertEquals(80, SweBank.getBalance("Bob").intValue());
		assertEquals(-250, DanskeBank.getBalance("Gertrud").intValue());
	}
	
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {

		// perform deposit/withdraw operations
		SweBank.deposit("Bob", new Money(100, SEK));
		Nordea.deposit("Bob", new Money(20, SEK));
		SweBank.withdraw("Bob", new Money(20, SEK));
		DanskeBank.withdraw("Gertrud", new Money(230, DKK));

		// check the balance
		assertEquals(80, SweBank.getBalance("Bob").intValue());
		assertEquals(-230, DanskeBank.getBalance("Gertrud").intValue());
		assertEquals(20, Nordea.getBalance("Bob").intValue());
	}
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		// check balance of some accounts
		assertEquals(0, SweBank.getBalance("Bob").intValue());
		assertEquals(0, SweBank.getBalance("Ulrika").intValue());
		assertEquals(0, DanskeBank.getBalance("Gertrud").intValue());
		assertEquals(0, Nordea.getBalance("Bob").intValue());

		//perform transfer
			//75 SEK from Bob(SweBank) to Gertrud(DanskeBank)
		SweBank.transfer("Bob", DanskeBank, "Gertrud", new Money(75, SEK));
			//20 SEK from Gertrud(DanskeBank) to Bob(Nordea)
		DanskeBank.transfer("Gertrud", Nordea, "Bob", new Money(15, SEK));
		SweBank.transfer("Bob", "Ulrika", new Money(25, SEK));

		// check the balance
		assertEquals(-100, SweBank.getBalance("Bob").intValue());
		assertEquals(80, DanskeBank.getBalance("Gertrud").intValue());
		assertEquals(15, Nordea.getBalance("Bob").intValue());
		assertEquals(25, SweBank.getBalance("Ulrika").intValue());

	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		//create Timed Payment
		SweBank.addTimedPayment("Bob", "1", 2, 1, new Money(10000, SEK), Nordea, "Bob");

		//check if Timed Payment was created
		Account bob = SweBank.getAccount("Bob");
		assertTrue(bob.timedPaymentExists("1"));

		// some time pass
		SweBank.tick();
		SweBank.tick();

		// check balances
		assertEquals(-10000, SweBank.getBalance("Bob").intValue());
		assertEquals(10000, Nordea.getBalance("Bob").intValue());
	}
}

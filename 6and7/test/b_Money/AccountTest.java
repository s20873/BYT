package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea, DanskeBank, SweBank;
	Account hans, alisa, john;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);

		hans = new Account("Hans", SEK);
		alisa = new Account("Alisa", SEK);
		john = new Account("John", DKK);

		hans.deposit(new Money(10000000, SEK));
		alisa.deposit(new Money(350000, SEK));
		john.deposit(new Money(20000, DKK));

		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
	}
	
	@Test
	public void testAddRemoveTimedPayment() {
		//create some Timed Payments
		alisa.addTimedPayment("2", 2, 1, new Money(2000, SEK), SweBank, "Bob");
		john.addTimedPayment("3", 10, 2, new Money(100000, SEK), SweBank, "Ulrika");

		// check if payments were created
		assertTrue(alisa.timedPaymentExists("2"));
		assertTrue(john.timedPaymentExists("3"));

		//remove Timed Payments
		alisa.removeTimedPayment("2");
		john.removeTimedPayment("3");

		// check if payments were removed
		assertFalse(alisa.timedPaymentExists("2"));
		assertFalse(john.timedPaymentExists("3"));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		//create a Timed Payment from hans to bob
		hans.addTimedPayment("0", 1, 1, new Money(10000, SEK), SweBank, "Bob");

		// check if payment was created
		assertTrue(hans.timedPaymentExists("0"));

		// passage of time
		hans.tick();
		hans.tick();

		//check if payments were done
		assertEquals(9990000, hans.getBalance().intValue());
		assertEquals(10000, SweBank.getAccount("Bob").getBalance().intValue());

		// passage of more time
		hans.tick();
		hans.tick();

		//check if payments were done again
		assertEquals(9980000, hans.getBalance().intValue());
		assertEquals(20000, SweBank.getAccount("Bob").getBalance().intValue());

		//remove Timed Payment
		hans.removeTimedPayment("0");

		// passage of more-more time
		hans.tick();
		hans.tick();

		//check if payments were not done again
		assertEquals(9980000, hans.getBalance().intValue());
		assertEquals(20000, SweBank.getAccount("Bob").getBalance().intValue());

	}

	@Test
	public void testAddWithdraw() {

		// perform deposit/withdraw operations
		alisa.withdraw(new Money(10000, SEK));
		john.withdraw(new Money(5000, DKK));
		alisa.deposit(new Money(10000, SEK));

		//check the balance
		assertEquals(350000, alisa.getBalance().intValue());
		assertEquals(10000000, hans.getBalance().intValue());
		assertEquals(15000, john.getBalance().intValue());

	}
	
	@Test
	public void testGetBalance() {

		// check the balance
		assertEquals(350000, alisa.getBalance().intValue());
		assertEquals(10000000, hans.getBalance().intValue());
		assertEquals(20000, john.getBalance().intValue());

	}
}

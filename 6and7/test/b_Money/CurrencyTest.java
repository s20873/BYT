package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, EUR;
	
	@Before
	public void setUp() {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {
		/* Check names for each currency*/
		assertEquals("SEK", SEK.getName());
		assertEquals("DKK", DKK.getName());
		assertEquals("EUR", EUR.getName());
	}
	
	@Test
	public void testGetRate() {
		/* Check rate for each currency*/
		assertEquals(0.15, SEK.getRate(), 0);
		assertEquals(0.20, DKK.getRate(), 0);
		assertEquals(1.5, EUR.getRate(), 0);
	}
	
	@Test
	public void testSetRate() {
		double newRate = 0.1; //new rate for SEK
		SEK.setRate(newRate); //assign new rate
		assertEquals(newRate, SEK.getRate(), 0); //check it

		newRate = 0.25; //new rate for DKK
		DKK.setRate(newRate); //assign new rate
		assertEquals(newRate, DKK.getRate(),0); //check it

		newRate = 1.3; //new rate for EUR
		EUR.setRate(newRate); //assign new rate
		assertEquals(newRate, EUR.getRate(),0); //check it
	}
	
	@Test
	public void testUniversalValue() {
		int amount = 150;
		int expected = (int) (amount / SEK.getRate()); //1000
		assertEquals(expected, SEK.universalValue(amount).intValue());

		amount = 60;
		expected = (int) (amount / DKK.getRate()); //300
		assertEquals(expected, DKK.universalValue(amount).intValue());

		amount = 135;
		expected = (int) (amount / EUR.getRate()); //90
		assertEquals(expected, EUR.universalValue(amount).intValue());
	}
	
	@Test
	public void testValueInThisCurrency() {
		int amount = 150;
		int expected = 1500;
		// 150 SEK in EUR
		assertEquals(expected, EUR.valueInThisCurrency(amount,SEK).intValue());

		amount = 135;
		expected = 18;
		// 135 EUR in DKK
		assertEquals(expected, DKK.valueInThisCurrency(amount, EUR).intValue());

		amount = 120;
		expected = 90;
		// 120 DKK in SEK
		assertEquals(expected, SEK.valueInThisCurrency(amount, DKK).intValue());
	}

}

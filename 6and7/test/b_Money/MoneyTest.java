package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Set;

public class MoneyTest {
	Currency SEK, DKK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100, EUR1000;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);

		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
		EUR1000 = new Money(100000, EUR);
	}

	@Test
	public void testGetAmount() {
		//amount is in Integer so 10.5 EUR == 1050 EUR
		assertEquals(10000, SEK100.getAmount(), 0);
		assertEquals(1000, EUR10.getAmount(), 0);
		assertEquals(20000, SEK200.getAmount(), 0);
		assertEquals(2000, EUR20.getAmount(), 0);
		assertEquals(0, SEK0.getAmount(), 0);
		assertEquals(0, EUR0.getAmount(), 0);
		assertEquals(-10000, SEKn100.getAmount(), 0);
	}

	@Test
	public void testGetCurrency() {
		//check if objects of class Currency are the same
		assertEquals(SEK, SEK100.getCurrency());
		assertEquals(EUR, EUR10.getCurrency());
		assertEquals(SEK, SEK200.getCurrency());
		assertEquals(EUR, EUR20.getCurrency());
		assertEquals(SEK, SEK0.getCurrency());
		assertEquals(EUR, EUR0.getCurrency());
		assertEquals(SEK, SEKn100.getCurrency());
	}

	@Test
	public void testToString() {
		assertEquals("100.0 SEK", SEK100.toString());
		assertEquals("10.0 EUR", EUR10.toString());
		assertEquals("200.0 SEK", SEK200.toString());
		assertEquals("20.0 EUR", EUR20.toString());
		assertEquals("0.0 SEK", SEK0.toString());
		assertEquals("0.0 EUR", EUR0.toString());
		assertEquals("-100.0 SEK", SEKn100.toString());
	}

	@Test
	public void testUniversalValue() {
		//each amount of some currency is converted to universal value(1 universal == rate)
		assertEquals(66666, SEK100.universalValue().intValue());
		assertEquals(666, EUR10.universalValue().intValue());
		assertEquals(133333, SEK200.universalValue().intValue());
		assertEquals(1333, EUR20.universalValue().intValue());
		assertEquals(0, SEK0.universalValue().intValue());
		assertEquals(0, EUR0.universalValue().intValue());
		assertEquals(-66666, SEKn100.universalValue().intValue());
	}

	@Test
	public void testEqualsMoney() {
		// 0 SEK == 0 SEK
		assertTrue("Should be equal", SEK0.equals(EUR0));
		// 100 SEK == 100 SEK
		assertTrue("Should be equal", SEK100.equals(EUR1000));
		// 100 SEK != 2 SEK
		assertFalse("Should not be equal", SEK100.equals(EUR20));
		// 100 SEK != -100 SEK
		assertFalse("Should not be equal", SEK100.equals(SEKn100));
		// 10 EUR == 10 EUR
		assertTrue("Should be equal", EUR10.equals(EUR10));
	}

	@Test
	public void testAdd() {
		Money newMoney = SEK100.add(SEK200); //300 SEK in result
		assertEquals(SEK, newMoney.getCurrency());
		assertEquals(30000, newMoney.getAmount().intValue());

		newMoney = EUR1000.add(SEK100); //200 EUR in result
		assertEquals(EUR, newMoney.getCurrency());
		assertEquals(200000, newMoney.getAmount().intValue());

		newMoney = SEK0.add(EUR20); // 2 SEK in result
		assertEquals(SEK, newMoney.getCurrency());
		assertEquals(200, newMoney.getAmount().intValue());
	}

	@Test
	public void testSub() {
		Money newMoney = SEK200.sub(SEK100); // 100 SEK in result
		assertEquals(SEK, newMoney.getCurrency());
		assertEquals(10000, newMoney.getAmount().intValue());

		newMoney = SEKn100.sub(EUR20); //- 102 SEK in result
		assertEquals(SEK, newMoney.getCurrency());
		assertEquals(-10200, newMoney.getAmount().intValue());

		newMoney = EUR10.sub(SEK0); // 10 EUR in result
		assertEquals(EUR, newMoney.getCurrency());
		assertEquals(1000, newMoney.getAmount().intValue());
	}

	@Test
	public void testIsZero() {
		assertTrue("Is zero", SEK0.isZero());
		assertTrue("Is zero", EUR0.isZero());
		assertFalse("Is not zero", SEK100.isZero());
		assertFalse("Is not zero", EUR1000.isZero());
		assertFalse("Is not zero", SEKn100.isZero());
	}

	@Test
	public void testNegate() {
		Money newMoney = SEKn100.negate(); // 100 SEK in result
		assertEquals(SEKn100.getCurrency(), newMoney.getCurrency());
		assertEquals(10000, newMoney.getAmount().intValue());

		newMoney = SEK200.negate(); // -200 SEK in result
		assertEquals(SEK200.getCurrency(), newMoney.getCurrency());
		assertEquals(-20000, newMoney.getAmount().intValue());

		newMoney = EUR1000.negate(); // -1000 EUR in result
		assertEquals(EUR1000.getCurrency(), newMoney.getCurrency());
		assertEquals(-100000, newMoney.getAmount().intValue());
	}

	@Test
	public void testCompareTo() {
		assertTrue(0 == SEK0.compareTo(EUR0)); // 0 SEK == 0 EUR
		assertTrue(-1 == EUR10.compareTo(SEK100)); // 10 EUR < 100 SEK
		assertTrue(1 == EUR1000.compareTo(SEK0)); // 1000 EUR > 0 SEK
		assertTrue(-1 == SEK100.compareTo(SEK200)); // 100 SEK < 200 SEK
	}
}

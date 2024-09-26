package information;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class InformationTest {
	@Test
	public void testCreationInformationVide() {
		final Information<Boolean> i = new Information<Boolean>();

		assertEquals(i.nbElements(), 0);
	}

	@Test
	public void testSousInformation() {
		final Boolean[] values = { true, false, true, true };
		final Information<Boolean> i = new Information<Boolean>(values);
		final Information<Boolean> subI = i.sousInformation(1, 2);

		// Test i
		assertEquals(4, i.nbElements());
		assertEquals(true, i.iemeElement(0));
		assertEquals(false, i.iemeElement(1));
		assertEquals(true, i.iemeElement(2));
		assertEquals(true, i.iemeElement(3));

		// Test subI
		assertEquals(2, subI.nbElements(), 2);
		assertEquals(false, subI.iemeElement(0));
		assertEquals(true, subI.iemeElement(1));
	}
}

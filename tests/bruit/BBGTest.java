package bruit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import information.Information;

public class BBGTest {

	@Test()
	public void testGenerationBruit() {
		final BBG bruit = new BBG(20f, 25);
		Float[] values = { 2f, 2f, 2f, 2f };
		final Information<Float> signal = new Information<Float>(values);

		Information<Float> signalBruite = bruit.bruitage(signal);
		final Float[] sortie = { 1.9470681f, 2.652151f, 2.0886931f, 3.030108f };
		final Information<Float> infoSortie = new Information<Float>(sortie);

		assertEquals(infoSortie, signalBruite);
	}

}

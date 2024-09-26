package bruit;

import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.junit.Rule;
import org.junit.Test;

import information.Information;
import org.junit.rules.ErrorCollector;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;


public class BBGTest {

	@Rule
	public ErrorCollector collector = new ErrorCollector();
	/**
	 * Teste la génération de bruit par la classe BBG.
	 */
	/*
	@Test()
	public void testGenerationBruit() {
		final BBG bruit = new BBG(20f, 25);
		Float[] values = { 2f, 2f, 2f, 2f };
		final Information<Float> signal = new Information<Float>(values);
		Information<Float> signalBruite = bruit.bruitage(signal);
		final Float[] sortie = { 1.9470681f, 2.652151f, 2.0886931f, 3.030108f };
		final Information<Float> infoSortie = new Information<Float>(sortie);
		collector.checkThat(infoSortie, signalBruite);
	}
	*/
	@Test
	public void testCalculPuissanceSignal() {
		final BBG bruit = new BBG(20f, 25);
		ArrayList<Float> signal = new ArrayList<>(Arrays.asList(2f, 2f, 2f, 2f));
		Information<Float> mockInformation = mockInit(signal);
		final Float puissanceSignal = bruit.calculPuissanceSignal(mockInformation);
		collector.checkThat(4f, is(puissanceSignal));
	}

	@Test
	public void testCalculVariance() {

		final BBG bruit = new BBG(20f, 25);
		Information<Float> mockInformation = mockInit(new ArrayList<>(Arrays.asList(2f, 2f, 2f, 2f)));
		final Float variance = bruit.calculVariance(bruit.calculPuissanceSignal(mockInformation), 20f, 4);
		collector.checkThat(0.08f, is(variance));
	}

	public static Information<Float> mockInit(ArrayList<Float> signal){
		Information<Float> mockInformation = EasyMock.createMock(Information.class);
		EasyMock.expect(mockInformation.getContent()).andReturn(signal);
		EasyMock.expect(mockInformation.iterator()).andReturn(signal.iterator());
		EasyMock.expect(mockInformation.nbElements()).andReturn(4);
		EasyMock.replay(mockInformation);
		return mockInformation;

	}
	//TO do: test bruitage + 1 constructeur

}


package simulateur;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import information.InformationNonConformeException;

public class SimulateurTest {
	/**
	 * Lancement d'une simulation analogique de forme d'onde RZ avec un message
	 * aléatoire
	 * 
	 * @throws InformationNonConformeException
	 * @throws ArgumentsException
	 */
	@Test
	public void testSimulationAnalogiqueRZMessageAleatoire()
			throws ArgumentsException, InformationNonConformeException {
		final String[] args = { "-mess", "500", "-form", "RZ" };
		final Simulateur s = new Simulateur(args);

		s.execute();
		assertEquals(true, s.getTransmissionAnalogique());
	}

	/**
	 * Lancement d'une simulation analogique de forme d'onde RZ avec un message
	 * défini
	 * 
	 * @throws InformationNonConformeException
	 * @throws ArgumentsException
	 */
	@Test
	public void testSimulationAnalogiqueRZMessageFixe() throws ArgumentsException, InformationNonConformeException {
		final String[] args = { "-mess", "01110010110001", "-form", "RZ" };
		final Simulateur s = new Simulateur(args);

		s.execute();
		assertEquals(true, s.getTransmissionAnalogique());
	}

	/**
	 * Lancement d'une simulation analogique de forme d'onde NRZT avec un message
	 * défini
	 * 
	 * @throws InformationNonConformeException
	 * @throws ArgumentsException
	 */
	@Test
	public void testSimulationAnalogiqueNRZTMessageFixe() throws ArgumentsException, InformationNonConformeException {
		final String[] args = { "-mess", "01110010110001", "-form", "NRZT" };
		final Simulateur s = new Simulateur(args);

		s.execute();
		assertEquals(true, s.getTransmissionAnalogique());
	}

	/**
	 * Lancement d'une simulation analogique de forme d'onde NRZT avec du bruit
	 * 
	 * @throws InformationNonConformeException
	 * @throws ArgumentsException
	 */
	@Test
	public void testSimulationAnalogiqueNRZTBruite() throws ArgumentsException, InformationNonConformeException {
		final String[] args = { "-mess", "1000", "-form", "NRZT", "-snrpb", "-5,9" };
		final Simulateur s = new Simulateur(args);

		s.execute();
		assertEquals(true, s.getTransmissionAnalogique());
	}

	/**
	 * Lancement d'une simulation logique
	 * 
	 * @throws InformationNonConformeException
	 * @throws ArgumentsException
	 */
	@Test
	public void testSimulationLogique() throws ArgumentsException, InformationNonConformeException {
		final String[] args = { "-mess", "500" };
		final Simulateur s = new Simulateur(args);

		s.execute();
		assertEquals(false, s.getTransmissionAnalogique());
		s.calculTauxErreurBinaire();
	}
}

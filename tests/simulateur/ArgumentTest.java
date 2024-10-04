package simulateur;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Optional;

//import destinations.Destination;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
//import sources.analogique.SourceAnalogique;
import sources.SourceAnalogiqueType;

/**
 * Classe de test pour Argument. Utilise JUnit pour les assertions et
 * ExpectedException pour gérer les exceptions attendues.
 */
public class ArgumentTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	/**
	 * Teste la gestion d'une forme invalide.
	 *
	 * @throws ArgumentsException si la forme est invalide.
	 */
	@Test()
	public void testFormeInvalide() throws ArgumentsException {
		final String[] args = { "-form", "TEST_INVALIDE" };

		thrown.expect(ArgumentsException.class);
		thrown.expectMessage("Argument invalide pour la forme d'onde, attendu : RZ | NRZ | NRZT, reçu : TEST_INVALIDE");

		new Simulateur(args);
	}

	/**
	 * Teste la gestion d'une amplitude minimale supérieure à l'amplitude maximale.
	 *
	 * @throws ArgumentsException si l'amplitude minimale est supérieure à
	 *                            l'amplitude maximale.
	 */
	@Test()
	public void testAmplitudeMinSuperieurAMax() throws ArgumentsException {
		final String[] args = { "-form", "NRZ", "-ampl", "3", "1" };

		thrown.expect(ArgumentsException.class);
		thrown.expectMessage(
				"L'amplitude min ne peut pas être supérieure ou égale à l'amplitude max, valeurs renseignées :\n"
						+ "min : 3.0, max : 1.0");

		new Simulateur(args);
	}

	/**
	 * Teste la gestion d'une amplitude minimale égale à l'amplitude maximale.
	 *
	 * @throws ArgumentsException si l'amplitude minimale est égale à l'amplitude
	 *                            maximale.
	 */
	@Test()
	public void testAmplitudeMinEgaleAMax() throws ArgumentsException {
		final String[] args = { "-form", "NRZ", "-ampl", "3", "3" };

		thrown.expect(ArgumentsException.class);
		thrown.expectMessage(
				"L'amplitude min ne peut pas être supérieure ou égale à l'amplitude max, valeurs renseignées :\n"
						+ "min : 3.0, max : 3.0");

		new Simulateur(args);
	}

	/**
	 * Teste la gestion d'une amplitude minimale différente de 0 pour la forme RZ.
	 *
	 * @throws ArgumentsException si l'amplitude minimale est différente de 0 pour
	 *                            la forme RZ.
	 */
	@Test()
	public void testRZAmplitudeMinDifferentDe0() throws ArgumentsException {
		final String[] args = { "-form", "RZ", "-ampl", "-3", "3" };

		thrown.expect(ArgumentsException.class);
		thrown.expectMessage(
				"Attention : Pour une forme d'onde impulsionnelle (RZ), l'amplitude min est forcément égale à 0");

		new Simulateur(args);
	}

	/**
	 * Teste la gestion d'une amplitude maximale inférieure à 0 pour la forme NRZ.
	 *
	 * @throws ArgumentsException si l'amplitude maximale est inférieure à 0 pour la
	 *                            forme NRZ.
	 */
	@Test()
	public void testNRZAmplitudeMaxInferieurA0() throws ArgumentsException {
		final String[] args = { "-form", "NRZ", "-ampl", "-5", "-1.2" };

		thrown.expect(ArgumentsException.class);
		thrown.expectMessage(
				"Pour une forme d'onde rectangulaire ou trapézoïdale (NRZ/NRZT), la valeur de l'amplitude max doit être supérieure ou égale à 0, valeur renseignée : -1.2");

		new Simulateur(args);
	}

	/**
	 * Teste la gestion d'une amplitude maximale inférieure à 0 pour la forme NRZT.
	 *
	 * @throws ArgumentsException si l'amplitude maximale est inférieure à 0 pour la
	 *                            forme NRZT.
	 */
	@Test()
	public void testNRZTAmplitudeMaxInferieurA0() throws ArgumentsException {
		final String[] args = { "-form", "NRZT", "-ampl", "-5", "-1.2" };

		thrown.expect(ArgumentsException.class);
		thrown.expectMessage(
				"Pour une forme d'onde rectangulaire ou trapézoïdale (NRZ/NRZT), la valeur de l'amplitude max doit être supérieure ou égale à 0, valeur renseignée : -1.2");

		new Simulateur(args);
	}

	/**
	 * Teste la gestion d'une amplitude minimale supérieure à 0 pour la forme NRZ.
	 *
	 * @throws ArgumentsException si l'amplitude minimale est supérieure à 0 pour la
	 *                            forme NRZ.
	 */
	@Test()
	public void testNRZAmplitudeMinSuperieurA0() throws ArgumentsException {
		final String[] args = { "-form", "NRZ", "-ampl", "0.3", "1.2" };

		thrown.expect(ArgumentsException.class);
		thrown.expectMessage(
				"Pour une forme d'onde rectangulaire ou trapézoïdale (NRZ/NRZT), la valeur de l'amplitude min doit être inférieure ou égale à 0, valeur renseignée : 0.3");

		new Simulateur(args);
	}

	/**
	 * Teste la gestion d'une amplitude minimale supérieure à 0 pour la forme NRZT.
	 *
	 * @throws ArgumentsException si l'amplitude minimale est supérieure à 0 pour la
	 *                            forme NRZT.
	 */
	@Test()
	public void testNRZTAmplitudeMinSuperieurA0() throws ArgumentsException {
		final String[] args = { "-form", "NRZT", "-ampl", "0.3", "1.2" };

		thrown.expect(ArgumentsException.class);
		thrown.expectMessage(
				"Pour une forme d'onde rectangulaire ou trapézoïdale (NRZ/NRZT), la valeur de l'amplitude min doit être inférieure ou égale à 0, valeur renseignée : 0.3");

		new Simulateur(args);
	}

	/**
	 * Teste la gestion d'une amplitude minimale et maximale égale à 0 pour la forme
	 * NRZ.
	 *
	 * @throws ArgumentsException si l'amplitude minimale et maximale est égale à 0
	 *                            pour la forme NRZ.
	 */
	@Test()
	public void testNRZAmplitudeMinMaxEgaleA0() throws ArgumentsException {
		final String[] args = { "-form", "NRZ", "-ampl", "0", "0" };

		thrown.expect(ArgumentsException.class);
		thrown.expectMessage(
				"L'amplitude min ne peut pas être supérieure ou égale à l'amplitude max, valeurs renseignées :\n"
						+ "min : 0.0, max : 0.0");

		new Simulateur(args);
	}

	/**
	 * Teste la gestion d'une amplitude minimale et maximale égale à 0 pour la forme
	 * NRZT.
	 *
	 * @throws ArgumentsException si l'amplitude minimale et maximale est égale à 0
	 *                            pour la forme NRZT.
	 */
	@Test()
	public void testNRZTAmplitudeMinMaxEgaleA0() throws ArgumentsException {
		final String[] args = { "-form", "NRZT", "-ampl", "0", "0" };

		thrown.expect(ArgumentsException.class);
		thrown.expectMessage(
				"L'amplitude min ne peut pas être supérieure ou égale à l'amplitude max, valeurs renseignées :\n"
						+ "min : 0.0, max : 0.0");

		new Simulateur(args);
	}

	/**
	 * Teste la gestion d'une amplitude minimale erronée.
	 *
	 * @throws ArgumentsException si l'amplitude minimale est erronée.
	 */
	@Test()
	public void testAmplitudeMinErronee() throws ArgumentsException {
		final String[] args = { "-ampl", "0@9", "0" };

		thrown.expect(ArgumentsException.class);
		thrown.expectMessage("Valeur du parametre amplitude min invalide : 0@9");

		new Simulateur(args);
	}

	/**
	 * Teste la gestion d'une amplitude maximale erronée.
	 *
	 * @throws ArgumentsException si l'amplitude maximale est erronée.
	 */
	@Test()
	public void testAmplitudeMaxErronee() throws ArgumentsException {
		final String[] args = { "-ampl", "0", "0@9" };

		thrown.expect(ArgumentsException.class);
		thrown.expectMessage("Valeur du parametre amplitude max invalide : 0@9");

		new Simulateur(args);
	}

	/**
	 * Teste la gestion d'une amplitude minimale égale à 0 pour la forme NRZ.
	 *
	 * @throws ArgumentsException si l'amplitude minimale est égale à 0 pour la
	 *                            forme NRZ.
	 */
	@Test()
	public void testNRZAmplitudeMinEgaleA0() throws ArgumentsException {
		final String[] args = { "-form", "NRZ", "-ampl", "0", "1.2" };

		new Simulateur(args);
	}

	/**
	 * Teste la gestion d'une amplitude minimale égale à 0 pour la forme NRZT.
	 *
	 * @throws ArgumentsException si l'amplitude minimale est égale à 0 pour la
	 *                            forme NRZT.
	 */
	@Test()
	public void testNRZTAmplitudeMinEgaleA0() throws ArgumentsException {
		final String[] args = { "-form", "NRZT", "-ampl", "0", "1.2" };

		new Simulateur(args);
	}

	/**
	 * Teste la gestion d'une amplitude maximale égale à 0 pour la forme NRZ.
	 *
	 * @throws ArgumentsException si l'amplitude maximale est égale à 0 pour la
	 *                            forme NRZ.
	 */
	@Test()
	public void testNRZAmplitudeMaxEgaleA0() throws ArgumentsException {
		final String[] args = { "-form", "NRZ", "-ampl", "-1.2", "0" };

		new Simulateur(args);
	}

	/**
	 * Teste la gestion d'une amplitude maximale égale à 0 pour la forme NRZT.
	 *
	 * @throws ArgumentsException si l'amplitude maximale est égale à 0 pour la
	 *                            forme NRZT.
	 */
	@Test()
	public void testNRZTAmplitudeMaxEgaleA0() throws ArgumentsException {
		final String[] args = { "-form", "NRZT", "-ampl", "-1.2", "0" };

		new Simulateur(args);
	}

	/**
	 * Test de l'argument snrpb valide
	 */
	@Test()
	public void testSNRPBValide() throws ArgumentsException {
		final String[] args = { "-snrpb", "10" };
		new Simulateur(args);
	}

	/**
	 * Test de l'argument snrpb invalide
	 */
	@Test()
	public void testSNRPBInvalide() throws ArgumentsException {
		final String[] args = { "-snrpb", "10@" };
		thrown.expect(ArgumentsException.class);
		thrown.expectMessage("Valeur du parametre signal a bruit invalide : 10@");
		new Simulateur(args);
	}

	/**
	 * Test de l'argument snrpb avec SNR négatif
	 */
	@Test()
	public void testSNRPBNegatif() throws ArgumentsException {
		final String[] args = { "-snrpb", "-10" };
		new Simulateur(args);
	}

	/**
	 * Test de l'argument snrpb avec SNR négatif et float
	 */
	@Test()
	public void testSNRPBNegatifFloat() throws ArgumentsException {
		final String[] args = { "-snrpb", "-10.5" };
		new Simulateur(args);
	}

	/**
	 * Test de l'argument snrpb avec SNR négatif et float mais invalide
	 */
	@Test()
	public void testSNRPBNegatifFloatInvalide() throws ArgumentsException {
		final String[] args = { "-snrpb", "-10.5@" };
		thrown.expect(ArgumentsException.class);
		thrown.expectMessage("Valeur du parametre signal a bruit invalide : -10.5@");
		new Simulateur(args);
	}

	/**
	 * Test de l'argument nbEch inférieur à 3
	 */
	@Test()
	public void testNbEchInferieurA0() throws ArgumentsException {
		final String[] args = { "-nbEch", "-2" };

		thrown.expect(ArgumentsException.class);
		thrown.expectMessage("Valeur du parametre -nbEch invalide : -2");

		new Simulateur(args);
	}

	/**
	 * Test de l'argument nbEch valide
	 */
	@Test()
	public void testNbEchNonMultipleDe3() throws ArgumentsException {
		final String[] args = { "-nbEch", "31" };

		// Capture output
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		final PrintStream originalOut = System.out;
		System.setOut(new PrintStream(outContent));

		try {
			new Simulateur(args);
			assertTrue(outContent.toString().contains("\tAttention: Le nombre d'échantillons a été ajusté à 33"));
		} finally {
			// Restore output in a safe way (even in there is an exception
			System.setOut(originalOut);
		}

	}

	/**
	 * Test de l'argument nbEch valide
	 */
	@Test()
	public void testNbEchValide() throws ArgumentsException {
		final String[] args = { "-nbEch", "30" };

		new Simulateur(args);
	}

	/**
	 * Test de l'argument mess chaine binaire valide
	 */
	@Test()
	public void testMessBinaireValide() throws ArgumentsException {
		final String[] args = { "-mess", "0110001" };

		new Simulateur(args);
	}

	/**
	 * Test de l'argument mess nombre valide
	 */
	@Test()
	public void testMessNombreValide() throws ArgumentsException {
		final String[] args = { "-mess", "100000" };

		new Simulateur(args);
	}

	/**
	 * Test de l'argument mess chiffre trop long
	 */
	@Test()
	public void testMessNombreTropLong() throws ArgumentsException {
		final String[] args = { "-mess", "2000000" };

		thrown.expect(ArgumentsException.class);
		thrown.expectMessage("Valeur du paramètre -mess invalide : 2000000");

		new Simulateur(args);
	}

	/**
	 * Test de l'argument mess chiffre nul
	 */
	@Test()
	public void testMessNombreEgalA0() throws ArgumentsException {
		final String[] args = { "-mess", "0" };

		thrown.expect(ArgumentsException.class);
		thrown.expectMessage("Valeur du paramètre -mess invalide : 0");

		new Simulateur(args);
	}

	/**
	 * Test de l'argument mess nombre negatif
	 */
	@Test()
	public void testMessNombreNegatif() throws ArgumentsException {
		final String[] args = { "-mess", "-500" };

		thrown.expect(ArgumentsException.class);
		thrown.expectMessage("Valeur du paramètre -mess invalide : -500");

		new Simulateur(args);
	}

	/**
	 * Test de l'argument seed valide
	 */
	@Test()
	public void testSeedValide() throws ArgumentsException {
		final String[] args = { "-seed", "-500" };

		new Simulateur(args);
	}

	/**
	 * Test de l'argument seed avec un nombre invalide
	 */
	@Test()
	public void testSeedNombreInvalide() throws ArgumentsException {
		final String[] args = { "-seed", "INVALIDE" };

		thrown.expect(ArgumentsException.class);
		thrown.expectMessage("Valeur du paramètre -seed invalide : INVALIDE");

		new Simulateur(args);
	}

	/**
	 * Test de l'argument -ti valide
	 */
	@Test()
	public void testTIValide() throws ArgumentsException {
		final String[] args = { "-ti", "0", "0,9", "23", "0.4", "11", "0.4", "13", "0.4", "9", "0.4" };
		new Simulateur(args);
	}

	/**
	 * Test de l'argument -ti > à 5 trajets
	 */
	@Test()
	public void testTISuperieur5Trajet() throws ArgumentsException {
		final String[] args = { "-ti", "2", "0.9", "23", "0.4", "11", "0.4", "13", "0.4", "9", "0.4", "50", "0.4" };
		thrown.expect(ArgumentsException.class);
		thrown.expectMessage("Nombre de trajets supérieur à 5");
		new Simulateur(args);
	}

	/**
	 * Test de l'argument -ti alpha < 0
	 */
	@Test()
	public void testTIAlphaInferieurA0() throws ArgumentsException {
		final String[] args = { "-ti", "2", "-0.9" };
		thrown.expect(ArgumentsException.class);
		thrown.expectMessage("Le paramètre ar doit être compris entre 0 inclus et 1 inclus, ar1 : -0.9");
		new Simulateur(args);
	}

	/**
	 * Test de l'argument -ti alpha > 1
	 */
	@Test()
	public void testTIAlphaSuperieurA1() throws ArgumentsException {
		final String[] args = { "-ti", "2", "1.5" };
		thrown.expect(ArgumentsException.class);
		thrown.expectMessage("Le paramètre ar doit être compris entre 0 inclus et 1 inclus, ar1 : 1.5");
		new Simulateur(args);
	}

	/**
	 * Test de l'argument -ti alpha invalide
	 */
	@Test()
	public void testTIAlphaInvalide() throws ArgumentsException {
		final String[] args = { "-ti", "8", "0.'4" };
		thrown.expect(ArgumentsException.class);
		thrown.expectMessage("Paramètre ar1 invalide : 0.'4");
		new Simulateur(args);
	}

	/**
	 * Test de l'argument -ti dt < 0
	 */
	@Test()
	public void testTIDtInferieurA0() throws ArgumentsException {
		final String[] args = { "-ti", "-6", "0.4" };
		thrown.expect(ArgumentsException.class);
		thrown.expectMessage("Le paramètre dt doit être positif (>= 0), dt1 : -6");
		new Simulateur(args);
	}

	/**
	 * Test de l'argument -ti dt invalide
	 */
	@Test()
	public void testTIDtInvalide() throws ArgumentsException {
		final String[] args = { "-ti", "8@", "0.4" };
		thrown.expect(ArgumentsException.class);
		thrown.expectMessage("Pas de valeur du paramètre de trajet multiple renseignée ou paramètre dt1 invalide");
		new Simulateur(args);
	}

	/**
	 * Test de l'argument -ti dt invalide
	 */
	@Test()
	public void testTIDtInvalideFloat() throws ArgumentsException {
		final String[] args = { "-ti", "8,4", "0.4" };
		thrown.expect(ArgumentsException.class);
		thrown.expectMessage("Paramètre dt1 invalide : 8,4");
		new Simulateur(args);
	}

	/*
	 *
	 * ARGUMENT VIDE
	 *
	 */

	/**
	 * Test de l'argument snrpb avec SNR vide
	 */
	@Test()
	public void testSNRPBVide() throws ArgumentsException {
		final String[] args = { "-snrpb" };
		thrown.expect(ArgumentsException.class);
		thrown.expectMessage("Pas de valeur du paramètre de signal à bruit renseignée");
		new Simulateur(args);
	}

	/**
	 * Test de l'argument -mess vide
	 */
	@Test()
	public void testMessVide() throws ArgumentsException {
		final String[] args = { "-mess" };
		thrown.expect(ArgumentsException.class);
		thrown.expectMessage("Pas de valeur du paramètre de message renseignée");
		new Simulateur(args);
	}

	/**
	 * Test de l'argument -seed vide
	 */
	@Test()
	public void testSeedVide() throws ArgumentsException {
		final String[] args = { "-seed" };
		thrown.expect(ArgumentsException.class);
		thrown.expectMessage("Pas de valeur du paramètre de seed renseignée");
		new Simulateur(args);
	}

	/**
	 * Test de l'argument -nbEch vide
	 */
	@Test()
	public void testNbEchVide() throws ArgumentsException {
		final String[] args = { "-nbEch" };
		thrown.expect(ArgumentsException.class);
		thrown.expectMessage("Pas de valeur du paramètre de nombre d'échantillons renseignée");
		new Simulateur(args);
	}

	/**
	 * Test de l'argument -form vide
	 */
	@Test()
	public void testFormVide() throws ArgumentsException {
		final String[] args = { "-form" };
		thrown.expect(ArgumentsException.class);
		thrown.expectMessage("Pas de valeur du paramètre de forme d'onde renseignée");
		new Simulateur(args);
	}

	/**
	 * Test de l'argument -ampl vide
	 */
	@Test()
	public void testAmplMinVide() throws ArgumentsException {
		final String[] args = { "-ampl" };
		thrown.expect(ArgumentsException.class);
		thrown.expectMessage("Pas de valeur du paramètre d'amplitude min renseignée");
		new Simulateur(args);
	}

	/**
	 * Test de l'argument -ampl vide
	 */
	@Test()
	public void testAmplMaxVide() throws ArgumentsException {
		final String[] args = { "-ampl", "0" };
		thrown.expect(ArgumentsException.class);
		thrown.expectMessage("Pas de valeur du paramètre d'amplitude max renseignée");
		new Simulateur(args);
	}

	/**
	 * Test de l'argument -form et -ampl vide
	 */
	@Test()
	public void testFormAmplVide() throws ArgumentsException {
		final String[] args = { "-form", "-ampl" };
		thrown.expect(ArgumentsException.class);
		thrown.expectMessage("Argument invalide pour la forme d'onde, attendu : RZ | NRZ | NRZT, reçu : -ampl");
		new Simulateur(args);
	}
	/**
	 * Test des getter des arguments
	 */
	@Test()
	public void testGetAguments() throws ArgumentsException{
		final String[] args = { "-form", "NRZ", "-ampl", "-1", "3", "-nbEch", "60", "-snrpb", "10", "-mess", "0110001", "-seed", "500" };
		Simulateur sim = new Simulateur(args);
		Configurations congig = sim.getConfig();
		assertEquals(congig.getFormatSignal(), SourceAnalogiqueType.NRZ);
		assertEquals(congig.getAmplMin(), -1, 0);
		assertEquals(congig.getAmplMax(), 3, 0);
		assertEquals(congig.getNbEch(), 60);
		assertEquals(congig.getSnrpb(), 10, 0);
		assertEquals(congig.getMessageString(), "0110001");
		assertEquals(Optional.ofNullable(congig.getSeed()), Optional.ofNullable(500));
	}


	/**
	 * Test de l'argument -ti vide
	 */
	@Test()
	public void testTIVide() throws ArgumentsException {
		final String[] args = { "-ti" };
		thrown.expect(ArgumentsException.class);
		thrown.expectMessage("Pas de valeur du paramètre de trajet multiple renseignée");
		new Simulateur(args);
	}

	/**
	 * Test de l'argument -ti alpha1 vide
	 */
	@Test()
	public void testTIAlpha1Vide() throws ArgumentsException {
		final String[] args = { "-ti", "1" };
		thrown.expect(ArgumentsException.class);
		thrown.expectMessage("Pas de valeur du paramètre ti a1 renseignée");
		new Simulateur(args);
	}

	/**
	 * Test de l'argument -ti vide
	 */
	@Test()
	public void testTIAlpha2Vide() throws ArgumentsException {
		final String[] args = { "-ti", "4", "0.3", "5" };
		thrown.expect(ArgumentsException.class);
		thrown.expectMessage("Pas de valeur du paramètre ti a2 renseignée");
		new Simulateur(args);
	}
}

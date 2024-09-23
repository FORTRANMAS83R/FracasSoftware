package simulateur;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
/**
 * Classe de test pour Argument.
 * Utilise JUnit pour les assertions et ExpectedException pour gérer les exceptions attendues.
 */
public class ArgumentTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	/**
	 * Teste la gestion d'une forme invalide.
	 * @throws ArgumentsException si la forme est invalide.
	 */
	@Test(expected = ArgumentsException.class)
	public void testFormeInvalide() throws ArgumentsException {
		final String[] args = { "-form", "TEST_INVALIDE" };
		new Simulateur(args);
	}
	/**
	 * Teste la gestion d'une amplitude minimale supérieure à l'amplitude maximale.
	 * @throws ArgumentsException si l'amplitude minimale est supérieure à l'amplitude maximale.
	 */
	@Test(expected = ArgumentsException.class)
	public void testAmplitudeMinSuperieurAMax() throws ArgumentsException {
		final String[] args = { "-form", "NRZ", "-ampl", "3", "1" };
		new Simulateur(args);
	}

	/**
	 * Teste la gestion d'une amplitude minimale égale à l'amplitude maximale.
	 * @throws ArgumentsException si l'amplitude minimale est égale à l'amplitude maximale.
	 */
	@Test(expected = ArgumentsException.class)
	public void testAmplitudeMinEgaleAMax() throws ArgumentsException {
		final String[] args = { "-form", "NRZ", "-ampl", "3", "3" };

		new Simulateur(args);
	}

	/**
	 * Teste la gestion d'une amplitude minimale différente de 0 pour la forme RZ.
	 * @throws ArgumentsException si l'amplitude minimale est différente de 0 pour la forme RZ.
	 */
	@Test(expected = ArgumentsException.class)
	public void testRZAmplitudeMinDifferentDe0() throws ArgumentsException {
		final String[] args = { "-form", "RZ", "-ampl", "-3", "3" };

		new Simulateur(args);
	}
	/**
	 * Teste la gestion d'une amplitude maximale inférieure à 0 pour la forme NRZ.
	 * @throws ArgumentsException si l'amplitude maximale est inférieure à 0 pour la forme NRZ.
	 */
	@Test(expected = ArgumentsException.class)
	public void testNRZAmplitudeMaxInferieurA0() throws ArgumentsException {
		final String[] args = { "-form", "NRZ", "-ampl", "-5", "-1.2" };

		new Simulateur(args);
	}
	/**
	 * Teste la gestion d'une amplitude maximale inférieure à 0 pour la forme NRZT.
	 * @throws ArgumentsException si l'amplitude maximale est inférieure à 0 pour la forme NRZT.
	 */
	@Test(expected = ArgumentsException.class)
	public void testNRZTAmplitudeMaxInferieurA0() throws ArgumentsException {
		final String[] args = { "-form", "NRZT", "-ampl", "-5", "-1.2" };

		new Simulateur(args);
	}
	/**
	 * Teste la gestion d'une amplitude minimale supérieure à 0 pour la forme NRZ.
	 * @throws ArgumentsException si l'amplitude minimale est supérieure à 0 pour la forme NRZ.
	 */
	@Test(expected = ArgumentsException.class)
	public void testNRZAmplitudeMinSuperieurA0() throws ArgumentsException {
		final String[] args = { "-form", "NRZ", "-ampl", "0.3", "1.2" };

		new Simulateur(args);
	}

	/**
	 * Teste la gestion d'une amplitude minimale supérieure à 0 pour la forme NRZT.
	 * @throws ArgumentsException si l'amplitude minimale est supérieure à 0 pour la forme NRZT.
	 */
	@Test(expected = ArgumentsException.class)
	public void testNRZTAmplitudeMinSuperieurA0() throws ArgumentsException {
		final String[] args = { "-form", "NRZT", "-ampl", "0.3", "1.2" };

		new Simulateur(args);
	}

	/**
	 * Teste la gestion d'une amplitude minimale et maximale égale à 0 pour la forme NRZ.
	 * @throws ArgumentsException si l'amplitude minimale et maximale est égale à 0 pour la forme NRZ.
	 */
	@Test(expected = ArgumentsException.class)
	public void testNRZAmplitudeMinMaxEgaleA0() throws ArgumentsException {
		final String[] args = { "-form", "NRZ", "-ampl", "0", "0" };

		new Simulateur(args);
	}
	/**
	 * Teste la gestion d'une amplitude minimale et maximale égale à 0 pour la forme NRZT.
	 * @throws ArgumentsException si l'amplitude minimale et maximale est égale à 0 pour la forme NRZT.
	 */
	@Test(expected = ArgumentsException.class)
	public void testNRZTAmplitudeMinMaxEgaleA0() throws ArgumentsException {
		final String[] args = { "-form", "NRZT", "-ampl", "0", "0" };

		new Simulateur(args);
	}
	/**
	 * Teste la gestion d'une amplitude minimale erronée.
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
	 * @throws ArgumentsException si l'amplitude minimale est égale à 0 pour la forme NRZ.
	 */
	@Test()
	public void testNRZAmplitudeMinEgaleA0() throws ArgumentsException {
		final String[] args = { "-form", "NRZ", "-ampl", "0", "1.2" };

		new Simulateur(args);
	}
	/**
	 * Teste la gestion d'une amplitude minimale égale à 0 pour la forme NRZT.
	 * @throws ArgumentsException si l'amplitude minimale est égale à 0 pour la forme NRZT.
	 */
	@Test()
	public void testNRZTAmplitudeMinEgaleA0() throws ArgumentsException {
		final String[] args = { "-form", "NRZT", "-ampl", "0", "1.2" };

		new Simulateur(args);
	}
	/**
	 * Teste la gestion d'une amplitude maximale égale à 0 pour la forme NRZ.
	 * @throws ArgumentsException si l'amplitude maximale est égale à 0 pour la forme NRZ.
	 */
	@Test()
	public void testNRZAmplitudeMaxEgaleA0() throws ArgumentsException {
		final String[] args = { "-form", "NRZ", "-ampl", "-1.2", "0" };

		new Simulateur(args);
	}
	/**
	 * Teste la gestion d'une amplitude maximale égale à 0 pour la forme NRZT.
	 * @throws ArgumentsException si l'amplitude maximale est égale à 0 pour la forme NRZT.
	 */
	@Test()
	public void testNRZTAmplitudeMaxEgaleA0() throws ArgumentsException {
		final String[] args = { "-form", "NRZT", "-ampl", "-1.2", "0" };

		new Simulateur(args);
	}
	@Test()
	/**
	 *Test de l'argument snrpb valide
	 */
	public void testSNRPBValide() throws ArgumentsException {
		final String[] args = { "-snrpb", "10" };
		new Simulateur(args);
	}
	@Test()
	/**
	 *Test de l'argument snrpb invalide
	 */
	public void testSNRPBInvalide() throws ArgumentsException {
		final String[] args = { "-snrpb", "10@" };
		thrown.expect(ArgumentsException.class);
		thrown.expectMessage("Valeur du parametre signal a bruit invalide : 10@");
		new Simulateur(args);
	}
	@Test()
	/**
	 *Test de l'argument snrpb avec SNR négatif
	 */
	public void testSNRPBNegatif() throws ArgumentsException {
		final String[] args = { "-snrpb", "-10" };
		new Simulateur(args);
	}
	@Test()
	/**
	 *Test de l'argument snrpb avec SNR négatif et float
	 */
	public void testSNRPBNegatifFloat() throws ArgumentsException {
		final String[] args = { "-snrpb", "-10.5" };
		new Simulateur(args);
	}
	@Test()
	/**
	 *Test de l'argument snrpb avec SNR négatif et float mais invalide
	 */
	public void testSNRPBNegatifFloatInvalide() throws ArgumentsException {
		final String[] args = { "-snrpb", "-10.5@" };
		thrown.expect(ArgumentsException.class);
		thrown.expectMessage("Valeur du parametre signal a bruit invalide : -10.5@");
		new Simulateur(args);
	}
	@Test()
	/**
	 *Test de l'argument snrpb avec SNR vide
	 */
	public void testSNRPBVide() throws ArgumentsException {
		final String[] args = { "-snrpb" };
		thrown.expect(ArgumentsException.class);
		thrown.expectMessage("Pas de valeur du paramètre de signal à bruit renseignée");
		new Simulateur(args);
	}
//TODO : Ajouter des tests pour les autres arguments

}


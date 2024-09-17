package simulateur;

import org.junit.Test;

public class ArgumentTest {
	@Test(expected = ArgumentsException.class)
	public void testFormeInvalide() throws ArgumentsException {
		final String[] args = { "-form", "TEST_INVALIDE" };
		new Simulateur(args);
	}

	@Test(expected = ArgumentsException.class)
	public void testAmplitudeMinSuperieurAMax() throws ArgumentsException {
		final String[] args = { "-form", "RZ", "-ampl", "3", "1" };
		new Simulateur(args);
	}

	@Test(expected = ArgumentsException.class)
	public void testAmplitudeMinEgaleAMax() throws ArgumentsException {
		final String[] args = { "-form", "RZ", "-ampl", "3", "3" };

		System.out.println("Début du test...");
		new Simulateur(args);
		System.out.println("Fin du test...");
	}
}

package simulateur;

import org.junit.Test;

public class ArgumentTest {
	@Test(expected = ArgumentsException.class)
	public void testFormeInvalide() {
		final String[] args = { "-form", "TEST_INVALIDE" };
		Simulateur.main(args);
	}

	@Test(expected = ArgumentsException.class)
	public void testAmplitudeMinSuperieurAMax() {
		final String[] args = { "-form", "RZ", "-ampl", "3", "1" };
		Simulateur.main(args);
	}

	@Test(expected = ArgumentsException.class)
	public void testAmplitudeMinEgaleAMax() {
		final String[] args = { "-form", "RZ", "-ampl", "3", "3" };
		Simulateur.main(args);
	}
}

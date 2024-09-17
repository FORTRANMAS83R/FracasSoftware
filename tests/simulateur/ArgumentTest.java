package simulateur;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ArgumentTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test(expected = ArgumentsException.class)
	public void testFormeInvalide() throws ArgumentsException {
		final String[] args = { "-form", "TEST_INVALIDE" };
		new Simulateur(args);
	}

	@Test(expected = ArgumentsException.class)
	public void testAmplitudeMinSuperieurAMax() throws ArgumentsException {
		final String[] args = { "-form", "NRZ", "-ampl", "3", "1" };
		new Simulateur(args);
	}

	@Test(expected = ArgumentsException.class)
	public void testAmplitudeMinEgaleAMax() throws ArgumentsException {
		final String[] args = { "-form", "NRZ", "-ampl", "3", "3" };

		new Simulateur(args);
	}

	@Test(expected = ArgumentsException.class)
	public void testRZAmplitudeMinDifferentDe0() throws ArgumentsException {
		final String[] args = { "-form", "RZ", "-ampl", "-3", "3" };

		new Simulateur(args);
	}

	@Test(expected = ArgumentsException.class)
	public void testNRZAmplitudeMaxInferieurA0() throws ArgumentsException {
		final String[] args = { "-form", "NRZ", "-ampl", "-5", "-1.2" };

		new Simulateur(args);
	}

	@Test(expected = ArgumentsException.class)
	public void testNRZTAmplitudeMaxInferieurA0() throws ArgumentsException {
		final String[] args = { "-form", "NRZT", "-ampl", "-5", "-1.2" };

		new Simulateur(args);
	}

	@Test(expected = ArgumentsException.class)
	public void testNRZAmplitudeMinSuperieurA0() throws ArgumentsException {
		final String[] args = { "-form", "NRZ", "-ampl", "0.3", "1.2" };

		new Simulateur(args);
	}

	@Test(expected = ArgumentsException.class)
	public void testNRZTAmplitudeMinSuperieurA0() throws ArgumentsException {
		final String[] args = { "-form", "NRZT", "-ampl", "0.3", "1.2" };

		new Simulateur(args);
	}

	@Test(expected = ArgumentsException.class)
	public void testNRZAmplitudeMinMaxEgaleA0() throws ArgumentsException {
		final String[] args = { "-form", "NRZ", "-ampl", "0", "0" };

		new Simulateur(args);
	}

	@Test(expected = ArgumentsException.class)
	public void testNRZTAmplitudeMinMaxEgaleA0() throws ArgumentsException {
		final String[] args = { "-form", "NRZT", "-ampl", "0", "0" };

		new Simulateur(args);
	}

	@Test()
	public void testAmplitudeMinErronee() throws ArgumentsException {
		final String[] args = { "-ampl", "0@9", "0" };

		thrown.expect(ArgumentsException.class);
		thrown.expectMessage("Valeur du parametre amplitude min invalide : 0@9");

		new Simulateur(args);
	}

	@Test()
	public void testAmplitudeMaxErronee() throws ArgumentsException {
		final String[] args = { "-ampl", "0", "0@9" };

		thrown.expect(ArgumentsException.class);
		thrown.expectMessage("Valeur du parametre amplitude max invalide : 0@9");

		new Simulateur(args);
	}

	@Test()
	public void testNRZAmplitudeMinEgaleA0() throws ArgumentsException {
		final String[] args = { "-form", "NRZ", "-ampl", "0", "1.2" };

		new Simulateur(args);
	}

	@Test()
	public void testNRZTAmplitudeMinEgaleA0() throws ArgumentsException {
		final String[] args = { "-form", "NRZT", "-ampl", "0", "1.2" };

		new Simulateur(args);
	}

	@Test()
	public void testNRZAmplitudeMaxEgaleA0() throws ArgumentsException {
		final String[] args = { "-form", "NRZ", "-ampl", "-1.2", "0" };

		new Simulateur(args);
	}

	@Test()
	public void testNRZTAmplitudeMaxEgaleA0() throws ArgumentsException {
		final String[] args = { "-form", "NRZT", "-ampl", "-1.2", "0" };

		new Simulateur(args);
	}
}

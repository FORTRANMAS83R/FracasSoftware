package sources;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import sources.analogique.*;

import java.util.Arrays;
import java.util.LinkedList;

import static org.hamcrest.CoreMatchers.is;

public class SourceRZTest {
    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void testSourceRZRFixe() {
        SourceRZ src = new SourceRZ("10100110", 10, -5, 5);
        collector.checkThat(src.getInformationGeneree().nbElements(), is(10 * 8));
        for (int i = 0; i < src.getInformationGeneree().nbElements(); i++) {
            if (src.getInformationGeneree().iemeElement(i) < -5 || src.getInformationGeneree().iemeElement(i) > 5) {
                collector.addError(new AssertionError("Les valeurs générées ne sont pas dans l'intervalle d'amplitude"));
            }
        }

    }

    @Test
    public void testSourceRZRandomAvecSeed() {
        SourceRZ src = new SourceRZ(10, -5, 5, 8, 10);
        collector.checkThat(src.getInformationGeneree().nbElements(), is(10 * 8));
        for (int i = 0; i < src.getInformationGeneree().nbElements(); i++) {
            if (src.getInformationGeneree().iemeElement(i) < -5 || src.getInformationGeneree().iemeElement(i) > 5) {
                collector.addError(new AssertionError("Les valeurs générées ne sont pas dans l'intervalle d'amplitude"));
            }
        }
    }

    @Test
    public void testSourceRZRandomSansSeed() {
        SourceRZ src = new SourceRZ(10, -5, 5, 8, null);
        collector.checkThat(src.getInformationGeneree().nbElements(), is(10 * 8));
        for (int i = 0; i < src.getInformationGeneree().nbElements(); i++) {
            if (src.getInformationGeneree().iemeElement(i) < -5 || src.getInformationGeneree().iemeElement(i) > 5) {
                collector.addError(new AssertionError("Les valeurs générées ne sont pas dans l'intervalle d'amplitude"));
            }
        }
    }
    @Test
    public void testInformationGeneree() {
        SourceRZ test = new SourceRZ("010011", 10, -5, 5);
        LinkedList<Float> expected = new LinkedList<>(Arrays.asList(-5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f,
                -5.0f, -5.0f, -5.0f, -5.0f, 5.0f, 5.0f, 5.0f, -5.0f, -5.0f, -5.0f,
                -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f,
                -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f,
                -5.0f, -5.0f, -5.0f, 5.0f, 5.0f, 5.0f, -5.0f, -5.0f, -5.0f, -5.0f,
                -5.0f, -5.0f, -5.0f, 5.0f, 5.0f, 5.0f, -5.0f, -5.0f, -5.0f));
        for (int i = 0; i < test.getInformationGeneree().nbElements(); i++) {
            if (!test.getInformationGeneree().iemeElement(i).equals(expected.get(i))) {
                System.out.println(test.getInformationGeneree().iemeElement(i));
                System.out.println(expected.get(i));
                collector.addError(new AssertionError("Les valeurs générées ne sont pas correctes"));

            }
        }

    }
}
package sources;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import sources.analogique.*;
import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.*;

public class SourceNRZTest {
    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void testSourceNRZRFixe(){
       SourceNRZ src = new SourceNRZ("10100110", 10, -5, 5);
       collector.checkThat(src.getInformationGeneree().nbElements(),is(10*8));
        for (int i = 0; i < src.getInformationGeneree().nbElements(); i++) {
            if (src.getInformationGeneree().iemeElement(i) < -5 || src.getInformationGeneree().iemeElement(i) > 5) {
                collector.addError(new AssertionError("Les valeurs générées ne sont pas dans l'intervalle d'amplitude"));
            }
        }

    }

    @Test
    public void testSourceNRZRandomAvecSeed() {
        SourceNRZ src = new SourceNRZ(10, -5, 5, 8, 10);
        collector.checkThat(src.getInformationGeneree().nbElements(), is(10 * 8));
        for (int i = 0; i < src.getInformationGeneree().nbElements(); i++) {
            if (src.getInformationGeneree().iemeElement(i) < -5 || src.getInformationGeneree().iemeElement(i) > 5) {
                collector.addError(new AssertionError("Les valeurs générées ne sont pas dans l'intervalle d'amplitude"));
            }
        }
    }
    @Test
    public void testSourceNRZRandomSansSeed() {
        SourceNRZ src = new SourceNRZ(10, -5, 5, 8, null);
        collector.checkThat(src.getInformationGeneree().nbElements(), is(10 * 8));
        for (int i = 0; i < src.getInformationGeneree().nbElements(); i++) {
            if (src.getInformationGeneree().iemeElement(i) < -5 || src.getInformationGeneree().iemeElement(i) > 5) {
                collector.addError(new AssertionError("Les valeurs générées ne sont pas dans l'intervalle d'amplitude"));
            }
        }
    }
}


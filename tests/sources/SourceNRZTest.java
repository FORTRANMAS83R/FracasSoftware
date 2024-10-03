package sources;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import sources.analogique.SourceNRZ;

import static org.hamcrest.CoreMatchers.is;

/**
 * Classe de test pour SourceNRZ. Utilise JUnit pour les assertions et
 * ErrorCollector pour collecter les erreurs.
 */
public class SourceNRZTest {
    @Rule
    public ErrorCollector collector = new ErrorCollector();

    /**
     * Teste la génération de la source NRZ avec un message fixe. Vérifie que le
     * nombre d'éléments générés est correct et que les valeurs sont dans
     * l'intervalle d'amplitude.
     */

    @Test
    public void testSourceNRZRFixe() {
        SourceNRZ src = new SourceNRZ("10100110", 10, -5, 5);
        collector.checkThat(src.getInformationGeneree().nbElements(), is(10 * 8));
        for (int i = 0; i < src.getInformationGeneree().nbElements(); i++) {
            if (src.getInformationGeneree().iemeElement(i) < -5 || src.getInformationGeneree().iemeElement(i) > 5) {
                collector
                        .addError(new AssertionError("Les valeurs générées ne sont pas dans l'intervalle d'amplitude"));
            }
        }

    }

    /**
     * Teste la génération de la source NRZ avec des valeurs aléatoires et une
     * graine. Vérifie que le nombre d'éléments générés est correct et que les
     * valeurs sont dans l'intervalle d'amplitude.
     */

    @Test
    public void testSourceNRZRandomAvecSeed() {
        SourceNRZ src = new SourceNRZ(10, -5, 5, 8, 10);
        collector.checkThat(src.getInformationGeneree().nbElements(), is(10 * 8));
        for (int i = 0; i < src.getInformationGeneree().nbElements(); i++) {
            if (src.getInformationGeneree().iemeElement(i) < -5 || src.getInformationGeneree().iemeElement(i) > 5) {
                collector
                        .addError(new AssertionError("Les valeurs générées ne sont pas dans l'intervalle d'amplitude"));
            }
        }
    }

    /**
     * Teste la génération de la source NRZ avec des valeurs aléatoires sans graine.
     * Vérifie que le nombre d'éléments générés est correct et que les valeurs sont
     * dans l'intervalle d'amplitude.
     */
    @Test
    public void testSourceNRZRandomSansSeed() {
        SourceNRZ src = new SourceNRZ(10, -5, 5, 8, null);
        collector.checkThat(src.getInformationGeneree().nbElements(), is(10 * 8));
        for (int i = 0; i < src.getInformationGeneree().nbElements(); i++) {
            if (src.getInformationGeneree().iemeElement(i) < -5 || src.getInformationGeneree().iemeElement(i) > 5) {
                collector
                        .addError(new AssertionError("Les valeurs générées ne sont pas dans l'intervalle d'amplitude"));
            }
        }
    }

    /**
     * Verification de la véracité des informations générées en comparant avec les
     * valeurs attendues.
     */
    @Test
    public void testInformationGeneree() {
        SourceNRZ test = new SourceNRZ("010011", 10, -5, 5);
        Float[] expected = {-5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, 5.0f, 5.0f, 5.0f, 5.0f,
                5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f,
                -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, -5.0f, 5.0f, 5.0f, 5.0f, 5.0f,
                5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f};
        for (int i = 0; i < test.getInformationGeneree().nbElements(); i++) {
            if (!test.getInformationGeneree().iemeElement(i).equals(expected[i])) {
                collector.addError(new AssertionError("Erreur de génération de la source NRZ"));
            }
        }
    }
}

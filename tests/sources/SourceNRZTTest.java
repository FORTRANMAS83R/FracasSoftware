package sources;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import sources.analogique.SourceNRZT;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.fail;

/**
 * Classe de test pour SourceNRZT. Utilise JUnit pour les assertions et
 * ErrorCollector pour collecter les erreurs.
 */
public class SourceNRZTTest {
    @Rule
    public ErrorCollector collector = new ErrorCollector();

    /**
     * Teste la génération de la source NRZT avec un message fixe. Vérifie que le
     * nombre d'éléments générés est correct et que les valeurs sont dans
     * l'intervalle d'amplitude.
     */
    @Test
    public void testNRZTFixe() {
        SourceNRZT src = new SourceNRZT("10100110", 10, -5, 5);

        collector.checkThat(src.getInformationGeneree().nbElements(), is(10 * 8));
        for (int i = 0; i < src.getInformationGeneree().nbElements(); i++) {
            if (src.getInformationGeneree().iemeElement(i) < -5 || src.getInformationGeneree().iemeElement(i) > 5) {
                collector
                        .addError(new AssertionError("Les valeurs générées ne sont pas dans l'intervalle d'amplitude"));
            }
        }
    }

    @Test
    public void testNRZTnbEch() {
        SourceNRZT src = new SourceNRZT("1", 1, -5, 5);
        collector.checkThat(src.getInformationGeneree().nbElements(), is(1 * 1));
        for (int i = 0; i < src.getInformationGeneree().nbElements(); i++) {
            if (src.getInformationGeneree().iemeElement(i) < -5 || src.getInformationGeneree().iemeElement(i) > 5) {
            collector
                    .addError(new AssertionError("Les valeurs générées ne sont pas dans l'intervalle d'amplitude"));
        }
    }
    }
    /**
     * Teste la génération de la source NRZT avec des valeurs aléatoires et une
     * graine. Vérifie que le nombre d'éléments générés est correct et que les
     * valeurs sont dans l'intervalle d'amplitude.
     */
    @Test
    public void testNRZTRandomAvecSeed() {
        SourceNRZT src = new SourceNRZT(10, -5, 5, 8, 10);
        collector.checkThat(src.getInformationGeneree().nbElements(), is(10 * 8));
        for (int i = 0; i < src.getInformationGeneree().nbElements(); i++) {
            if (src.getInformationGeneree().iemeElement(i) < -5 || src.getInformationGeneree().iemeElement(i) > 5) {
                collector
                        .addError(new AssertionError("Les valeurs générées ne sont pas dans l'intervalle d'amplitude"));
            }
        }
    }

    /**
     * Teste la génération de la source NRZT avec des valeurs aléatoires sans
     * graine. Vérifie que le nombre d'éléments générés est correct et que les
     * valeurs sont dans l'intervalle d'amplitude.
     */
    @Test
    public void testNRZTRandomSansSeed() {
        SourceNRZT src = new SourceNRZT(10, -5, 5, 8, null);
        collector.checkThat(src.getInformationGeneree().nbElements(), is(10 * 8));
        for (int i = 0; i < src.getInformationGeneree().nbElements(); i++) {
            if (src.getInformationGeneree().iemeElement(i) < -5 || src.getInformationGeneree().iemeElement(i) > 5) {
                collector
                        .addError(new AssertionError("Les valeurs générées ne sont pas dans l'intervalle d'amplitude"));
            }
        }
    }

    /**
     * Vérifie la véracité des informations générées en comparant avec les valeurs
     * attendues. Actuellement, ce test n'est pas implémenté et génère une erreur.
     */
    @Test
    public void testInformationGeneree() {
        SourceNRZT test = new SourceNRZT("010011", 10, -2.5f, 5);

        LinkedList<Float> expected = new LinkedList<>(Arrays.asList(-0.0f, -0.75f, -1.5f, -2.5f, -2.5f, -2.5f, -2.5f,
                -1.75f, -1.0f, -2.5f, 0.0f, 1.5f, 3.0f, 5.0f, 5.0f, 5.0f, 5.0f, 3.5f, 2.0f, 5.0f, -0.0f, -0.75f, -1.5f,
                -2.5f, -2.5f, -2.5f, -2.5f, -2.5f, -2.5f, -2.5f, -2.5f, -2.5f, -2.5f, -2.5f, -2.5f, -2.5f, -2.5f,
                -1.75f, -1.0f, -2.5f, 0.0f, 1.5f, 3.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f,
                5.0f, 5.0f, 5.0f, 5.0f, 2.8571427f, 0.7142854f, 5.0f));
        for (int i = 0; i < test.getInformationGeneree().nbElements(); i++) {
            if (!Objects.equals(test.getInformationGeneree().iemeElement(i), expected.get(i))) {
                fail("Erreur de génération de la source NRZT");
            }
        }
    }
}

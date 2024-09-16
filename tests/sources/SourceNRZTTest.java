package sources;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import sources.analogique.*;

import java.util.Arrays;
import java.util.LinkedList;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.fail;
/**
 * Classe de test pour SourceNRZT.
 * Utilise JUnit pour les assertions et ErrorCollector pour collecter les erreurs.
 */
public class SourceNRZTTest {
    @Rule
    public ErrorCollector collector = new ErrorCollector();
    /**
     * Teste la génération de la source NRZT avec un message fixe.
     * Vérifie que le nombre d'éléments générés est correct et que les valeurs sont dans l'intervalle d'amplitude.
     */
    @Test
    public void testNRZTFixe(){
        SourceNRZT src = new SourceNRZT("10100110", 10, -5, 5);
        collector.checkThat(src.getInformationGeneree().nbElements(),is(10*8));
        for (int i = 0; i < src.getInformationGeneree().nbElements(); i++) {
            if (src.getInformationGeneree().iemeElement(i) < -5 || src.getInformationGeneree().iemeElement(i) > 5) {
                collector.addError(new AssertionError("Les valeurs générées ne sont pas dans l'intervalle d'amplitude"));
            }
        }
    }

    /**
     * Teste la génération de la source NRZT avec des valeurs aléatoires et une graine.
     * Vérifie que le nombre d'éléments générés est correct et que les valeurs sont dans l'intervalle d'amplitude.
     */
    @Test
    public void testNRZTRandomAvecSeed() {
        SourceNRZT src = new SourceNRZT(10, -5, 5, 8, 10);
        collector.checkThat(src.getInformationGeneree().nbElements(), is(10 * 8));
        for (int i = 0; i < src.getInformationGeneree().nbElements(); i++) {
            if (src.getInformationGeneree().iemeElement(i) < -5 || src.getInformationGeneree().iemeElement(i) > 5) {
                collector.addError(new AssertionError("Les valeurs générées ne sont pas dans l'intervalle d'amplitude"));
            }
        }
    }
    /**
     * Teste la génération de la source NRZT avec des valeurs aléatoires sans graine.
     * Vérifie que le nombre d'éléments générés est correct et que les valeurs sont dans l'intervalle d'amplitude.
     */
    @Test
    public void testNRZTRandomSansSeed() {
        SourceNRZT src = new SourceNRZT(10, -5, 5, 8, null);
        collector.checkThat(src.getInformationGeneree().nbElements(), is(10 * 8));
        for (int i = 0; i < src.getInformationGeneree().nbElements(); i++) {
            if (src.getInformationGeneree().iemeElement(i) < -5 || src.getInformationGeneree().iemeElement(i) > 5) {
                collector.addError(new AssertionError("Les valeurs générées ne sont pas dans l'intervalle d'amplitude"));
            }
        }
    }
    /**
     * Vérifie la véracité des informations générées en comparant avec les valeurs attendues.
     * Actuellement, ce test n'est pas implémenté et génère une erreur.
     */
    @Test
    public void testInformationGeneree() {
        SourceNRZT test = new SourceNRZT("010011", 10, -5, 5);
        /*
        LinkedList<Float> expected = new LinkedList<>(Arrays.asList("" Les valeurs attendues ""));
        for (int i = 0; i < test.getInformationGeneree().nbElements(); i++) {
            if (test.getInformationGeneree().iemeElement(i) != expected.get(i)) {
                fail("Erreur de génération de la source NRZT");
            }
        }
        */
        collector.addError(new Error("Test non implemente"));
    }
}

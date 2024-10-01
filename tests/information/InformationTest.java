package information;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

/**
 * La classe InformationTest contient des tests unitaires pour la classe
 * Information.
 * Elle utilise JUnit pour vérifier la correction de diverses méthodes de la
 * classe Information.
 */
public class InformationTest {
    /**
     * ErrorCollector est une règle JUnit qui permet l'exécution d'un test de
     * continuer après la première erreur trouvée.
     * Cela est nécessaire pour collecter tous les problèmes dans une méthode de
     * test et les rapporter tous en une seule fois.
     */
    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void testCreationInformationVide() {
        final Information<Boolean> i = new Information<Boolean>();
        collector.checkThat(i.nbElements(), is(0));
    }

    /**
     * Test de la création d'une information non vide
     */
    @Test
    public void testCreationInformationNonVide() {
        final Information<Boolean> i = new Information<Boolean>(new Boolean[] { true, false, true });
        collector.checkThat(i.nbElements(), is(3));
        collector.checkThat(i.iemeElement(0), is(true));
        collector.checkThat(i.iemeElement(1), is(false));
        collector.checkThat(i.iemeElement(2), is(true));
    }

    /**
     * Test de la création d'une information non vide
     */
    @Test
    public void testCreationInformationNonVide2() {
        final Information<Boolean> i = new Information<Boolean>(new Boolean[] { true, false, true, false, true }, 5);
        collector.checkThat(i.nbElements(), is(5));
        collector.checkThat(i.iemeElement(0), is(true));
        collector.checkThat(i.iemeElement(1), is(false));
        collector.checkThat(i.iemeElement(2), is(true));
        collector.checkThat(i.iemeElement(3), is(false));
        collector.checkThat(i.iemeElement(4), is(true));
        collector.checkThat(i.getNbEchantillons(), is(5));
    }

    /**
     * Vérification du getter
     */
    @Test
    public void testGetContent() {
        final Information<Boolean> i = new Information<Boolean>(new Boolean[] { true, false, true, false, true }, 5);
        collector.checkThat(i.getContent().size(), is(5));
        collector.checkThat(i.getContent().get(0), is(true));
        collector.checkThat(i.getContent().get(1), is(false));
        collector.checkThat(i.getContent().get(2), is(true));
        collector.checkThat(i.getContent().get(3), is(false));
        collector.checkThat(i.getContent().get(4), is(true));
    }

    /**
     * Vérification de la méthode sousInformation
     */
    @Test
    public void testSousInformation() {
        final Boolean[] values = { true, false, true, true };
        final Information<Boolean> i = new Information<Boolean>(values);
        final Information<Boolean> subI = i.sousInformation(1, 2);

        // Test i
        assertEquals(4, i.nbElements());
        assertEquals(true, i.iemeElement(0));
        assertEquals(false, i.iemeElement(1));
        assertEquals(true, i.iemeElement(2));
        assertEquals(true, i.iemeElement(3));

        // Test subI
        assertEquals(2, subI.nbElements(), 2);
        assertEquals(false, subI.iemeElement(0));
        assertEquals(true, subI.iemeElement(1));
    }

    /**
     * Vérification du setter de nbEchantillons
     */
    @Test
    public void testSetNbEchantillons() {
        final Information<Boolean> i = new Information<Boolean>(new Boolean[] { true, false, true, false, true });
        i.setNbEchantillons(5);
        collector.checkThat(i.getNbEchantillons(), is(5));
    }

    /**
     * Vérification du setter de iemeElement
     */
    @Test
    public void testSetIemeElement() {
        final Information<Boolean> i = new Information<Boolean>(new Boolean[] { true, false, true, false, true });
        i.setIemeElement(0, false);
        collector.checkThat(i.iemeElement(0), is(false));
    }

    /**
     * Vérification de la méthode Equals
     */
    @Test
    public void testEquals() {
        final Information<Boolean> i = new Information<Boolean>(new Boolean[] { true, false, true, false, true });
        final Information<Boolean> i2 = new Information<Boolean>(new Boolean[] { true, false, true, false, true });
        final Information<Boolean> i3 = new Information<Boolean>(new Boolean[] { true, false, true, false });
        final Information<Boolean> i4 = new Information<Boolean>(new Boolean[] { true, false, true, false, false });
        collector.checkThat(i.equals(i2), is(true));
        collector.checkThat(i.equals(2), is(false));
        collector.checkThat(i.equals(i3), is(false));
        collector.checkThat(i.equals(i4), is(false));
    }

    /**
     * Vérification de la méthode clone
     */
    @Test
    public void testClone() {
        final Information<Boolean> i = new Information<Boolean>(new Boolean[] { true, false, true, false, true });
        final Information<Boolean> i2 = i.clone();
        collector.checkThat(i.equals(i2), is(true));
    }

    /**
     * Vérification de la méthode iterator
     */
    @Test
    public void testIterator() {
        final Information<Boolean> i = new Information<Boolean>(new Boolean[] { true, false, true, false, true });
        int j = 0;
        for (Boolean b : i) {
            collector.checkThat(b, is(i.iemeElement(j)));
            j++;
        }
    }

}

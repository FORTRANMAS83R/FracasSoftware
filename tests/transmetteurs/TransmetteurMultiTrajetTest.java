package transmetteurs;

import bruit.BBG;
import destinations.DestinationFinale;
import information.Information;
import information.InformationNonConformeException;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Classe de test pour TransmetteurMultiTrajet.
 */
public class TransmetteurMultiTrajetTest {
    /**
     * Test du constructeur avec des trajets nuls et un bruit nul.
     */
    @Test
    public void testConstructeurNullTrajetNullBruit() {
        TransmetteurMultiTrajet transmetteurMultiTrajet = new TransmetteurMultiTrajet(null, null, null);
        assertNull(transmetteurMultiTrajet.getTrajets());
        assertNull(transmetteurMultiTrajet.getSNRpb());
        assertNull(transmetteurMultiTrajet.getSeed());
    }

    /**
     * Test du constructeur avec des trajets nuls et sans bruit.
     */
    @Test
    public void testConstructeurNullTrajetSansBruit() {
        TransmetteurMultiTrajet transmetteurMultiTrajet = new TransmetteurMultiTrajet(null, 0.0f, null);
        assertNull(transmetteurMultiTrajet.getTrajets());
        assertEquals(transmetteurMultiTrajet.getSNRpb(), (Float) 0.0f, 0.0f);
        assertNull(transmetteurMultiTrajet.getSeed());
    }

    /**
     * Test du constructeur avec des trajets nuls et un bruit.
     */
    @Test
    public void testConstructeurNullTrajetEtBruit() {
        TransmetteurMultiTrajet transmetteurMultiTrajet = new TransmetteurMultiTrajet(null, 1.0f, null);
        assertNull(transmetteurMultiTrajet.getTrajets());
        assertEquals(transmetteurMultiTrajet.getSNRpb(), (Float) 1.0f, 0.0f);
        assertNull(transmetteurMultiTrajet.getSeed());
    }

    /**
     * Test du constructeur avec des trajets et un bruit.
     */
    @Test
    public void testConstructeurAvecTrajetEtBruit() {
        List<AbstractMap.SimpleEntry<Integer, Float>> trajets = new ArrayList<>();
        trajets.add(new AbstractMap.SimpleEntry<>(1, 0.10f));
        trajets.add(new AbstractMap.SimpleEntry<>(2, 0.8f));
        TransmetteurMultiTrajet transmetteurMultiTrajet = new TransmetteurMultiTrajet(trajets, 2.0f, null);
        assertEquals(transmetteurMultiTrajet.getTrajets(), trajets);
        assertEquals(transmetteurMultiTrajet.getSNRpb(), 2.0f, 0.0f);
        assertNull(transmetteurMultiTrajet.getSeed());
    }

    /**
     * Test du constructeur avec des trajets sans bruit.
     */
    @Test
    public void testConstructeurAvecTrajetSansBruit() {
        List<AbstractMap.SimpleEntry<Integer, Float>> trajets = new ArrayList<>();
        trajets.add(new AbstractMap.SimpleEntry<>(1, 0.2f));
        trajets.add(new AbstractMap.SimpleEntry<>(2, 0.8f));
        TransmetteurMultiTrajet transmetteurMultiTrajet = new TransmetteurMultiTrajet(trajets, null, null);
        assertEquals(transmetteurMultiTrajet.getTrajets(), trajets);
        assertNull(transmetteurMultiTrajet.getSNRpb());
        assertNull(transmetteurMultiTrajet.getSeed());
    }

    /**
     * Test de la méthode emettre.
     */
    @Test
    public void emettreTest() throws InformationNonConformeException {
        List<AbstractMap.SimpleEntry<Integer, Float>> trajets = new ArrayList<>();
        trajets.add(new AbstractMap.SimpleEntry<>(1, 0.1f));
        trajets.add(new AbstractMap.SimpleEntry<>(2, 0.7f));
        Information<Float> infoTested = new Information<>(
                new Float[] { 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f });
        Information<Float> infoExpected = new Information<>(
                new Float[] { 0.70710677f, 0.1f, 1.4071068f, 0.1f, 1.4071068f, 0.1f, 1.4071068f, 0.1f, 0.7f, 0.0f });
        constructAndCheck(trajets, infoTested, infoExpected);
    }

    /**
     * Test de la méthode emettre avec des trajets non réguliers.
     */
    @Test
    public void emettreNonRegTest() throws InformationNonConformeException {
        List<AbstractMap.SimpleEntry<Integer, Float>> trajets = new ArrayList<>();
        trajets.add(new AbstractMap.SimpleEntry<>(0, 0.0f));
        trajets.add(new AbstractMap.SimpleEntry<>(0, 0.0f));
        Information<Float> infoTested = new Information<>(
                new Float[] { 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f });
        Information<Float> infoExpected = infoTested.clone();
        constructAndCheck(trajets, infoTested, infoExpected);
    }

    private void constructAndCheck(List<AbstractMap.SimpleEntry<Integer, Float>> trajets, Information<Float> infoTested,
            Information<Float> infoExpected) throws InformationNonConformeException {
        DestinationFinale<Float> destinationFinale = new DestinationFinale<>();
        TransmetteurMultiTrajet transmetteurMultiTrajet = new TransmetteurMultiTrajet(trajets, null, null);
        transmetteurMultiTrajet.connecter(destinationFinale);
        transmetteurMultiTrajet.recevoir(infoTested);
        assertEquals(destinationFinale.getInformationRecue(), infoExpected);
    }

    /**
     * Test de la méthode tauMax.
     */
    @Test
    public void tauMaxTest() {
        List<AbstractMap.SimpleEntry<Integer, Float>> trajets = new ArrayList<>();
        trajets.add(new AbstractMap.SimpleEntry<>(1, 0.01f));
        trajets.add(new AbstractMap.SimpleEntry<>(5, 0.4f));
        trajets.add(new AbstractMap.SimpleEntry<>(2, 0.4f));
        trajets.add(new AbstractMap.SimpleEntry<>(9, 0.01f));
        trajets.add(new AbstractMap.SimpleEntry<>(0, 0.02f));
        TransmetteurMultiTrajet transmetteurMultiTrajet = new TransmetteurMultiTrajet(trajets, null, null);
        assertEquals(Optional.ofNullable(transmetteurMultiTrajet.tauMax()), Optional.of(9));
    }

    /**
     * Test de la méthode nonCreationEnergie.
     */
    @Test
    public void nonCreationEnergieTest() {
        List<AbstractMap.SimpleEntry<Integer, Float>> trajets = new ArrayList<>();
        trajets.add(new AbstractMap.SimpleEntry<>(1, 0.1f));
        trajets.add(new AbstractMap.SimpleEntry<>(5, 0.1f));
        trajets.add(new AbstractMap.SimpleEntry<>(2, 0.1f));
        trajets.add(new AbstractMap.SimpleEntry<>(9, 0.1f));
        trajets.add(new AbstractMap.SimpleEntry<>(20, 0.10f));
        TransmetteurMultiTrajet transmetteurMultiTrajet = new TransmetteurMultiTrajet(trajets, null, null);
        Information<Float> infoTested = new Information<>(
                new Float[] { 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f });
        assertTrue(BBG.calculPuissanceSignal(transmetteurMultiTrajet.multiTrajet(infoTested)) <= BBG
                .calculPuissanceSignal(infoTested));

    }
}

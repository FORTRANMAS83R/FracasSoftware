package transmetteurs;

import bruit.BBG;
import destinations.DestinationFinale;
import information.Information;
import information.InformationNonConformeException;
import org.junit.Test;
import java.util.*;

import static org.junit.Assert.*;

public class TransmetteurMultiTrajetTest {


    //TO DO: Test de non création d'énergie


    @Test
    public void testConstructeurNullTrajetNullBruit() {
        TransmetteurMultiTrajet transmetteurMultiTrajet = new TransmetteurMultiTrajet(null, null);
        assertNull(transmetteurMultiTrajet.getTrajets());
        assertNull(transmetteurMultiTrajet.getSNRpb());
    }

    @Test
    public void testConstructeurNullTrajetSansBruit() {
        TransmetteurMultiTrajet transmetteurMultiTrajet = new TransmetteurMultiTrajet(null, 0.0f);
        assertNull(transmetteurMultiTrajet.getTrajets());
        assertEquals(transmetteurMultiTrajet.getSNRpb(), 0.0f, 0.0f);
    }

    @Test
    public void testConstructeurNullTrajetEtBruit() {
        TransmetteurMultiTrajet transmetteurMultiTrajet = new TransmetteurMultiTrajet(null, 1.0f);
        assertNull(transmetteurMultiTrajet.getTrajets());
        assertEquals(transmetteurMultiTrajet.getSNRpb(), 1.0f, 0.0f);
    }

    @Test
    public void testConstructeurAvecTrajetEtBruit() {
        List<AbstractMap.SimpleEntry<Integer, Float>> trajets = new ArrayList<>();
        trajets.add(new AbstractMap.SimpleEntry<>(1, 0.10f));
        trajets.add(new AbstractMap.SimpleEntry<>(2, 0.8f));
        TransmetteurMultiTrajet transmetteurMultiTrajet = new TransmetteurMultiTrajet(trajets, 2.0f);
        assertEquals(transmetteurMultiTrajet.getTrajets(), trajets);
        assertEquals(transmetteurMultiTrajet.getSNRpb(), 2.0f, 0.0f);
    }

    @Test
    public void testConstructeurAvecTrajetSansBruit() {
        List<AbstractMap.SimpleEntry<Integer, Float>> trajets = new ArrayList<>();
        trajets.add(new AbstractMap.SimpleEntry<>(1, 0.2f));
        trajets.add(new AbstractMap.SimpleEntry<>(2, 0.8f));
        TransmetteurMultiTrajet transmetteurMultiTrajet = new TransmetteurMultiTrajet(trajets);
        assertEquals(transmetteurMultiTrajet.getTrajets(), trajets);
    }

    @Test
    public void emettreTest() throws InformationNonConformeException {
        List<AbstractMap.SimpleEntry<Integer, Float>> trajets = new ArrayList<>();
        trajets.add(new AbstractMap.SimpleEntry<>(1, 0.1f));
        trajets.add(new AbstractMap.SimpleEntry<>(2, 0.7f));
        Information<Float> infoTested = new Information<>(new Float[]{1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f});
        Information<Float> infoExpected = new Information<>(new Float[]{0.70710677f, 0.1f, 1.4071068f, 0.1f, 1.4071068f, 0.1f, 1.4071068f, 0.1f, 0.7f, 0.0f});
        DestinationFinale destinationFinale = new DestinationFinale();
        TransmetteurMultiTrajet transmetteurMultiTrajet = new TransmetteurMultiTrajet(trajets);
        transmetteurMultiTrajet.connecter(destinationFinale);
        transmetteurMultiTrajet.recevoir(infoTested);
        assertEquals(destinationFinale.getInformationRecue(), infoExpected);
    }

    @Test
    public void emettreNonRegTest() throws InformationNonConformeException {
        List<AbstractMap.SimpleEntry<Integer, Float>> trajets = new ArrayList<>();
        trajets.add(new AbstractMap.SimpleEntry<>(0, 0.0f));
        trajets.add(new AbstractMap.SimpleEntry<>(0, 0.0f));
        Information<Float> infoTested = new Information<>(new Float[]{1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f});
        Information<Float> infoExpected = infoTested.clone();
        DestinationFinale destinationFinale = new DestinationFinale();
        TransmetteurMultiTrajet transmetteurMultiTrajet = new TransmetteurMultiTrajet(trajets);
        transmetteurMultiTrajet.connecter(destinationFinale);
        transmetteurMultiTrajet.recevoir(infoTested);
        assertEquals(destinationFinale.getInformationRecue(),infoExpected);
    }

    @Test
    public void tauMaxTest() {
        List<AbstractMap.SimpleEntry<Integer, Float>> trajets = new ArrayList<>();
        trajets.add(new AbstractMap.SimpleEntry<>(1, 0.01f));
        trajets.add(new AbstractMap.SimpleEntry<>(5, 0.4f));
        trajets.add(new AbstractMap.SimpleEntry<>(2, 0.4f));
        trajets.add(new AbstractMap.SimpleEntry<>(9, 0.01f));
        trajets.add(new AbstractMap.SimpleEntry<>(0, 0.02f));
        TransmetteurMultiTrajet transmetteurMultiTrajet = new TransmetteurMultiTrajet(trajets);
        assertEquals(Optional.ofNullable(transmetteurMultiTrajet.tauMax()), Optional.ofNullable(9));
    }

    @Test
    public void nonCreationEnergieTest(){
        List<AbstractMap.SimpleEntry<Integer, Float>> trajets = new ArrayList<>();
        trajets.add(new AbstractMap.SimpleEntry<>(1, 0.1f));
        trajets.add(new AbstractMap.SimpleEntry<>(5, 0.1f));
        trajets.add(new AbstractMap.SimpleEntry<>(2, 0.1f));
        trajets.add(new AbstractMap.SimpleEntry<>(9, 0.1f));
        trajets.add(new AbstractMap.SimpleEntry<>(20, 0.10f));
        TransmetteurMultiTrajet transmetteurMultiTrajet = new TransmetteurMultiTrajet(trajets);
        Information<Float> infoTested = new Information<>(new Float[]{1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f});
        assertTrue(BBG.calculPuissanceSignal(transmetteurMultiTrajet.multiTrajet(infoTested))<=BBG.calculPuissanceSignal(infoTested));


    }
}



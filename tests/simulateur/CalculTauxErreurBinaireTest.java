package simulateur;

import information.Information;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CalculTauxErreurBinaireTest {
    int nbEch, nbBitsMessage;
    private Simulateur simulateur;
    private Information<Float> sourceInfo;
    private Information<Float> destinationInfo;

    @Before
    public void setUp() throws ArgumentsException {
        simulateur = new Simulateur(new String[]{"-mess", "10101100", "-form", "NRZ"});
        sourceInfo = new Information<>();
        destinationInfo = new Information<>();

        nbEch = 30;
        nbBitsMessage = 10;
    }

    @Test
    public void testAucunErreur() throws Exception {
        for (int i = 0; i < nbEch * nbBitsMessage; i++) {
            sourceInfo.add(1.0f);
            destinationInfo.add(1.0f);
        }
        simulateur.execute();
        simulateur.getSource().setInformationEmise(sourceInfo);
        simulateur.getDestination().setInformationRecue(destinationInfo);

        float TEB = simulateur.calculTauxErreurBinaire();
        assertEquals(0.0f, TEB, 0.01f);
    }

    @Test
    public void testAvecErreurs() throws Exception {
        for (int i = 0; i < nbEch * nbBitsMessage; i++) {
            sourceInfo.add(1.0f);
            if (i%4 == 0) {
                destinationInfo.add(0.0f);
            } else {
                destinationInfo.add(1.0f);
            }
        }

        simulateur.execute();
        simulateur.getSource().setInformationEmise(sourceInfo);
        simulateur.getDestination().setInformationRecue(destinationInfo);

        float TEB = simulateur.calculTauxErreurBinaire();
        assertTrue(TEB > 0.0f);
    }

    @Test
    public void testErreursComplete() throws Exception {
        for (int i = 0; i < nbEch * nbBitsMessage; i++) {
            sourceInfo.add(1.0f);
            destinationInfo.add(0.0f);
        }

        simulateur.execute();
        simulateur.getSource().setInformationEmise(sourceInfo);
        simulateur.getDestination().setInformationRecue(destinationInfo);

        float TEB = simulateur.calculTauxErreurBinaire();
        assertEquals(1.0f, TEB, 0.01f);
    }
}

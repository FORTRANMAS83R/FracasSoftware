package simulateur;

import information.Information;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Teste la génération de bruit par la classe BBG.
 */
public class CalculTauxErreurBinaireTest {
    int nbEch, nbBitsMessage;
    private Simulateur simulateur;
    private Information<Float> sourceInfo;
    private Information<Float> destinationInfo;

    /**
     * Configuration initiale avant chaque test.
     *
     * @throws ArgumentsException Si les arguments du simulateur sont incorrects.
     */
    @Before
    public void setUp() throws ArgumentsException {
        simulateur = new Simulateur(new String[]{"-mess", "10101100", "-form", "NRZ", "-nbEch", "3"});
        sourceInfo = new Information<>();
        destinationInfo = new Information<>();

        nbEch = 30;
        nbBitsMessage = 10;
    }

    /**
     * Teste le calcul du TEB lorsque aucune erreur n'est présente.
     *
     * @throws Exception Si une erreur se produit lors de l'exécution du simulateur.
     */
    @Test
    public void testAucuneErreur() throws Exception {
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

    /**
     * Teste le calcul du TEB lorsque des erreurs sont présentes.
     *
     * @throws Exception Si une erreur se produit lors de l'exécution du simulateur.
     */
    @Test
    public void testAvecErreurs() throws Exception {
        for (int i = 0; i < nbEch * nbBitsMessage; i++) {
            sourceInfo.add(1.0f);
            if (i % 2 == 0) {
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

    /**
     * Teste le calcul du TEB lorsque toutes les valeurs sont erronées.
     *
     * @throws Exception Si une erreur se produit lors de l'exécution du simulateur.
     */
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

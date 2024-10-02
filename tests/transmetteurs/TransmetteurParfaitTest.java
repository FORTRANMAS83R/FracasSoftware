package transmetteurs;

import destinations.DestinationFinale;
import information.Information;
import information.InformationNonConformeException;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import static org.easymock.EasyMock.*;
/**
 * Classe de test pour TransmetteurParfait.
 */
public class TransmetteurParfaitTest {
    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Mock
    public DestinationFinale<Boolean> mockDestinationFinale;

    TransmetteurParfait<Boolean> transmetteurParfait;
    Information<Boolean> info;

    /**
     * Configuration initiale avant chaque test.
     * @throws InformationNonConformeException si l'information est non conforme.
     */
    @Before
    public void setUp() throws InformationNonConformeException {
        transmetteurParfait = new TransmetteurParfait<>();
        info = new Information<>(new Boolean[]{true, false, true, false, true, false, true, false});
        mockDestinationFinale = createMock(DestinationFinale.class);
        mockDestinationFinale.recevoir(info);
        expectLastCall();
        expect(mockDestinationFinale.getInformationRecue()).andReturn(info);
        replay(mockDestinationFinale);
    }

    /**
     * Test de la méthode recevoir.
     * @throws InformationNonConformeException si l'information est non conforme.
     */
    @Test
    public void recevoirTest() throws InformationNonConformeException {
        transmetteurParfait.recevoir(info);
        if (!transmetteurParfait.getInformationRecue().equals(info)) {
            collector.addError(new AssertionError("Information reçue non identique à l'information envoyée"));
        }
    }

    /**
     * Test de la méthode connecter.
     */
    @Test
    public void connecterTest() {
        int connectionListLength = transmetteurParfait.getDestinationsConnectees().size();
        transmetteurParfait.connecter(mockDestinationFinale);
        if (transmetteurParfait.getDestinationsConnectees().size() != connectionListLength + 1) {
            collector.addError(new AssertionError("Destination non connectée"));
        }
    }

    /**
     * Test de la méthode emettre.
     * @throws InformationNonConformeException si l'information est non conforme.
     */
    @Test
    public void emettreTest() throws InformationNonConformeException {
        transmetteurParfait.recevoir(info);
        transmetteurParfait.connecter(mockDestinationFinale);
        transmetteurParfait.emettre();
        if (!transmetteurParfait.getInformationEmise().equals(mockDestinationFinale.getInformationRecue())) {
            collector.addError(new AssertionError("Information émise non identique à l'information reçue"));
        }
    }

}
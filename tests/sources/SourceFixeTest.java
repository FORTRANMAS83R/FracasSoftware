package sources;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;

import org.easymock.EasyMock;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import destinations.DestinationFinale;
import information.Information;
import information.InformationNonConformeException;

public class SourceFixeTest {
    private DestinationFinale<Boolean> mockDestinationFinale = EasyMock.createMock(DestinationFinale.class);

    Information<Boolean> info;

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    /**
     * Test de la classe SourceAleatoire Rq: On ne peut tester que la génération de
     * la source aléatoire avec un seed donné.
     */

    @Test
    public void testConstructorSourceFixe() {
        String message = "0101010101";
        Source<Boolean> src = new SourceFixe(message);
        Boolean current;
        for (int i = 0; i < 10; i++) {
            if (message.charAt(i) == '0')
                current = false;
            else if (message.charAt(i) == '1')
                current = true;
            else {
                collector.addError(new AssertionError("Erreur de génération de la source fixe"));
                return;
            }
            if (src.getInformationGeneree().iemeElement(i) != current) {
                collector.addError(new AssertionError("Erreur de génération de la source fixe"));
            }
        }
    }

    @Test
    public void testConnecterSourceFixe() {
        String message = "0101010101";
        Source<Boolean> src = new SourceFixe(message);
        src.connecter(this.mockDestinationFinale);
        if (!src.getDestinationsConnectees().contains(this.mockDestinationFinale)) {
            collector.addError(new AssertionError("Erreur de connexion de la source fixe"));
        }
    }

    @Test
    public void emettreTest() throws InformationNonConformeException {
        info = new Information<>(new Boolean[] { true, false, true, false, true, false, true, false });
        String message = "10101010";
        Source<Boolean> src = new SourceFixe(message);
        mockDestinationFinale = EasyMock.createMock(DestinationFinale.class);
        mockDestinationFinale.recevoir(info);
        expectLastCall();
        expect(mockDestinationFinale.getInformationRecue()).andReturn(info);
        replay(mockDestinationFinale);
        src.connecter(mockDestinationFinale);
        src.emettre();
        if (!src.getInformationEmise().equals(mockDestinationFinale.getInformationRecue())) {
            collector.addError(new AssertionError("Information émise non identique à l'information reçue"));
        }
    }
}
package destinations;

import information.Information;
import information.InformationNonConformeException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

public class DestinationFinaleTest {
    @Rule
    public ErrorCollector collector = new ErrorCollector();

    Information<Boolean> info;
    private DestinationFinale<Boolean> destinationFinale;

    @Before
    public void setUp() {
        info = new Information<>(new Boolean[]{true, false, true, false, true, false, true, false});
        destinationFinale = new DestinationFinale<>();
    }

    @Test
    public void recevoirTest() throws InformationNonConformeException {
        destinationFinale.recevoir(info);
        if (!destinationFinale.getInformationRecue().equals(info)) {
            collector.addError(new AssertionError("Information reçue non identique à l'information envoyée"));
        }
    }

}
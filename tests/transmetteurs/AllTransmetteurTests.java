package transmetteurs;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({TransmetteurParfaitTest.class, TransmetteurMultiTrajetTest.class, ConvertisseurAnalogiqueNumeriqueTest.class, DecodeurTest.class, EgaliseurTest.class, CodageCanalTest.class})
public class AllTransmetteurTests {
}

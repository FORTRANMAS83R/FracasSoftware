package transmetteurs;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({TransmetteurParfaitTest.class, TransmetteurMultiTrajetTest.class, ConvertisseurAnalogiqueNumeriqueTest.class, ConvertisseurNumeriqueAnalogiqueTest.class, DecodeurTest.class, EgaliseurTest.class, CodeurTest.class})
public class AllTransmetteurTests {
}

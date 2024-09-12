package simulateur;

import destinations.AllDestinationTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import sources.AllSourceTests;
import transmetteurs.AllTransmetteurTests;

@RunWith(Suite.class)
@Suite.SuiteClasses({ AllDestinationTests.class, AllTransmetteurTests.class, AllSourceTests.class })
public class AllTests {
}
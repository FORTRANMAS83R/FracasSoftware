package simulateur;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import bruit.BBGTest;
import destinations.AllDestinationTests;
import sources.AllSourceTests;
import transmetteurs.AllTransmetteurTests;

@RunWith(Suite.class)
@Suite.SuiteClasses({ AllDestinationTests.class, AllTransmetteurTests.class, AllSourceTests.class, ArgumentTest.class,
		BBGTest.class })
public class AllTests {
}
package sources;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Classe de tests des différentes sources.
 * Cette classe utilise JUnit pour exécuter une suite de tests sur les classes de sources.
 */
@RunWith(Suite.class)
@SuiteClasses({SourceTest.class,
        SourceAleatoireTest.class,
        SourceFixeTest.class})
public class AllSourceTests {
}

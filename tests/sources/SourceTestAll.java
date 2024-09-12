package sources;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
/*
 *Class de tests des différentes sources
 */

@RunWith(Suite.class)
@SuiteClasses({ SourceTest.class,
        SourceTestAleatoire.class,
        SourceTestFixe.class,
        SourceRZTest.class,
        SourceNRZTest.class,
        SourceNRZTTest.class,
        SourceAnalogiqueTest.class })
public class SourceTestAll {}

package sources;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
/*
 *Class de tests des différentes sources
 */

@RunWith(Suite.class)
@SuiteClasses({ SourceTest.class,
        SourceAleatoireTest.class,
        SourceFixeTest.class,
        SourceAnalogiqueTest.class })
public class AllSourceTests {}

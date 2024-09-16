package sources;

import information.InformationNonConformeException;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Teste la méthode emettre de SourceAleatoire.
 * Vérifie que l'information émise est identique à l'information reçue par la destination.
 *
 * @throws InformationNonConformeException si l'information est non conforme.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        SourceRZTest.class,// Teste la classe SourceRZ
        SourceNRZTest.class,// Teste la classe SourceNRZT
        SourceNRZTTest.class})// Teste la classe SourceNRZ
public class SourceAnalogiqueTest {
}




package sources;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.util.Random;

import static org.hamcrest.CoreMatchers.is;

/**
 * Vérifie la véracité des informations générées en comparant avec les valeurs attendues.
 */
public class SourceTest {
    @Rule
    public ErrorCollector collector = new ErrorCollector();

    /**
     * Teste la génération d'information aléatoire sans graine.
     * Vérifie que la taille du message généré est correcte.
     */
    @Test
    public void testGenInformationAletoireSansSeed() {
        //Appel de la méthode genInformationAletoire avec seed == null
        SourceAleatoire src = new SourceAleatoire(10, null);
        //Vérification de la taille du message généré
        collector.checkThat("Test genInformation aléatoire sans seed", src.getInformationGeneree().nbElements(), is(10));
    }

    /**
     * Teste la génération d'information aléatoire avec graine.
     * Vérifie que la taille du message généré est correcte et que les valeurs générées sont correctes.
     */
    @Test
    public void testGenInformationAletoireAvecSeed() {
        //Appel de la méthode genInformationAletoire avec seed != null
        SourceAleatoire src = new SourceAleatoire(10, 10);
        //Vérification de la taille du message généré
        collector.checkThat("Test genInformation aléatoire avec seed", src.getInformationGeneree().nbElements(), is(10));
        //Vérification terme à terme
        Random expected = new Random(10);
        for (int i = 0; i < 10; i++) {
            if (src.informationGeneree.iemeElement(i) != expected.nextBoolean()) {
                collector.addError(new AssertionError("Erreur de génération de la source aléatoire"));
            }
        }
    }

    /**
     * Teste la génération d'information fixe.
     * Vérifie que la génération de la source fixe à partir d'un message donné est correcte.
     */
    @Test
    public void testgenInformation() {
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
}

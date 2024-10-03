package transmetteurs;

import information.Information;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import static org.hamcrest.CoreMatchers.is;

public class ConvertisseurAnalogiqueNumeriqueTest {

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    /**
     * Test du constructeur
     */
    @Test
    public void testConstructeurNullTrajetNullBruit() {
        ConvertisseurAnalogiqueNumerique<Float, Boolean> can = new ConvertisseurAnalogiqueNumerique<>(1.0f, 10, 1000);
        collector.checkThat(can.getSeuil(), is(1.0f));
        collector.checkThat(can.getNbBitsMessage(), is(1000));
        collector.checkThat(can.getNbEch(), is(10));
        collector.checkThat(can.getInformationRecue(), is((Information<Float>) null));
        collector.checkThat(can.getInformationEmise(), is((Information<Boolean>) null));
    }

    /**
     * Teste que la taille de l'information émise est conforme.
     */
    @Test
    public void testTailleInfoEmise() {
        ConvertisseurAnalogiqueNumerique<Float, Boolean> can = new ConvertisseurAnalogiqueNumerique<>(1.0f, 10, 1000);
        Information<Float> info = new Information<>();
        for (int i = 0; i < 1000 * 10; i++) {
            info.add(1.0f);
        }
        try {
            can.recevoir(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        collector.checkThat(can.getInformationEmise().nbElements(), is(1000));
    }

    /**
     * Teste que la conversion est conforme.
     */
    @Test
    public void conversionTypeInfo() {
        ConvertisseurAnalogiqueNumerique<Float, Boolean> can = new ConvertisseurAnalogiqueNumerique<>(1.0f, 10, 1000);
        Information<Float> info = new Information<>();
        for (int i = 0; i < 1000 * 10; i++) {
            info.add(1.0f);
        }
        try {
            can.recevoir(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 1000; i++) {
            collector.checkThat(can.getInformationEmise().iemeElement(i), is(true));
        }
    }

}

package transmetteurs;

import destinations.DestinationFinale;
import information.Information;
import information.InformationNonConformeException;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.Rule;
import sources.*;

import static org.hamcrest.CoreMatchers.is;

public class ConvertisseurNumeriqueAnalogiqueTest {
    @Rule
    public ErrorCollector collector = new ErrorCollector();

    String message;
    int messageLength;
    int nbEch;
    float amp_min_RZ;
    float amp_max_RZ;
    float amp_min_NRZ_NRZT;
    float amp_max_NRZ_NRZT;
    SourceAnalogiqueType type;
    Source<Boolean> sourceFixe;
    ConvertisseurNumeriqueAnalogique<Boolean, Float> cna;
    ConvertisseurAnalogiqueNumerique<Float, Boolean> can;
    DestinationFinale<Boolean> destination;
    Information<Float> infoCible;

    @Before
    public void setUp() {
        message = "10101100";
        messageLength = message.length();
        nbEch = 30;
        amp_min_RZ = 0.0f;
        amp_max_RZ = 5.0f;
        amp_min_NRZ_NRZT = -5.0f;
        amp_max_NRZ_NRZT = 5.0f;
        sourceFixe = new SourceFixe(message);
        destination = new DestinationFinale<>();
    }

    @Test
    public void testConversionRZ() throws InformationNonConformeException {
        type = SourceAnalogiqueType.RZ;
        infoCible = new Information<>(new Float[]{0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f});
        cna = new ConvertisseurNumeriqueAnalogique<>(nbEch, amp_min_RZ, amp_max_RZ, type);
        can = new ConvertisseurAnalogiqueNumerique<>(nbEch, 8,(amp_min_RZ+amp_max_RZ)/2, type);
        sourceFixe.connecter(cna);
        cna.connecter(can);
        can.connecter(destination);
        sourceFixe.emettre();

        collector.checkThat("Vérification taille information reçue CNA", cna.getInformationRecue().nbElements(), is(messageLength));
        collector.checkThat("Vérification taille information émise CNA", cna.getInformationEmise().nbElements(), is(messageLength*nbEch));
        collector.checkThat("Vérification information émise CNA", cna.getInformationEmise().equals(infoCible), is(true));
    }
    @Test
    public void testConversionNRZ() throws InformationNonConformeException {
        type = SourceAnalogiqueType.NRZ;
        infoCible = new Information<>(new Float[]{5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f});
        cna = new ConvertisseurNumeriqueAnalogique<>(nbEch, amp_min_NRZ_NRZT, amp_max_NRZ_NRZT, type);
        can = new ConvertisseurAnalogiqueNumerique<>(nbEch, 8,(amp_min_NRZ_NRZT+amp_max_NRZ_NRZT)/2, type);
        sourceFixe.connecter(cna);
        cna.connecter(can);
        can.connecter(destination);
        sourceFixe.emettre();

        collector.checkThat("Vérification taille information reçue CNA", cna.getInformationRecue().nbElements(), is(messageLength));
        collector.checkThat("Vérification taille information émise CNA", cna.getInformationEmise().nbElements(), is(messageLength*nbEch));
        collector.checkThat("Vérification information émise CNA", cna.getInformationEmise().equals(infoCible), is(true));
    }

    @Test
    public void testConversionNRZT() throws InformationNonConformeException {
        type = SourceAnalogiqueType.NRZT;
        infoCible = new Information<>(new Float[]{0.0f,0.5f,1.0f,1.5f,2.0f,2.5f,3.0f,3.5f,4.0f,4.5f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,4.5f,4.0f,3.5f,3.0f,2.5f,2.0f,1.5f,1.0f,0.5f,-0.0f,-0.5f,-1.0f,-1.5f,-2.0f,-2.5f,-3.0f,-3.5f,-4.0f,-4.5f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-4.5f,-4.0f,-3.5f,-3.0f,-2.5f,-2.0f,-1.5f,-1.0f,-0.5f,0.0f,0.5f,1.0f,1.5f,2.0f,2.5f,3.0f,3.5f,4.0f,4.5f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,4.5f,4.0f,3.5f,3.0f,2.5f,2.0f,1.5f,1.0f,0.5f,-0.0f,-0.5f,-1.0f,-1.5f,-2.0f,-2.5f,-3.0f,-3.5f,-4.0f,-4.5f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-4.5f,-4.0f,-3.5f,-3.0f,-2.5f,-2.0f,-1.5f,-1.0f,-0.5f,0.0f,0.5f,1.0f,1.5f,2.0f,2.5f,3.0f,3.5f,4.0f,4.5f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,4.5f,4.0f,3.5f,3.0f,2.5f,2.0f,1.5f,1.0f,0.5f,-0.0f,-0.5f,-1.0f,-1.5f,-2.0f,-2.5f,-3.0f,-3.5f,-4.0f,-4.5f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-5.0f,-4.4444447f,-3.8888888f,-3.3333333f,-2.7777777f,-2.222222f,-1.6666665f,-1.1111109f,-0.55555534f,0.0f});
        cna = new ConvertisseurNumeriqueAnalogique<>(nbEch, amp_min_NRZ_NRZT, amp_max_NRZ_NRZT, type);
        can = new ConvertisseurAnalogiqueNumerique<>(nbEch, 8,(amp_min_NRZ_NRZT+amp_max_NRZ_NRZT)/2, type);
        sourceFixe.connecter(cna);
        cna.connecter(can);
        can.connecter(destination);
        sourceFixe.emettre();

        collector.checkThat("Vérification taille information reçue CNA", cna.getInformationRecue().nbElements(), is(messageLength));
        collector.checkThat("Vérification taille information émise CNA", cna.getInformationEmise().nbElements(), is(messageLength*nbEch));
        collector.checkThat("Vérification information émise CNA", cna.getInformationEmise().equals(infoCible), is(true));
    }

}

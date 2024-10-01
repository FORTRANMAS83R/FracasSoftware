package bruit;

import information.Information;
import org.easymock.EasyMock;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.CoreMatchers.is;

/**
 * Classe de test pour la classe BBG.
 */
public class BBGTest {

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    /**
     * Teste le calcul de la puissance du signal par la classe BBG.
     */
    @Test
    public void testCalculPuissanceSignal() {
        ArrayList<Float> signal = new ArrayList<>(Arrays.asList(2f, 2f, 2f, 2f));
        Information<Float> mockInformation = mockInit(signal);
        final Float puissanceSignal = BBG.calculPuissanceSignal(mockInformation);
        collector.checkThat(4f, is(puissanceSignal));
    }

    /**
     * Teste le calcul de la variance par la classe BBG.
     */
    @Test
    public void testCalculVariance() {
        Information<Float> mockInformation = mockInit(new ArrayList<>(Arrays.asList(2f, 2f, 2f, 2f)));
        final Float variance = BBG.calculVariance(BBG.calculPuissanceSignal(mockInformation), 20f, 4);
        collector.checkThat(0.08f, is(variance));
    }

    /**
     * Initialise et retourne un objet mock de type Information<Float> en utilisant
     * EasyMock.
     * Cette méthode simule le comportement d'un objet Information pour les tests.
     *
     * @param signal La liste des valeurs flottantes à utiliser pour initialiser le
     *               mock.
     * @return Un objet mock de type Information<Float>.
     */
    public static Information<Float> mockInit(ArrayList<Float> signal) {
        // Crée un mock de la classe Information
        Information<Float> mockInformation = EasyMock.createMock(Information.class);
        // Définit les attentes pour le mock
        expect(mockInformation.getContent()).andReturn(signal);
        expect(mockInformation.iterator()).andReturn(signal.iterator());
        expect(mockInformation.nbElements()).andReturn(4);
        // Passe le mock en mode replay
        EasyMock.replay(mockInformation);
        // Retourne le mock configuré
        return mockInformation;

    }

    /**
     * Teste le constructeur de la classe BBG sans seed.
     */
    @Test
    public void testConstructeur() {
        final BBG bruit = new BBG(20f);
        collector.checkThat(20f, is(bruit.getSNRpb()));

    }

    /**
     * Teste le constructeur de la classe BBG avec seed.
     */
    @Test
    public void testConstructeurSeed() {
        final BBG bruit = new BBG(20f, 25);
        // check that bruit is instance of BBG
        collector.checkThat(20f, is(bruit.getSNRpb()));

    }

    /**
     * Initialise et retourne un objet mock de type Information\<Float\> pour la
     * méthode bruitage en utilisant EasyMock.
     * Cette méthode simule le comportement d'un objet Information pour les tests.
     *
     * @param signal La liste des valeurs flottantes à utiliser pour initialiser le
     *               mock.
     * @return Un objet mock de type Information\<Float\>.
     */
    public static Information<Float> mockBruitage(ArrayList<Float> signal) {
        // Crée un mock de la classe Information
        Information<Float> mockInformation = EasyMock.createMock(Information.class);

        // Définit les attentes pour le mock
        expect(mockInformation.clone()).andReturn(mockInformation).anyTimes();
        expect(mockInformation.getNbEchantillons()).andReturn(4).anyTimes();
        expect(mockInformation.nbElements()).andReturn(4).anyTimes();
        expect(mockInformation.iterator()).andReturn(signal.iterator()).anyTimes();

        // Ajout des attentes pour les appels à iemeElement
        for (int i = 0; i < signal.size(); i++) {
            expect(mockInformation.iemeElement(i)).andReturn(signal.get(i)).anyTimes();
        }

        // Permet d'appeler setIemeElement dans le bruitage
        mockInformation.setIemeElement(EasyMock.anyInt(), EasyMock.anyFloat());
        EasyMock.expectLastCall().anyTimes();

        // Passe le mock en mode replay
        EasyMock.replay(mockInformation);

        // Retourne le mock configuré
        return mockInformation;
    }

    /**
     * Teste la méthode bruitage de la classe BBG.
     */
    @Test
    public void testBruitage() {
        final BBG bruit = new BBG(20f, 25);
        Random a1 = new Random(25);
        Random a2 = new Random(25);

        // Crée un signal de base (par exemple 4 échantillons avec des valeurs
        // identiques)
        ArrayList<Float> signal = new ArrayList<>(Arrays.asList(2f, 2f, 2f, 2f));
        // Mock d'information basé sur le signal original
        Information<Float> mockInformation = mockBruitage(signal);
        // Crée une instance réelle de l'information pour stocker le signal bruité
        // attendu
        Information<Float> signalBruiteExpected = new Information<>(signal.toArray(new Float[0]));
        // Calcul de la variance pour le bruit attendu
        Float variance = BBG.calculVariance(BBG.calculPuissanceSignal(signalBruiteExpected), 20f, 4);

        // Applique le bruitage manuellement en utilisant les générateurs a1 et a2
        for (int i = 0; i < signalBruiteExpected.nbElements(); i++) {
            float bruitAjoute = (float) Math.sqrt(variance) * (float) Math.sqrt(-2 * Math.log(1 - a1.nextFloat()))
                    * (float) Math.cos(2 * Math.PI * a2.nextFloat());

            // Met à jour le signal bruité attendu
            signalBruiteExpected.setIemeElement(i, signalBruiteExpected.iemeElement(i) + bruitAjoute);
        }

        // Bruitage réel basé sur la classe BBG
        Information<Float> signalBruite = bruit.bruitage(mockInformation);

        // Comparaison des résultats : signal bruité par BBG vs signal bruité attendu
        Iterator<Float> itBruite = signalBruite.iterator();
        Iterator<Float> itBruiteAttendu = signalBruiteExpected.iterator();

        while (itBruite.hasNext() && itBruiteAttendu.hasNext()) {
            Float valeurBruite = itBruite.next();
            Float valeurAttendue = itBruiteAttendu.next();
            collector.checkThat(valeurAttendue, is(valeurBruite));
        }
    }
}

package transmetteurs;

import bruit.BBG;
import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;

public class TransmetteurMultiTrajet extends Transmetteur<Float, Float> {

    private final List<SimpleEntry<Integer, Float>> trajets; // liste des parametres des trajets multiples
    private Float SNRpb = null; // SNR par bit, vaut null si non défini
    private Integer seed = null;

    /**
     * Constructeur du transmetteur multi-trajet avec bruit.
     * 
     * @param trajets les coefficients des trajets multiples
     * @param SNRpb   le snrpb du bruit, null pour pas de bruit
     * @param seed    seed ou null pour pas de seed
     */
    public TransmetteurMultiTrajet(List<SimpleEntry<Integer, Float>> trajets, Float SNRpb, Integer seed) {
        super();
        this.trajets = trajets;
        this.SNRpb = SNRpb;
        this.seed = seed;
    }

    /**
     * Reçoit une information, y ajoute des trajets multiples, éventuellement le
     * bruit, puis l'émet.
     *
     * @param information L'information reçue.
     * @throws InformationNonConformeException Si l'information est non conforme.
     */
    @Override
    public void recevoir(Information<Float> information) throws InformationNonConformeException {
        this.informationRecue = information.clone();
        if (this.SNRpb == null) {
            this.informationEmise = multiTrajet(informationRecue);
        } else {
            BBG bruit = new BBG(this.SNRpb, this.seed);
            this.informationEmise = bruit.bruitage(multiTrajet(informationRecue));
        }
        emettre();
    }

    /**
     * Émet l'information bruitée à toutes les destinations connectées.
     *
     * @throws InformationNonConformeException Si l'information est non conforme.
     */
    @Override
    public void emettre() throws InformationNonConformeException {
        for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationEmise);
        }
    }

    /**
     * Retourne le plus grand retard d'un trajet multiple.
     * 
     * @return le plus grand retard.
     */
    public Integer tauMax() {
        Integer maxTrajet = 0;
        for (SimpleEntry<Integer, Float> trajet : trajets) {
            if (trajet.getKey() > maxTrajet) {
                maxTrajet = trajet.getKey();
            }
        }
        return maxTrajet;
    }

    /**
     * Applique les paramètres de multi-trajet à une information
     * 
     * @param information
     * @return
     */
    public Information<Float> multiTrajet(Information<Float> information) {
        Information<Float> informationMultiTrajet = multiply(information.clone(), setAmpTrajetDirect(this.trajets));
        Integer maxTau = this.tauMax();
        informationMultiTrajet.addLast(new ArrayList<>(Collections.nCopies(maxTau, 0.0f)));
        for (int i = 0; i < informationMultiTrajet.nbElements(); i++) {
            for (SimpleEntry<Integer, Float> trajet : trajets) {
                if (i >= trajet.getKey() && i - trajet.getKey() < information.nbElements()) {
                    informationMultiTrajet.setIemeElement(i,
                            informationMultiTrajet.iemeElement(i)
                                    + trajet.getValue() * information.iemeElement(i -
                                            trajet.getKey()));
                }
            }
        }
        return informationMultiTrajet;
    }

    /**
     * Retourne la liste des paramètres des trajets multiples.
     *
     * @return la liste des paramètres des trajets multiples.
     */
    public List<SimpleEntry<Integer, Float>> getTrajets() {
        return trajets;
    }

    /**
     * Retourne le SNR par bit.
     *
     * @return le SNR par bit.
     */
    public Float getSNRpb() {
        return SNRpb;
    }

    /**
     * Retourne le seed.
     *
     * @return le seed.
     */
    public Integer getSeed() {
        return seed;
    }

    public static Float setAmpTrajetDirect(List<SimpleEntry<Integer, Float>> trajets) {
        Float sum = 0.0f;
        for (int i = 0; i < trajets.size(); i++) {
            sum += (float) Math.pow(trajets.get(i).getValue(), 2);
        }
        System.out.println("AmpTrajetDirect: " + sum);
        return (float) Math.sqrt(1 - sum);
    }

    public static Information<Float> multiply(Information<Float> information, Float a) {
        for (int i = 0; i < information.nbElements(); i++) {
            information.setIemeElement(i, information.iemeElement(i) * a);
        }
        return information;
    }
}

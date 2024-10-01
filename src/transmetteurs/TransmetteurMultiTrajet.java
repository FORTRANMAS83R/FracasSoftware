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

    private List<SimpleEntry<Integer, Float>> trajets; // liste des parametres des trajets multiples
    private Float SNRpb = null; // SNR par bit, vaut null si non défini

    /**
     * Constructeur du transmetteur multi-trajet sans bruit.
     *
     * @param trajets La liste des paramètres des trajets multiples.
     */
    public TransmetteurMultiTrajet(List<SimpleEntry<Integer, Float>> trajets) {
        super();
        this.trajets = trajets;
    }

    /**
     * Constructeur du transmetteur multi-trajet avec bruit.
     * 
     * @param trajets
     * @param SNRpb
     */
    public TransmetteurMultiTrajet(List<SimpleEntry<Integer, Float>> trajets, Float SNRpb) {
        super();
        this.trajets = trajets;
        this.SNRpb = SNRpb;
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
            BBG bruit = new BBG(this.SNRpb);
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
        Information<Float> informationMultiTrajet = information.clone();
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
}

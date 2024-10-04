package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

// TODO Pour codage canal, prévoir un champ codage dans le constructeur et modifier la traduction float vers binaire
//  en conséquence (voir énoncé pour les spécifications)

/**
 * La classe ConvertisseurAnalogiqueNumerique représente un convertisseur
 * analogique-numérique. Elle étend la classe Transmetteur et implémente la
 * méthode de conversion spécifique pour le codage NRZ.
 */
public class ConvertisseurAnalogiqueNumerique<R, E> extends Transmetteur<Float, Boolean> {
    public float seuil;
    public int nbEch;
    public int nbBitsMessage;

    /**
     * Construit un objet ConvertisseurAnalogiqueNumerique avec les paramètres
     * spécifiés.
     *
     * @param moyAmpl        Le seuil décision qui correspond dans notre cas à la moyenne de l'amplitude.
     * @param nbEch          Le nombre d'échantillons par symbole.
     * @param nbBitsMessage  Le nombre de bits dans le message.
     */
    public ConvertisseurAnalogiqueNumerique(float moyAmpl, int nbEch, int nbBitsMessage) {
        super();
        this.seuil = moyAmpl;
        this.nbEch = nbEch;
        this.nbBitsMessage = nbBitsMessage;
    }

    /**
     * Méthode traitant la réception de l'information par le convertisseur analogique-numérique.
     *
     * @param information L'information reçue.
     */
    @Override
    public void recevoir(Information<Float> information) throws InformationNonConformeException {
        this.informationRecue = information.clone();
        informationEmise = new Information<>();
        for (int i = 0; i < nbBitsMessage; i++) {
            float somme = 0;
            for (int j = 0; j < nbEch; j++) {
                somme += information.iemeElement(i * nbEch + j);
            }
            float moy = somme / (float) nbEch;
            if (moy < seuil) {
                informationEmise.add(false);
            } else {
                informationEmise.add(true);
            }
        }
        emettre();
    }

    /**
     * Méthode traitant l'émission de l'information par le convertisseur analogique-numérique.
     */
    @Override
    public void emettre() throws InformationNonConformeException {
        for (DestinationInterface<Boolean> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationEmise);
        }
    }

    public float getSeuil() {
        return seuil;
    }

    public int getNbBitsMessage() {
        return nbBitsMessage;
    }

    public int getNbEch() {
        return nbEch;
    }
}

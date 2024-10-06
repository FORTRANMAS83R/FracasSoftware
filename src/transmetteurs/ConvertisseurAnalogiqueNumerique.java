package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;
import sources.SourceAnalogiqueType;

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
     * @param nbEch          Le nombre d'échantillons par symbole.
     * @param nbBitsMessage  Le nombre de bits dans le message.
     */
    public ConvertisseurAnalogiqueNumerique(int nbEch, int nbBitsMessage, float moyAmpl, SourceAnalogiqueType type) {
        super();
        this.nbEch = nbEch;
        this.nbBitsMessage = nbBitsMessage;
        if (type == SourceAnalogiqueType.RZ) {
            this.seuil = moyAmpl/3;
        } else {
            this.seuil = moyAmpl;
        }
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
        // Pour chaque symbole :
        for (int i = 0; i < this.informationRecue.nbElements()/nbEch; i++) {
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

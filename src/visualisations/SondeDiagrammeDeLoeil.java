package visualisations;

import information.Information;

/**
 * Classe réalisant l'affichage d'information composée d'éléments
 */
public class SondeDiagrammeDeLoeil extends Sonde<Float> {

    public SondeDiagrammeDeLoeil(String nom) {
        super(nom);
    }

    public void recevoir(Information<Float> information) {
        informationRecue = information;
        new VueOeil(information, nom);
    }
}

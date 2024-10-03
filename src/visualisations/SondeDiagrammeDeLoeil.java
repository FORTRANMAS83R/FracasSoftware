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
        int nbElements = information.nbElements();
        float[] table = new float[nbElements];
        int i = 0;
        for (float f : information) {
            table[i] = f;
            i++;
        }
        new VueOeil(table, nom);
    }
}

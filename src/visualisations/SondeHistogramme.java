package visualisations;

import information.Information;

import java.util.ArrayList;
import java.util.Collections;

public class SondeHistogramme extends Sonde<Float> {

    public SondeHistogramme(String nom) {
        super(nom);
    }

    @Override
    public void recevoir(Information<Float> information) {
        informationRecue = information;
        ArrayList<Float> valeurs = information.getContent();
        Float k = (float) Math.floor(2*Math.log(information.nbElements())+1);
        Float max = Collections.max(valeurs);
        Float min = Collections.min(valeurs);
        Float delta = (max - min) / k;

        ArrayList<Float> bords = new ArrayList<>();
        for (float i = min; i <= max + delta; i += delta) {
            bords.add(i);
        }

        // Initialiser l'histogramme (fréquences pour chaque intervalle)
        int[] histogramme = new int[(bords.size() - 1)];

        // Calculer les fréquences dans chaque intervalle
        for (float valeur : valeurs) {
            for (int i = 0; i < bords.size() - 1; i++) {
                if (valeur >= bords.get(i) && valeur < bords.get(i + 1)) {
                    histogramme[i]++;
                    break;
                }
            }
        }
        new VueHistogramme(histogramme, nom);
    }


}

package visualisations;

import information.Information;
import org.apache.commons.math3.special.Gamma; // Pour calculer la fonction gamma (CDF du chi-carré)
import org.apache.commons.math3.stat.descriptive.moment.Kurtosis;
import org.apache.commons.math3.stat.descriptive.moment.Skewness;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Classe SondeHistogramme
 */
public class SondeHistogramme extends Sonde<Float> {

    private boolean affichage; // Si vrai, affiche l'histogramme

    /**
     * Constructeur de la sonde
     * @param nom le nom de la sonde
     * @param affichage si vrai, affiche l'histogramme
     */
    public SondeHistogramme(String nom, boolean affichage) {
        super(nom);
        this.affichage = affichage;
    }

    /**
     * Méthode qui reçoit une information et qui génère un histogramme de cette information
     * @param information l'information reçue
     */
    @Override
    public void recevoir(Information<Float> information) {
        informationRecue = information;
        int[] histogramme = histogramme(information);
        estGaussien(histogramme);

        if (affichage){
            new VueHistogramme(histogramme, nom);
        }
    }

    /**
     * Méthode qui génère un histogramme à partir d'une information
     * @param information l'information à traiter
     * @return l'histogramme
     */
    private static int[] histogramme(Information<Float> information){
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
        return histogramme;
    }

    /**
     * Méthode qui teste si un histogramme est gaussien. Se base sur le test de Jarque-Bera pour calculer la p-valeur
     * @param histogramme l'histogramme à tester
     */
    public static void estGaussien(int[] histogramme){
        Float alpha = 0.05f;
        Skewness skewness = new Skewness();
        Kurtosis kurtosis = new Kurtosis();
        double[] histogrammeDouble = new double[histogramme.length];
        for (int i = 0; i < histogramme.length; i++) {
            histogrammeDouble[i] = (double) histogramme[i];
        }


        double skew = skewness.evaluate(histogrammeDouble); // Calculer le coefficient d'asymétrie
        double kurt = kurtosis.evaluate(histogrammeDouble); // Calculer le coefficient d'aplatissement

        double n = histogrammeDouble.length;
        double jarqueBera = n / 6 * (Math.pow(skew, 2) + Math.pow(kurt - 3, 2) / 4); // Calculer la statistique de test de Jarque-Bera
        double pValue = 1 - Gamma.regularizedGammaP(n / 2, jarqueBera / 2); // Calculer la p-valeur
        if(pValue < alpha){
            System.out.println("L'échantillon n'est pas gaussien: \n\tp-valeur = " + pValue+"\n\talpha = "+alpha+"\n\tjarqueBera = "+jarqueBera+"\n");
        } else {
            System.out.println("L'échantillon est gaussien: \n\tp-valeur = " + pValue+"\n\talpha = "+alpha+"\n\tjarqueBera = "+jarqueBera+"\n");
        }
    }


}

package simulateur;

import visualisations.VueCourbe;

import java.util.Arrays;
import java.util.LinkedList;

public class tebEnFctSNR {
    public static void main(String[] args) throws Exception {
        int size = 800; // si vous modifiez cette valeur, vérifiez que la boucle avec snr ne lance pas OutOfBandsException

        LinkedList<float[]> tabs = new LinkedList<>();
        float[] finalTab = new float[size];
        // Boucle pour effectuer plusieurs simulations
        for (int j = 0; j < 5; j++) {
            float[] tempTab = new float[size];
            int i = 0;
            // Boucle pour varier le SNR de -50 à 30 avec un pas de 0.1
            for (double snr = -50; snr <= 30; snr += 0.1) {
                // Création et exécution du simulateur avec les paramètres spécifiés
                Simulateur simulateur = new Simulateur(new String[]{"-mess", "100", "-form", "NRZ", "-nbEch", "3", "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000)});
                simulateur.execute();
                // Calcul du taux d'erreur binaire et stockage dans le tableau temporaire
                tempTab[i] = simulateur.calculTauxErreurBinaire();
                i++;
            }
            tabs.add(tempTab);
        }
        // Calcul de la moyenne des taux d'erreur binaire pour chaque valeur de SNR
        for (int y = 0; y < size; y++) {
            float somme = 0;
            for (float[] tab : tabs) {
                somme += tab[y];
            }
            finalTab[y] = somme / (float) tabs.size();
        }
        // Affichage des résultats finaux et création de la courbe
        System.out.println(Arrays.toString(finalTab));
        new VueCourbe(finalTab, "TEB en fonction du SNR");
    }
}

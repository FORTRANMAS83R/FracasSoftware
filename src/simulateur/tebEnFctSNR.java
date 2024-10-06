package simulateur;

import org.apache.commons.math3.special.Erf;

import java.io.File;
import java.io.FileWriter;
import java.util.LinkedList;

public class tebEnFctSNR {
    /**
     * Cette méthode et plus généralement cette classe permet de dessiner les
     * courbes du TEB en fonction du SNR
     * Les courbes concernées sont les courbes observées en pratique et la courbe
     * théorique.
     * Pour un affichage exploitable des courbes observées via la simulation, nous
     * avons pris la décision de lancer
     * 5000 fois les 800 simulations et de faire la moyenne des valeurs obtenues
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        int size = 300; // si vous modifiez cette valeur, vérifiez que la boucle avec snr ne lance pas
        // OutOfBandsException
        double snrMin = 0;
        double snrMax = 30;
        int tailleMessage = 100000;
        int nbEch = 30;
        int ampMinRZ = 0;
        int ampMaxRZ = 5;
        float ampMinNRZetNRZT = -2.5f;
        float ampMaxNRZetNRZT = 2.5f;

        int nombreDeSimulations = 10;


        LinkedList<float[]> tabsNRZ = new LinkedList<>();
        LinkedList<float[]> tabsRZ = new LinkedList<>();
        LinkedList<float[]> tabsNRZT = new LinkedList<>();
        float[] finalTabNRZ = new float[size];
        float[] finalTabRZ = new float[size];
        float[] finalTabNRZT = new float[size];
        float[] tabTEBTheo = new float[size];
        float[] tabTEBTheoCodeur = new float[size];
        float[] tabSNR = new float[size];

        // Boucle pour effectuer plusieurs simulations
        for (int j = 0; j < nombreDeSimulations; j++) {
            float[] tempTabNRZ = new float[size];
            float[] tempTabRZ = new float[size];
            float[] tempTabNRZT = new float[size];


            int i = 0;
            // Boucle pour varier le SNR avec un pas de 0.1
            for (double snr = snrMin; snr <= snrMax; snr += 0.1) {
                String[] argumentsNRZT = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "NRZT", "-codeur", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinNRZetNRZT), String.valueOf(ampMaxNRZetNRZT), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000)};
                String[] argumentsNRZ = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "NRZ", "-codeur", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinNRZetNRZT), String.valueOf(ampMaxNRZetNRZT), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000)};
                String[] argumentsRZ = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "RZ", "-codeur", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinRZ), String.valueOf(ampMaxRZ), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000)};
                // Création et exécution du simulateur avec les paramètres spécifiés
                Simulateur simulateur = new Simulateur(argumentsNRZT);
                simulateur.execute();
                // Calcul du taux d'erreur binaire et stockage dans le tableau temporaire
                tempTabNRZT[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(argumentsRZ);
                simulateur.execute();
                tempTabRZ[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(argumentsNRZ);
                simulateur.execute();
                tempTabNRZ[i] = simulateur.calculTauxErreurBinaire();

                // Définition des arguments des différentes simulations

                i++;
                System.out.println("Simulation " + j + "/" + nombreDeSimulations + " boucle SNR " + i);
            }
            System.out.println("numéro de la simulation " + j + "/" + nombreDeSimulations);
            // Ajout des tableaux temporaires dans les listes

            tabsNRZ.add(tempTabNRZ);
            tabsRZ.add(tempTabRZ);
            tabsNRZT.add(tempTabNRZT);
        }

        // Calcul de la courbe théorique en utilisant la fonction d'erreur
        // complémentaire
        int i = 0;
        for (double snr = snrMin; snr <= snrMax; snr += 0.1) {
//            tabTEBTheo[i] = (float) (0.5 * Erf.erfc(Math.sqrt(Math.pow(10, snr / 10))));
            tabTEBTheo[i] = (float) ((1/2.0f) * Erf.erfc(1/(Math.sqrt(2) * Math.sqrt((double) 1 /(2*Math.pow(10, snr/10))))));
            tabTEBTheoCodeur[i] = (float) ((1/6.0f) * Erf.erfc(1/(Math.sqrt(2) * Math.sqrt((double) 1 /(2*Math.pow(10, snr/10))))));
            tabSNR[i] = (float) snr;
            if (snr < 0.1 && snr > -0.1) {
                tabSNR[i] = 0;
            }
            i++;
        }

        // Calcul de la moyenne des taux d'erreur binaire pour chaque valeur de SNR
        for (int y = 0; y < size; y++) {
            float sommeNRZ = 0;
            float sommeRZ = 0;
            float sommeNRZT = 0;


            for (float[] tab : tabsNRZ) {
                sommeNRZ += tab[y];
            }
            for (float[] tab : tabsRZ) {
                sommeRZ += tab[y];
            }
            for (float[] tab : tabsNRZT) {
                sommeNRZT += tab[y];
            }

            // Calcul de la moyenne
            // POUR RZ


            finalTabNRZ[y] = sommeNRZ / (float) tabsNRZ.size();
            finalTabRZ[y] = sommeRZ / (float) tabsRZ.size();
            finalTabNRZT[y] = sommeNRZT / (float) tabsNRZT.size();
        }
        // Affichage des résultats finaux et création de la courbe
//        System.out.println(Arrays.toString(finalTabNRZ));
//        System.out.println(Arrays.toString(finalTabRZ));
//        System.out.println(Arrays.toString(finalTabNRZT));
//        System.out.println(Arrays.toString(tabTEBTheo));


        String[] finalTabNRZString = new String[size];
        String[] finalTabRZString = new String[size];
        String[] finalTabNRZTString = new String[size];
        String[] tabTEBTheoString = new String[size];
        String[] tabTEBTheoCodeurString = new String[size];
        String[] tabSNRString = new String[size];

        for (int x = 0; x < size; x++) {
            finalTabNRZString[x] = String.valueOf(finalTabNRZ[x]);
            finalTabRZString[x] = String.valueOf(finalTabRZ[x]);
            finalTabNRZTString[x] = String.valueOf(finalTabNRZT[x]);
            tabTEBTheoString[x] = String.valueOf(tabTEBTheo[x]);
            tabTEBTheoCodeurString[x] = String.valueOf(tabTEBTheoCodeur[x]);
            tabSNRString[x] = String.valueOf(tabSNR[x]);
        }

        LinkedList<String[]> finalTabs = new LinkedList<>();
        finalTabs.add(tabSNRString);
        finalTabs.add(tabTEBTheoString);
        finalTabs.add(tabTEBTheoCodeurString);
        finalTabs.add(finalTabRZString);
        finalTabs.add(finalTabNRZString);
        finalTabs.add(finalTabNRZTString);

        // Créer un fichier CSV avec le nom de chaque tableau et les valeurs associées
        File csvFile = new File("tebEnFctSNR_Theo.csv");
        FileWriter writer = new FileWriter(csvFile);
        writer.write("SNR par bit,TEB theorique,TEB Theorique codeur,TEB RZ, TEB NRZ, TEB NRZT\n");
//        writer.write("SNR par bit,TEB theorique,TEB Theorique codeur\n");
        for (int x = 0; x < size; x++) {
            StringBuilder sb = new StringBuilder();
            i = 0;
            for (String[] tab : finalTabs) {
                sb.append(tab[x]);
                if (i < finalTabs.size() - 1) {
                    sb.append(",");
                }
                i++;
            }
            sb.append("\n");
            writer.write(sb.toString());
        }

//        new VueCourbe(finalTabRZ_1ti_nbEch_div_10_alpha_075, "RZ 1ti nbEch div 10 alpha 075");

        // new VueCourbe(finalTabNRZ, "TEB en fonction du SNR pour NRZ");
        // new VueCourbe(finalTabRZ, "TEB en fonction du SNR pour RZ");
        // new VueCourbe(finalTabNRZT, "TEB en fonction du SNR pour NRZT");
        // new VueCourbe(tabTEBTheo, "TEB théorique en fonction du SNR");
    }

}

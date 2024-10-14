package simulateur;

import org.apache.commons.math3.special.Erf;

import java.io.File;
import java.io.FileWriter;
import java.util.LinkedList;

public class tebEnFctSNRetTM {
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
        int nbEch = 6;
        float ampMinNRZetNRZT = -2.5f;
        float ampMaxNRZetNRZT = 2.5f;

        int nombreDeSimulations = 1;

        // POUR NRZ
        LinkedList<float[]> tabsNRZ_1ti_10_alpha_05 = new LinkedList<>();

        // POUR NRZT
        LinkedList<float[]> tabsNRZT_1ti_10_alpha_05 = new LinkedList<>();

        // POUR NRZ
        float[] finalTabNRZ_1ti_10_alpha_05 = new float[size];

        // POUR NRZT
        float[] finalTabNRZT_1ti_10_alpha_05 = new float[size];

        float[] tabTEBTheo = new float[size];
        float[] tabSNR = new float[size];

        // Boucle pour effectuer plusieurs simulations
        for (int j = 0; j < nombreDeSimulations; j++) {

            // POUR NRZ
            float[] tempTabNRZ_1ti_10_alpha_05 = new float[size];

            // POUR NRZT
            float[] tempTabNRZT_1ti_10_alpha_05 = new float[size];


            int i = 0;
            // Boucle pour varier le SNR avec un pas de 0.1
            for (double snr = snrMin; snr <= snrMax; snr += 0.1) {
                // Définition des arguments des différentes simulations

                // POUR NRZ
                String[] argumentsNRZ_1ti_10_alpha_05 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "NRZ", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinNRZetNRZT), String.valueOf(ampMaxNRZetNRZT), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", "10", "0.5"};
                // POUR NRZT
                String[] argumentsNRZT_1ti_10_alpha_05 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "NRZT", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinNRZetNRZT), String.valueOf(ampMaxNRZetNRZT), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", "10", "0.5"};

                //POUR NRZ
                Simulateur simulateur = new Simulateur(argumentsNRZ_1ti_10_alpha_05);
                simulateur.execute();
                tempTabNRZ_1ti_10_alpha_05[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(argumentsNRZT_1ti_10_alpha_05);
                simulateur.execute();
                tempTabNRZT_1ti_10_alpha_05[i] = simulateur.calculTauxErreurBinaire();

                i++;
                System.out.println("Simulation " + j + "/" + nombreDeSimulations + " boucle SNR " + i);
            }
            System.out.println("numéro de la simulation " + j + "/" + nombreDeSimulations);
            // Ajout des tableaux temporaires dans les listes

            // POUR NRZ
            tabsNRZ_1ti_10_alpha_05.add(tempTabNRZ_1ti_10_alpha_05);

            // POUR NRZT
            tabsNRZT_1ti_10_alpha_05.add(tempTabNRZT_1ti_10_alpha_05);

        }

        // Calcul de la courbe théorique en utilisant la fonction d'erreur
        // complémentaire
        int i = 0;
        for (double snr = snrMin; snr <= snrMax; snr += 0.1) {
            tabTEBTheo[i] = (float) (0.5 * Erf.erfc(Math.sqrt(Math.pow(10, snr / 10))));
            tabSNR[i] = (float) snr;
            if (snr < 0.1 && snr > -0.1) {
                tabSNR[i] = 0;
            }
            i++;
        }

        // Calcul de la moyenne des taux d'erreur binaire pour chaque valeur de SNR
        for (int y = 0; y < size; y++) {
            // POUR NRZ
            float sommeNRZ_1ti_10_alpha_05 = 0;

            // POUR NRZT
            float sommeNRZT_1ti_10_alpha_05 = 0;

            for (float[] tab : tabsNRZ_1ti_10_alpha_05) {
                sommeNRZ_1ti_10_alpha_05 += tab[y];
            }

            // POUR NRZT
            for (float[] tab : tabsNRZT_1ti_10_alpha_05) {
                sommeNRZT_1ti_10_alpha_05 += tab[y];
            }

            // POUR NRZ
            finalTabNRZ_1ti_10_alpha_05[y] = sommeNRZ_1ti_10_alpha_05 / (float) tabsNRZ_1ti_10_alpha_05.size();

            // POUR NRZT
            finalTabNRZT_1ti_10_alpha_05[y] = sommeNRZT_1ti_10_alpha_05 / (float) tabsNRZT_1ti_10_alpha_05.size();

        }

        // POUR NRZ
        String[] finalTabNRZ_1ti_10_alpha_05String = new String[size];

        // POUR NRZT
        String[] finalTabNRZT_1ti_10_alpha_05String = new String[size];

        String[] tabTEBTheoString = new String[size];
        String[] tabSNRString = new String[size];

        for (int x = 0; x < size; x++) {

            finalTabNRZ_1ti_10_alpha_05String[x] = String.valueOf(finalTabNRZ_1ti_10_alpha_05[x]);

            finalTabNRZT_1ti_10_alpha_05String[x] = String.valueOf(finalTabNRZT_1ti_10_alpha_05[x]);

            tabTEBTheoString[x] = String.valueOf(tabTEBTheo[x]);
            tabSNRString[x] = String.valueOf(tabSNR[x]);
        }

        LinkedList<String[]> finalTabs = new LinkedList<>();
        finalTabs.add(tabSNRString);
        finalTabs.add(tabTEBTheoString);
        // POUR RZ
        // POUR NRZ
        finalTabs.add(finalTabNRZ_1ti_10_alpha_05String);

        // POUR NRZT
        finalTabs.add(finalTabNRZT_1ti_10_alpha_05String);

        // Créer un fichier CSV avec le nom de chaque tableau et les valeurs associées
        File csvFile = new File("tebEnFctSNRetTM_ss_egaliseur.csv");
        FileWriter writer = new FileWriter(csvFile);
//        writer.write("SNR par bit,TEB theorique,TEB RZ 1ti nbEch div 10 alpha 075, TEB RZ 1ti nbEch div 3 alpha 075, TEB RZ 1ti nbEch div 2 alpha 05, TEB RZ 1ti nbEch div 2 alpha 025, TEB RZ ti1 5 alpha 05, TEB RZ ti2 10 alpha 025, TEB RZ ti3 15 alpha 01, TEB RZ ti4 20 alpha 005, TEB RZ ti5 30 alpha 01,TEB NRZ 1ti nbEch div 10 alpha 075, TEB NRZ 1ti nbEch div 3 alpha 075, TEB NRZ 1ti nbEch div 2 alpha 05, TEB NRZ 1ti nbEch div 2 alpha 025, TEB NRZ ti1 5 alpha 05, TEB NRZ ti2 10 alpha 025, TEB NRZ ti3 15 alpha 01, TEB NRZ ti4 20 alpha 005, TEB NRZ ti5 30 alpha 01, TEB NRZT 1ti nbEch div 10 alpha 075, TEB NRZT 1ti nbEch div 3 alpha 075, TEB NRZT 1ti nbEch div 2 alpha 05, TEB NRZT 1ti nbEch div 2 alpha 025, TEB NRZT ti1 5 alpha 05, TEB NRZT ti2 10 alpha 025, TEB NRZT ti3 15 alpha 01, TEB NRZT ti4 20 alpha 005, TEB NRZT ti5 30 alpha 01\n");
        writer.write("SNR par bit,TEB theorique,TEB NRZ 1ti 10 alpha 05, TEB NRZT 1ti 10 alpha 05\n");
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
        writer.close();
    }

}

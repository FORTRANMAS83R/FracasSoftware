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
        int nbEch = 30;
        int ampMinRZ = 0;
        int ampMaxRZ = 5;
        float ampMinNRZetNRZT = -2.5f;
        float ampMaxNRZetNRZT = 2.5f;

        int nombreDeSimulations = 1;

        // POUR RZ
        LinkedList<float[]> tabsRZ_1ti_5_alpha_08 = new LinkedList<>();
        LinkedList<float[]> tabsRZ_2ti_10_alpha_05 = new LinkedList<>();
        LinkedList<float[]> tabsRZ_3ti_20_alpha_02 = new LinkedList<>();

        // POUR NRZ
        LinkedList<float[]> tabsNRZ_1ti_5_alpha_08 = new LinkedList<>();
        LinkedList<float[]> tabsNRZ_2ti_10_alpha_05 = new LinkedList<>();
        LinkedList<float[]> tabsNRZ_3ti_20_alpha_02 = new LinkedList<>();

        // POUR NRZT
        LinkedList<float[]> tabsNRZT_1ti_5_alpha_08 = new LinkedList<>();
        LinkedList<float[]> tabsNRZT_2ti_10_alpha_05 = new LinkedList<>();
        LinkedList<float[]> tabsNRZT_3ti_20_alpha_02 = new LinkedList<>();

        // Tableaux finaux
        // POUR RZ
        float[] finalTabRZ_1ti_5_alpha_08 = new float[size];
        float[] finalTabRZ_2ti_10_alpha_05 = new float[size];
        float[] finalTabRZ_3ti_20_alpha_02 = new float[size];

        // POUR NRZ
        float[] finalTabNRZ_1ti_5_alpha_08 = new float[size];
        float[] finalTabNRZ_2ti_10_alpha_05 = new float[size];
        float[] finalTabNRZ_3ti_20_alpha_02 = new float[size];

        // POUR NRZT
        float[] finalTabNRZT_1ti_5_alpha_08 = new float[size];
        float[] finalTabNRZT_2ti_10_alpha_05 = new float[size];
        float[] finalTabNRZT_3ti_20_alpha_02 = new float[size];

        float[] tabTEBTheo = new float[size];
        float[] tabSNR = new float[size];

        // Boucle pour effectuer plusieurs simulations
        for (int j = 0; j < nombreDeSimulations; j++) {

            // POUR RZ
            float[] tempTabRZ_1ti_5_alpha_08 = new float[size];
            float[] tempTabRZ_2ti_10_alpha_05 = new float[size];
            float[] tempTabRZ_3ti_20_alpha_02 = new float[size];

            // POUR NRZ
            float[] tempTabNRZ_1ti_5_alpha_08 = new float[size];
            float[] tempTabNRZ_2ti_10_alpha_05 = new float[size];
            float[] tempTabNRZ_3ti_20_alpha_02 = new float[size];

            // POUR NRZT
            float[] tempTabNRZT_1ti_5_alpha_08 = new float[size];
            float[] tempTabNRZT_2ti_10_alpha_05 = new float[size];
            float[] tempTabNRZT_3ti_20_alpha_02 = new float[size];


            int i = 0;
            // Boucle pour varier le SNR avec un pas de 0.1
            for (double snr = snrMin; snr <= snrMax; snr += 0.1) {
                // Définition des arguments des différentes simulations
                // POUR RZ
                String[] argumentsRZ_1ti_5_alpha_08 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "RZ", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinRZ), String.valueOf(ampMaxRZ), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", "5", "0.8"};
                String[] argumentsRZ_2ti_10_alpha_05 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "RZ", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinRZ), String.valueOf(ampMaxRZ), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", "5", "0.8", "10", "0.5"};
                String[] argumentsRZ_3ti_20_alpha_02 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "RZ", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinRZ), String.valueOf(ampMaxRZ), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", "5", "0.8", "10", "0.5", "20", "0.2"};

                // POUR NRZ
                String[] argumentsNRZ_1ti_5_alpha_08 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "NRZ", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinNRZetNRZT), String.valueOf(ampMaxNRZetNRZT), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", "5", "0.8"};
                String[] argumentsNRZ_2ti_10_alpha_05 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "NRZ", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinNRZetNRZT), String.valueOf(ampMaxNRZetNRZT), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", "5", "0.8", "10", "0.5"};
                String[] argumentsNRZ_3ti_20_alpha_02 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "NRZ", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinNRZetNRZT), String.valueOf(ampMaxNRZetNRZT), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", "5", "0.8", "10", "0.5", "20", "0.2"};

                // POUR NRZT
                String[] argumentsNRZT_1ti_5_alpha_08 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "NRZT", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinNRZetNRZT), String.valueOf(ampMaxNRZetNRZT), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", "5", "0.8"};
                String[] argumentsNRZT_2ti_10_alpha_05 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "NRZT", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinNRZetNRZT), String.valueOf(ampMaxNRZetNRZT), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", "5", "0.8", "10", "0.5"};
                String[] argumentsNRZT_3ti_20_alpha_02 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "NRZT", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinNRZetNRZT), String.valueOf(ampMaxNRZetNRZT), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", "5", "0.8", "10", "0.5", "20", "0.2"};

                //POUR RZ
                // Création et exécution du simulateur avec les paramètres spécifiés
                Simulateur simulateur = new Simulateur(argumentsRZ_1ti_5_alpha_08);
                simulateur.execute();
                // Calcul du taux d'erreur binaire et stockage dans le tableau temporaire
                tempTabRZ_1ti_5_alpha_08[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(argumentsRZ_2ti_10_alpha_05);
                simulateur.execute();
                tempTabRZ_2ti_10_alpha_05[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(argumentsRZ_3ti_20_alpha_02);
                simulateur.execute();
                tempTabRZ_3ti_20_alpha_02[i] = simulateur.calculTauxErreurBinaire();

                //POUR NRZ
                simulateur = new Simulateur(argumentsNRZ_1ti_5_alpha_08);
                simulateur.execute();
                tempTabNRZ_1ti_5_alpha_08[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(argumentsNRZ_2ti_10_alpha_05);
                simulateur.execute();
                tempTabNRZ_2ti_10_alpha_05[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(argumentsNRZ_3ti_20_alpha_02);
                simulateur.execute();
                tempTabNRZ_3ti_20_alpha_02[i] = simulateur.calculTauxErreurBinaire();

                // POUR NRZT
                simulateur = new Simulateur(argumentsNRZT_1ti_5_alpha_08);
                simulateur.execute();
                tempTabNRZT_1ti_5_alpha_08[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(argumentsNRZT_2ti_10_alpha_05);
                simulateur.execute();
                tempTabNRZT_2ti_10_alpha_05[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(argumentsNRZT_3ti_20_alpha_02);
                simulateur.execute();
                tempTabNRZT_3ti_20_alpha_02[i] = simulateur.calculTauxErreurBinaire();

                i++;
                System.out.println("Simulation " + j + "/" + nombreDeSimulations + " boucle SNR " + i);
            }
            System.out.println("numéro de la simulation " + j + "/" + nombreDeSimulations);
            // Ajout des tableaux temporaires dans les listes
            // Pour RZ
            tabsRZ_1ti_5_alpha_08.add(tempTabRZ_1ti_5_alpha_08);
            tabsRZ_2ti_10_alpha_05.add(tempTabRZ_2ti_10_alpha_05);
            tabsRZ_3ti_20_alpha_02.add(tempTabRZ_3ti_20_alpha_02);

            // POUR NRZ
            tabsNRZ_1ti_5_alpha_08.add(tempTabNRZ_1ti_5_alpha_08);
            tabsNRZ_2ti_10_alpha_05.add(tempTabNRZ_2ti_10_alpha_05);
            tabsNRZ_3ti_20_alpha_02.add(tempTabNRZ_3ti_20_alpha_02);

            // POUR NRZT
            tabsNRZT_1ti_5_alpha_08.add(tempTabNRZT_1ti_5_alpha_08);
            tabsNRZT_2ti_10_alpha_05.add(tempTabNRZT_2ti_10_alpha_05);
            tabsNRZT_3ti_20_alpha_02.add(tempTabNRZT_3ti_20_alpha_02);

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

            // POUR RZ
            float sommeRZ_1ti_5_alpha_08 = 0;
            float sommeRZ_2ti_10_alpha_05 = 0;
            float sommeRZ_3ti_20_alpha_02 = 0;

            // POUR NRZ
            float sommeNRZ_1ti_5_alpha_08 = 0;
            float sommeNRZ_2ti_10_alpha_05 = 0;
            float sommeNRZ_3ti_20_alpha_02 = 0;


            // POUR NRZT
            float sommeNRZT_1ti_5_alpha_08 = 0;
            float sommeNRZT_2ti_10_alpha_05 = 0;
            float sommeNRZT_3ti_20_alpha_02 = 0;

            // POUR RZ
            for (float[] tab : tabsRZ_1ti_5_alpha_08) {
                sommeRZ_1ti_5_alpha_08 += tab[y];
            }
            for (float[] tab : tabsRZ_2ti_10_alpha_05) {
                sommeRZ_2ti_10_alpha_05 += tab[y];
            }
            for (float[] tab : tabsRZ_3ti_20_alpha_02) {
                sommeRZ_3ti_20_alpha_02 += tab[y];
            }

            // POUR NRZ
            for (float[] tab : tabsNRZ_1ti_5_alpha_08) {
                sommeNRZ_1ti_5_alpha_08 += tab[y];
            }
            for (float[] tab : tabsNRZ_2ti_10_alpha_05) {
                sommeNRZ_2ti_10_alpha_05 += tab[y];
            }
            for (float[] tab : tabsNRZ_3ti_20_alpha_02) {
                sommeNRZ_3ti_20_alpha_02 += tab[y];
            }

            // POUR NRZT
            for (float[] tab : tabsNRZT_1ti_5_alpha_08) {
                sommeNRZT_1ti_5_alpha_08 += tab[y];
            }
            for (float[] tab : tabsNRZT_2ti_10_alpha_05) {
                sommeNRZT_2ti_10_alpha_05 += tab[y];
            }
            for (float[] tab : tabsNRZT_3ti_20_alpha_02) {
                sommeNRZT_3ti_20_alpha_02 += tab[y];
            }

            // Calcul de la moyenne
            // POUR RZ
            finalTabRZ_1ti_5_alpha_08[y] = sommeRZ_1ti_5_alpha_08 / (float) tabsRZ_1ti_5_alpha_08.size();
            finalTabRZ_2ti_10_alpha_05[y] = sommeRZ_2ti_10_alpha_05 / (float) tabsRZ_2ti_10_alpha_05.size();
            finalTabRZ_3ti_20_alpha_02[y] = sommeRZ_3ti_20_alpha_02 / (float) tabsRZ_3ti_20_alpha_02.size();

            // POUR NRZ
            finalTabNRZ_1ti_5_alpha_08[y] = sommeNRZ_1ti_5_alpha_08 / (float) tabsNRZ_1ti_5_alpha_08.size();
            finalTabNRZ_2ti_10_alpha_05[y] = sommeNRZ_2ti_10_alpha_05 / (float) tabsNRZ_2ti_10_alpha_05.size();
            finalTabNRZ_3ti_20_alpha_02[y] = sommeNRZ_3ti_20_alpha_02 / (float) tabsNRZ_3ti_20_alpha_02.size();

            // POUR NRZT
            finalTabNRZT_1ti_5_alpha_08[y] = sommeNRZT_1ti_5_alpha_08 / (float) tabsNRZT_1ti_5_alpha_08.size();
            finalTabNRZT_2ti_10_alpha_05[y] = sommeNRZT_2ti_10_alpha_05 / (float) tabsNRZT_2ti_10_alpha_05.size();
            finalTabNRZT_3ti_20_alpha_02[y] = sommeNRZT_3ti_20_alpha_02 / (float) tabsNRZT_3ti_20_alpha_02.size();

        }

        // POUR RZ
        String[] finalTabRZ_1ti_5_alpha_08String = new String[size];
        String[] finalTabRZ_2ti_10_alpha_05String = new String[size];
        String[] finalTabRZ_3ti_20_alpha_02String = new String[size];


        // POUR NRZ
        String[] finalTabNRZ_1ti_5_alpha_08String = new String[size];
        String[] finalTabNRZ_2ti_10_alpha_05String = new String[size];
        String[] finalTabNRZ_3ti_20_alpha_02String = new String[size];


        // POUR NRZT
        String[] finalTabNRZT_1ti_5_alpha_08String = new String[size];
        String[] finalTabNRZT_2ti_10_alpha_05String = new String[size];
        String[] finalTabNRZT_3ti_20_alpha_02String = new String[size];

        String[] tabTEBTheoString = new String[size];
        String[] tabSNRString = new String[size];

        for (int x = 0; x < size; x++) {
            finalTabRZ_1ti_5_alpha_08String[x] = String.valueOf(finalTabRZ_1ti_5_alpha_08[x]);
            finalTabRZ_2ti_10_alpha_05String[x] = String.valueOf(finalTabRZ_2ti_10_alpha_05[x]);
            finalTabRZ_3ti_20_alpha_02String[x] = String.valueOf(finalTabRZ_3ti_20_alpha_02[x]);

            finalTabNRZ_1ti_5_alpha_08String[x] = String.valueOf(finalTabNRZ_1ti_5_alpha_08[x]);
            finalTabNRZ_2ti_10_alpha_05String[x] = String.valueOf(finalTabNRZ_2ti_10_alpha_05[x]);
            finalTabNRZ_3ti_20_alpha_02String[x] = String.valueOf(finalTabNRZ_3ti_20_alpha_02[x]);

            finalTabNRZT_1ti_5_alpha_08String[x] = String.valueOf(finalTabNRZT_1ti_5_alpha_08[x]);
            finalTabNRZT_2ti_10_alpha_05String[x] = String.valueOf(finalTabNRZT_2ti_10_alpha_05[x]);
            finalTabNRZT_3ti_20_alpha_02String[x] = String.valueOf(finalTabNRZT_3ti_20_alpha_02[x]);

            tabTEBTheoString[x] = String.valueOf(tabTEBTheo[x]);
            tabSNRString[x] = String.valueOf(tabSNR[x]);
        }

        LinkedList<String[]> finalTabs = new LinkedList<>();
        finalTabs.add(tabSNRString);
        finalTabs.add(tabTEBTheoString);
        // POUR RZ
        finalTabs.add(finalTabRZ_1ti_5_alpha_08String);
        finalTabs.add(finalTabRZ_2ti_10_alpha_05String);
        finalTabs.add(finalTabRZ_3ti_20_alpha_02String);

        // POUR NRZ
        finalTabs.add(finalTabNRZ_1ti_5_alpha_08String);
        finalTabs.add(finalTabNRZ_2ti_10_alpha_05String);
        finalTabs.add(finalTabNRZ_3ti_20_alpha_02String);

        // POUR NRZT
        finalTabs.add(finalTabNRZT_1ti_5_alpha_08String);
        finalTabs.add(finalTabNRZT_2ti_10_alpha_05String);
        finalTabs.add(finalTabNRZT_3ti_20_alpha_02String);


        // Créer un fichier CSV avec le nom de chaque tableau et les valeurs associées
        File csvFile = new File("tebEnFctSNRetTM_egaliseur.csv");
        FileWriter writer = new FileWriter(csvFile);
//        writer.write("SNR par bit,TEB theorique,TEB RZ 1ti nbEch div 10 alpha 075, TEB RZ 1ti nbEch div 3 alpha 075, TEB RZ 1ti nbEch div 2 alpha 05, TEB RZ 1ti nbEch div 2 alpha 025, TEB RZ ti1 5 alpha 05, TEB RZ ti2 10 alpha 025, TEB RZ ti3 15 alpha 01, TEB RZ ti4 20 alpha 005, TEB RZ ti5 30 alpha 01,TEB NRZ 1ti nbEch div 10 alpha 075, TEB NRZ 1ti nbEch div 3 alpha 075, TEB NRZ 1ti nbEch div 2 alpha 05, TEB NRZ 1ti nbEch div 2 alpha 025, TEB NRZ ti1 5 alpha 05, TEB NRZ ti2 10 alpha 025, TEB NRZ ti3 15 alpha 01, TEB NRZ ti4 20 alpha 005, TEB NRZ ti5 30 alpha 01, TEB NRZT 1ti nbEch div 10 alpha 075, TEB NRZT 1ti nbEch div 3 alpha 075, TEB NRZT 1ti nbEch div 2 alpha 05, TEB NRZT 1ti nbEch div 2 alpha 025, TEB NRZT ti1 5 alpha 05, TEB NRZT ti2 10 alpha 025, TEB NRZT ti3 15 alpha 01, TEB NRZT ti4 20 alpha 005, TEB NRZT ti5 30 alpha 01\n");
        writer.write("SNR par bit,TEB theorique,TEB RZ 1ti 5 alpha 08, TEB RZ 2ti 10 alpha 05, TEB RZ 3ti 20 alpha 02, TEB NRZ 1ti 5 alpha 08, TEB NRZ 2ti 10 alpha 05, TEB NRZ 3ti 20 alpha 02, TEB NRZT 1ti 5 alpha 08, TEB NRZT 2ti 10 alpha 05, TEB NRZT 3ti 20 alpha 02\n");
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

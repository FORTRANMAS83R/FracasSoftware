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
        int tailleMessage = 1000;
        int nbEch = 6;
        int ampMinRZ = 0;
        int ampMaxRZ = 5;
        float ampMinNRZetNRZT = -2.5f;
        float ampMaxNRZetNRZT = 2.5f;

        int nombreDeSimulations = 10;


//        LinkedList<float[]> tabsNRZ = new LinkedList<>();
//        LinkedList<float[]> tabsRZ = new LinkedList<>();
//        LinkedList<float[]> tabsNRZT = new LinkedList<>();
//        float[] finalTabNRZ = new float[size];
//        float[] finalTabRZ = new float[size];
//        float[] finalTabNRZT = new float[size];
        float[] tabTEBTheo = new float[size];
        float[] tabTEBTheoCodeur = new float[size];
        float[] tabSNR = new float[size];


        LinkedList<float[]> tabsNRZ_nbEch6 = new LinkedList<>();
        LinkedList<float[]> tabsRZ_nbEch6 = new LinkedList<>();
        LinkedList<float[]> tabsNRZT_nbEch6 = new LinkedList<>();
        LinkedList<float[]> tabsNRZ_nbEch12 = new LinkedList<>();
        LinkedList<float[]> tabsRZ_nbEch12 = new LinkedList<>();
        LinkedList<float[]> tabsNRZT_nbEch12 = new LinkedList<>();
        LinkedList<float[]> tabsNRZ_nbEch24 = new LinkedList<>();
        LinkedList<float[]> tabsRZ_nbEch24 = new LinkedList<>();
        LinkedList<float[]> tabsNRZT_nbEch24 = new LinkedList<>();
        LinkedList<float[]> tabsNRZ_nbEch60 = new LinkedList<>();
        LinkedList<float[]> tabsRZ_nbEch60 = new LinkedList<>();
        LinkedList<float[]> tabsNRZT_nbEch60 = new LinkedList<>();

        float[] finalTabNRZ_nbEch6 = new float[size];
        float[] finalTabRZ_nbEch6 = new float[size];
        float[] finalTabNRZT_nbEch6 = new float[size];
        float[] finalTabNRZ_nbEch12 = new float[size];
        float[] finalTabRZ_nbEch12 = new float[size];
        float[] finalTabNRZT_nbEch12 = new float[size];
        float[] finalTabNRZ_nbEch24 = new float[size];
        float[] finalTabRZ_nbEch24 = new float[size];
        float[] finalTabNRZT_nbEch24 = new float[size];
        float[] finalTabNRZ_nbEch60 = new float[size];
        float[] finalTabRZ_nbEch60 = new float[size];
        float[] finalTabNRZT_nbEch60 = new float[size];

        // Boucle pour effectuer plusieurs simulations
        for (int j = 0; j < nombreDeSimulations; j++) {
//            float[] tempTabNRZ = new float[size];
//            float[] tempTabRZ = new float[size];
//            float[] tempTabNRZT = new float[size];
            float[] tempTabNRZ_nbEch6 = new float[size];
            float[] tempTabRZ_nbEch6 = new float[size];
            float[] tempTabNRZT_nbEch6 = new float[size];
            float[] tempTabNRZ_nbEch12 = new float[size];
            float[] tempTabRZ_nbEch12 = new float[size];
            float[] tempTabNRZT_nbEch12 = new float[size];
            float[] tempTabNRZ_nbEch24 = new float[size];
            float[] tempTabRZ_nbEch24 = new float[size];
            float[] tempTabNRZT_nbEch24 = new float[size];
            float[] tempTabNRZ_nbEch60 = new float[size];
            float[] tempTabRZ_nbEch60 = new float[size];
            float[] tempTabNRZT_nbEch60 = new float[size];


            int i = 0;
            // Boucle pour varier le SNR avec un pas de 0.1
            for (double snr = snrMin; snr <= snrMax; snr += 0.1) {
                String[] argumentsNRZT = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "NRZT", "-codeur", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinNRZetNRZT), String.valueOf(ampMaxNRZetNRZT), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000)};
                String[] argumentsNRZ = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "NRZ", "-codeur", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinNRZetNRZT), String.valueOf(ampMaxNRZetNRZT), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000)};
                String[] argumentsRZ = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "RZ", "-codeur", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinRZ), String.valueOf(ampMaxRZ), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000)};
                // Création et exécution du simulateur avec les paramètres spécifiés
//                Simulateur simulateur = new Simulateur(argumentsNRZT);
//                simulateur.execute();
//                // Calcul du taux d'erreur binaire et stockage dans le tableau temporaire
//                tempTabNRZT[i] = simulateur.calculTauxErreurBinaire();
//
//                simulateur = new Simulateur(argumentsRZ);
//                simulateur.execute();
//                tempTabRZ[i] = simulateur.calculTauxErreurBinaire();
//
//                simulateur = new Simulateur(argumentsNRZ);
//                simulateur.execute();
//                tempTabNRZ[i] = simulateur.calculTauxErreurBinaire();

                Simulateur simulateur = new Simulateur(formatArguments(argumentsNRZT, 1, nbEch));
                simulateur.execute();
                tempTabNRZT_nbEch6[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(formatArguments(argumentsNRZ, 1, nbEch));
                simulateur.execute();
                tempTabNRZ_nbEch6[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(formatArguments(argumentsRZ, 1, nbEch));
                simulateur.execute();
                tempTabRZ_nbEch6[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(formatArguments(argumentsNRZT, 2, nbEch));
                simulateur.execute();
                tempTabNRZT_nbEch12[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(formatArguments(argumentsNRZ, 2, nbEch));
                simulateur.execute();
                tempTabNRZ_nbEch12[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(formatArguments(argumentsRZ, 2, nbEch));
                simulateur.execute();
                tempTabRZ_nbEch12[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(formatArguments(argumentsNRZT, 4, nbEch));
                simulateur.execute();
                tempTabNRZT_nbEch24[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(formatArguments(argumentsNRZ, 4, nbEch));
                simulateur.execute();
                tempTabNRZ_nbEch24[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(formatArguments(argumentsRZ, 4, nbEch));
                simulateur.execute();
                tempTabRZ_nbEch24[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(formatArguments(argumentsNRZT, 10, nbEch));
                simulateur.execute();
                tempTabNRZT_nbEch60[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(formatArguments(argumentsNRZ, 10, nbEch));
                simulateur.execute();
                tempTabNRZ_nbEch60[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(formatArguments(argumentsRZ, 10, nbEch));
                simulateur.execute();
                tempTabRZ_nbEch60[i] = simulateur.calculTauxErreurBinaire();

                // Définition des arguments des différentes simulations

                i++;
                System.out.println("Simulation " + j + "/" + nombreDeSimulations + " boucle SNR " + i);
            }
            System.out.println("numéro de la simulation " + j + "/" + nombreDeSimulations);
            // Ajout des tableaux temporaires dans les listes

//            tabsNRZ.add(tempTabNRZ);
//            tabsRZ.add(tempTabRZ);
//            tabsNRZT.add(tempTabNRZT);
            tabsNRZ_nbEch6.add(tempTabNRZ_nbEch6);
            tabsRZ_nbEch6.add(tempTabRZ_nbEch6);
            tabsNRZT_nbEch6.add(tempTabNRZT_nbEch6);
            tabsNRZ_nbEch12.add(tempTabNRZ_nbEch12);
            tabsRZ_nbEch12.add(tempTabRZ_nbEch12);
            tabsNRZT_nbEch12.add(tempTabNRZT_nbEch12);
            tabsNRZ_nbEch24.add(tempTabNRZ_nbEch24);
            tabsRZ_nbEch24.add(tempTabRZ_nbEch24);
            tabsNRZT_nbEch24.add(tempTabNRZT_nbEch24);
            tabsNRZ_nbEch60.add(tempTabNRZ_nbEch60);
            tabsRZ_nbEch60.add(tempTabRZ_nbEch60);
            tabsNRZT_nbEch60.add(tempTabNRZT_nbEch60);
        }

        // Calcul de la courbe théorique en utilisant la fonction d'erreur
        // complémentaire
        int i = 0;
        for (double snr = snrMin; snr <= snrMax; snr += 0.1) {
//            tabTEBTheo[i] = (float) (0.5 * Erf.erfc(Math.sqrt(Math.pow(10, snr / 10))));
            tabTEBTheo[i] = (float) ((1 / 2.0f) * Erf.erfc(1 / (Math.sqrt(2) * Math.sqrt((double) 1 / (2 * Math.pow(10, snr / 10))))));
            tabTEBTheoCodeur[i] = (float) ((1 / 6.0f) * Erf.erfc(1 / (Math.sqrt(2) * Math.sqrt((double) 1 / (2 * Math.pow(10, snr / 10))))));
            tabSNR[i] = (float) snr;
            if (snr < 0.1 && snr > -0.1) {
                tabSNR[i] = 0;
            }
            i++;
        }

        // Calcul de la moyenne des taux d'erreur binaire pour chaque valeur de SNR
        for (int y = 0; y < size; y++) {
//            float sommeNRZ = 0;
//            float sommeRZ = 0;
//            float sommeNRZT = 0;
            float sommeNRZ_nbEch6 = 0;
            float sommeRZ_nbEch6 = 0;
            float sommeNRZT_nbEch6 = 0;
            float sommeNRZ_nbEch12 = 0;
            float sommeRZ_nbEch12 = 0;
            float sommeNRZT_nbEch12 = 0;
            float sommeNRZ_nbEch24 = 0;
            float sommeRZ_nbEch24 = 0;
            float sommeNRZT_nbEch24 = 0;
            float sommeNRZ_nbEch60 = 0;
            float sommeRZ_nbEch60 = 0;
            float sommeNRZT_nbEch60 = 0;

//            for (float[] tab : tabsNRZ) {
//                sommeNRZ += tab[y];
//            }
//            for (float[] tab : tabsRZ) {
//                sommeRZ += tab[y];
//            }
//            for (float[] tab : tabsNRZT) {
//                sommeNRZT += tab[y];
//            }

            for (float[] tab : tabsNRZ_nbEch6) {
                sommeNRZ_nbEch6 += tab[y];
            }
            for (float[] tab : tabsRZ_nbEch6) {
                sommeRZ_nbEch6 += tab[y];
            }
            for (float[] tab : tabsNRZT_nbEch6) {
                sommeNRZT_nbEch6 += tab[y];
            }
            for (float[] tab : tabsNRZ_nbEch12) {
                sommeNRZ_nbEch12 += tab[y];
            }
            for (float[] tab : tabsRZ_nbEch12) {
                sommeRZ_nbEch12 += tab[y];
            }
            for (float[] tab : tabsNRZT_nbEch12) {
                sommeNRZT_nbEch12 += tab[y];
            }
            for (float[] tab : tabsNRZ_nbEch24) {
                sommeNRZ_nbEch24 += tab[y];
            }
            for (float[] tab : tabsRZ_nbEch24) {
                sommeRZ_nbEch24 += tab[y];
            }
            for (float[] tab : tabsNRZT_nbEch24) {
                sommeNRZT_nbEch24 += tab[y];
            }
            for (float[] tab : tabsNRZ_nbEch60) {
                sommeNRZ_nbEch60 += tab[y];
            }
            for (float[] tab : tabsRZ_nbEch60) {
                sommeRZ_nbEch60 += tab[y];
            }
            for (float[] tab : tabsNRZT_nbEch60) {
                sommeNRZT_nbEch60 += tab[y];
            }

//            finalTabNRZ[y] = sommeNRZ / (float) tabsNRZ.size();
//            finalTabRZ[y] = sommeRZ / (float) tabsRZ.size();
//            finalTabNRZT[y] = sommeNRZT / (float) tabsNRZT.size();

            finalTabNRZ_nbEch6[y] = sommeNRZ_nbEch6 / (float) tabsNRZ_nbEch6.size();
            finalTabRZ_nbEch6[y] = sommeRZ_nbEch6 / (float) tabsRZ_nbEch6.size();
            finalTabNRZT_nbEch6[y] = sommeNRZT_nbEch6 / (float) tabsNRZT_nbEch6.size();
            finalTabNRZ_nbEch12[y] = sommeNRZ_nbEch12 / (float) tabsNRZ_nbEch12.size();
            finalTabRZ_nbEch12[y] = sommeRZ_nbEch12 / (float) tabsRZ_nbEch12.size();
            finalTabNRZT_nbEch12[y] = sommeNRZT_nbEch12 / (float) tabsNRZT_nbEch12.size();
            finalTabNRZ_nbEch24[y] = sommeNRZ_nbEch24 / (float) tabsNRZ_nbEch24.size();
            finalTabRZ_nbEch24[y] = sommeRZ_nbEch24 / (float) tabsRZ_nbEch24.size();
            finalTabNRZT_nbEch24[y] = sommeNRZT_nbEch24 / (float) tabsNRZT_nbEch24.size();
            finalTabNRZ_nbEch60[y] = sommeNRZ_nbEch60 / (float) tabsNRZ_nbEch60.size();
            finalTabRZ_nbEch60[y] = sommeRZ_nbEch60 / (float) tabsRZ_nbEch60.size();
            finalTabNRZT_nbEch60[y] = sommeNRZT_nbEch60 / (float) tabsNRZT_nbEch60.size();

        }
        // Affichage des résultats finaux et création de la courbe
//        System.out.println(Arrays.toString(finalTabNRZ));
//        System.out.println(Arrays.toString(finalTabRZ));
//        System.out.println(Arrays.toString(finalTabNRZT));
//        System.out.println(Arrays.toString(tabTEBTheo));


//        String[] finalTabNRZString = new String[size];
//        String[] finalTabRZString = new String[size];
//        String[] finalTabNRZTString = new String[size];
        String[] finalTabNRZ_nbEch6String = new String[size];
        String[] finalTabRZ_nbEch6String = new String[size];
        String[] finalTabNRZT_nbEch6String = new String[size];
        String[] finalTabNRZ_nbEch12String = new String[size];
        String[] finalTabRZ_nbEch12String = new String[size];
        String[] finalTabNRZT_nbEch12String = new String[size];
        String[] finalTabNRZ_nbEch24String = new String[size];
        String[] finalTabRZ_nbEch24String = new String[size];
        String[] finalTabNRZT_nbEch24String = new String[size];
        String[] finalTabNRZ_nbEch60String = new String[size];
        String[] finalTabRZ_nbEch60String = new String[size];
        String[] finalTabNRZT_nbEch60String = new String[size];

        String[] tabTEBTheoString = new String[size];
        String[] tabTEBTheoCodeurString = new String[size];
        String[] tabSNRString = new String[size];

        for (int x = 0; x < size; x++) {
//            finalTabNRZString[x] = String.valueOf(finalTabNRZ[x]);
//            finalTabRZString[x] = String.valueOf(finalTabRZ[x]);
//            finalTabNRZTString[x] = String.valueOf(finalTabNRZT[x]);
            finalTabNRZ_nbEch6String[x] = String.valueOf(finalTabNRZ_nbEch6[x]);
            finalTabRZ_nbEch6String[x] = String.valueOf(finalTabRZ_nbEch6[x]);
            finalTabNRZT_nbEch6String[x] = String.valueOf(finalTabNRZT_nbEch6[x]);
            finalTabNRZ_nbEch12String[x] = String.valueOf(finalTabNRZ_nbEch12[x]);
            finalTabRZ_nbEch12String[x] = String.valueOf(finalTabRZ_nbEch12[x]);
            finalTabNRZT_nbEch12String[x] = String.valueOf(finalTabNRZT_nbEch12[x]);
            finalTabNRZ_nbEch24String[x] = String.valueOf(finalTabNRZ_nbEch24[x]);
            finalTabRZ_nbEch24String[x] = String.valueOf(finalTabRZ_nbEch24[x]);
            finalTabNRZT_nbEch24String[x] = String.valueOf(finalTabNRZT_nbEch24[x]);
            finalTabNRZ_nbEch60String[x] = String.valueOf(finalTabNRZ_nbEch60[x]);
            finalTabRZ_nbEch60String[x] = String.valueOf(finalTabRZ_nbEch60[x]);
            finalTabNRZT_nbEch60String[x] = String.valueOf(finalTabNRZT_nbEch60[x]);
            tabTEBTheoString[x] = String.valueOf(tabTEBTheo[x]);
            tabTEBTheoCodeurString[x] = String.valueOf(tabTEBTheoCodeur[x]);
            tabSNRString[x] = String.valueOf(tabSNR[x]);
        }

        LinkedList<String[]> finalTabs = new LinkedList<>();
        finalTabs.add(tabSNRString);
        finalTabs.add(tabTEBTheoString);
        finalTabs.add(tabTEBTheoCodeurString);
//        finalTabs.add(finalTabRZString);
//        finalTabs.add(finalTabNRZString);
//        finalTabs.add(finalTabNRZTString);
        finalTabs.add(finalTabRZ_nbEch6String);
        finalTabs.add(finalTabRZ_nbEch12String);
        finalTabs.add(finalTabRZ_nbEch24String);
        finalTabs.add(finalTabRZ_nbEch60String);
        finalTabs.add(finalTabNRZ_nbEch6String);
        finalTabs.add(finalTabNRZ_nbEch12String);
        finalTabs.add(finalTabNRZ_nbEch24String);
        finalTabs.add(finalTabNRZ_nbEch60String);
        finalTabs.add(finalTabNRZT_nbEch6String);
        finalTabs.add(finalTabNRZT_nbEch12String);
        finalTabs.add(finalTabNRZT_nbEch24String);
        finalTabs.add(finalTabNRZT_nbEch60String);

        // Créer un fichier CSV avec le nom de chaque tableau et les valeurs associées
        File csvFile = new File("tebEnFctSNR_nbEch.csv");
        FileWriter writer = new FileWriter(csvFile);
        writer.write("SNR par bit,TEB theorique,TEB Theorique codeur,TEB RZ nbEch6,TEB RZ nbEch12,TEB RZ nbEch24,TEB RZ nbEch60,TEB NRZ nbEch6,TEB NRZ nbEch12,TEB NRZ nbEch24,TEB NRZ nbEch60,TEB NRZT nbEch6,TEB NRZT nbEch12,TEB NRZT nbEch24,TEB NRZT nbEch60\n");
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

    public static String[] formatArguments(String[] arguments, int multiple, int nbEch) {
        arguments[6] = String.valueOf(nbEch * multiple);
        return arguments;
    }

}

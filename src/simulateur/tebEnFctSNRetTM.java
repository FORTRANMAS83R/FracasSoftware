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

        int nombreDeSimulations = 10;

        // POUR RZ
        LinkedList<float[]> tabsRZ_1ti_nbEch_div_10_alpha_075 = new LinkedList<>();
        LinkedList<float[]> tabsRZ_1ti_nbEch_div_3_alpha_075 = new LinkedList<>();
        LinkedList<float[]> tabsRZ_1ti_nbEch_div_2_alpha_05 = new LinkedList<>();
        LinkedList<float[]> tabsRZ_1ti_nbEch_div_2_alpha_025 = new LinkedList<>();

        // Pour nbEch = 30
        LinkedList<float[]> tabsRZ_ti1_of_5_alpha_05 = new LinkedList<>();
        LinkedList<float[]> tabsRZ_ti2_of_10_alpha_025 = new LinkedList<>();
        LinkedList<float[]> tabsRZ_ti3_of_15_alpha_01 = new LinkedList<>();
        LinkedList<float[]> tabsRZ_ti4_of_20_alpha_005 = new LinkedList<>();
        LinkedList<float[]> tabsRZ_ti5_of_30_alpha_01 = new LinkedList<>();

        // POUR NRZ
        LinkedList<float[]> tabsNRZ_1ti_nbEch_div_10_alpha_075 = new LinkedList<>();
        LinkedList<float[]> tabsNRZ_1ti_nbEch_div_3_alpha_075 = new LinkedList<>();
        LinkedList<float[]> tabsNRZ_1ti_nbEch_div_2_alpha_05 = new LinkedList<>();
        LinkedList<float[]> tabsNRZ_1ti_nbEch_div_2_alpha_025 = new LinkedList<>();

        // Pour nbEch = 30
        LinkedList<float[]> tabsNRZ_ti1_of_5_alpha_05 = new LinkedList<>();
        LinkedList<float[]> tabsNRZ_ti2_of_10_alpha_025 = new LinkedList<>();
        LinkedList<float[]> tabsNRZ_ti3_of_15_alpha_01 = new LinkedList<>();
        LinkedList<float[]> tabsNRZ_ti4_of_20_alpha_005 = new LinkedList<>();
        LinkedList<float[]> tabsNRZ_ti5_of_30_alpha_01 = new LinkedList<>();

        // POUR NRZT
        LinkedList<float[]> tabsNRZT_1ti_nbEch_div_10_alpha_075 = new LinkedList<>();
        LinkedList<float[]> tabsNRZT_1ti_nbEch_div_3_alpha_075 = new LinkedList<>();
        LinkedList<float[]> tabsNRZT_1ti_nbEch_div_2_alpha_05 = new LinkedList<>();
        LinkedList<float[]> tabsNRZT_1ti_nbEch_div_2_alpha_025 = new LinkedList<>();

        // Pour nbEch = 30
        LinkedList<float[]> tabsNRZT_ti1_of_5_alpha_05 = new LinkedList<>();
        LinkedList<float[]> tabsNRZT_ti2_of_10_alpha_025 = new LinkedList<>();
        LinkedList<float[]> tabsNRZT_ti3_of_15_alpha_01 = new LinkedList<>();
        LinkedList<float[]> tabsNRZT_ti4_of_20_alpha_005 = new LinkedList<>();
        LinkedList<float[]> tabsNRZT_ti5_of_30_alpha_01 = new LinkedList<>();

        // Tableaux finaux
        // POUR RZ
        float[] finalTabRZ_1ti_nbEch_div_10_alpha_075 = new float[size];
        float[] finalTabRZ_1ti_nbEch_div_3_alpha_075 = new float[size];
        float[] finalTabRZ_1ti_nbEch_div_2_alpha_05 = new float[size];
        float[] finalTabRZ_1ti_nbEch_div_2_alpha_025 = new float[size];
        float[] finalTabRZ_ti1_of_5_alpha_05 = new float[size];
        float[] finalTabRZ_ti2_of_10_alpha_025 = new float[size];
        float[] finalTabRZ_ti3_of_15_alpha_01 = new float[size];
        float[] finalTabRZ_ti4_of_20_alpha_005 = new float[size];
        float[] finalTabRZ_ti5_of_30_alpha_01 = new float[size];

        // POUR NRZ
        float[] finalTabNRZ_1ti_nbEch_div_10_alpha_075 = new float[size];
        float[] finalTabNRZ_1ti_nbEch_div_3_alpha_075 = new float[size];
        float[] finalTabNRZ_1ti_nbEch_div_2_alpha_05 = new float[size];
        float[] finalTabNRZ_1ti_nbEch_div_2_alpha_025 = new float[size];
        float[] finalTabNRZ_ti1_of_5_alpha_05 = new float[size];
        float[] finalTabNRZ_ti2_of_10_alpha_025 = new float[size];
        float[] finalTabNRZ_ti3_of_15_alpha_01 = new float[size];
        float[] finalTabNRZ_ti4_of_20_alpha_005 = new float[size];
        float[] finalTabNRZ_ti5_of_30_alpha_01 = new float[size];

        // POUR NRZT
        float[] finalTabNRZT_1ti_nbEch_div_10_alpha_075 = new float[size];
        float[] finalTabNRZT_1ti_nbEch_div_3_alpha_075 = new float[size];
        float[] finalTabNRZT_1ti_nbEch_div_2_alpha_05 = new float[size];
        float[] finalTabNRZT_1ti_nbEch_div_2_alpha_025 = new float[size];
        float[] finalTabNRZT_ti1_of_5_alpha_05 = new float[size];
        float[] finalTabNRZT_ti2_of_10_alpha_025 = new float[size];
        float[] finalTabNRZT_ti3_of_15_alpha_01 = new float[size];
        float[] finalTabNRZT_ti4_of_20_alpha_005 = new float[size];
        float[] finalTabNRZT_ti5_of_30_alpha_01 = new float[size];

//		LinkedList<float[]> tabsNRZ = new LinkedList<>();
//		LinkedList<float[]> tabsRZ = new LinkedList<>();
//		LinkedList<float[]> tabsNRZT = new LinkedList<>();
//		float[] finalTabNRZ = new float[size];
//		float[] finalTabRZ = new float[size];
//		float[] finalTabNRZT = new float[size];
        float[] tabTEBTheo = new float[size];
        float[] tabSNR = new float[size];

        // Boucle pour effectuer plusieurs simulations
        for (int j = 0; j < nombreDeSimulations; j++) {
//			float[] tempTabNRZ = new float[size];
//			float[] tempTabRZ = new float[size];
//			float[] tempTabNRZT = new float[size];

            // POUR RZ
            float[] tempTabRZ_1ti_nbEch_div_10_alpha_075 = new float[size];
            float[] tempTabRZ_1ti_nbEch_div_3_alpha_075 = new float[size];
            float[] tempTabRZ_1ti_nbEch_div_2_alpha_05 = new float[size];
            float[] tempTabRZ_1ti_nbEch_div_2_alpha_025 = new float[size];
            float[] tempTabRZ_ti1_of_5_alpha_05 = new float[size];
            float[] tempTabRZ_ti2_of_10_alpha_025 = new float[size];
            float[] tempTabRZ_ti3_of_15_alpha_01 = new float[size];
            float[] tempTabRZ_ti4_of_20_alpha_005 = new float[size];
            float[] tempTabRZ_ti5_of_30_alpha_01 = new float[size];

            // POUR NRZ
            float[] tempTabNRZ_1ti_nbEch_div_10_alpha_075 = new float[size];
            float[] tempTabNRZ_1ti_nbEch_div_3_alpha_075 = new float[size];
            float[] tempTabNRZ_1ti_nbEch_div_2_alpha_05 = new float[size];
            float[] tempTabNRZ_1ti_nbEch_div_2_alpha_025 = new float[size];
            float[] tempTabNRZ_ti1_of_5_alpha_05 = new float[size];
            float[] tempTabNRZ_ti2_of_10_alpha_025 = new float[size];
            float[] tempTabNRZ_ti3_of_15_alpha_01 = new float[size];
            float[] tempTabNRZ_ti4_of_20_alpha_005 = new float[size];
            float[] tempTabNRZ_ti5_of_30_alpha_01 = new float[size];

            // POUR NRZT
            float[] tempTabNRZT_1ti_nbEch_div_10_alpha_075 = new float[size];
            float[] tempTabNRZT_1ti_nbEch_div_3_alpha_075 = new float[size];
            float[] tempTabNRZT_1ti_nbEch_div_2_alpha_05 = new float[size];
            float[] tempTabNRZT_1ti_nbEch_div_2_alpha_025 = new float[size];
            float[] tempTabNRZT_ti1_of_5_alpha_05 = new float[size];
            float[] tempTabNRZT_ti2_of_10_alpha_025 = new float[size];
            float[] tempTabNRZT_ti3_of_15_alpha_01 = new float[size];
            float[] tempTabNRZT_ti4_of_20_alpha_005 = new float[size];
            float[] tempTabNRZT_ti5_of_30_alpha_01 = new float[size];

            int i = 0;
            // Boucle pour varier le SNR avec un pas de 0.1
            for (double snr = snrMin; snr <= snrMax; snr += 0.1) {
//                String[] argumentsNRZT = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "NRZT", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinNRZetNRZT), String.valueOf(ampMaxNRZetNRZT), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000)};
//                String[] argumentsNRZ = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "NRZ", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinNRZetNRZT), String.valueOf(ampMaxNRZetNRZT), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000)};
//                String[] argumentsRZ = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "RZ", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinRZ), String.valueOf(ampMaxRZ), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000)};
//                // Création et exécution du simulateur avec les paramètres spécifiés
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

                // Définition des arguments des différentes simulations
                // POUR RZ
                String[] argumentsRZ_1ti_nbEch_div_10_alpha_075 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "RZ", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinRZ), String.valueOf(ampMaxRZ), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", String.valueOf(nbEch / 10), "0.75"};
                String[] argumentsRZ_1ti_nbEch_div_3_alpha_075 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "RZ", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinRZ), String.valueOf(ampMaxRZ), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", String.valueOf(nbEch / 3), "0.75"};
                String[] argumentsRZ_1ti_nbEch_div_2_alpha_05 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "RZ", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinRZ), String.valueOf(ampMaxRZ), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", String.valueOf(nbEch / 2), "0.5"};
                String[] argumentsRZ_1ti_nbEch_div_2_alpha_025 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "RZ", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinRZ), String.valueOf(ampMaxRZ), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", String.valueOf(nbEch / 2), "0.25"};
                String[] argumentsRZ_ti1_of_5_alpha_05 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "RZ", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinRZ), String.valueOf(ampMaxRZ), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", "5", "0.5"};
                String[] argumentsRZ_ti2_of_10_alpha_025 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "RZ", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinRZ), String.valueOf(ampMaxRZ), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", "5", "0.5", "10", "0.25"};
                String[] argumentsRZ_ti3_of_15_alpha_01 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "RZ", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinRZ), String.valueOf(ampMaxRZ), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", "5", "0.5", "10", "0.25", "15", "0.1"};
                String[] argumentsRZ_ti4_of_20_alpha_005 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "RZ", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinRZ), String.valueOf(ampMaxRZ), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", "5", "0.5", "10", "0.25", "15", "0.1", "20", "0.05"};
                String[] argumentsRZ_ti5_of_30_alpha_01 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "RZ", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinRZ), String.valueOf(ampMaxRZ), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", "5", "0.5", "10", "0.25", "15", "0.1", "20", "0.05", "30", "0.1"};

                // POUR NRZ
                String[] argumentsNRZ_1ti_nbEch_div_10_alpha_075 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "NRZ", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinNRZetNRZT), String.valueOf(ampMaxNRZetNRZT), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", String.valueOf(nbEch / 10), "0.75"};
                String[] argumentsNRZ_1ti_nbEch_div_3_alpha_075 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "NRZ", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinNRZetNRZT), String.valueOf(ampMaxNRZetNRZT), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", String.valueOf(nbEch / 3), "0.75"};
                String[] argumentsNRZ_1ti_nbEch_div_2_alpha_05 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "NRZ", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinNRZetNRZT), String.valueOf(ampMaxNRZetNRZT), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", String.valueOf(nbEch / 2), "0.5"};
                String[] argumentsNRZ_1ti_nbEch_div_2_alpha_025 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "NRZ", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinNRZetNRZT), String.valueOf(ampMaxNRZetNRZT), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", String.valueOf(nbEch / 2), "0.25"};
                String[] argumentsNRZ_ti1_of_5_alpha_05 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "NRZ", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinNRZetNRZT), String.valueOf(ampMaxNRZetNRZT), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", "5", "0.5"};
                String[] argumentsNRZ_ti2_of_10_alpha_025 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "NRZ", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinNRZetNRZT), String.valueOf(ampMaxNRZetNRZT), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", "5", "0.5", "10", "0.25"};
                String[] argumentsNRZ_ti3_of_15_alpha_01 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "NRZ", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinNRZetNRZT), String.valueOf(ampMaxNRZetNRZT), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", "5", "0.5", "10", "0.25", "15", "0.1"};
                String[] argumentsNRZ_ti4_of_20_alpha_005 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "NRZ", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinNRZetNRZT), String.valueOf(ampMaxNRZetNRZT), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", "5", "0.5", "10", "0.25", "15", "0.1", "20", "0.05"};
                String[] argumentsNRZ_ti5_of_30_alpha_01 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "NRZ", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinNRZetNRZT), String.valueOf(ampMaxNRZetNRZT), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", "5", "0.5", "10", "0.25", "15", "0.1", "20", "0.05", "30", "0.1"};
                // POUR NRZT
                String[] argumentsNRZT_1ti_nbEch_div_10_alpha_075 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "NRZT", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinNRZetNRZT), String.valueOf(ampMaxNRZetNRZT), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", String.valueOf(nbEch / 10), "0.75"};
                String[] argumentsNRZT_1ti_nbEch_div_3_alpha_075 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "NRZT", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinNRZetNRZT), String.valueOf(ampMaxNRZetNRZT), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", String.valueOf(nbEch / 3), "0.75"};
                String[] argumentsNRZT_1ti_nbEch_div_2_alpha_05 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "NRZT", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinNRZetNRZT), String.valueOf(ampMaxNRZetNRZT), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", String.valueOf(nbEch / 2), "0.5"};
                String[] argumentsNRZT_1ti_nbEch_div_2_alpha_025 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "NRZT", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinNRZetNRZT), String.valueOf(ampMaxNRZetNRZT), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", String.valueOf(nbEch / 2), "0.25"};
                String[] argumentsNRZT_ti1_of_5_alpha_05 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "NRZT", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinNRZetNRZT), String.valueOf(ampMaxNRZetNRZT), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", "5", "0.5"};
                String[] argumentsNRZT_ti2_of_10_alpha_025 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "NRZT", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinNRZetNRZT), String.valueOf(ampMaxNRZetNRZT), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", "5", "0.5", "10", "0.25"};
                String[] argumentsNRZT_ti3_of_15_alpha_01 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "NRZT", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinNRZetNRZT), String.valueOf(ampMaxNRZetNRZT), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", "5", "0.5", "10", "0.25", "15", "0.1"};
                String[] argumentsNRZT_ti4_of_20_alpha_005 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "NRZT", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinNRZetNRZT), String.valueOf(ampMaxNRZetNRZT), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", "5", "0.5", "10", "0.25", "15", "0.1", "20", "0.05"};
                String[] argumentsNRZT_ti5_of_30_alpha_01 = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "NRZT", "-nbEch", String.valueOf(nbEch), "-ampl", String.valueOf(ampMinNRZetNRZT), String.valueOf(ampMaxNRZetNRZT), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000), "-ti", "5", "0.5", "10", "0.25", "15", "0.1", "20", "0.05", "30", "0.1"};

                //POUR RZ
                // Création et exécution du simulateur avec les paramètres spécifiés
                Simulateur simulateur = new Simulateur(argumentsRZ_1ti_nbEch_div_10_alpha_075);
                simulateur.execute();
                // Calcul du taux d'erreur binaire et stockage dans le tableau temporaire
                tempTabRZ_1ti_nbEch_div_10_alpha_075[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(argumentsRZ_1ti_nbEch_div_3_alpha_075);
                simulateur.execute();
                tempTabRZ_1ti_nbEch_div_3_alpha_075[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(argumentsRZ_1ti_nbEch_div_2_alpha_05);
                simulateur.execute();
                tempTabRZ_1ti_nbEch_div_2_alpha_05[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(argumentsRZ_1ti_nbEch_div_2_alpha_025);
                simulateur.execute();
                tempTabRZ_1ti_nbEch_div_2_alpha_025[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(argumentsRZ_ti1_of_5_alpha_05);
                simulateur.execute();
                tempTabRZ_ti1_of_5_alpha_05[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(argumentsRZ_ti2_of_10_alpha_025);
                simulateur.execute();
                tempTabRZ_ti2_of_10_alpha_025[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(argumentsRZ_ti3_of_15_alpha_01);
                simulateur.execute();
                tempTabRZ_ti3_of_15_alpha_01[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(argumentsRZ_ti4_of_20_alpha_005);
                simulateur.execute();
                tempTabRZ_ti4_of_20_alpha_005[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(argumentsRZ_ti5_of_30_alpha_01);
                simulateur.execute();
                tempTabRZ_ti5_of_30_alpha_01[i] = simulateur.calculTauxErreurBinaire();

                //POUR NRZ
                simulateur = new Simulateur(argumentsNRZ_1ti_nbEch_div_10_alpha_075);
                simulateur.execute();
                tempTabNRZ_1ti_nbEch_div_10_alpha_075[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(argumentsNRZ_1ti_nbEch_div_3_alpha_075);
                simulateur.execute();
                tempTabNRZ_1ti_nbEch_div_3_alpha_075[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(argumentsNRZ_1ti_nbEch_div_2_alpha_05);
                simulateur.execute();
                tempTabNRZ_1ti_nbEch_div_2_alpha_05[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(argumentsNRZ_1ti_nbEch_div_2_alpha_025);
                simulateur.execute();
                tempTabNRZ_1ti_nbEch_div_2_alpha_025[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(argumentsNRZ_ti1_of_5_alpha_05);
                simulateur.execute();
                tempTabNRZ_ti1_of_5_alpha_05[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(argumentsNRZ_ti2_of_10_alpha_025);
                simulateur.execute();
                tempTabNRZ_ti2_of_10_alpha_025[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(argumentsNRZ_ti3_of_15_alpha_01);
                simulateur.execute();
                tempTabNRZ_ti3_of_15_alpha_01[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(argumentsNRZ_ti4_of_20_alpha_005);
                simulateur.execute();
                tempTabNRZ_ti4_of_20_alpha_005[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(argumentsNRZ_ti5_of_30_alpha_01);
                simulateur.execute();
                tempTabNRZ_ti5_of_30_alpha_01[i] = simulateur.calculTauxErreurBinaire();

                // POUR NRZT
                simulateur = new Simulateur(argumentsNRZT_1ti_nbEch_div_10_alpha_075);
                simulateur.execute();
                tempTabNRZT_1ti_nbEch_div_10_alpha_075[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(argumentsNRZT_1ti_nbEch_div_3_alpha_075);
                simulateur.execute();
                tempTabNRZT_1ti_nbEch_div_3_alpha_075[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(argumentsNRZT_1ti_nbEch_div_2_alpha_05);
                simulateur.execute();
                tempTabNRZT_1ti_nbEch_div_2_alpha_05[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(argumentsNRZT_1ti_nbEch_div_2_alpha_025);
                simulateur.execute();
                tempTabNRZT_1ti_nbEch_div_2_alpha_025[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(argumentsNRZT_ti1_of_5_alpha_05);
                simulateur.execute();
                tempTabNRZT_ti1_of_5_alpha_05[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(argumentsNRZT_ti2_of_10_alpha_025);
                simulateur.execute();
                tempTabNRZT_ti2_of_10_alpha_025[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(argumentsNRZT_ti3_of_15_alpha_01);
                simulateur.execute();
                tempTabNRZT_ti3_of_15_alpha_01[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(argumentsNRZT_ti4_of_20_alpha_005);
                simulateur.execute();
                tempTabNRZT_ti4_of_20_alpha_005[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(argumentsNRZT_ti5_of_30_alpha_01);
                simulateur.execute();
                tempTabNRZT_ti5_of_30_alpha_01[i] = simulateur.calculTauxErreurBinaire();


                i++;
                System.out.println("Simulation " + j + "/" + nombreDeSimulations + " boucle SNR " + i);
            }
            System.out.println("numéro de la simulation " + j + "/" + nombreDeSimulations);
            // Ajout des tableaux temporaires dans les listes
            // Pour RZ
            tabsRZ_1ti_nbEch_div_10_alpha_075.add(tempTabRZ_1ti_nbEch_div_10_alpha_075);
            tabsRZ_1ti_nbEch_div_3_alpha_075.add(tempTabRZ_1ti_nbEch_div_3_alpha_075);
            tabsRZ_1ti_nbEch_div_2_alpha_05.add(tempTabRZ_1ti_nbEch_div_2_alpha_05);
            tabsRZ_1ti_nbEch_div_2_alpha_025.add(tempTabRZ_1ti_nbEch_div_2_alpha_025);
            tabsRZ_ti1_of_5_alpha_05.add(tempTabRZ_ti1_of_5_alpha_05);
            tabsRZ_ti2_of_10_alpha_025.add(tempTabRZ_ti2_of_10_alpha_025);
            tabsRZ_ti3_of_15_alpha_01.add(tempTabRZ_ti3_of_15_alpha_01);
            tabsRZ_ti4_of_20_alpha_005.add(tempTabRZ_ti4_of_20_alpha_005);
            tabsRZ_ti5_of_30_alpha_01.add(tempTabRZ_ti5_of_30_alpha_01);

            // POUR NRZ
            tabsNRZ_1ti_nbEch_div_10_alpha_075.add(tempTabNRZ_1ti_nbEch_div_10_alpha_075);
            tabsNRZ_1ti_nbEch_div_3_alpha_075.add(tempTabNRZ_1ti_nbEch_div_3_alpha_075);
            tabsNRZ_1ti_nbEch_div_2_alpha_05.add(tempTabNRZ_1ti_nbEch_div_2_alpha_05);
            tabsNRZ_1ti_nbEch_div_2_alpha_025.add(tempTabNRZ_1ti_nbEch_div_2_alpha_025);
            tabsNRZ_ti1_of_5_alpha_05.add(tempTabNRZ_ti1_of_5_alpha_05);
            tabsNRZ_ti2_of_10_alpha_025.add(tempTabNRZ_ti2_of_10_alpha_025);
            tabsNRZ_ti3_of_15_alpha_01.add(tempTabNRZ_ti3_of_15_alpha_01);
            tabsNRZ_ti4_of_20_alpha_005.add(tempTabNRZ_ti4_of_20_alpha_005);
            tabsNRZ_ti5_of_30_alpha_01.add(tempTabNRZ_ti5_of_30_alpha_01);

            // POUR NRZT
            tabsNRZT_1ti_nbEch_div_10_alpha_075.add(tempTabNRZT_1ti_nbEch_div_10_alpha_075);
            tabsNRZT_1ti_nbEch_div_3_alpha_075.add(tempTabNRZT_1ti_nbEch_div_3_alpha_075);
            tabsNRZT_1ti_nbEch_div_2_alpha_05.add(tempTabNRZT_1ti_nbEch_div_2_alpha_05);
            tabsNRZT_1ti_nbEch_div_2_alpha_025.add(tempTabNRZT_1ti_nbEch_div_2_alpha_025);
            tabsNRZT_ti1_of_5_alpha_05.add(tempTabNRZT_ti1_of_5_alpha_05);
            tabsNRZT_ti2_of_10_alpha_025.add(tempTabNRZT_ti2_of_10_alpha_025);
            tabsNRZT_ti3_of_15_alpha_01.add(tempTabNRZT_ti3_of_15_alpha_01);
            tabsNRZT_ti4_of_20_alpha_005.add(tempTabNRZT_ti4_of_20_alpha_005);
            tabsNRZT_ti5_of_30_alpha_01.add(tempTabNRZT_ti5_of_30_alpha_01);

//            tabsNRZ.add(tempTabNRZ);
//            tabsRZ.add(tempTabRZ);
//            tabsNRZT.add(tempTabNRZT);
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
//            float sommeNRZ = 0;
//            float sommeRZ = 0;
//            float sommeNRZT = 0;

            // POUR RZ
            float sommeRZ_1ti_nbEch_div_10_alpha_075 = 0;
            float sommeRZ_1ti_nbEch_div_3_alpha_075 = 0;
            float sommeRZ_1ti_nbEch_div_2_alpha_05 = 0;
            float sommeRZ_1ti_nbEch_div_2_alpha_025 = 0;
            float sommeRZ_ti1_of_5_alpha_05 = 0;
            float sommeRZ_ti2_of_10_alpha_025 = 0;
            float sommeRZ_ti3_of_15_alpha_01 = 0;
            float sommeRZ_ti4_of_20_alpha_005 = 0;
            float sommeRZ_ti5_of_30_alpha_01 = 0;

            // POUR NRZ
            float sommeNRZ_1ti_nbEch_div_10_alpha_075 = 0;
            float sommeNRZ_1ti_nbEch_div_3_alpha_075 = 0;
            float sommeNRZ_1ti_nbEch_div_2_alpha_05 = 0;
            float sommeNRZ_1ti_nbEch_div_2_alpha_025 = 0;
            float sommeNRZ_ti1_of_5_alpha_05 = 0;
            float sommeNRZ_ti2_of_10_alpha_025 = 0;
            float sommeNRZ_ti3_of_15_alpha_01 = 0;
            float sommeNRZ_ti4_of_20_alpha_005 = 0;
            float sommeNRZ_ti5_of_30_alpha_01 = 0;

            // POUR NRZT
            float sommeNRZT_1ti_nbEch_div_10_alpha_075 = 0;
            float sommeNRZT_1ti_nbEch_div_3_alpha_075 = 0;
            float sommeNRZT_1ti_nbEch_div_2_alpha_05 = 0;
            float sommeNRZT_1ti_nbEch_div_2_alpha_025 = 0;
            float sommeNRZT_ti1_of_5_alpha_05 = 0;
            float sommeNRZT_ti2_of_10_alpha_025 = 0;
            float sommeNRZT_ti3_of_15_alpha_01 = 0;
            float sommeNRZT_ti4_of_20_alpha_005 = 0;
            float sommeNRZT_ti5_of_30_alpha_01 = 0;

            // POUR RZ
            for (float[] tab : tabsRZ_1ti_nbEch_div_10_alpha_075) {
                sommeRZ_1ti_nbEch_div_10_alpha_075 += tab[y];
            }
            for (float[] tab : tabsRZ_1ti_nbEch_div_3_alpha_075) {
                sommeRZ_1ti_nbEch_div_3_alpha_075 += tab[y];
            }
            for (float[] tab : tabsRZ_1ti_nbEch_div_2_alpha_05) {
                sommeRZ_1ti_nbEch_div_2_alpha_05 += tab[y];
            }
            for (float[] tab : tabsRZ_1ti_nbEch_div_2_alpha_025) {
                sommeRZ_1ti_nbEch_div_2_alpha_025 += tab[y];
            }
            for (float[] tab : tabsRZ_ti1_of_5_alpha_05) {
                sommeRZ_ti1_of_5_alpha_05 += tab[y];
            }
            for (float[] tab : tabsRZ_ti2_of_10_alpha_025) {
                sommeRZ_ti2_of_10_alpha_025 += tab[y];
            }
            for (float[] tab : tabsRZ_ti3_of_15_alpha_01) {
                sommeRZ_ti3_of_15_alpha_01 += tab[y];
            }
            for (float[] tab : tabsRZ_ti4_of_20_alpha_005) {
                sommeRZ_ti4_of_20_alpha_005 += tab[y];
            }
            for (float[] tab : tabsRZ_ti5_of_30_alpha_01) {
                sommeRZ_ti5_of_30_alpha_01 += tab[y];
            }

            // POUR NRZ
            for (float[] tab : tabsNRZ_1ti_nbEch_div_10_alpha_075) {
                sommeNRZ_1ti_nbEch_div_10_alpha_075 += tab[y];
            }
            for (float[] tab : tabsNRZ_1ti_nbEch_div_3_alpha_075) {
                sommeNRZ_1ti_nbEch_div_3_alpha_075 += tab[y];
            }
            for (float[] tab : tabsNRZ_1ti_nbEch_div_2_alpha_05) {
                sommeNRZ_1ti_nbEch_div_2_alpha_05 += tab[y];
            }
            for (float[] tab : tabsNRZ_1ti_nbEch_div_2_alpha_025) {
                sommeNRZ_1ti_nbEch_div_2_alpha_025 += tab[y];
            }
            for (float[] tab : tabsNRZ_ti1_of_5_alpha_05) {
                sommeNRZ_ti1_of_5_alpha_05 += tab[y];
            }
            for (float[] tab : tabsNRZ_ti2_of_10_alpha_025) {
                sommeNRZ_ti2_of_10_alpha_025 += tab[y];
            }
            for (float[] tab : tabsNRZ_ti3_of_15_alpha_01) {
                sommeNRZ_ti3_of_15_alpha_01 += tab[y];
            }
            for (float[] tab : tabsNRZ_ti4_of_20_alpha_005) {
                sommeNRZ_ti4_of_20_alpha_005 += tab[y];
            }
            for (float[] tab : tabsNRZ_ti5_of_30_alpha_01) {
                sommeNRZ_ti5_of_30_alpha_01 += tab[y];
            }

            // POUR NRZT
            for (float[] tab : tabsNRZT_1ti_nbEch_div_10_alpha_075) {
                sommeNRZT_1ti_nbEch_div_10_alpha_075 += tab[y];
            }
            for (float[] tab : tabsNRZT_1ti_nbEch_div_3_alpha_075) {
                sommeNRZT_1ti_nbEch_div_3_alpha_075 += tab[y];
            }
            for (float[] tab : tabsNRZT_1ti_nbEch_div_2_alpha_05) {
                sommeNRZT_1ti_nbEch_div_2_alpha_05 += tab[y];
            }
            for (float[] tab : tabsNRZT_1ti_nbEch_div_2_alpha_025) {
                sommeNRZT_1ti_nbEch_div_2_alpha_025 += tab[y];
            }
            for (float[] tab : tabsNRZT_ti1_of_5_alpha_05) {
                sommeNRZT_ti1_of_5_alpha_05 += tab[y];
            }
            for (float[] tab : tabsNRZT_ti2_of_10_alpha_025) {
                sommeNRZT_ti2_of_10_alpha_025 += tab[y];
            }
            for (float[] tab : tabsNRZT_ti3_of_15_alpha_01) {
                sommeNRZT_ti3_of_15_alpha_01 += tab[y];
            }
            for (float[] tab : tabsNRZT_ti4_of_20_alpha_005) {
                sommeNRZT_ti4_of_20_alpha_005 += tab[y];
            }
            for (float[] tab : tabsNRZT_ti5_of_30_alpha_01) {
                sommeNRZT_ti5_of_30_alpha_01 += tab[y];
            }

//            for (float[] tab : tabsNRZ) {
//                sommeNRZ += tab[y];
//            }
//            for (float[] tab : tabsRZ) {
//                sommeRZ += tab[y];
//            }
//            for (float[] tab : tabsNRZT) {
//                sommeNRZT += tab[y];
//            }

            // Calcul de la moyenne
            // POUR RZ
            finalTabRZ_1ti_nbEch_div_10_alpha_075[y] = sommeRZ_1ti_nbEch_div_10_alpha_075 / (float) tabsRZ_1ti_nbEch_div_10_alpha_075.size();
            finalTabRZ_1ti_nbEch_div_3_alpha_075[y] = sommeRZ_1ti_nbEch_div_3_alpha_075 / (float) tabsRZ_1ti_nbEch_div_3_alpha_075.size();
            finalTabRZ_1ti_nbEch_div_2_alpha_05[y] = sommeRZ_1ti_nbEch_div_2_alpha_05 / (float) tabsRZ_1ti_nbEch_div_2_alpha_05.size();
            finalTabRZ_1ti_nbEch_div_2_alpha_025[y] = sommeRZ_1ti_nbEch_div_2_alpha_025 / (float) tabsRZ_1ti_nbEch_div_2_alpha_025.size();
            finalTabRZ_ti1_of_5_alpha_05[y] = sommeRZ_ti1_of_5_alpha_05 / (float) tabsRZ_ti1_of_5_alpha_05.size();
            finalTabRZ_ti2_of_10_alpha_025[y] = sommeRZ_ti2_of_10_alpha_025 / (float) tabsRZ_ti2_of_10_alpha_025.size();
            finalTabRZ_ti3_of_15_alpha_01[y] = sommeRZ_ti3_of_15_alpha_01 / (float) tabsRZ_ti3_of_15_alpha_01.size();
            finalTabRZ_ti4_of_20_alpha_005[y] = sommeRZ_ti4_of_20_alpha_005 / (float) tabsRZ_ti4_of_20_alpha_005.size();
            finalTabRZ_ti5_of_30_alpha_01[y] = sommeRZ_ti5_of_30_alpha_01 / (float) tabsRZ_ti5_of_30_alpha_01.size();

            // POUR NRZ
            finalTabNRZ_1ti_nbEch_div_10_alpha_075[y] = sommeNRZ_1ti_nbEch_div_10_alpha_075 / (float) tabsNRZ_1ti_nbEch_div_10_alpha_075.size();
            finalTabNRZ_1ti_nbEch_div_3_alpha_075[y] = sommeNRZ_1ti_nbEch_div_3_alpha_075 / (float) tabsNRZ_1ti_nbEch_div_3_alpha_075.size();
            finalTabNRZ_1ti_nbEch_div_2_alpha_05[y] = sommeNRZ_1ti_nbEch_div_2_alpha_05 / (float) tabsNRZ_1ti_nbEch_div_2_alpha_05.size();
            finalTabNRZ_1ti_nbEch_div_2_alpha_025[y] = sommeNRZ_1ti_nbEch_div_2_alpha_025 / (float) tabsNRZ_1ti_nbEch_div_2_alpha_025.size();
            finalTabNRZ_ti1_of_5_alpha_05[y] = sommeNRZ_ti1_of_5_alpha_05 / (float) tabsNRZ_ti1_of_5_alpha_05.size();
            finalTabNRZ_ti2_of_10_alpha_025[y] = sommeNRZ_ti2_of_10_alpha_025 / (float) tabsNRZ_ti2_of_10_alpha_025.size();
            finalTabNRZ_ti3_of_15_alpha_01[y] = sommeNRZ_ti3_of_15_alpha_01 / (float) tabsNRZ_ti3_of_15_alpha_01.size();
            finalTabNRZ_ti4_of_20_alpha_005[y] = sommeNRZ_ti4_of_20_alpha_005 / (float) tabsNRZ_ti4_of_20_alpha_005.size();
            finalTabNRZ_ti5_of_30_alpha_01[y] = sommeNRZ_ti5_of_30_alpha_01 / (float) tabsNRZ_ti5_of_30_alpha_01.size();

            // POUR NRZT
            finalTabNRZT_1ti_nbEch_div_10_alpha_075[y] = sommeNRZT_1ti_nbEch_div_10_alpha_075 / (float) tabsNRZT_1ti_nbEch_div_10_alpha_075.size();
            finalTabNRZT_1ti_nbEch_div_3_alpha_075[y] = sommeNRZT_1ti_nbEch_div_3_alpha_075 / (float) tabsNRZT_1ti_nbEch_div_3_alpha_075.size();
            finalTabNRZT_1ti_nbEch_div_2_alpha_05[y] = sommeNRZT_1ti_nbEch_div_2_alpha_05 / (float) tabsNRZT_1ti_nbEch_div_2_alpha_05.size();
            finalTabNRZT_1ti_nbEch_div_2_alpha_025[y] = sommeNRZT_1ti_nbEch_div_2_alpha_025 / (float) tabsNRZT_1ti_nbEch_div_2_alpha_025.size();
            finalTabNRZT_ti1_of_5_alpha_05[y] = sommeNRZT_ti1_of_5_alpha_05 / (float) tabsNRZT_ti1_of_5_alpha_05.size();
            finalTabNRZT_ti2_of_10_alpha_025[y] = sommeNRZT_ti2_of_10_alpha_025 / (float) tabsNRZT_ti2_of_10_alpha_025.size();
            finalTabNRZT_ti3_of_15_alpha_01[y] = sommeNRZT_ti3_of_15_alpha_01 / (float) tabsNRZT_ti3_of_15_alpha_01.size();
            finalTabNRZT_ti4_of_20_alpha_005[y] = sommeNRZT_ti4_of_20_alpha_005 / (float) tabsNRZT_ti4_of_20_alpha_005.size();
            finalTabNRZT_ti5_of_30_alpha_01[y] = sommeNRZT_ti5_of_30_alpha_01 / (float) tabsNRZT_ti5_of_30_alpha_01.size();



//            finalTabNRZ[y] = sommeNRZ / (float) tabsNRZ.size();
//            finalTabRZ[y] = sommeRZ / (float) tabsRZ.size();
//            finalTabNRZT[y] = sommeNRZT / (float) tabsNRZT.size();
        }
        // Affichage des résultats finaux et création de la courbe
//        System.out.println(Arrays.toString(finalTabNRZ));
//        System.out.println(Arrays.toString(finalTabRZ));
//        System.out.println(Arrays.toString(finalTabNRZT));
//        System.out.println(Arrays.toString(tabTEBTheo));

        // POUR RZ
        String[] finalTabRZ_1ti_nbEch_div_10_alpha_075String = new String[size];
        String[] finalTabRZ_1ti_nbEch_div_3_alpha_075String = new String[size];
        String[] finalTabRZ_1ti_nbEch_div_2_alpha_05String = new String[size];
        String[] finalTabRZ_1ti_nbEch_div_2_alpha_025String = new String[size];
        String[] finalTabRZ_ti1_of_5_alpha_05String = new String[size];
        String[] finalTabRZ_ti2_of_10_alpha_025String = new String[size];
        String[] finalTabRZ_ti3_of_15_alpha_01String = new String[size];
        String[] finalTabRZ_ti4_of_20_alpha_005String = new String[size];
        String[] finalTabRZ_ti5_of_30_alpha_01String = new String[size];

        // POUR NRZ
        String[] finalTabNRZ_1ti_nbEch_div_10_alpha_075String = new String[size];
        String[] finalTabNRZ_1ti_nbEch_div_3_alpha_075String = new String[size];
        String[] finalTabNRZ_1ti_nbEch_div_2_alpha_05String = new String[size];
        String[] finalTabNRZ_1ti_nbEch_div_2_alpha_025String = new String[size];
        String[] finalTabNRZ_ti1_of_5_alpha_05String = new String[size];
        String[] finalTabNRZ_ti2_of_10_alpha_025String = new String[size];
        String[] finalTabNRZ_ti3_of_15_alpha_01String = new String[size];
        String[] finalTabNRZ_ti4_of_20_alpha_005String = new String[size];
        String[] finalTabNRZ_ti5_of_30_alpha_01String = new String[size];

        // POUR NRZT
        String[] finalTabNRZT_1ti_nbEch_div_10_alpha_075String = new String[size];
        String[] finalTabNRZT_1ti_nbEch_div_3_alpha_075String = new String[size];
        String[] finalTabNRZT_1ti_nbEch_div_2_alpha_05String = new String[size];
        String[] finalTabNRZT_1ti_nbEch_div_2_alpha_025String = new String[size];
        String[] finalTabNRZT_ti1_of_5_alpha_05String = new String[size];
        String[] finalTabNRZT_ti2_of_10_alpha_025String = new String[size];
        String[] finalTabNRZT_ti3_of_15_alpha_01String = new String[size];
        String[] finalTabNRZT_ti4_of_20_alpha_005String = new String[size];
        String[] finalTabNRZT_ti5_of_30_alpha_01String = new String[size];


//        String[] finalTabNRZString = new String[size];
//        String[] finalTabRZString = new String[size];
//        String[] finalTabNRZTString = new String[size];
        String[] tabTEBTheoString = new String[size];
        String[] tabSNRString = new String[size];

        for (int x = 0; x < size; x++) {
            finalTabRZ_1ti_nbEch_div_10_alpha_075String[x] = String.valueOf(finalTabRZ_1ti_nbEch_div_10_alpha_075[x]);
            finalTabRZ_1ti_nbEch_div_3_alpha_075String[x] = String.valueOf(finalTabRZ_1ti_nbEch_div_3_alpha_075[x]);
            finalTabRZ_1ti_nbEch_div_2_alpha_05String[x] = String.valueOf(finalTabRZ_1ti_nbEch_div_2_alpha_05[x]);
            finalTabRZ_1ti_nbEch_div_2_alpha_025String[x] = String.valueOf(finalTabRZ_1ti_nbEch_div_2_alpha_025[x]);
            finalTabRZ_ti1_of_5_alpha_05String[x] = String.valueOf(finalTabRZ_ti1_of_5_alpha_05[x]);
            finalTabRZ_ti2_of_10_alpha_025String[x] = String.valueOf(finalTabRZ_ti2_of_10_alpha_025[x]);
            finalTabRZ_ti3_of_15_alpha_01String[x] = String.valueOf(finalTabRZ_ti3_of_15_alpha_01[x]);
            finalTabRZ_ti4_of_20_alpha_005String[x] = String.valueOf(finalTabRZ_ti4_of_20_alpha_005[x]);
            finalTabRZ_ti5_of_30_alpha_01String[x] = String.valueOf(finalTabRZ_ti5_of_30_alpha_01[x]);

            finalTabNRZ_1ti_nbEch_div_10_alpha_075String[x] = String.valueOf(finalTabNRZ_1ti_nbEch_div_10_alpha_075[x]);
            finalTabNRZ_1ti_nbEch_div_3_alpha_075String[x] = String.valueOf(finalTabNRZ_1ti_nbEch_div_3_alpha_075[x]);
            finalTabNRZ_1ti_nbEch_div_2_alpha_05String[x] = String.valueOf(finalTabNRZ_1ti_nbEch_div_2_alpha_05[x]);
            finalTabNRZ_1ti_nbEch_div_2_alpha_025String[x] = String.valueOf(finalTabNRZ_1ti_nbEch_div_2_alpha_025[x]);
            finalTabNRZ_ti1_of_5_alpha_05String[x] = String.valueOf(finalTabNRZ_ti1_of_5_alpha_05[x]);
            finalTabNRZ_ti2_of_10_alpha_025String[x] = String.valueOf(finalTabNRZ_ti2_of_10_alpha_025[x]);
            finalTabNRZ_ti3_of_15_alpha_01String[x] = String.valueOf(finalTabNRZ_ti3_of_15_alpha_01[x]);
            finalTabNRZ_ti4_of_20_alpha_005String[x] = String.valueOf(finalTabNRZ_ti4_of_20_alpha_005[x]);
            finalTabNRZ_ti5_of_30_alpha_01String[x] = String.valueOf(finalTabNRZ_ti5_of_30_alpha_01[x]);

            finalTabNRZT_1ti_nbEch_div_10_alpha_075String[x] = String.valueOf(finalTabNRZT_1ti_nbEch_div_10_alpha_075[x]);
            finalTabNRZT_1ti_nbEch_div_3_alpha_075String[x] = String.valueOf(finalTabNRZT_1ti_nbEch_div_3_alpha_075[x]);
            finalTabNRZT_1ti_nbEch_div_2_alpha_05String[x] = String.valueOf(finalTabNRZT_1ti_nbEch_div_2_alpha_05[x]);
            finalTabNRZT_1ti_nbEch_div_2_alpha_025String[x] = String.valueOf(finalTabNRZT_1ti_nbEch_div_2_alpha_025[x]);
            finalTabNRZT_ti1_of_5_alpha_05String[x] = String.valueOf(finalTabNRZT_ti1_of_5_alpha_05[x]);
            finalTabNRZT_ti2_of_10_alpha_025String[x] = String.valueOf(finalTabNRZT_ti2_of_10_alpha_025[x]);
            finalTabNRZT_ti3_of_15_alpha_01String[x] = String.valueOf(finalTabNRZT_ti3_of_15_alpha_01[x]);
            finalTabNRZT_ti4_of_20_alpha_005String[x] = String.valueOf(finalTabNRZT_ti4_of_20_alpha_005[x]);
            finalTabNRZT_ti5_of_30_alpha_01String[x] = String.valueOf(finalTabNRZT_ti5_of_30_alpha_01[x]);

//            finalTabNRZString[x] = String.valueOf(finalTabNRZ[x]);
//            finalTabRZString[x] = String.valueOf(finalTabRZ[x]);
//            finalTabNRZTString[x] = String.valueOf(finalTabNRZT[x]);
            tabTEBTheoString[x] = String.valueOf(tabTEBTheo[x]);
            tabSNRString[x] = String.valueOf(tabSNR[x]);
        }

        LinkedList<String[]> finalTabs = new LinkedList<>();
        finalTabs.add(tabSNRString);
        finalTabs.add(tabTEBTheoString);
        // POUR RZ
        finalTabs.add(finalTabRZ_1ti_nbEch_div_10_alpha_075String);
        finalTabs.add(finalTabRZ_1ti_nbEch_div_3_alpha_075String);
        finalTabs.add(finalTabRZ_1ti_nbEch_div_2_alpha_05String);
        finalTabs.add(finalTabRZ_1ti_nbEch_div_2_alpha_025String);
        finalTabs.add(finalTabRZ_ti1_of_5_alpha_05String);
        finalTabs.add(finalTabRZ_ti2_of_10_alpha_025String);
        finalTabs.add(finalTabRZ_ti3_of_15_alpha_01String);
        finalTabs.add(finalTabRZ_ti4_of_20_alpha_005String);
        finalTabs.add(finalTabRZ_ti5_of_30_alpha_01String);

        // POUR NRZ
        finalTabs.add(finalTabNRZ_1ti_nbEch_div_10_alpha_075String);
        finalTabs.add(finalTabNRZ_1ti_nbEch_div_3_alpha_075String);
        finalTabs.add(finalTabNRZ_1ti_nbEch_div_2_alpha_05String);
        finalTabs.add(finalTabNRZ_1ti_nbEch_div_2_alpha_025String);
        finalTabs.add(finalTabNRZ_ti1_of_5_alpha_05String);
        finalTabs.add(finalTabNRZ_ti2_of_10_alpha_025String);
        finalTabs.add(finalTabNRZ_ti3_of_15_alpha_01String);
        finalTabs.add(finalTabNRZ_ti4_of_20_alpha_005String);
        finalTabs.add(finalTabNRZ_ti5_of_30_alpha_01String);

        // POUR NRZT
        finalTabs.add(finalTabNRZT_1ti_nbEch_div_10_alpha_075String);
        finalTabs.add(finalTabNRZT_1ti_nbEch_div_3_alpha_075String);
        finalTabs.add(finalTabNRZT_1ti_nbEch_div_2_alpha_05String);
        finalTabs.add(finalTabNRZT_1ti_nbEch_div_2_alpha_025String);
        finalTabs.add(finalTabNRZT_ti1_of_5_alpha_05String);
        finalTabs.add(finalTabNRZT_ti2_of_10_alpha_025String);
        finalTabs.add(finalTabNRZT_ti3_of_15_alpha_01String);
        finalTabs.add(finalTabNRZT_ti4_of_20_alpha_005String);
        finalTabs.add(finalTabNRZT_ti5_of_30_alpha_01String);


//        finalTabs.add(finalTabNRZString);
//        finalTabs.add(finalTabRZString);
//        finalTabs.add(finalTabNRZTString);

        // Créer un fichier CSV avec le nom de chaque tableau et les valeurs associées
        File csvFile = new File("tebEnFctSNRetTM.csv");
        FileWriter writer = new FileWriter(csvFile);
        writer.write("SNR par bit,TEB theorique,TEB RZ 1ti nbEch div 10 alpha 075, TEB RZ 1ti nbEch div 3 alpha 075, TEB RZ 1ti nbEch div 2 alpha 05, TEB RZ 1ti nbEch div 2 alpha 025, TEB RZ ti1 5 alpha 05, TEB RZ ti2 10 alpha 025, TEB RZ ti3 15 alpha 01, TEB RZ ti4 20 alpha 005, TEB RZ ti5 30 alpha 01,TEB NRZ 1ti nbEch div 10 alpha 075, TEB NRZ 1ti nbEch div 3 alpha 075, TEB NRZ 1ti nbEch div 2 alpha 05, TEB NRZ 1ti nbEch div 2 alpha 025, TEB NRZ ti1 5 alpha 05, TEB NRZ ti2 10 alpha 025, TEB NRZ ti3 15 alpha 01, TEB NRZ ti4 20 alpha 005, TEB NRZ ti5 30 alpha 01, TEB NRZT 1ti nbEch div 10 alpha 075, TEB NRZT 1ti nbEch div 3 alpha 075, TEB NRZT 1ti nbEch div 2 alpha 05, TEB NRZT 1ti nbEch div 2 alpha 025, TEB NRZT ti1 5 alpha 05, TEB NRZT ti2 10 alpha 025, TEB NRZT ti3 15 alpha 01, TEB NRZT ti4 20 alpha 005, TEB NRZT ti5 30 alpha 01\n");
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

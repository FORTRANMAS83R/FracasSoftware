package simulateur;

import org.apache.commons.math3.special.Erf;
import visualisations.VueCourbe;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.LinkedList;

public class tebEnFctSNR {
    /**
     * Cette méthode et plus généralement cette classe permet de dessiner les courbes du TEB en fonction du SNR
     * Les courbes concernées sont les courbes observées en pratique et la courbe théorique.
     * Pour un affichage exploitable des courbes observées via la simulation, nous avons pris la décision de lancer
     * 5000 fois les 800 simulations et de faire la moyenne des valeurs obtenues
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        int size = 800;     // si vous modifiez cette valeur, vérifiez que la boucle avec snr ne lance pas OutOfBandsException
        double snrMin = -50;
        double snrMax = 30;
        int tailleMessage = 100000;
        int nbEch = 60;

        LinkedList<float[]> tabsNRZ = new LinkedList<>();
        LinkedList<float[]> tabsRZ = new LinkedList<>();
        LinkedList<float[]> tabsNRZT = new LinkedList<>();

        float[] finalTabNRZ = new float[size];
        float[] finalTabRZ = new float[size];
        float[] finalTabNRZT = new float[size];
        float[] tabTEBTheo = new float[size];
        float[] tabSNR = new float[size];

        // Boucle pour effectuer plusieurs simulations
        for (int j = 0; j < 10; j++) {
            float[] tempTabNRZ = new float[size];
            float[] tempTabRZ = new float[size];
            float[] tempTabNRZT = new float[size];
            int i = 0;
            // Boucle pour varier le SNR de -50 à 30 avec un pas de 0.1
            for (double snr = snrMin; snr <= snrMax; snr += 0.1) {
                String[] arguments = new String[]{"-mess", String.valueOf(tailleMessage), "-form", "NRZT", "-nbEch", String.valueOf(nbEch), "-snrpb", String.valueOf(Math.round(snr * 1000) / (float) 1000)};
                // Création et exécution du simulateur avec les paramètres spécifiés
                Simulateur simulateur = new Simulateur(arguments);
                simulateur.execute();
                // Calcul du taux d'erreur binaire et stockage dans le tableau temporaire
                tempTabNRZT[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(arguments);
                simulateur.execute();
                tempTabRZ[i] = simulateur.calculTauxErreurBinaire();

                simulateur = new Simulateur(arguments);
                simulateur.execute();
                tempTabNRZ[i] = simulateur.calculTauxErreurBinaire();

                i++;
                System.out.println("boucle SNR" + i);
            }
            System.out.println("numéro de la simulation" + j);
            tabsNRZ.add(tempTabNRZ);
            tabsRZ.add(tempTabRZ);
            tabsNRZT.add(tempTabNRZT);
        }

        // Calcul de la courbe théorique en utilisant la fonction d'erreur complémentaire
        int i = 0;
        for (double snr = snrMin; snr <= snrMax; snr += 0.1) {
            tabTEBTheo[i] = (float) (0.5 * Erf.erfc(Math.sqrt(Math.pow(10, snr / 10))));
            tabSNR[i] = (float) snr;
            if (snr > 0.1 || snr < -0.1) {
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
            finalTabNRZ[y] = sommeNRZ / (float) tabsNRZ.size();
            finalTabRZ[y] = sommeRZ / (float) tabsRZ.size();
            finalTabNRZT[y] = sommeNRZT / (float) tabsNRZT.size();
        }
        // Affichage des résultats finaux et création de la courbe
        System.out.println(Arrays.toString(finalTabNRZ));
        System.out.println(Arrays.toString(finalTabRZ));
        System.out.println(Arrays.toString(finalTabNRZT));
        System.out.println(Arrays.toString(tabTEBTheo));

        String[] finalTabNRZString = new String[size];
        String[] finalTabRZString = new String[size];
        String[] finalTabNRZTString = new String[size];
        String[] tabTEBTheoString = new String[size];
        String[] tabSNRString = new String[size];

        for (int x = 0; x < size; x++) {
            finalTabNRZString[x] = String.valueOf(finalTabNRZ[x]);
            finalTabRZString[x] = String.valueOf(finalTabRZ[x]);
            finalTabNRZTString[x] = String.valueOf(finalTabNRZT[x]);
            tabTEBTheoString[x] = String.valueOf(tabTEBTheo[x]);
            tabSNRString[x] = String.valueOf(tabSNR[x]);
        }

        LinkedList<String[]> finalTabs = new LinkedList<>();
        finalTabs.add(tabSNRString);
        finalTabs.add(tabTEBTheoString);
        finalTabs.add(finalTabNRZString);
        finalTabs.add(finalTabRZString);
        finalTabs.add(finalTabNRZTString);

        //Créer un fichier CSV avec le nom de chaque tableau et les valeurs associées
        File csvFile = new File("tebEnFctSNR.csv");
        FileWriter writer = new FileWriter(csvFile);
        writer.write("SNR par bit,TEB théorique,TEB NRZ,TEB RZ,TEB NRZT\n");
        for (int x = 0; x < size; x++) {
            StringBuilder sb = new StringBuilder();
            i = 0;
            for (String[] tab : finalTabs) {
                sb.append(tab[x]);
                if (i < 4) {
                    sb.append(",");
                }
                i++;
            }
            sb.append("\n");
            writer.write(sb.toString());
        }


//        new VueCourbe(finalTabNRZ, "TEB en fonction du SNR pour NRZ");
//        new VueCourbe(finalTabRZ, "TEB en fonction du SNR pour RZ");
//        new VueCourbe(finalTabNRZT, "TEB en fonction du SNR pour NRZT");
//        new VueCourbe(tabTEBTheo, "TEB théorique en fonction du SNR");
    }


}

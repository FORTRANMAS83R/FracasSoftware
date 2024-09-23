package simulateur;

import visualisations.VueCourbe;

import java.util.Arrays;
import java.util.LinkedList;

public class tebEnFctSNR {
    public static void main (String[] args) {
        int size = 800; // if you modify this value, check that the loop with snr does not throw OutOfBandsException

        LinkedList<float[]> tabs = new LinkedList<>();
        float[] finalTab = new float[size];

        for (int j = 0; j <50; j++) {
            float[] tempTab = new float[size];
            int i = 0;
            for (double snr = -50; snr <= 30; snr += 0.1) {
                Simulateur.main(new String[] {"-mess","100","-form","NRZ","-nbEch","30","-snrpb", String.valueOf(Math.round(snr * 1000)/(float)1000)});
                tempTab[i] = Simulateur.TEB;
                i++;
            }
            tabs.add(tempTab);
        }

        for (int y = 0; y < size; y++) {
            float somme = 0;
            for (float[] tab : tabs) {
                somme+=tab[y];
            }
            finalTab[y] = somme/(float) tabs.size();
        }



        System.out.println(Arrays.toString(finalTab));
        new VueCourbe(finalTab, "TEB en fonction du SNR");
    }
}

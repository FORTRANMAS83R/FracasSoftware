package simulateur;

import visualisations.VueCourbe;

import java.util.Arrays;

public class tebEnFctSNR {
    public static void main (String[] args) {
        float[] tab = new float[800];
        int i = 0;
        for (double snr = -30; snr <= 50; snr += 0.1) {
            Simulateur.main(new String[] {"-mess","100","-form","NRZ","-nbEch","30","-snrpb", String.valueOf(Math.round(snr * 1000)/(float)1000)});
            if (Simulateur.TEB > 0.5) {
                tab[i] = Math.abs(1-Simulateur.TEB);
            } else {
                tab[i] = Simulateur.TEB;
            }
            i++;
        }
        System.out.println(Arrays.toString(tab));
        new VueCourbe(tab, "TEB en fonction du SNR");
    }
}

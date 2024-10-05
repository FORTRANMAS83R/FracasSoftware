package bruit;

import java.util.Random;

import information.Information;

/**
 * Classe émulant un bruit blanc gaussien. Applicable théoriquement aussi bien
 * sur les transmetteurs que
 * les sources ou les destinations
 * Rq: on pourrait envisager une classe parent ou une interface si l'on venait à
 * avoir plusieurs types de bruits
 */
public class BBG {
    private final Float SNRpb;
    private Float variance;
    private Float SNR;
    private final Random a1; // générateur de nombres aléatoires suivant une loi uniforme entre 0 et 1
    private final Random a2; // générateur de nombres aléatoires suivant une loi uniforme entre 0 et 1
    private Float puissanceSignal;

    /**
     * *
     * 
     * @param SNRpb: le rapport signal sur bruit en dB
     * @param seed:  la graine pour le générateur de nombres aléatoires a1, null =
     *               sans seed
     */
    public BBG(Float SNRpb, Integer seed) {
        this.variance = 0.0f;
        this.SNR = 0.0f;
        this.puissanceSignal = 0.0f;
        if (seed != null) {
            this.a1 = new Random(seed);
            this.a2 = new Random(seed);
        } else {
            this.a1 = new Random();
            this.a2 = new Random();
        }
        this.SNRpb = SNRpb;
    }

    /**
     * Méthode permettant d'ajouter la valeur du bruit à un signal
     *
     * @param signalRecu le signal qui doit être bruité
     * @return le bruit généré
     */
    public Information<Float> bruitage(Information<Float> signalRecu) {
        Information<Float> signal = signalRecu.clone();
        this.SNR = calculSNR(SNRpb, signal.getNbEchantillons());
        this.puissanceSignal = calculPuissanceSignal(signal);
        this.variance = calculVariance(puissanceSignal, SNRpb, signal.getNbEchantillons());

        for (int i = 0; i < signal.nbElements(); i++) {
            signal.setIemeElement(i,
                    signal.iemeElement(i)
                            + (float) Math.sqrt(this.variance) * (float) Math.sqrt(-2 * Math.log(1 - a1.nextFloat()))
                                    * (float) Math.cos(2 * Math.PI * a2.nextFloat()));
        }

        return signal;
    }

    /**
     * Méthode renvoyant une description de l'objet sous forme de chaîne de
     * caractères
     *
     * @return la description de l'objet
     */
    public String toString() {
        return "BBG{" + "SNRpb=" + SNRpb + ", variance=" + variance + ", SNR=" + SNR + '}';
    }

    /**
     * Méthode permettant de récupérer la puissance du signal
     *
     * @param signal: le signal
     * @return le bruit généré
     */
    public static Float calculPuissanceSignal(Information<Float> signal) {
        Float puissance = 0f;
        for (Float s : signal) {
            puissance += s * s;
        }
        return puissance / signal.nbElements();
    }

    /**
     * Méthode permettant de calculer la variance du bruit sur un symbole donné
     *
     * @param puissanceSignal: la puissance du signal au sein du symbole
     * @param snrPb:           le rapport signal sur bruit en dB
     * @param nbEch:           le nombre d'échantillons par symbole
     * @return la variance du bruit
     */
    public static Float calculVariance(Float puissanceSignal, Float snrPb, Integer nbEch) {
        return (nbEch * puissanceSignal) / ((float) (2 * Math.pow(10, snrPb / 10)));
    }

    /**
     * Méthode permettant de calculer le rapport signal sur bruit à partir du
     * rapport signal sur bruit par bit en dB
     *
     * @param snrPB: le rapport signal sur bruit en dB
     * @param nbEch: le nombre d'échantillons par symbole
     * @return le rapport signal sur bruit
     */
    private static Float calculSNR(Float snrPB, Integer nbEch) {
        return (float) (snrPB - 10 * Math.log10(nbEch));
    }

    /**
     * Méthode permettant de récupérer le rapport signal sur bruit
     *
     * @return snrpb
     */
    public Float getSNRpb() {
        return this.SNRpb;
    }
}

package bruit;

import information.Information;


import java.util.LinkedList;
import java.util.Random;


/*
    Classe émulant un bruit blanc gaussien. Applicable théoriquement aussi bien sur les transmetteurs que
    les sources ou les destinations
    Rq: on pourrait envisager une classe parent ou une interface si l'on venait à avoir plusieurs types de bruits
 */
public class BBG {
    private Information<Float> bruit;
    private LinkedList<Float> variances;
    private Random a1; // générateur de nombres aléatoires suivant une loi uniforme entre 0 et 1
    private Random a2; // générateur de nombres aléatoires suivant une loi uniforme entre 0 et 1

    /*
        Constructeur de la classe BBG (sans graine)
        @param SNRpb: le rapport signal sur bruit en dB
        @param signal: le signal à bruiter
        @param nbEch: le nombre d'échantillons par symbole
     */
    public BBG(Float SNRpb, Information<Float> signal, Integer nbEch){
        this.bruit = new Information<>();
        this.variances = new LinkedList<>();
        this.a1 = new Random();
        this.a2 = new Random();
        this.appliquerBruit(signal, SNRpb, nbEch);
    }

    /*
        Constructeur de la classe BBG (avec graine)
        @param SNRpb: le rapport signal sur bruit en dB
        @param signal: le signal à bruiter
        @param nbEch: le nombre d'échantillons par symbole
        @param seed1: la graine pour le générateur de nombres aléatoires a1
        @param seed2: la graine pour le générateur de nombres aléatoires a2
     */
    public BBG(Float SNRpb, Information<Float> signal, Integer nbEch, Integer seed1, Integer seed2){
        this.bruit = new Information<>();
        this.variances = new LinkedList<>();
        this.a1 = new Random(seed1);
        this.a2 = new Random(seed2);
        this.appliquerBruit(signal, SNRpb, nbEch);
    }

    /*
        Méthode permettant de calculer le bruit généré avec un SNRpb donné pour un signal donné
        @param signal: le signal à bruiter
        @param SNRpb: le rapport signal sur bruit en dB
        @param nbEch: le nombre d'échantillons par symbole
        @return le bruit généré
     */
    private void appliquerBruit(Information<Float> signal, Float SNRpb, Integer nbEch){
        Float puissanceSignal = 0f;
        Float variance = 0f;

        for (int i=0; i<signal.nbElements()/nbEch; i++){
            puissanceSignal = calculPuissanceSignal(signal.sousInformation(i*nbEch, (i+1)*nbEch));
            variance = calculVariance(puissanceSignal, SNRpb, nbEch);
            this.variances.add(variance);
            for (int j=0; j<nbEch; j++){
                this.bruit.add(variance*(float)Math.sqrt(-2*Math.log(a1.nextFloat()))*(float)Math.cos(2*Math.PI*a2.nextFloat()));
            }
        }
    }

    /*
        Méthode permettant d'ajouter la valeur du bruit à un signal'
        @param signal: le signal à bruiter
        @return le bruit généré
     */
    private Information<Float> bruitage(Information<Float> signal){
        return signal.add(this.bruit);
    }

    /*
        Méthode permettant de récupérer la puissance du signal
        @param signal: le signal
        @return le bruit généré
     */
    private static Float calculPuissanceSignal(Information<Float> signal){
        Float puissance = 0f;
        for(Float s : signal){
            puissance += s*s;
        }
        return puissance/signal.nbElements();
    }

    /*
        Méthode permettant de calculer la variance du bruit sur un symbole donné
        @param puissanceSignal: la puissance du signal au sein du symbole
        @param snrPb: le rapport signal sur bruit en dB
        @param nbEch: le nombre d'échantillons par symbole
        @return la variance du bruit
     */
    private static Float calculVariance(Float puissanceSignal, Float snrPb, int nbEch){
        return ((float)Math.pow(10, snrPb/10)/(2*nbEch*puissanceSignal));
    }
}

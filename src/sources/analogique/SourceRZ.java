package sources.analogique;

/**
 * La classe SourceRZ représente une source analogique utilisant le codage RZ (Return to Zero).
 * Elle hérite de la classe SourceAnalogique et implémente la méthode de filtrage de mise en forme spécifique au codage RZ.
 */
public class SourceRZ extends SourceAnalogique {

    /**
     * Constructeur de la classe SourceRZ.
     *
     * @param message       Le message à coder.
     * @param nbEchantillon Le nombre d'échantillons par symbole.
     * @param amp_min       L'amplitude minimale.
     * @param amp_max       L'amplitude maximale.
     */
    public SourceRZ(String message, int nbEchantillon, float amp_min, float amp_max) {
        super(message, nbEchantillon, amp_min, amp_max);
    }

    /**
     * Constructeur de la classe SourceRZ.
     *
     * @param message       Le message à coder.
     * @param nbEchantillon Le nombre d'échantillons par symbole.
     * @param amp_min       L'amplitude minimale.
     * @param amp_max       L'amplitude maximale.
     */
    public SourceRZ(int nbEchantillon, float amp_min, float amp_max, int nbBits, Integer seed) {
        super(nbEchantillon, amp_min, amp_max, nbBits, seed);
    }

    /**
     * Méthode de filtrage de mise en forme spécifique au codage RZ.
     * Cette méthode divise chaque symbole en trois parties et applique les amplitudes correspondantes.
     */
    @Override
    protected void filtreMiseEnForme() {
        int premierTiers = this.nbEchantillon / 3;
        int deuxiemeTiers = this.nbEchantillon / 3;
        informationGeneree = informationEchantillon.clone();

        for (int i = 0; i < informationEchantillon.nbElements(); i += nbEchantillon) {
            for (int j = 0; j < nbEchantillon; j++) {
                if ((j <= premierTiers)) {
                    informationGeneree.setIemeElement(i + j, 0.0f);
                } else if (j <= premierTiers + deuxiemeTiers) {
                    if (informationEchantillon.iemeElement(i + j) == amp_max) {
                        informationGeneree.setIemeElement(i + j, amp_max);
                    } else {
                        informationGeneree.setIemeElement(i + j, amp_min);
                    }
                } else {
                    informationGeneree.setIemeElement(i + j, 0.0f);
                }
            }
        }

    }

}

package sources.analogique;

/**
 * La classe SourceRZ représente une source analogique utilisant le codage RZ
 * (Return to Zero). Elle hérite de la classe SourceAnalogique et implémente la
 * méthode de filtrage de mise en forme spécifique au codage RZ.
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
     * @param nbBits        Le nombre de bits du message à générer
     * @param nbEchantillon Le nombre d'échantillons par symbole.
     * @param amp_min       L'amplitude minimale.
     * @param amp_max       L'amplitude maximale.
     * @param seed          La graine utilisée pour lancer plusieurs fois la même génération aléatoire
     */
    public SourceRZ(int nbEchantillon, float amp_min, float amp_max, int nbBits, Integer seed) {
        super(nbEchantillon, amp_min, amp_max, nbBits, seed);
    }

    /**
     * Méthode de filtrage de mise en forme spécifique au codage RZ. Cette méthode
     * divise chaque symbole en trois parties et applique les amplitudes
     * correspondantes.
     */
    @Override
    protected void filtreMiseEnForme() {
        final int nbEchantillon = this.informationEchantillon.getNbEchantillons();
        informationGeneree = informationEchantillon.clone();

        for (int i = 0; i < informationEchantillon.nbElements(); i += nbEchantillon) {
            if (informationEchantillon.iemeElement(i) != 0)
                for (int j = 0; j < nbEchantillon / 3; j++) {
                    this.informationGeneree.setIemeElement(i + j, 0f);
                    this.informationGeneree.setIemeElement(i + nbEchantillon - 1 - j, 0f);
                }
        }

    }

}

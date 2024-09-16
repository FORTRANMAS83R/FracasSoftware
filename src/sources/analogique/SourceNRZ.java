package sources.analogique;

/**
 * La classe SourceNRZ représente une source analogique utilisant le codage Non-Return-to-Zero (NRZ).
 * Elle étend la classe SourceAnalogique et implémente la méthode de filtrage spécifique pour le codage NRZ.
 */
public class SourceNRZ extends SourceAnalogique {
    /**
     * Construit un objet SourceNRZ avec le message et les paramètres spécifiés.
     *
     * @param message       Le message à encoder.
     * @param nbEchantillon Le nombre d'échantillons par symbole.
     * @param amp_min       L'amplitude minimale.
     * @param amp_max       L'amplitude maximale.
     */
    public SourceNRZ(String message, int nbEchantillon, float amp_min, float amp_max) {
        super(message, nbEchantillon, amp_min, amp_max);
    }

    /**
     * Construit un objet SourceNRZ avec des paramètres de message générés aléatoirement.
     *
     * @param nbEchantillon Le nombre d'échantillons par symbole.
     * @param amp_min       L'amplitude minimale.
     * @param amp_max       L'amplitude maximale.
     * @param nbBits        Le nombre de bits dans le message généré.
     * @param seed          La graine pour la génération aléatoire (peut être null).
     */
    public SourceNRZ(int nbEchantillon, float amp_min, float amp_max, int nbBits, Integer seed) {
        super(nbEchantillon, amp_min, amp_max, nbBits, seed);
    }

    /**
     * Implémente la méthode de filtrage spécifique pour le codage NRZ.
     * Cette méthode traite les échantillons d'information générés.
     */
    @Override
    protected void filtreMiseEnForme() {
        informationGeneree = informationEchantillon.clone();
        informationGeneree.setIemeElement(0,0f);
        informationGeneree.setIemeElement(informationGeneree.nbElements()-1,0f);

    }

}

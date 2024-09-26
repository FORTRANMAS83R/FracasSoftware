package sources.analogique;

/**
 * La classe SourceNRZT représente une source analogique utilisant le codage
 * Non-Return-to-Zero Inversé (NRZT). Elle étend la classe SourceAnalogique et
 * implémente la méthode de filtrage spécifique pour le codage NRZT.
 */

public class SourceNRZT extends SourceAnalogique {
    /**
     * Construit un objet SourceNRZT avec le message et les paramètres spécifiés.
     *
     * @param message       Le message à encoder.
     * @param nbEchantillon Le nombre d'échantillons par symbole.
     * @param amp_min       L'amplitude minimale.
     * @param amp_max       L'amplitude maximale.
     */
    public SourceNRZT(String message, int nbEchantillon, float amp_min, float amp_max) {
        super(message, nbEchantillon, amp_min, amp_max);
    }

    /**
     * Construit un objet SourceNRZT avec des paramètres de message générés
     * aléatoirement.
     *
     * @param nbEchantillon Le nombre d'échantillons par symbole.
     * @param amp_min       L'amplitude minimale.
     * @param amp_max       L'amplitude maximale.
     * @param nbBits        Le nombre de bits dans le message généré.
     * @param seed          La graine pour la génération aléatoire (peut être null).
     */
    public SourceNRZT(int nbEchantillon, float amp_min, float amp_max, int nbBits, Integer seed) {
        super(nbEchantillon, amp_min, amp_max, nbBits, seed);
    }

    /**
     * Implémente la méthode de filtrage spécifique pour le codage NRZT. Cette
     * méthode traite les échantillons d'information générés.
     */
    @Override
    protected void filtreMiseEnForme() {
        informationGeneree = informationEchantillon.clone();

        final int nbEchantillon = this.informationEchantillon.getNbEchantillons();

        for (int i = 0; i < informationEchantillon.nbElements(); i += nbEchantillon) {
            final float symbole_actuel = informationEchantillon.iemeElement(i);
            final float val_fin = informationEchantillon.nbElements() > i + nbEchantillon
                    ? (informationEchantillon.iemeElement(i + nbEchantillon) + symbole_actuel) / 2
                    : 0;
            final float val_depart = i == 0 ? 0 : (informationEchantillon.iemeElement(i - 1) + symbole_actuel) / 2;
            final float delta_1er_tier = symbole_actuel == val_depart ? 0
                    : symbole_actuel / ((float) nbEchantillon / 3f);
            final float delta_3eme_tier = symbole_actuel == val_fin ? 0
                    : (0 - symbole_actuel) / (((float) nbEchantillon / 3f)
                    - (i + nbEchantillon == informationEchantillon.nbElements() ? 1 : 0));

            if (delta_1er_tier != 0)
                for (int j = 0; j < nbEchantillon / 3; j++) {
                    informationGeneree.setIemeElement(i + j, j * delta_1er_tier);
                }

            if (delta_3eme_tier != 0)
                for (int j = 0; j < nbEchantillon / 3; j++) {
                    informationGeneree.setIemeElement(i + (2 * nbEchantillon / 3) + j,
                            symbole_actuel + j * delta_3eme_tier);
                }
        }
    }
}

package sources.analogique;

import information.Information;
import sources.Source;

/**
 * Une classe abstraite pour effectuer des actions globale aux sources
 * analogiques
 *
 * @param <T> Le type de l'information qui sera transmise
 */
public abstract class SourceAnalogique extends Source<Float> {
    protected String message;
    protected int nbEchantillon;
    protected float amp_min;
    protected float amp_max;

    protected Information<Float> informationEchantillon;

    /**
     * Le constructeur par défaut des sources analogique prenant les paramètres
     * suivant :
     *
     * @param message       Le message, une suite de 0 et de 1 qui sera ensuite
     *                      traduit en une liste de Boolean
     * @param nbEchantillon Le nombre d'échantillon par bit
     * @param amp_min       Amplitude maximum du signal, c'est à dire l'amplitude
     *                      d'un bit à l'état 1
     * @param amp_max       Amplitude minimum du signal, c'est à dire l'amplitude
     *                      d'un bit à l'état 0
     */
    public SourceAnalogique(String message, int nbEchantillon, float amp_min, float amp_max) {
        super();
        this.message = message;
        this.amp_min = amp_min;
        this.amp_max = amp_max;

        this.informationBinaire = genInformation(message);
        this.informationBinaire.setNbEchantillons(nbEchantillon);
        echantillonnage();
        filtreMiseEnForme();
    }

    public SourceAnalogique(int nbEchantillon, float amp_min, float amp_max, int nbBits, Integer seed) {
        super();
        this.informationBinaire = genInformationAleatoire(nbBits, seed);
        this.informationBinaire.setNbEchantillons(nbEchantillon);
        this.amp_min = amp_min;
        this.amp_max = amp_max;

        echantillonnage();
        filtreMiseEnForme();
    }

    private void echantillonnage() {
        this.informationEchantillon = new Information<>();
        for (int i = 0; i < informationBinaire.nbElements(); i++) {
            final Float element = informationBinaire.iemeElement(i) ? amp_max : amp_min;
            for (int j = 0; j < nbEchantillon; j++)
                this.informationEchantillon.add(element);
        }
    }

    /**
     * Cette methode permet la mise en forme du signal selon le code choisis
     */
    protected abstract void filtreMiseEnForme();
}

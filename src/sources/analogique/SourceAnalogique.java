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
	protected int amp_min;
	protected int amp_max;

	protected Information<Boolean> informationBinaire;

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
	public SourceAnalogique(String message, int nbEchantillon, int amp_min, int amp_max) {
		super();
		this.message = message;
		this.nbEchantillon = nbEchantillon;
		this.amp_min = amp_min;
		this.amp_max = amp_max;

		informationBinaire = genInformation(message);
		echantillonnage();
		filtreMiseEnForme();
	}

	private void echantillonnage() {
		informationGeneree = new Information<Float>();
		for (int i = 0; i < informationBinaire.nbElements(); i++) {
			final Float element = informationBinaire.iemeElement(i) ? 1f : 0f;
			for (int j = 0; j < nbEchantillon; j++)
				informationGeneree.add(element);
		}
	}

	/**
	 * Cette methode permet la mise en forme du signal selon le code choisis
	 */
	protected abstract void filtreMiseEnForme();
}

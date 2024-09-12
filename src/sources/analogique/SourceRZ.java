package sources.analogique;

import information.Information;

public class SourceRZ extends SourceAnalogique {

	public SourceRZ(String message, int nbEchantillon, float amp_min, float amp_max) {
		super(message, nbEchantillon, amp_min, amp_max);
	}

	public SourceRZ(int nbEchantillon, float amp_min, float amp_max, int nbBits, Integer seed) {
		super(nbEchantillon, amp_min, amp_max, nbBits, seed);
	}

	@Override
	protected void filtreMiseEnForme() {
		int premierTiers = this.nbEchantillon/3;
		int deuxiemeTiers = this.nbEchantillon/3;
		informationGeneree = informationEchantillon;

		for (int i = 0; i < informationEchantillon.nbElements(); i+=nbEchantillon) {
			for (int j = 0; j < nbEchantillon; j++) {
				if ((j <= premierTiers)) {
					informationGeneree.setIemeElement(i+j,amp_min);
				} else if (j <= premierTiers+deuxiemeTiers) {
					if (informationGeneree.iemeElement(i+j) == amp_max) {
						informationGeneree.setIemeElement(i+j,amp_max);
					}
				} else {
					informationGeneree.setIemeElement(i+j,amp_min);
				}
			}
		}

	}

}

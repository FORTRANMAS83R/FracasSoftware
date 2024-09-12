package sources.analogique;

public class SourceNRZ extends SourceAnalogique {

	public SourceNRZ(String message, int nbEchantillon, float amp_min, float amp_max) {
		super(message, nbEchantillon, amp_min, amp_max);
	}

	public SourceNRZ(int nbEchantillon, float amp_min, float amp_max, int nbBits, Integer seed) {
		super(nbEchantillon, amp_min, amp_max, nbBits, seed);
	}

	@Override
	protected void filtreMiseEnForme() {
		informationGeneree = informationEchantillon;
	}

}

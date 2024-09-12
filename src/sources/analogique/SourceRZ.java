package sources.analogique;

public class SourceRZ extends SourceAnalogique {

	public SourceRZ(String message, int nbEchantillon, float amp_min, float amp_max) {
		super(message, nbEchantillon, amp_min, amp_max);
	}

	public SourceRZ(int nbEchantillon, float amp_min, float amp_max, int nbBits, Integer seed) {
		super(nbEchantillon, amp_min, amp_max, nbBits, seed);
	}

	@Override
	protected void filtreMiseEnForme() {
		// TODO Auto-generated method stub

	}

}

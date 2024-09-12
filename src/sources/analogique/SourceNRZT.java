package sources.analogique;

public class SourceNRZT extends SourceAnalogique {

	public SourceNRZT(String message, int nbEchantillon, int amp_min, int amp_max) {
		super(message, nbEchantillon, amp_min, amp_max);
	}

	@Override
	protected void filtreMiseEnForme() {
		// TODO Auto-generated method stub

	}

}

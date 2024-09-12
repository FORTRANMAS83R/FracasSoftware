package sources.analogique;

public class SourceNRZ extends SourceAnalogique {

	public SourceNRZ(String message, int nbEchantillon, int amp_min, int amp_max) {
		super(message, nbEchantillon, amp_min, amp_max);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void filtreMiseEnForme() {
	} // Nothing to do here

}

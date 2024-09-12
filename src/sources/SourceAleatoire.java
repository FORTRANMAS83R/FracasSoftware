package sources;

public class SourceAleatoire extends Source<Boolean> {
	public SourceAleatoire(Integer nbBitsMess, Integer seed) {
		super();
		informationGeneree = genInformationAleatoire(nbBitsMess, seed);
	}
}

package sources;

/**
 * La classe SourceAleatoire représente une source d'informations aléatoires.
 * Elle étend la classe Source et génère une information binaire aléatoire.
 */
public class SourceAleatoire extends Source<Boolean> {

    /**
     * Construit un objet SourceAleatoire avec le nombre de bits et la graine spécifiés.
     *
     * @param nbBitsMess Le nombre de bits du message à générer.
     * @param seed       La graine pour la génération aléatoire (peut être null).
     */
    public SourceAleatoire(Integer nbBitsMess, Integer seed) {
        super();
        informationGeneree = genInformationAleatoire(nbBitsMess, seed);
    }
}

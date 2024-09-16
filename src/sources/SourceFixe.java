package sources;

/**
 * La classe SourceFixe représente une source d'informations fixes.
 * Elle étend la classe Source et génère une information binaire à partir d'un message fixe.
 */
public class SourceFixe extends Source<Boolean> {

    /**
     * Construit un objet SourceFixe avec le message spécifié.
     *
     * @param message Le message à encoder en information binaire.
     */
    public SourceFixe(String message) {
        super();
        informationGeneree = genInformation(message);
    }

}

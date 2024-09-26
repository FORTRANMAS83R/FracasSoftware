package destinations;

import information.Information;
import information.InformationNonConformeException;
/**
 * Classe représentant une destination finale.
 * Cette classe hérite de la classe Destination et implémente la méthode recevoir.
 *
 * @param <T> Le type des éléments de l'information.
 */
public class DestinationFinale<T> extends Destination<T> {
    /**
     * Constructeur de la classe DestinationFinale.
     */
    public DestinationFinale() {
        super();
    }
    /**
     * Reçoit une information et la stocke.
     *
     * @param information L'information reçue.
     * @throws InformationNonConformeException Si l'information est non conforme.
     */
    @Override
    public void recevoir(Information<T> information) throws InformationNonConformeException {
        informationRecue = information;
    }
}

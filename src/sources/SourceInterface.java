package sources;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

/**
 * Interface d'un composant ayant le comportement d'une source
 * d'informations dont les éléments sont de type T
 *
 * @author prou
 */
public interface SourceInterface<T> {

    /**
     * pour obtenir la dernière information émise par une source.
     *
     * @return une information
     */
    Information<T> getInformationEmise();

    /**
     * pour connecter une destination à la source
     *
     * @param destination la destination à connecter
     */
    void connecter(DestinationInterface<T> destination);

    /**
     * pour émettre l'information contenue dans une source
     *
     * @throws InformationNonConformeException si l'Information comporte une anomalie
     */
    void emettre() throws InformationNonConformeException;
}

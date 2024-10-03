package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;
/**
 * Classe représentant un transmetteur parfait.
 * Ce transmetteur transmet l'information reçue sans aucune modification.
 *
 * @param <R> Le type des éléments de l'information.
 */
public class TransmetteurParfait<R> extends Transmetteur<R, R> {
    /**
     * Constructeur du transmetteur parfait.
     */
    public TransmetteurParfait() {
        super();
    }
    /**
     * Reçoit une information et l'émet directement.
     *
     * @param information L'information reçue.
     * @throws InformationNonConformeException Si l'information est non conforme.
     */
    @Override
    public void recevoir(Information<R> information) throws InformationNonConformeException {
        informationRecue = information.clone();
        emettre();
    }
    /**
     * Émet l'information reçue à toutes les destinations connectées.
     *
     * @throws InformationNonConformeException Si l'information est non conforme.
     */
    @Override
    public void emettre() throws InformationNonConformeException {
        // émission vers les composants connectés
        for (DestinationInterface<R> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationRecue);
        }
        this.informationEmise = informationRecue;
    }
}




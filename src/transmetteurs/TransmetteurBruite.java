package transmetteurs;

import bruit.BBG;
import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;
/**
 * Classe représentant un transmetteur bruite.
 * Ce transmetteur ajoute du bruit à l'information reçue en fonction du SNR par bit (SNRpb).
 */
public class TransmetteurBruite extends Transmetteur<Float, Float> {

	private Float SNRpb;
	/**
	 * Constructeur du transmetteur bruite.
	 *
	 * @param snrPB Le rapport signal sur bruit par bit.
	 */
	public TransmetteurBruite(Float snrPB) {
		super();
		this.SNRpb = snrPB;
	}
	/**
	 * Reçoit une information, y ajoute du bruit, puis l'émet.
	 *
	 * @param information L'information reçue.
	 * @throws InformationNonConformeException Si l'information est non conforme.
	 */
	@Override
	public void recevoir(Information information) throws InformationNonConformeException {
		this.informationRecue = information.clone();
		BBG bruit = new BBG(this.SNRpb);
		informationEmise = bruit.bruitage(this.informationRecue);
		emettre();
	}
	/**
	 * Émet l'information bruitée à toutes les destinations connectées.
	 *
	 * @throws InformationNonConformeException Si l'information est non conforme.
	 */
	@Override
	public void emettre() throws InformationNonConformeException {
		for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
			destinationConnectee.recevoir(informationEmise);
		}
	}
}

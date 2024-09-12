package sources;

import java.util.LinkedList;
import java.util.Random;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

/**
 * Classe Abstraite d'un composant source d'informations dont les éléments sont
 * de type T
 *
 * @author prou
 */
public abstract class Source<T> implements SourceInterface<T> {

	/**
	 * la liste des composants destination connectés
	 */
	protected LinkedList<DestinationInterface<T>> destinationsConnectees;

	/**
	 * l'information générée par la source
	 */
	protected Information<T> informationGeneree;

	/**
	 * l'information émise par la source
	 */
	protected Information<T> informationEmise;

	/**
	 * un constructeur factorisant les initialisations communes aux réalisations de
	 * la classe abstraite Source
	 */
	public Source() {
		destinationsConnectees = new LinkedList<DestinationInterface<T>>();
		informationGeneree = null;
		informationEmise = null;
	}

	/**
	 * retourne la dernière information émise par la source
	 *
	 * @return une information
	 */
	public Information<T> getInformationEmise() {
		return this.informationEmise;
	}

	public LinkedList<DestinationInterface<T>> getDestinationsConnectees() {
		return this.destinationsConnectees;
	}

	public Information<T> getInformationGeneree() {
		return this.informationGeneree;
	}

	/**
	 * connecte une destination à la source
	 *
	 * @param destination la destination à connecter
	 */
	public void connecter(DestinationInterface<T> destination) {
		destinationsConnectees.add(destination);
	}

	/**
	 * déconnecte une destination de la source
	 *
	 * @param destination la destination à déconnecter
	 */
	public void deconnecter(DestinationInterface<T> destination) {
		destinationsConnectees.remove(destination);
	}

	/**
	 * émet l'information générée
	 *
	 * @throws InformationNonConformeException si l'Information comporte une
	 *                                         anomalie
	 */
	public void emettre() throws InformationNonConformeException {
		// émission vers les composants connectés
		for (DestinationInterface<T> destinationConnectee : destinationsConnectees) {
			destinationConnectee.recevoir(informationGeneree);
		}
		this.informationEmise = informationGeneree;
	}

	protected Information<Boolean> genInformationAleatoire(int nBits, Integer seed) {
		final Information<Boolean> info = new Information<>();
		Random rdm = seed == null ? new Random() : new Random(seed);
		for (int i = 0; i < nBits; i++) {
			info.add(rdm.nextBoolean());
		}
		return info;
	}

	protected Information<Boolean> genInformation(String message) {
		final Information<Boolean> info = new Information<>();
		for (int i = 0; i < message.length(); i++) {
			if (message.charAt(i) == '0')
				info.add(false);
			else if (message.charAt(i) == '1')
				info.add(true);
		}

		return info;
	}
}

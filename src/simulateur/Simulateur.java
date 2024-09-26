package simulateur;

import destinations.Destination;
import destinations.DestinationFinale;
import information.Information;
import sources.Source;
import sources.SourceAleatoire;
import sources.SourceFixe;
import sources.analogique.SourceAnalogiqueType;
import sources.analogique.SourceNRZ;
import sources.analogique.SourceNRZT;
import sources.analogique.SourceRZ;
import transmetteurs.Transmetteur;
import transmetteurs.TransmetteurBruite;
import transmetteurs.TransmetteurParfait;
import visualisations.SondeAnalogique;
import visualisations.SondeHistogramme;
import visualisations.SondeLogique;

/**
 * La classe Simulateur permet de construire et simuler une chaîne de
 * transmission composée d'une Source, d'un nombre variable de Transmetteur(s)
 * et d'une Destination.
 *
 * @author cousin
 * @author prou
 */
public class Simulateur {

	/**
	 * indique si le Simulateur utilise des sondes d'affichage
	 */
	private boolean affichage = false;

	/**
	 * indique si le Simulateur utilise un message généré de manière aléatoire
	 * (message imposé sinon)
	 */
	private boolean messageAleatoire = true;

	private boolean transmissionAnalogique = false;

	/**
	 * indique si le Simulateur utilise un bruit blanc gaussien
	 */
	private boolean messageBruitee = false;

	/**
	 * indique si le Simulateur utilise un bruit blanc gaussien
	 */
	private Float snrpb = 0.0f;

	/**
	 * la valeur de la semence utilisée pour les générateurs aléatoires
	 */
	private Integer seed = null; // pas de semence par défaut

	/**
	 * la longueur du message aléatoire à transmettre si un message n'est pas imposé
	 */
	private int nbBitsMess = 100;

	/**
	 * la chaîne de caractères correspondant à m dans l'argument -mess m
	 */
	private String messageString = "100";

	/**
	 * le composant Source de la chaine de transmission
	 */
	private Source<Float> sourceAnalogique = null;
	private Source<Boolean> sourceLogique = null;

	/**
	 * le composant Transmetteur parfait logique de la chaine de transmission
	 */
	private Transmetteur<Boolean, Boolean> transmetteurLogique = null;
	private Transmetteur<Float, Float> transmetteurAnalogique = null;

	/**
	 * le composant Destination de la chaine de transmission
	 */
	private Destination<Boolean> destinationLogique = null;
	private Destination<Float> destinationAnalogique = null;

	private SourceAnalogiqueType formatSignal = SourceAnalogiqueType.RZ;

	private int nbEch = 30;

	private float ampl_min = 0.0f, ampl_max = 1.0f;

	private float TEB = 0.0f;

	/**
	 * Le constructeur de Simulateur construit une chaîne de transmission composée
	 * d'une Source <Boolean>, d'une Destination <Boolean> et de Transmetteur(s)
	 * [voir la méthode analyseArguments]... <br>
	 * Les différents composants de la chaîne de transmission (Source,
	 * Transmetteur(s), Destination, Sonde(s) de visualisation) sont créés et
	 * connectés.
	 *
	 * @param args le tableau des différents arguments.
	 * @throws ArgumentsException si un des arguments est incorrect
	 */
	public Simulateur(String[] args) throws ArgumentsException {
		// analyser et récupérer les arguments
		analyseArguments(args);

		if (transmissionAnalogique) {
			// Analogique
			if (!messageAleatoire) {
				switch (formatSignal) {
				case RZ -> sourceAnalogique = new SourceRZ(messageString, nbEch, ampl_min, ampl_max);
				case NRZ -> sourceAnalogique = new SourceNRZ(messageString, nbEch, ampl_min, ampl_max);
				case NRZT -> sourceAnalogique = new SourceNRZT(messageString, nbEch, ampl_min, ampl_max);
				}
			} else {
				switch (formatSignal) {
				case RZ -> sourceAnalogique = new SourceRZ(nbEch, ampl_min, ampl_max, nbBitsMess, seed);
				case NRZ -> sourceAnalogique = new SourceNRZ(nbEch, ampl_min, ampl_max, nbBitsMess, seed);
				case NRZT -> sourceAnalogique = new SourceNRZT(nbEch, ampl_min, ampl_max, nbBitsMess, seed);
				}
			}

			if (messageBruitee) {
				transmetteurAnalogique = new TransmetteurBruite(this.snrpb);
				sourceAnalogique.connecter(transmetteurAnalogique);
			} else {
				transmetteurAnalogique = new TransmetteurParfait<>();
				sourceAnalogique.connecter(transmetteurAnalogique);
			}

			destinationAnalogique = new DestinationFinale<>();
			transmetteurAnalogique.connecter(destinationAnalogique);

			if (affichage) {
				sourceAnalogique.connecter(new SondeAnalogique("Sonde en sortie de la source"));
				transmetteurAnalogique.connecter(new SondeAnalogique("Sonde en sortie du transmetteur"));
				transmetteurAnalogique.connecter(new SondeHistogramme("Histogramme de l'information reçue"));
			}
		} else {
			// Logique
			sourceLogique = messageAleatoire ? new SourceAleatoire(nbBitsMess, seed) : new SourceFixe(messageString);
			destinationLogique = new DestinationFinale<>();
			transmetteurLogique = new TransmetteurParfait<Boolean>();

			sourceLogique.connecter(transmetteurLogique);
			transmetteurLogique.connecter(destinationLogique);

			if (affichage) {
				sourceLogique.connecter(new SondeLogique("Sonde en sortie de la source", 300));
				transmetteurLogique.connecter(new SondeLogique("Sonde en sortie du transmetteur", 300));
			}
		}

	}

	/**
	 * La méthode analyseArguments extrait d'un tableau de chaînes de caractères les
	 * différentes options de la simulation. <br>
	 * Elle met à jour les attributs correspondants du Simulateur.
	 *
	 * @param args le tableau des différents arguments. <br>
	 *             <br>
	 *             Les arguments autorisés sont : <br>
	 *             <dl>
	 *             <dt>-mess m</dt>
	 *             <dd>m (String) constitué de 7 ou plus digits à 0 | 1, le message
	 *             à transmettre</dd>
	 *             <dt>-mess m</dt>
	 *             <dd>m (int) constitué de 1 à 6 digits, le nombre de bits du
	 *             message "aléatoire" à transmettre</dd>
	 *             <dt>-s</dt>
	 *             <dd>pour demander l'utilisation des sondes d'affichage</dd>
	 *             <dt>-seed v</dt>
	 *             <dd>v (int) d'initialisation pour les générateurs aléatoires</dd>
	 *             </dl>
	 * @throws ArgumentsException si un des arguments est incorrect.
	 */
	private void analyseArguments(String[] args) throws ArgumentsException {

		for (int i = 0; i < args.length; i++) { // traiter les arguments 1 par 1

			if (args[i].matches("-s")) {
				affichage = true;
			} else if (args[i].matches("-seed")) {
				i++;
				String argSeed = getArgumentOrThrows(args, i, "Pas de valeur du paramètre de seed renseignée");
				try {
					seed = Integer.valueOf(argSeed);
				} catch (Exception e) {
					throw new ArgumentsException("Valeur du paramètre -seed invalide : " + argSeed);
				}
			} else if (args[i].matches("-mess")) {
				i++;
				// traiter la valeur associee
				messageString = getArgumentOrThrows(args, i, "Pas de valeur du paramètre de message renseignée ");
				if (messageString.matches("[0,1]{7,}")) { // au moins 7 digits
					messageAleatoire = false;
					nbBitsMess = args[i].length();
				} else if (messageString.matches("[0-9]{1,6}")) { // de 1 à 6 chiffres
					messageAleatoire = true;
					nbBitsMess = Integer.parseInt(args[i]);
					if (nbBitsMess < 1)
						throw new ArgumentsException("Valeur du paramètre -mess invalide : " + nbBitsMess);
				} else
					throw new ArgumentsException("Valeur du paramètre -mess invalide : " + messageString);
			} else if (args[i].matches("-form")) {
				transmissionAnalogique = true;
				i++;
				String argForm = getArgumentOrThrows(args, i, "Pas de valeur du paramètre de forme d'onde renseignée");
				if (args[i].matches("NRZ")) {
					formatSignal = SourceAnalogiqueType.NRZ;
				} else if (args[i].matches("NRZT")) {
					formatSignal = SourceAnalogiqueType.NRZT;
				} else if (args[i].matches("RZ")) {
					formatSignal = SourceAnalogiqueType.RZ;
				} else {
					throw new ArgumentsException(
							"Argument invalide pour la forme d'onde, attendu : RZ | NRZ | NRZT, reçu : " + args[i]);
				}
			} else if (args[i].matches("-nbEch")) {
				i++;
				String argNbEch = getArgumentOrThrows(args, i,
						"Pas de valeur du paramètre de nombre d'échantillons renseignée");
				if (args[i].matches("[0-9]+")) {
					nbEch = Integer.parseInt(args[i]);
					if (nbEch % 3 != 0) {
						nbEch = nbEch - nbEch % 3 + 3;
						System.out.println("\tAttention: Le nombre d'échantillons a été ajusté à " + nbEch);
					}
				} else
					throw new ArgumentsException("Valeur du parametre -nbEch invalide : " + args[i]);
			} else if (args[i].matches("-ampl")) {
				i++;
				String argAmpl = getArgumentOrThrows(args, i, "Pas de valeur du paramètre d'amplitude renseignée");
				if (args[i].matches("-?[0-9]+([.,][0-9]+)?")) {
					ampl_min = Float.parseFloat(args[i].replace(',', '.'));
					i++;
					if (args[i].matches("-?[0-9]+([.,][0-9]+)?")) {
						ampl_max = Float.parseFloat(args[i].replace(',', '.'));
					} else
						throw new ArgumentsException("Valeur du parametre amplitude max invalide : " + args[i]);
				} else
					throw new ArgumentsException("Valeur du parametre amplitude min invalide : " + args[i]);

			} else if (args[i].matches("-snrpb")) {
				i++;
				// match regex d'un float

				if (getArgumentOrThrows(args, i, "Pas de valeur du paramètre de signal à bruit renseignée")
						.matches("-?[0-9]+([.,][0-9]+)?")) {
					messageBruitee = true;
					snrpb = Float.parseFloat(args[i].replace(',', '.'));

				} else
					throw new ArgumentsException("Valeur du parametre signal a bruit invalide : " + args[i]);

			} else if (args[i].matches("-ti")) {
				int count = 0;
				// TODO vérifie les deux valeurs suivantes de args et les stocks dans des
				// variables
				// TODO faire en sorte de vérifier la présence de jusqu'à 5 couples de valeurs
				// TODO appel fonction associée
			} else
				throw new ArgumentsException("Option invalide :" + args[i]);
		}

		// Vérification de la cohérence des arguments passés

		if (formatSignal == SourceAnalogiqueType.RZ && ampl_min != 0) {
			throw new ArgumentsException(
					"Attention : Pour une forme d'onde impulsionnelle (RZ), l'amplitude min est forcément égale à 0");
		}

		if (ampl_max <= ampl_min)
			throw new ArgumentsException(
					"L'amplitude min ne peut pas être supérieure ou égale à l'amplitude max, valeurs renseignées :\nmin : "
							+ ampl_min + ", max : " + ampl_max);

		if (formatSignal == SourceAnalogiqueType.NRZ || formatSignal == SourceAnalogiqueType.NRZT) {
			if (ampl_max < 0)
				throw new ArgumentsException(
						"Pour une forme d'onde rectangulaire ou trapézoïdale (NRZ/NRZT), la valeur de l'amplitude max doit être supérieure ou égale à 0, valeur renseignée : "
								+ ampl_max);
			if (ampl_min > 0)
				throw new ArgumentsException(
						"Pour une forme d'onde rectangulaire ou trapézoïdale (NRZ/NRZT), la valeur de l'amplitude min doit être inférieure ou égale à 0, valeur renseignée : "
								+ ampl_min);
		}
	}

	/**
	 * La méthode execute effectue un envoi de message par la source de la chaîne de
	 * transmission du Simulateur.
	 *
	 * @throws Exception si un problème survient lors de l'exécution
	 */
	public void execute() throws Exception {
		getSource().emettre();
	}

	/**
	 * La méthode qui calcule le taux d'erreur binaire en comparant les bits du
	 * message émis avec ceux du message reçu.
	 *
	 * @return La valeur du Taux dErreur Binaire.
	 */
	public float calculTauxErreurBinaire() {
		TEB = transmissionAnalogique ? calculTEBAnalogique() : calculTEBLogique();
		return TEB;
	}

	private float calculTEBLogique() {
		int nbBitEronnes = 0;
		Information<?> src = getSource().getInformationEmise();
		Information<?> dst = getDestination().getInformationRecue();
		for (int i = 0; i < nbBitsMess; i++) {
			if (src.iemeElement(i) != dst.iemeElement(i)) {
				nbBitEronnes++;
			}
		}
		return (float) nbBitEronnes / (float) nbBitsMess;
	}

	private float calculTEBAnalogique() {
		int nbBitEronnes = 0;
		float moy_src, moy_dst, somme_src, somme_dst;
		float moy_ampl = (ampl_max + ampl_min) / 2.0f;
		Information<?> src = getSource().getInformationEmise();
		Information<?> dst = getDestination().getInformationRecue();
		for (int i = 0; i < nbBitsMess; i++) {
			somme_src = 0;
			somme_dst = 0;
			for (int j = 0; j < nbEch; j++) {
				somme_src += (float) src.iemeElement(i * nbEch + j);
				somme_dst += (float) dst.iemeElement(i * nbEch + j);
			}
			// Calcul de la moyenne Source et destination
			moy_src = (float) Math.round((somme_src / (float) nbEch) * 100) / 100;
			moy_dst = (float) Math.round((somme_dst / (float) nbEch) * 100) / 100;

			// si c'est un 0 à la source
			if (moy_src < moy_ampl) {
				if (moy_dst > moy_ampl) {
					nbBitEronnes++;
				}
			} else { // si c'est un 1 à la source
				if (moy_dst < moy_ampl) {
					nbBitEronnes++;
				}
			}
		}
		return (float) nbBitEronnes / (float) nbBitsMess;
	}

	public Source<?> getSource() {
		return transmissionAnalogique ? sourceAnalogique : sourceLogique;
	}

	public Destination<?> getDestination() {
		return transmissionAnalogique ? destinationAnalogique : destinationLogique;
	}

	public static String getArgumentOrThrows(String[] args, int index, String error) throws ArgumentsException {
		if (args.length <= index) {
			throw new ArgumentsException(error);
		}
		return args[index];
	}

	/**
	 * La fonction main instancie un Simulateur à l'aide des arguments paramètres et
	 * affiche le résultat de l'exécution d'une transmission.
	 *
	 * @param args les différents arguments qui serviront à l'instanciation du
	 *             Simulateur.
	 */

	public static void main(String[] args) {

		Simulateur simulateur = null;

		final long start = System.nanoTime();

		try {
			simulateur = new Simulateur(args);
		} catch (Exception e) {
			System.out.println(e);
			System.exit(-1);
		}

		final long executionStart = System.nanoTime();

		try {
			simulateur.execute();

			final long end = System.nanoTime();

			System.out.printf("Temps construction : %.2fms\nTemps d'execution : %.2fms\nTemps total : %.2fms\n",
					(executionStart - start) * 1f / 1000000, (end - executionStart) * 1f / 1000000,
					(end - start) * 1f / 1000000);

			// Condition temporaire nécessaire pour l'affichage de courbe utile au rapport
			// mais non prévu dans la programmation des sondes
			if (simulateur.affichage) {
				SondeLogique sondeEmise = new SondeLogique("Message binaire", 1080);
				sondeEmise.recevoir(simulateur.getSource().getInformationBinaire());

				SondeAnalogique sondeDest = new SondeAnalogique("Information reçue par la destination");
				sondeDest.recevoir(simulateur.destinationAnalogique.getInformationRecue());
			}
			String s = "java  Simulateur  ";
			for (int i = 0; i < args.length; i++) { // copier tous les paramètres de simulation
				s += args[i] + "  ";
			}
			System.out.println(s + "  =>   TEB : " + simulateur.calculTauxErreurBinaire());
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			System.exit(-2);
		}
	}
}

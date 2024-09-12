package simulateur;

import destinations.Destination;
import destinations.DestinationFinale;
import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;
import sources.Source;
import sources.SourceAleatoire;
import sources.SourceFixe;
import sources.analogique.SourceAnalogiqueType;
import sources.analogique.SourceNRZ;
import sources.analogique.SourceNRZT;
import sources.analogique.SourceRZ;
import transmetteurs.Transmetteur;
import transmetteurs.TransmetteurParfait;
import visualisations.SondeAnalogique;
import visualisations.SondeLogique;


/**
 * La classe Simulateur permet de construire et simuler une chaîne de
 * transmission composée d'une Source, d'un nombre variable de
 * Transmetteur(s) et d'une Destination.
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
     * indique si le Simulateur utilise un message généré de manière aléatoire (message imposé sinon)
     */
    private boolean messageAleatoire = true;

    /**
     * indique si le Simulateur utilise un germe pour initialiser les générateurs aléatoires
     */
    private boolean aleatoireAvecGerme = false;

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
     * le  composant Source de la chaine de transmission
     */
    private Source<Float> source = null;

    /**
     * le  composant Transmetteur parfait logique de la chaine de transmission
     */
    private Transmetteur<Float, Float> transmetteurLogique = null;

    /**
     * le  composant Destination de la chaine de transmission
     */
    private Destination<Float> destination = null;

    private SourceAnalogiqueType formatSignal = SourceAnalogiqueType.RZ;

    private int nbEch = 30;

    private float ampl_min = 0.0f, ampl_max = 1.0f;


    /**
     * Le constructeur de Simulateur construit une chaîne de
     * transmission composée d'une Source <Boolean>, d'une Destination
     * <Boolean> et de Transmetteur(s) [voir la méthode
     * analyseArguments]...  <br> Les différents composants de la
     * chaîne de transmission (Source, Transmetteur(s), Destination,
     * Sonde(s) de visualisation) sont créés et connectés.
     *
     * @param args le tableau des différents arguments.
     * @throws ArgumentsException si un des arguments est incorrect
     */
    public Simulateur(String[] args) throws ArgumentsException {
        // analyser et récupérer les arguments
        analyseArguments(args);
//        source = messageAleatoire ? new SourceAleatoire(nbBitsMess, seed) : new SourceFixe(messageString);
//        transmetteurLogique = new TransmetteurParfait<Float>();
//        source.connecter(transmetteurLogique);

        if (!messageAleatoire) {
            switch (formatSignal) {
                case RZ -> source = new SourceRZ(messageString, nbEch, ampl_min, ampl_max);
                case NRZ -> source = new SourceNRZ(messageString, nbEch, ampl_min, ampl_max);
                case NRZT -> source = new SourceNRZT(messageString, nbEch, ampl_min, ampl_max);
            }
        } else {
            switch (formatSignal) {
                case RZ -> source = new SourceRZ(nbEch, ampl_min, ampl_max, nbBitsMess, seed);
                case NRZ -> source = new SourceNRZ(nbEch, ampl_min, ampl_max, nbBitsMess, seed);
                case NRZT -> source = new SourceNRZT(nbEch, ampl_min, ampl_max, nbBitsMess, seed);
            }
        }

        transmetteurLogique = new TransmetteurParfait<>();
        source.connecter(transmetteurLogique);

        destination = new DestinationFinale<>();
        transmetteurLogique.connecter(destination);

        if (affichage) {
            source.connecter(new SondeAnalogique("Entrée"));
            transmetteurLogique.connecter(new SondeAnalogique("Transmetteur"));
        }

//
//        if (affichage)
//            source.connecter(new SondeLogique("Entrée", 720));
//        destination = new DestinationFinale<Boolean>();
//        transmetteurLogique.connecter(destination);
//        if (affichage)
//            transmetteurLogique.connecter(new SondeLogique("Sortie", 720));
    }


    /**
     * La méthode analyseArguments extrait d'un tableau de chaînes de
     * caractères les différentes options de la simulation.  <br>Elle met
     * à jour les attributs correspondants du Simulateur.
     *
     * @param args le tableau des différents arguments.
     *             <br>
     *             <br>Les arguments autorisés sont :
     *             <br>
     *             <dl>
     *             <dt> -mess m  </dt><dd> m (String) constitué de 7 ou plus digits à 0 | 1, le message à transmettre</dd>
     *             <dt> -mess m  </dt><dd> m (int) constitué de 1 à 6 digits, le nombre de bits du message "aléatoire" à transmettre</dd>
     *             <dt> -s </dt><dd> pour demander l'utilisation des sondes d'affichage</dd>
     *             <dt> -seed v </dt><dd> v (int) d'initialisation pour les générateurs aléatoires</dd>
     *             </dl>
     * @throws ArgumentsException si un des arguments est incorrect.
     */
    private void analyseArguments(String[] args) throws ArgumentsException {

        for (int i = 0; i < args.length; i++) { // traiter les arguments 1 par 1

            if (args[i].matches("-s")) {
                affichage = true;
            } else if (args[i].matches("-seed")) {
                aleatoireAvecGerme = true;
                i++;
                try {
                    seed = Integer.valueOf(args[i]);
                } catch (Exception e) {
                    throw new ArgumentsException("Valeur du parametre -seed  invalide :" + args[i]);
                }
            } else if (args[i].matches("-mess")) {
                i++;
                // traiter la valeur associee
                messageString = args[i];
                if (args[i].matches("[0,1]{7,}")) { // au moins 7 digits
                    messageAleatoire = false;
                    nbBitsMess = args[i].length();
                } else if (args[i].matches("[0-9]{1,6}")) { // de 1 à 6 chiffres
                    messageAleatoire = true;
                    nbBitsMess = Integer.parseInt(args[i]);
                    if (nbBitsMess < 1)
                        throw new ArgumentsException("Valeur du parametre -mess invalide : " + nbBitsMess);
                } else
                    throw new ArgumentsException("Valeur du parametre -mess invalide : " + args[i]);
            } else if (args[i].matches("-form")) {
                i++;
                if (args[i].matches("NRZ")) {
                    formatSignal = SourceAnalogiqueType.NRZ;
                } else if (args[i].matches("NRZT")) {
                    formatSignal = SourceAnalogiqueType.NRZT;
                }
            } else if (args[i].matches("-nbEch")) {
                i++;
                if (args[i].matches("[0-9]+")) {
                    nbEch = Integer.parseInt(args[i]);
                } else throw new ArgumentsException("Valeur du parametre -nbEch invalide : " + args[i]);
            } else if (args[i].matches("-ampl")) {
                i++;
                if (args[i].matches("-?[0-9]+([.,][0-9]+)?")) {
                    ampl_min = Float.parseFloat(args[i]);
                    i++;
                    if (args[i].matches("-?[0-9]+([.,][0-9]+)?")) {
                        ampl_max = Float.parseFloat(args[i]);
                        i++;
                    } else throw new ArgumentsException("Valeur du parametre amplitude min invalide : " + args[i]);
                } else throw new ArgumentsException("Valeur du parametre amplitude max invalide : " + args[i]);

            } else if (args[i].matches("-snrpb")) {
                i++;
                if (args[i].matches("[0-9]+.[0-9]+")) {
                    //TODO appel fonction associée
                } else throw new ArgumentsException("Valeur du parametre signal a bruit invalide : " + args[i]);

            } else if (args[i].matches("-ti")) {
                int count = 0;
                //TODO vérifie les deux valeurs suivantes de args et les stocks dans des variables
                //TODO faire en sorte de vérifier la présence de jusqu'à 5 couples de valeurs
                //TODO appel fonction associée
            }


            //TODO : ajouter ci-après le traitement des nouvelles options

            else throw new ArgumentsException("Option invalide :" + args[i]);
        }

    }


    /**
     * La méthode execute effectue un envoi de message par la source
     * de la chaîne de transmission du Simulateur.
     *
     * @throws Exception si un problème survient lors de l'exécution
     */
    public void execute() throws Exception {
        source.emettre();
    }


    /**
     * La méthode qui calcule le taux d'erreur binaire en comparant
     * les bits du message émis avec ceux du message reçu.
     *
     * @return La valeur du Taux dErreur Binaire.
     */
    public float calculTauxErreurBinaire() {
        int nbBitEronnes = 0;
        Information<?> src = source.getInformationEmise();
        Information<?> dst = destination.getInformationRecue();
        for (int i = 0; i < nbBitsMess; i++) {
            if (src.iemeElement(i) != dst.iemeElement(i)) {
                nbBitEronnes++;
            }
        }
        return (float) nbBitEronnes / (float) nbBitsMess;
    }


    /**
     * La fonction main instancie un Simulateur à l'aide des
     * arguments paramètres et affiche le résultat de l'exécution
     * d'une transmission.
     *
     * @param args les différents arguments qui serviront à l'instanciation du Simulateur.
     */
    public static void main(String[] args) {

        Simulateur simulateur = null;

        try {
            simulateur = new Simulateur(args);
        } catch (Exception e) {
            System.out.println(e);
            System.exit(-1);
        }

        try {
            simulateur.execute();
            String s = "java  Simulateur  ";
            for (int i = 0; i < args.length; i++) { //copier tous les paramètres de simulation
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


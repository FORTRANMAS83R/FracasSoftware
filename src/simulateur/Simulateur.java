package simulateur;

import transmetteurs.*;
import destinations.Destination;
import destinations.DestinationFinale;
import information.Information;
import information.InformationNonConformeException;
import sources.Source;
import sources.SourceAleatoire;
import sources.SourceFixe;
import visualisations.SondeAnalogique;
import visualisations.SondeDiagrammeDeLoeil;
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
    private Transmetteur<Float,Float> egaliseur = null;
    /**
     * le composant Configuration de la chaine de transmission. Utilisé pour traiter les paramètres de la simulation.
     */
    private final Configurations config;

    private final Source<Boolean> source;

    private Codeur<Boolean, Boolean> codeur = null;
    private Decodeur<Boolean, Boolean> decodeur = null;

    /**
     * le composant Transmetteur parfait logique de la chaine de transmission
     */
    private Transmetteur<Boolean, Boolean> transmetteurLogique = null;
    /**
     * le composant Transmetteur analogique de la chaine de transmission
     */
    private Transmetteur<Float, Float> transmetteurAnalogique = null;

    private ConvertisseurNumeriqueAnalogique<Boolean, Float> convertisseurNumeriqueAnalogique = null;

    /**
     * le composant Convertisseur analogique-numérique de la chaine de transmission
     */
    private ConvertisseurAnalogiqueNumerique<Float, Boolean> convertisseurAnalogiqueNumerique = null;
    /**
     * le composant Destination de la chaine de transmission
     */
    private Destination<Boolean> destination = null;

    /**
     * le composant TEB de la chaine de transmission
     */
    private float TEB = 0.0f;

    /**
     * Le constructeur de Simulateur construit une chaîne de transmission composée
     * d'une Source boolean, d'une Destination boolean et de Transmetteur(s) [voir
     * la méthode analyseArguments]... <br>
     * Les différents composants de la chaîne de transmission (Source,
     * Transmetteur(s), Destination, Sonde(s) de visualisation) sont créés et
     * connectés.
     *
     * @param args le tableau des différents arguments.
     * @throws ArgumentsException si un des arguments est incorrect
     */

    public Simulateur(String[] args) throws ArgumentsException {
        // analyser et récupérer les arguments
        config = new Configurations(args);

        // Instanciation de la source et destination
        source = config.getMessageAleatoire() ? new SourceAleatoire(config.getNbBitsMess(), config.getSeed()) : new SourceFixe(config.getMessageString());
        destination = new DestinationFinale<>();

        if (config.getTransmissionAnalogique()) {
            // Analogique
            // CAN
            convertisseurNumeriqueAnalogique = new ConvertisseurNumeriqueAnalogique<>(config.getNbEch(), config.getAmplMin(), config.getAmplMax(), config.getFormatSignal());
            if (config.getCodeur()) {
                codeur = new Codeur<>();
                source.connecter(codeur);
                codeur.connecter(convertisseurNumeriqueAnalogique);
            } else {
                source.connecter(convertisseurNumeriqueAnalogique);
            }

            // Instanciation du transmetteur
            if (!config.getMultiTrajets().isEmpty()) {
                transmetteurAnalogique = config.getMessageBruitee() ? new TransmetteurMultiTrajet(config.getMultiTrajets(), config.getSnrpb()) : new TransmetteurMultiTrajet(config.getMultiTrajets());
            } else if (config.getMessageBruitee()) {
                transmetteurAnalogique = new TransmetteurBruite<>(config.getSnrpb());
                transmetteurAnalogique.connecter(new SondeHistogramme("Histogramme de l'information reçue", config.getAffichage()));
            } else {
                transmetteurAnalogique = new TransmetteurParfait<>();
            }

            // Connexion CNA au transmetteur
            convertisseurNumeriqueAnalogique.connecter(transmetteurAnalogique);

            // Instanciation CAN et connexion du transmetteur au CAN
            convertisseurAnalogiqueNumerique = new ConvertisseurAnalogiqueNumerique<>(config.getNbEch(), config.getNbBitsMess(), (config.getAmplMin()+config.getAmplMax())/2, config.getFormatSignal());
            if(!config.getMultiTrajets().isEmpty()){
                egaliseur = new Egaliseur(config.getMultiTrajets(), convertisseurNumeriqueAnalogique);
                transmetteurAnalogique.connecter(egaliseur);
                egaliseur.connecter(convertisseurAnalogiqueNumerique);
            }
            else{
                transmetteurAnalogique.connecter(convertisseurAnalogiqueNumerique);
            }

            // Connexion du CAN à la destination
            if (config.getCodeur()) {
                decodeur = new Decodeur<>();
                convertisseurAnalogiqueNumerique.connecter(decodeur);
                decodeur.connecter(destination);
            } else {
                convertisseurAnalogiqueNumerique.connecter(destination);
            }

            if (config.getAffichage()) {
                source.connecter(new SondeLogique("Sonde en sortie de la source",720));
                transmetteurAnalogique.connecter(new SondeAnalogique("Sonde en sortie du transmetteur"));
                transmetteurAnalogique.connecter(new SondeDiagrammeDeLoeil("Diagramme de l'oeil"));
                egaliseur.connecter(new SondeAnalogique("Sonde en sortie de l'égaliseur"));
            }
        } else {
            // Logique
            transmetteurLogique = new TransmetteurParfait<>();

            source.connecter(transmetteurLogique);
            transmetteurLogique.connecter(destination);

            if (config.getAffichage()) {
                source.connecter(new SondeLogique("Sonde en sortie de la source", 300));
                transmetteurLogique.connecter(new SondeLogique("Sonde en sortie du transmetteur", 300));
            }
        }

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

            System.out.printf("Temps construction : %.2fms\nTemps d'execution : %.2fms\nTemps total : %.2fms\n", (executionStart - start) * 1f / 1000000, (end - executionStart) * 1f / 1000000, (end - start) * 1f / 1000000);

            // Condition temporaire nécessaire pour l'affichage de courbe utile au rapport
            // mais non prévu dans la programmation des sondes
            // if (simulateur.affichage) {
            // SondeLogique sondeEmise = new SondeLogique("Message binaire", 1080);
            // sondeEmise.recevoir(simulateur.getSource().getInformationBinaire());
            //
            // SondeAnalogique sondeDest = new SondeAnalogique("Information reçue par la
            // destination");
            // sondeDest.recevoir(simulateur.destinationAnalogique.getInformationRecue());
            // }
            StringBuilder s = new StringBuilder("java  Simulateur  ");
            for (String arg : args) { // copier tous les paramètres de simulation
                s.append(arg).append("  ");
            }
            System.out.println(s + "  =>   TEB : " + simulateur.calculTauxErreurBinaire());
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            System.exit(-2);
        }
    }

    /**
     * La méthode renvoie l'objet Configurations qui contient les paramètres de la
     * simulation.
     *
     * @return L'objet Configurations
     */
    public Configurations getConfig() {
        return config;
    }

    /**
     * La méthode execute effectue un envoi de message par la source de la chaîne de
     * transmission du Simulateur.
     *
     * @throws InformationNonConformeException si un problème survient lors de
     *                                         l'exécution
     */
    public void execute() throws InformationNonConformeException {
        getSource().emettre();
    }

    /**
     * La méthode qui calcule le taux d'erreur binaire en comparant les bits du
     * message émis avec ceux du message reçu.
     *
     * @return La valeur du Taux dErreur Binaire.
     */
    public float calculTauxErreurBinaire() {
        int nbBitEronnes = 0;
//        Information<?> src = config.getTransmissionAnalogique() ? getSource().getInformationBinaire() : getSource().getInformationEmise();
        Information<?> src = getSource().getInformationEmise();
        Information<?> dst = getDestination().getInformationRecue();
        for (int i = 0; i < config.getNbBitsMess(); i++) {
            if (src.iemeElement(i) != dst.iemeElement(i)) {
                nbBitEronnes++;
            }
        }
        TEB = (float) nbBitEronnes / (float) config.getNbBitsMess();
        return TEB;
    }

    /**
     * Getter pour transmission analogique
     *
     * @return L'état du simulateur, true = analogique, false = logique
     */
    public boolean getTransmissionAnalogique() {
        return config.getTransmissionAnalogique();
    }

    public Source<?> getSource() {
        return source;
//        return config.getTransmissionAnalogique() ? sourceAnalogique : sourceLogique;
    }

    public Destination<?> getDestination() {
        return destination;
    }
}

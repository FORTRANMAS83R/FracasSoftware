package simulateur;

import transmetteurs.ConvertisseurAnalogiqueNumerique;
import destinations.Destination;
import destinations.DestinationFinale;
import information.Information;
import information.InformationNonConformeException;
import sources.Source;
import sources.SourceAleatoire;
import sources.SourceFixe;
import sources.analogique.SourceNRZ;
import sources.analogique.SourceNRZT;
import sources.analogique.SourceRZ;
import transmetteurs.Transmetteur;
import transmetteurs.TransmetteurBruite;
import transmetteurs.TransmetteurMultiTrajet;
import transmetteurs.TransmetteurParfait;
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

    private Configurations config;

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

    private ConvertisseurAnalogiqueNumerique<Float, Boolean> convertisseurAnalogiqueNumerique = null;
    /**
     * le composant Destination de la chaine de transmission
     */
    private Destination<Boolean> destinationLogique = null;
    private Destination<Float> destinationAnalogique = null;
    private Destination<Boolean> destination = null;

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
        // analyseArguments(args);
        destination = new DestinationFinale<>();
        if (config.getTransmissionAnalogique()) {
            // Analogique
            if (!config.getMessageAleatoire()) {
                switch (config.getFormatSignal()) {
                    case RZ ->
                            sourceAnalogique = new SourceRZ(config.getMessageString(), config.getNbEch(), config.getAmplMin(), config.getAmplMax());
                    case NRZ ->
                            sourceAnalogique = new SourceNRZ(config.getMessageString(), config.getNbEch(), config.getAmplMin(), config.getAmplMax());
                    case NRZT ->
                            sourceAnalogique = new SourceNRZT(config.getMessageString(), config.getNbEch(), config.getAmplMin(), config.getAmplMax());
                }
            } else {
                switch (config.getFormatSignal()) {
                    case RZ ->
                            sourceAnalogique = new SourceRZ(config.getNbEch(), config.getAmplMin(), config.getAmplMax(), config.getNbBitsMess(), config.getSeed());
                    case NRZ ->
                            sourceAnalogique = new SourceNRZ(config.getNbEch(), config.getAmplMin(), config.getAmplMax(), config.getNbBitsMess(), config.getSeed());
                    case NRZT ->
                            sourceAnalogique = new SourceNRZT(config.getNbEch(), config.getAmplMin(), config.getAmplMax(), config.getNbBitsMess(), config.getSeed());
                }
            }

            if (config.getMultiTrajets().size() > 0) {
                transmetteurAnalogique = config.getMessageBruitee() ? new TransmetteurMultiTrajet(config.getMultiTrajets(), config.getSnrpb()) : new TransmetteurMultiTrajet(config.getMultiTrajets());
            } else if (config.getMessageBruitee()) {
                transmetteurAnalogique = new TransmetteurBruite(config.getSnrpb());
                transmetteurAnalogique.connecter(new SondeHistogramme("Histogramme de l'information reçue", config.getAffichage()));
            } else {
                transmetteurAnalogique = new TransmetteurParfait<>();
            }

            sourceAnalogique.connecter(transmetteurAnalogique);
            convertisseurAnalogiqueNumerique = new ConvertisseurAnalogiqueNumerique<>((config.getAmplMin() + config.getAmplMax())/2.0f, config.getNbEch(), config.getNbBitsMess());
            destinationLogique = new DestinationFinale<>();
            transmetteurAnalogique.connecter(convertisseurAnalogiqueNumerique);
            convertisseurAnalogiqueNumerique.connecter(destination);

            if (config.getAffichage()) {
                sourceAnalogique.connecter(new SondeAnalogique("Sonde en sortie de la source"));
                transmetteurAnalogique.connecter(new SondeAnalogique("Sonde en sortie du transmetteur"));
                transmetteurAnalogique.connecter(new SondeDiagrammeDeLoeil("Diagramme de l'oeil"));
            }
        } else {
            // Logique
            sourceLogique = config.getMessageAleatoire() ? new SourceAleatoire(config.getNbBitsMess(), config.getSeed()) : new SourceFixe(config.getMessageString());
//            destinationLogique = new DestinationFinale<>();
            transmetteurLogique = new TransmetteurParfait<>();

            sourceLogique.connecter(transmetteurLogique);
            transmetteurLogique.connecter(destination);

            if (config.getAffichage()) {
                sourceLogique.connecter(new SondeLogique("Sonde en sortie de la source", 300));
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
        Information<?> src = config.getTransmissionAnalogique() ? getSource().getInformationBinaire() : getSource().getInformationEmise();
        Information<?> dst = getDestination().getInformationRecue();
        for (int i = 0; i < config.getNbBitsMess(); i++) {
            if (src.iemeElement(i) != dst.iemeElement(i)) {
                nbBitEronnes++;
            }
        }
        TEB =  (float) nbBitEronnes / (float) config.getNbBitsMess();
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
        return config.getTransmissionAnalogique() ? sourceAnalogique : sourceLogique;
    }

    public Destination<?> getDestination() {
        return destination;
    }
}

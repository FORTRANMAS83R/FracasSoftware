package simulateur;

import sources.analogique.SourceAnalogiqueType;

import static simulateur.Simulateur.getArgumentOrThrows;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

public class Configurations {
    private boolean affichage = false;
    private boolean messageAleatoire = true;
    private boolean transmissionAnalogique = false;
    private boolean messageBruitee = false;
    private Float snrpb = 0.0f;
    private Integer seed = null; // pas de semence par défaut
    private int nbBitsMess = 100;
    private String messageString = "100";
    private int nbEch = 30;
    private float ampl_min = 0.0f, ampl_max = 1.0f;
    private SourceAnalogiqueType formatSignal = SourceAnalogiqueType.RZ;
    private List<SimpleEntry<Integer, Float>> multiTrajets = new ArrayList<>();

    public Configurations(String[] args) throws ArgumentsException {
        analyseArguments(args);

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
                this.affichage = true;
            } else if (args[i].matches("-seed")) {
                i++;
                String argSeed = getArgumentOrThrows(args, i, "Pas de valeur du paramètre de seed renseignée");
                try {
                    this.seed = Integer.valueOf(argSeed);
                } catch (Exception e) {
                    throw new ArgumentsException("Valeur du paramètre -seed invalide : " + argSeed);
                }
            } else if (args[i].matches("-mess")) {
                i++;
                // traiter la valeur associee
                this.messageString = getArgumentOrThrows(args, i, "Pas de valeur du paramètre de message renseignée ");
                if (this.messageString.matches("[0,1]{7,}")) { // au moins 7 digits
                    this.messageAleatoire = false;
                    this.nbBitsMess = this.messageString.length();
                } else if (this.messageString.matches("[0-9]{1,6}")) { // de 1 à 6 chiffres
                    this.messageAleatoire = true;
                    this.nbBitsMess = Integer.parseInt(this.messageString);
                    if (this.nbBitsMess < 1)
                        throw new ArgumentsException("Valeur du paramètre -mess invalide : " + this.nbBitsMess);
                } else
                    throw new ArgumentsException("Valeur du paramètre -mess invalide : " + this.messageString);
            } else if (args[i].matches("-form")) {
                transmissionAnalogique = true;
                i++;
                String argForm = getArgumentOrThrows(args, i, "Pas de valeur du paramètre de forme d'onde renseignée");
                if (argForm.matches("NRZ")) {
                    this.formatSignal = SourceAnalogiqueType.NRZ;
                } else if (argForm.matches("NRZT")) {
                    this.formatSignal = SourceAnalogiqueType.NRZT;
                } else if (argForm.matches("RZ")) {
                    this.formatSignal = SourceAnalogiqueType.RZ;
                } else {
                    throw new ArgumentsException(
                            "Argument invalide pour la forme d'onde, attendu : RZ | NRZ | NRZT, reçu : " + args[i]);
                }
            } else if (args[i].matches("-nbEch")) {
                i++;
                transmissionAnalogique = true;
                String argNbEch = getArgumentOrThrows(args, i,
                        "Pas de valeur du paramètre de nombre d'échantillons renseignée");
                if (argNbEch.matches("[0-9]+")) {
                    this.nbEch = Integer.parseInt(argNbEch);
                    if (this.nbEch % 3 != 0) {
                        this.nbEch = this.nbEch - this.nbEch % 3 + 3;
                        System.out.println("\tAttention: Le nombre d'échantillons a été ajusté à " + this.nbEch);
                    }
                } else
                    throw new ArgumentsException("Valeur du parametre -nbEch invalide : " + argNbEch);
            } else if (args[i].matches("-ampl")) {
                i++;
                String argAmpl = getArgumentOrThrows(args, i, "Pas de valeur du paramètre d'amplitude min renseignée");
                if (argAmpl.matches("-?[0-9]+([.,][0-9]+)?")) {
                    this.ampl_min = Float.parseFloat(argAmpl.replace(',', '.'));
                    i++;
                    argAmpl = getArgumentOrThrows(args, i, "Pas de valeur du paramètre d'amplitude max renseignée");
                    if (argAmpl.matches("-?[0-9]+([.,][0-9]+)?")) {
                        this.ampl_max = Float.parseFloat(argAmpl.replace(',', '.'));
                    } else
                        throw new ArgumentsException("Valeur du parametre amplitude max invalide : " + argAmpl);
                } else
                    throw new ArgumentsException("Valeur du parametre amplitude min invalide : " + argAmpl);

            } else if (args[i].matches("-snrpb")) {
                transmissionAnalogique = true;
                i++;
                // match regex d'un float
                String argSnrPb = getArgumentOrThrows(args, i,
                        "Pas de valeur du paramètre de signal à bruit renseignée");
                if (argSnrPb.matches("-?[0-9]+([.,][0-9]+)?")) {
                    this.messageBruitee = true;
                    this.snrpb = Float.parseFloat(argSnrPb.replace(',', '.'));

                } else
                    throw new ArgumentsException("Valeur du parametre signal a bruit invalide : " + args[i]);

            } else if (args[i].matches("-ti")) {
                transmissionAnalogique = true;
                while (i < args.length - 1 && args[i + 1].matches("-?[0-9]+([.,][0-9]+)?")) {
                    i++;
                    int count = multiTrajets.size() + 1;

                    if (count > 5)
                        throw new ArgumentsException("Nombre de trajets supérieur à 5");

                    int dt;
                    float ar;

                    try {
                        dt = Integer.parseInt(args[i]);
                    } catch (Exception e) {
                        throw new ArgumentsException(
                                "Paramètre dt" + count + " invalide : " + args[i]);
                    }

                    if (dt < 0)
                        throw new ArgumentsException(
                                "Le paramètre dt doit être positif (>= 0), dt" + count + " : " + args[i]);

                    i++;

                    String arStr = getArgumentOrThrows(args, i,
                            "Pas de valeur du paramètre ti a" + count + " renseignée");

                    try {
                        ar = Float.parseFloat(
                                arStr.replace(',', '.'));
                    } catch (Exception e) {
                        throw new ArgumentsException(
                                "Paramètre ar" + count + " invalide : " + arStr);
                    }

                    if (ar < 0 || ar > 1)
                        throw new ArgumentsException(
                                "Le paramètre ar doit être compris entre 0 inclus et 1 inclus, ar" + count + " : "
                                        + arStr);

                    multiTrajets.add(new SimpleEntry<>(dt, ar));
                }

                if (multiTrajets.isEmpty()) {
                    throw new ArgumentsException(
                            "Pas de valeur du paramètre de trajet multiple renseignée ou paramètre dt1 invalide");
                }
                Float sum = 0.0f;
                for (int j = 0; i < multiTrajets.size(); j++) {
                    sum += (float) Math.pow(multiTrajets.get(j).getValue(), 2);
                }
                if (sum > 1) {
                    throw new ArgumentsException(
                            "La somme des carrés des amplitudes des trajets multiples doit être inférieure ou égale à 1");
                }
            } else
                throw new ArgumentsException("Option invalide :" + args[i]);
        }

        // Vérification de la cohérence des arguments passés

        if (this.formatSignal == SourceAnalogiqueType.RZ && this.ampl_min != 0) {
            throw new ArgumentsException(
                    "Attention : Pour une forme d'onde impulsionnelle (RZ), l'amplitude min est forcément égale à 0");
        }

        if (this.ampl_max <= this.ampl_min)
            throw new ArgumentsException(
                    "L'amplitude min ne peut pas être supérieure ou égale à l'amplitude max, valeurs renseignées :\nmin : "
                            + this.ampl_min + ", max : " + this.ampl_max);

        if (this.formatSignal == SourceAnalogiqueType.NRZ || this.formatSignal == SourceAnalogiqueType.NRZT) {
            if (this.ampl_max < 0)
                throw new ArgumentsException(
                        "Pour une forme d'onde rectangulaire ou trapézoïdale (NRZ/NRZT), la valeur de l'amplitude max doit être supérieure ou égale à 0, valeur renseignée : "
                                + this.ampl_max);
            if (this.ampl_min > 0)
                throw new ArgumentsException(
                        "Pour une forme d'onde rectangulaire ou trapézoïdale (NRZ/NRZT), la valeur de l'amplitude min doit être inférieure ou égale à 0, valeur renseignée : "
                                + this.ampl_min);
        }
    }

    public boolean getAffichage() {
        return this.affichage;
    }

    public boolean getMessageAleatoire() {
        return this.messageAleatoire;
    }

    public boolean getTransmissionAnalogique() {
        return this.transmissionAnalogique;
    }

    public boolean getMessageBruitee() {
        return this.messageBruitee;
    }

    public Float getSnrpb() {
        return this.snrpb;
    }

    public Integer getSeed() {
        return this.seed;
    }

    public int getNbBitsMess() {
        return this.nbBitsMess;
    }

    public String getMessageString() {
        return this.messageString;
    }

    public int getNbEch() {
        return this.nbEch;
    }

    public float getAmplMin() {
        return this.ampl_min;
    }

    public float getAmplMax() {
        return this.ampl_max;
    }

    public SourceAnalogiqueType getFormatSignal() {
        return this.formatSignal;
    }

    public List<SimpleEntry<Integer, Float>> getMultiTrajets() {
        return this.multiTrajets;
    }

}

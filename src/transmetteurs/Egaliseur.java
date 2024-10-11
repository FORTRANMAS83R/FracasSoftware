package transmetteurs;
import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;
import sources.Source;
import utils.Operations;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Egaliseur extends Transmetteur<Float, Float> {
    private final int ordre; // Ordre du filtre de l'égaliseur
    private final Float[] coefficients; // Coefficients du filtre de l'égaliseur
    private Float mu; // Pas d'apprentissage (taux de convergence)
    private final ConvertisseurNumeriqueAnalogique<Boolean,Float> source;

    public Egaliseur(List<AbstractMap.SimpleEntry<Integer,Float>> multiTrajet, ConvertisseurNumeriqueAnalogique<Boolean,Float> source) {
        super();
        this.ordre = tauMax(multiTrajet) + 1;
        this.coefficients = new Float[ordre];
        this.source = source;
        // Initialiser les coefficients à zéro
        Arrays.fill(this.coefficients, 0.0f);
    }

    // Méthode pour égaliser le signal reçu
    public Float[] egaliser(ArrayList<Float> signalRecu, ArrayList<Float> signalRef) {
        int longueurSignal = signalRef.size();
        Float[] sortie = new Float[longueurSignal];
//        this.mu = 1/(ordre+ Operations.calculPuissanceSignal(signalRef));
        this.mu = 0.0f;
     //   this.mu = 2/(ordre+ Operations.calculPuissanceSignal(signalRef))-0.01f;
        // Vecteur d'entrée pour le filtre
        Float[] vecteurEntree = new Float[ordre];
        Arrays.fill(vecteurEntree, 0f);

        for (int n = 0; n < longueurSignal; n++) {
            // Mettre à jour le vecteur d'entrée
            for (int i = ordre - 1; i > 0; i--) {
                vecteurEntree[i] = vecteurEntree[i - 1];
            }
            vecteurEntree[0] = signalRecu.get(n);

            // Calculer la sortie de l'égaliseur
            Float sortieFiltre = 0.0f;
            for (int i = 0; i < ordre; i++) {
                sortieFiltre += coefficients[i] * vecteurEntree[i];
            }

            sortie[n] =  sortieFiltre;

            // Calculer l'erreur
            Float erreur = signalRef.get(n) - sortieFiltre;

            // Mettre à jour les coefficients du filtre
            for (int i = 0; i < ordre; i++) {
                coefficients[i] +=  mu * erreur * vecteurEntree[i];
            }

        }

        return sortie;
    }





    public static Integer tauMax(List<AbstractMap.SimpleEntry<Integer, Float>> trajets) {
        Integer maxTrajet = 0;
        for (AbstractMap.SimpleEntry<Integer, Float> trajet : trajets) {
            if (trajet.getKey() > maxTrajet) {
                maxTrajet = trajet.getKey();
            }
        }
        return maxTrajet;
    }


    @Override
    public void recevoir(Information<Float> information) throws InformationNonConformeException {
        this.informationRecue = information;
        if(this.ordre > 1){
            this.informationEmise = new Information<>(egaliser(information.getContent(), this.source.getInformationEmise().getContent()));    
        }
        else this.informationEmise = information;
        emettre();
    }

    @Override
    public void emettre() throws InformationNonConformeException {
        for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationEmise);
        }

    }
}


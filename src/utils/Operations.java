package utils;

import information.Information;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class Operations {
    /**
     * Retourne le plus grand retard d'un trajet multiple.
     *
     * @return le plus grand retard.
     */
    public static Integer tauMax(ArrayList<AbstractMap.SimpleEntry<Integer, Float>> trajets) {
        Integer maxTrajet = 0;
        for (AbstractMap.SimpleEntry<Integer, Float> trajet : trajets) {
            if (trajet.getKey() > maxTrajet) {
                maxTrajet = trajet.getKey();
            }
        }
        return maxTrajet;
    }

    /**
     * Méthode permettant de récupérer la puissance du signal
     *
     * @param signal: le signal
     * @return le bruit généré
     */
    public static Float calculPuissanceSignal(Information<Float> signal) {
        Float puissance = 0f;
        for (Float s : signal) {
            puissance += s * s;
        }
        return puissance / signal.nbElements();
    }

    public static Information<Float> multiply(Information<Float> information, Float a)
    {
        for(int i = 0; i < information.nbElements(); i++)
        {
            information.setIemeElement(i, information.iemeElement(i)*a);
        }
        return information;
    }

    public static Float setAmpTrajetDirect(List<AbstractMap.SimpleEntry<Integer, Float>> trajets) {
        Float sum = 0.0f;
        for (int i = 0; i < trajets.size(); i++) {
            sum += (float)Math.pow(trajets.get(i).getValue(),2);
        }
        return (float) Math.sqrt(1-sum);
    }

    public static Float calculPuissanceSignal(ArrayList<Float> signal) {
        Float puissance = 0f;
        for (Float s : signal) {
            puissance += s * s;
        }
        return puissance / signal.size();
    }
}

package transmetteurs;

import information.Information;
import information.InformationNonConformeException;

import java.util.ArrayList;
import java.util.Map;

public class TransmetteurMultiTrajet extends Transmetteur{

    private ArrayList<Map.Entry<Integer, Float>>trajets;
    public TransmetteurMultiTrajet(ArrayList<Map.Entry<Integer, Float>> trajets){
        super();
        this.trajets = trajets;
    }
    @Override
    public void recevoir(Information information) throws InformationNonConformeException {
        this.informationRecue = information.clone();

    }

    @Override
    public void emettre() throws InformationNonConformeException {

    }

    public Information multiTrajet(Information<Float> information){
        Information<Float> informationMultiTrajet = information.clone();
        //TODO: Ralonger la chaine du max du nb de bit tau
        for(int j=0; j<informationMultiTrajet.nbElements(); j++){
            for(Map.Entry<Integer, Float> trajet : trajets){
                if(!(trajet.getKey() < j)){
                    informationMultiTrajet.setIemeElement(j,information.iemeElement(j)+trajet.getValue()*information.iemeElement(j-trajet.getKey()));}
            }
        }
        return informationMultiTrajet;
    }
}

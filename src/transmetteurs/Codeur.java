package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class Codeur<R,E> extends Transmetteur<Boolean, Boolean> {
   //1-> 101
   //0-> 010

    public static Information<Boolean> codeCanal(Information<Boolean> information) {
        Information<Boolean> informationCodee = new Information<>();
        for (int i = 0; i < information.nbElements(); i++) {
            if (information.iemeElement(i)) {
                informationCodee.add(true);
                informationCodee.add(false);
                informationCodee.add(true);
            } else {
                informationCodee.add(false);
                informationCodee.add(true);
                informationCodee.add(false);
            }
        }
        return informationCodee;
    }

    @Override
    public void recevoir(Information<Boolean> information) throws InformationNonConformeException {
        this.informationRecue = information;
        this.informationEmise = codeCanal(information);
        emettre();
    }

    @Override
    public void emettre() throws InformationNonConformeException {
        for (DestinationInterface<Boolean> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationEmise);
        }
    }
}









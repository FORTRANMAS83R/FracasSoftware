package transmetteurs;

import bruit.BBG;
import destinations.DestinationFinale;
import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class TransmetteurBruite extends Transmetteur<Float,Float> {

    private Float SNRpb;

    public TransmetteurBruite(Float snrPB){
        super();
        this.SNRpb = snrPB;
    }
    @Override
    public void recevoir(Information information) throws InformationNonConformeException {
        this.informationRecue = information.clone();
        BBG bruit = new BBG(this.SNRpb);
        informationEmise = bruit.bruitage(this.informationRecue);
        emettre();
    }

    @Override
    public void emettre() throws InformationNonConformeException {
        for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationEmise);
        }

    }
}

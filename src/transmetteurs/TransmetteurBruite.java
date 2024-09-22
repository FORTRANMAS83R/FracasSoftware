package transmetteurs;

import bruit.BBG;
import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class TransmetteurBruite<Float> extends Transmetteur<Float, Float> {

    private Float SNRpb;

    public TransmetteurBruite(Float snrPB){
        super();
        this.SNRpb = snrPB;
    }
    @Override
    public void recevoir(Information<Float> information) throws InformationNonConformeException {
        this.informationRecue = information;
        BBG bruit = new BBG(10f, (Information<java.lang.Float>) information);
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

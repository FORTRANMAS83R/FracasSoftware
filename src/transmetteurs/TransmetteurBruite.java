package transmetteurs;

import bruit.BBG;
import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

/**
 * Classe représentant un transmetteur bruite.
 * Ce transmetteur ajoute du bruit à l'information reçue en fonction du SNR par bit (SNRpb).
 */
public class TransmetteurBruite<R,E> extends Transmetteur<Float, Float> {

    private final Float SNRpb;
    private Integer seed;
    private Boolean haveSeed = false;

    /**
     * Constructeur du transmetteur bruite.
     *
     * @param snrPB Le rapport signal sur bruit par bit.
     */
    public TransmetteurBruite(Float snrPB) {
        super();
        this.SNRpb = snrPB;
    }
    public TransmetteurBruite(Float snrPB, Integer seed) {
        super();
        this.SNRpb = snrPB;
        this.seed = seed;
        this.haveSeed = true;
    }

    /**
     * Reçoit une information, y ajoute du bruit, puis l'émet.
     *
     * @param information L'information reçue.
     * @throws InformationNonConformeException Si l'information est non conforme.
     */
    @Override
    public void recevoir(Information<Float> information) throws InformationNonConformeException {
        this.informationRecue = information.clone();
        BBG bruit;
        if (!this.haveSeed) {
             bruit = new BBG(this.SNRpb);
        }else {
             bruit = new BBG(this.SNRpb, this.seed);
        }
        this.informationEmise = bruit.bruitage(this.informationRecue);
        emettre();
    }

    /**
     * Émet l'information bruitée à toutes les destinations connectées.
     *
     * @throws InformationNonConformeException Si l'information est non conforme.
     */
    @Override
    public void emettre() throws InformationNonConformeException {
        for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationEmise);
        }
    }
}

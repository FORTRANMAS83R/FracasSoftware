package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class Decodeur<R,E> extends Transmetteur<Boolean, Boolean> {


    /**
     * Constructeur de la classe Decodeur
     */
    public Decodeur() {
        super();
    }

    /**
     * Méthode permettant de recevoir une information
     *
     * @param information L'information reçue
     * @throws InformationNonConformeException Si l'information reçue est non conforme
     */
    @Override
    public void recevoir(Information<Boolean> information) throws InformationNonConformeException {
        this.informationRecue = information;
        this.informationEmise = decodeInformation(information);
        emettre();
    }

    /**
     * Méthode permettant d'émettre une information
     *
     * @throws InformationNonConformeException Si l'information émise est non conforme
     */
    @Override
    public void emettre() throws InformationNonConformeException {
        for (DestinationInterface<Boolean> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationEmise);
        }
    }

    /**
     * Méthode permettant de décoder une information
     *
     * @param information L'information à décoder
     * @return L'information décodée
     */
    public Information<Boolean> decodeInformation(Information<Boolean> information) throws InformationNonConformeException {
        if(information.nbElements()%3 != 0){
            throw new InformationNonConformeException("Le nombre d'éléments de l'information n'est pas un multiple de 3, des bits ont été perdus");
        }
        //information vide
        if(information.nbElements() == 0){
            return new Information<>();
        }
        Information<Boolean> signalDecode = new Information<>();
        for (int i = 0; i < information.nbElements(); i+=3) {
            Boolean[] symb = {information.iemeElement(i), information.iemeElement(i+1), information.iemeElement(i+2)};
            signalDecode.add(decode(symb));
        }
        return signalDecode;
    }

    /**
     * Méthode permettant de décoder un symbole
     *
     * @param symb Le symbole à décoder
     * @return Le symbole décodé
     */
    public static Boolean decode(Boolean[] symb){
        return (!symb[1] && symb[2]) || (symb[0] && symb[2]) || (symb[0] && !symb[1]);
    }
}

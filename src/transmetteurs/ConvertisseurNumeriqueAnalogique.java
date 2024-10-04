package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;
import sources.analogique.SourceAnalogiqueType;

public class ConvertisseurNumeriqueAnalogique<R, E> extends Transmetteur<Boolean, Float> {
    private int nbEch;
    private float amp_min;
    private float amp_max;
    private SourceAnalogiqueType type;

    public ConvertisseurNumeriqueAnalogique(int nbEch, float amp_min, float amp_max, SourceAnalogiqueType type) {
        super();
        this.nbEch = nbEch;
        this.amp_min = amp_min;
        this.amp_max = amp_max;
        this.type = type;
    }

    private Information<Float> echantillonnage(Integer nbEch) {
        Information<Float> informationEchantillon = new Information<>();
        informationEchantillon.setNbEchantillons(nbEch);
        for (int i = 0; i < this.informationRecue.nbElements(); i++) {
            final Float element = informationRecue.iemeElement(i) ? amp_max : amp_min;
            for (int j = 0; j < informationEchantillon.getNbEchantillons(); j++)
                informationEchantillon.add(element);
        }
        return informationEchantillon;
    }

    public void miseEnFormeRZ(int nbEch) {
        Information<Float> informationEchantillon = echantillonnage(nbEch);

        for (int i = 0; i < informationRecue.nbElements(); i += nbEch) {
            if (informationEchantillon.iemeElement(i) != 0) for (int j = 0; j < nbEch / 3; j++) {
                this.informationEmise.setIemeElement(i + j, 0f);
                this.informationEmise.setIemeElement(i + nbEch - 1 - j, 0f);
            }
        }
    }

    public void miseEnFormeNRZ(int nbEch) {
        this.informationEmise = echantillonnage(nbEch);
    }

    public void miseEnFormeNRZT(int nbEch) {
        Information<Float> informationEchantillon = echantillonnage(nbEch);

        for (int i = 0; i < informationEchantillon.nbElements(); i += nbEch) {
            final float symbole_actuel = informationEchantillon.iemeElement(i);
            final float val_fin = informationEchantillon.nbElements() > i + nbEch ? (informationEchantillon.iemeElement(i + nbEch) + symbole_actuel) / 2 : 0;
            final float val_depart = i == 0 ? 0 : (informationEchantillon.iemeElement(i - 1) + symbole_actuel) / 2;
            final float delta_1er_tier = symbole_actuel == val_depart ? 0 : symbole_actuel / ((float) nbEch / 3f);
            final float delta_3eme_tier = symbole_actuel == val_fin ? 0 : (0 - symbole_actuel) / (((float) nbEch / 3f) - (i + nbEch == informationEchantillon.nbElements() ? 1 : 0));

            if (delta_1er_tier != 0) for (int j = 0; j < nbEch / 3; j++) {
                informationEmise.setIemeElement(i + j, j * delta_1er_tier);
            }

            if (delta_3eme_tier != 0) for (int j = 0; j < nbEch / 3; j++) {
                informationEmise.setIemeElement(i + (2 * nbEch / 3) + j, symbole_actuel + j * delta_3eme_tier);
            }
        }
    }

    @Override
    public void recevoir(Information<Boolean> information) throws InformationNonConformeException {
        this.informationRecue = information;
        switch (this.type) {
            case RZ:
                miseEnFormeRZ(nbEch);
                break;
            case NRZ:
                miseEnFormeNRZ(nbEch);
                break;
            case NRZT:
                miseEnFormeNRZT(nbEch);
                break;
        }
        emettre();
    }

    @Override
    public void emettre() throws InformationNonConformeException {
        for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationEmise);
        }
    }
}

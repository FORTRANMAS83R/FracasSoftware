package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;
import sources.SourceAnalogiqueType;

public class ConvertisseurNumeriqueAnalogique<R, E> extends Transmetteur<Boolean, Float> {
    private final int nbEch;
    private final float amp_min;
    private final float amp_max;
    private final SourceAnalogiqueType type;

    public ConvertisseurNumeriqueAnalogique(int nbEch, float amp_min, float amp_max, SourceAnalogiqueType type) {
        super();
        this.nbEch = nbEch;
        this.amp_min = amp_min;
        this.amp_max = amp_max;
        this.type = type;
    }

    private void echantillonnage(Integer nbEch) {
        this.informationEmise = new Information<>();
        this.informationEmise.setNbEchantillons(nbEch);
        for (int i = 0; i < this.informationRecue.nbElements(); i++) {
            final Float element = informationRecue.iemeElement(i) ? amp_max : amp_min;
            for (int j = 0; j < informationEmise.getNbEchantillons(); j++)
                informationEmise.add(element);
        }
    }

    public void miseEnFormeRZ(int nbEch) {
        Information<Float> temp = this.informationEmise.clone();
        for (int i = 0; i < informationRecue.nbElements(); i += nbEch) {
            if (temp.iemeElement(i) != 0) for (int j = 0; j < nbEch / 3; j++) {
                this.informationEmise.setIemeElement(i + j, 0f);
                this.informationEmise.setIemeElement(i + nbEch - 1 - j, 0f);
            }
        }
    }

    public void miseEnFormeNRZ(int nbEch) {

    }

    public void miseEnFormeNRZT(int nbEch) {
        Information<Float> temp = this.informationEmise.clone();

        for (int i = 0; i < temp.nbElements(); i += nbEch) {
            final float symbole_actuel = temp.iemeElement(i);
            final float val_fin = temp.nbElements() > i + nbEch ? (temp.iemeElement(i + nbEch) + symbole_actuel) / 2 : 0;
            final float val_depart = i == 0 ? 0 : (temp.iemeElement(i - 1) + symbole_actuel) / 2;
            final float delta_1er_tier = symbole_actuel == val_depart ? 0 : symbole_actuel / ((float) nbEch / 3f);
            final float delta_3eme_tier = symbole_actuel == val_fin ? 0 : (0 - symbole_actuel) / (((float) nbEch / 3f) - (i + nbEch == temp.nbElements() ? 1 : 0));

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
        echantillonnage(nbEch);
        switch (this.type) {
            case RZ:
                miseEnFormeRZ(nbEch);
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

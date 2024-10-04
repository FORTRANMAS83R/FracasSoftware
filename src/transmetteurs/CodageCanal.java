package transmetteurs;

import information.Information;

public class CodageCanal {
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
}









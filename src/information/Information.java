package information;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @author prou
 */
public class Information<T> implements Iterable<T> {

    private final ArrayList<T> content;
    private Integer nbEchantillons;

    /**
     * pour construire une information vide
     */
    public Information() {
        this.content = new ArrayList<T>();
        this.nbEchantillons = 0;
    }

    /**
     * pour construire à partir d'un tableau de T une information
     *
     * @param content le tableau d'éléments pour initialiser l'information
     *                construite
     */
    public Information(T[] content) {
        this.content = new ArrayList<T>();
        this.nbEchantillons = content.length;
        for (int i = 0; i < content.length; i++) {
            this.content.add(content[i]);
        }

    }

    public Information(T[] content, Integer nbEchantillons) {
        this.content = new ArrayList<T>();
        this.nbEchantillons = nbEchantillons;
        for (int i = 0; i < content.length; i++) {
            this.content.add(content[i]);
        }

    }

    /**
     * @return le nombre d'échantillons
     */
    public Integer getNbEchantillons() {
        return this.nbEchantillons;
    }

    /**
     * @return le contenu de l'information
     */
    public ArrayList<T> getContent() {
        return this.content;
    }

    /**
     * @param nbEchantillons: le nombre d'échantillons
     */
    public void setNbEchantillons(Integer nbEchantillons) {
        this.nbEchantillons = nbEchantillons;
    }

    /**
     * pour connaître le nombre d'éléments d'une information
     *
     * @return le nombre d'éléments de l'information
     */
    public int nbElements() {
        return this.content.size();
    }

    /**
     * pour obtenir une sous-information
     *
     * @param start le rang du premier élément de la sous-information (à partir de
     *              0)
     * @param end   le rang du dernier élément de la sous-information
     * @return la sous-information de rang start à end-1
     */
    public Information<T> sousInformation(int start, int end) {
        Information<T> sub = new Information<>();
        for (int i = start; i <= end; i++) {
            sub.add(this.iemeElement(i));
        }
        return sub;
    }

    /**
     * pour renvoyer un élément d'une information
     *
     * @param i le rang de l'information à renvoyer (à partir de 0)
     * @return le ieme élément de l'information
     */
    public T iemeElement(int i) {
        return this.content.get(i);
    }

    /**
     * pour modifier le ième élément d'une information
     *
     * @param i le rang de l'information à modifier (à partir de 0)
     * @param v la nouvelle ieme information
     */
    public void setIemeElement(int i, T v) {
        this.content.set(i, v);
    }

    /**
     * pour ajouter un élément à la fin de l'information
     *
     * @param valeur l'élément à rajouter
     */
    public void add(T valeur) {
        this.content.add(valeur);
    }

    /**
     * pour ajouter une liste d'éléments à la fin de l'information
     *
     * @param valeurs la liste d'éléments à rajouter
     */
    public void addLast(ArrayList<T> valeurs) {
        this.content.addAll(valeurs);
    }



    /**
     * pour comparer l'information courante avec une autre information
     *
     * @param o l'information avec laquelle se comparer
     * @return "true" si les 2 informations contiennent les mêmes éléments aux mêmes
     * places; "false" dans les autres cas
     */
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (!(o instanceof Information))
            return false;
        Information<T> information = (Information<T>) o;
        if (this.nbElements() != information.nbElements())
            return false;
        for (int i = 0; i < this.nbElements(); i++) {
            if (!this.iemeElement(i).equals(information.iemeElement(i)))
                return false;
        }
        return true;
    }

    /**
     * Effectue une copie en dur de l'information.
     *
     * @return une copie de l'information
     */
    public Information<T> clone() {
        Information<T> clone = new Information<T>();
        for (int i = 0; i < this.nbElements(); i++) {
            clone.add(this.iemeElement(i));
        }
        clone.setNbEchantillons(this.getNbEchantillons());
        return clone;
    }

    /**
     * pour afficher une information
     *
     * @return representation de l'information sous forme de String
     */
    public String toString() {
        String s = "";
        for (int i = 0; i < this.nbElements(); i++) {
            s += " " + this.iemeElement(i);
        }
        return s;
    }

    /**
     * pour utilisation du "for each"
     */
    public Iterator<T> iterator() {
        return content.iterator();
    }
}

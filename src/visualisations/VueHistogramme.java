package visualisations;

import java.awt.*;
import java.util.ArrayList;

public class VueHistogramme extends Vue{

    private static final long serialVersionUID = 1917L;

    public VueHistogramme(int[] histogramme, ArrayList<Float> bords, String nom) {
        super(nom);
        int xPosition = Vue.getXPosition();
        int yPosition = Vue.getYPosition();
        setLocation(xPosition, yPosition);
        int largeur = 1000;
        int hauteur = 200;
        setSize(largeur, hauteur);
        setVisible(true);
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        int[] histogramme = { /* valeurs de l'histogramme */ };
        int largeur = getWidth();
        int hauteur = getHeight();
        int barWidth = largeur / histogramme.length;

        for (int i = 0; i < histogramme.length; i++) {
            int barHeight = (int) ((hauteur - 20) * ((double) histogramme[i] / getMaxValue(histogramme)));
            g.setColor(Color.BLUE);
            g.fillRect(i * barWidth, hauteur - barHeight, barWidth - 2, barHeight);
    }
}

    private int getMaxValue(int[] histogramme) {
        int max = Integer.MIN_VALUE;
        for (int value : histogramme) {
            if (value > max) {
                max = value;
            }
        }
        return max;
}

}

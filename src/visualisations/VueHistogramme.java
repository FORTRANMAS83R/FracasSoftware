package visualisations;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class VueHistogramme extends Vue{

    private static final long serialVersionUID = 1917L;
    private int[] histogramme;
    private int yMax = 0;


    public VueHistogramme(int[] histogramme, String nom) {
        super(nom);
        int xPosition = Vue.getXPosition();
        int yPosition = Vue.getYPosition();

        this.histogramme = histogramme;
        yMax = Collections.max(Arrays.stream(histogramme).boxed().toList());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(xPosition, yPosition);
        setSize(histogramme.length * 15, 200);
        setVisible(true);
        repaint();
    }

    public void paint(Graphics g){
        g.setColor(Color.BLACK);

        if (g == null) {
            return;
        }
        // effacement total
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.black);


        int x0Axe = 10;

        int y0Axe = getContentPane().getHeight()-10;

        getContentPane().getGraphics().drawLine(x0Axe, y0Axe, getContentPane().getWidth(), y0Axe);
        getContentPane().getGraphics().drawLine(getContentPane().getWidth() - 5, y0Axe - 5, getContentPane().getWidth(), y0Axe);
        getContentPane().getGraphics().drawLine(getContentPane().getWidth() - 5, y0Axe + 5, getContentPane().getWidth(), y0Axe);

        getContentPane().getGraphics().drawLine(x0Axe, y0Axe, x0Axe, 0);
        getContentPane().getGraphics().drawLine(x0Axe + 5, 5, x0Axe, 0);
        getContentPane().getGraphics().drawLine(x0Axe - 5, 5, x0Axe, 0);

        if(histogramme.length != 0){
            float deltaY = y0Axe / (this.yMax * 1f);
            float deltaX = (getContentPane().getWidth() -x0Axe)/histogramme.length;
            for(int i =0; i<histogramme.length; i++){
                getContentPane().getGraphics().drawRect((int)(x0Axe+deltaX*i),(int)(y0Axe-deltaY*histogramme[i]),(int)deltaX,(int)(deltaY*histogramme[i]));
            }
        }
    }

}

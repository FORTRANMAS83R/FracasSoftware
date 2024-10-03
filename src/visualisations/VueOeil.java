package visualisations;


import java.awt.*;

import information.Information;

public class VueOeil extends Vue {

    private static final long serialVersionUID = 1917L;

    private Information<Float> signal;
    private float yMax = 0;
    private float yMin = 0;

    public VueOeil(Information<Float> signal, String nom) {
        super(nom);

        this.signal = signal;

        int xPosition = Vue.getXPosition();
        int yPosition = Vue.getYPosition();
        setLocation(xPosition, yPosition);

        yMax = 0;
        yMin = 0;

        for (int i = 0; i < signal.nbElements(); i++) {
            if (signal.iemeElement(i) > yMax)
                yMax = signal.iemeElement(i);
            if (signal.iemeElement(i) < yMin)
                yMin = signal.iemeElement(i);
        }

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 200);
        setVisible(true);
        repaint();
    }

    public void paint(Graphics g) {
        if (g == null) {
            return;
        }

        // effacement total
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.black);


        int width = getContentPane().getWidth();
        int height = getContentPane().getHeight();

        int x0Axe = 10;
        float deltaX = width - (2 * x0Axe);

        int y0Axe = 10;
        float deltaY = height - (2 * y0Axe);

        if ((yMax > 0) && (yMin <= 0)) {
            y0Axe += (int) (deltaY * (yMax / (yMax - yMin)));
        } else if ((yMax > 0) && (yMin > 0)) {
            y0Axe += deltaY;
        } else if (yMax <= 0) {
            y0Axe += 0;
        }

        getContentPane().getGraphics().drawLine(x0Axe, y0Axe, x0Axe + (int) deltaX + x0Axe, y0Axe);
        getContentPane().getGraphics().drawLine(x0Axe + (int) deltaX + x0Axe - 5, y0Axe - 5,
                x0Axe + (int) deltaX + x0Axe, y0Axe);
        getContentPane().getGraphics().drawLine(x0Axe + (int) deltaX + x0Axe - 5, y0Axe + 5,
                x0Axe + (int) deltaX + x0Axe, y0Axe);

        getContentPane().getGraphics().drawLine(x0Axe, y0Axe, x0Axe, y0Axe - (int) deltaY - y0Axe);
        getContentPane().getGraphics().drawLine(x0Axe + 5, 5, x0Axe, 0);
        getContentPane().getGraphics().drawLine(x0Axe - 5, 5, x0Axe, 0);

        // tracer la courbe
        g.setColor(Color.BLUE);

        if (signal.nbElements() > signal.getNbEchantillons() * 3) {
            float dx = deltaX / (signal.getNbEchantillons() * 3.0f);
            float dy = 0.0f;
            if ((yMax >= 0) && (yMin <= 0)) {
                dy = deltaY / (yMax - yMin);
            } else if (yMin > 0) {
                dy = deltaY / yMax;
            } else if (yMax < 0) {
                dy = -(deltaY / yMin);
            }

            // Parcourir chaque symbole, de 1 à N-1
            for (int i = 0; i <= signal.nbElements()
                    - 3 * signal.getNbEchantillons(); i += signal.getNbEchantillons()) {
                // Parcourir les échantillons
                for (int j = 0; j < signal.getNbEchantillons() * 3 - 1; j++) {
                    float x1 = x0Axe + j * dx;
                    float y1 = y0Axe - signal.iemeElement(i + j) * dy;
                    float x2 = x0Axe + (j + 1) * dx;
                    float y2 = y0Axe - signal.iemeElement(i + j + 1) * dy;
                    g.setColor(new Color(20, 200, 180, 255));
                    g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
                    //getContentPane().getGraphics().drawLine((int) x1, (int) y1, (int) x2, (int) y2);
                }
            }
        }

        System.out.println("Paint end");

    }

}

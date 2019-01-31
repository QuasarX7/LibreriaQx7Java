package it.quasar_x7.java.disegno;

import it.quasar_x7.java.utile.Colore;
import java.awt.BasicStroke;
import java.awt.Graphics2D;


/**
 *
 * @author ninja
 */
public class Punto2D extends FiguraGenerica {    
    
    private Colore colore;
    private float spessore;

    public Punto2D(Coordinate2D baricentro, Colore colore, float spessore) {
        this.baricentro = baricentro;
        this.colore = colore;
        this.spessore=spessore;
    }
    
    public Punto2D(double x, double y, Colore colore, float spessore) {
        this(new Coordinate2D(x,y),colore,spessore);        
    }

    public float spessore() {
        return spessore;
    }

    public void spessore(float spessore) {
        this.spessore = spessore;
    }

  
    public void disegna(Graphics2D g) {
        g.setStroke(new BasicStroke(spessore));       
        g.setColor(colore);
                
        g.drawLine((int)baricentro.getX(), (int)baricentro.getY(), 
                (int)baricentro.getX(), (int)baricentro.getY());
    }

    
}

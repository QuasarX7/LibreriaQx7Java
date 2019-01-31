package it.quasar_x7.java.disegno;

import it.quasar_x7.java.utile.Colore;
import java.awt.BasicStroke;
import java.awt.Graphics2D;


/**
 *
 * @author ninja
 */
public class Linea extends FiguraGenerica {    
    
    private Colore colore;
    private float spessore;
    private Coordinate2D p1,p2;

    public Linea(Coordinate2D p1,Coordinate2D p2, Colore colore, float spessore) {
        this.p1=p1;
        this.p2=p2;
        this.colore = colore;
        this.spessore=spessore;
    }
    
    public Linea(double x1, double y1,double x2, double y2, Colore colore, float spessore) {
        this(new Coordinate2D(x1,y1),new Coordinate2D(x2,y2),colore,spessore);        
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
                
        g.drawLine((int)p1.getX(), (int)p1.getY(), 
                (int)p2.getX(), (int)p2.getY());
    }

    
}

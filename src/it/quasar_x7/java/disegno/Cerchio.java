package it.quasar_x7.java.disegno;

import it.quasar_x7.java.utile.Colore;
import java.awt.BasicStroke;
import java.awt.Graphics2D;


/**
 *
 * @author ninja
 */
public class Cerchio extends FiguraGenerica {    
    
    protected Colore colore;
    private float spessore;
    protected double raggio;

    public Cerchio(Coordinate2D baricentro, double raggio,  Colore colore, float spessore) {
        this.raggio=raggio;
        this.colore = colore;        
        this.baricentro = baricentro;
        this.spessore=spessore;
    }
    
    public Cerchio(double x, double y,    double raggio, Colore colore, float spessore) {
        
        this(new Coordinate2D(x,y),raggio,colore,spessore);        
    }

    

    public Colore colore() {
        return colore;
    }

    public void colore(Colore colore) {
        this.colore = colore;
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
        
        g.drawOval((int)(baricentro.getX()), 
                (int)(baricentro.getY() ), 
                (int)(2*raggio), (int)(2*raggio));
        
    }
    

    
}

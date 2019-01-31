package it.quasar_x7.java.disegno;

import it.quasar_x7.java.utile.Colore;
import java.awt.BasicStroke;
import java.awt.Graphics2D;


/**
 *
 * @author ninja
 */
public class Rettangolo extends FiguraGenerica {    
    
    protected Colore colore;
    private float spessore;
    protected double altezza;
    protected double lunghezza;

    public Rettangolo(Coordinate2D baricentro,
            double altezza, double lunghezza,
            Colore colore, float spessore) {
        this.altezza=altezza;
        this.lunghezza=lunghezza;
        this.colore = colore;        
        this.baricentro = baricentro;
        this.spessore=spessore;
    }
    
    public Rettangolo(double x, double y,    double altezza, double lunghezza,
             Colore colore, float spessore) {
        
        this(new Coordinate2D(x,y),altezza,lunghezza,colore,spessore);        
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
        
        g.drawRect((int)(baricentro.getX()- lunghezza/2), 
                (int)(baricentro.getY() + altezza/2), 
                (int)(lunghezza), (int)(altezza));
        
    }
    

    
}

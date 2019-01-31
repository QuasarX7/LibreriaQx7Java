/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.quasar_x7.java.disegno;


import it.quasar_x7.java.utile.Colore;
import java.awt.Graphics2D;

/**
 *
 * @author ninja
 */
public class RettangoloPieno extends Rettangolo {

    public RettangoloPieno(double x, double y, double altezza, double lunghezza, Colore colore) {
        super(x, y, altezza, lunghezza, colore, 0);
    }

    public RettangoloPieno(Coordinate2D baricentro, double altezza, double lunghezza, Colore colore) {
        super(baricentro, altezza, lunghezza, colore, 0);
    }
    
    
    @Override
    public void disegna(Graphics2D g) {
        g.setColor(colore);     
        
        g.fillRect((int)(baricentro.getX()- lunghezza/2), 
                (int)(baricentro.getY() + altezza/2), 
                (int)(lunghezza), (int)(altezza));
        
    }
    
}

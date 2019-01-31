package it.quasar_x7.java.disegno;

import java.awt.Component;

/*******************************************************************************
 *
 * @author Domenico della Peruta
 ******************************************************************************/
public interface Figura {
    
    public Coordinate baricentro();
    public void posiziona(Coordinate2D baricentro);
    public Orientamento vediOrientamento();
    public void orienta(Orientamento2D orientamento);
    public void movimento(double velocita, Orientamento2D direzione);
    public void rotazione(double velocita, Coordinate2D centro);
    public void disegna(java.awt.Graphics2D g); 
    
}

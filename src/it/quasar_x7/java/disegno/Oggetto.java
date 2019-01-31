package it.quasar_x7.java.disegno;

/*******************************************************************************
 *
 * @author Domenico della Peruta
 ******************************************************************************/
public interface Oggetto {
    
    public Coordinate baricentro();
    public void posiziona(Coordinate baricentro);
    public Orientamento vediOrientamento();
    public void orienta(Orientamento orientamento);
    public void movimento(double velocita, Orientamento direzione);
    public void rotazione(double velocita, Orientamento asse, Coordinate centro);
    public void disegna();  
    
    
}

package it.quasar_x7.java.disegno;

/**
 *
 * @author ninja
 */
public interface SistemaRiferimento {
    
    public void unita(int pixel)throws ErroreGrafico;
    public int unita();
    public void centro(int x, int y);
    public Coordinate centro();
    public void orientamento(double zenit);
    public double orientamento();
    
}

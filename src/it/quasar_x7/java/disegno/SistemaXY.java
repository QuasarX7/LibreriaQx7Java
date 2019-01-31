
package it.quasar_x7.java.disegno;

/**
 *
 * @author ninja
 */
public class SistemaXY implements SistemaRiferimento{

    private int cX, cY; //centro
    private double zenit;//angolo tra l'asse X e lo Zenit
    private int unita;//unità in pixel

    public SistemaXY(int cX, int cY) {
        this.cX = cX;
        this.cY = cY;
        zenit = 0.0;
        unita = 1;
    }

    public SistemaXY(int cX, int cY, double zenit) {
        this.cX = cX;
        this.cY = cY;
        this.zenit = zenit;
        unita = 1;
    }
    
    public SistemaXY(int cX, int cY, double zenit, int unita) {
        this.cX = cX;
        this.cY = cY;
        this.zenit = zenit;
        this.unita = unita;
    }
    
    public void unita(int pixel) throws ErroreGrafico{
        if(pixel > 0){
            this.unita=pixel;
        }else{
            throw new ErroreGrafico("i pixel non possono essere negativi o nulli");
        }
    }

    public void centro(int x, int y){
        cX=x;
        cY=y;
    }

    public void orientamento(double zenit) {
        this.zenit=zenit;
    }

    public int unita() {
        return this.unita;
    }

    public Coordinate centro() {
        return new Coordinate2D(cX,cY);
    }

    public double orientamento() {
        return this.zenit;
    }
    
}

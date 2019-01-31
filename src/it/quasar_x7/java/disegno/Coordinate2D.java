package it.quasar_x7.java.disegno;

/*******************************************************************************
 *
 * @author Domenico della Peruta
 ******************************************************************************/
public class Coordinate2D implements Coordinate{
    
    private double x,y;

    public Coordinate2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

   
    
    
    public void assegna(double[] v) throws ErroreGrafico{
        if(v != null){
            if(v.length == 2){
                x=v[0];
                y=v[1];
            }else{
                throw new ErroreGrafico("vettore con dimensione diverse da 3");
            }
        }else{
            throw new ErroreGrafico("vettore nullo!");
        }
    }

    public double[] vedi() {
        return new double[]{x,y};
    }
    
}

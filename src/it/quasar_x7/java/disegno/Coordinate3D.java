package it.quasar_x7.java.disegno;

/*******************************************************************************
 *
 * @author Domenico della Peruta
 ******************************************************************************/
public class Coordinate3D implements Coordinate{
    
    double x,y,z;

    public Coordinate3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
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

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
    
    public void assegna(double[] v) throws ErroreGrafico{
        if(v != null){
            if(v.length == 3){
                x=v[0];
                y=v[1];
                z=v[2];
            }else{
                throw new ErroreGrafico("vettore con dimensione diverse da 3");
            }
        }else{
            throw new ErroreGrafico("vettore nullo!");
        }
    }

    public double[] vedi() {
        return new double[]{x,y,z};
    }
    
}

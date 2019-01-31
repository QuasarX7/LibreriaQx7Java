package it.quasar_x7.java.matematica;

/**
 *
 * @author Domenico della Peruta
 */
public class NumeroComplesso {
    
    private double parte_reale,parte_immaginaria;

    public NumeroComplesso(double parte_reale, double parte_immaginaria) {
        this.parte_reale = parte_reale;
        this.parte_immaginaria = parte_immaginaria;
    }
    
    public double Re(){
        return this.parte_reale;
    }
    
    public double Im(){
        return this.parte_immaginaria;
    }
    
    public double modulo(){
        return Math.sqrt(
                parte_immaginaria*parte_immaginaria + parte_reale*parte_reale);
    }
    
    public double argomento(){
        return Math.atan(parte_immaginaria/parte_reale);
    }
    
    public static NumeroComplesso somma(NumeroComplesso a,NumeroComplesso b){        
        return new NumeroComplesso(a.Re()+b.Re(),a.Im()+b.Im());
    }
    
    public static NumeroComplesso prodotto(NumeroComplesso a,NumeroComplesso b){        
        return new NumeroComplesso(
                a.Re()*b.Re()-a.Im()*b.Im(),
                a.Im()*b.Re()+a.Re()*b.Im());
    }

    @Override
    public String toString() {
        return parte_reale + "+ j " + parte_immaginaria +" ";
    }
    
    
}

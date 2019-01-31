package it.quasar_x7.java.matematica;

/*******************************************************************************
 *
 * @author Domenico della Peruta
 ******************************************************************************/
public class Vettore {
    private double[] elementi;

    public Vettore(double[] elementi) {
        this.elementi = elementi;
    }

    public double[] getElementi() {
        return elementi;
    }

    public void setElementi(double[] elementi) {
        this.elementi = elementi;
    }
    
    public int dimensioni(){
        return elementi.length;
    }
    
    static public double[] somma(double[] a,double[] b) throws ErroreMatematico{
        double[] c = new double[b.length];
        if(a.length==b.length){
            for(int i=0;i<b.length;i++){
                c[i]=a[i]+b[i];
            }
        }else{
            throw new ErroreMatematico(
                    "impossibile effettuare la somma di due vettori"
                    + "con diversa lunghezza\n");
        }
        return c;
    }

    static public double[] prodotto(double a,double[] b){
        double[] c = new double[b.length];
        for(int i=0;i<b.length;i++){
            c[i]=b[i]*a;
        }
        return c;
    }

    /***********************************************
     * SommatoriaProdotti fra due vettori
     *
     * @param a
     * @param b
     * @return
     * @throws ErroreMatematico
     ***********************************************/
    static public double SommatoriaProdotti(double[] a,double[] b) throws ErroreMatematico{
        double r=0;
        if(a.length==b.length){
            for(int i=0;i<a.length;i++){
                r+=a[i]*b[i];
            }
            return r;
        }else{
            throw new ErroreMatematico(
                    "impossibile effettuare il prodotto di due vettori"
                    + "con diversa lunghezza\n");
        }
    }

    

    public static String stampa(double[] x) {
        String v="[";
        boolean primo = true;
        for(int i=0;i<x.length;i++){
            if(primo){
                v+= x[i];
                primo=false;
            }else{
                v+= "; "+x[i];
            }

        }
        return v+"]";
    }

}

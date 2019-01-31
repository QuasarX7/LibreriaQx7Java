package it.quasar_x7.java.disegno;

/*******************************************************************************
 *
 * @author Domenico della Peruta 
 ******************************************************************************/
public class ErroreGrafico extends Exception {    
    private String messaggio="";

    public ErroreGrafico(String messaggio){
        this.messaggio = messaggio;
    }

    @Override
    public String getMessage() {
        return this.messaggio;
    }
    
}

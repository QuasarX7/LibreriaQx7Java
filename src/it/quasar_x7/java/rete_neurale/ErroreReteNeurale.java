package it.quasar_x7.java.rete_neurale;

/**
 *
 * @author Domenico della Peruta
 */
public class ErroreReteNeurale extends java.lang.Exception{
    private String messaggio="";

    public ErroreReteNeurale(String messaggio){
        this.messaggio = messaggio;
    }

    @Override
    public String getMessage() {
        return this.messaggio;
    }


}

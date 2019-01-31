
package it.quasar_x7.java.matematica;

/**
 *
 * @author Domenico della Peruta
 */
public class ErroreMatematico extends java.lang.Exception{
    private String messaggio="";

    public ErroreMatematico(String messaggio){
        this.messaggio = messaggio;
    }

    @Override
    public String getMessage() {
        return this.messaggio;
    }

}

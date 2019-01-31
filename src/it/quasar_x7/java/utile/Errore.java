/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.quasar_x7.java.utile;

/**
 *
 * @author Ninja
 */
public class Errore extends java.lang.Exception{
    private String messaggio="";

    public Errore(String messaggio){
        this.messaggio = messaggio;
    }

    @Override
    public String getMessage() {
        return this.messaggio;
    }

}

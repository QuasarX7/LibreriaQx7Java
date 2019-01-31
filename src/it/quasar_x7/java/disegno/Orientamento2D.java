/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.quasar_x7.java.disegno;

/**
 *
 * @author ninja
 */
public class Orientamento2D implements Orientamento{
   
    private double angolo;    
       

    public Orientamento2D(double angolo){
        this.angolo= angolo;
    }

    public void orienta(double angolo) {
        this.angolo=angolo;
    }

    public double vedi() {
        return this.angolo;
    }

    public double getX() {
        return Math.cos(angolo);
    }
    
    public double getY(){
        return Math.sin(angolo);
    }
    
    
   
    
    
}

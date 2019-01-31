/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.quasar_x7.java.Sistema;

/**
 *
 * @author ninja
 */
public class Java {
    
    static public String versione(){
       return System.getProperty("java.version");
    }
    
    static public double memoriaLiberaMB(){
        Runtime r = Runtime.getRuntime();
        long mem = r.freeMemory();
        double kB = ((mem/1024)*100)/100;
        return ((kB/1024)*100)/100; //MB
    }
    
    static public double memoriaMax(){
        Runtime r = Runtime.getRuntime();
        long mem = r.maxMemory();
        double kB = ((mem/1024)*100)/100;
        double MB = ((kB/1024)*100)/100; 
        return MB;
    }
    
    /***********************************
     * Memoria occupata dai processi java
     * 
     * @return 
     ***********************************/
    static public double memoriaMB(){
        Runtime r = Runtime.getRuntime();
        long mem = r.totalMemory();
        double kB = ((mem/1024)*100)/100;
        return ((kB/1024)*100)/100; //MB
    }
}

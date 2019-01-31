/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.quasar_x7.java.Sistema;

/**
 *
 * @author ninja
 */
public class Hardware {
    
    
    
    static public String processore(){
        int cpu = Runtime.getRuntime().availableProcessors();
        return System.getProperty("os.arch")+ " (n°"+cpu+" core)";
    }
    
}

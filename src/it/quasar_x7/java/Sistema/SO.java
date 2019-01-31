/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.quasar_x7.java.Sistema;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author ninja
 */
public class SO {
    
    static public String nome(){
        return System.getProperty("os.name");
    }
    
    static public String versione(){
        return System.getProperty("os.version");
    }
    
    static public File[] fileSystem(){
        return File.listRoots();
    }
    
    static public boolean creaFile(File file) throws IOException{
        return file.createNewFile();
    }
    
    static public boolean creaDirectory(String percorso){
        File cartella = new File(percorso);
        return cartella.mkdir();
    }
    
    /***********************************************
     * Elimina File o directory
     * 
     * @param file file o directory
     * @return 
     ***********************************************/
    static public boolean elimina(File file){
        return file.delete();
    }
    
    /***********************************************
     * Modifica file o directory
     * 
     * @param file file o cartella da modificare
     * @param percorso_nome percorso e/o nome file
     * @return restituisce null se non è possiblile
     * modificare oppure il file o la directory 
     * modificate
     ***********************************************/
    static public File modifica(File file,String percorso_nome){
        File rinomina = new File(percorso_nome);
        if(file.renameTo(rinomina)){
            return file;
        }else{
            return null;
        }        
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.quasar_x7.java.BaseDati;

/*******************************************************************************
 *
 * @author Domenico della Peruta
 ******************************************************************************/
public class FunzioneSQL implements Dominio{
    private int id = -1;

    public FunzioneSQL(int idColonna){
        id = idColonna;
    }

    public FunzioneSQL() {
        
    }

    public int colonna(){
        return id;
    }

    public String toString(){
        return "Generico";
    }

}

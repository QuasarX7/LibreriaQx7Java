package it.quasar_x7.java.BaseDati;

/**
 * Classe che definisce un dominio degli attributi di una relazione di tipo stringa
 * variabile di testo, usata in tipicamente in SQLite.
 * 
 * @author Dr. Domenico della Peruta
 */
public class DatoTesto implements Dominio{
    
	/**
         * Costruttore
         */
	public DatoTesto(){
	}
	
	
        @Override
	public String toString(){
		return String.format("TEXT");
	}
    
}

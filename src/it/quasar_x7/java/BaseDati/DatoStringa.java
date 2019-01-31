package it.quasar_x7.java.BaseDati;

public class DatoStringa implements Dominio {
	
	private final int lunghezza;
	
	//costruttore
	public DatoStringa(int lunghezza){
		this.lunghezza = lunghezza;
	}
	
	public int lunghezza(){
		return this.lunghezza;
	}
	
        @Override
	public String toString(){
		return String.format("VARCHAR("+lunghezza+")");
	}
}

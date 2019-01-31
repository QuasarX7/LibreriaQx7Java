package it.quasar_x7.java.BaseDati;

public class DatoInteroCorto implements Dominio {

	private int max = Integer.MAX_VALUE;
	private int min = Integer.MIN_VALUE;
	
	//costruttore senza parametro
	public DatoInteroCorto(){
		
	}
	//costruttore con parametri
	public DatoInteroCorto(int min,int max){		
		if(min < max){
			this.max = max;
			this.min = min;
		}
	}
	
	public int limiteMax(){
		return this.max;
	}
	
	public int limiteMin(){
		return this.min;
	}
	
        @Override
	public String toString(){
            return String.format("INT");
	}
}

package it.quasar_x7.java.BaseDati;

public class DatoInteroLungo implements Dominio {

	private long max = Long.MAX_VALUE;
	private long min = Long.MIN_VALUE;
	
	//costruttore senza parametro
	public DatoInteroLungo(){
		
	}
	//costruttore con parametri
	public DatoInteroLungo(long min,long max){		
		if(min < max){
			this.max = max;
			this.min = min;
		}
	}
	
	public long limiteMax(){
		return this.max;
	}
	
	public long limiteMin(){
		return this.min;
	}
	
        @Override
	public String toString(){
            return String.format("INT");
	}
}

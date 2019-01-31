package it.quasar_x7.java.BaseDati;

public class DatoRealeSemplice implements Dominio {

	private float max = Float.MAX_VALUE;
	private float min = Float.MIN_VALUE;
	
	//costruttore senza parametro
	public DatoRealeSemplice(){
		
	}
	//costruttore con parametri
	public DatoRealeSemplice(float min,float max){		
		if(min < max){
			this.max = max;
			this.min = min;
		}
	}
	
	public float limiteMax(){
		return this.max;
	}
	
	public float limiteMin(){
		return this.min;
	}
	
	public String toString(){
		return String.format("FLOAT");
	}
}

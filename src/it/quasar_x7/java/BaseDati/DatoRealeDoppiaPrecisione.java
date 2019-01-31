package it.quasar_x7.java.BaseDati;

public class DatoRealeDoppiaPrecisione implements Dominio {

	private double max = Double.MAX_VALUE;
	private double min = Double.MIN_VALUE;
	
	//costruttore senza parametro
	public DatoRealeDoppiaPrecisione(){
		
	}
	//costruttore con parametri
	public DatoRealeDoppiaPrecisione(double min,double max){		
		if(min < max){
			this.max = max;
			this.min = min;
		}
	}
	
	public double limiteMax(){
		return this.max;
	}
	
	public double limiteMin(){
		return this.min;
	}
	
        @Override
	public String toString(){
            return String.format("DOUBLE");
	}
}

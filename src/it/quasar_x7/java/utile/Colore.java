package it.quasar_x7.java.utile;

import java.awt.Color;

/*******************************************************************************
 * 
 * @author  Domenico della Peruta
 ******************************************************************************/
public class Colore extends Color{
	
	public static final Colore  BIANCO = new Colore(255,255,255);
	public static final Colore  NERO  = new Colore(0,0,0);
	public static final Colore  ROSSO = new Colore(255,0,0);
	public static final Colore  VERDE = new Colore(0,255,0);
	public static final Colore  BLU = new Colore(0,0,255);
	public static final Colore  GRIGIO = new Colore(127,127,127);
	public static final Colore  GIALLO = new Colore(255,255,0);
	
	
	public Colore(){
		super(255,255,255);
	}
	
	public Colore(int rosso,int verde, int blu,int alpha){
		super(rosso, verde, blu, alpha);
	}
	
	public Colore(int rosso,int verde, int blu){
		super(rosso, verde, blu);
	}
	


}

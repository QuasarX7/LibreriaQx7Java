package it.quasar_x7.java.utile;

public interface Schedulatore<E>{
	
	public void esegui();
	public void blocca();
	public boolean statoAttivo();
	public void aggiungi(E x);
	public E preleva();
	public boolean vuoto();

}

package it.quasar_x7.java.utile;

public interface Coda<E> {
	
	public void inserisci(E x);
	
	public E preleva();
	
	public boolean vuota();
	
	public int dimensione();
}

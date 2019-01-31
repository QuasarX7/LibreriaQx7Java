package it.quasar_x7.java.utile;

public abstract class _schedulatore<E> implements Schedulatore<E> {

	protected boolean _attivo = false;	
	
//-------------------------------------------------------------------------------------------------
	@Override
	public abstract void aggiungi(E x);
//-------------------------------------------------------------------------------------------------
	@Override
	public boolean statoAttivo() {
		return this._attivo;
	}
//-------------------------------------------------------------------------------------------------
	@Override
	public void esegui() {
		this._attivo = true;

	}
//-------------------------------------------------------------------------------------------------
	@Override
	public abstract E preleva() ;
//-------------------------------------------------------------------------------------------------
	@Override
	public void blocca() {
		this._attivo=false;
		
	}
}

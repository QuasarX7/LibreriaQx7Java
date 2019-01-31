package it.quasar_x7.java.utile;

public class accodamentoFIFO<E> extends _schedulatore<E>{
	
	private Coda<E> _coda	= null;	
	
//==================================================================================================
//                   costruttore

	public accodamentoFIFO(){
		_coda = new CodaFIFO<E>();
		
	}
//=================================================================================================
	public void aggiungi(E x) {
		if(this._attivo)
			_coda.inserisci(x);

	}
//-------------------------------------------------------------------------------------------------
	@Override
	public E preleva() {
		if(this._attivo)
			return this._coda.preleva();
		return null;
	}
//------------------------------------------------------------------------------------------------
	@Override
	public boolean vuoto() {		
		return this._coda.vuota();
	}
//------------------------------------------------------------------------------------------------
	public String toString(){
		return this._coda.toString();
	}
//================================================================================================
}//fine

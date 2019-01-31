package it.quasar_x7.java.utile;

import java.util.ArrayList;
/**
 * 
 * 
 */
public class accodamentoPriorita<E> extends _schedulatore<E>{

	private ArrayList<CodaFIFO<E>> _coda = null;
	private int nrCode = 0;
//=================================================================================================
	public accodamentoPriorita(int nr_code){
		this.nrCode = nr_code;
		_coda = new ArrayList<CodaFIFO<E>>(nr_code);
		for(int i=0;i<nr_code;i++){
			this._coda.add(new CodaFIFO<E>());
		}
	}
//=================================================================================================
	

	/**
	 * preleva l'elemento E della coda con priorità più alta, se non è presente nessun elemento 
	 * passa a quella con priorità inferiore
	 */
	@Override
	public E preleva() {
		int i = this.nrCode;
		while(i > 0){
			if(this._coda.get(i-1).dimensione() > 0)
				return this._coda.get(i-1).preleva();
			i--;			
		}
		return null;
	}
//-------------------------------------------------------------------------------------------------
	@Override
	public void aggiungi(E x) {
			this._coda.get(0).inserisci(x);//aggiungi alla coda con priorità 0
		
	}
//-------------------------------------------------------------------------------
	public boolean aggiungi(E x, int priorità) {
		if(priorità >= 0 && priorità < this.nrCode){
			this._coda.get(priorità).inserisci(x);
			return true;
		}
		return false;
	}
//---------------------------------------------------------------------------------------------------
	@Override
	public boolean vuoto() {
		return this.nrCode == 0;
	}
//===================================================================================================		

}

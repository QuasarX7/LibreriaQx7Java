package it.quasar_x7.java.utile;

import java.util.ArrayList;

/******************************************************************************* 
 *  Classe che implementa una semplice coda F.I.F.O. in cui il primo elemento 
 *  introdotto è anche il primo ad essere prelevato
 *
 ******************************************************************************/

public class CodaFIFO<E> implements Coda<E> {

	protected ArrayList<E> _coda = null;	
	
//==============================================================================
//                          Costruttore
	
	public CodaFIFO(){
		_coda = new ArrayList<E>();		
	}
	
//==============================================================================
	
	@Override
	public int dimensione() {		
		return _coda.size();
	}
//------------------------------------------------------------------------------
	@Override
	public void inserisci(E x) {
		this._coda.add(x);
	}
//------------------------------------------------------------------------------
	@Override
	public E preleva() {
		if(!this.vuota()){			
			return this._coda.remove(0);
		}
		return null;
	}
//------------------------------------------------------------------------------
	@Override
	public boolean vuota() {		
		return this._coda.isEmpty();
	}
	
//------------------------------------------------------------------------------
	@Override
        public String toString(){
		String s ="";
		for(int i=0; i < this.dimensione();i++){
			s += _coda.toArray()[i].toString()+' ';			
		}
		return s;		
	}

//------------------------------------------------------------------------------
        public String stampaInversa(){
            String s ="";
            for(int i=this.dimensione()-1; i >= 0 ;i--){
                    s += _coda.toArray()[i].toString()+' ';			
            }
            return s;
            
        }

//==============================================================================
}//fine

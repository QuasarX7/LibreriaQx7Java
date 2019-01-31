package it.quasar_x7.java.utile;

import java.util.ArrayList;

public class arrayCircolare<E> {
	
//=================================================================================================
	protected ArrayList<E> _coda = null;	
	
//=================================================================================================
//	                          Costruttore
		
		public arrayCircolare(){
			_coda = new ArrayList<E>();		
		}
		
//=================================================================================================
		
		
		public int dimensione() {		
			return _coda.size();
		}
//-------------------------------------------------------------------------------------------------
		/**
		 * inserisci in "testa" alla array
		 */
		public void inserisci(E x) {
			this._coda.add(x);
		}
//--------------------------------------------------------------------------------------------------
		/**
		 * preleva in "coda" all'array
		 */
		public E preleva() {
			if(!this.vuota()){			
				return this._coda.remove(0);
			}
			return null;
		}
//--------------------------------------------------------------------------------------------------
		
		public boolean vuota() {		
			return this._coda.isEmpty();
		}
//==================================================================================================
	/**
	 * leggi elemento a partire dal ultimo elemento creato (id=0)
	 * il primo elemento risulta con offset = 1
	 */
	public E leggi(int offset){
		if(!this.vuota()){
			int j=this.dimensione()-1;//elemento di coda
			if(offset >= 0){
				for(int i=0; i <= offset;i++){
					if( i== offset)
						return this._coda.get(j);
					if(j++ == this.dimensione()-1)
						j=0;					
				}
			}else{// offset <0				
				for(int i=0; i >= offset;i--){
					if( i== offset)
						return this._coda.get(j);
					if(j-- == 0)
						j=this.dimensione()-1;					
				}		
			}
		}
		return null;
	}
	
//------------------------------------------------------------------------------------------------
	public String toString(){
		String s ="";
		for(int i=0; i < this.dimensione();i++){
			s += String.valueOf(i)+"° elemento array circolare\n" + 
				this._coda.toArray()[i].toString()+
				"\n============================\n";			
		}
		return s;		
	}

//================================================================================================
}//fine

package it.quasar_x7.java.utile;

public class accodamentoRR<E> extends _schedulatore<E> {

	private arrayCircolare<CodaFIFO<E>> _coda = null;
	private boolean attiva =false;
	private int pCoda = 0;    //puntatore alla coda
	private int pElemento = 0;//puntatore all'elemento della coda
//=====================================================================================================
//                     Costruttore
	
	public accodamentoRR(int nr_priorità){
		_coda = new arrayCircolare<CodaFIFO<E>>();
		for(int i=0; i < nr_priorità; i++){
			_coda.inserisci(new CodaFIFO<E>());		
		}
		pCoda = nr_priorità-1;//punta alla coda con priorità più alta
	}
//=====================================================================================================
	@Override
	public void aggiungi(E x) {
		_coda.leggi(0).inserisci(x);
		
	}
	
	public void aggiungi(E elemento,int priorità) {
		if(priorità < this._coda.dimensione())
			//aggiunge l'elemento con priorità minore al prima coda  
			_coda.leggi(priorità+1).inserisci(elemento);
		else
			System.out.println("errore: priorità non prevista....");
		
	}

//-------------------------------------------------------------------------------------------------	


	@Override
	public E preleva() {
		E elemento = null;
		
		if(!this.vuoto()){
			
			if(this.pCoda == this._coda.dimensione()-1){ //coda con priortà più alta			
				this.pElemento = pCoda+1;//= priorità+1 (elementi da prendere)
			}
			
			//preleva elemento dalla coda 
			if(!this._coda.leggi(1+this.pCoda).vuota()){
				elemento = this._coda.leggi(1+this.pCoda).preleva();
				//conta gli elementi presi 
				this.pElemento--;
			}
			else
				this.pElemento=0;
			
			if(this.pElemento == 0){//se non c'è nessun elemento da prelevare
				
				//finito di prendere gli elementi dalla coda passa a quella successiva
				//con priorità più bassa 
				this.pCoda--;
				//deve prende n+1 elementi dalla coda con priorità n
				this.pElemento = this.pCoda+1;
			}
			
			if(this.pCoda < 0){
				this.pCoda = this._coda.dimensione()-1;
			}
			
			if(elemento == null)
				return this.preleva();
		}
		
		return elemento;			
		
	}
 //----------------------------------------------------------------------------------------------------
	public boolean vuoto(){
		int n = 0;
		for(int i=0; i < this._coda.dimensione();i++){
			if(!this._coda.leggi(i+1).vuota())
				n++;
		}
		if(n > 0)
			return false;
		return true;
	}
//----------------------------------------------------------------------------------------------------
	public String toString(){		
		return this._coda.toString();
	}
//====================================================================================================
}//fine

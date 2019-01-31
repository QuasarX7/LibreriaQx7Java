package it.quasar_x7.java.utile;

/*******************************************************************************
 * Particolare Coda FIFO a lunghezza predefinita, dove se si supera il numero
 * massimo di elementi, si procede all'eliminazione del primo elemento inserito
 * 
 * @author Domenico della Peruta
 ******************************************************************************/
public class CodaFissa<E> extends CodaFIFO<E>{
    private int lunghezza;
    
    public CodaFissa(int lunghezza) {
        super();
        this.lunghezza=lunghezza;
    }

    public int lunghezzaMax(){
        return lunghezza;
    }
    
    @Override
    public void inserisci(E x) {
        super.inserisci(x);
        if(this._coda.size()>lunghezza){
            this.preleva();
        }
    }
    
    
    
    
}

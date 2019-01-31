package it.quasar_x7.java.BaseDati;

import it.quasar_x7.java.utile.DataOraria;
import java.util.HashMap;

/*******************************************************************************
 *
 * @author Domenico della Peruta
 ******************************************************************************/
public class EccezioneBaseDati extends java.lang.Exception{
    static private int n= 0;   
    static private HashMap<String,Integer> nTipo = new HashMap<String,Integer>();
    private DataOraria lancio;
    private String tipo;
    
    public EccezioneBaseDati(String tipo,String testo) {
        super(testo);
        lancio = new DataOraria();
        lancio.adesso();
        this.tipo=tipo;
        
        n++;
        incrementaTipo();
    }
    
    
    private void incrementaTipo(){
        if(!nTipo.containsKey(tipo)){            
            nTipo.put(tipo, 1);
        }else{
            nTipo.put(tipo, nTipo.get(tipo)+1);
        }
    }

    /*********************************************
     * Numero delle volte che viene l'anciato
     * l'eccezione riferita allo stesso 'tipo'
     * 
     * @return intero
     ********************************************/
    public int nVolte(){
        return nTipo.get(tipo);
    }
    
    public void finestra(){
        javax.swing.JOptionPane.showMessageDialog(
                null, this.getMessage(), "eccezione", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    
    public String tipo(){
        return tipo;
    }
    
    @Override
    public String getMessage() {
        return  "\n_________________________________________\n"
                + "Errore n°"+n+
                "\ntipo: "+tipo+" verificatosi n° "+nVolte()+" volte\n"
                +"descrizione:\n"
                +super.getMessage()+
                "\n_________________________________________\n";
    }
    
}

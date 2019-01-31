package it.quasar_x7.java.BaseDati;

/**
 * Identifica tutti i vincoli, azioni o integrità referenziali tra attributi di tabelle esterne.
 * 
 * @author Dr Domenico della Peruta
 */
public class LegameReferenziale {
    
    public enum Azione{NON_APPLICARE,VALORE_NULLO,VALORE_PTREDEFINITO,APPLICA};
    
    private Attributo attributo = null;
    
    private Azione cancella = Azione.NON_APPLICARE;
    private Azione modifica = Azione.NON_APPLICARE;

    /**
     * 
     * @param attributo
     * @param cancella
     * @param modifica 
     */
    public LegameReferenziale(Attributo attributo,Azione cancella,Azione modifica) {
        this.cancella = cancella;
        this.modifica = modifica;
        this.attributo = attributo;
    }

    public Attributo getAttributo() {
        return attributo;
    }

    public void setAttributo(Attributo attributo) {
        this.attributo = attributo;
    }

    public Azione cancella() {
        return cancella;
    }

    

    public Azione modifica() {
        return modifica;
    }


    
    
    
}

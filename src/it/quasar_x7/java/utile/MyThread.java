package it.quasar_x7.java.utile;

/*******************************************************************************
 *
 * @author Domenico della Peruta
 ******************************************************************************/
public abstract class MyThread implements Runnable{
    static private int contatore = 0;
    protected int id = -1;
    private DataOraria creazione, chiusura;
    private boolean stop = false;

    public MyThread(){
        DataOraria d = new DataOraria();
        d.adesso();
        this.creazione = d;
        id = contatore;
        contatore++;
    }

    /*******************************************
     * Ferma l'esecuzione del codice del thread
     *
     *******************************************/
    public void ferma(){
        DataOraria d = new DataOraria();
        d.adesso();
        this.chiusura = d;
        this.stop = true;
    }

    /*******************************************
     * restituisce il numero di thread creato
     * @return
     ******************************************/
    public int id(){
        return id;
    }

    public void run() {
        while(!this.stop){
            this.codice();
        }
    }

    @Override
    public String toString() {

        return "Thread id:"+id+
                " creazione:"+this.creazione+
                " chiusura:"+this.chiusura;
    }


    /*****************************************
     * Codice eseguito in modo ciclico dal
     * thread
     *****************************************/
    abstract public void codice();



}

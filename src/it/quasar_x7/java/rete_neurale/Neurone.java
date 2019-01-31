package it.quasar_x7.java.rete_neurale;

import it.quasar_x7.java.XML.Elemento;
import it.quasar_x7.java.matematica.ErroreMatematico;
import it.quasar_x7.java.matematica.Vettore;
import java.util.Random;



/******************************************************
 *
 * @author Domenico della Peruta
 ******************************************************/
public class Neurone {

   private double statoAttivazione;
   private double[] pesi;


   public Neurone(){
       statoAttivazione=0;
       pesi = null;
   }

   public Neurone(int ingressi){
       statoAttivazione=0;
       pesi = new double[ingressi];
   }

   public Neurone(Neurone n){
        this.statoAttivazione=n.statoAttivazione;
        this.pesi = new double[n.pesi.length];
        System.arraycopy(n.pesi, 0, this.pesi, 0, pesi.length);
   }

   public Neurone(double[] pesiIniz){
       statoAttivazione = 0;
       this.pesi = new double[pesiIniz.length];
        System.arraycopy(pesiIniz, 0, this.pesi, 0, pesi.length);
   }

   public int quantiIngressi(){
       return pesi.length;

   }

   /*************************************************
    * Effettua l'elaborazione dei segnali in ingresso
    * attraverso la sommatoria degli ingressi
    * opportunamente pesati
    *
    * @param ingressi
    * @return restituisce il valore della sommatoria
    * dei prodotti degli ingressi per i rispettivi pesi
    * delle sinaps
    * @throws ErroreReteNeurale
    *************************************************/
   public double attivazione(double[] ingressi) throws ErroreReteNeurale{
        try {
            this.statoAttivazione = Vettore.SommatoriaProdotti(pesi, ingressi);
        } catch (ErroreMatematico ex) {
            throw new ErroreReteNeurale(
                   "errore 'Neurone.attivazione(double[] ingr)',\n" +
                   "lungnezza vettori diverse: Neurone.pesi="+this.pesi.length+
                   " e pesi(ingresso)="+ingressi.length);
        }
        return statoAttivazione;
    }


   /*****************************************************
    * Permette di elaborare l'uscita del segnale in funzione
    * dello stato del neurone.
    *                                     <br>
    * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
    * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
    * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
    *                     1               <br>
    * uscita= ------------------------------ <br>
    * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
    * &nbsp&nbsp&nbsp&nbsp
    *           1 + e ^ (- statoNeurone)   <br><br>
    *
    * statoNeurone = Sommatoria(pesi*ingressi)
    * 
    * @return
    *****************************************************/
    public double uscita(){
          return 1 / ( 1 + Math.exp( - statoAttivazione ) );
    }


    public double leggiPeso(int i){
        return pesi[i];
    }

    public double[] leggiPesi(){
        return pesi;
    }

    public void assegnaPesi(double[] p ) throws ErroreReteNeurale{
        if(this.pesi.length==p.length){
            for(int i=0;i<p.length;i++){
                pesi[i] = p[i] ;
            }
        }else
           throw new ErroreReteNeurale(
                   "errore 'assegnaPesi(double[] p )',\n" +
                   "lungnezza vettori diverse: Neurone.pesi="+this.pesi.length+
                   " e pesi(ingresso)="+pesi.length);
        }

    /**********************************************************
     * Incrementa il valore dei pesi degli ingressi del neurone
     * sommandogli un vettore di valori aggiuntivi.
     *
     * @param pesi vettore di valori aggiuntivi
     * @throws ErroreReteNeurale lunghezza del vettore dei valori
     * aggiuntivi diverso dal numero di ingressi
     **********************************************************/
    public void incrementaPesi(double[] pesi) throws ErroreReteNeurale{
        try {
            this.pesi = Vettore.somma(this.pesi,pesi);
        } catch (ErroreMatematico ex) {
            throw new ErroreReteNeurale(                   
                   "errore 'incrementaPesi(double[] pesi)',\n" +
                   "lungnezza vettori diverse: Neurone.pesi="+this.pesi.length+
                   " e pesi(ingresso)="+pesi.length);
        }
    }

    /******************************************************
     * Stampa il valore dello 'stato di attivazione' e i
     * pesi del neurone
     *
     * @return
     ******************************************************/
    public String stampa(){
        String s = "pesi=[";
       for ( int i = 0; i < pesi.length; i++ )
           s += pesi[i] + ",";
       s+="]";
       return String.format(" stato=<%g> %s",statoAttivazione,s);
    }

    /*******************************************************
     * Assegna pesi casuali agli ingressi del neurone
     *
     * @param max rappresenta il valore massimo (max) e minimo
     * (-max) dell'intervallo dei pesi casuali
     *******************************************************/
    public void pesiCasuali(double max ){
        for ( int i = 0; i < pesi.length; i++ )
            pesi[i] = casuale( max );
    }


    /**********************************
     * Restituisce un valore casuale che
     * va da max a -max
     *
     * @param max valore massimo
     * @return
     ***********************************/
    private double casuale(final double max){
        Random rn = new Random();
        double rand = rn.nextDouble();
        return max * (2 *rand - 1);
    }


    /*****************************************
     * Inizializza a zero i pesi degl'ingressi
     * del neurome
     *****************************************/
    public void pesiZero(){
        for ( int i = 0; i < pesi.length; i++ )
            pesi[i] =0;
    }

    public void assegnaPeso(double peso, int i) {
        this.pesi[i]=peso;
    }

    /*********************************************
     * Permette di serializzare in un fail xml un
     * neurone
     *
     * @param id
     * @return
     *********************************************/
    public Elemento serializza(int id) {
        Elemento neurone = new Elemento("neurone_"+id);
        for(int i=0;i<this.pesi.length;i++){
            Elemento peso = new Elemento("peso_"+i);
            peso.setValore(String.valueOf(pesi[i]));
            neurone.addElemento(peso);
        }
        return neurone;
    }

    public void deserializza(Elemento neurone) throws ErroreReteNeurale{
        final int numeroPesi = neurone.numeroElementi();
        this.pesi = new double[numeroPesi];

        for(int i=0; i< numeroPesi;i++){
            Elemento peso = neurone.getElemento(i);
            String valore = peso.getValore();
            if(valore != null){
                if(valore.length()>0){
                    pesi[i]= Double.valueOf(peso.getValore()).doubleValue();
                }
            }else{
                throw new ErroreReteNeurale(
                        "errore 'deserializza(Elemento neurone)',\n" +
                        "valore peso letto dal file xml non valido");
            }

        }
    }

}


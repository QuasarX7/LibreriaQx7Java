package it.quasar_x7.java.rete_neurale;

import it.quasar_x7.java.XML.*;
import it.quasar_x7.java.matematica.ErroreMatematico;
import it.quasar_x7.java.matematica.Vettore;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/*******************************************************************************
 * La Rete feed-forward e' composta da strati di neuroni non ricorsiva, in cui
 * ogni  neurone non e' connesso con quelli dello stesso strato ma con  i neuroni
 * dello strato successivo. La propagazione dei segnali avviene in una sola
 * direzione attraversando i vari strati.
 *
 * @author Domenico della Peruta
 ******************************************************************************/
public class ReteMLP {

    private Strato[] strati;
    private double errore=0;
    private int tentativi=0;
    private double eta=0;

    /*******************************************
     * Costruttore che crea una rete vuota la
     * cui inizializzazione puo' avvenire
     * solo mediante l'uso di un file xml 
     * (attraverso il metodo di deserializzazione)
     *
     *
     * @param numero_strati numero di Strati
     *******************************************/
    public ReteMLP() {
        errore = 0; 
    }


    /****************************************
     * Costruttore che crea una rete neurale
     * completa partendo dal numero di neuroni
     * presenti per strato e dagli ingressi
     * applicati al primo strato.
     * 
     * @param neuroni_strato numero di
     * neuroni per strato
     * @param ingressi numero di ingressi
     * della rete (ognuno e' applicato a un
     * singolo neurone dello strato di ingresso)
     ****************************************/
    public ReteMLP(int neuroni_strato[], int ingressi) {
        errore = 0;
        int numero_strati = neuroni_strato.length;

        strati = new Strato[numero_strati];

        strati[0] = new Strato(ingressi, neuroni_strato[0]);
        for (int s = 1; s < numero_strati; s++) {
            strati[s] = new Strato(neuroni_strato[s - 1], neuroni_strato[s]);
        }
    }


    /***********************************************
     * Costruzione della rete per copia di un'altra
     * rete
     *
     * @param rete
     ***********************************************/
    public ReteMLP(ReteMLP rete) {
        errore = rete.errore;
        strati = new Strato[rete.strati.length];
        for (int s = 0; s < rete.strati.length; s++) {
            strati[s] = new Strato(rete.strati[s]);
        }
    }

    /***********************************************
     * Questa funzione effettua l'elaborazione dei
     * segnali in ingresso, attaverso tutti gli strati
     * della rete
     *
     * @param ingressi
     ***********************************************/
    public void attivazione(double[] ingressi){
        try {
            strati[0].attivazione(ingressi);
            for (int s = 1; s < strati.length; s++) {
                strati[s].attivazione(strati[s - 1].uscita());
            }

        } catch (ErroreReteNeurale ex) {
            ex.printStackTrace();
        }
    }

    /************************************************
     * Vettore delle uscite della rete, corrispondente
     * alle uscite dell'ultimo strato
     *
     * @return
     ************************************************/
    protected double[] uscita() {
        return strati[strati.length - 1].uscita();
    }

    /*************************************************
     * Permette di verificare se le uscite rappresentate
     * da un vettore numerico con valori che varinano da
     * 0 a 1, siano approssimabili a una sequenza binaria
     * corrispondente a quella delle uscite dei trainset.
     * Entro un valore di approssimazione (tolleranza)
     * prestabilito.
     *
     *
     * @param tolleranza non deve superare il valore 0.5
     * @return restituisce true se tutte le uscite hanno
     * valori che sono maggiori o uguali di 1-tolleranza
     * (per i valori vicino a 1), o minori e uguali
     * tolleranza (per i valori vicino a 0)
     *
     ***************************************************/
    public boolean attendibilitaUscita(double tolleranza) {
        boolean conosco = true;
        double[] uscite = this.uscita(); //uscita della rete
        for (int i = 0; i < uscite.length; i++) {
            if (uscite[i] < 1-tolleranza && uscite[i] > tolleranza)
                conosco = false;            
        }
        return conosco;
    }

    /***************************************************
     * Permette di approssimare il vettore delle uscite
     * in una sequenza binaria, da confrontare con quella
     * dei trainset.
     *
     * @param nCifreDecimali
     * @return
     ***************************************************/
    public double[] osservaUscita() {
        double[] uscite = this.uscita(); //uscita della rete
        for (int i = 0; i < uscite.length; i++) {
            uscite[i] = Math.round(uscite[i]);

        }
        return uscite;
    }

    protected int quantiIngressi() {
        return strati[0].quantiIngressi();
    }


    /**********************************************************
     *
     * @param deltaRete rete usata per aggiornare e incrementare
     * i peri della nostra rete
     * @param ingressoDesiderato esempio di addestramento
     * @param uscitaDesiderata uscita associata all'ingresso
     * @param eta
     * @return restituisce la deltaRete aggiornata
     **********************************************************/
    protected ReteMLP apprendiEsempio(ReteMLP deltaRete, double[] ingressoDesiderato, double[] uscitaDesiderata, double eta) {
        try {
                   /*assegna i pesi alla rete delta*/


            //ultimo strato (strato delle uscite)
            double[] uscita = uscita();
            int numeroNeuroniStrato = strati[strati.length - 1].quantiNeuroni();
            double[] deltaSucc = new double[numeroNeuroniStrato];
            for (int n = 0; n < numeroNeuroniStrato; n++) {
                deltaSucc[n] = eta * (uscitaDesiderata[n] - uscita[n]) * uscita[n] * (1 - uscita[n]);
                deltaRete.strati[strati.length - 1].assegnaPesi(n, Vettore.prodotto(deltaSucc[n], strati[strati.length - 2].uscita()));
            }

            //strati interni (strati nascosti)
            double[] delta;
            double[] uscPesi;
            int numNeuSucc;
            for (int s = strati.length - 2; s > 0; s--) {
                numNeuSucc = numeroNeuroniStrato;
                numeroNeuroniStrato = strati[s].quantiNeuroni();
                uscita = strati[s].uscita();
                delta = new double[numeroNeuroniStrato];
                uscPesi = new double[numNeuSucc];

                for (int n = 0; n < numeroNeuroniStrato; n++) {
                    for (int j = 0; j < numNeuSucc; j++) {
                        uscPesi[j] = strati[s + 1].leggiPeso(j, n);
                    }

                    delta[n] = Vettore.SommatoriaProdotti(deltaSucc, uscPesi) * (eta * uscita[n] * (1 - uscita[n]));
                    deltaRete.strati[s].assegnaPesi(n, Vettore.prodotto(delta[n], strati[s - 1].uscita()));
                }
                deltaSucc = delta;
            }

            //strati di ingresso
            numNeuSucc = numeroNeuroniStrato;
            numeroNeuroniStrato = strati[0].quantiNeuroni();
            uscita = strati[0].uscita();
            delta = new double[numeroNeuroniStrato];
            uscPesi = new double[numNeuSucc];
            for (int n = 0; n < numeroNeuroniStrato; n++) {
                for (int j = 0; j < numNeuSucc; j++) {
                    uscPesi[j] = strati[1].leggiPeso(j, n);
                }
                delta[n] = Vettore.SommatoriaProdotti(deltaSucc, uscPesi) * (eta * uscita[n] * (1 - uscita[n]));
                deltaRete.strati[0].assegnaPesi(n, Vettore.prodotto(delta[n], ingressoDesiderato));
            }


        } catch (ErroreReteNeurale ex) {
            ex.printStackTrace();
        } catch (ErroreMatematico ex1) {
            ex1.printStackTrace();
        }
        return deltaRete;
    }

    protected void aggiornaErrore(double[] uscitaDesiderata) {
        double[] uscita = strati[strati.length - 1].uscita();
        for (int u = 0; u < uscita.length; u++) {
            errore += Math.pow((uscitaDesiderata[u] - uscita[u]), 2) / 2;
        }
    }

    private int[] generaIndiciCasuali(int maxIndice) {
        int[] perm = new int[maxIndice]; // permutazione casuale indici
        int temp;
        int r;
        //inizializza un vettore fatto da 0,1,2,3,... [maxIndice-1]
        for (int i = 0; i < maxIndice; i++) {
            perm[i] = i;
        }

        //effettua una sostituzione casuale della sequenza
        //del vettore inizializzato es.: 3,0,1,4,...
        for (int i = 0; i < maxIndice; i++) {
            r = Math.abs(rand()) % maxIndice;

            temp = perm[i];
            perm[i] = perm[r];
            perm[r] = temp;
        }
        return perm;
    }

    private int rand() {
        Random rn = new Random();
        return rn.nextInt();
    }

    /**********************************************************
     * Permette di istruire la rete tramite un insieme di esempi
     * di apprendimento (trainset) composti da una serie di coppie
     * di ingressi-uscite desiderate. L'algoritmo permette di
     * modellare tutti i pesi della rete, in modo da ricordare
     * gli esempi in modo permanente.
     *
     * @param ingressiDesiderati sono esempi per istruire la rete,
     * rappresentati da una matrice composta da un vettore di
     * singoli esempi,ciascuno composto da vettore BINARIO.
     *
     * @param usiteDesiderate matrice composta da un vettore di
     * uscite abbinate agli esempi in ingresso, anch'essi in forma
     * BINARIA
     *
     * @param epslon errore minimo prefissato
     * @param etaMax rapidita' di discesa
     * @param maxTent massimo numero di tentativi di
     * acquisizione degli esempi
     * @return se restituisce false, la rete non apprende bene,
     * cioè l'errore di apprendimento non e' sceso sotto la soglia
     * epslon.
     ***********************************************************/
    public boolean backpropagation(double[][] ingressiDesiderati, double[][] usciteDesiderate,
             double epslon, double etaMax, int maxTent) {

        int numTrainSet = ingressiDesiderati.length;        
        int[] indiceIngresso;
        
        ReteMLP deltaRete = new ReteMLP(this); //copia di una rete con pesi azzerati

        tentativi = 0;
        pesiCasuali(); // assegna pesi casuali

        int p;
        eta = etaMax;

        do {//ciclo di tentativi di istruzione
            errore = 0;
            indiceIngresso = generaIndiciCasuali(numTrainSet);

            //ciclo di acquisizione degli esempi
            for (int t = 0; t < numTrainSet; t++) {
                //gli ingressi e le uscite desiderate vengono caricati in modo casuale
                p = indiceIngresso[t];//indici mischiati

                attivazione(ingressiDesiderati[p]);//elabora le uscite della rete

                deltaRete.pesiZero();
                deltaRete = apprendiEsempio(deltaRete, ingressiDesiderati[p], usciteDesiderate[p], eta);

                incrementa(deltaRete);//this+= deltaRete;
                aggiornaErrore(usciteDesiderate[p]);
            }
            eta = etaMax * (1 - (double)tentativi / (double) maxTent);
            tentativi++;
            System.out.println("N. " + tentativi + "\tE = " + errore + "\teta = " + eta);
        } while ((errore > epslon) && (tentativi < maxTent));

        if (tentativi == maxTent) {
            return false;
        }
        return true;
    }

    /*******************************************
     * Velocita' di discesa dell'errore
     * 
     * @return
     *******************************************/
    public double eta(){
        return eta;
    }

    /*******************************************
     * Numero di tentativi per istruire la rete
     *
     * @return
     *******************************************/
    public int tentativi(){
        return tentativi;
    }

    /*******************************************
     * Errore associato all'elaborazione delle
     * uscite
     *
     * @return
     *******************************************/
    public double errore(){
        return errore;
    }

    /*********************************************
     * Funzione che va ad incrementare tutti i pesi
     * della rete con i corrispondenti pesi di
     * un'altra rete (simile)
     *
     * @param r rete simile (con la stessa topologia
     * della nostra rete da implementare)
     **********************************************/
    protected void incrementa(ReteMLP r){
        Strato[] s = r.leggiStrati();
        try {
            for (int i = 0; i < s.length; i++) {
                this.strati[i].incrementaPesi(s[i].leggiNeuroni());
            }
        } catch (ErroreReteNeurale ex) {
            System.err.println("errore di incrementazione dei pesi della rete:"
                    + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /***************************************************
     * Assegna pesi casuali a tutti gli ingressi dei
     * singoli neuroni della rete
     *
     **************************************************/
    protected void pesiCasuali() {
        for (int s = 0; s < strati.length; s++) {
            strati[s].pesiCasuali();
        }
    }


    protected void pesiZero() {
        for (int s = 0; s < strati.length; s++) {
            strati[s].pesiZero();
        }
    }

    /****************************************************
     * Permette di salvare su un file la struttura e il
     * valore dei pesi di una rete.
     *
     * @param nomeFile
     ****************************************************/
    public void salva(String nomeFile){
        try {
            RandomAccessFile file = new RandomAccessFile(nomeFile, "rw");
            int n_strati = strati.length;
            file.writeInt(n_strati);//numero strati

            for(int s=0;s < n_strati;s++){
                //per ogni strato
                int n_neuroni=this.strati[s].quantiNeuroni();
                int n_pesi=this.strati[s].quantiIngressi();
                file.writeInt(n_neuroni);
                file.writeInt(n_pesi);

                for(int n=0; n< n_neuroni;n++){
                    //per ogni neurone dello strato
                    for(int p=0; p< n_pesi;p++){//scrivi tutti i pesi
                        file.writeDouble(strati[s].leggiPeso(n, p));
                    }
                }
            }

            file.close();
        } catch (IOException ex) {
            Logger.getLogger(ReteMLP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    /****************************************************
     * Permetti l'inizializzazione della rete tramite un
     * file contenente la struttura e il valore dei pesi.
     *
     * @param nomeFile
     ****************************************************/
    public void carica(String nomeFile) {
        try {
            RandomAccessFile file = new RandomAccessFile(nomeFile, "r");
            int n_strati = file.readInt();//numero strati
            this.strati = new Strato[n_strati];

            for(int s=0;s < n_strati;s++){
                //per ogni strato
                int n_neuroni = file.readInt();
                int n_pesi = file.readInt();
                
                double[][] pesi = new double[n_neuroni][n_pesi];
                for(int n=0; n< n_neuroni;n++){
                    //per ogni neurone dello strato
                    for(int p=0; p< n_pesi;p++){//leggi tutti i pesi
                        pesi[n][p]=file.readDouble();
                    }
                }
                this.strati[s] = new Strato(pesi,n_neuroni);
            }

            file.close();
        } catch (IOException ex) {
            Logger.getLogger(ReteMLP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    

    /****************************************************
     * Permette di salvare i pesi della rete su un file
     * xml
     *
     * @param file
     ****************************************************/
    public void serializza(String file) {
        Elemento rete = new Elemento("rete_neurale");
        for (int i = 0; i < this.strati.length; i++) {
            rete.addElemento(this.strati[i].serializza(i));
        }
        XML xml = new XML();
        xml.setXML(rete);
        xml.serializza(file);
    }


    /*********************************************
     * Permette inizializzare la rete da un file xml
     * 
     * @param file
     * @deprecated Da utilizzare solo per reti molto 
     * piccole, a causa del tempo di computazione
     **********************************************/
    @Deprecated
    public void deserializza(String file) {
        XML xml = new XML();
        xml.deserializza(file);
        Elemento rete = xml.getXML();
        final int numeroStrati = rete.numeroElementi();
        this.strati = new Strato[numeroStrati];
        for (int i = 0; i < numeroStrati; i++) {
            try {
                this.strati[i]=new Strato();
                this.strati[i].deserializza(rete.getElemento(i));
            } catch (ErroreReteNeurale ex) {
                Logger.getLogger(ReteMLP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /****************************************************
     * Effettua la stampa dei pesi e dello stato di
     * attivazione (prodotto peso-ingresso) dei vari neuroni
     * componenti gli strati, oltre alle uscite della rete.
     *
     * @return una stringa da stampare
     ****************************************************/
    public String stampa() {
        if(strati != null){
            String testo = "\n\n_________________________";
            for (int s = 0; s < strati.length; s++) {
                testo += String.format("\n\tstrato %d\n%s", s, strati[s].stampa());
            }
            testo += "\n\tuscite\n"+Vettore.stampa(this.uscita());
            return testo;
        }
        return "rete vuota!";
    }

    
    protected Strato[] leggiStrati() {
        return this.strati;
    }

    @Override
    public String toString() {
        return this.stampa();
    }

}

/*







*/

package it.quasar_x7.java.utile;

import java.util.ArrayList;

/************************************************************
 *Classe che permette di analizzare il testo di una stringa
 *
 * @author Domenico della Peruta
 ************************************************************/
public class Testo {
//++++++++++++++++++++++++     PROPRIETA'       ++++++++++++++++++++++++++
    private String testo = "";

//++++++++++++++++++++++++    COSTRUTTORI      ++++++++++++++++++++++++++++
    /***********************************************
     *Costruttore ad un parametro
     * @param testo Testo da manipolare
     **********************************************/
    public Testo(String testo){
        this.testo=testo;
    }

    /**********************************************
     * Costruttore che permette la tabulazione di un
     * insieme di stringhe
     *
     * @param stringhe
     * @param lunghezza
     **********************************************/
    public Testo(String[] stringhe,int lunghezza){
        this.tabulazione(stringhe, lunghezza);
    }

//++++++++++++++++++++++++      METODI     ++++++++++++++++++++++++++++++++

    /*****************************************************
     * Verifica se il testo è una data del tipo 23/01/2000
     * oppure 01-03-2009
     * @return
     *****************************************************/
    public boolean formatoDataSemplice(){
        if(this.testo.length()==10){
            if(     Character.isDigit(testo.charAt(0)) &&
                    Character.isDigit(testo.charAt(1)) &&
                    Character.isDigit(testo.charAt(3)) &&
                    Character.isDigit(testo.charAt(4)) &&
                    Character.isDigit(testo.charAt(6)) &&
                    Character.isDigit(testo.charAt(7)) &&
                    Character.isDigit(testo.charAt(8)) &&
                    Character.isDigit(testo.charAt(9))){
                
                return true;
            }
        }
        return false;
    }
    /*****************************************************
     * Restituisce la stringa di testo contenuta nel 
     * documento
     * 
     * @return Stringa
     *****************************************************/
    public String getTesto(){
        return testo;
    }

    /******************************************************
     * Numero dei caratteri contenuto nel testo
     *
     * @return lunghezza stringa testo
     ******************************************************/
    public int numeroCaratteri(){
        return this.testo.length();
    }

    /*******************************************************
     * Restituisce il numero di parole contenute nel testo,
     * dove per sequenza si intende un'insieme di lettere e
     * cifre
     *
     *    es.: "pluto2x alfa=4" -> pluto2x, alfa, 4
     *
     * @return numero di parole
     *******************************************************/
    public int numeroParole(){
        int numP=0;
         boolean trovata=false;
         for(int i=0; i < this.testo.length();i++){
            if(Character.isLetter(this.testo.charAt(i))||
                    Character.isDigit(this.testo.charAt(i))){//se e una lettera o cifra
                if(!trovata){
                    trovata = true;//se è una lettera o un carattere, marca la sequenza
                    numP++;
                }
            }else{//se non è ne una lettera ne una cifra
                trovata = false;
            }
        }

        return numP;
    }

    /****************************************************
     * Permette di determinare quante volte si ripete una
     * sequenza nel testo
     * 
     * @param s sequenza da trovare
     * @return numero di parole trovate
     ****************************************************/
    public int numeroParole(String s){
        String _testo = this.testo + " ";//artificio per analizzare l'ultimo carattere
        int numP=0;
        String c ="";//stringa di confronto
         boolean trovata=false;
         for(int i=0; i < _testo.length();i++){
            if(Character.isLetter(_testo.charAt(i))||
                    Character.isDigit(_testo.charAt(i))){//se e una lettera o cifra
                c += _testo.charAt(i);
                if(!trovata){
                    trovata = true;//se è una lettera o un carattere, marca la sequenza
                }

            }else{//se non è ne una lettera ne una cifra
                trovata = false;
                if(c.compareTo(s)==0){
                    numP++;
                }
                c = ""; //inizializza stringa di confronto
            }
        }

        return numP;
    }

    /****************************************************
     * Elenca tutte le parole (di caratteri e cifre)
     * presenti nel testo
     *
     * @return tipo ArrayList<Testo>
     ****************************************************/
    public ArrayList<String> trovaParole(){
        String _testo = this.testo + " ";//artificio per analizzare l'ultimo carattere
        ArrayList<String> elenco = new ArrayList<String>();
        String parola ="";// sequenza da trovare
         boolean trovata=false;
         for(int i=0; i < _testo.length();i++){
            if(Character.isLetter(_testo.charAt(i))||
                    Character.isDigit(_testo.charAt(i))){//se e una lettera o cifra
                parola += _testo.charAt(i);
                if(!trovata){
                    trovata = true;//se è una lettera o un carattere, marca la sequenza
                }

            }else{//se non è ne una lettera ne una cifra
                trovata = false;

                if(!parola.isEmpty())
                    elenco.add(parola);
                parola = ""; //inizializza stringa di confronto
            }
        }

        return elenco;
    }


    /*************************************************
     * Trova tutte le stringhe separate da spazi
     * bianchi
     * @return
     ************************************************/
    public ArrayList<String> trovaStringheTraSpazi(){
        String _testo = this.testo + " ";//artificio per analizzare l'ultimo carattere
        ArrayList<String> elenco = new ArrayList<String>();
        String sequenza ="";// sequenza da trovare
         boolean trovata=false;
         for(int i=0; i < _testo.length();i++){
            if(_testo.charAt(i) != ' '){
                sequenza += _testo.charAt(i);
                if(!trovata){
                    trovata = true;//se è una lettera o un carattere, marca la sequenza
                }

            }else{//se non è ne una lettera ne una cifra
                trovata = false;

                if(!sequenza.isEmpty())
                    elenco.add(sequenza);
                sequenza = ""; //inizializza stringa di confronto
            }
        }

        return elenco;
    }

    /****************************************************
     * Elenca tutte le parole del testo nella sotto-stringa
     * definita dagli estremi primo e ultimo. Delle parole
     * fanno parte anche quelle troncate dagli estremi
     *
     * @param primo indice del primo carattere
     * @param ultimo indice dell'ultimo caratere (escluso)
     * @return tipo ArrayList<String>
     ****************************************************/
    public ArrayList<String> trovaParoleTra(int primo,int ultimo){
        Testo x = new Testo(this.sottoStringa(primo, ultimo));
        return x.trovaParole();
    }

    /***************************************************
     * Trova tutte le sequenze di caratteri separati da
     * spazi bianchi, nella sottostringa del testo 
     * compreso tra il primo e l'ultimo caratere del
     * testo.
     * 
     * @param primo indice del primo carattere
     * @param ultimo indice dell'ultimo caratere (escluso)
     * @return tipo ArrayList<String>
     ***************************************************/
    public ArrayList<String> trovaStringheTraSpazi(int primo,int ultimo){
        Testo x = new Testo(this.sottoStringa(primo, ultimo));
        return x.trovaStringheTraSpazi();
    }

    /****************************************************
     * Elenca tutte le parole del testo nella sotto-stringa
     * definita dagli estremi primo e ultimo. Delle parole
     * fanno parte anche quelle troncate dagli estremi.
     *
     *@param primo primo carattere
     * @param ultimo ultimo caratere (incluso)
     * @return tipo ArrayList<String>
     ****************************************************/
    public ArrayList<String> trovaParoleTra(char primo,char ultimo){
        Testo x = new Testo(this.sottoStringa(primo, ultimo));
        return x.trovaParole();
    }



    /****************************************************
     * Funzione che calcola il numero di lettere complessivo
     * nel testo
     * 
     * @return intero
     ****************************************************/
    public int numeroLettere(){
       int numP=0;
        for(int i=0; i < this.testo.length();i++){
            boolean trovata=false;
            if(Character.isLetter(this.testo.charAt(i))){//se e una lettera
                if(!trovata){
                    trovata = true;//se è un carattere marca la sequenza
                    numP++;
                }
            }else{//se non è una lettera
                trovata = false;
            }
        }

        return numP;
    }

    /****************************************************
     * Funzione che restituisce quante volte un dato
     * carattere è presente nel testo
     *
     * @return intero
     ****************************************************/
     public int numeroCaratteri(char c){
       int numP=0;
       for(int i=0; i < this.testo.length();i++){
            boolean trovata=false;
            if(this.testo.charAt(i)==c){//se e una lettera
                if(!trovata){
                    trovata = true;//se è un carattere marca la sequenza
                    numP++;
                }
            }else{//se non è una lettera
                trovata = false;
            }
       }

        return numP;
    }

    /**************************************************
     * Funzione che determina quante cifre sono presenti
     * nel testo
     *
     * @return intero
     **************************************************/
    public int numeroCifre(){
        int numP=0;
        for(int i=0; i < this.testo.length();i++){
            boolean trovata=false;
            if(Character.isDigit(this.testo.charAt(i))){//se e una lettera
                if(!trovata){
                    trovata = true;//se è un carattere marca la sequenza
                    numP++;
                }
            }else{//se non è una lettera
                trovata = false;
            }
        }

        return numP;
    }

    /*************************************************
     * Funzione che permette di estrarre una sotto-stringa
     * dal testo
     *
     * @param primo indice del primo carattere
     * @param ultimo indice dell'ultimo caratere (escluso)
     * @return Testo
     ***************************************************/
    public String sottoStringa(int primo,int ultimo){
        return this.testo.substring(primo, ultimo);
    }

    /*************************************************
     * Funzione che permette di estrarre una sotto-stringa
     * dal testo, compresa trai due caratteri estremi.
     * I carattere estremi sono il primo che compare nel
     * testo. Gli estremi sono inclusi nella stringa
     *
     * @param primo primo caratere
     * @param ultimo ultimo carattere (incluso)
     * @return Testo
     ***************************************************/
    public String sottoStringa(char primo,char ultimo){
        int p = this.cercaCarattere(primo);

        int u;
        if(primo != ultimo){
            u = this.cercaCarattere(ultimo);
        }else{
            u = this.cercaSecondoCarattere(ultimo);
        }
        return (u!=-1 && p!=-1) ? this.testo.substring(p, u+1) : "" ;
    }

    /***************************************************
     *Funzione che restituisce la posizione del carattere.
     * se non lo trova restituisce -1
     *
     * @param c caratere da cercare
     * @return intero
     ***************************************************/
    public int cercaCarattere(char c){
        int i;
        for(i=0; i < this.testo.length();i++){
            if(this.testo.charAt(i)==c){
                return i;
            }
        }
        return -1;
    }

    /***************************************************
     *Funzione privata che restituisce la posizione del
     * secondo carattere cercato
     *
     * @param c caratere da cercare
     * @return intero
     ***************************************************/
    private int cercaSecondoCarattere(char c){
        int i;
        boolean secondo = false;
        for(i=0; i < this.testo.length();i++){
            if(this.testo.charAt(i)==c){
                if(secondo){
                    return i;
                }
                secondo=true;
            }
        }
        return -1;
    }

    /********************************************************
     * Funzione che se trova una sequenza nel testo restituisce
     * l'indirizzo del carattere successivo. Se non la trova
     * ritorna valore -1
     *
     * @param s sequenza da cercare
     * @return intero
     *******************************************************/
    public int cercaParola(String s){
        String _testo = this.testo + " ";//artificio per analizzare l'ultimo carattere
        String c ="";//stringa di confronto
         boolean trovata=false;
         for(int i=0; i < _testo.length();i++){
            if(Character.isLetter(_testo.charAt(i))||
                    Character.isDigit(_testo.charAt(i))){//se e una lettera o cifra
                c += _testo.charAt(i);
                if(!trovata){
                    trovata = true;//se è una lettera o un carattere, marca la sequenza
                }

            }else{//se non è ne una lettera ne una cifra
                trovata = false;
                if(c.compareTo(s)==0){
                    return i;
                }
                c = ""; //inizializza stringa di confronto
            }
        }
         return -1;
    }

    /*****************************************************
     * Funzione che permette di visualizzare alcune
     * caratteristiche del testo
     *
     * @return Testo
     *****************************************************/
    @Override
    public String toString() {

        String s="TESTO:=" +
                "\n++++++++++++++++++\n"+
                this.testo +
                "\n++++++++++++++++++" +
                "\nn° caratteri =" + this.numeroCaratteri()+
                "\nn° lettere ="+ this.numeroLettere()+
                "\nn° cifre ="+ this.numeroCifre()+
                "\nn° parole ="+this.numeroParole()+
                "\n\nelenco parole:=" +
                "\n++++++++++++++++++\n";
        for(int i=0;i < this.trovaParole().size();i++){
            s+=this.trovaParole().get(i)+"\n";
        }
        return s;
    }

        /*********************************************************
     * Funzione che elimina tutte le sottostringhe cercate nel
     * testo e restituisce il loro numero
     *
     * @param elimina stringa da eliminare
     * @return numero di stringhe eliminate
     *********************************************************/
    public int eliminaStringa(String elimina){
        return this.sostituisciStringa(elimina, "");
    }

    /*********************************************************
     * Funzione che sostituisce tutte le sottostringhe del
     * testo e restituisce il numero di cambiamenti
     *
     * @param elimina stringa da eliminare
     * @return numero di stringhe eliminate
     *********************************************************/
    public int sostituisciStringa(String vecchia,String nuova){

        int i=0,p1=0,p2=0;

        while(p2 != -1){
            p2 = this.cercaSottoStringa(vecchia);
            p1 = p2-vecchia.length();
            if(p2 !=-1){
                String testoPrima = this.sottoStringa(0, p1);
                String testoDopo = this.sottoStringa(p2, this.numeroCaratteri());
                testo = testoPrima + nuova +testoDopo;
                i++;
            }
        }
        return i;
    }

    /**********************************************************
     * Funzione che cerca una stringa nel testo e restituisce
     * l'indice alla posizione successiva oppure valore -1
     *
     * @param s stringa da trovare
     * @return intero
     **********************************************************/
    public int cercaSottoStringa(String s){
        for(int i=0; i < this.testo.length()-s.length()+1;i++) {
            if(this.testo.substring(i, i+s.length()).compareTo(s)==0){
                return i+s.length();
            }
        }
        return -1;
    }

    /***************************************************
     * Metodo che permette di convertire tutti i caratteri
     * da minuscolo a maiuscolo
     *
     * @return la stringa del testo convertito
     ****************************************************/
    public String tuttoMaiuscolo() {
        int diff = 'A'-'a';
       
        for(char c='a'; c<='z';c+=1){
            this.sostituisciStringa(
                    String.valueOf(c), 
                    String.valueOf((char)(c+diff))); //prima es.: "A"
            
        }
        return this.testo;
    }
    
    /*****************************************************
     * Verifica che la stringa contenga solo caratteri
     * alfanumerico (italiani)
     * 
     * @return 
     *****************************************************/
    public boolean alfanumerica() {
        String s = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890òàùéèì";
        
       
        return controllaStringa(s);
    }

    /*******************************************************
     *  Verifica che la stringa contenga solo i caratteri 
     *  previsti in 's'.
     * @param s
     * @return 
     *******************************************************/
    public boolean controllaStringa(String s) {
        
       
        for(int i=0;i<testo.length();i++){
            char x = testo.charAt(i);
            int j=0;
            for(;j<s.length();j++){
                if(x == s.charAt(j)){
                    break;
                }
            }
            if(j == s.length()) {
                return false;
            }
        }
        return true;
    }
    
    /*****************************************************
     * Funzione che elimina gli spazi vuoti in coda al
     * stinga del testo
     * <br>
     * Es.:<br>
     * input  <i>"Ciao mondo   "</i> <br>
     * output <i>"Ciao mondo"</i>
     * @return
     *****************************************************/
    public String eliminaSpaziVuoti(){
        int id = this.numeroCaratteri();
       while(id>0){
           if(this.testo.charAt(id-1)==' '){
               id--;
           }else{
               break;
           }
       }
        return this.sottoStringa(0, id);
    }


    /*************************************************
     * Funzione che permette di disporre un insieme
     * di stringhe tutte ad uguale distanza
     *
     * @param stringhe lista di stringhe
     * @param lunghezza - lunghezza tra le stringhe
     ************************************************/
    public void tabulazione(String[] stringhe, int lunghezza){
        if(stringhe!=null){
            for(int i=0;i<stringhe.length;i++){
                if(stringhe[i]!= null)
                    if(stringhe[i].length()>lunghezza){
                        this.testo += stringhe[i].substring(0, lunghezza)+".. ";
                    }else{
                       this.testo += stringhe[i]+ tab(lunghezza-stringhe[i].length())+"   ";
                    }
            }
        }
    }

    private String tab(int l) {
        String _tab="";
        for(int i=0;i<l;i++){
            _tab+=" ";
        }
        return _tab;
    }

    public void aggiungi(String string) {
        this.testo += string;
    }

    /**************************************************
     * Funzione che analizza il testo e restituisce
     * l'insieme delle stringhe dei numeri e dei
     * caratteri in esso contenuti.
     *
     * esempio:<br />
     * L'area del rettangolo 2x2.5 è 10<br />
     *<br />
     * L             - carattere<br />
     * '             - carattere<br />
     * area          - stringa<br />
     * del           - stringa<br />
     * rettangolo    - stringa<br />
     * 2             - intero<br />
     * x             - carattere<br />
     * 2.5           - decimale<br />
     * è             - carattere<br />
     * 10            - intero<br />
     *
     * @return
     **************************************************/
    public ArrayList<Object> scomposizione(){
        ArrayList<Object> elementi = new ArrayList<Object>();

        String intero="",stringa="",decimale="";
        String testo = this.testo+" ";
        for(int i=0;i<testo.length();i++){
            char carattere = testo.charAt(i);

            if(Character.isDigit(carattere)){//se è un cifra
                if(stringa.length()==1){
                    elementi.add(new Character(stringa.charAt(0)));
                    stringa="";
                }else if(stringa.length()>1){
                    elementi.add(stringa);
                    stringa="";
                }
                if(decimale.length()==0)
                    intero += carattere;
                else
                    decimale += carattere;
            }else if(Character.isLetter(carattere)){
                if(intero.length()>0){
                    elementi.add(new Integer(intero));
                    intero="";
                }else if(decimale.length()>0){
                    elementi.add(new Float(decimale));
                    decimale="";
                }
                stringa += carattere;
            }else if(carattere == '.' && intero.length()>0){//se è un punto di un mumero
                intero += carattere;
                decimale = intero;
                intero ="";
            }else if(carattere == ' '){
                if(intero.length()>0){
                    elementi.add(new Integer(intero));
                }else if(decimale.length()>0){
                    elementi.add(new Float(decimale));
                }else if(stringa.length()==1){
                    elementi.add(new Character(stringa.charAt(0)));
                }else if(stringa.length()>1){
                    elementi.add(stringa);
                }
                //inizializziamo
                intero=decimale=stringa="";
            }else{ // se è un altro tipo di carattere
                if(intero.length()>0){
                    elementi.add(new Integer(intero));
                }else if(decimale.length()>0){
                    elementi.add(new Float(decimale));
                }else if(stringa.length()==1){
                    elementi.add(new Character(stringa.charAt(0)));
                }else if(stringa.length()>1){
                    elementi.add(stringa);
                }
                //aggiungiamo il carattere attuale
                elementi.add(new Character(carattere));
                //inizializziamo
                intero=decimale=stringa="";
            }
        }
        return elementi;
    }

    /**************************************************
     * Verifica se la stringa di testo è un valore 
     * numerico.
     * @return true se "-1", "12.34", "-12.34567E10"
     **************************************************/
    public boolean numero() {
        try{ 
            Double.parseDouble(testo);
        }
        catch (NumberFormatException e){
            return false;
        }
        return true;
    }

    /**************************************************
     * Verifica se la stringa di testo è un valore 
     * intero.
     * @return true se "-1", "123"
     **************************************************/
    public boolean intero() {
        try{ 
            Integer.parseInt(testo);
        }
        catch (NumberFormatException e){
            return false;
        }
        return true;
    }
    
}
////////////////////////////////////////////////////////////////////////////////
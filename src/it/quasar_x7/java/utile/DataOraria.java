package it.quasar_x7.java.utile;

import java.util.*;


/*******************************************************************************
 *  Classe DataOraria implementa data e ora
 *
 * @author  Domenico della Peruta
 *******************************************************************************/
public class DataOraria implements java.lang.Comparable<DataOraria>{

//--------------------  proprietà ----------------------------------------------
    private Calendar calendario;

//---------------------- costruttori -------------------------------------------
    /**************************************************
     * Costruttore senza parametri, che inizializza 
     * l'oggetto alla data e all'ora in cui viene
     * creato
     **************************************************/
    public DataOraria(){
        calendario = Calendar.getInstance();
        calendario.set(Calendar.MILLISECOND, 0);
    }


    
    public DataOraria(Date data){
        this();
        calendario.setTime(data);
    }

    public DataOraria(int gg, mese mm, int aa){
        this();
        calendario.set(aa, Mese(mm), gg,0,0,0);
    }

    /**************************************************
     * Costrittore con parametri numerici
     * es.: DataOraria(17,3,2009)
     *
     * @param gg
     * @param mm
     * @param aa
     **************************************************/
    public DataOraria(int gg, int mm, int aa){
        this();
        calendario.set(aa, mm-1, gg,0,0,0); 
    }

    /***************************************************
     * Costrittore con parametri numerici
     * es.: DataOraria(17,3,2009,22,58)
     * @param gg
     * @param mm
     * @param aa
     * @param h
     * @param min
     ****************************************************/
    public DataOraria(int gg, int mm, int aa,int h,int min){
        this();
        calendario.set(aa, mm-1, gg,h,min,0);
    }

    public DataOraria(int h,int min){
        this();
        calendario.set(0, Mese(mese.GENNAIO), 1,h,min,0);
    }

    public DataOraria(int gg,mese mm, int aa, int h,int min){
        this();
        calendario.set(aa, Mese(mm), gg,h,min,0);
    }

    /**
     * Accetta come argomento in ingresso una stringa
     * del tipo: "04-08-2007", "04-08-2007 12:00:00" oppure "04-08-2007 12:05"
     * @param formato
     * @throws Errore
     */
    public DataOraria(String formato) throws Errore {
        this();
        int gg = 1,mm= 1, aaaa = 1970, h = 0, min = 0, sec = 0;
        try{
            gg = Integer.valueOf(formato.substring(0, 2));
            mm = Integer.valueOf(formato.substring(3, 5));
            aaaa = Integer.valueOf(formato.substring(6, 10));
            if(formato.length() > 10){
                h   =  Integer.valueOf(formato.substring(11, 13));
                min =  Integer.valueOf(formato.substring(14, 16));
            }
            if(formato.length() > 16){
                 sec =  Integer.valueOf(formato.substring(17, 19));
            }
            calendario.set(aaaa, mm-1, gg, h, min, sec);

        }catch(java.lang.NumberFormatException ex){
            throw new Errore("Errore formato sringa data:\n"+ex.getMessage());

        }catch(java.lang.StringIndexOutOfBoundsException e){
            throw new Errore("Errore formato sringa data:\n"+e.getMessage());
        }
    }

    
//----------------------- metodi -----------------------------------------------

    public void nullo(){
        this.calendario=null;
    }
    
    private int Mese(mese x){
        switch(x){
            case GENNAIO:   return 0;
            case FEBBRAIO:  return 1;
            case MARZO:     return 2;
            case APRILE:    return 3;
            case MAGGIO:    return 4;
            case GIUGNO:    return 5;
            case LUGLIO:    return 6;
            case AGOSTO:    return 7;
            case SETTEMBRE: return 8;
            case OTTOBRE:   return 9;
            case NOVEMBRE:  return 10;
            case DICEMBRE:  return 11;
            default: return 13;
        }
    }

    private mese Mese(int x){
        switch(x){
            case 0: return mese.GENNAIO;
            case 1: return mese.FEBBRAIO;
            case 2: return mese.MARZO;
            case 3: return mese.APRILE;
            case 4: return mese.MAGGIO;
            case 5: return mese.GIUGNO;
            case 6: return mese.LUGLIO;
            case 7: return mese.AGOSTO;
            case 8: return mese.SETTEMBRE;
            case 9: return mese.OTTOBRE;
            case 10:return mese.NOVEMBRE;
            case 11:return mese.DICEMBRE;
            default: return mese.NULLO;
        }
    }

    public settimana giornoSettimana(){
        switch(this.calendario.get(Calendar.DAY_OF_WEEK)){
            case 1: return settimana.Domenica;
            case 2: return settimana.Lunedì;
            case 3: return settimana.Martedì;
            case 4: return settimana.Mercoledì;
            case 5: return settimana.Giovedì;
            case 6: return settimana.Venerdì;
            case 7: return settimana.Sabato;
            default: return settimana.NULLO;
        }
    }

    /***************************************************
     *
     * @return Lunedì=1,Martedì=2,...,Domenica=7
     ***************************************************/
    public int numeroSettimana(){
        switch(giornoSettimana()){
            case Domenica: return 7;
            case Lunedì: return 1;
            case Martedì: return 2;
            case Mercoledì: return 3;
            case Giovedì: return 4;
            case Venerdì: return 5;
            case Sabato: return 6;
            default: return -1;
        }
    }
    
    public String stampaGiornoSettimana(){
        switch(giornoSettimana()){
            case Domenica: return "domenica";
            case Lunedì: return "lunedì";
            case Martedì: return "martedì";
            case Mercoledì: return "mercoledì";
            case Giovedì: return "giovedì";
            case Venerdì: return "venerdì";
            case Sabato: return "sabato";
            default: return "";
        }
    }
    public String stampaMese(){
        switch(this.calendario.get(Calendar.MONTH)){
            case 0: return "gennaio";
            case 1: return "febbraio";
            case 2: return "marzo";
            case 3: return "aprile";
            case 4: return "maggio";
            case 5: return "giugno";
            case 6: return "luglio";
            case 7: return "agosto";
            case 8: return "settembre";
            case 9: return "ottobre";
            case 10: return "novembre";
            case 11: return "dicembre";
            default: return "?";
        }
    }

    /******************************************************
     * visualizza data inversa es.: 2009-02-29 12:23:00
     * N.B.: utile nelle interrogazioni in SQL
     * 
     * @return
     *******************************************************/
    public String stampaGiornoOraInverso(){
        return this.stampaGiornoInverso()+" "+
                this.stampaOrario();
    }

    /*******************************************************
     * visualizza data inversa es.: 2009-02-29
     * N.B.: utile nelle interrogazioni in SQL
     * 
     * @return
     ******************************************************/
    public String stampaGiornoInverso(){
        String data="";

          data += this.calendario.get(Calendar.YEAR);
        data +="-";

        if((this.calendario.get(Calendar.MONTH)+1) > 9) {
            data += String.valueOf(this.calendario.get(Calendar.MONTH)+1);
        }
        else {
            data += "0"+String.valueOf(this.calendario.get(Calendar.MONTH)+1);
        }

         data +="-";

          if(this.calendario.get(Calendar.DATE) > 9) {
            data += String.valueOf(this.calendario.get(Calendar.DATE));
        }
        else {
            data += "0"+String.valueOf(this.calendario.get(Calendar.DATE));
        }



        return data;

    }

    /*******************************************************
     * visualizza data es.: 28-02-2009
     * @return
     ******************************************************/
    public String stampaGiorno(){
        return stampaGiorno('-');
    }
    
    /*******************************************************
     *  visualizza la data separata dal carattere specificato.
     * @param separatore esempio '/' '.' '\' '-'
     * @return 
     *******************************************************/
    public String stampaGiorno(char separatore){
        String data="";

        if(!this.equals(DataOraria.NULL())){
            if(this.calendario.get(Calendar.DATE) > 9) {
                data += String.valueOf(this.calendario.get(Calendar.DATE));
            }
            else {
                data += "0"+String.valueOf(this.calendario.get(Calendar.DATE));
            }

            data +=separatore;

            if((this.calendario.get(Calendar.MONTH)+1) > 9) {
                data += String.valueOf(this.calendario.get(Calendar.MONTH)+1);
            }
            else {
                data += "0"+String.valueOf(this.calendario.get(Calendar.MONTH)+1);
            }

             data +=separatore;

           data += this.calendario.get(Calendar.YEAR);
        }
        return data;
                
    }

    
    /*******************************************************
     *  visualizza la data separata dal carattere specificato.<br />
     *  12/12/15
     *  
     * @param separatore esempio '/' '.' '\' '-'
     * @return 
     *******************************************************/
    public String stampaGiornoRidotto(char separatore){
        String data="";


        if(this.calendario.get(Calendar.DATE) > 9) {
            data += String.valueOf(this.calendario.get(Calendar.DATE));
        }
        else {
            data += "0"+String.valueOf(this.calendario.get(Calendar.DATE));
        }
         
        data +=separatore;

        if((this.calendario.get(Calendar.MONTH)+1) > 9) {
            data += String.valueOf(this.calendario.get(Calendar.MONTH)+1);
        }
        else {
            data += "0"+String.valueOf(this.calendario.get(Calendar.MONTH)+1);
        }

         data +=separatore;
         if(this.calendario.get(Calendar.YEAR) > 999)
            data += (this.calendario.get(Calendar.YEAR)+"").substring(2, 4);

        return data;
                
    }
    
    /****************************************************
     * visualizza data es.: mercoledì 11 novembre 2000
     *
     * @return
     ***************************************************/
    public String stampaGiornoCompleto(){
        return
                this.stampaGiornoSettimana()+" "+
                String.format("%d %s %d",
			       this.calendario.get(Calendar.DATE),
                               this.stampaMese(),
                               this.calendario.get(Calendar.YEAR));
    }

    /************************************************
     * Stampa orario (ora e minuti) es.: 23:12
     * @return
     ************************************************/
    public String stampaOra(){
        String zeroH ,zeroMin;
        if (this.calendario.get(Calendar.HOUR_OF_DAY)< 10) {
            zeroH = "0";
        }
	else {
            zeroH = "";
        }
        
        if (this.calendario.get(Calendar.MINUTE)< 10) {
            zeroMin = "0";
        }
        else {
            zeroMin = "";
        }

        return
                zeroH +
                String.valueOf(calendario.get(Calendar.HOUR_OF_DAY)) + ":"+
                zeroMin +
                String.valueOf(this.calendario.get(Calendar.MINUTE));
    }
    /**************************************************
     * stampa orario completo es.: 09:04:56
     * @return
     **************************************************/
    public String stampaOrario(){

        String zero;
        if (this.calendario.get(Calendar.SECOND)< 10)
                zero = ":0";
        else
                zero = ":";

        return
                this.stampaOra()+
                zero +
                String.valueOf(this.calendario.get(Calendar.SECOND));
    }

    /**
     * Stampa l'ora e la data completa.
     * Es.: mercoledì 11 novembre 2000 12:30
     * @return 
     */
    public String stampa(){
        return this.stampaGiornoCompleto()+"  "+this.stampaOra();
    }

    /**************************************************
     * inizializza solo la data al giorno, mese e anno
     * di oggi ma non l'orario ( ora 00:00)
     *************************************************/
    public void oggi(){
        this.calendario.set(
                Calendar.DAY_OF_WEEK,
                Calendar.getInstance().get(Calendar.DAY_OF_WEEK));

        this.calendario.set(
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DATE),
                0,
                0,
                0);
    }

    public boolean giorno(int gg, mese mm, int aa){
        if(mm != mese.NULLO ){
            if(gg > 0 && gg <= giorniMese(Mese(mm)+1,aa)){
                this.calendario.set(aa, Mese(mm), gg);
                return true;
            }
        }
            return false;
    }

    public boolean giorno(int gg, int mm, int aa){
        return giorno(gg,Mese(mm-1),aa);
    }

    /*******************************************
     * restituisce il giorno del mese contenuto
     * nella data
     *
     * @return
     *******************************************/
    public int giornoMese(){
        return this.calendario.get(Calendar.DATE);
    }

    /*******************************************
     * restituisce l'anno contenuto nella data
     *
     *@return
     *******************************************/
    public int anno(){
        return this.calendario.get(Calendar.YEAR);
    }

    
    /*******************************************
     * inserisci anno
     * 
     * @param x
     *******************************************/
    public void anno(int x){
        this.calendario.set(Calendar.YEAR, x);
    }

    
    /********************************************
     *restituisce i minuti della data oraria
     * esempio
     *
     * 10/03/200  23:07:02 -> 07
     * @return
     ********************************************/
    public int minuti(){
        return this.calendario.get(Calendar.MINUTE);
    }

    /********************************************
     * restituisce le ore della data oraria
     * esempio
     *
     * 10/03/200  23:07:02 -> 23
     * @return
     ********************************************/
    public int ore(){
        return this.calendario.get(Calendar.HOUR_OF_DAY);
    }
    
    public int secondi(){
        return this.calendario.get(Calendar.SECOND);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.calendario != null ? this.calendario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DataOraria other = (DataOraria) obj;
        
        if (this.calendario != other.calendario && (this.calendario == null || !this.stampa().equals(other.stampa()))) {

            return false;
        }
        return true;
    }

    /*******************************************
     * restituisce il mese contenuto nella data
     *
     * @return
     *******************************************/
    public int mese(){
        return this.calendario.get(Calendar.MONTH)+1;
    }

    public void adesso(){
        this.oggi();
        this.calendario.set(Calendar.HOUR_OF_DAY,
                        Calendar.getInstance().get(Calendar.HOUR_OF_DAY));

        this.calendario.set(Calendar.MINUTE,
                        Calendar.getInstance().get(Calendar.MINUTE));

        this.calendario.set(Calendar.SECOND,
                        Calendar.getInstance().get(Calendar.SECOND));
    }

    public boolean ora(int h,int min, int sec){
        if(this.ora(h, min) && sec >=0 && sec < 60){
            this.calendario.set(Calendar.SECOND, sec);
            return true;
        }
        return false;
    }

    public boolean ora(int h,int min){
        if(h >= 0 && h < 60 && min >= 0 && min < 60){
            this.calendario.set(Calendar.HOUR_OF_DAY, h);
            this.calendario.set(Calendar.MINUTE, min);
            return true;
        }
        return false;
    }

    /*************************************************
     * 
     * @param ora es.: "22:10" oppure "01:08:54"
     * @return true se formato corretto
     *************************************************/
    public boolean ora(String ora){
        int h = 0,min= 0,sec=0;
        try{
            h = Integer.valueOf(ora.substring(0, 2));
            min = Integer.valueOf(ora.substring(3, 5));
            
            if(ora.length()>5 && ora.length()<=8){
                sec = Integer.valueOf(ora.substring(7, 9));
                return ora(h, min,sec);
            }else if(ora.length() == 5){
                return ora(h, min);
            }else{
                return false;
            }
        }catch(java.lang.NumberFormatException ex){
            return false;
        }
    }
    /*******************************************
     * 
     * @param x
     * @param y
     * @return giorni
     ********************************************/
    static public double differenza(final DataOraria x,final DataOraria y){
        long dallaDataMilliSecondi = x.calendario.getTimeInMillis();
        long allaDataMilliSecondi = y.calendario.getTimeInMillis();
        long millisecondiFraDueDate = allaDataMilliSecondi - dallaDataMilliSecondi;
        
        // conversione in giorni con arrotondamento della divisione reale
        return Math.round( millisecondiFraDueDate / 86400000.0 );

		
    }

    /**************************************************
     * Restituisce il numero dei giorni nel mese in 
     * relazione all'anno (per gli anni bisestili).
     * 
     * @param mm mese Es.: gennaio=1; dicembre=12.
     * @param aaaa anno
     * @return numero di giorni del mese; -1 
     * se 'mm' non corretto
     **************************************************/
    static public int giorniMese(int mm,int aaaa){
        if(mm > 0 && mm <= 12){
            switch(mm){
            case 1:case 3: case 5:case 7: case 8: case 10: case 12:
                    return 31;
            case 4: case 6: case 9: case 11:
                    return 30;
            }
            return 28+bisestile(aaaa);
        }
        return -1;
    }
	
    
    /**
     * Metodo statico che crea una data relativa al giorno corrente.
     * 
     * @return 
     */
    static public DataOraria creaDataOggi(){
        DataOraria data = new DataOraria();
        data.oggi();
        return data;
    }
    
    /**
     * Metodo statico che crea una data oraria dell'istante in cui viene chiamata.
     * @return 
     */
    static public DataOraria creaDataOraAdesso(){
        DataOraria data = new DataOraria();
        data.adesso();
        return data;
    }
    
    /**********************************************
     * restituisce 1 se anno bisstile altrimento 0
     **********************************************/
    static private int bisestile(int aaaa){
            if((aaaa % 4) == 0)
                    return 1;
            return 0;
    }

    /**********************************************
     * restituisce il valore nella classe Date
     * @return
     **********************************************/
    public Date convertiDate(){
        return this.calendario.getTime();
    }

    /**
     * Stampa giorno e ora
     * Es.: 12-02-2009 10:23:45
     * @return 
     */
    @Override
    public String toString() {
        if(this.minuti()==0 && this.ore()==0 && this.secondi() == 0){
            return this.stampaGiorno();
        }
        return this.stampaGiorno()+" "+this.stampaOrario();
    }

    /*******************************************************
     * Effettua il confronto e restituisce la differenza
     *
     * @param o valore di confronto
     * @return
     ******************************************************/
    public int compareTo(DataOraria o) {
            return this.calendario.compareTo(o.calendario);
    }

    /******************************************************
     * Tempo trascorso in anni
     * 
     * esempio
     * 01/11/2003 = 2003,91666667
     * @return 
     * @throws Errore
     ******************************************************/
    public double tempo() {
        double t = this.anno(); 
        t += (double)this.mese()/12;
        t += (double)(this.giornoMese()/giorniMese(this.mese(), this.anno()))/12;
        t += (double)this.minuti()* 0.00000190133;
        t += (double) this.ore()* 0.00011408;
        return t;                               
    }

    public void inputFinestra(String[] input) throws Errore {     
        if(input != null){
            String selezione = (String) javax.swing.JOptionPane.showInputDialog(
                           null, "seleziona data", "imput data",            
                           javax.swing.JOptionPane.QUESTION_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/it/quasar_x7/java/utile/calendario.gif")), input, input[0]);

            DataOraria d = new DataOraria(selezione);

            this.giorno(d.giornoMese(), d.mese(), d.anno());
            this.ora(0, 0, 0);
        }
    }
    
    public void incrementaGiorno(){
        calendario.add(Calendar.DAY_OF_YEAR, 1);
    }
    public void decrementaGiorno(){
        calendario.add(Calendar.DAY_OF_YEAR, -1);
    }
    public void incrementaGiorno(int i){
        calendario.add(Calendar.DAY_OF_YEAR, i);
    }
    public void decrementaGiorno(int i){
        calendario.add(Calendar.DAY_OF_YEAR, -i);
    }
    
    /**
     * Metodo che crea una data non stampabile, corrispontente al valore 09-09-9999.
     * @return 
     */
    public static DataOraria NULL(){
        return new DataOraria(9,9,9999);
    }
}

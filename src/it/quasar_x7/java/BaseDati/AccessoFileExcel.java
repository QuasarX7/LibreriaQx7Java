package it.quasar_x7.java.BaseDati;

import it.quasar_x7.java.excel.FileExcel;
import it.quasar_x7.java.utile.DataOraria;
import it.quasar_x7.java.utile.Errore;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.write.WritableCell;
import jxl.write.WriteException;

/**
 * La classe AccessoFileExcel, permette di accedere ad un file excel come se fosse
 * una base di dati relazionale. Esso implementa delle pseudo tabelle di tipo relazionale.
 * su ogni foglio di un file excel.
 * 
 * @author Dr. Domenico della Peruta
 */
public class AccessoFileExcel implements BaseDati {

    static protected final String CHIAVE = "chiave";
    static protected final String NON_CHIAVE = "-";
    
    static protected final int RIGA_NOME   = 0;
    static protected final int RIGA_CHIAVE = 1;
    static protected final int RIGA_TIPO   = 2;
    static protected final int RIGA_VALORE = 3;
    static protected final int PRIMA_RIGA  = 4;

    private final HashMap<String,String[][]> listaDatiTabelle = new HashMap<String,String[][]>();
    
    
    protected  FileExcel file = null;
    /**
     * Costruttore.
     * 
     * @param nomeFile percorso del file excel
     */
    public AccessoFileExcel(String nomeFile) {
        file = new FileExcel(nomeFile);
        
    }

    /**
     * Accedi alla lista dei dati di una tabella precedentemente acquisita per 
     * la manipolazione.
     * 
     * @param file
     * @param nomeTabella
     * @return 
     */
    public String[][] leggiTabella(FileExcel file,String nomeTabella){
        String[][] celle = null;
        if(file != null){
            celle = listaDatiTabelle.get(nomeTabella);
            if(celle == null){
                celle = file.leggiCelleDalFile(nomeTabella);
                if(celle != null)
                    listaDatiTabelle.put(nomeTabella, celle);
                
            }
        }
        return celle;
    }
    
    @Override
    public void connetti() throws EccezioneBaseDati {
        try {
            file.carattere(FileExcel.Carattere.ARIAL, FileExcel.Stile.NORMALE, 8, FileExcel.Colore.NERO);
            file.bordatura(FileExcel.Bordo.TUTTO, FileExcel.Linea.SOTTILE, FileExcel.Colore.NERO);
            file.coloraSfondo(FileExcel.Colore.BIANCO);
            
        } catch (WriteException ex) {
            Logger.getLogger(AccessoFileExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
                    
    }


    
    @Override
    public Object[] vediTupla(Relazione tabella, Object[] chiave) throws EccezioneBaseDati {
        throw new UnsupportedOperationException("Not supported yet.");
/*        
        Object[] record = null;
        String[][] relazione = file.leggiCelleDalFile(tabella.nome());
        if(relazione != null){
            if(relazione.length > 0){
                //TODO....
            }
        }
        return record;
*/
    }
        

    /**
     * Verifica se esiste il foglio dove risiede la relazione all'interno del file
     * excel.
     * 
     * @param tabella
     * @return 
     */
    public boolean esiste(Relazione tabella){
        return file.cercaFoglioNelFile(tabella.nome()) != -1;
    }
    
    /**
     * Verifica la corretta formattazione della struttura relazione
     * 
     * @param tabella
     * @param relazione 
     * @throws it.quasar_x7.java.BaseDati.EccezioneBaseDati 
     */
    protected void integrita(Relazione tabella,String[][] relazione) throws EccezioneBaseDati{
        final String ERRORE_INTEGRITA = "Errore integrità del tabella «%s»";
        if(tabella instanceof Relazione){
            if(relazione.length >= PRIMA_RIGA){
                String[] nomeColonne = relazione[RIGA_NOME];
                String[] chiaviColonne = relazione[RIGA_CHIAVE];
                String[] tipoColonne = relazione[RIGA_TIPO];
                //String[] valoreInizColonne = relazione[RIGA_VALORE];
                if(nomeColonne != null && chiaviColonne != null && tipoColonne != null){
                    ArrayList<Attributo> attr = tabella.vediTuttiAttributi();
                    if(attr != null){
                        if(nomeColonne.length >= attr.size() && chiaviColonne.length >= attr.size() && tipoColonne.length >= attr.size()){
                            int i=0;
                            for(Attributo colonna : attr){
                                if(colonna != null){
                                    //controllo nuovaRiga 1
                                    String nome = colonna.nome();
                                    if(nome != null){
                                        if(!nome.equals(nomeColonne[i]))
                                            throw new EccezioneBaseDati(
                                                    String.format(ERRORE_INTEGRITA,tabella.nome()),
                                                    String.format("nome `%s` diverso da `%s`",nome,nomeColonne[i] )
                                            );
                                    }else{
                                        throw new EccezioneBaseDati(
                                                String.format(ERRORE_INTEGRITA,tabella.nome()),
                                                String.format("nome nullo rispetto `%s`",nomeColonne[i] )
                                        );
                                    }
                                    //controllo nuovaRiga 2
                                    boolean chiave = colonna.chiave();
                                    if(chiave){
                                        if(!CHIAVE.equals(chiaviColonne[i]))
                                            throw new EccezioneBaseDati(
                                                String.format(ERRORE_INTEGRITA,tabella.nome()),
                                                String.format("la colonna `%d` erroniamente non è chiave",i )
                                            );

                                    }else{
                                        if(!NON_CHIAVE.equals(chiaviColonne[i]))
                                            throw new EccezioneBaseDati(
                                                String.format(ERRORE_INTEGRITA,tabella.nome()),
                                                String.format("la colonna `%d` erroniamente è chiave",i )
                                            );
                                    }
                                    //controllo nuovaRiga 3
                                    Dominio dominio = colonna.tipo();
                                    if(dominio != null && tipoColonne[i] != null){

                                        if(!dominio.toString().equals(tipoColonne[i])){
                                            throw new EccezioneBaseDati(
                                                String.format(ERRORE_INTEGRITA,tabella.nome()),
                                                String.format(
                                                        "la colonna `%d` di dominio `%s` ha valore  `%s`",
                                                        i,
                                                        dominio.toString(),
                                                        tipoColonne[i]
                                                ) 
                                            );
                                        }
                                    }else{
                                        throw new EccezioneBaseDati(
                                                String.format(ERRORE_INTEGRITA,tabella.nome()),
                                                String.format("la colonna `%d` ha dominio nullo",i)
                                        );
                                    }
                                }else{
                                    throw new EccezioneBaseDati(
                                            String.format(ERRORE_INTEGRITA,tabella.nome()),
                                            String.format("la colonna `%d` nulla",i)
                                    );
                                }
                                i++;
                            }
                        }else{
                            throw new EccezioneBaseDati(
                                String.format(ERRORE_INTEGRITA,tabella.nome()),
                                "la lunghezza degli attributi non coincide con quella di intestazione"
                            );
                        }
                    }else{
                        throw new EccezioneBaseDati(
                            String.format(ERRORE_INTEGRITA,tabella.nome()),
                            "attributi della relazione non validi"
                        );
                    }
                }else{
                   throw new EccezioneBaseDati(
                        String.format(ERRORE_INTEGRITA,tabella.nome()),
                        "una o più righe di intestazioni sono nulle"
                ); 
                }
            }else{
                throw new EccezioneBaseDati(
                        String.format(ERRORE_INTEGRITA,tabella.nome()),
                        String.format("la relazione ha numero di righe  `%d` < `%d",relazione.length, PRIMA_RIGA)
                );
            }
            
        }else{
            throw new EccezioneBaseDati(
                    String.format(ERRORE_INTEGRITA,"tabella non valida"),
                    ""
            );
        }
    }
    /**
     * Verifica la corretta formattazione del foglio tabella
     * 
     * @param tabella 
     * @throws it.quasar_x7.java.BaseDati.EccezioneBaseDati 
     */
    public void integrita(Relazione tabella) throws EccezioneBaseDati{
        String[][] relazione = leggiTabella(file, tabella.nome());
        integrita(tabella, relazione);
    }
    /**
     * Crea una tabella sul modello relazionale usando un foglio del file
     * in excel: generando l'intestazione con i relativi dati degli attributi
     * della futura relazione.
     * 
     * <code><pre>
     *  +---------------+-----------+------------+
     *  | nome_colonna_1|  nome_2   |   nome_3   |  nome colonna (attributo)
     *  +---------------+-----------+------------+
     *  |   chiave      |   chiave  | non chiave |  (identifica attr. chiave)
     *  +---------------+-----------+------------+
     *  |      INT      |VARCHAR(20)|  DATETIME  |  tipo di dato
     *  +---------------+-----------+------------+
     *  |       0       |    ""     |   NULL     |  valore iniziale
     *  +---------------+-----------+------------+
     *  ·               ·           ·            ·
     *  +...............+...........+............+   DATI RELAZIONE
     *  ·               ·           ·            ·
     *  +...............+...........+............+
     * 
     * </pre></code>
     * @param tabella
     * @throws EccezioneBaseDati 
     */
    @Override
    public void generaTabella(Relazione tabella) throws EccezioneBaseDati {
        try {
            file.coloraSfondo(FileExcel.Colore.GRIGIO);
            file.bordatura(FileExcel.Bordo.TUTTO, FileExcel.Linea.SPESSA, FileExcel.Colore.NERO);
            file.carattere(FileExcel.Carattere.ARIAL, FileExcel.Stile.GROSSETTO_CORSIVO, 10, FileExcel.Colore.BIANCO);
                    
            ArrayList<WritableCell> intestazione = new ArrayList<WritableCell>();
            int colonna = 0;
            for(Attributo cella:tabella.vediTuttiAttributi()){
                // nuovaRiga 1
                intestazione.add(file.cella(cella.nome(), colonna, RIGA_NOME));
                // nuovaRiga 2
                if(cella.chiave()){
                    intestazione.add(file.cella(CHIAVE, colonna, RIGA_CHIAVE));
                }else{
                    intestazione.add(file.cella(NON_CHIAVE, colonna, RIGA_CHIAVE));
                }
                // nuovaRiga 3
                Dominio dominio = cella.tipo();
                if(dominio != null){
                    intestazione.add(file.cella(dominio.toString(), colonna, RIGA_TIPO));
                }else{
                    intestazione.add(file.cella("null", colonna, RIGA_TIPO));
                }
                // nuovaRiga 4
                String valore = cella.valorePredefinito();
                if(valore != null)
                    intestazione.add(file.cella(valore, colonna, RIGA_VALORE));
                else
                    intestazione.add(file.cella("null", colonna, RIGA_VALORE));
                
                dimenzionaColonna(dominio != null ? dominio.toString() : "", tabella, colonna);
                colonna++;
            }
            
            if(!file.creaFoglio(tabella.nome(), intestazione)){
                file.aggiornaFoglio(tabella.nome(), intestazione);
            }
            
            
            
            
        } catch (WriteException ex) {
            throw new EccezioneBaseDati("errore creazione tabella",ex.getMessage());
        }
    }
    /**
     * Verifica se esiste già un valore 'chiave' nelle celle della tabella: se la trova restituisce la posizione della riga
     * in cui è presente, altrimenti restiuisce -1;
     * 
     * @param celle
     * @param chiave
     * @return 
     */
    protected int verificaChiave(String[][] celle,Object[] chiave){
        int riga= PRIMA_RIGA;
        for(; riga < celle.length; riga++){
            for(int colonna =0,colonnaK=0; colonnaK < chiave.length || colonna < celle[0].length; colonna++){
                if(celle[RIGA_CHIAVE][colonna].equals(CHIAVE)){ // la colonna è chiave
                    
                    String cella = celle[riga][colonna];
                    if(chiave[colonnaK] instanceof DataOraria){
                        DataOraria d = (DataOraria) chiave[colonnaK];
                        String sG1 = d.stampaGiorno('/'); // es.: "10/03/2019"
                        String sG2 = sG1 + " " + d.stampaOra();// es.: "10/03/2019 12:05"
                        if(!cella.equals(sG1) && !cella.equals(sG2)){
                            break;
                        }
                    }else{
                       if(!cella.equals(chiave[colonnaK].toString())){
                            break;
                        } 
                    }
                    colonnaK++;
                }
          
                if(colonnaK == chiave.length){
                    return riga;
                }
            }
        }
        return -1;
    }
    
    protected void dimenzionaColonna(String dominio,Relazione relazione, int colonna){
        if(dominio.equals(new DatoInteroCorto().toString())){
            file.dimensionaColonna(3000, colonna, relazione.nome());
        }else if(dominio.equals(new DatoInteroLungo().toString())){
            file.dimensionaColonna(3000, colonna, relazione.nome());
        }else if(dominio.equals(new DatoRealeSemplice().toString())){
            file.dimensionaColonna(3000, colonna, relazione.nome());
        }else if(dominio.equals(new DatoRealeDoppiaPrecisione().toString())){
            file.dimensionaColonna(3000, colonna, relazione.nome());
        }else if(dominio.contains("VARCHAR(")){
            try{
                int lim =Integer.parseInt(dominio.substring(dominio.indexOf("(")+1,dominio.indexOf(")")));
                file.dimensionaColonna(270*lim, colonna, relazione.nome());

            }catch(NumberFormatException e){
                file.dimensionaColonna(7000, colonna, relazione.nome());
            }
            
        }else if(dominio.equals(new DatoData().toString())){
            file.dimensionaColonna(3000, colonna, relazione.nome());
        }else if(dominio.equals(new DatoDataOraria().toString())){
            file.dimensionaColonna(5000, colonna, relazione.nome());
        }else{
            file.dimensionaColonna(7000, colonna, relazione.nome());
        }
    }
    
    protected void dimenzionaColonna(String[][] tabella,Relazione relazione, int colonna){
        dimenzionaColonna(tabella[RIGA_TIPO][colonna], relazione, colonna);
        
    }
    
    protected Object[] verificaDati(String[][] tabella,Object[] record) throws EccezioneBaseDati{
        Object[] tupla = new Object[record.length];
        for(int colonna =0; colonna < record.length; colonna++){
            // INT
            if(tabella[RIGA_TIPO][colonna].equals(new DatoInteroCorto().toString())){
                if(record[colonna] instanceof Integer){
                    tupla[colonna] = record[colonna];
                }else{
                    throw new EccezioneBaseDati(
                        "errore conversione record",
                        "tipo non numero intero corto"
                    );
                }
            // LONG
            }else if(tabella[RIGA_TIPO][colonna].equals(new DatoInteroLungo().toString())){
                if(record[colonna] instanceof Long){
                    tupla[colonna] = record[colonna];
                }else{
                    throw new EccezioneBaseDati(
                            "errore conversione record",
                            "tipo non numero intero lungo"
                    );
                }
            //FLOAT
            }else if(tabella[RIGA_TIPO][colonna].equals(new DatoRealeSemplice().toString())){
                if(record[colonna] instanceof Float){
                    tupla[colonna] = record[colonna];
                }else{
                    throw new EccezioneBaseDati(
                        "errore conversione record",
                        "tipo non numero reale a semplice precisione"
                    );
                }
            //DOUBLE
            }else if(tabella[RIGA_TIPO][colonna].equals(new DatoRealeDoppiaPrecisione().toString())){
                if(record[colonna] instanceof Double){
                    tupla[colonna] = record[colonna];
                }else{
                    throw new EccezioneBaseDati(
                            "errore conversione record",
                            "tipo non numero reale a doppia precisione"
                    );
                }
            //STRING
            }else if(tabella[RIGA_TIPO][colonna].contains("VARCHAR(")){
                if(record[colonna] instanceof String){
                    String s = tabella[RIGA_TIPO][colonna];
                    try{
                        int lim =Integer.parseInt(s.substring(s.indexOf("(")+1,s.indexOf(")")));
                        if(s.length() > lim)
                            tupla[colonna] = record[colonna].toString().substring(0, lim);
                        else
                            tupla[colonna] = record[colonna];
                        
                    }catch(NumberFormatException e){
                        throw new EccezioneBaseDati(
                                "errore conversione record",
                                "tipo stringa di lunghezza indefinita"
                        );
                    }
                }else{
                    throw new EccezioneBaseDati(
                            "errore conversione record",
                            "tipo non stringa"
                    );
                }
            //TEMPO
            }else if(tabella[RIGA_TIPO][colonna].equals(new DatoDataOraria().toString()) || tabella[RIGA_TIPO][colonna].equals(new DatoData().toString())){
                if(record[colonna] instanceof DataOraria){
                    tupla[colonna] = record[colonna];
                }
            //TESTO 
            }else if(tabella[RIGA_TIPO][colonna].equals(new DatoTesto().toString()) ){
                if(record[colonna] instanceof String){
                    tupla[colonna] = record[colonna];
                }
            //BOOL
            }else if(tabella[RIGA_TIPO][colonna].equals(new DatoBooleano().toString()) ){
                if(record[colonna] instanceof Boolean){
                    tupla[colonna] = record[colonna];
                }
            }else{
                throw new EccezioneBaseDati(
                        "errore conversione record",
                        String.format(
                                "tipi di dati non corrispondenti alla tabella\n"
                                        + "- dato: %s"
                                        + "- tipo: %s",
                                record[colonna].toString(),
                                tabella[RIGA_TIPO][colonna]
                                )
                );
            }
        }
        return tupla;
    }
    
    protected int tuplaVuota(Relazione tabella,String[][] celle){
        int riga= PRIMA_RIGA;
        for(; riga < celle.length; riga++){
            for(int colonna =0; colonna < tabella.vediTuttiAttributi().size(); colonna++){
   
                String cella = celle[riga][colonna];
                if(cella != null)
                    if(!cella.equals("")){
                        break;
                    }
                if(colonna == tabella.vediTuttiAttributi().size()-1)
                    return riga;
            }
        }
        return riga;//riga fuori campo tabella precedente
    }
    /**
     * Identifica gli attributi chiave.
     * 
     * @param tabella
     * @param record
     * @return 
     */
    protected Object[] chiave(Object[][] tabella,Object[] record){
        ArrayList<Object> elenco = new ArrayList<Object>();
        for(int colonna =0; colonna < record.length; colonna++){
            if(tabella[RIGA_CHIAVE][colonna].equals(CHIAVE)){ // la colonna è chiave
                elenco.add(record[colonna]);
            }
        }
        return elenco.toArray();
    }

    /**
     * Permette di ottenere un dato di tipo stringa adatto a essere scritto su file.
     * 
     * @param dato
     * @return 
     */
    private String correggiDato(Object dato){
        String stringa;
        if(dato instanceof Boolean)
            stringa = (Boolean)dato==true ? "Sì" : "No";
        else  
            stringa = dato != null ? dato.toString() : "";
        return stringa;
    }
    
    /**
     * 
     * @param nomeTabella
     * @param id                    indice della nuova riga 
     * @param record
     * @throws EccezioneBaseDati 
     */
    public void aggiornaDatiTabella(String nomeTabella,int id, Object[] record) throws EccezioneBaseDati{
        String[][] dati = listaDatiTabelle.get(nomeTabella);
    
        if(dati != null && record != null){

            if(id >= 0 && id < dati.length){
    
                if(dati[id] != null){
                    for(int i=0; i < dati[id].length; i++)
                        dati[id][i] = correggiDato(record[i]);
                             
                    listaDatiTabelle.put(nomeTabella,dati);
                }
                // nuovo riga
            }else if(id == dati.length){
    
                String[][] nuoviDati = new String[dati.length+1][dati[0].length];
                int i =0;
                for (String[] riga : dati) {
                    nuoviDati[i++] = riga;
                }
    
                String[] nuovaRiga = new String[dati[0].length];
                for(int j=0; j < dati[0].length; j++){
                    nuovaRiga[j] = correggiDato(record[j]);
                }
                nuoviDati[i] = nuovaRiga;
                
                listaDatiTabelle.put(nomeTabella,nuoviDati);
    
    
            }else{
                throw new EccezioneBaseDati(
                        "errore aggiornamento della tabella dati interna",
                        String.format(
                                "tabella %s, indice input `%d` fuori intervallo (lunghezza dati letti `%d`)",
                                nomeTabella,id,dati.length
                        )
                );
            }
            
        }else{
            throw new EccezioneBaseDati(
                    "errore aggiornamento della tabella dati interna",
                    String.format(
                            "dati %s tabella `%s` nulli !",
                            dati != null ? "dati lettura della" : "input record da aggiungere alla",
                            nomeTabella
                    )
            );
        }
    }
    
    public void rimuoviDatiTabella(String nomeTabella,int id){
        String[][] dati = listaDatiTabelle.get(nomeTabella);
        if(dati != null ){
            if(id >=0 && id < dati.length){
                ArrayList<String[]> nuoviDati = new ArrayList<String[]>();
                for (String[] riga : dati) {
                    String[] nuovaRiga = new String[dati[0].length];
                    System.arraycopy(riga, 0, nuovaRiga, 0, dati[0].length);
                    nuoviDati.add(nuovaRiga);
                }
                nuoviDati.remove(id);
                String[][] celle = nuoviDati.toArray(new String[dati[0].length][nuoviDati.size()]);
                listaDatiTabelle.put(nomeTabella,celle);
            }
            
        }
    }
   
    @Override
    public void aggiungiTupla(Relazione tabella, Object[] record) throws EccezioneBaseDati {
        String[][] celle = leggiTabella(file, tabella.nome());
        if(celle != null){
            try {
                file.bordatura(FileExcel.Bordo.TUTTO, FileExcel.Linea.SOTTILE, FileExcel.Colore.NERO);
                file.coloraSfondo(FileExcel.Colore.BIANCO);
                file.carattere(FileExcel.Carattere.ARIAL, FileExcel.Stile.NORMALE, 8, FileExcel.Colore.NERO);
                integrita(tabella,celle);
    
                if(tabella.vediTuttiAttributi().size() == record.length){
                    Object[] tupla = verificaDati(celle, record);
                    Object[] chiave = chiave(celle, tupla);
           
                    if(verificaChiave(celle, chiave) < 0){
            
                        int idRiga = tuplaVuota(tabella, celle);
                        int colonna =0;
                        for(Object valoreCella : tupla){
                            dimenzionaColonna(celle, tabella, colonna);
                            file.aggiungiCella(valoreCella, colonna++, idRiga, tabella.nome());
                        }
                        aggiornaDatiTabella(tabella.nome(),idRiga,record);
        
                    }else{
                        throw new EccezioneBaseDati(
                                "errore di duplicazione record",
                                String.format(
                                        "tabella: %s\n"
                                                + "record: %s", 
                                        tabella.nome(),
                                        stampa(record)
                                )
                        );
                    }

                }else{
                    throw new EccezioneBaseDati("errore aggiunta record","lunghezza record non corretta");
                }
                
            } catch (WriteException ex) {
                throw new EccezioneBaseDati("errore aggiunta record",ex.getMessage());
            }
        }else{
            throw new EccezioneBaseDati("errore aggiunta record","impossibile leggere il file");
        }
    }

    @Override
    public void modificaTupla(Relazione tabella, Object[] chiave, Object[] record) throws EccezioneBaseDati {
        String[][] celle = leggiTabella(file, tabella.nome());
        if(celle != null){
            int id = verificaChiave(celle, chiave);
            if(id >= PRIMA_RIGA){
                file.modificaCelle(tabella.nome(), id, record);
                aggiornaDatiTabella(tabella.nome(),id,record);
                
            }else{
                throw new EccezioneBaseDati("errore modifica record","record inesistente");
            }
        }else{
            throw new EccezioneBaseDati("errore modifica record","impossibile leggere il file");
        }
    }

    @Override
    public void eliminaTupla(Relazione tabella, Object[] chiave) throws EccezioneBaseDati {
        String[][] celle = leggiTabella(file, tabella.nome());
        if(celle != null){
            int id = verificaChiave(celle, chiave);
            if(id >= PRIMA_RIGA){
                file.rimuoviRiga(id, tabella.nome());
                rimuoviDatiTabella(tabella.nome(),id);
            }else{
                throw new EccezioneBaseDati("errore eliminazione record","record inesistente");
            }
        }else{
            throw new EccezioneBaseDati("errore eliminazione record","impossibile leggere il file");
        }
    }

    @Override
    public ArrayList<Object[]> interrogazioneSQL(String query, Attributo[] dati) throws EccezioneBaseDati {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Object[]> interrogazioneSempliceTabella(Relazione tabella, String condizione) throws EccezioneBaseDati {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Object[]> interrogazioneSempliceTabella(Relazione tabella, Attributo[] colonne, String condizione) throws EccezioneBaseDati {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Object[]> interrogazioneJoin(Relazione[] tabella, Attributo[] colonne, String condizione) throws EccezioneBaseDati {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Object[]> interrogazioneJoin(Relazione tabellaSx, Relazione tabellaDx, boolean AbilitaLeftOuter, Attributo[] colonne, String condizione) throws EccezioneBaseDati {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Object[]> vediTutteLeTuple(Relazione tabella) throws EccezioneBaseDati {
        ArrayList<Object[]> elencoTuple=new ArrayList<Object[]>();
        String[][] celle = leggiTabella(file, tabella.nome());
        if(celle != null){
            integrita(tabella,celle);
            int maxRiga = this.tuplaVuota(tabella, celle);
            int maxCol = tabella.vediTuttiAttributi().size();
            for(int riga = PRIMA_RIGA; riga < maxRiga; riga++){
                Object[] record = new Object[maxCol];
                for(int col=0; col < maxCol; col++){
                    // INTERO CORTO
                    if(celle[RIGA_TIPO][col].equals(new DatoInteroCorto().toString())){
                        try{
                            record[col] = Integer.parseInt(celle[riga][col]);
                        }catch(NumberFormatException e){
                        }
                    // INTERO LUNGO
                    }else if(celle[RIGA_TIPO][col].equals(new DatoInteroLungo().toString())){
                        try{
                            record[col] = Long.parseLong(celle[riga][col]);
                        }catch(NumberFormatException e){
                        }
                    // REALE SEMPLICE PRECISIONE
                    }else if(celle[RIGA_TIPO][col].equals(new DatoRealeSemplice().toString())){
                        try{
                            record[col] = Float.parseFloat(celle[riga][col]);
                        }catch(NumberFormatException e){
                        }
                    // REALE DOPPIA PRECISIONE
                    }else if(celle[RIGA_TIPO][col].equals(new DatoRealeDoppiaPrecisione().toString())){
                        try{
                            record[col] = Double.parseDouble(celle[riga][col]);
                        }catch(NumberFormatException e){
                        }
                    // STRINGA
                    }else if(celle[RIGA_TIPO][col].contains("VARCHAR(")){
                        if(celle[riga][col] instanceof String){
                            String s = celle[RIGA_TIPO][col];
                            try{
                                int lim =Integer.parseInt(s.substring(s.indexOf("(")+1,s.indexOf(")")));
                                if(celle[riga][col].length() > lim)
                                    record[col] = celle[riga][col].substring(0, lim);
                                else
                                    record[col] = celle[riga][col];

                            }catch(NumberFormatException e){
                            }
                        }
                    // DATA ORARIA
                    }else if(celle[RIGA_TIPO][col].equals(new DatoDataOraria().toString()) || celle[RIGA_TIPO][col].equals(new DatoData().toString())){
                        if(celle[riga][col] instanceof String){
                            try {
                                record[col] = new DataOraria(celle[riga][col]);
                            } catch (Errore ex) {
                            }
                        }
                    // TESTO
                    }else if(celle[RIGA_TIPO][col].equals(new DatoTesto().toString())){
                        if(celle[riga][col] instanceof String){
                            record[col] = celle[riga][col];
                        }
                    // BOOLEAN
                    }else if(celle[RIGA_TIPO][col].equals(new DatoBooleano().toString())){
                        if(celle[riga][col] instanceof String){
                            if(celle[riga][col].equals("true") || celle[riga][col].equals("sì") || celle[riga][col].equals("Sì") || celle[riga][col].equals("SÌ") || celle[riga][col].equals("vero") || celle[riga][col].equals("Vero") || celle[riga][col].equals("VERO"))
                                record[col] = true; 
                            else if(celle[riga][col].equals("false") || celle[riga][col].equals("no") || celle[riga][col].equals("No") || celle[riga][col].equals("NO") || celle[riga][col].equals("Falso") || celle[riga][col].equals("falso") || celle[riga][col].equals("FALSO"))
                                record[col] = false; 
                            
                        }
                    }
                }
                elencoTuple.add(record);
            }
        }else{
            throw new EccezioneBaseDati("errore visualizzazione tabella","impossibile leggere il file");
        }
        return elencoTuple;
    }

    
    /**
     * Chiude l'accesso alla risorsa del file.
     * @throws EccezioneBaseDati 
     */
    @Override
    public void chiudi() throws EccezioneBaseDati {
        applicaModifice();
        file.chiudi();
    }
    
    private void applicaModifice() throws EccezioneBaseDati{
        if(!file.applicaModifiche()){
            file.chiudi();
            throw new EccezioneBaseDati("errore scrittura file", "");
        }
    }
    
    /**
     * Metodo statico che crea in modo automatico un file con relativa tabella in xls.
     * 
     * @param file
     * @param tabella
     * @throws EccezioneBaseDati 
     */
    public static void creaFile(File file, Relazione tabella) throws EccezioneBaseDati {
        AccessoFileExcel creaFile = new AccessoFileExcel(file.getAbsolutePath());
        creaFile.connetti();
        creaFile.generaTabella(tabella);
        creaFile.chiudi();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }
    }
    
    public static String stampa(Object[] record){
        String s = "\n";
        int i = 0;
        for(Object voce:record){
            s += ""+i + ")\t";
            if(voce != null)
                s += voce.toString();
            s += "\n";
            i++;
        }
        return s + "\n";
    }
}

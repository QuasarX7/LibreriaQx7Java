package it.quasar_x7.java.excel;

import it.quasar_x7.java.utile.DataOraria;
import it.quasar_x7.java.utile.Errore;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.Cell;
import jxl.CellType;
import jxl.CellView;
import jxl.FormulaCell;
import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.biff.DisplayFormat;
import jxl.biff.formula.FormulaException;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableFont.FontName;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;



/**
 *  Libreria per leggere o modificare un file di tipo Excel (.xls)
 * 
 * @author Dr Domenico della Peruta
 * @version 1.0.2 29/09/2017
 */
public class FileExcel {

    
    
    
    public enum Carattere{
        ARIAL,TIMES ,COURIER,TAHOMA}
    
    public enum Stile{
        NORMALE,GROSSETTO,CORSIVO,GROSSETTO_CORSIVO
    }
    
    public enum Bordo{
        PRIVO,TUTTO, SINISTRA,DESTRA,SOPRA,SOTTO
    }
    public enum Colore{
        NERO,BLU,VERDE,ROSSO,GIALLO,GRIGIO,BIANCO,NESSUNO
    }
    public enum Linea{
        SOTTILE, DOPPIA,SPESSA,TRATTEGGIATA
    }
    
    
    protected File file = null;
    
    Workbook accediFile = null;
    //WritableWorkbook scriviFile = null;
    
    protected HashMap<String,ArrayList<WritableCell>> datiCelle = new HashMap<String,ArrayList<WritableCell>>();
    protected HashMap<String,ArrayList<CellView>> configurazioneColonne = new HashMap<String,ArrayList<CellView>>();
    protected HashMap<String,ArrayList<CellView>> configurazioneRighe = new HashMap<String,ArrayList<CellView>>();
    protected HashMap<String,Range[]> celleUnite = new HashMap<String,Range[]>();
    
    // variabili in scrittura
    private WritableFont    formatoCarattere;
    private BorderLineStyle lineeBordo;
    private Colour coloreBordo,coloreSfondo,coloreTesto;
    private Border bordatura;
    
    /**
     * Costruttore
     * 
     * @param file 
     */
    public FileExcel(File file){
        this.file = file;
        inizializzaValoriInScrittura();
        
        try {
            accediFile = Workbook.getWorkbook(file,configurazione());
            
            caricaDati();
            caricaConfigurazioniColonne();
            caricaConfigurazioniRighe();
            caricaUnioniCelle();
        } catch (FileNotFoundException ex){
            accediFile = null;
        } catch (IOException ex) {
            Logger.getLogger(FileExcel.class.getName()).log(Level.SEVERE, null, ex);
            accediFile = null;
        } catch (BiffException ex) {
            if(!ex.getMessage().contains("The input file was not found")){
                Logger.getLogger(FileExcel.class.getName()).log(Level.SEVERE, null, ex);
            }
            accediFile = null;
        } catch (ArrayIndexOutOfBoundsException ex){
            accediFile = null;
        }
    }
    
    /**
     * Costruttore.
     * 
     * @param file 
     */
    public FileExcel(String file){
        this(new File(file));
    }
    /**
     * Inizializza i parametri standard di formattazione in fase di modifica.
     */
    protected final void inizializzaValoriInScrittura(){
        try {
            coloraSfondo(Colore.NESSUNO);
            carattere(Carattere.ARIAL, Stile.NORMALE, 10,Colore.NERO);  
            bordatura(Bordo.PRIVO,Linea.SOTTILE, Colore.NERO);

        } catch (WriteException ex) {
            Logger.getLogger(FileExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private WorkbookSettings configurazione(){
        WorkbookSettings configurazione = new WorkbookSettings();
        configurazione.setLocale(new Locale("it", "IT"));
        configurazione.setEncoding("Cp1252");
        return configurazione;
    }
    
    /**
     * Numero di fogli
     * @return 
     */
    public int numeroFogli() {
         if(accediFile != null){
            return accediFile.getNumberOfSheets();
        }
        return -1;
    }
    
    /**
     * Crea un foglio di calcolo (solo se non esiste) 
     * che verrà aggiunto al file alla fine del processo.
     * 
     * @param nome      del foglio
     * @return 
     */
    public boolean creaFoglio(String nome) {
        return creaFoglio(nome, new ArrayList<WritableCell>());
    }
    
    public int numeroColonne(String nomeFoglio){
        if(accediFile != null){
            Sheet foglio = accediFile.getSheet(nomeFoglio);
            if(foglio != null)
                return foglio.getColumns();
            
        }
        return -1;
    }
    
    public int numeroRighe(String nomeFoglio){
        if(accediFile != null){
            Sheet foglio = accediFile.getSheet(nomeFoglio);
            if(foglio != null)
                return foglio.getRows();
            
        }
        return -1;
    }
    
    /**
     * Accedi ad una cella di un file esistente
     * 
     * @param nomeFoglio
     * @param colonna
     * @param riga
     * @return è null se non esiste una cella acquisita in lettura.
     */
    public Cell cellaFoglio(String nomeFoglio,int colonna,int riga){
        if(accediFile != null){
            Sheet foglio = accediFile.getSheet(nomeFoglio);
            if(foglio != null){
                return foglio.getCell(colonna, riga);
            }
        }
        return null;
    }
    /**
     * Crea una lista di tutte le celle di un foglio.
     * @param idFoglio ID del foglio
     * 
     * @return restituisce null se non esiste il foglio altrimenti un ArrayList con le celle create
     */
    public ArrayList<WritableCell> celleFoglio(int idFoglio){
        ArrayList<WritableCell> lista = null;
        if(accediFile != null){
            lista = new ArrayList<WritableCell>();
            if(idFoglio >= 0 &&  idFoglio < accediFile.getNumberOfSheets()){
                Sheet foglio = accediFile.getSheet(idFoglio);
                for(int riga =0; riga < foglio.getRows(); riga++){
                    Cell[] celleRiga = foglio.getRow(riga);
                    if(celleRiga != null)
                        for (Cell cella : celleRiga) {
                            if(cella != null){
                                WritableCell valore = converti(cella);
                                if(valore != null)
                                    lista.add(valore);
                            }
                        }
                        
                }
            }
        }
        return lista;
    }
    protected static WritableCellFormat converti(CellFormat formato,DisplayFormat tipo){
        try {
            
            WritableFont font = new WritableFont(formato.getFont());
            formato.getAlignment();
            WritableCellFormat formatoInModifica
                    = (tipo != null)
                    ? new WritableCellFormat(font,tipo)
                    : new WritableCellFormat(font);
            
            formatoInModifica.setAlignment(formato.getAlignment());
            formatoInModifica.setBackground(formato.getBackgroundColour(),formato.getPattern());
            Border[] border = new Border[]{
                jxl.format.Border.ALL,
                jxl.format.Border.NONE,
                jxl.format.Border.LEFT,
                jxl.format.Border.RIGHT,
                jxl.format.Border.TOP,
                jxl.format.Border.BOTTOM           
            };
            for(Border tipoBorder: border){
                if(formato.getBorderLine(tipoBorder) != null && formato.getBorderColour(tipoBorder) != null)
                    formatoInModifica.setBorder(tipoBorder,formato.getBorderLine(tipoBorder), formato.getBorderColour(tipoBorder));
            }
            formatoInModifica.setIndentation(formato.getIndentation());
            formatoInModifica.setLocked(formato.isLocked());
            formatoInModifica.setOrientation(formato.getOrientation());
            formatoInModifica.setShrinkToFit(formato.isShrinkToFit());
            formatoInModifica.setVerticalAlignment(formato.getVerticalAlignment());
            formatoInModifica.setWrap(formato.getWrap());
            
            return formatoInModifica;
        
        } catch (WriteException ex) {
            Logger.getLogger(FileExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    /**
     * Converte una cella di sola lettura in una riscriviblile;
     * @param cella
     * @return 
     */
    protected static WritableCell converti(Cell cella){
        CellType tipo = cella.getType();
        if(tipo == CellType.LABEL){
            return new Label(cella.getColumn(),cella.getRow(),cella.getContents(),converti(cella.getCellFormat(),null));
        } else if(tipo == CellType.NUMBER){
            String sNumero = cella.getContents();
            Double valore = 0.0;
            sNumero = sNumero.replace(',', '.');
            if(sNumero.length() > 0){
                valore = new Double(sNumero);
            }
            
            return new jxl.write.Number(cella.getColumn(),cella.getRow(),valore,converti(cella.getCellFormat(),sNumero.contains(".") ? NumberFormats.FLOAT : NumberFormats.INTEGER));
        }else if(tipo == CellType.DATE){
            try {
                DataOraria data = new DataOraria(cella.getContents());
                return new DateTime(cella.getColumn(),cella.getRow(),data.convertiDate(),converti(cella.getCellFormat(),new DateFormat ("dd/MM/yyyy")));
            } catch (Errore ex) {
                Logger.getLogger(FileExcel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(tipo == CellType.NUMBER_FORMULA || tipo == CellType.STRING_FORMULA || tipo == CellType.BOOLEAN_FORMULA 
                ||  tipo == CellType.DATE_FORMULA ||  tipo == CellType.FORMULA_ERROR){
            try {
                return new Formula(cella.getColumn(),cella.getRow(),((FormulaCell)cella).getFormula(),converti(cella.getCellFormat(),null) );
            } catch (FormulaException ex) {
                return new Label(cella.getColumn(),cella.getRow(),"formula????",converti(cella.getCellFormat(),null));
            }
        }
         
        return null;
    }
    /**
     * Restituisce il nome del foglio se esiste nel file.
     * @param id
     * @return 
     */
    public String nomeFoglio(int id){
        if(accediFile != null){
            if(id >= 0 && id < accediFile.getNumberOfSheets()){
               return accediFile.getSheet(id).getName();
            }   
        }
        return null;
    }
    
    /**
     * Carica tutte le celle del file nella "struttura" datiCelle
     */
    protected final void caricaDati(){
        if(accediFile != null){
            for(int i=0; i < accediFile.getNumberOfSheets(); i++){
                String nomeFoglio = accediFile.getSheet(i).getName();
                datiCelle.put(nomeFoglio,celleFoglio(i));
            }   
        }
    }
    /**
     * Carica le impostazioni delle colonne dal file alla "struttura" 
     * configurazioneColonne
     */
    protected final void caricaConfigurazioniColonne(){
        if(accediFile != null){
            for(Sheet foglio : accediFile.getSheets()){
                ArrayList<CellView> configColonneFoglio = new ArrayList<CellView>();
                for(int colonna=0; colonna < foglio.getColumns(); colonna++){
                    CellView configColonna = foglio.getColumnView(colonna);
                    if(configColonna != null)
                        configColonneFoglio.add(configColonna);
                }
                configurazioneColonne.put(foglio.getName(), configColonneFoglio);
            }   
        }
    }
    protected final void caricaUnioniCelle(){
        if(accediFile != null){
            for(Sheet foglio : accediFile.getSheets()){
                celleUnite.put(foglio.getName(), foglio.getMergedCells());
                
            }   
        }
    }
    /**
     * Carica le impostazioni delle righe dal file alla "struttura" 
     * configurazioneRighe
     */
    protected final void caricaConfigurazioniRighe(){
        if(accediFile != null){
            for(Sheet foglio : accediFile.getSheets()){
                ArrayList<CellView> configRigheFoglio = new ArrayList<CellView>();
                for(int riga=0; riga < foglio.getRows(); riga++){
                    CellView configRiga = foglio.getRowView(riga);
                    if(configRiga != null)
                        configRigheFoglio.add(configRiga);
                }
                configurazioneRighe.put(foglio.getName(), configRigheFoglio);
            }   
        }
    }
    
    /**
     * Crea un foglio excel solo se non esiste già.
     * 
     * @param foglio
     * @param celle
     * @return 
     */
    public boolean creaFoglio(String foglio, ArrayList<WritableCell> celle){
        if(datiCelle.get(foglio) == null){
            datiCelle.put(foglio, celle);
            return true;
        }
        return false;
    }
    /**
     * Crea o sostituisce foglio excel.
     * 
     * @param foglio
     * @param celle 
     */
    public void sostituisciFoglio(String foglio, ArrayList<WritableCell> celle){
        datiCelle.put(foglio, celle);
    }
    /**
     * Aggiungi le nuove celle al foglio precedente.
     * @param foglio
     * @param celle 
     */
    public void aggiornaFoglio(String foglio, ArrayList<WritableCell> celle){
        ArrayList<WritableCell> vecchiDati = datiCelle.get(foglio);
        if(vecchiDati != null)
            datiCelle.put(foglio, unisci(vecchiDati,celle));
        else
            datiCelle.put(foglio, celle);
    }
    /**
     * Sostituisci le celle vecchie con quelle nuove, se ci sono.
     * @param vecchio
     * @param nuovo
     * @return 
     */
    private ArrayList<WritableCell> unisci(ArrayList<WritableCell>vecchio,ArrayList<WritableCell>nuovo){
        class Posizione{
            int colonna = 0;
            int riga = 0;
            Posizione(WritableCell cella){
                colonna = cella.getColumn();
                riga =cella.getRow();
            }
            @Override
            public int hashCode() {
                int hash = 5;
                hash = 89 * hash + this.colonna;
                hash = 89 * hash + this.riga;
                return hash;
            }

            @Override
            public boolean equals(Object obj) {
                if(obj instanceof Posizione){
                    if (this.colonna == ((Posizione)obj).colonna && this.riga == ((Posizione)obj).riga) {
                        return false;
                    }
                }
                return false;
            }
            
        }
        HashMap<Posizione,WritableCell> unione = new HashMap<Posizione,WritableCell>();
        for(WritableCell cella : vecchio){
                unione.put(new Posizione(cella),cella);
        }
        for(WritableCell cella : nuovo){
                unione.put(new Posizione(cella),cella);
        }
        ArrayList<WritableCell> elenco = new ArrayList<WritableCell>();
        for(Posizione posizione : unione.keySet()){
            elenco.add(unione.get(posizione));
        }
        return elenco;
    }
    /**
     * Cerca il nome del foglio nel file, se lo trova restituisce l' ID, altrimenti -1.
     * 
     * @param nome
     * @return 
     */
    public int cercaFoglioNelFile(String nome){
        int id = 0;
        if(accediFile != null){
            for(Sheet foglio: accediFile.getSheets()){
                if(foglio.getName() == null ? nome == null : foglio.getName().equals(nome)) 
                    return id;
                id++;
            }
        }
        return -1;
    }
    /**
     * Imposta le caratteristiche del testo delle celle che verrano costruite dopo la chiamata del metodo.
     * 
     * @param tipo                  font del carattere
     * @param stile                 grossetto, corsivo o normale
     * @param dimensione            
     * @param colore
     * 
     * @throws WriteException 
     */
    public void carattere(Carattere tipo,Stile stile, int dimensione,Colore colore) throws WriteException{
        FontName font;
        switch(tipo){
            case TIMES: font = WritableFont.TIMES;break;
            case ARIAL: font = WritableFont.ARIAL;break;
            case COURIER: font = WritableFont.COURIER;break;
            case TAHOMA: font = WritableFont.TAHOMA;break; 
            default: font= WritableFont.ARIAL;break;                    
        }
        switch(colore){
           case NERO: coloreTesto = jxl.format.Colour.BLACK;break; 
           case BLU: coloreTesto = jxl.format.Colour.BLUE;break;  
           case VERDE: coloreTesto = jxl.format.Colour.GREEN;break; 
           case ROSSO: coloreTesto = jxl.format.Colour.RED;break;     
           case GIALLO: coloreTesto = jxl.format.Colour.YELLOW;break; 
           case BIANCO: coloreTesto = jxl.format.Colour.WHITE;break; 
           case GRIGIO: coloreTesto = jxl.format.Colour.GREY_40_PERCENT;break;
           default:coloreTesto = jxl.format.Colour.UNKNOWN;break;
       }
        if(null != stile)
            switch (stile) {
            case NORMALE:
                formatoCarattere= new WritableFont(font, dimensione, WritableFont.NO_BOLD,false);
                break;
            case GROSSETTO:
                formatoCarattere = new WritableFont(font, dimensione, WritableFont.BOLD,false);
                break;
            case CORSIVO:
                formatoCarattere= new WritableFont(font, dimensione, WritableFont.NO_BOLD,true);
                break;
            case GROSSETTO_CORSIVO:
                formatoCarattere = new WritableFont(font, dimensione, WritableFont.BOLD,true);             
                break;
            default:
                break;
            }
        formatoCarattere.setColour(coloreTesto);
        
    }
    
    /**
     * Imposta la configurazione del bordo delle cella create dopo la chiamata del metodo.
     * 
     * @param tipo              bordo interessato
     * @param linea             tipo di linea
     * @param colore
     * @throws WriteException 
     */
    public void bordatura(Bordo tipo,Linea linea, Colore colore) throws WriteException{
         bordatura =jxl.format.Border.NONE;

        switch(tipo){
            case TUTTO: bordatura= jxl.format.Border.ALL;break; 
            case PRIVO: bordatura= jxl.format.Border.NONE;break;  
            case SINISTRA: bordatura= jxl.format.Border.LEFT;break; 
            case DESTRA: bordatura= jxl.format.Border.RIGHT;break; 
            case SOPRA: bordatura= jxl.format.Border.TOP;break;
            case SOTTO: bordatura= jxl.format.Border.BOTTOM;break;            
        }
         coloreBordo= jxl.format.Colour.BLACK;

        switch(colore){
            case NERO: coloreBordo = jxl.format.Colour.BLACK;break; 
            case BLU: coloreBordo = jxl.format.Colour.BLUE;break;  
            case VERDE: coloreBordo = jxl.format.Colour.GREEN;break; 
            case ROSSO: coloreBordo = jxl.format.Colour.RED;break;     
            case GIALLO: coloreBordo = jxl.format.Colour.YELLOW;break; 
            case BIANCO: coloreBordo = jxl.format.Colour.WHITE;break; 
            case GRIGIO: coloreBordo = jxl.format.Colour.GREY_40_PERCENT;break;
            
            default:coloreBordo = jxl.format.Colour.UNKNOWN;break;
        }
         lineeBordo= jxl.format.BorderLineStyle.NONE;

        switch(linea){
            case SOTTILE: lineeBordo = jxl.format.BorderLineStyle.THIN;break; 
            case DOPPIA: lineeBordo = jxl.format.BorderLineStyle.DOUBLE;break;
            case TRATTEGGIATA: lineeBordo = jxl.format.BorderLineStyle.DOTTED;break;  
            case SPESSA: lineeBordo = jxl.format.BorderLineStyle.MEDIUM;break;           

        }


    }
   
    /**
     * Imposta lo sfondo delle celle che verrano create dopo la chiamata del metodo.
     * @param sfondo 
     */
    public void coloraSfondo( Colore sfondo) {
        switch(sfondo){
           case NERO: coloreSfondo = jxl.format.Colour.BLACK;break; 
           case BLU: coloreSfondo = jxl.format.Colour.BLUE;break;  
           case VERDE: coloreSfondo = jxl.format.Colour.GREEN;break; 
           case ROSSO: coloreSfondo = jxl.format.Colour.RED;break;     
           case GIALLO: coloreSfondo = jxl.format.Colour.YELLOW;break; 
           case BIANCO: coloreSfondo = jxl.format.Colour.WHITE;break; 
           case GRIGIO: coloreSfondo = jxl.format.Colour.GREY_40_PERCENT;break;
            
           default:coloreSfondo = jxl.format.Colour.UNKNOWN;break;
       }        
        
    }
    /**
     * Aggiungi nuova cella al file da modificare.
     * 
     * @param contenuto
     * @param colonna
     * @param riga
     * @param foglio
     */
    public void aggiungiCella(Object contenuto,int colonna,int riga,String foglio){
        aggiungi(cella(contenuto, colonna, riga), foglio);
    }
    /**
     * Aggiungi una nuova cella al foglio da modificare.
     * @param cella
     * @param foglio
     */
    protected void aggiungi(WritableCell cella,String foglio){
        creaFoglio(foglio);//se non esiste in datiCelle
        datiCelle.get(foglio).add(cella);
    }
    
    /**
     * Modifica il contenuto di una riga.
     * @param nomeFoglio
     * @param riga
     * @param record
     * @return 
     */
    public boolean modificaCelle(String nomeFoglio, int riga, Object[] record) {
        if(!datiCelle.isEmpty()){
            ArrayList<WritableCell> foglio = datiCelle.get(nomeFoglio);
            if(foglio!= null){
                rimuoviRiga(riga, nomeFoglio);
                for(int colonna=0; colonna < record.length || colonna < numeroColonne(nomeFoglio); colonna++)
                    foglio.add(cella(record[colonna],colonna,riga));
                return true;
            }
        }
        return false;
    }

    
    
    /**
     * Immetti una formula nella cella del foglio da modificare.
     * 
     * @param formulaExcel     es.: "A2+B9-123"   
     * @param colonna          numero della colonna
     * @param riga             numero della riga
     * @param foglio           nome foglio
     */
    public void aggiungiFormulaCella(String formulaExcel,int colonna,int riga, String foglio){
        creaFoglio(foglio);//se non esiste in datiCelle
        Formula formula= new Formula(colonna,riga,formulaExcel);
        datiCelle.get(foglio).add(formula);
    }
   
    /**
     * Formatta cella in scrittura in base ai valori predefiniti.
     * 
     * @param formato
     * @throws WriteException 
     */
    private void formattaCella(WritableCellFormat formato) throws WriteException{
        formato.setBorder(bordatura, lineeBordo, coloreBordo);
        if(coloreSfondo!=Colour.UNKNOWN)
            formato.setBackground(coloreSfondo);
    }
    /**
     * Crea una cella da scrivere nel file alla fine del processo.
     * 
     * @param contenuto     tipo di dato (es. Double, Stringa, DataOraria ...)
     * @param colonna
     * @param riga
     * @return 
     */
    public WritableCell cella(Object contenuto,int colonna,int riga){
        try{
            if(contenuto == null){
                WritableCellFormat formatoCella = new WritableCellFormat(formatoCarattere);
                formattaCella(formatoCella);
                return new Label(colonna, riga, "", formatoCella);
                
            }else if(contenuto instanceof String){
                WritableCellFormat formatoCella = new WritableCellFormat(formatoCarattere);
                formattaCella(formatoCella);
                return new Label(colonna, riga, (String)contenuto, formatoCella);               
                
            }else if(contenuto instanceof Integer){
                WritableCellFormat integerFormat = new WritableCellFormat (formatoCarattere,NumberFormats.INTEGER);
                formattaCella(integerFormat);
                return new jxl.write.Number(colonna, riga, (Integer)contenuto, integerFormat);
                
            }else if(contenuto instanceof Double){
                WritableCellFormat doubleFormat = new WritableCellFormat (formatoCarattere,NumberFormats.FLOAT);
                formattaCella(doubleFormat);
                return new jxl.write.Number(colonna, riga, (Double)contenuto, doubleFormat);
                
            }else if(contenuto instanceof DataOraria){
                DateFormat customDateFormat = new DateFormat ("dd/MM/yyyy");
                WritableCellFormat dateFormat = new WritableCellFormat (formatoCarattere,customDateFormat);
                formattaCella(dateFormat);
                return  new DateTime(0, 6, ((DataOraria)contenuto).convertiDate(), dateFormat);
                

            }else if(contenuto instanceof Boolean){
                WritableCellFormat boolFormat = new WritableCellFormat (formatoCarattere);
                formattaCella(boolFormat);
                return new Label(colonna, riga, ((Boolean)contenuto)  ? "Sì" : "No", boolFormat);
                

            }else{
                WritableCellFormat formatoCella = new WritableCellFormat(formatoCarattere);
                formattaCella(formatoCella);
                return new Label(colonna, riga, contenuto.toString(), formatoCella);               
                
            } 
        } catch (WriteException ex) {
            Logger.getLogger(FileExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Se non esiste la directory, ne screa una....
     * 
     * @throws IOException      errore nella creazione forzata della directory
     */
    private void forzaCreazioneFile() throws IOException{
        Path path = Paths.get(file.toURI());
        
        String nomeFile = path.getFileName().toString();
        String directory = path.getParent().toString();
        try {
            Files.createDirectory(Paths.get(directory));
        } catch(FileAlreadyExistsException e){
            //  la directory esiste
        }
    }
    
    /**
     * Crea o sovrascrive il file.
     * 
     * @param nomeFile
     * @return 
     */
    private boolean creaFile(File nomeFile){
        WritableWorkbook scriviFile;
        try {
            forzaCreazioneFile();
            scriviFile = Workbook.createWorkbook(nomeFile,configurazione());
            if(scriviFile != null){
                int i=0;
                for(String nomeFoglio : datiCelle.keySet()){
                    WritableSheet foglio = scriviFile.createSheet(nomeFoglio, i++);
                   
                    if(foglio != null){
                        if(celleUnite != null)
                            if(!celleUnite.isEmpty()){
                                Range[] celle = celleUnite.get(nomeFoglio);
                                if(celle != null)
                                    for(Range cellaUnita: celle){
                                        foglio.mergeCells(
                                                cellaUnita.getTopLeft().getColumn(),cellaUnita.getTopLeft().getRow(),
                                                cellaUnita.getBottomRight().getColumn(),cellaUnita.getBottomRight().getRow()
                                        );
                                    }
                            }
                        if(datiCelle != null)
                            if(!datiCelle.isEmpty()){
                                ArrayList<WritableCell> celle = datiCelle.get(nomeFoglio);
                                if(celle != null)
                                    for(WritableCell cella : celle){
                                        foglio.addCell(cella); // (*)
                                    }
                            }
                        
                        int id =0;
                        if(configurazioneColonne != null)
                            if(!configurazioneColonne.isEmpty()){
                                ArrayList<CellView> celle = configurazioneColonne.get(nomeFoglio);
                                if(celle != null)
                                    for(CellView colonna : celle){
                                        foglio.setColumnView(id++, colonna);
                                    }
                            }
                        
                        id =0;
                        if(configurazioneRighe != null)
                            if(!configurazioneRighe.isEmpty()){
                                ArrayList<CellView> celle = configurazioneRighe.get(nomeFoglio);
                                if(celle != null)
                                    for(CellView riga : celle)
                                        foglio.setRowView(id++, riga);
                            }
                        
                        
                    }else{
                        scriviFile.close();
                        return false;
                    }
                    
                }
                
                scriviFile.write();
                scriviFile.close(); // (**)
                return true;
            }
        } catch (IOException ex) {
            Logger.getLogger(FileExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (WriteException ex) {
            // errore conversione da Cell a WritableCell (*)   (**)
            Logger.getLogger(FileExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    /**
     * Imposta la dimensione di una colonna di un file già creato...
     * 
     * @param dimensione
     * @param colonna
     * @param nomeFoglio
     * @return 
     */
    public boolean dimensionaColonna(int dimensione, int colonna,String nomeFoglio){
        ArrayList<CellView> colonne = configurazioneColonne.get(nomeFoglio);
        if(colonne != null){
            if(colonna >= 0 && colonna < colonne.size()){
                CellView elemento = new CellView();
                elemento.setSize(dimensione);
                colonne.set(colonna, elemento);
                return true;
            }
        }
        return false;
    }
    /**
     * Imposta la dimensione di una riga di un file già creato...
     * @param dimensione
     * @param riga
     * @param nomeFoglio
     * @return 
     */
    public boolean dimensionaRiga(int dimensione, int riga,String nomeFoglio){
        ArrayList<CellView> righe = configurazioneRighe.get(nomeFoglio);
        if(righe != null){
            if(riga >= 0 && riga < righe.size()){
                CellView elemento = new CellView();
                elemento.setSize(dimensione);
                righe.set(riga, elemento);
                return true;
            }
        }
        return false;
    }
    
    
    /**
     * Crea un nuovo file con le modifiche apportate che sostituisce il precedente.
     * 
     * @return 
     */
    public boolean applicaModifiche(){
        return creaFile(file);
    }
    /**
     * Fine uso dei metodi della classe.
     */
    public void chiudi(){
        if(accediFile != null){
            accediFile.close();
        }
    }
    
    /**
     * Elimina la riga dopo la modifica....
     * @param riga
     * @param foglio 
     */
    public void rimuoviRiga(int riga, String foglio) {
        if(configurazioneRighe!= null)
            if(configurazioneRighe.size() >0)
                configurazioneRighe.get(foglio).remove(riga);
        if(datiCelle != null)
            if(!datiCelle.isEmpty()){
                ArrayList<WritableCell> celle = datiCelle.get(foglio);
                ArrayList<WritableCell> nuoveCelle = new ArrayList<WritableCell>();
                CellFormat format = null;
                if(celle != null)
                    for(WritableCell cella:celle){
                        if(cella.getRow() < riga){
                            nuoveCelle.add(cella);
                            format = cella.getCellFormat();
                        }else if(cella.getRow() > riga){
                            WritableCell nuova = this.cella(cella.getContents(),cella.getColumn(),cella.getRow()-1);
                            nuova.setCellFormat(format);
                            nuoveCelle.add(nuova);
                        }
                    }
                datiCelle.put(foglio, nuoveCelle);
            }
        
                
    }
    
    
    public ArrayList<String> listaFogli() {
        ArrayList<String> lista = new ArrayList<String>();
        if(accediFile != null){
            for(Sheet foglio : accediFile.getSheets()){
                lista.add(foglio.getName());
            }
        }
        return lista;
    }

    /**
     * Elimina il foglio dai dati caricati. La modifica deve essere approvata.
     * @param nomeFoglio
     * @return 
     */
    public boolean eliminaFoglio(String nomeFoglio) {
        if(datiCelle.get(nomeFoglio) != null){
            datiCelle.remove(nomeFoglio);
            return true;
        }
        return false;
    }

    
    /**
     * Permette un accesso immediato a tutte le celle del foglio direttamente dal file.
     * @param nomeFoglio
     * @return 
     */
    public String[][] leggiCelleDalFile(String nomeFoglio){
        if(accediFile != null){
            Sheet foglio = accediFile.getSheet(nomeFoglio);
            if(foglio != null){
                final int nr_righe = foglio.getRows();
                final int nr_colonne = foglio.getColumns();
                 String[][] tabella = new String[nr_righe][nr_colonne];
                for(int riga = 0; riga < nr_righe; riga++){
                    for(int colonna = 0; colonna < nr_colonne; colonna++){
                        Cell cella = foglio.getCell(colonna, riga);
                        if(cella != null){
                            String valore = cella.getContents();
                            if(valore != null)
                                tabella[riga][colonna] = valore;
                            else
                                tabella[riga][colonna] = "";
                            
                        }
                    }
                }
                return tabella;
            }
            
        }
        return null;
    }
    
}

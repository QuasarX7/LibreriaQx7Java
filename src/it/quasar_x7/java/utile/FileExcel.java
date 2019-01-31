package it.quasar_x7.java.utile;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.*;
import jxl.format.Colour;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Number; 
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableFont.FontName;

import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/*******************************************************************************
 *
 * @author Domenico della Peruta
 * @deprecated 
 * @see Vedi nuova classe it.quasar_x7.java.excel.FileExcel
 ******************************************************************************/


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
    
        
    private WritableWorkbook fogli;
    private int idFoglio=0;
    private WritableFont formatoCarattere;
    private BorderLineStyle lineeBordo;
    private Colour coloreBordo,coloreSfondo,coloreTesto;
    private Border bordatura;
    
    /**********************************************************
     * Costruttore 
     * 
     * @param inputFile nome del file excel da creare 
     * @throws IOException 
     **********************************************************/
    public FileExcel(String inputFile) throws IOException {
        File file = new File(inputFile);
        
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("it", "IT"));
        
        fogli = Workbook.createWorkbook(file, wbSettings);
        
       
    }
    
    public FileExcel(File file) throws IOException {        
       
        WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("it", "IT"));

            fogli = Workbook.createWorkbook(file, wbSettings);
        
    }
    
    
    public void creaFoglo(String nome) {
        fogli.createSheet(nome, idFoglio++);  
        //inizializzazione
        try {
            this.coloraSfondo(Colore.NESSUNO);
            this.carattere(Carattere.ARIAL, Stile.NORMALE, 10,Colore.NERO);  
            this.bordatura(Bordo.PRIVO,Linea.SOTTILE, Colore.NERO);
            
        } catch (WriteException ex) {
            Logger.getLogger(FileExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
    
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
        if(stile == Stile.NORMALE){
            formatoCarattere= new WritableFont(font, dimensione, WritableFont.NO_BOLD,false);
            
        }else  if(stile == Stile.GROSSETTO){
            formatoCarattere = new WritableFont(font, dimensione, WritableFont.BOLD,false);
        
        }else if(stile == Stile.CORSIVO){
            formatoCarattere= new WritableFont(font, dimensione, WritableFont.NO_BOLD,true);
            
        }else  if(stile == Stile.GROSSETTO_CORSIVO){
            formatoCarattere = new WritableFont(font, dimensione, WritableFont.BOLD,true);
        }             
        formatoCarattere.setColour(coloreTesto);
        
    }
    
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
    
    public void dimensionaColonna(int colonna,int lunghezza) throws RowsExceededException, WriteException{
        if(fogli.getNumberOfSheets()>0){
            WritableSheet foglio = fogli.getSheet(idFoglio-1);
            foglio.setColumnView(colonna, lunghezza);
        }        
    }
    
    public void dimensionaRigha(int riga,double multiploAltezza) throws RowsExceededException, WriteException{
        if(fogli.getNumberOfSheets()>0){
            WritableSheet foglio = fogli.getSheet(idFoglio-1);
            foglio.setRowView(riga, ((int)Math.ceil(multiploAltezza))*foglio.getRowView(riga).getSize());
        }        
    }
    
    public void aggiungiCella(Object contenuto,int x,int y) throws  RowsExceededException, WriteException{
                 
        if(fogli.getNumberOfSheets()>0){
            WritableSheet foglio = fogli.getSheet(idFoglio-1);
       
            if(contenuto instanceof String){
                WritableCellFormat formatoCella = new WritableCellFormat(formatoCarattere);
                formatoCella.setWrap(true);
                formatoCella.setBorder(bordatura, lineeBordo, coloreBordo);
                
                if(coloreSfondo!=Colour.UNKNOWN)
                    formatoCella.setBackground(coloreSfondo);
                
                Label label = new Label(x, y, (String)contenuto, formatoCella);               
                foglio.addCell(label);
            }else if(contenuto instanceof Integer){
                WritableCellFormat integerFormat = new WritableCellFormat (formatoCarattere,NumberFormats.INTEGER);
                integerFormat.setBorder(bordatura, lineeBordo, coloreBordo);
                if(coloreSfondo!=Colour.UNKNOWN)
                    integerFormat.setBackground(coloreSfondo);
                Number n = new Number(x, y, (Integer)contenuto, integerFormat);
                foglio.addCell(n);
            }else if(contenuto instanceof Double){
                WritableCellFormat doubleFormat = new WritableCellFormat (formatoCarattere,NumberFormats.FLOAT);
                doubleFormat.setBorder(bordatura, lineeBordo, coloreBordo);
                if(coloreSfondo!=Colour.UNKNOWN)
                    doubleFormat.setBackground(coloreSfondo);
                Number n = new Number(x, y, (Double)contenuto, doubleFormat);
                foglio.addCell(n);
            }else if(contenuto instanceof DataOraria){
                DateFormat customDateFormat = new DateFormat ("dd/MM/yyyy");
                WritableCellFormat dateFormat = new WritableCellFormat (formatoCarattere,customDateFormat);
                dateFormat.setBorder(bordatura, lineeBordo, coloreBordo);
                if(coloreSfondo!=Colour.UNKNOWN)
                    dateFormat.setBackground(coloreSfondo);
                DateTime data = new DateTime(0, 6, ((DataOraria)contenuto).convertiDate(), dateFormat);
                foglio.addCell(data); 
            
            } else{
                WritableCellFormat formatoCella = new WritableCellFormat(formatoCarattere);
                formatoCella.setWrap(true);
                formatoCella.setBorder(bordatura, lineeBordo, coloreBordo);
                
                if(coloreSfondo!=Colour.UNKNOWN)
                    formatoCella.setBackground(coloreSfondo);
                
                Label label = new Label(x, y, "", formatoCella);               
                foglio.addCell(label);
            } 
            
            
        }            
    }
    
    public void aggiungiSempliceTabella(Tabella tab,int x, int y,Carattere carattere,int dimTesto) throws RowsExceededException, WriteException{
        for(int i=0;i<tab.colonne();i++){
            this.bordatura(Bordo.TUTTO, Linea.DOPPIA, Colore.NERO);
            this.carattere(carattere, Stile.GROSSETTO, dimTesto,Colore.NERO);
            this.aggiungiCella(tab.colonna(i), x+i, y);//stampa intestazione
            this.bordatura(Bordo.TUTTO, Linea.SOTTILE, Colore.NERO);
            this.carattere(carattere, Stile.NORMALE, dimTesto,Colore.NERO);
            for(int j=0;j<tab.righe();j++){
                this.carattere(carattere, Stile.NORMALE, dimTesto,Colore.NERO);
                Object v = tab.get(i, j);
                if(v!=null)
                    this.aggiungiCella(v.toString(), x+i, y+j+1);
                else
                    this.aggiungiCella("", x+i, y+j+1);
                
            }
        }
    }
    
    public void aggiungiTabella(Tabella tab,int x, int y,Carattere carattere,int dimTesto) throws RowsExceededException, WriteException{
        for(int i=0;i<tab.colonne();i++){
            this.bordatura(Bordo.TUTTO, Linea.DOPPIA, Colore.NERO);
            this.carattere(carattere, Stile.GROSSETTO, dimTesto,Colore.NERO);
            this.aggiungiCella(tab.colonna(i), x+i+1, y);//stampa intestazione
            this.bordatura(Bordo.TUTTO, Linea.SOTTILE, Colore.NERO);
            this.carattere(carattere, Stile.NORMALE, dimTesto,Colore.NERO);
            for(int j=0;j<tab.righe();j++){         
                this.carattere(carattere, Stile.GROSSETTO, dimTesto,Colore.NERO);
                this.bordatura(Bordo.TUTTO, Linea.DOPPIA, Colore.NERO);
                this.aggiungiCella(tab.riga(j), x, y+j+1);
                this.bordatura(Bordo.TUTTO, Linea.SOTTILE, Colore.NERO);
                this.carattere(carattere, Stile.NORMALE, dimTesto,Colore.NERO);
                Object v = tab.get(i, j);
                if(v!=null)
                    this.aggiungiCella(v.toString(), x+i+1, y+j+1);
                else
                    this.aggiungiCella("", x+i+1, y+j+1);
                
            }
        }
    }
    
    public void chiudi() throws IOException, WriteException{
        fogli.write();
        fogli.close();
    }
    
    
}

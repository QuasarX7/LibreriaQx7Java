package it.quasar_x7.java.utile;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/*******************************************************************************
 *
 * @author Domenico della Peruta
 ******************************************************************************/
public class FilePDF {
	
	
    
    //TIPO CARATTERI
    public static final String COURIER = FontFactory.COURIER;
    public static final String COURIER_GROSSETTO = FontFactory.COURIER_BOLD;
    public static final String COURIER_OBLIQUO = FontFactory.COURIER_OBLIQUE;
    public static final String COURIER_GROSSETTO_OBLIQUO = FontFactory.COURIER_BOLDOBLIQUE;
    public static final String HELVETICA = FontFactory.HELVETICA;
    public static final String HELVETICA_GROSSETTO = FontFactory.HELVETICA_BOLD;
    public static final String HELVETICA_OBLIQUO = FontFactory.HELVETICA_OBLIQUE;
    public static final String HELVETICA_GROSSETTO_OBLIQUE = FontFactory.HELVETICA_BOLDOBLIQUE;
    public static final String SYMBOL = FontFactory.SYMBOL;
    public static final String TIMES = FontFactory.TIMES;
    public static final String TIMES_ROMAN = FontFactory.TIMES_ROMAN;
    public static final String TIMES_GROSSETTO = FontFactory.TIMES_BOLD;
    public static final String TIMES_CORSIVO = FontFactory.TIMES_ITALIC;
    public static final String TIMES_GROSSETTO_CORSIVO = FontFactory.TIMES_BOLDITALIC;
    
    //STILE
    public static final int GROSSETTO = Font.BOLD;
    public static final int GROSSETTO_CORSIVO = Font.BOLDITALIC;
    public static final int CORSIVO = Font.ITALIC;
    public static final int NORMALE = Font.NORMAL;
    public static final int BARRATO = Font.STRIKETHRU;
    public static final int SOTTOLINEATO = Font.UNDERLINE;
    
    //ALLINEAMENTO
    public static final int ALLINEAMENTO_CENTRO = Element.ALIGN_CENTER;
    public static final int ALLINEAMENTO_DESTRA = Element.ALIGN_RIGHT;
    public static final int ALLINEAMENTO_SINISTRA = Element.ALIGN_LEFT;
    
    //COLORI
    public static final BaseColor BIANCO = BaseColor.WHITE;
    public static final BaseColor GRIGIO_CHIARO= BaseColor.LIGHT_GRAY;
    public static final BaseColor GRIGIO = BaseColor.GRAY;
    public static final BaseColor GRIGIO_SCURO = BaseColor.DARK_GRAY;
    public static final BaseColor NERO = BaseColor.BLACK;
    public static final BaseColor ROSSO = BaseColor.RED;
    public static final BaseColor ROSA = BaseColor.PINK;
    public static final BaseColor ARANCIONE = BaseColor.ORANGE;
    public static final BaseColor GIALLO = BaseColor.YELLOW;
    public static final BaseColor VERDE = BaseColor.GREEN;
    public static final BaseColor MAGENTA = BaseColor.MAGENTA;
    public static final BaseColor CIANO = BaseColor.CYAN;
    public static final BaseColor BLU = BaseColor.BLUE;
    
    public enum Orientamento{
        VERTICALE,ORIZZONTALE
    };
    
    private Document d;

    public FilePDF(String file){
        
        try {
            d = new Document(PageSize.A4);
            PdfWriter.getInstance(d, new FileOutputStream(file));
            d.open();

        } catch (FileNotFoundException ex) {
            System.out.println("errore scrittura file pdf");
        } catch (DocumentException ex) {
            System.out.println("errore istanziamento del documento PDF...");
            ex.printStackTrace();
        }

    }
    
    public FilePDF(String file,Orientamento pagina){
        try {
            if(pagina == Orientamento.VERTICALE){
                d = new Document(PageSize.A4);
                PdfWriter.getInstance(d, new FileOutputStream(file));
                d.open();
            }else{
               d = new Document(PageSize.A4.rotate());
                PdfWriter.getInstance(d, new FileOutputStream(file));
                d.open(); 
            }
            
        } catch (FileNotFoundException ex) {
            System.out.println("errore scrittura file pdf");
        } catch (DocumentException ex) {
            System.out.println("errore istanziamento del documento PDF...");
            ex.printStackTrace();
        }
    } 

        
    /**************************************************
     * 
     * @param testo
     * @param tipoCaratteri  es.: FilePDF.COURIER
     * @param grandezza 
     * @param stile          es.: FilePDF.CORSIVO
     * @param allineamento   es.: FilePDF.ALLINEATO_CENTRO
     * @param colore         es.: FilePDF.NERO 
     **************************************************/
    public void aggiungi(String testo,String tipoCaratteri, int grandezza,int stile,int allineamento, BaseColor colore){
         try {
            Paragraph p = new Paragraph(testo,FontFactory.getFont(tipoCaratteri,  grandezza, stile, colore));
            p.setAlignment(allineamento);
            d.add(p);
        } catch (DocumentException ex) {
            System.out.println("errore aggiunta stringa a documento PDF");
            ex.printStackTrace();
        }
    }
    
    
    
    public void aggiungiTabella( ArrayList<String> testo,float[] colonne,String tipoCaratteri, int grandezza,int stile,boolean bordi){
        try {
            if(colonne != null && testo != null){
                PdfPTable tabella = new PdfPTable(colonne.length);
                tabella.setWidthPercentage(100f);
                tabella.setTotalWidth(colonne); 
                if(!bordi)
                    tabella.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                for(String testoCella: testo){
                    Paragraph p = new Paragraph(
                            testoCella,
                            FontFactory.getFont(tipoCaratteri,  grandezza, stile, BaseColor.BLACK)
                    );
                    PdfPCell cella = new PdfPCell(p);
                    //cella.setColspan(2);
                    cella.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cella.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    if(!bordi)cella.setBorder(PdfPCell.NO_BORDER);
                    tabella.addCell(cella);
                }
                tabella.completeRow();
                tabella.setHorizontalAlignment(ALLINEAMENTO_SINISTRA);
         
                
                d.add(tabella);
            }
        } catch (DocumentException ex) {
            Logger.getLogger(FilePDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Aggiungi una tabella personalizzata per celle.
     * 
     * @param testo
     * @param colonne
     */
    public void aggiungiTabella( ArrayList<CellaPDF> testo,float[] colonne){
        try {
            if(colonne != null && testo != null){
                PdfPTable tabella = new PdfPTable(colonne.length);
                tabella.setWidthPercentage(100f);
                tabella.setTotalWidth(colonne); 
                
                for(CellaPDF cella: testo){
                   tabella.addCell(cella.crea());
                }
                tabella.completeRow();
                tabella.setHorizontalAlignment(ALLINEAMENTO_SINISTRA);
                
                d.add(tabella);
            }
        } catch (DocumentException ex) {
            Logger.getLogger(FilePDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void aggiungi(String file_immagine){
        try {

            Image immagine = Image.getInstance(file_immagine);
            d.add(immagine);


        } catch (BadElementException ex) {
            System.out.println("errore aggiunta immagine a documento PDF");
            ex.printStackTrace();
        } catch (MalformedURLException ex) {
            System.out.println("errore aggiunta immagine a documento PDF");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("errore aggiunta immagine a documento PDF");
            ex.printStackTrace();
        } catch (DocumentException ex) {
            System.out.println("errore aggiunta immagine a documento PDF");
            ex.printStackTrace();
        }
    }

    public void chiudi(){
        d.close();
    }

    public void aggiungi(JPanel sfondo) {
        try {
            d.add((Element) sfondo);
        } catch (DocumentException ex) {
            System.out.println("errore aggiunta pannello a documento PDF");
            ex.printStackTrace();
        }
    }

	

}

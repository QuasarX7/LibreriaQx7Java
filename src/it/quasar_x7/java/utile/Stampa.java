package it.quasar_x7.java.utile;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.PrintJob;
import java.awt.Toolkit;
import java.util.Properties;

/*******************************************************************************
 *Classe che permette di stampare un Report creato a partire da un pannello o
 * frame.
 *
 * @author Domenico della Peruta
 * @version 1.0.1 ultima modifica 02.01.2010
 ******************************************************************************/
public class Stampa {

    static public final int VERTICALE   = 1001;
    static public final int ORIZZONTALE = 1002;
    static public final int A4      = 2001;
    static public final int LETTERA = 2002;
    
//---------------------  Proprietà ---------------------------------------------
    private Component[] report;
    private Properties proprietà;//della stampante
    private int copie = 1;
    private int formato, orientamento;
    
 //--------------------- Costruttore -------------------------------------------
    /*************************************************
     * Costruttore con ingresso l'elemento da stampare
     *
     * @param pagina
     *************************************************/
    public Stampa(Component pagina){
        this.report= new Component[]{pagina};
        proprietà = new Properties();
    }

    /*************************************************
     * Costruttore con ingresso più elementi da stampare
     * 
     * @param pagine
     ************************************************/
    public Stampa(Component[] pagine){
        this.report= pagine;
        proprietà = new Properties();
    }
//----------------------- metodi -----------------------------------------------
    public void formato(int formato){
        this.formato=formato;
    }
    public void orientamento(int orientamento){
        this.orientamento = orientamento;
    }
    public void copie(int n){
        copie = n;
    }
    /************************************************
     * Attiva la finestra Diaologo della stampante
     *
     * @param finestraOrigine Frame da associare
     * all'apertura
     ************************************************/
    public void attivaStampante(Frame finestraOrigine){
      /*  La classe Toolkit ci permette di accedere a varie caratteristiche 
          grafiche e non grafiche, del sistema su cui ci troviamo             */
           Toolkit tk= Toolkit.getDefaultToolkit();  // Acquisiamo il toolkit
         PrintJob pj= tk.getPrintJob(finestraOrigine,"stampa report java",proprietà);
         if(formato == A4){
             proprietà.put("awt.print.paperSize","a4");
         }else if(formato == LETTERA){
             proprietà.put("awt.print.paperSize","letter");
         }
         proprietà.put("awt.print.numCopies", new Integer(copie).toString() );
         
         if(orientamento == VERTICALE){
             proprietà.put( "awt.print.orientation", "portrait" );
         }else if(orientamento == ORIZZONTALE){
             proprietà.put( "awt.print.orientation", "landscape" );
         }

         if(pj!=null){
             for(int i=0;i<report.length;i++){
                 Graphics g =  pj.getGraphics(); //acquisisce pagina/e
           
                 if (g!=null){
                     report[i].printAll(g);
                 }                 
             }
             pj.end();
         }
    }

}
////////////////////////////////////////////////////////////////////////////////
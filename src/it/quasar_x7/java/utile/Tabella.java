package it.quasar_x7.java.utile;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import jxl.format.BoldStyle;
import jxl.write.WritableFont.FontName;

/**
 *
 * @author Domenico della Peruta
 */
public class Tabella {

    private String nome;
    private String[] colonne;
    private String[] righe;
    private Object[][] tabella;

    public Tabella(String nome,String[]colonne,String[]righe) {
        this.nome=nome;
        this.colonne=colonne;
        this.righe=righe;

        this.tabella = new Object[colonne.length][righe.length];

    }
    
    public Tabella(String nome,String[]colonne,String[]righe,Object[][] tabella) {
        this.nome=nome;
        this.colonne=colonne;
        this.righe=righe;
        this.tabella = tabella;

    }
    
     
    public boolean set(int x,int y,Object dato){
        if(x<colonne.length && y<righe.length && x>=0 && y>=0){
            this.tabella[x][y]=dato;
            return true;
        }
        return false;
    }

    public boolean set(String colonna,String riga,Object dato){
        int x = this.colonna(colonna);
        int y = this.riga(riga);
        return this.set(x, y, dato);
    }

    public Object get(int x,int y){
        if(x<colonne.length && y<righe.length && x>=0 && y>=0){
            return this.tabella[x][y];
        }
        return null;
    }

    public Object get(String colonna,String riga){
        int x =this.colonna(colonna);
        int y = this.riga(riga);
        return this.get(x, y);
    }

    private int colonna(String colonna){
        for(int i=0;i<this.colonne.length;i++){
            if(this.colonne[i].compareTo(colonna)==0){
                return i;
            }
        }
        return -1;
    }

    private int riga(String riga){
        for(int i=0;i<this.righe.length;i++){
            if(this.righe[i].compareTo(riga)==0){
                return i;
            }
        }
        return -1;
    }

    public String colonna(int x){
        if(x>=0 && x< this.colonne.length)
            return this.colonne[x];
        return null;
    }

    public String riga(int y){
        if(y>=0 && y< this.righe.length)
            return this.righe[y];
        return null;
    }


    public String getNome() {
        return this.nome;
    }

    /***************************************************
     * Confronta due righe per constatare l'eventuale
     * uguaglianza.
     * 
     * @param riga1
     * @param riga2
     * @return
     ***************************************************/
    public boolean ugualiRoghe(int riga1,int riga2){
        if(riga1 >=0 && riga1 <righe.length && riga2 >=0 && riga2 <righe.length){
            for(int i=0;i<this.colonne.length;i++){
                if(!tabella[i][riga1].equals(tabella[i][riga2])){
                    return false;
                }
            }
            return true;
        }
        System.out.println("errore tabella: impossiblie comparare le righe "
                + "perché non presenti nella tabella");
        return false;
    }

    /*********************************************************
     * Confronta due colonne per constatare l'eventuale
     * uguaglianza.
     * 
     * @param colonna1
     * @param colonna2
     * @return 
     ********************************************************/
    public boolean ugualiColonne(int colonna1,int colonna2){
        if(colonna1 >=0 && colonna1 <colonne.length && colonna2 >=0 && colonna2 <colonne.length){
            for(int i=0;i<this.righe.length;i++){
                if(!tabella[colonna1][i].equals(tabella[colonna2][i])){
                    return false;
                }
            }
            return true;
        }
        System.out.println("errore tabella: impossiblie comparare le colonne "
                + "perché non presenti nella tabella");
        return false;
    }

    @Override
    public String toString() {
        String s ="";
        s += "             "+new Testo(this.colonne,10).getTesto()+"\n";
        for(int y=0;y < this.righe.length; y++){
            String[] v_col = new String[this.colonne.length+1];
            v_col[0] = this.riga(y);
            for(int x=0;x < this.colonne.length; x++){               
                Object e = this.tabella[x][y];
                if(e != null)
                    v_col[x+1] = e.toString();
                else{
                    v_col[x+1] = null;
                }
            }
            s += new Testo(v_col,10).getTesto();
            s+="\n";
        }
        return s;
    }

    public int colonne() {
        return this.colonne.length;
    }
    
    public int righe(){
        return this.righe.length;
    }

    
    public JPanel disegna(int altezza,int larghezza,
            final Colore sfondo,final Colore coloreCaratteri,final Colore griglia,
            final String stileCarattere,final int dimCarattere, 
            final int[]dimColonne,final int dimRighe){
        
        JPanel pannello = new JPanel(){
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                
                if(tabella!=null){   
                     int x=0,y=0;                                        
                    for(int c=0; c< colonne.length;c++){
                        y=0;
                        //intestazione colonne                        
                        g.setColor(griglia);
                        g.fillRect(x, y, dimColonne[c]-2, dimRighe-2);
                        
                        y = dimRighe; 
                        g.setFont(new Font(stileCarattere, Font.BOLD, dimCarattere));
                        g.setColor(coloreCaratteri);
                        g.drawString(
                                colonne[c].toString(),
                                x+dimColonne[c]/10,
                                y-dimRighe/5);

                        for(int r=0; r< righe.length;r++){
                            g.setFont(new Font(stileCarattere, Font.PLAIN, dimCarattere));
                            y = dimRighe+ dimRighe*r;  
                            
                            g.setColor(sfondo);
                            g.fillRect(x, y, dimColonne[c], dimRighe);
                            
                            g.setColor(griglia);
                            g.drawRect(x, y, dimColonne[c]-2, dimRighe-2);
                            
                            g.setColor(coloreCaratteri);
                            if( tabella[c][r] != null)
                                g.drawString(
                                        tabella[c][r].toString(),
                                        x+dimColonne[c]/10,
                                        y+dimRighe-dimRighe/5);
                            
                        }
                        x += dimColonne[c]; 
                    }
                }
                
                
            }            
        };
        pannello.setSize(larghezza, altezza);
        pannello.setBackground(sfondo);
        
        
        
        return pannello;
    }
    
    public JPanel disegnaCompleta(int altezza,int larghezza,
            final Colore sfondo,final Colore coloreCaratteri,final Colore griglia,
            final String stileCarattere,final int dimCarattere, 
            final int[]dimColonne,final int dimRighe,final int dimEtichettaRighe){
        
        JPanel pannello = new JPanel(){
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                
                if(tabella!=null){   
                     int x=dimEtichettaRighe,y=0;                                        
                    for(int c=0; c< colonne.length;c++){
                        y=0;
                        //intestazione colonne                        
                        g.setColor(griglia);
                        g.fillRect(x, y, dimColonne[c]-2, dimRighe-2);                        
                        y = dimRighe; 
                        g.setFont(new Font(stileCarattere, Font.BOLD, dimCarattere));
                        g.setColor(coloreCaratteri);
                        g.drawString(
                                colonne[c].toString(),
                                x+dimColonne[c]/10,
                                y-dimRighe/5);

                        
                        for(int r=0; r< righe.length;r++){
                            y = dimRighe+ dimRighe*r;
                            //etichetta righa                            
                            if(c==0){
                                g.setFont(new Font(stileCarattere, Font.BOLD, dimCarattere));
                                
                                g.setColor(griglia);
                                g.fillRect(x-dimEtichettaRighe, y, dimEtichettaRighe, dimRighe-2);
                                
                                g.setColor(coloreCaratteri);
                                g.drawString(
                                            righe[r],
                                            x-dimEtichettaRighe,
                                            y+dimRighe-dimRighe/5);
                            }
                            
                            g.setFont(new Font(stileCarattere, Font.PLAIN, dimCarattere));                              
                            
                            g.setColor(sfondo);
                            g.fillRect(x, y, dimColonne[c], dimRighe);
                            
                            g.setColor(griglia);
                            g.drawRect(x, y, dimColonne[c]-2, dimRighe-2);
                            
                            g.setColor(coloreCaratteri);
                            if( tabella[c][r] != null)
                                g.drawString(
                                        tabella[c][r].toString(),
                                        x+dimColonne[c]/10,
                                        y+dimRighe-dimRighe/5);
                            
                        }
                        x += dimColonne[c]; 
                    }
                }
                
                
            }            
        };
        pannello.setSize(larghezza, altezza);
        pannello.setBackground(sfondo);
        
        
        
        return pannello;
    }
    
    public boolean ugualiValori(int x1, int y1, int x2, int y2) {
        if(x1 >=0 && x1 <colonne.length && x2 >=0 && x2 <colonne.length && 
                y1 >=0 && y1 <righe.length && y2 >=0 && y2 <righe.length){
                        
            if(!tabella[x1][y1].equals(tabella[x2][y2])){
                return false;               
            }
            return true;
        }
        System.out.println("errore tabella: controlla coordinate!!!");
        return false;
    }

}

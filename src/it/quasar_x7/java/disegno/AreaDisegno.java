package it.quasar_x7.java.disegno;

import it.quasar_x7.java.utile.Colore;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

/*******************************************************************************
 *
 * @author Domenico della Peruta
 ******************************************************************************/
public class AreaDisegno {
    
    private SistemaXY assiXY;
    private int x0,y0,xMax,yMax; //dimenzione foglio    
    private Colore sfondo;
    private boolean assi,reticolo;
    private ArrayList<Figura> elementi;
    private Graphics2D g;

    public AreaDisegno(Graphics g) {
        this.g = (Graphics2D) g;
        this.inizializzazione();
    }
    
    
    private void inizializzazione(){
        //coordinate foglio
        x0=(int) g.getClip().getBounds().getX();
        y0=(int) g.getClip().getBounds().getY();
        xMax= x0+(int) g.getClip().getBounds().getWidth();
        yMax= y0+(int) g.getClip().getBounds().getHeight();
        //sfondo bianco
        sfondo = Colore.BIANCO;
        //assi al centro
        assiXY = new SistemaXY(xMax/2,yMax/2);
                
        elementi= new ArrayList<Figura>();
        //non visualizza
        assi = reticolo = false;
    }
    
    public void sistemaDiRiferimento(SistemaXY assiXY){
        this.assiXY = assiXY;
    }
    
    public void aggiungi(Figura x){
        this.elementi.add(x);
    }
    
    public void svuota(){
        this.elementi.clear();
    }
    
    public void disegna(){
       this.disegnaSfondo();
       
       g.translate((
               (Coordinate2D)this.assiXY.centro()).getX(),
               ((Coordinate2D)this.assiXY.centro()).getY());
       
       g.rotate(this.assiXY.orientamento());
       
        for(Figura x: this.elementi){
            x.disegna(g);
        }
        
        disegnaAssi();
         
    }
    
    private void disegnaSfondo(){
        g.setColor(sfondo);        
        g.setClip(x0, y0, xMax-x0, yMax-y0);
        
    }    
    
    public Coordinate2D vertice(){
        return new Coordinate2D(x0,y0);
    }
    
    public int lunghezza(){
        return xMax - x0;
    }
    
    public int altezza(){
        return yMax-y0;
    }
    
    public void dimensiona(Component isThis){
        dimensiona(
                isThis.getX(),isThis.getY(),
                isThis.getX()+isThis.getWidth(),
                isThis.getY()+isThis.getHeight());
    }
    
    public void dimensiona(Coordinate2D vertice,int larghezza,int altezza){
        dimensiona(
                (int)vertice.getX(), (int)vertice.getY(),                 
                (int)vertice.getX() + larghezza,
                (int)vertice.getY() + altezza);
    }
    
    public void dimensiona(int x0, int y0,int xMax,int yMax){
        this.x0=x0;      this.y0=y0;
        this.yMax=yMax;  this.xMax=xMax;
    }

    public void assi(boolean assi) {
        this.assi = assi;
    }
    
    public void reticolo(boolean reticolo){
        this.reticolo = reticolo;
    }

    private void disegnaAssi() {
        Linea asseX = new Linea(x0,(y0+yMax)/2,xMax,(y0+yMax)/2,Colore.NERO,1);
        asseX.disegna(g);
    }

        
}

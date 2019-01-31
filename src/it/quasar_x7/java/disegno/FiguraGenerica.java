package it.quasar_x7.java.disegno;




/**
 *
 * @author ninja
 */
public abstract class FiguraGenerica implements Figura {
    
    protected Coordinate2D baricentro;
    
       
    
    public Coordinate baricentro() {
        return this.baricentro;
    }

    public void posiziona(Coordinate2D baricentro) {
        this.baricentro = baricentro;
    }

    public Orientamento vediOrientamento() {
        return null;
    }

    public void orienta(Orientamento2D orientamento) {
        //null
    }

    public void movimento(double velocita, Orientamento2D direzione) {
        double variazioneX = velocita*direzione.getX();
        double variazioneY = velocita*direzione.getY();
        baricentro.setX(baricentro.getX()+variazioneX);
        baricentro.setY(baricentro.getY()+variazioneY);
    }

    public void rotazione(double velocita, Coordinate2D centro) {
        double distanzaX= baricentro.getX()-centro.getX();
        double distanzaY= baricentro.getY()-centro.getY();
        double alfa,beta;
        if(distanzaX != 0) {  
            if(distanzaX>0){
                alfa = Math.atan(distanzaY/distanzaX);
            }else{
                alfa = Math.atan(distanzaY/distanzaX)+Math.PI;
            }
        }else{
            if(distanzaY>0)
                alfa = Math.PI/2;
            else
                alfa = -Math.PI/2;
        }
        double distanza = Math.sqrt(distanzaX*distanzaX+distanzaY*distanzaY);
        beta=alfa+velocita;        
        baricentro.setX(centro.getX()+distanza*Math.cos(beta));
        baricentro.setY(centro.getY()+distanza*Math.sin(beta));
    }

  
    
}

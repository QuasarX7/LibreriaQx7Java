package it.quasar_x7.java.utile;

/*******************************************************************************
 *
 * @author Domenico della Peruta
 ******************************************************************************/

public class Carattere {
    private char c;

    public Carattere(char c) {
        this.c = c;
    }
    
    public boolean numero(){
        return Character.isDigit(c);
    }
    
    public boolean lettera(){
        return Character.isLetter(c);
    }
    
    public boolean lettera_numero(){
        return Character.isLetterOrDigit(c);
    }
    
    public boolean letteraMaiuscola(){
        if(this.lettera()){
            if(c >= 'A' && c <= 'Z'){
                return true;
            }
        }
        return false;
    }

    public boolean letteraMinuscola() {
        if(this.lettera()){
            if(c >= 'a' && c <= 'z'){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return ""+c;
    }

    public boolean spazio() {
        return (c == ' ');
    }

    public boolean uguare(char c) {
        return (this.c == c);
    }
    
    
    
}

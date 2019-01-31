
package it.quasar_x7.java.matematica;

/**
 *
 * @author ninja
 */
public class Matrice {   
    private Object matrice[][];
    private int colonne,righe;

    public Matrice(int colonne,int righe) {
        this.colonne=colonne;
        this.righe=righe;
        matrice = new Object[colonne][righe];
    }

    public Matrice(Object[][] matrice) {
        this.matrice = matrice;
        colonne = matrice.length;
        righe = matrice[0].length;
    }
    
    public Object[][] inversa(){
        Object[][] inversa = new Object[righe][colonne];
        for(int i=0; i<colonne;i++){
            for(int j=0; j<righe;j++){
                inversa[j][i] = matrice[i][j];
            }
        }
        return inversa;
    }
}

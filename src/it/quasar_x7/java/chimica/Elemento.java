package it.quasar_x7.java.chimica;

import java.util.Arrays;

/*******************************************************************************
 *
 * @author Domenico della Peruta
 ******************************************************************************/
public class Elemento implements java.lang.Comparable{
      
    private String  nome;
    private String  simbolo;
    private int     numero_atomico;
    private int     periodo;
    private String  gruppo;
    private float   peso_atomico;
    private int[]   valenza;

    
    public Elemento(String simbolo,String nome, int numero_atomico, int periodo, String gruppo, float peso_atomico, int[] valenza) {
        this.nome = nome;
        this.numero_atomico = numero_atomico;
        this.periodo = periodo;
        this.gruppo = gruppo;
        this.peso_atomico = peso_atomico;
        this.valenza = valenza;
        this.simbolo=simbolo;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    
    public String getGruppo() {
        return gruppo;
    }

    public String getNome() {
        return nome;
    }

    public int getNumeroAtomico() {
        return numero_atomico;
    }

    public int getPeriodo() {
        return periodo;
    }

    public float getPesoAtomico() {
        return peso_atomico;
    }

    public int[] getValenza() {
        return valenza;
    }

   
    /*********************************************************
     * Compara i due elementi e restituisce la differenza in
     * peso atomico tra i due elementi
     * 
     * @param o
     * @return 
     *********************************************************/
    public int compareTo(Object o) {
        if(o instanceof Elemento){
            return this.numero_atomico - ((Elemento)o).numero_atomico;
        }
        return Integer.MIN_VALUE;
    }

   
    
}

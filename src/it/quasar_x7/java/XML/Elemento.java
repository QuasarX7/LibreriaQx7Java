package it.quasar_x7.java.XML;

import it.quasar_x7.java.utile.Testo;
import java.util.ArrayList;

/**
 *
 * @author Domenico della Peruta
 */
public class Elemento{
    String nome;
    String valore;
    ArrayList<Elemento> elementi;
    ArrayList<Attributo> attributi;

    public Elemento(String nome) {
        this.nome = correzione(nome);
        elementi=new ArrayList<Elemento>();
        attributi = new ArrayList<Attributo>();
    }

    public Attributo getAttributo(int i) {
        return attributi.get(i);
    }



    public void addAttributo(Attributo attributo) {
        this.attributi.add(attributo);
    }

    public Elemento getElemento(int i) {
        return elementi.get(i);
    }

    public void setElementi(ArrayList<Elemento> elementi) {
        this.elementi = elementi;
    }


    public int numeroElementi(){
        return this.elementi.size();
    }

    public int numeroAttributi(){
        return this.attributi.size();
    }

    public void addElemento(Elemento nodo) {
        this.elementi.add(nodo);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome =  correzione(nome);
    }

    public String getValore() {
        return valore;
    }

    public void setValore(String valore) {
        this.valore = valore;
    }

    void setAttributi(ArrayList<Attributo> attributi) {
        this.attributi = attributi;
    }

    private String correzione(String xml){
        Testo stringa = new Testo(xml);
        stringa.sostituisciStringa("&amp","&");
        stringa.sostituisciStringa("&lt","<");
        stringa.sostituisciStringa("&gt",">");
        stringa.sostituisciStringa("&quot","\"");
        stringa.sostituisciStringa( "&apos","'");
        return stringa.getTesto();
    }

}

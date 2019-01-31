package it.quasar_x7.java.XML;

import it.quasar_x7.java.utile.Testo;

/**
 *
 * @author Domenico della Peruta
 */
public class Attributo{
    String nome;
    String valore;


    public Attributo(String nome, String valore) {
        this.nome = correzione(nome);
        this.valore = correzione(valore);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = correzione(nome);
    }

    public String getValore() {
        return valore;
    }

    public void setValore(String valore) {
        this.valore = correzione(valore);
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

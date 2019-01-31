package it.quasar_x7.java.diagrammi;

import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JPanel;

/*******************************************************************************
 * Interfaccia di Diagrammi grafici, ottenuti da dati provenienti in modo 
 * dinamico da Basi di Dati, oppure in modo statico dal programma stesso.
 * L'interfaccia viene implementata utilizzando metodi e classi esterni come
 * JFreeChart (libreria open sorce)
 * 
 * @author Domenico della Peruta
 ******************************************************************************/
public interface Diagramma {

    /******************************************************
     * Metodo che permette di caricare i dati da utilizzare
     * nel diagramma, dinamicamente da una base di dati
     * MySQL.
     * 
     * @param host IP del computer dove è istallato MySQL
     * @param schema del DB MySQL contenente le tabelle
     * oggetto dell'interrogazione SQL
     * 
     * @param utente
     * @param password
     * @param sql Interrogazione semplice di SQL che permette
     * di generare semplici tabelle a due colonne VARCHAR e
     * NUMBER, o nel caso dei diagrammi XY, NUMBER e NUMBER
     * 
     * @throws ClassNotFoundException
     * @throws SQLException 
     *******************************************************/
    void caricaDatiMySQL(String host, String schema, String utente, String password, String sql) throws ClassNotFoundException, SQLException;

    void creaFinestra(int altezza, int larghezza);

    void creaImmaginePNG(String filePNG, int altezza, int larghezza) throws IOException;

    JPanel creaPannello();

    void titolo(String titolo);
    
    void svuota();
    
}

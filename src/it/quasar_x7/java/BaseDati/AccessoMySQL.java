package it.quasar_x7.java.BaseDati;

import it.quasar_x7.java.utile.DataOraria;
import it.quasar_x7.java.utile.Testo;
import java.sql.*;
import java.util.ArrayList;



/*******************************************************************************
 * Classe che classe che permette di accedere ad una Base di Dati MySQL
 *
 * @author Domenico della Peruta
 ******************************************************************************/
public class AccessoMySQL extends AccessoDB {

    private String utente,schema,password;
    private boolean ODBC;
 //--------------------------- costruttore -------------------------------------
      /**************************************************
     * Costruttore con accesso MySQL tramite connessione
     * ODBC o driver MySQL connector/J
     *
     * @param origineDati
     * @param schema
     * @param utente
     * @param password
     * @param ODBC
     **************************************************/
    public AccessoMySQL(String origineDati,String schema, String utente,
            String password,boolean ODBC){
        
        this.origineDati = origineDati;
        this.password=password;
        this.utente = utente;
        this.ODBC = ODBC;
        this.schema = schema;
    }
    
    
    /**************************************************
     * Effettua la connessione alla base dati Access
     * @throws it.quasar_x7.java.BaseDati.EccezioneBaseDati
     *************************************************/
    @Override
    public void connetti() throws EccezioneBaseDati {
        if (this.connessione != null) {
            return; //esce
        }
        if (ODBC) {
            connessioneODBC();
        } else {
            connessioneDriverConnectorJ();
        }
    }


    private void connessioneDriverConnectorJ() throws EccezioneBaseDati{
        try{
             Class.forName("com.mysql.jdbc.Driver");

            connessione = DriverManager.getConnection(
                    "jdbc:mysql://" + origineDati + ":3306/"+schema+"?user="
                    + utente + "&password=" + password);
        
        }catch(ClassNotFoundException e){            
            /*javax.swing.JOptionPane.showMessageDialog(
                    null,
                    "errore caricamento driver MySQL:\n"+e.getMessage(),
                    "errore",
                    javax.swing.JOptionPane.ERROR_MESSAGE);*/
            
            EccezioneBaseDati eccezione = 
                    new EccezioneBaseDati(
                    "connessione diver MySQL",e.getMessage());
            
            eccezione.setStackTrace(e.getStackTrace());
            throw eccezione;
        }
        catch(SQLException e){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
            }
            
            /*javax.swing.JOptionPane.showMessageDialog(
                    null,
                    "ERRORE DI CONNESSIONE alla base di dati \""
                    +origineDati+"\"....\n"+
                    e.getMessage(),
                    "errore",
                    javax.swing.JOptionPane.ERROR_MESSAGE);*/
            
            EccezioneBaseDati eccezione =
                    new EccezioneBaseDati(
                    "connessione server MySQL "+origineDati,
                    e.getMessage());
            
            eccezione.setStackTrace(e.getStackTrace());
            throw eccezione;
            
        }
    }

    private void connessioneODBC() throws EccezioneBaseDati{
        try{
            //accesso ODBC
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

             connessione= DriverManager.getConnection(
                "jdbc:odbc:"+origineDati,utente,password);
        }catch(ClassNotFoundException e){
            javax.swing.JOptionPane.showMessageDialog(
                    null,
                    "errore caricamento driver JDBC-ODBC :\n"+e.getMessage(),
                    "errore",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            
            EccezioneBaseDati eccezione = new EccezioneBaseDati("connessione driver JDBC-ODBC",e.getMessage());
            eccezione.setStackTrace(e.getStackTrace());
            throw eccezione;
            
        }catch(SQLException e){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
            }
            
            javax.swing.JOptionPane.showMessageDialog(
                    null,
                    "ERRORE DI CONNESSIONE alla base di dati \""
                    +origineDati+"\"....\n"+
                    e.getMessage(),
                    "errore",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            
            EccezioneBaseDati eccezione = new EccezioneBaseDati("connessione ODBC "+origineDati,e.getMessage());
            eccezione.setStackTrace(e.getStackTrace());
            throw eccezione;
            
        }
    }

}
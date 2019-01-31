package it.quasar_x7.java.BaseDati;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Dr. Domenico della Peruta
 */
public class AccessoSQLite extends AccessoDB{

    public AccessoSQLite(String file) {
        this.origineDati = file;
    }

    @Override
    public void connetti() throws EccezioneBaseDati {
        if (this.connessione != null) {
            return; //esce
        }
        try {
            Class.forName("org.sqlite.JDBC");
            connessione =DriverManager.getConnection(String.format("jdbc:sqlite:%s",origineDati));
        } catch (ClassNotFoundException ex) {
            EccezioneBaseDati eccezione = new EccezioneBaseDati("connessione diver SQLite",ex.getMessage());
            throw eccezione;
            
        } catch (SQLException ex) {
             EccezioneBaseDati eccezione = new EccezioneBaseDati(
                     "connessione file SQLite "+origineDati,
                     ex.getMessage()
             );
            
            throw eccezione;
        }
            
    }

    
    
    
    
}

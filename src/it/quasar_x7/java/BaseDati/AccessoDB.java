package it.quasar_x7.java.BaseDati;

import it.quasar_x7.java.utile.DataOraria;
import it.quasar_x7.java.utile.Testo;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe astratta che implementa  molti dei metodi di accesso a una base di dati
 * Relazionale.
 * 
 * @author Dr. Domenico della Peruta
 */
public abstract class AccessoDB implements BaseDati {
    
    protected Connection connessione = null;
    protected String origineDati;




    protected PreparedStatement interrogazione(String SQL, Object... parametri) throws EccezioneBaseDati {
        try {
            PreparedStatement DB = connessione.prepareStatement(SQL);
            for (int i = 0; i < parametri.length; i++) {
                if (parametri[i] instanceof String) {
                    DB.setString(i + 1, (String) parametri[i]);
                } else if (parametri[i] instanceof Integer) {
                    DB.setInt(i + 1, (Integer) parametri[i]);
                } else if (parametri[i] instanceof Long) {
                    DB.setLong(i + 1, (Long) parametri[i]);
                } else if (parametri[i] instanceof Double) {
                    DB.setDouble(i + 1, (Double) parametri[i]);
                } else if (parametri[i] instanceof Float) {
                    DB.setFloat(i + 1, (Float) parametri[i]);
                } else if (parametri[i] instanceof DataOraria) {
                    DB.setObject(i + 1, ((DataOraria) parametri[i]).convertiDate());
                } else if (parametri[i] instanceof Boolean) {
                    DB.setBoolean(i + 1, (Boolean) parametri[i]);
                } else if (parametri[i] == null) {
                    DB.setString(i + 1, null);
                } else {
                    throw new SQLException(String.format("il valore '%s' è del tipo '%s', non previsto per l'interrogazione SQL: ", parametri[i], parametri[i].getClass().getName()));
                }
            }
            return DB;
        } catch (SQLException ex) {
            String msg = "\nistanziazione PreparedStatement parametri in ingresso:\n" + "- stringa SQL: " + SQL + "\n" + "- parametri : ";
            for (int i = 0; i < parametri.length; i++) {
                if (parametri[i] != null) {
                    msg += (i + 1) + ") " + parametri[i].toString() + ";\n";
                }
            }
            EccezioneBaseDati eccezione = new EccezioneBaseDati("metodo interrogazione()", ex.getMessage() + msg);
            eccezione.setStackTrace(ex.getStackTrace());
            throw eccezione;
        }
    }

    protected ResultSet seleziona(String SQL, Object... parametri) throws EccezioneBaseDati {
        try {
            return this.interrogazione(SQL, parametri).executeQuery();
        } catch (SQLException ex) {
            String msg = "errore: " + ex.getMessage() + "\n";
            EccezioneBaseDati eccezione = new EccezioneBaseDati("metodo seleziona()", msg);
            eccezione.setStackTrace(ex.getStackTrace());
            throw eccezione;
        }
    }

    protected void crea_modifica_cancella(String SQL, Object... parametri) throws EccezioneBaseDati {
        int creazione = 0;
        try {
            creazione = this.interrogazione(SQL, parametri).executeUpdate();
        } catch (SQLException ex) {
            EccezioneBaseDati eccezione = new EccezioneBaseDati("metodo crea_modifica_cancella()", stampaSQL(SQL, parametri, ex.getMessage()));
            eccezione.setStackTrace(ex.getStackTrace());
            throw eccezione;
        }
        if (creazione == 0) {
            throw new EccezioneBaseDati("metodo crea_modifica_cancella()", stampaSQL(SQL, parametri, "nussuna modifica apportata alla tabella!"));
        }
    }

    protected String stampaSQL(String SQL, Object[] parametri, String msg) {
        String _parametri = "";
        for (Object p : parametri) {
            if (p != null) {
                _parametri += "'" + p.toString() + "',";
            } else {
                _parametri += "NULL,";
            }
        }
        return "errore esecuzione SQL: " + msg + "\n" + "-SQL: " + SQL + "\n" + "-parametri: " + _parametri + "\n\n";
    }

    @Override
    public Object[] vediTupla(Relazione tabella, Object[] chiave) throws EccezioneBaseDati {
        if (tabella != null && chiave != null) {
            try {
                String nomeTabella = "`" + tabella.nome() + "`";
                String nomiAttributi = "";
                ArrayList<Attributo> attributi = tabella.vediTuttiAttributi();
                boolean primoValore = true;
                for (Attributo x : attributi) {
                    if (primoValore) {
                        nomiAttributi += "`" + x.nome() + "`";
                        primoValore = false;
                    } else {
                        nomiAttributi += ", `" + x.nome() + "`";
                    }
                }
                String condizione = "";
                primoValore = true;
                ArrayList<Attributo> valoriChiavi = tabella.chiave();
                for (Attributo x : valoriChiavi) {
                    if (primoValore) {
                        condizione += "`" + x.nome() + "` = ? ";
                        primoValore = false;
                    } else {
                        condizione += "AND `" + x.nome() + "` = ? ";
                    }
                }
                String SQL = String.format("SELECT %s FROM %s WHERE %s", nomiAttributi, nomeTabella, condizione);
                ResultSet dati = this.seleziona(SQL, chiave);
                ArrayList<Object[]> record = creaRecord(dati, tabella);
                if (record != null) {
                    if (record.size() > 0) {
                        return record.get(0);
                    } else {
                        throw new EccezioneBaseDati("metodo vediTuple()", "numero record > 0");
                    }
                }
            } catch (SQLException ex) {
                EccezioneBaseDati eccezione = new EccezioneBaseDati("metodo vediTuple()", ex.getMessage());
                eccezione.setStackTrace(ex.getStackTrace());
                throw eccezione;
            }
        }
        return null;
    }

    protected ArrayList<Object[]> creaRecord(ResultSet risultato, Relazione tabella) throws SQLException, EccezioneBaseDati {
        ArrayList<Object[]> listaRecord = new ArrayList<Object[]>();
        while (risultato.next()) {
            //se esiste
            int i = 0;
            Object[] record = new Object[tabella.vediTuttiAttributi().size()];
            //genera un record
            for (Attributo x : tabella.vediTuttiAttributi()) {
                if (x.tipo() instanceof DatoStringa) {
                    record[i++] = risultato.getString(x.nome());
                } else if (x.tipo() instanceof DatoTesto) {
                    record[i++] = risultato.getString(x.nome());
                } else if (x.tipo() instanceof DatoInteroCorto) {
                    record[i++] = risultato.getInt(x.nome());
                } else if (x.tipo() instanceof DatoBooleano) {
                    record[i++] = risultato.getBoolean(x.nome());
                } else if (x.tipo() instanceof DatoInteroLungo) {
                    record[i++] = risultato.getLong(x.nome());
                } else if (x.tipo() instanceof DatoRealeSemplice) {
                    record[i++] = risultato.getFloat(x.nome());
                } else if (x.tipo() instanceof DatoRealeDoppiaPrecisione) {
                    record[i++] = risultato.getDouble(x.nome());
                } else if (x.tipo() instanceof FunzioneSQL) {
                    record[i++] = risultato.getLong(((FunzioneSQL) x.tipo()).colonna());
                } else if (x.tipo() instanceof DatoDataOraria) {
                    Timestamp d = risultato.getTimestamp(x.nome());
                    DataOraria data = null;
                    if (d != null) {
                        data = new DataOraria(d);
                    }
                    record[i++] = data;
                } else if (x.tipo() instanceof DatoData) {
                    Date d = risultato.getDate(x.nome());
                    DataOraria data = null;
                    if (d != null) {
                        data = new DataOraria(d);
                    }
                    record[i++] = data;
                } else {
                    String msg = String.format("tipo \"%s\" non implementato nel metodo ", x.tipo().toString());
                    throw new EccezioneBaseDati("metodo creaRecord()", msg);
                }
            }
            listaRecord.add(record);
        }
        if (listaRecord.isEmpty()) {
            return null;
        }
        return listaRecord;
    }

    /**
     * Metodo che permette di creare una nuova
     * riga (o tuple) nella tabella (o relazione)
     * della Base di Dati
     *
     * @param tabella
     * @param record
     * @throws it.quasar_x7.java.BaseDati.EccezioneBaseDati
     */
    @Override
    public void aggiungiTupla(Relazione tabella, Object[] record) throws EccezioneBaseDati {
        if (connessione != null) {
            if (tabella != null && record != null) {
                String errore = tabella.toString() + "\nvalori record:";
                for (Object o : record) {
                    if (o != null) {
                        errore += "\n" + o.toString();
                    } else {
                        errore += "\nnull";
                    }
                }
                String nomeTabella = "`" + tabella.nome() + "`";
                ArrayList<Attributo> attributi = tabella.vediTuttiAttributi();
                if (attributi.size() == record.length) {
                    String nomiAttributi = "";
                    String nParametri = "";
                    boolean primoValore = true;
                    for (Attributo x : attributi) {
                        if (primoValore) {
                            nomiAttributi += "`" + x.nome() + "`";
                            nParametri += " ? ";
                            primoValore = false;
                        } else {
                            nomiAttributi += ", `" + x.nome() + "`";
                            nParametri += ", ? ";
                        }
                    }
                    String SQL = String.format("INSERT INTO %s (%s) VALUES (%s)", nomeTabella, nomiAttributi, nParametri);
                    crea_modifica_cancella(SQL, record);
                } else {
                    String msg = "il numero di valori da aggiungere " + "non e' uguale a quello dei campi" + " presenti nella tabella\n\n" + errore;
                    throw new EccezioneBaseDati("metodo aggiungiTupla()", msg);
                }
            } else {
                String msg = "uno dei parametri d'ingresso " + "(tabella o record) è nullo\n";
                throw new EccezioneBaseDati("metodo aggiungiTupla()", msg);
            }
        } else {
            throw new EccezioneBaseDati("metodo aggiungiTupla()", "connessione nulla");
        }
    }

    /***************************************************************************
     * Metodo che permette di modificare una nuova riga (o tuple) nella tabella
     * (o relazione).
     * della Base di Dati
     * @param tabella
     * @param chiave
     * @param record gli elementi nulli non vengono  modificati
     * @throws EccezioneBaseDati
     **************************************************************************/
    @Override
    public void modificaTupla(Relazione tabella, Object[] chiave, Object[] record) throws EccezioneBaseDati {
        if (connessione != null) {
            if (tabella != null && chiave != null && record != null) {
                String nomeTabella = "`" + tabella.nome() + "`";
                if (tabella.chiave().size() == chiave.length) {
                    ArrayList<Attributo> attributi = tabella.vediTuttiAttributi();
                    if (attributi.size() != record.length) {
                        String msg = String.format("nr. input [%s] diverso da nr. campi record DB [%s]", record.length, attributi.size());
                        throw new EccezioneBaseDati("metodo modificaTupla()", msg);
                    }
                    String nomiAttributi = "";
                    boolean privoValore = true;
                    int i = 0;
                    int m = 0;
                    for (Attributo x : attributi) {
                        if (record[i++] != null) {
                            m++;
                            if (privoValore) {
                                nomiAttributi += "`" + x.nome() + "` = ? ";
                                privoValore = false;
                            } else {
                                nomiAttributi += ", `" + x.nome() + "` = ? ";
                            }
                        }
                    }
                    Object[] parametri = new Object[m + chiave.length];
                    i = 0;
                    for (Object valore : record) {
                        if (valore != null) {
                            parametri[i++] = valore;
                        }
                    }
                    System.arraycopy(chiave, 0, parametri, i, chiave.length);
                    
                    String condizione = "";
                    privoValore = true;
                    ArrayList<Attributo> nomiChiavi = tabella.chiave();
                    for (Attributo attrChiave : nomiChiavi) {
                        if (privoValore) {
                            condizione += "`" + attrChiave.nome() + "` = ? ";
                            privoValore = false;
                        } else {
                            condizione += "AND `" + attrChiave.nome() + "` = ? ";
                        }
                    }
                    String SQL = String.format("UPDATE %s SET %s WHERE %s ", nomeTabella, nomiAttributi, condizione);
                    crea_modifica_cancella(SQL, parametri);
                } else {
                    String msg = "numero valori della chiave non corrispondenti" + " a quelli presenti nella tabella\n";
                    throw new EccezioneBaseDati("metodo modificaTupla()", msg);
                }
            } else {
                String msg = "uno dei parametri d'ingresso " + "(tabella, record o chiave) e' nullo\n";
                throw new EccezioneBaseDati("metodo modificaTupla()", msg);
            }
        } else {
            throw new EccezioneBaseDati("metodo modificaTupla()", "connessione nulla");
        }
    }

    @Override
    public void eliminaTupla(Relazione tabella, Object[] chiave) throws EccezioneBaseDati {
        if (connessione != null) {
            if (tabella != null && chiave != null) {
                String nomeTabella = "`" + tabella.nome() + "`";
                if (tabella.chiave().size() == chiave.length) {
                    String condizione = "";
                    boolean privoValore = true;
                    ArrayList<Attributo> valoriChiavi = tabella.chiave();
                    for (Attributo x : valoriChiavi) {
                        if (privoValore) {
                            condizione += "`" + x.nome() + "` = ? ";
                            privoValore = false;
                        } else {
                            condizione += "AND `" + x.nome() + "` = ? ";
                        }
                    }
                    String SQL = String.format("DELETE FROM %s WHERE %s ", nomeTabella, condizione);
                    crea_modifica_cancella(SQL, chiave);
                } else {
                    String msg = "numero valori della chiave non corrispondenti" + " a quelli presenti nella tabella\n";
                    throw new EccezioneBaseDati("metodo eliminaTupla()", msg);
                }
            } else {
                String msg = "uno dei parametri d'ingresso " + "(tabella o chiave) e' nullo\n";
                throw new EccezioneBaseDati("metodo eliminaTupla()", msg);
            }
        } else {
            throw new EccezioneBaseDati("metodo eliminaTupla()", "connessione nulla");
        }
    }

    /**
     * Trasforma il dialotto SQL di Access Microsoft in quello delle basi dati MySQL.
     * @param sqlAccess
     * @return
     */
    protected String correzioneAccessSQL(String sqlAccess) {
        Testo correzione = new Testo(sqlAccess);
        correzione.sostituisciStringa("[", "`");
        correzione.sostituisciStringa("]", "`");
        correzione.sostituisciStringa("#", "'");
        return correzione.getTesto();
    }

    @Override
    public ArrayList<Object[]> interrogazioneSempliceTabella(Relazione tabella, String condizione) throws EccezioneBaseDati {
        condizione = correzioneAccessSQL(condizione);
        if (tabella != null) {
            try {
                String nomeTabella = "`" + tabella.nome() + "`";
                String nomiAttributi = "";
                ArrayList<Attributo> attributi = tabella.vediTuttiAttributi();
                boolean primoValore = true;
                for (Attributo x : attributi) {
                    if (primoValore) {
                        nomiAttributi += "`" + x.nome() + "`";
                        primoValore = false;
                    } else {
                        nomiAttributi += ", `" + x.nome() + "`";
                    }
                }
                String SQL = String.format("SELECT %s FROM %s WHERE %s", nomiAttributi, nomeTabella, condizione);
                ResultSet dati = this.seleziona(SQL);
                ArrayList<Object[]> record = creaRecord(dati, tabella);
                if (record != null) {
                    if (record.size() > 0) {
                        return record;
                    } else {
                        throw new EccezioneBaseDati("metodo interrogazioneSempliceTabella()", " numero record <= 0");
                    }
                }
            } catch (SQLException ex) {
                EccezioneBaseDati eccezione = new EccezioneBaseDati("metodo interrogazioneSempliceTabella()", ex.getMessage());
                eccezione.setStackTrace(ex.getStackTrace());
                throw eccezione;
            }
        }
        return null;
    }

    /**
     * 
     * @param tabella
     * @return
     * @throws EccezioneBaseDati 
     */
    @Override
    public ArrayList<Object[]> vediTutteLeTuple(Relazione tabella) throws EccezioneBaseDati {
        return this.interrogazioneSempliceTabella(tabella, " 1 = 1 ");
    }

    /*************************************************
     * Effettua la chiusura della connessione alla
     * base dati MySQL
     * @throws it.quasar_x7.java.BaseDati.EccezioneBaseDati
     ************************************************/
    @Override
    public void chiudi() throws EccezioneBaseDati {
        if (this.connessione != null) {
            try {
                this.connessione.close();
                this.connessione = null;
            } catch (SQLException ex) {
                //JOptionPane.showMessageDialog(null, "errore di chiusura connessione base dati MySQL:\n" + ex.getMessage(), "errore", JOptionPane.ERROR_MESSAGE);
                EccezioneBaseDati eccezione = new EccezioneBaseDati("metodo chiudi()", ex.getMessage());
                eccezione.setStackTrace(ex.getStackTrace());
                throw eccezione;
            }
        }
    }

    protected boolean funzioneSQL(String nomeColonna) {
        return new Testo(nomeColonna).cercaCarattere('(') != -1 && new Testo(nomeColonna).cercaCarattere(')') != -1;
    }

    @Override
    public ArrayList<Object[]> interrogazioneSempliceTabella(Relazione tabella, Attributo[] colonne, String condizione) throws EccezioneBaseDati {
        condizione = correzioneAccessSQL(condizione);
        if (tabella != null) {
            try {
                String nomeTabella = "`" + tabella.nome() + "`";
                tabella = new Relazione(nomeTabella);
                String seleziona = "";
                boolean primoElemento = true;
                for (Attributo x : colonne) {
                    if (primoElemento) {
                        if (this.funzioneSQL(x.nome())) {
                            seleziona += x.nome();
                        } else {
                            seleziona += "`" + x.nome() + "`";
                        }
                        primoElemento = false;
                    } else {
                        if (this.funzioneSQL(x.nome())) {
                            seleziona += ", " + x.nome();
                        } else {
                            seleziona += ", `" + x.nome() + "`";
                        }
                        primoElemento = false;
                    }
                    tabella.creaAttributo(x.nome(), x.tipo(), null, false);
                }
                String SQL = String.format("SELECT %s FROM %s WHERE %s", seleziona, nomeTabella, condizione);
                ResultSet dati = this.seleziona(SQL);
                ArrayList<Object[]> record = creaRecord(dati, tabella);
                if (record != null) {
                    if (record.size() > 0) {
                        return record;
                    } else {
                        throw new EccezioneBaseDati("metodo interrogazioneSempliceTabella()", " numero record <=0");
                    }
                }
            } catch (SQLException ex) {
                EccezioneBaseDati eccezione = new EccezioneBaseDati("metodo interrogazioneSempliceTabella()", ex.getMessage());
                eccezione.setStackTrace(ex.getStackTrace());
                throw eccezione;
            }
        }
        return null;
    }
    //------------------------------------------------------------------------------

    /**
     * Interrogazione senza restituzione di valore.
     * 
     * @param query          Es.: "UPDATE tabella SET nome = ?,  WHERE id = ? "
     * @param parametri      valori da sostituire nei punti interrogativi
     * @throws EccezioneBaseDati 
     */
    public void interrogazioneDiModificaSQL(String query, Object... parametri) throws EccezioneBaseDati{
        try {
            crea_modifica_cancella(query, parametri);
        } catch (EccezioneBaseDati ex) {
            if(!ex.getMessage().contains("nussuna modifica apportata alla tabella!")){
                throw new EccezioneBaseDati("metodo interrogazioneSQL()", "interrogazione SQL fallinta:"+ex.getMessage());
            }
        }
    }
    
    @Override
    public ArrayList<Object[]> interrogazioneSQL(String query, Attributo[] dati) throws EccezioneBaseDati {
        query = correzioneAccessSQL(query);
        Relazione tabella = new Relazione("");
        try {
            for (Attributo att : dati) {
                tabella.creaAttributo(att.nome(), att.tipo(), att.valorePredefinito(), att.chiave());
            }
            ResultSet d = this.seleziona(query);
            ArrayList<Object[]> record = creaRecord(d, tabella);
            if (record != null) {
                if (record.size() > 0) {
                    return record;
                } else {
                    throw new EccezioneBaseDati("metodo interrogazioneSQL()", " numero record <= 0");
                }
            }
        } catch (SQLException ex) {
            EccezioneBaseDati eccezione = new EccezioneBaseDati("metodo interrogazioneSQL()", ex.getMessage());
            eccezione.setStackTrace(ex.getStackTrace());
            throw eccezione;
        }
        return null;
    }

    @Override
    public ArrayList<Object[]> interrogazioneJoin(Relazione[] tabelle, Attributo[] colonne, String condizione) throws EccezioneBaseDati {
        condizione = correzioneAccessSQL(condizione);
        if (tabelle != null && colonne != null) {
            try {
                String[] nomeTabelle = new String[tabelle.length];
                for (int i = 0; i < tabelle.length; i++) {
                    nomeTabelle[i] = "`" + tabelle[i].nome() + "`";
                }
                boolean primoElemento = true;
                String elencoTabelle = "";
                for (int i = 0; i < nomeTabelle.length; i++) {
                    if (primoElemento) {
                        elencoTabelle += nomeTabelle[i];
                        primoElemento = false;
                    } else {
                        elencoTabelle += "," + nomeTabelle[i];
                    }
                }
                Relazione join = new Relazione("join");
                String seleziona = "";
                primoElemento = true;
                for (Attributo x : colonne) {
                    if (primoElemento) {
                        if (this.funzioneSQL(x.nome())) {
                            if (x.relazione() != null) {
                                seleziona += x.relazione() + "." + x.nome();
                            } else {
                                seleziona += x.nome();
                            }
                        } else {
                            if (x.relazione() != null) {
                                seleziona += x.relazione() + ".`" + x.nome() + "`";
                            } else {
                                seleziona += "`" + x.nome() + "`";
                            }
                        }
                        primoElemento = false;
                    } else {
                        if (this.funzioneSQL(x.nome())) {
                            if (x.relazione() != null) {
                                seleziona += ", " + x.relazione() + "." + x.nome();
                            } else {
                                seleziona += ", " + x.nome();
                            }
                        } else {
                            if (x.relazione() != null) {
                                seleziona += ", " + x.relazione() + ".`" + x.nome() + "`";
                            } else {
                                seleziona += ", `" + x.nome() + "`";
                            }
                        }
                        primoElemento = false;
                    }
                    join.creaAttributo(x.nome(), x.tipo(), null, false);
                }
                String SQL = String.format("SELECT %s FROM %s WHERE %s", seleziona, elencoTabelle, condizione);
                ResultSet dati = this.seleziona(SQL);
                ArrayList<Object[]> record = creaRecord(dati, join);
                if (record != null) {
                    if (record.size() > 0) {
                        return record;
                    } else {
                        throw new EccezioneBaseDati("metodo interrogazioneJoin()", " numero record <= 0");
                    }
                }
            } catch (SQLException ex) {
                EccezioneBaseDati eccezione = new EccezioneBaseDati("metodo interrogazioneJoint()", ex.getMessage());
                eccezione.setStackTrace(ex.getStackTrace());
                throw eccezione;
            }
        }
        return null;
    }

    @Override
    public void generaTabella(Relazione tabella) throws EccezioneBaseDati {
        if (tabella != null) {
            String SQL = "";
            try {
                if (tabella.nome() != null) {
                    SQL += "CREATE TABLE ";
                    SQL += "`" + tabella.nome() + "` ( ";
                    if (tabella.vediTuttiAttributi() != null) {
                        boolean primo = true;
                        for (Attributo colonna : tabella.vediTuttiAttributi()) {
                            if (colonna != null) {
                                if (primo) {
                                    primo = false;
                                } else {
                                    SQL += ", ";
                                }
                                String nome = "`" + colonna.nome() + "`";
                                if (nome == null) {
                                    throw new EccezioneBaseDati("metodo generaTabella()", "nome attributo nullo");
                                }
                                if (nome.length() == 0) {
                                    throw new EccezioneBaseDati("metodo generaTabella()", "attributo senza nome");
                                }
                                if (colonna.tipo() == null) {
                                    throw new EccezioneBaseDati("metodo generaTabella()", "tipo attributo nullo");
                                }
                                String dominio = colonna.tipo().toString();
                                String valore = "";
                                if (colonna.valorePredefinito() != null) {
                                    valore = " NOT NULL ";
                                    if (dominio.length() >= 7) {
                                        if (dominio.substring(0, 7).compareTo("VARCHAR") == 0) {
                                            valore += "DEFAULT '" + colonna.valorePredefinito() + "'";
                                        } else if (dominio.compareTo("DATETIME") == 0) {
                                            valore += "DEFAULT '" + colonna.valorePredefinito() + "'";
                                        }
                                    } else if (dominio.compareTo("TEXT") == 0) {
                                        valore += "DEFAULT '" + colonna.valorePredefinito() + "'";
                                    } else {
                                        valore += "DEFAULT "+colonna.valorePredefinito();
                                    }
                                }
                                SQL += nome + " " + dominio + valore;
                            } else {
                                throw new EccezioneBaseDati("metodo generaTabella()", "attributo nullo");
                            }
                        }
                        // CHIAVI
                        primo = true;
                        for (Attributo colonna : tabella.vediTuttiAttributi()) {
                            if (colonna.chiave()) {
                                if (primo) {
                                    primo = false;
                                    SQL += ", PRIMARY KEY (";
                                } else {
                                    SQL += ", ";
                                }
                                SQL += "`" + colonna.nome() + "`";
                            }
                        }
                        if (!primo) {
                            SQL += ")";
                        }
                    }
                    SQL += " )";
                    connessione.createStatement().executeUpdate(SQL);
                } else {
                    throw new EccezioneBaseDati("metodo generaTabella()", "nome tabella nullo");
                }
            } catch (SQLException ex) {
                EccezioneBaseDati eccezione = new EccezioneBaseDati(
                        "metodo generaTabella()",
                        String.format("- messaggio: %s\n- SQL:\n%s", ex.getMessage(),SQL)
                );
                eccezione.setStackTrace(ex.getStackTrace());
                throw eccezione;
            }
        } else {
            throw new EccezioneBaseDati("metodo generaTabella()", "Relazione (tabella) nulla");
        }
    }

    /**
     * Interrogazione di tipo JOINT OUTER di sinistra tra due tabbelle.
     *
     * @param tabellaSx
     * @param tabellaDx
     * @param AbilitaLeftOuter
     * @param colonne
     * @param condizione
     * @return
     * @throws EccezioneBaseDati
     */
    @Override
    public ArrayList<Object[]> interrogazioneJoin(Relazione tabellaSx, Relazione tabellaDx, boolean AbilitaLeftOuter, Attributo[] colonne, String condizione) throws EccezioneBaseDati {
        condizione = correzioneAccessSQL(condizione);
        if (tabellaSx != null && tabellaDx != null && colonne != null) {
            try {
                boolean primoElemento = true;
                Relazione join = new Relazione("join");
                String seleziona = "";
                for (Attributo x : colonne) {
                    if (primoElemento) {
                        if (this.funzioneSQL(x.nome())) {
                            if (x.relazione() != null) {
                                seleziona += x.relazione() + "." + x.nome();
                            } else {
                                seleziona += x.nome();
                            }
                        } else {
                            if (x.relazione() != null) {
                                seleziona += x.relazione() + ".`" + x.nome() + "`";
                            } else {
                                seleziona += "`" + x.nome() + "`";
                            }
                        }
                        primoElemento = false;
                    } else {
                        if (this.funzioneSQL(x.nome())) {
                            if (x.relazione() != null) {
                                seleziona += ", " + x.relazione() + "." + x.nome();
                            } else {
                                seleziona += ", " + x.nome();
                            }
                        } else {
                            if (x.relazione() != null) {
                                seleziona += ", " + x.relazione() + ".`" + x.nome() + "`";
                            } else {
                                seleziona += ", `" + x.nome() + "`";
                            }
                        }
                        primoElemento = false;
                    }
                    join.creaAttributo(x.nome(), x.tipo(), null, false);
                }
                String leftOuter = AbilitaLeftOuter ? "LEFT OUTER" : "";
                String SQL = String.format("SELECT %s FROM `%s` %s JOIN `%s` ON %s", seleziona, tabellaSx, leftOuter, tabellaDx, condizione);
                ResultSet dati = this.seleziona(SQL);
                ArrayList<Object[]> record = creaRecord(dati, join);
                if (record != null) {
                    if (record.size() > 0) {
                        return record;
                    } else {
                        throw new EccezioneBaseDati("metodo interrogazioneJoin()", " numero record <= 0");
                    }
                }
            } catch (SQLException ex) {
                EccezioneBaseDati eccezione = new EccezioneBaseDati("metodo interrogazioneJoint()", ex.getMessage());
                eccezione.setStackTrace(ex.getStackTrace());
                throw eccezione;
            }
        }
        return null;
    }
    
    public void eliminaTutteLeTuple(Relazione tabella, String condizione) throws EccezioneBaseDati{
    	if (connessione != null) {
            if (tabella != null) {
                String nomeTabella = "`" + tabella.nome() + "`";
                
                String SQL = String.format("DELETE FROM %s %s", nomeTabella, condizione != null ? ("WHERE "+condizione) : "");
                 
                try {
                    interrogazione(SQL).executeUpdate();
                } catch (SQLException ex) {
                    throw new EccezioneBaseDati("metodo eliminaTutteLeTuple()", ex.getMessage());
                }
                
            } else {
                String msg = String.format("la tabella è nulla\n");
                throw new EccezioneBaseDati("metodo eliminaTutteLeTuple()", msg);
            }
        } else {
            throw new EccezioneBaseDati("metodo eliminaTutteLeTuple()", "connessione nulla");
        }
    }
    
    public void eliminaTutteLeTuple(Relazione tabella) throws EccezioneBaseDati {
        this.eliminaTupla(tabella, null); 
    }
    
}

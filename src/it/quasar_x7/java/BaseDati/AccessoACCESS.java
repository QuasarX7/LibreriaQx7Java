package it.quasar_x7.java.BaseDati;

import it.quasar_x7.java.utile.DataOraria;
import it.quasar_x7.java.utile.Testo;
import java.sql.*;
import java.util.ArrayList;

/*******************************************************************************
 * Classe che permette di accedere ad una Base di Dati Jet/Access
 *
 * @author Domenico della Peruta
 ******************************************************************************/
public class AccessoACCESS implements BaseDati {

 //------------------------------ proprietà ------------------------------------
    private Connection connessione =null;
    private String ODCB;
 //--------------------------- costruttore -------------------------------------
      /**************************************************
     * Costruttore con accesso MySQL tramite connessione
     * ODBC
     *
     * @param connessioneODCB
     **************************************************/
    public AccessoACCESS(String connessioneODCB){
        this.ODCB = connessioneODCB;
    }
//------------------------------ metodi ----------------------------------------

    /**************************************************
     * Effettua la connessione alla base dati Access
     *************************************************/
    @Override
    public void connetti() {

        if(this.connessione != null){//se gia' e' aperta una connessione
            return;                  //esce
        }

        try{
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            
            connessione= DriverManager.getConnection(
                        "jdbc:odbc:"+ODCB,"","");
            
        }
        catch(ClassNotFoundException e){
            javax.swing.JOptionPane.showMessageDialog(
                    null,
                    "errore caricamento driver ACCESS:\n"+e.getMessage(),
                    "errore", 
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch(SQLException e){

            javax.swing.JOptionPane.showMessageDialog(
                    null,
                    "ERRORE DI CONNESSIONE alla base di dati \""
                    +ODCB+"\"....\n"+
                    e.getMessage(),
                    "errore",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    private PreparedStatement interrogazione(String SQL,Object  ... parametri){
        try {
            PreparedStatement DB= connessione.prepareStatement(SQL);

            for(int i=0; i<parametri.length;i++){
                if(parametri[i]instanceof String){
                    DB.setString(i+1, (String)parametri[i]);

                }else if(parametri[i]instanceof Integer){
                    DB.setInt(i+1, (Integer)parametri[i]);

                }else if(parametri[i]instanceof DataOraria){
                    DB.setObject(i+1,java.sql.Timestamp.valueOf(((DataOraria)parametri[i]).stampaGiornoOraInverso()));

                }else if(parametri[i]instanceof Boolean){
                    DB.setBoolean(i+1, (Boolean)parametri[i]);

                }else if(parametri[i]== null){
                    DB.setString(i+1, null);
                }else{
                    throw new SQLException("tipo parametro non definito");
                }
            }
            return DB;

        } catch (SQLException ex) {
            System.out.println(
                    "Errore istanziazione PreparedStatement " +
                    "nel metodo interrogazione()");
            System.out.println("parametri in ingresso:\n");
            System.out.println("- stringa SQL: "+SQL+"\n");
            System.out.println("- parametri : ");
            for(int i=0; i<parametri.length;i++){
                if(parametri[i]!=null)
                    System.out.println((i+1)+") "+parametri[i].toString()+"; ");
            }

            ex.printStackTrace();
        }
        return null;
    }

    private ResultSet seleziona(String SQL,Object  ... parametri){
        try {
            return this.interrogazione(SQL, parametri).executeQuery();

        } catch (SQLException ex) {
            System.out.println("errore sintassi selezione SQL:"+
                    ex.getMessage()+"\n");

            ex.printStackTrace();
        }
        return null;
    }

    private boolean crea_modifica_cancella(String SQL,Object  ... parametri){
        int n = 0;
        try {
            n=this.interrogazione(SQL, parametri).executeUpdate();
        } catch (SQLException ex) {
            System.out.println("errore sintassi SQL:"+
                    ex.getMessage()+"\n");

            ex.printStackTrace();
        }
        if(n != 0)
            return true;
        return false;
    }


    public Object[] vediTupla(Relazione tabella, Object[] chiave) {       
 
        if(tabella != null && chiave!=null){
            try {
                String nomeTabella = tabella.nome();
                
                String nomiAttributi = "";
                ArrayList<Attributo> attributi = tabella.vediTuttiAttributi();
                boolean primoValore=true;
                for(Attributo x: attributi){
                    if(primoValore){
                        nomiAttributi += "["+x.nome()+"]";
                        primoValore=false;
                    }else{
                        nomiAttributi += ", ["+x.nome()+"]";
                    }
                }
                
                String condizione = "";
                primoValore=true;
                ArrayList<Attributo> valoriChiavi = tabella.chiave();
                for(Attributo x: valoriChiavi){
                    if(primoValore){
                        condizione += "["+x.nome()+"] = ? ";
                        primoValore=false;
                    }else{
                        condizione += "AND ["+x.nome()+"] = ? ";
                    }
                }
                    
                String SQL =String.format(
                        "SELECT %s FROM %s WHERE %s",
                        nomiAttributi,nomeTabella,condizione);

                ResultSet dati = this.seleziona(SQL, chiave);
                
                ArrayList<Object[]> record = creaRecord(dati, tabella);
                if(record != null){
                    if(record.size()>0){
                        return record.get(0);
                    }else{
                        System.out.println(
                                "\nerrore metodo vediTuple():" +
                                " numero record > 0");
                    }
                }
                
            } catch (SQLException ex) {
                System.out.println(
                                "\nerrore metodo vediTuple():" 
                                +ex.getMessage()+"\n");
                ex.printStackTrace();
            }
        }
        return null;
    }

    private ArrayList<Object[]> creaRecord(ResultSet risultato,Relazione tabella) throws SQLException{
        ArrayList<Object[]> listaRecord =  new ArrayList<Object[]>();

        while(risultato.next()){//se esiste            
            int i =0;
            Object[] record = new Object[tabella.vediTuttiAttributi().size()];
            
            //genera un record
            for(Attributo x: tabella.vediTuttiAttributi()){
                if(x.tipo() instanceof DatoStringa){
                    record[i++] = risultato.getString(x.nome());

                }else if(x.tipo() instanceof DatoInteroCorto){
                    record[i++] = risultato.getInt(x.nome());

                }else if(x.tipo() instanceof DatoBooleano){
                    record[i++] = risultato.getBoolean(x.nome());

                }else if(x.tipo() instanceof DatoInteroLungo){                    
                    record[i++] = risultato.getLong(x.nome());
                    
                    
                }else if(x.tipo() instanceof DatoRealeSemplice){
                    record[i++] = risultato.getFloat(x.nome());

                }else if(x.tipo() instanceof DatoRealeDoppiaPrecisione){
                    record[i++] = risultato.getDouble(x.nome());

                }else if(x.tipo() instanceof FunzioneSQL){
                   record[i++] = risultato.getLong(
                           ((FunzioneSQL)x.tipo()).colonna());
                    
                } else if (x.tipo() instanceof DatoDataOraria) {
                   Timestamp d = risultato.getTimestamp(x.nome());

                   DataOraria data = null;
                   if(d != null){
                       data = new DataOraria(d);
                   }
                   record[i++] = data;

                }else{

                    System.out.println(
                            String.format(
                            "Tipo \"%s\" non implementato nel metodo " +
                            "creaRecord()",
                            x.tipo().toString()));
                    
                }
            }
            listaRecord.add(record);                           
        }
        if(listaRecord.size()==0)
            return null;
        return listaRecord;
    }

    
    /*************************************************
     * Metodo che permette di creare una nuova
     * riga (o tuple) nella tabella (o relazione)
     * della Base di Dati
     *
     * @param tabella
     * @param chiave valori d'aggiungere
     * @return
     *************************************************/
    public void aggiungiTupla(Relazione tabella, Object[] record) {
        if(connessione != null){
            if(tabella != null && record != null){
                String nomeTabella = tabella.nome();

                ArrayList<Attributo> attributi = tabella.vediTuttiAttributi();
                
                if(attributi.size() == record.length){
                    String nomiAttributi = "";
                    String nParametri = "";
                    boolean privoValore=true;
                    for(Attributo x: attributi){
                        if(privoValore){
                            nomiAttributi += "["+x.nome()+"]";
                            nParametri += " ? ";
                            privoValore=false;
                        }else{
                            nomiAttributi += ", ["+x.nome()+"]";
                            nParametri += ", ? ";
                        }
                    }

                    String SQL =
                            String.format(
                            "INSERT INTO %s (%s) VALUES (%s)"
                            ,nomeTabella,nomiAttributi,nParametri);

                    this.crea_modifica_cancella(SQL, record);

                }else{
                    System.out.println(
                            "\nerrore metodo aggiungiTupla(): " +
                            "il numero di valori da aggiungere " +
                            "non e' uguale a quello dei campi" +
                            " presenti nella tabella\n");
                }
            }else{
                System.out.println(
                    "\nerrore metodo aggiungiTupla(): " +
                    "uno dei parametri d'ingresso " +
                    "(tabella o record) è nullo\n");
            }

        }else{
            System.out.println(
                    "\nerrore metodo aggiungiTupla(): connessione nulla");
        }
    }

    public void modificaTupla(Relazione tabella, Object[] chiave, Object[] record) {
        if(connessione != null){
            if(tabella != null && chiave != null && record != null){

                String nomeTabella = tabella.nome();
                if(tabella.chiave().size() == chiave.length){

                    ArrayList<Attributo> attributi=tabella.vediTuttiAttributi();
                    if(attributi.size() != record.length){
                        System.out.println(
                            "\nerrore metodo modificaTupla(): " +
                            "il numero di valori da modificare " +
                            "non e' uguale a quello dei campi" +
                            " presenti nella tabella\n");
                         
                     }
                    String nomiAttributi = "";
                    boolean privoValore=true;
                    int i=0,m=0;
                    for(Attributo x: attributi){
                        if(record[i++] != null){
                            m++;
                            if(privoValore){
                                nomiAttributi += "["+x.nome()+"] = ? ";
                                privoValore=false;
                               
                            }else{
                                nomiAttributi += ", ["+x.nome()+"] = ? ";
                            }
                        }
                    }

                    Object[] parametri = new Object[m+chiave.length];
                    i=0;
                    for(int j=0; j < record.length; j++){
                        if(record[j]!=null){
                            parametri[i++] = record[j];
                        }
                    }
                    for(int k=0; k < chiave.length; k++){
                        parametri[i+k]=chiave[k];
                    }
                    
                    String condizione = "";
                    privoValore=true;
                    ArrayList<Attributo> valoriChiavi = tabella.chiave();
                    for(Attributo x: valoriChiavi){
                        if(privoValore){
                            condizione += "["+x.nome()+"] = ? ";
                            privoValore=false;
                        }else{
                            condizione += "AND ["+x.nome()+"] = ? ";
                        }
                    }

                    String SQL =
                            String.format(
                            "UPDATE %s SET %s WHERE %s "
                            ,nomeTabella,nomiAttributi,condizione);

                    this.crea_modifica_cancella(SQL, parametri);

                }else{
                    System.out.println(
                            "\nerrore metodo modificaTupla(): " +
                            "numero valori della chiave non corrispondenti" +
                            " a quelli presenti nella tabella\n");
                }
            }else{
                System.out.println(
                    "\nerrore metodo modificaTupla(): " +
                    "uno dei parametri d'ingresso " +
                    "(tabella, record o chiave) e' nullo\n");
            }

        }else{
            System.out.println(
                    "\nerrore metodo modificaTupla(): connessione nulla");
        }
    }

    public void eliminaTupla(Relazione tabella, Object[] chiave) {
         if(connessione != null){
            if(tabella != null && chiave != null){
                String nomeTabella = tabella.nome();
                if(tabella.chiave().size() == chiave.length){
                    String condizione = "";
                    boolean privoValore=true;
                    ArrayList<Attributo> valoriChiavi = tabella.chiave();
                    for(Attributo x: valoriChiavi){
                        if(privoValore){
                            condizione += "["+x.nome()+"] = ? ";
                            privoValore=false;
                        }else{
                            condizione += "AND ["+x.nome()+"] = ? ";
                        }
                    }

                    String SQL =
                            String.format(
                            "DELETE FROM %s WHERE %s "
                            ,nomeTabella,condizione);

                    this.crea_modifica_cancella(SQL, chiave);

                }else{
                    System.out.println(
                            "\nerrore metodo eliminaTupla(): " +
                            "numero valori della chiave non corrispondenti" +
                            " a quelli presenti nella tabella\n");
                }
            }else{
                System.out.println(
                    "\nerrore metodo eliminaTupla(): " +
                    "uno dei parametri d'ingresso " +
                    "(tabella o chiave) e' nullo\n");
            }

        }else{
            System.out.println(
                    "\nerrore metodo eliminaTupla(): connessione nulla");
        }
    }

    public ArrayList<Object[]> interrogazioneSempliceTabella(Relazione tabella, String condizione) {
        if(tabella != null){
            try {
                String nomeTabella = tabella.nome();

                String nomiAttributi = "";
                ArrayList<Attributo> attributi = tabella.vediTuttiAttributi();
                boolean primoValore=true;
                for(Attributo x: attributi){
                    if(primoValore){
                        nomiAttributi += "["+x.nome()+"]";
                        primoValore=false;
                    }else{
                        nomiAttributi += ", ["+x.nome()+"]";
                    }
                }

                String SQL =String.format(
                        "SELECT %s FROM %s WHERE %s",
                        nomiAttributi,nomeTabella,condizione);

                ResultSet dati = this.seleziona(SQL);
                ArrayList<Object[]> record = creaRecord(dati, tabella);
                if(record != null){
                    if(record.size()>0){
                        return record;
                    }else{
                        System.out.println(
                                "\nerrore metodo interrogazioneSempliceTabella() " +
                                " numero record > 0");
                    }
                }
            } catch (SQLException ex) {
                System.out.println(
                                "\nerrore metodo interrogazioneSempliceTabella():"
                                +ex.getMessage()+"\n");
                ex.printStackTrace();
            }
        }
        return null;
    }

    public ArrayList<Object[]> vediTutteLeTuple(Relazione tabella) {
        return this.interrogazioneSempliceTabella(tabella, " 1 = 1 ");
    }

    /*************************************************
     * Effettua la chiusura della connessione alla
     * base dati MySQL
     ************************************************/
    @Override
    public void chiudi() {
        if(this.connessione != null){
            try {
                this.connessione.close();
                this.connessione = null;
            } catch (SQLException ex) {
                javax.swing.JOptionPane.showMessageDialog(
                        null,
                        "errore di chiusura connessione base dati Jet/Access:\n"+
                        ex.getMessage(),
                        "errore",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean funzioneSQL(String nomeColonna){
         return(
                 new Testo(nomeColonna).cercaCarattere('(')!=-1 &&
                 new Testo(nomeColonna).cercaCarattere(')')!=-1
                 );
    }

    @Override
    public ArrayList<Object[]> interrogazioneSempliceTabella(Relazione tabella, Attributo[] colonne, String condizione) {
       if(tabella != null){
            try {
                String nomeTabella = tabella.nome();

                tabella = new Relazione(nomeTabella);
                String seleziona ="";

                boolean primoElemento=true;
                for(Attributo x: colonne){
                    if(primoElemento){
                        if(this.funzioneSQL(x.nome())){
                             seleziona += x.nome();
                        }else{
                            seleziona += "["+x.nome()+"]";
                        }
                         primoElemento=false;
                    }else{
                        if(this.funzioneSQL(x.nome())){
                             seleziona += ", "+x.nome();
                        }else{
                            seleziona += ", ["+x.nome()+"]";
                        }
                        primoElemento=false;
                    }

                    tabella.creaAttributo(x.nome(), x.tipo());
                }

                String SQL =String.format(
                        "SELECT %s FROM %s WHERE %s",
                        seleziona,nomeTabella,condizione);


                ResultSet dati = this.seleziona(SQL);
                ArrayList<Object[]> record = creaRecord(dati, tabella);
                if(record != null){
                    if(record.size()>0){
                        return record;
                    }else{
                        System.out.println(
                                "\nerrore metodo interrogazioneSempliceTabella() " +
                                " numero record > 0");
                    }
                }
            } catch (SQLException ex) {
                System.out.println(
                                "\nerrore metodo interrogazioneSempliceTabella():"
                                +ex.getMessage()+"\n");
                ex.printStackTrace();
            }
        }
        return null;

    }
//------------------------------------------------------------------------------

    public ArrayList<Object[]> interrogazioneSQL(String query, Attributo[] dati) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    public ArrayList<Object[]> interrogazioneJoin(Relazione[] tabella, Attributo[] colonne, String condizione) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void generaTabella(Relazione tabella) throws EccezioneBaseDati {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Object[]> interrogazioneJoin(Relazione tabellaSx, Relazione tabellaDx, boolean AbilitaLeftOuter, Attributo[] colonne, String condizione) throws EccezioneBaseDati {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
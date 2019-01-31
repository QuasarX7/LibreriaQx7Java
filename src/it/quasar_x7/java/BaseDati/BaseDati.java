package it.quasar_x7.java.BaseDati;

import java.util.ArrayList;

public interface BaseDati  {	

    public void connetti()throws EccezioneBaseDati;
    public Object[] vediTupla(Relazione tabella,Object[] chiave)throws EccezioneBaseDati;
    public void generaTabella(Relazione tabella) throws EccezioneBaseDati;
    public void aggiungiTupla(Relazione tabella,Object[] record)throws EccezioneBaseDati;
    public void modificaTupla(Relazione tabella,Object[] chiave,Object[] record)throws EccezioneBaseDati;
    public void eliminaTupla(Relazione tabella,Object[] chiave)throws EccezioneBaseDati;
    public ArrayList<Object[]> interrogazioneSQL(String query, Attributo[] dati)throws EccezioneBaseDati;
    public ArrayList<Object[]> interrogazioneSempliceTabella(Relazione tabella, String condizione)throws EccezioneBaseDati;
    public ArrayList<Object[]> interrogazioneSempliceTabella(Relazione tabella, Attributo[] colonne,String condizione)throws EccezioneBaseDati;
    public ArrayList<Object[]> interrogazioneJoin(Relazione tabella[], Attributo[] colonne,String condizione)throws EccezioneBaseDati;
    public ArrayList<Object[]> interrogazioneJoin(Relazione tabellaSx,Relazione tabellaDx, boolean AbilitaLeftOuter, Attributo[] colonne,String condizione)throws EccezioneBaseDati;
    public ArrayList<Object[]> vediTutteLeTuple(Relazione tabella)throws EccezioneBaseDati;
    public void chiudi()throws EccezioneBaseDati;
		
}

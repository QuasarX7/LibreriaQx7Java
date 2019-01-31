package it.quasar_x7.java.BaseDati;

import java.util.ArrayList;

public class Relazione{

        public static boolean CHIAVE    = true;
        public static boolean NO_CHIAVE = false;
        
	private String nome;
	private final ArrayList<Attributo> campi;
       
        
		
	public Relazione(String nome){
		campi = new ArrayList<Attributo>();
		this.nome = nome;
	}

        /*
         * Permette di creare i vari campi di una tabella
         * cioè l'insieme degli attributi.
         *
         * @param nome
         * @param tipo
         * @param valoreIniziale Es.:
         * - stringhe 'ciao';
         * - interi 61;
         * - data #2009-02-23#;
         * @param chiave
         * @throws it.quasar_x7.java.BaseDati.EccezioneBaseDati valore predefinito chiave nullo
         **/
	public void creaAttributo(String nome,Dominio tipo,
                String valoreIniziale,boolean chiave) throws EccezioneBaseDati{
		
		Attributo campo = new Attributo(nome, tipo, chiave);
                campo.relazione(this.nome);
		campo.valorePredefinito(valoreIniziale);
		
		this.campi.add(campo);
		
	}
        /***
         * Crea campi di una tabella usati solo per interrogazioni SQL.
         * @param nome
         * @param tipo 
         */
        public void creaAttributo(String nome,Dominio tipo) {
		Attributo campo = new Attributo(nome, tipo, false);
                campo.relazione(this.nome);
		
		this.campi.add(campo);
		
	}
        
        
        public String nomeAttributo(int id){
            return campi.get(id).nome();
        }
	
	public Attributo vediAttributo(String nome){
		for(Attributo x:campi){
			if(((Attributo)x).nome().compareTo(nome)==0){
				return (Attributo)x;
			}
		}
		return null;
	}
	
        /***********************************************************************
         * Mostra le specifiche di un attributo
         * @param id
         * @return 
         **********************************************************************/
        public Attributo vediAttributo(int id){
		return campi.get(id);
	}
	
        
	public ArrayList<Attributo> chiave(){
		ArrayList<Attributo> key = null;
		if(!campi.isEmpty()){
			key = new ArrayList<Attributo>();
			for(Attributo x:campi){
				if(((Attributo)x).chiave()){
					key.add((Attributo) x);
				}
			}
		}
		return key;
	}
	
	public ArrayList<Attributo> vediTuttiAttributi(){
		ArrayList<Attributo> key = null;
		if(!campi.isEmpty()){
			key = new ArrayList<Attributo>();
			for(Attributo x:campi){
				key.add((Attributo) x);
			}
		}
		return key;
	}
	
        
	public String nome(){
		return this.nome;
	}


        public void nome(String nome){
		this.nome = nome;
	}
        
        @Override
        public String toString(){

            String x =                
                    "\n"+nome+"\n" ;
                   
            for(Attributo a: this.campi){
                x+= "["+a.nome()+"]";
            }
            x+="\n";
            return x;
        }
        
}

package it.quasar_x7.java.BaseDati;

public class Attributo{
	
	private String		nome	= "";
	private Dominio		tipo	= null;
	private boolean		chiave	= false;
	private String      valorePredefinito = null;
        private String          nomeRelazione = null;
        private LegameReferenziale legame = null;

	public Attributo(String nome,Dominio tipo,boolean chiave){
            this.tipo   = tipo;
            this.nome   = nome;
	    this.chiave = chiave;
                
	}
        
        
        public Attributo(String nome,Dominio tipo,String relazione,boolean chiave){
            this(nome,tipo,chiave);
            this.nomeRelazione = relazione;
	}
        
        public Attributo(String nome,Dominio tipo,String valore, String relazione,boolean chiave) throws EccezioneBaseDati{
            this(nome,tipo,relazione,chiave);
            valorePredefinito(valore);
	}
        
        public Attributo(String nome,Dominio tipo,String valore, String relazione,LegameReferenziale legame,boolean chiave) throws EccezioneBaseDati{
            this(nome,tipo,valore,relazione,chiave);
            this.legame = legame;
	}

        public String relazione() {
            return nomeRelazione;
        }

        public void relazione(String nomeRelazione) {
            this.nomeRelazione = nomeRelazione;
        }

        
	public String nome(){
		return this.nome;
	}
		
	public Dominio tipo(){
		return this.tipo;
	}
	
		
	public boolean chiave(){
		return this.chiave;
	}

	public void valorePredefinito(String valore) throws EccezioneBaseDati {
            if(chiave && valore == null) 
                throw new EccezioneBaseDati("Valore Predefinito Attributo","valore predefinito chiave nullo!");
            this.valorePredefinito = valore;
	}
	
	public String valorePredefinito() {
		return this.valorePredefinito;
	}
	
        @Override
	public String toString(){
		return String.format("nome= %s\ntipo= %s\nvalore= %s\nchiave= %b\n", 
				this.nome,this.tipo.toString(),
				this.valorePredefinito,this.chiave);
	}
}//fine

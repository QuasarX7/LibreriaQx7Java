package it.quasar_x7.java.chimica;

import it.quasar_x7.java.utile.Tabella;
import it.quasar_x7.java.matematica.Interi;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;

/*******************************************************************************
 *
 * @author Domenico della Peruta
 ******************************************************************************/
public class EquazioneChimica {
    private ArrayList<Composto> reagenti, prodotti;
    private HashMap<Composto,Integer> coefficienti= 
            new HashMap<Composto,Integer> ();
    

    public EquazioneChimica() {
        reagenti=new ArrayList<Composto>();
        prodotti=new ArrayList<Composto>();
        
    }
    
    public EquazioneChimica(Composto[] reagenti, Composto[] prodotti) {
        this();
        this.reagenti.addAll(Arrays.asList(reagenti));
        this.prodotti.addAll(Arrays.asList(prodotti));
    }
    
    public void aggiungiProdotto(String formula){
        this.aggiungiProdotto(new Composto(formula));
    }
    
    public void aggiungiReagente(String formula){
        this.aggiungiReagente(new Composto(formula));
    }
    
    public void aggiungiProdotto(Composto prodotto){
        this.prodotti.add(prodotto);
    }
    
    public void aggiungiReagente(Composto reagente){
        this.reagenti.add(reagente);
    }
    
    /*********************************************
     * Verifica che ci sia gli stessi elementi tra
     * i prodotti e i reagenti
     * 
     * @return 
     **********************************************/
    public boolean verifica(){
        TreeSet<Elemento> listaProdotti = new TreeSet();
        for(Composto c :prodotti){
            listaProdotti.addAll(Arrays.asList(c.getElementi()));
        }
        TreeSet<Elemento> listaReagenti = new TreeSet();
        for(Composto c :reagenti){
            listaReagenti.addAll(Arrays.asList(c.getElementi()));
        }
        if(listaProdotti.equals(listaReagenti))
            return true;
        return false;
    }  
    
    private Elemento[] elementiProdotti(){
        TreeSet<Elemento> listaElementi = new TreeSet();
            for(Composto c :prodotti){
                listaElementi.addAll(Arrays.asList(c.getElementi()));
            }
        return listaElementi.toArray(new Elemento[listaElementi.size()]);        
    }
    
    private Elemento[] elementiReagenti(){
        TreeSet<Elemento> listaElementi = new TreeSet();
            for(Composto c :reagenti){
                listaElementi.addAll(Arrays.asList(c.getElementi()));
            }
        return listaElementi.toArray(new Elemento[listaElementi.size()]);        
    }
    
    private void inizializzaCoefficienti(){
        //inizializzazione a 1 dei coff. stecchiometrici
        for(int i=0;i<this.prodotti.size();i++){
            coefficienti.put(prodotti.get(i), 1);
        }
        for(int i=0;i<this.reagenti.size();i++){
            coefficienti.put(reagenti.get(i), 1);
        }
    }
    
    public HashMap<Composto,Integer> bilancia(){    
       /**
        if(this.verifica()){ 
            Elemento[] elementiReagenti = this.elementiReagenti();            
            String[] elementi =new String[elementiReagenti.length]; 
            
            for(int i=0;i<elementiReagenti.length;i++){
                elementi[i]=elementiReagenti[i].getNome();
            }
            
            Tabella tabAtomi = new Tabella(
                    "tabella numero atomi",//titolo
                    elementi,//colonne - nome elementi
                    new String[]{"prodotti","reagenti"}//righe
                    );
            
            //inizializzazione tabbella degli atomi
            tabAtomi=this.inizializzazioneTabAtomi(elementi, tabAtomi);
            
            this.inizializzaCoefficienti();
                    
            int i =0,e=0;
            while(!this.controllaTabAtomi(tabAtomi)){//se prodotti = reagenti
                   
                //controllo atomi (prodotti con reagenti)                
                while(i <tabAtomi.colonne()){     
                    //controlla colonna (singolo elemento atomico)
                    if(!tabAtomi.ugualiValori(i,0,i,1)){
                        String nomeAtomo = tabAtomi.colonna(i); 
                        int nAtomiP = (int)tabAtomi.get(i, 0);
                        int nAtomiR = (int)tabAtomi.get(i, 1);
                        
   System.out.println(tabAtomi);                
                       this.aggiornaCoefficienti(nomeAtomo,nAtomiP,nAtomiR);
                        //aggiorna valori tabella                    
                        tabAtomi=this.aggiurnaTabAtomi(tabAtomi);
   //riduci i coefficienti (se presente un MCD)
        //riduciCoefficienti();
                        break;                        
                    }
                    i++;
                    if(i==tabAtomi.colonne()){
                        i=0;
                    }
                }
                e++;
     System.out.println(this.toString());
                if(e > 100000){
                    System.out.println("errore nel bilanciamento!");
                    break;
                }
            }
            
        }**/
        return coefficienti; 
       
    }
    
    private Tabella inizializzazioneTabAtomi(String[] elementi,Tabella tabAtomi){
        TavolaPeriodica tavolaPeriodica = new TavolaPeriodica(); 
        for(int i=0;i<elementi.length;i++){
            for(int j=0;j<2;j++){
                Integer numAtomi = new Integer(0);
                if(j==0){//prodotti
                    for(Composto c :prodotti){
                        numAtomi=c.numeroElementi().get(
                                tavolaPeriodica.nomeElemento(elementi[i]));

                        if(numAtomi==null)
                            numAtomi = 0;

                        if(tabAtomi.get(i, j)!= null)
                            tabAtomi.set(i, j,((Integer)tabAtomi.get(i, j) + numAtomi));
                        else
                            tabAtomi.set(i, j, numAtomi);
                    }

                }else{//reagenti
                    for(Composto c :reagenti){
                        numAtomi=c.numeroElementi().get(
                                tavolaPeriodica.nomeElemento(elementi[i]));

                        if(numAtomi==null)
                            numAtomi = 0;

                        if(tabAtomi.get(i, j)!= null)
                            tabAtomi.set(i, j,((Integer)tabAtomi.get(i, j) + numAtomi));
                        else
                            tabAtomi.set(i, j, numAtomi);                            
                    }

                }

            }
                
        }
        return tabAtomi;            
    }
    
    private Tabella aggiurnaTabAtomi(Tabella tabAtomi){
       
        for(int x=0;x<tabAtomi.colonne();x++){
            String atomo = tabAtomi.colonna(x);
            //prodotti
            int p=0,r=0;
            for(Composto c: prodotti){    
                p+=c.numeroElemento(atomo) * coefficienti.get(c).intValue();
            }
            tabAtomi.set(atomo,"prodotti", p);
               
            for(Composto c: reagenti){    
                r+=c.numeroElemento(atomo) * coefficienti.get(c).intValue();              
            }
            tabAtomi.set(atomo,"reagenti", r);            
        }        
        return tabAtomi;            
    }

    private boolean controllaTabAtomi(Tabella tabAtomi) {
        final int PRODOTTI = 0;
        final int REAGENTI = 1;
        return tabAtomi.ugualiRoghe(PRODOTTI,REAGENTI);
    }

    private void aggiornaCoefficienti(String nomeAtomo,int nAtomiP,int nAtomiR) {
        Composto nAe = this.compostoElementoMin(nomeAtomo);
        int coeff;         
        if(nAtomiR>nAtomiP)
            coeff = (nAtomiR- (nAtomiP-nAe.numeroElemento(nomeAtomo)))/nAe.numeroElemento(nomeAtomo);
        else
            coeff = (nAtomiP- (nAtomiR-nAe.numeroElemento(nomeAtomo)))/nAe.numeroElemento(nomeAtomo); 
        
        for(Composto c : this.reagenti){
            if(c.equals(nAe)){             
                    this.coefficienti.put(c,coeff*this.coefficienti.get(c));                
            }
        }
          
        for(Composto c : this.prodotti){
            if(c.equals(nAe)){             
                    this.coefficienti.put(c,coeff*this.coefficienti.get(c));                
            }
        }
        
    }

    @Override
    public String toString() {                
       String s="";
       if(this.coefficienti.size()>0){
           boolean primo=true;
           for(Composto c :this.reagenti){
               int i = this.coefficienti.get(c);
               if(i>1)
                   if(primo){                
                       s+=i+" "+c.getFormula()+" ";
                       primo = false;
                   }else
                       s+="+ "+i+" "+c.getFormula()+" ";
               else{
                   if(primo){                
                       s+=c.getFormula()+" ";
                       primo = false;
                   }else
                       s+="+ "+c.getFormula()+" ";
               }
           }
           s += " -> ";
           primo = true;
           for(Composto c :this.prodotti){
               int i = this.coefficienti.get(c);
               if(i>1)
                   if(primo){                
                       s+=i+" "+c.getFormula()+" ";
                       primo = false;
                   }else
                       s+="+ "+i+" "+c.getFormula()+" ";
               else{
                   if(primo){                
                       s+=c.getFormula()+" ";
                       primo = false;
                   }else
                       s+="+ "+c.getFormula()+" ";
               }
           }
           return s;
       }
       return "formula non bilanciata!\n";
    }

    private void riduciCoefficienti() {
       int [] coeff = new int[prodotti.size()+reagenti.size()]; 
        int i=0;
       for(Composto c: this.prodotti){  
           coeff[i++] = coefficienti.get(c).intValue(); 
       }
        for(Composto c: this.reagenti){  
           coeff[i++] = coefficienti.get(c).intValue();           
        }
        
       int MCD=Interi.MCD(coeff);
       for(Composto c: this.prodotti){  
           coefficienti.put(c, coefficienti.get(c)/MCD);            
        }       
       for(Composto c: this.reagenti){  
           coefficienti.put(c, coefficienti.get(c)/MCD);           
        }
      
    }

    private Composto compostoElementoMin(String nomeAtomo) {
        int nA =0;
        Composto x = null;
        for(Composto c : this.reagenti){
            if(c.contiene(nomeAtomo)){
                int coeff= this.coefficienti.get(c);
                if(nA == 0){//la mima volta
                    nA=coeff*c.numeroElemento(nomeAtomo);
                    x = c;
                }else if(nA>0){//prendi l'elemento minore
                    if(nA > coeff*c.numeroElemento(nomeAtomo)){
                        nA = coeff*c.numeroElemento(nomeAtomo);
                        x=c;
                    }
                }else{
                    return null;
                }
            }
        }
        for(Composto c : this.prodotti){
            if(c.contiene(nomeAtomo)){
                int coeff= this.coefficienti.get(c);
                if(nA>0){//prendi l'elemento minore
                    if(nA > coeff*c.numeroElemento(nomeAtomo)){
                        nA = coeff*c.numeroElemento(nomeAtomo);
                        x=c;
                    }
                }else{
                    return null;
                }
            }
        }
        return x;
    }

    
    
}

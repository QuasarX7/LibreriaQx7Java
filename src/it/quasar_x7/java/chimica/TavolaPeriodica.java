package it.quasar_x7.java.chimica;

import it.quasar_x7.java.utile.Carattere;
import it.quasar_x7.java.utile.Testo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/*******************************************************************************
 *
 * @author Domenico della Peruta
 ******************************************************************************/

public class TavolaPeriodica {
    
                   
    static public Elemento H = new Elemento(
            "H",        //simbolo
            "idrogeno", //nome
            1,          // numero atomico
            1,          //periodo
            "IA",       //gruppo
            (float)1.0079, //peso atomico
            new int[] {1}  //valenza
            ); 
    
    static public Elemento He = new Elemento(
            "He",        //simbolo
            "elio", //nome
            2,          // numero atomico
            1,          //periodo
            "VIIIA",       //gruppo
            (float)4.0026, //peso atomico
            null  //valenza
            );
    
    static public Elemento Li = new Elemento(
            "Li",        //simbolo
            "litio", //nome
            3,          // numero atomico
            2,          //periodo
            "IA",       //gruppo
            (float)6.941, //peso atomico
            new int[] {1}  //valenza
            );
    
    static public Elemento Be = new Elemento(
            "Be",        //simbolo
            "berillio", //nome
            4,          // numero atomico
            2,          //periodo
            "IIA",       //gruppo
            (float)9.0122, //peso atomico
            new int[] {2}  //valenza
            );
    
    static public Elemento B = new Elemento(
            "B",        //simbolo
            "boro", //nome
            5,          // numero atomico
            2,          //periodo
            "IIIA",       //gruppo
            (float)10.811, //peso atomico
            new int[] {3}  //valenza
            );
    
    static public Elemento C = new Elemento(
            "C",        //simbolo
            "carbonio", //nome
            6,          // numero atomico
            2,          //periodo
            "IVA",       //gruppo
            (float)12.011, //peso atomico
            new int[] {4,2,-4}  //valenza
            );
    
    static public Elemento N = new Elemento(
            "N",        //simbolo
            "azoto", //nome
            7,          // numero atomico
            2,          //periodo
            "VA",       //gruppo
            (float)14.007, //peso atomico
            new int[] {5,4,3,2,-3}  //valenza
            );
    
    static public Elemento O = new Elemento(
            "O",        //simbolo
            "ossigeno", //nome
            8,          // numero atomico
            2,          //periodo
            "VIA",       //gruppo
            (float)15.999, //peso atomico
            new int[] {-2}  //valenza
            );
    
    static public Elemento F = new Elemento(
            "F",        //simbolo
            "fluoro", //nome
            9,          // numero atomico
            2,          //periodo
            "VIIA",       //gruppo
            (float)18.998, //peso atomico
            new int[] {-1}  //valenza
            );
    
    static public Elemento Ne = new Elemento(
            "Ne",        //simbolo            
            "neon", //nome
            10,          // numero atomico
            2,          //periodo
            "VIIIA",       //gruppo
            (float)20.180, //peso atomico
            null  //valenza
            );
    
    static public Elemento Na = new Elemento(
            "Na",        //simbolo
            "sodio", //nome
            11,          // numero atomico
            3,          //periodo
            "IA",       //gruppo
            (float)22.99, //peso atomico
            new int[]{1}  //valenza
            );
    
    static public Elemento Mg = new Elemento(
            "Mg",        //simbolo
            "magnesio", //nome
            12,          // numero atomico
            3,          //periodo
            "IIA",       //gruppo
            (float)24.305, //peso atomico
            new int[]{2}  //valenza
            );
    
    static public Elemento Al = new Elemento(
            "Al",        //simbolo
            "alluminio", //nome
            13,          // numero atomico
            3,          //periodo
            "IIIA",       //gruppo
            (float)26.982, //peso atomico
            new int[]{3}  //valenza
            );
    
    static public Elemento Si = new Elemento(
            "Si",        //simbolo
            "silicio", //nome
            14,          // numero atomico
            3,          //periodo
            "IVA",       //gruppo
            (float)28.086, //peso atomico
            new int[]{4}  //valenza
            );
    
    
    static public Elemento P = new Elemento(
            "P",        //simbolo
            "fosforo", //nome
            15,          // numero atomico
            3,          //periodo
            "VA",       //gruppo
            (float)30.974, //peso atomico
            new int[]{5,4,3,-3}  //valenza
            );
    
    static public Elemento S = new Elemento(
            "S",        //simbolo
            "zolfo", //nome
            16,          // numero atomico
            3,          //periodo
            "VIA",       //gruppo
            (float)32.065, //peso atomico
            new int[]{6,4,2,-2}  //valenza
            );
    
    static public Elemento Cl = new Elemento(
            "Cl",        //simbolo
            "cluoro", //nome
            17,          // numero atomico
            3,          //periodo
            "VIIA",       //gruppo
            (float)35.453, //peso atomico
            new int[]{7,5,3,1,-1}  //valenza
            );
    
    
    static public Elemento Ar = new Elemento(
            "Ar",        //simbolo
            "argo", //nome
            18,          // numero atomico
            3,          //periodo
            "VIIIA",       //gruppo
            (float)39.948, //peso atomico
            null  //valenza
            );
    
    
    static public Elemento K = new Elemento(
            "K",        //simbolo
            "potassio", //nome
            19,          // numero atomico
            4,          //periodo
            "IA",       //gruppo
            (float)39.098, //peso atomico
            new int[]{1}  //valenza
            );
    
    static public Elemento Ca = new Elemento(
            "Ca",        //simbolo
            "calcio", //nome
            20,          // numero atomico
            4,          //periodo
            "IIA",       //gruppo
            (float)40.078, //peso atomico
            new int[]{2}  //valenza
            );
    
    static public Elemento Sc = new Elemento(
            "Sc",        //simbolo
            "scandio", //nome
            21,          // numero atomico
            4,          //periodo
            "IIIB",       //gruppo
            (float)44.956, //peso atomico
            new int[]{3}  //valenza
            );
    
    static public Elemento Ti = new Elemento(
            "Ti",        //simbolo
            "titanio", //nome
            22,          // numero atomico
            4,          //periodo
            "IVB",       //gruppo
            (float)47.067, //peso atomico
            new int[]{4,3}  //valenza
            );
    
    static public Elemento V = new Elemento(
            "V",        //simbolo
            "vanadio", //nome
            23,          // numero atomico
            4,          //periodo
            "VB",       //gruppo
            (float)50.942, //peso atomico
            new int[]{5,4,3,2}  //valenza
            );
    
    static public Elemento Cr = new Elemento(
            "Cr",        //simbolo
            "cromo", //nome
            24,          // numero atomico
            4,          //periodo
            "VIB",       //gruppo
            (float)51.996, //peso atomico
            new int[]{6,3,2}  //valenza
            );
    
    static public Elemento Mn = new Elemento(
            "Mn",        //simbolo
            "mancanese", //nome
            25,          // numero atomico
            4,          //periodo
            "VIIB",       //gruppo
            (float)54.938, //peso atomico
            new int[]{7,6,4,2,3}  //valenza
            );
    
    static public Elemento Fe = new Elemento(
            "Fe",        //simbolo
            "ferro", //nome
            26,          // numero atomico
            4,          //periodo
            "VIIIB",       //gruppo
            (float)55.845, //peso atomico
            new int[]{2,3}  //valenza
            );
    //.... 
    
    private ArrayList<Elemento> elementi = null;

    public TavolaPeriodica() {
        elementi = new ArrayList<Elemento>();
        elementi.add(H);
        elementi.add(He);
        elementi.add(Be);
        elementi.add(B);
        elementi.add(C);
        elementi.add(N);
        elementi.add(O);
        elementi.add(F);
        elementi.add(Ne);
        elementi.add(Na);
        elementi.add(Mg);
        elementi.add(Al);
        elementi.add(Si);
        elementi.add(P);
        elementi.add(S);        
        elementi.add(Cl);
        elementi.add(Ar);
        elementi.add(K);
        elementi.add(Ca);
        elementi.add(Sc);
        elementi.add(Ti);        
        elementi.add(V);
        elementi.add(Cr);
        elementi.add(Mn);
        elementi.add(Fe);
        //....
        
    }
    
    /***************************************************
     * Permette di determinare quanti elementi sono
     * presenti nel composto
     * 
     * @param formula composto
     * @return elemento e numero (tramite oggetto HashMap)
     ***************************************************/
    public HashMap<Elemento,Integer> numeroElemtiComposto(String formula){
        HashMap<Elemento,Integer> lista = new HashMap<Elemento,Integer>();
        
        Elemento[] _elementi = this.elementiComposto(formula);        
        Integer numero;       
        
        for(int i=0;i<_elementi.length;i++){
            if((numero=lista.get(_elementi[i]))!= null){
                lista.put(_elementi[i], numero+1 );
            }else{
               lista.put(_elementi[i], 1 ); 
            }
        }        
        return lista;
    }
    
    public float pesoMolecolare(String formula){
        Elemento[] _elementi = this.elementiComposto(formula);
        float pm = 0;
        for(int i=0;i<_elementi.length;i++){                
                pm +=_elementi[i].getPesoAtomico();                             
            }
        return pm;
    }
    
    /*****************************************************************
     * Permette di estrarre di elementi presenti in una formula
     * 
     * @param formula stringa contenente la formula bruta del composto
     * @return restituisce tugli gli elementi atomici presenti nella
     * formula
     ****************************************************************/
    public Elemento[] elementiComposto(String formula){
        ArrayList<Elemento> _elementi = new ArrayList<Elemento>();
        formula += "@";
        String simbolo = ""; //simbolo chimico
        String numero = "";
        String composto = ""; //eventuali simboli contenuti in parentesi
        boolean parentesi=false; //apertura parentesi        
        boolean primo=true; //primo carattere del simboloElemento
        Elemento e;
                
        for(int i=0; i<formula.length();i++){    
            //acquisisci carattere
            Carattere c = new Carattere(formula.charAt(i));
            
            if(parentesi){//se è stata aperta una parentesi                
                if(c.uguare(')')){
                        parentesi = false;
                }else{
                    composto += c.toString();
                }
            }else{ //se non è stata aperta una parentesi
                   // si fa l'analesi degli elementi chimici
                
                if(c.numero()){ 
                    if(simbolo.length()>0){//se non è il primo elemento
                        numero +=c.toString();                    
                    }else{
                        System.out.println("formula '"+formula+"' sbagliata, non sono ammessi numeri all'inizio della formula");
                        return null;
                    }
                    
                }else if(c.letteraMaiuscola()){
                     if(simbolo.length()>0){//se non è il primo elemento (dopo la parentesi o della formula) 
                        //acquisisci semplici elementi
                       if(composto.length()==0){       
                            //acquisizione del Simbolo precedente
                           e =this.simboloElemento(simbolo);
                           if(e != null){
                               int n = 1;
                               if(numero.length()>0){
                                   n=Integer.valueOf(numero);
                                   numero = "";
                               }                       
                               for(int j=0;j<n;j++){
                                   _elementi.add(e);
                               }
                           }else{
                               System.out.println("formula '"+formula+"' sbagliata, elemento ("+simbolo+") inesistente");
                               return null;
                           }
                        }else{
                            //+++++ acquisisci aggregato parantesi+++++
                            Elemento[] sub=this.elementiComposto(composto);
                            if(sub !=null){
                                int n = 1;
                                if(numero.length()>0){
                                    n=Integer.valueOf(numero);
                                    numero = "";
                                }
                                for(int k=0;k<n;k++)
                                    _elementi.addAll(Arrays.asList(sub));
                                //inizializzazione composto
                                composto="";                                
                            }else{
                                System.out.println("formula '"+formula+"' sbagliata, valori nelle parentesi non corretti");
                                return null;
                            }
                            //++++++++++++++++++++++++++++
                        }  
                     }
                    //inizializzazione del nuovo simboloElemento
                    simbolo = ""+ c;
                    primo=false; //non è più il primo caraterre del simboloElemento
                        
                    
                }else if(c.letteraMinuscola()){                
                    if(!primo){
                       simbolo += c;
                       primo = true;
                    }else{
                       System.out.println("formula '"+formula+"' sbagliata, elemento con lettera minuscola");
                       return null;
                   }                

                }else{ // se non è un carattere o un numero

                    /* carattere di fine*/
                    if(c.uguare('@')){ 
                        //-----------acquisisci simboloElemento---------------
                        if(composto.length()==0){
                            e =this.simboloElemento(simbolo);
                           if(e != null){
                               int n = 1;
                               if(numero.length()>0){
                                   n=Integer.valueOf(numero);
                                   numero = "";
                               }
                               for(int j=0;j<n;j++){
                                   _elementi.add(e);
                               }
                           }else{
                               System.out.println("formula '"+formula+"' sbagliata, elemento ("+simbolo+") inesistente");
                               return null;
                           }                            
                            //-----------------------------------
                           break;

                        }else{                               
                            //+++++ acquisisci aggregato parantesi+++++
                            Elemento[] sub=this.elementiComposto(composto);
                            if(sub !=null){
                                int n = 1;
                                if(numero.length()>0){
                                    n=Integer.valueOf(numero);
                                    numero = "";
                                }
                                for(int k=0;k<n;k++)
                                    _elementi.addAll(Arrays.asList(sub));
                                //inizializzazione composto
                                composto="";                                
                            }else{
                                System.out.println("formula '"+formula+"' sbagliata, valori nelle parentesi non corretti");
                                return null;
                            }
                            //++++++++++++++++++++++++++++
                        }

                        /* aperta parentesi*/
                    }else if(c.uguare('(')){
                        //-----------acquisisci simboloElemento---------------                         
                        
                        e =this.simboloElemento(simbolo);
                        if(e != null){
                            int n = 1;
                            if(numero.length()>0){
                               n=Integer.valueOf(numero);
                               numero = "";
                            }
                            for(int j=0;j<n;j++){
                                  _elementi.add(e);
                            }
                        }else{
                            System.out.println("formula '"+formula+"' sbagliata, elemento ("+simbolo+") inesistente");
                            return null;
                        }                            
                        //-----------------------------------
                             //inizializza
                        parentesi = true;
                        

                        /*chiusura parentesi*/                    
                    }else if(c.uguare(')')){                        
                        System.out.println("formula '"+formula+"' sbagliata, presenza di una perantesi in più");
                       return null; 

                    }else{
                       System.out.println("formula '"+formula+"' sbagliata, presenza di caratteri non chimici");
                       return null; 
                    }               
                }
            }            
        }//fine ciclo analesi carattere
        
        return _elementi.toArray(new Elemento[_elementi.size()]);
    } 
    
    
      public Elemento numeroAtomicoElemento(int n){        
        for(Elemento e: this.elementi){
            if(e.getNumeroAtomico()== n)
                return e;
        }
        return null;
    }  
    
    public Elemento nomeElemento(String nome){        
        for(Elemento e: this.elementi){
            if(e.getNome()!= null)
                if(nome.compareTo(e.getNome())==0){                    
                    return e;
                }
        }
        return null;
    }   
    
    public Elemento simboloElemento(String simbolo){        
        for(Elemento e: this.elementi){
            if(e.getSimbolo()!= null)
                if(simbolo.compareTo(e.getSimbolo())==0){                    
                    return e;
                }
        }
        return null;
    }

    public String stampaElementi(String string) {
        HashMap<Elemento, Integer> composto = this.numeroElemtiComposto(string);
        String s = "";
        Elemento e;
        for(int i=0;i<elementi.size();i++){
            if(composto.containsKey((e=elementi.get(i)))){
                s += e.getNome()+"("+e.getSimbolo() +") nr. "+composto.get(e) +"\n";
            }
            
        }
        return s;
    }

    public float moli(double grammi, String formula) {
        float pm = this.pesoMolecolare(formula);
        return (float) (grammi/pm);
    }

    
}

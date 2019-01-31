package it.quasar_x7.java.chimica;

import java.util.HashMap;

/*******************************************************************************
 *
 * @author Domenico della Peruta
 ******************************************************************************/
public class Composto {
    private String nome ="";
    private String formula;
    private Elemento[] elementi;

    public Composto(String formula) {
        this.formula = formula;
        this.elementi = new TavolaPeriodica().elementiComposto(formula);
    }

    public Composto(String nome, String formula) {
        this.nome = nome;
        this.formula = formula;
        this.elementi = new TavolaPeriodica().elementiComposto(formula);
    }

    public Elemento[] getElementi() {
        return elementi;
    }

    public String getFormula() {
        return formula;
    }

    public String getNome() {
        return nome;
    }

    public int numeroElemento(String elemento){
        TavolaPeriodica t = new TavolaPeriodica();     
        Integer n =this.numeroElementi().get(t.nomeElemento(elemento));
        if(n!=null)
            return n.intValue();
        return 0;
    }
    
    public HashMap<Elemento,Integer> numeroElementi(){
        TavolaPeriodica t = new TavolaPeriodica();       
        return t.numeroElemtiComposto(formula);
    }
    
    public boolean contiene(String nomeElemento){
        return this.contiene(new Elemento("",nomeElemento,0,0,"",0,null));
    }
    
    public boolean contiene(Elemento elemento){
        for(int i=0;i<this.elementi.length;i++){
            if(this.elementi[i].getNome().compareTo(elemento.getNome())==0){
                return true;
            }
        }
        return false;
    }
            
    @Override
    public String toString() {
        return nome + "[ " + formula + " ]";
    }
    
    
    
}

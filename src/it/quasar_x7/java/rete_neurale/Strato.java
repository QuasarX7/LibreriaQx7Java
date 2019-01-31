package it.quasar_x7.java.rete_neurale;

import it.quasar_x7.java.XML.Elemento;

/*******************************************************************************
 *
 * @author Domenico della Peruta
 ******************************************************************************/
public class Strato {    
    private Neurone[] neuroni;


    public Strato(){
        this.neuroni=null;
    }
    
    /****************************************************
     * Costrutore di uno strato di num_neuroni
     *
     * @param ingressi_neurone numero ingressi per ogni
     * neurone
     * @param num_neuroni numero num_neuroni
     ****************************************************/
    public Strato(int ingressi_neurone, int num_neuroni){
        this.neuroni = new Neurone[num_neuroni];
        for ( int i = 0; i < this.neuroni.length; i++ )
            this.neuroni[i]= new Neurone(ingressi_neurone);
    }


    public Strato(Strato strato){
        this.neuroni = new Neurone[strato.neuroni.length];
        for ( int i = 0; i < strato.neuroni.length; i++ ){
            this.neuroni[i] = new Neurone(strato.neuroni[i]);
        }
    }

    /***************************************************
     * Costruttore di uno strato di neuroni
     *
     * @param pesi tutti i pesi dei singoli neuroni dello
     * strato, rappresentato da una matrice di pesi per
     * neuroni
     * @param num_neuroni numero neuroni
     ***************************************************/
    public Strato(double[][] pesi, int num_neuroni){
        neuroni = new Neurone[num_neuroni];
        for ( int n = 0; n < neuroni.length; n++ )
            neuroni[n] = new Neurone(pesi[n]);
    }

    public double[] leggiPesi(int n){
        return neuroni[n].leggiPesi();
    }


    public void assegnaPesi( int n, double[] p ) throws ErroreReteNeurale{
        neuroni[n].assegnaPesi(p);
    }

    /*********************************************************
     * Legge il peso di un singolo neurone n di un ingresso j
     * 
     * @param n
     * @param j
     * @return
     *********************************************************/
    public double leggiPeso( int n, int j){
        return neuroni[n].leggiPeso( j );
    }

    public void attivazione(double[] ingr ) throws ErroreReteNeurale{
        for ( int n = 0; n < neuroni.length; n++ )
            neuroni[n].attivazione(ingr);
    }

    public double[] uscita(){
        double[] result = new double[neuroni.length];
        for ( int n = 0; n < neuroni.length; n++ )
            result[n] = neuroni[n].uscita();
        return result;
    }

    public void incrementaPesi(Neurone[] n) throws ErroreReteNeurale{
        for ( int i = 0; i < neuroni.length; i++ )
            this.neuroni[i].incrementaPesi(n[i].leggiPesi());
    }

    /********************************
     *
     * @return numero di ingressi per
     * neurone
     ********************************/
    public int quantiIngressi(){
        return neuroni[0].quantiIngressi();
    }


    public void pesiCasuali(){
        double max = 1 / Math.sqrt(quantiIngressi());
        for (int n = 0; n < neuroni.length; n++)
            neuroni[n].pesiCasuali( max );
    }

    public void pesiZero(){
        for ( int n = 0; n < neuroni.length; n++ )
            neuroni[n].pesiZero();
    }


    public String stampa(){
        String testo="";
        for ( int n = 0; n < neuroni.length; n++ ){
            testo+= "(neurone "+n+")"+neuroni[n].stampa()+"\n";
        }
        return testo;
    }

    public Elemento serializza(int id){
        Elemento strato = new Elemento("strato_"+id);
        for(int i=0;i<neuroni.length;i++){
            strato.addElemento(neuroni[i].serializza(i));
        }
        return strato;
    }

    public void deserializza(Elemento strato) throws ErroreReteNeurale{
        neuroni = new Neurone[strato.numeroElementi()];
        for (int i = 0; i < neuroni.length; i++ ){
            neuroni[i] = new Neurone();
            neuroni[i].deserializza(strato.getElemento(i));
        }
    }

    public int quantiNeuroni() {
        return this.neuroni.length;
    }

    public Neurone[] leggiNeuroni() {
        return this.neuroni;
    }

}





package it.quasar_x7.java.matematica;

/*******************************************************************************
 *
 * @author Domenico della Peruta
 ******************************************************************************/
public class Interi {
    
    /*****************************************
     * Minimo Comune Multiplo tra due numeri
     * 
     * @param a
     * @param b
     * @return 
     ******************************************/
    public static int  mcm (int a, int b){
        int x,y,mcm;
        x = a; y = b;
        while (x!=y)
            if (x<y)
                x += a;
            else
                y += b;
        mcm = x;
        return mcm;
    }
    
    /*****************************************************
     * Massimo  minimo comune multiplo
     * 
     * @param a
     * @param b
     * @return 
     *****************************************************/
    public static int MCD(int a, int b){
        if (b == 0)
            return a;
        else 
            return MCD(b, a % b);
        
    }
    
    public static int MCD(int[] v){
        if(v.length>1){
            int x = v[0];
            for(int i=0;i<v.length-1;i++){
                x = MCD(x,v[i+1]);
            }
            return x;
        }
        return -1;
    }
    
    public static int mcm(int[] v){
        if(v.length>1){
            int x = v[0];
            for(int i=0;i<v.length-1;i++){
                x = mcm(x,v[i+1]);
            }
            return x;
        }
        return -1;
    }
}

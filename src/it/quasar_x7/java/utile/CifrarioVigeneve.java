package it.quasar_x7.java.utile;
/**
 * 
 * Classe che implementa l'interfaccia Sicurezza, e permete di implementare una semplice tecnica
 * di crittografia a chiave simmetrica nota come cifrario di Vigeneve o cifrario polialfabetico.
 * 
 * Data di realizzazione e di 1� test (black box) 02/06/2008. 
 * 
 *
 */
public class CifrarioVigeneve implements Crittografia {
//=================================================================================================
	private char[] _chiave = null;	
	private char[] _alfabeto = null;
	private char[][] _cifrario = null;
	
//=================================================================================================
	public CifrarioVigeneve(String chiave){
		this.chiave(chiave);
		_alfabeto = new char[]{
				'a','b','c','d','e','f','g','h','i','j','k','l','m',
				'n','o','p','q','r','s','t','u','v','w','x','y','z'};
		
		_cifrario = new char[_chiave.length][_alfabeto.length];	
		
		this.inizializzaCifrario();		
	}
//=================================================================================================	
	private void chiave(String key){
		_chiave = new char[key.length()];
		_chiave = key.toCharArray();
		this.correzioneChiave();
		
	}
		
//------------------------------------------------------------------------------------------------	
	private void inizializzaCifrario(){
				
		int j = 0;//puntatore a _chave		
		for(char c: _chiave){
			if(j == _chiave.length)
				break;	
			
			//individuazione del cifrario in funzione del'indice della chiave
			int offset = 0;
			for(char k: _alfabeto){				
				if(k == c)
					break; //trova l'offset 
				offset++;
			}
			
			for(int h= 0; h < _alfabeto.length; h++){
				if(h+offset < _alfabeto.length){
					_cifrario[j][h] = _alfabeto[h+offset];
				}
				else					
					_cifrario[j][h] = _alfabeto[(h+offset)-_alfabeto.length];
					
			}
			j++;
		}
	}
//-------------------------------------------------------------------------------------------------	
	public String cifra(String testoInChiaro) {
		String testoCifrato = "";
		
		int i = 0;//indice testo		
		int j = 0;//indice chiave
		while( i < testoInChiaro.length()){//scorri i singoli caratteri del testo				
			if(j < this._chiave.length){
				int offset =  testoInChiaro.charAt(i)- 'a';
				
				if(offset < _alfabeto.length && offset >= 0){
					testoCifrato +=_cifrario[j][offset];
				}
				else
					testoCifrato +=testoInChiaro.charAt(i); //carattere non alfabetico o Maiuscolo
				i++;
				j++;
			}
			else
				j=0;
			
		}		
		return testoCifrato;		
	}
//--------------------------------------------------------------------------------------------------
	public String decifra(String testoCifrato) {
		String testoInChiaro = "";
		
		int i = 0;//indice testo		
		int j = 0;//indice chiave
		while( i < testoCifrato.length()){//scorri i singoli caratteri del testo				
			if(j < _chiave.length){
				
				int k=0;
				while(k < _alfabeto.length){
					if(_cifrario[j][k] == testoCifrato.charAt(i)){
						testoInChiaro += _alfabeto[k];
						break;
					}
					k++;
				}
				if(k == _alfabeto.length)//valore non trovato nella matrica
					testoInChiaro += testoCifrato.charAt(i);
				
				j++;
				i++;
			}
			else
				j=0; 
			
		}		
		return testoInChiaro;	
	}
//-------------------------------------------------------------------------------------------------	
	public void stampaMatriceCifrario(){
		
		System.out.print("\nchiave: ");System.out.println(this._chiave);
		
		System.out.println("\nmatrice dei cifrari:");
		for(int j=0; j <this._chiave.length;j++){
			for(int i=0;i<_alfabeto.length;i++){
				System.out.print(this._cifrario[j][i]);
			}
			System.out.print("\n");
		}
	}
//-------------------------------------------------------------------------------------------------
	/**
	 *  funzione che sostituisce nella chiave tutti quei caratteri che siano diversi dalle
	 *  lettere minuscole dall'alfabeto con la lettera 'x'.  
	 */
	private void correzioneChiave(){
		String nuovaChiave="";
		for(int i=0;i< _chiave.length;i++){
			if((_chiave[i]-'a')>=0 && (_chiave[i]-'z')<=0)
				nuovaChiave += _chiave[i];
			else
				nuovaChiave += 'x';
		}
		_chiave = nuovaChiave.toCharArray();
	}
//--------------------------------------------------------------------------------------------------
}

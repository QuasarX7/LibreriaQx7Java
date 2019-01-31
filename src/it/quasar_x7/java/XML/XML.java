package it.quasar_x7.java.XML;


import it.quasar_x7.java.utile.Testo;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/*******************************************************************************
 *
 * @author Domenico della Peruta
 ******************************************************************************/

class Tag{
    private String testo;
    private String nome;
    private ArrayList<String> attributi;
    private String contenuto;

    private int inizio,fine;//contenuto del tag

    Tag(String xml) {
        testo = xml;
        inizio=fine=0;
        attributi = new ArrayList<String>();
    }

    

    ArrayList<String> getAttributi() {
        return attributi;
    }

    void setAttributi(ArrayList<String> attributi) {
        this.attributi = attributi;
    }


    String getContenuto() {
        return contenuto;
    }

    void setContenuto(String contenuto) {
        this.contenuto = contenuto;
    }

    String getNome() {
        return nome;
    }

    void setNome(String nome) {
        this.nome = nome;
    }

    String stampa(){
        String _attributi ="";
        if(this.attributi.size()>0){
            for(String a:this.attributi){
                _attributi +=" "+a;
            }
        }
        if(this.contenuto==null){
            return "<"+this.nome+_attributi+"/>";
        }else{
            return "<"+this.nome+_attributi+">"+this.contenuto+"</"+this.nome+">";
        }
    }


    void filtraDTD() throws ErroreXML{

            Testo _testo = new Testo(testo);
            int id=_testo.cercaSottoStringa("<!DOCTYPE");
            int inizioDTD=id-9;

            if(id != -1){

                String testoFiltrato = testo.substring(0, inizioDTD);
                id=new Testo(testo.substring(id)).cercaCarattere('>')+id+1;
                if(id != -1){

                    testoFiltrato += testo.substring(id, testo.length());
                    testo=testoFiltrato;
     
                }else{
                    throw new ErroreXML("dichiarazione DTD non chiusa correttamente"
                            + "\n_______________"
                            + "\n"+testo.substring(inizioDTD, testo.length())
                            + "\n_______________");
                }

            }
    }

    void filtraCommento() throws ErroreXML{
        do{
            Testo _testo = new Testo(testo);
            int id=_testo.cercaSottoStringa("<!--");
            int inizioC=id-4;
            if(id != -1){
                String prima = testo.substring(0, inizioC);
                id=_testo.cercaSottoStringa("-->");            
                if(id != -1){
                    String dopo = testo.substring(id, testo.length());
                    testo=prima+dopo;
                }else{
                    throw new ErroreXML("commento non chiuso correttamente"
                            + "\n_______________"
                            + "\n"+testo.substring(inizioC, testo.length())
                            + "\n_______________");
                }
               
            }else{
                break;
            }
        }while(true);
    }

    boolean acquisisciContenuto(){
        Testo _testo = new Testo(testo);
         contenuto=_testo.sottoStringa(inizio, fine);
         if(contenuto.compareTo("")==0){
             return false;
         }
         return true;
    }

    void verificaIntestazione() throws ErroreXML{

        Testo _testo = new Testo(testo);
        String _tag = _testo.sottoStringa('<', '>');
        if(_tag.length()>0){
            String __tag = _testo.sottoStringa('?', '?');
            if(__tag.length()>0){
                int id = _testo.cercaSottoStringa("?xml");
                if(id != -1){
                    id = _testo.cercaSottoStringa("version");
                    if(id != -1){
                        id = _testo.cercaCarattere('>');
                        testo = testo.substring(id+1, testo.length());
                        return ;
                    }
                }
            }
        }
        throw new ErroreXML("errore intestazione XML"
                + "\n___________________"
                + "\n"+_tag
                + "\n___________________");
    }


    Tag verificaTagSuccessivo(){

        Testo _testo = new Testo(testo);
        int id = _testo.cercaSottoStringa("</"+nome+">");

        if(id != -1){
            if(id < testo.length()-1){ // se vi è del testo dopo il tag
                return new Tag(testo.substring(id, testo.length()));
            }
        }else{
            id = _testo.cercaSottoStringa("/>");
            if(id != -1){
                if(id < testo.length()-1){ // se vi è del testo dopo il tag
                    return new Tag(testo.substring(id, testo.length()));
                }
            }
        }
        return null;
    }

    boolean tagVuoto() throws ErroreXML{
        Testo _testo = new Testo(testo);
        String tagInizio = _testo.sottoStringa('<', '>');
        _testo = new Testo(tagInizio);
        if(_testo.cercaSottoStringa("/>")>=0){
            if(this.nome.charAt(nome.length()-1)=='/'){ // correzione
                nome =nome.substring(0, nome.length()-1);
            }
            return true;
        }
        return false;
    }

    void acquisizioneChiusura() throws ErroreXML{
        if(nome!=null){
            Testo _testo = new Testo(testo);
            int id = _testo.cercaSottoStringa("</"+nome+">");

            if(id != -1){
                fine = id-nome.length()-3;
                
            }else{
                throw new ErroreXML(
                        "tag <"+nome+"> non chiuso correttamente "
                        + "\n_____________________"
                        + "\n"+_testo.getTesto()
                        + "\n_____________________");
            }
        }else{
            throw new ErroreXML("nome tag nullo");
        }
        
    }

    boolean esistenzaTag(){
        Testo _testo = new Testo(testo);
        if(_testo.cercaCarattere('<')>=0){
             if(_testo.cercaCarattere('>')>=0){
                 return true;
             }
        }
        return false;
    }

    void acquisizioneApertura() throws ErroreXML{
        Testo _testo = new Testo(testo);
        String _tag = _testo.sottoStringa('<', '>');
        if(_tag.length()>0)
            if(_tag.charAt(1)==' '){
                throw new ErroreXML("spazio vuoto inizio tag "
                        + "\n________________"
                        + "\n"+_tag
                        + "\n________________");
            }

            String _nome="",_attributo="";
            char c;
            boolean primo=true,virgolette1=false,uguale=false;
            for(int i=1;i<_tag.length();i++){

                if(primo){//analesi del nome tag
                    if((c=_tag.charAt(i))!=' ' && (c=_tag.charAt(i))!='>'){
                        _nome+=c;
                    }else{
                        this.nome=_nome;
                        if(c == '>'){
                            break;
                        }
                        primo=false;
                    }

                }else{//analesi degli eventuali attributi
                    if((c=_tag.charAt(i))!='"' || !(virgolette1 && uguale)){
                        if(c== '='){
                            uguale=true;//trovato un [=]
                        }
                        if(c == '"'){
                            virgolette1=true;//trovato una ["]
                        }

                        _attributo+=c;

                    }else{

                        //aggiungi se trovati un [=] e due ["]
                        this.attributi.add(_attributo+'"');
                        _attributo="";
                        uguale=virgolette1=false;
                    }
                }

            }
            if(!primo){//presenza di attributi

                /*attributi non aggiunti*/

                if(uguale || virgolette1){
                    throw new ErroreXML("formattazione attributi non valida "
                            + "\n________________"
                            + "\n"+_tag
                            + "\n________________");

                }
                if(attributi.isEmpty()){
                    if((new Testo(_tag).cercaSottoStringa("/>"))==-1){
                        throw new ErroreXML(
                                "presenza di spazio vuoto alla fine del nome "
                                + "\n_______________"
                                + "\n"+_tag
                                + "\n_______________");
                    }
                }
            }
            this.inizio = _testo.cercaCarattere('>')+1;

    }
}

public class XML {

    private Elemento root;

    public XML(){
    }


    public Elemento getXML() {
        return root;
    }


    public void setXML(Elemento root){
        this.root = root;
    }

    /*****************************************
     * Crea un oggetto XML da un file xml
     *
     *
     *****************************************/
    public void deserializza(String file){
        try {
            RandomAccessFile f = new RandomAccessFile(file, "r");
            int c;
            String xml = "";
            while((c = f.read())!=-1){
                xml += (char)c;
            }
            f.close();
            Tag corpoXML = new Tag(xml);

            corpoXML.filtraCommento();//elimina commenti
            corpoXML.filtraDTD();
            corpoXML.verificaIntestazione();
            corpoXML.acquisizioneApertura();
            corpoXML.acquisizioneChiusura();

            this.root = new Elemento(corpoXML.getNome());
            this.root.attributi =caricaAttributi(corpoXML.getAttributi());

            if(corpoXML.acquisisciContenuto()){//se esiste un corpo

                Tag tag = new Tag(corpoXML.getContenuto());

                tag.acquisizioneApertura();//tag nidificati
                ArrayList<Elemento> tags=
                        this.tagMultipli(corpoXML.getContenuto());
                if(tags != null){
                    root.setElementi(tags);
                }
            }else{//non vi sono tag nidificati nel corpo
                this.root.setValore(corpoXML.getContenuto());
            }
            
 

        } catch (ErroreXML ex) {
            javax.swing.JOptionPane.showMessageDialog(
                    null,
                    "Errore lettura file '"+file+"' :"+ex.getMessage(),
                    "errore", javax.swing.JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (IOException ex) {
            javax.swing.JOptionPane.showMessageDialog(
                    null,
                    "Errore lettura file '"+file+"' :"+ex.getMessage(),
                    "errore", javax.swing.JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    /*****************************************
     * Permette di creare un file xml
     *
     * @param file
     *****************************************/
    public void serializza(String file){
        File _f = new File(file);
        if(_f.isFile()){
            _f.delete();
        }
        try {
            RandomAccessFile f = new RandomAccessFile(file, "rw");
            String xml =
                    "<?xml version= \"1.0\"?>";
           if(root != null){
               xml += "\n<"+correzione(root.nome)+this.stampaAttributi(root)+">";
               xml += this.stampaTag(0,root);
               xml += "\n</"+correzione(root.nome)+">";
           }
            f.writeBytes(xml);
            f.close();

        } catch (IOException ex) {
            javax.swing.JOptionPane.showMessageDialog(
                    null,
                    "Errore scrittura file '"+file+"' :"+ex.getMessage(),
                    "errore", javax.swing.JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private String spaziatura(int s){
        String xml="";
        for(int j=0;j<s;j++){
            xml+="\t";
        }
        return xml;
    }
    private String stampaTag(int t,Elemento nodo){
        t++;
        String xml = "";
        if(nodo.numeroElementi()>0){
            for(int i=0;i<nodo.numeroElementi();i++){
                Elemento sottoNodo = nodo.getElemento(i);
                String contenuto = null;
                if(sottoNodo.numeroElementi()>0 || (contenuto=sottoNodo.getValore())!=null){
                    //se tag non vuoto
                    xml += "\n" + this.spaziatura(t);
                    xml += "<"+correzione(sottoNodo.getNome())+this.stampaAttributi(sottoNodo)+">";
                    xml +=this.stampaTag(t,sottoNodo);
                    if(contenuto!=null){
                        if(contenuto.compareTo("")>0){
                            xml +=  contenuto;
                        }
                    }
                    if(sottoNodo.numeroElementi()>0){
                        xml += "\n"+this.spaziatura(t);
                    }
                    xml+="</"+correzione(sottoNodo.getNome())+">";
                }else{
                    //se tag vuoto
                    xml += "\n" + this.spaziatura(t);
                    xml += "<"+correzione(sottoNodo.getNome())+this.stampaAttributi(sottoNodo)+" />";
                }
           }
        }
        return xml;
    }

     private String stampaAttributi(Elemento nodo){
        String xml = "";
        for(int i=0;i<nodo.numeroAttributi();i++){
            xml += " "+correzione(nodo.getAttributo(i).getNome())+
                    "=\""+correzione(nodo.getAttributo(i).getValore())+"\"";
       }
        return xml;
    }


    private String stampaNodo(Elemento nodo,int n){
        n++;
        String s = "\n"+spaziatura(n)+"[+] "  +nodo.getNome();
        //attrributi
        for(int i=0; i<nodo.numeroAttributi();i++){
            s += " "+ nodo.getAttributo(i).nome+"=\""+nodo.getAttributo(i).valore+"\"";
        }
       //elementi
        for(int i=0; i<nodo.numeroElementi();i++){
            s +=   stampaNodo(nodo.getElemento(i),n);
        }
        
        if(nodo.numeroElementi()==0){
            s += " ("+nodo.getValore()+")";
        }
        
        return s;
    }

    @Override
    public String toString() {
        String s = "[+] "+root.getNome();
        //attributi
        for(int i=0; i<root.numeroAttributi();i++){
            s += " "+ root.getAttributo(i).getNome()+
                    "=\""+root.getAttributo(i).getValore()+"\"";
        }        
        //elementi
        for(int i=0; i<root.numeroElementi();i++){
            s +=  stampaNodo(root.getElemento(i),0);
        }
        //commento
        if(root.numeroElementi()==0){
            s += " ("+root.getValore()+")";
        }
        
        return s;
    }

   

    private ArrayList<Attributo> caricaAttributi(ArrayList<String> attributi) throws ErroreXML {

        ArrayList<Attributo> listaAttributi = new ArrayList<Attributo>();
        for(String attributo:attributi){
            Testo testo = new Testo(attributo);
            int id;
            String nomeAttr,valoreAttr;
            if((id=testo.cercaCarattere('='))>0){
                //analisi nome attributo
                ArrayList<String> lista = testo.trovaStringheTraSpazi(0, id);
                if(lista.size()>=0 && lista.size()<2){
                    if(lista.size()==1){
                        nomeAttr=lista.get(0);
                    }else{
                        nomeAttr=testo.sottoStringa(0, id);
                    }

                }else{
                    throw new ErroreXML("nome attributo non valido '"+attributo+"'");
                    
                }
                //analisi valore
                String s =testo.sottoStringa('"', '"');
                valoreAttr=s.substring(1, s.length()-1);

                listaAttributi.add(new Attributo(nomeAttr,valoreAttr));
            }else{
                throw new ErroreXML("attributo senza valore '"+attributo+"'");
            }

        }
        return listaAttributi;
    }

    private ArrayList<Elemento> tagMultipli(String contenuto) throws ErroreXML {

        ArrayList<Elemento> listaNodi = new ArrayList<Elemento>();
        Tag tag = new Tag(contenuto);

        do{ 
            if(tag.esistenzaTag()){//se il contenuto è un o più sotto-tag
                //verifica correttezza tag
                tag.acquisizioneApertura();
                boolean tagNonVuoto = !tag.tagVuoto();
                if(tagNonVuoto){
                    tag.acquisizioneChiusura();
                }
                
                //crea il nodo associato al tag
                Elemento nodo = new Elemento(tag.getNome());
                nodo.setAttributi(caricaAttributi(tag.getAttributi()));

                if(tagNonVuoto){
                    if(tag.acquisisciContenuto()){//se esiste il contenuto
                        ArrayList<Elemento> tagNidificati=
                                this.tagMultipli(tag.getContenuto());

                        if(!tagNidificati.isEmpty()){//se esistono sotto elementi
                            for(Elemento sottoNodo:tagNidificati){
                                nodo.addElemento(sottoNodo);
                            }
                        }else{//se non esistono elementi
                            nodo.setValore(tag.getContenuto());
                        }
                    }
                }
                listaNodi.add(nodo);

            }else{//non vi sono tag nidificati nel corpo
                break;
            }

        }while((tag=tag.verificaTagSuccessivo())!=null);
        
        return listaNodi;
    }

    private String correzione(String xml){
        Testo stringa = new Testo(xml);
        stringa.sostituisciStringa("&", "&amp");
        stringa.sostituisciStringa("<", "&lt");
        stringa.sostituisciStringa(">", "&gt");
        stringa.sostituisciStringa("\"", "&quot");
        stringa.sostituisciStringa("'", "&apos");
        return stringa.getTesto();
    }


}



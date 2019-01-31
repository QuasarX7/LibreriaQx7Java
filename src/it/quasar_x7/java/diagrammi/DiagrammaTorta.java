package it.quasar_x7.java.diagrammi;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JPanel;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.Plot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.jdbc.JDBCPieDataset;

/*******************************************************************************
 *  
 * @author Domenico della Peruta
 ******************************************************************************/
public class DiagrammaTorta implements Diagramma {
    private String titolo;
    private PieDataset dati;
    private boolean legenda;
   
    
    public DiagrammaTorta(String titolo,boolean legenda) {
        this.titolo = titolo;
        this.legenda=legenda;
    }
    
       
    
    public void caricaDati(String etichetta,Number valore){
        if(dati == null){
            dati = new DefaultPieDataset();            
        }
        if(dati instanceof DefaultPieDataset) {
            ((DefaultPieDataset)dati).setValue(etichetta, valore);
        }
    }
    
    @Override
    public void caricaDatiMySQL(String host,String schema, String utente, 
            String password,String sql) 
            throws ClassNotFoundException, SQLException{
        if(dati == null){
            Class.forName("com.mysql.jdbc.Driver");
            Connection connessione = DriverManager.getConnection(
                               "jdbc:mysql://" + host + ":3306/"+schema+"?user="
                               + utente + "&password=" + password);
            dati = new JDBCPieDataset(connessione);
            ((JDBCPieDataset)dati).executeQuery(sql);
            connessione.close();
        }
    }
    
    @Override
    public void titolo(String titolo){
        this.titolo = titolo;
    }
    
    @Override
    public void creaImmaginePNG(String filePNG,int altezza,int larghezza) throws IOException{
        JFreeChart chart=creaGrafico();    
        ChartUtilities.saveChartAsPNG(new java.io.File(filePNG), chart, larghezza, altezza);        
    }
    
    @Override
    public void creaFinestra(int altezza,int larghezza){
        JFreeChart chart=creaGrafico();    
        ChartFrame frame1=new ChartFrame(titolo,chart);
        frame1.setVisible(true);
        frame1.setSize(larghezza,altezza);
    }
    
    @Override
   public JPanel creaPannello(){
       JFreeChart chart=creaGrafico();    
       return new ChartPanel(chart);
   }
    
   private JFreeChart creaGrafico(){
       JFreeChart grafico=org.jfree.chart.ChartFactory.createPieChart3D(titolo, dati, legenda,true,true); 
       
       PiePlot3D plot =  (PiePlot3D) grafico.getPlot();
       plot.setForegroundAlpha(0.4f);
       
       return grafico;
   }

   public void svuota(){
        if(dati instanceof DefaultPieDataset){
            ((DefaultPieDataset)dati).clear();
            dati = new DefaultPieDataset();  
        }
    }
}

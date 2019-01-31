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
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.jdbc.JDBCXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Domenico della Peruta 
 */
public class DiagrammaXY  implements Diagramma {

    private String titolo,asseX,asseY;
    private boolean legenda;
    private XYDataset dati;

    public DiagrammaXY(String titolo, String etichettaX, String etichettaY, boolean legenda) {
        this.titolo = titolo;
        this.asseX = etichettaX;
        this.asseY = etichettaY;
        this.legenda = legenda;
    }
    
        
    public void caricaDati(String serie,Number x,Number y) {        
        if(dati == null){            
            dati = new XYSeriesCollection();
            XYSeries nuovaSerie = new XYSeries(serie); 
            nuovaSerie.add(x, y);
            ((XYSeriesCollection)dati).addSeries(nuovaSerie);
                        
        }
        
        if(dati instanceof XYSeriesCollection){
            try{
                XYSeries s = ((XYSeriesCollection)dati).getSeries(serie);
                ((XYSeriesCollection)dati).removeSeries(s);
                s.add(x, y);
                ((XYSeriesCollection)dati).addSeries(s);
                
            }catch(org.jfree.data.UnknownKeyException ex){
                XYSeries nuovaSerie = new XYSeries(serie); 
                nuovaSerie.add(x, y);
                ((XYSeriesCollection)dati).addSeries(nuovaSerie);                  
            }
        }
    }
    
    

    public void caricaDatiMySQL(String host, String schema, String utente, String password, String sql) throws ClassNotFoundException, SQLException {
        if(dati == null){
            Class.forName("com.mysql.jdbc.Driver");
            Connection connessione = DriverManager.getConnection(
                               "jdbc:mysql://" + host + ":3306/"+schema+"?user="
                               + utente + "&password=" + password);
            dati = new JDBCXYDataset(connessione);
            ((JDBCXYDataset)dati).executeQuery(sql);
            connessione.close();
        }
    }

    public void creaFinestra(int altezza, int larghezza) {         
        JFreeChart chart=org.jfree.chart.ChartFactory.createXYLineChart(titolo, asseX, asseY, dati, PlotOrientation.VERTICAL, legenda, true, true);
        ChartFrame frame1=new ChartFrame(titolo,chart);
        frame1.setVisible(true);
        frame1.setSize(larghezza,altezza);
    }

    public void creaImmaginePNG(String filePNG, int altezza, int larghezza) throws IOException {
        JFreeChart chart=org.jfree.chart.ChartFactory.createXYLineChart(titolo, asseX, asseY, dati, PlotOrientation.VERTICAL, legenda, true, true);
        ChartUtilities.saveChartAsPNG(new java.io.File(filePNG), chart, larghezza, altezza); 
    }

    public JPanel creaPannello() {        
       JFreeChart chart=org.jfree.chart.ChartFactory.createXYLineChart(titolo, asseX, asseY, dati, PlotOrientation.VERTICAL, legenda, true, true);
        return new ChartPanel(chart);
    }

    public void titolo(String titolo) {
        this.titolo=titolo;
    }

    public void svuota() {
        if(dati instanceof XYSeriesCollection)
            ((XYSeriesCollection)dati).removeAllSeries();
    }
    
}

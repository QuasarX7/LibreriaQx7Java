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
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.jdbc.JDBCCategoryDataset;

/*******************************************************************************
 *
 * @author Domenico della Peruta 
 ******************************************************************************/
public class DiagrammaBarre  implements Diagramma {

    protected String titolo,categoriaAsse,valoreAsse;
    protected boolean legenda;
    protected CategoryDataset dati;

    public DiagrammaBarre(String titolo, String etichettaCategorie, String etichettaValori, boolean legenda) {
        this.titolo = titolo;
        this.categoriaAsse = etichettaCategorie;
        this.valoreAsse = etichettaValori;
        this.legenda = legenda;
    }
    
        
    public void caricaDati(String gruppo,String barra, Number valore) {        
        if(dati == null){            
            dati = new DefaultCategoryDataset();
        }
        if(dati instanceof DefaultCategoryDataset){
            ((DefaultCategoryDataset)dati).addValue(valore, barra,gruppo);
        }
    }
    
    public void svuota(){
        if(dati instanceof DefaultCategoryDataset){
            ((DefaultCategoryDataset)dati).clear();
            dati = new DefaultCategoryDataset();
        }
    }

    public void caricaDatiMySQL(String host, String schema, String utente, String password, String sql) throws ClassNotFoundException, SQLException {
        if(dati == null){
            Class.forName("com.mysql.jdbc.Driver");
            Connection connessione = DriverManager.getConnection(
                               "jdbc:mysql://" + host + ":3306/"+schema+"?user="
                               + utente + "&password=" + password);
            dati = new JDBCCategoryDataset(connessione);
            ((JDBCCategoryDataset)dati).executeQuery(sql);
            connessione.close();
        }
    }

    public void creaFinestra(int altezza, int larghezza) {        
        JFreeChart chart=creaGrafico();
        ChartFrame frame1=new ChartFrame(titolo,chart);
        frame1.setVisible(true);
        frame1.setSize(larghezza,altezza);
    }

    public void creaImmaginePNG(String filePNG, int altezza, int larghezza) throws IOException {
        JFreeChart chart=creaGrafico();
        ChartUtilities.saveChartAsPNG(new java.io.File(filePNG), chart, larghezza, altezza); 
    }

    public JPanel creaPannello() {
        JFreeChart chart=creaGrafico();
        return new ChartPanel(chart);
    }

    public void titolo(String titolo) {
        this.titolo=titolo;
    }
    
    protected JFreeChart creaGrafico(){
        JFreeChart grafico = org.jfree.chart.ChartFactory.createBarChart3D(
                           titolo, categoriaAsse, valoreAsse, dati, 
                           PlotOrientation.VERTICAL, legenda, true, true);
        
        Plot plot =  grafico.getPlot();
        plot.setForegroundAlpha(0.4f);
        
        
        return grafico;
    }
    
}

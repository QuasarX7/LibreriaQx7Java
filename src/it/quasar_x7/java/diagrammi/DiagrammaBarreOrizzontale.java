
package it.quasar_x7.java.diagrammi;

import java.io.IOException;
import javax.swing.JPanel;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;

/**
 *
 * @author ninja
 */
public class DiagrammaBarreOrizzontale extends DiagrammaBarre{

    public DiagrammaBarreOrizzontale(String titolo, String etichettaCategorie, String etichettaValori, boolean legenda) {
        super(titolo, etichettaCategorie, etichettaValori, legenda);
    }
    
    @Override
    protected JFreeChart creaGrafico(){
        JFreeChart grafico = org.jfree.chart.ChartFactory.createBarChart3D(
                           titolo, categoriaAsse, valoreAsse, dati, 
                           PlotOrientation.HORIZONTAL, legenda, true, true);
        
        Plot plot =  grafico.getPlot();
        
        plot.setForegroundAlpha(0.5f);
        
        return grafico;
    }
}

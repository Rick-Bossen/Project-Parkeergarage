package parkeersimulator.view.statistics;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents a single line chart.
 *
 * @version 28.01.2019.
 */
public class StatisticsChart extends JPanel {

    private JFreeChart chart;
    private DefaultCategoryDataset dataset;
    private final StandardChartTheme chartTheme = (StandardChartTheme)org.jfree.chart.StandardChartTheme.createJFreeTheme();

    public StatisticsChart(String title, String xLabel, String yLabel)
    {
        createTheme();
        dataset = new DefaultCategoryDataset();
        chart = ChartFactory.createLineChart(title,xLabel,yLabel,dataset,PlotOrientation.VERTICAL,false,false,false);
        chartTheme.apply(chart);
        CategoryPlot plot = chart.getCategoryPlot();
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setDefaultShapesVisible(true);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(300,200));
        add(chartPanel);
    }

    /**
     * Create chart theme.
     */
    private void createTheme(){
        Font oldExtraLargeFont = chartTheme.getExtraLargeFont();
        Font oldLargeFont = chartTheme.getLargeFont();
        Font oldRegularFont = chartTheme.getRegularFont();
        Font oldSmallFont = chartTheme.getSmallFont();

        chartTheme.setExtraLargeFont(new Font("Dubai", oldExtraLargeFont.getStyle(), oldExtraLargeFont.getSize()));
        chartTheme.setLargeFont(new Font("Dubai", oldLargeFont.getStyle(), oldLargeFont.getSize()));
        chartTheme.setRegularFont(new Font("Dubai", oldRegularFont.getStyle(), oldRegularFont.getSize()));
        chartTheme.setSmallFont(new Font("Dubai", oldSmallFont.getStyle(), oldSmallFont.getSize()));
    }

    /**
     * Add a value to the chart.
     *
     * @param columnKey Column of the chart.
     * @param value Value of the column.
     */
    public void addValue(int columnKey, int value) {
        dataset.addValue(value, "default", Integer.toString(columnKey));
        if(dataset.getColumnCount() > 10){
            dataset.removeColumn(0);
        }
    }

}

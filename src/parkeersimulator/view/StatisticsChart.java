package parkeersimulator.view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
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

    public StatisticsChart(String title, String xLabel, String yLabel)
    {
        dataset = new DefaultCategoryDataset();
        chart = ChartFactory.createLineChart(title,xLabel,yLabel,dataset,PlotOrientation.VERTICAL,false,false,false);
        CategoryPlot plot = chart.getCategoryPlot();
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setDefaultShapesVisible(true);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(300,200));
        add(chartPanel);
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

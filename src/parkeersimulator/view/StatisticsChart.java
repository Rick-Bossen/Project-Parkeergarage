package parkeersimulator.view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import parkeersimulator.model.Clock;

import javax.swing.*;
import java.awt.*;

public class StatisticsChart extends JPanel {

    private JFreeChart chart;
    private DefaultCategoryDataset dataset;
    private Clock clock;
    private Boolean useDays;
    private int totalAdded;

    public StatisticsChart(String title, String xLabel, String yLabel,Boolean useDays)
    {
        this.useDays = useDays;
        clock = new Clock();
        dataset = new DefaultCategoryDataset();
        chart = ChartFactory.createLineChart(title,xLabel,yLabel,dataset,PlotOrientation.VERTICAL,false,false,false);
        CategoryPlot plot = chart.getCategoryPlot();
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setDefaultShapesVisible(true);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(300,200));
        add(chartPanel);
    }

    public void tick()
    {
        clock.advanceTime();
    }

    public void add(int value) {
        Integer columnKey;
        if (clock.getMinute() == 0) {
            if (useDays) {
                columnKey = clock.getDay();
            } else {
                columnKey = clock.getHour();
            }
            if (totalAdded > 23) {
                reset();
            }
            dataset.addValue(value, "profit", columnKey.toString());
            totalAdded++;
        }

    }

    private void reset()
    {
        dataset.clear();
        totalAdded = 0;
    }

}

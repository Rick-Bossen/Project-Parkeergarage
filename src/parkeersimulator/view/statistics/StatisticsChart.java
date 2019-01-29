package parkeersimulator.view.statistics;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import parkeersimulator.enums.theme.ThemeFonts;

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

    public StatisticsChart(String title, String xLabel, String yLabel, boolean doubleChart)
    {
        createTheme();
        setOpaque(false);
        dataset = new DefaultCategoryDataset();
        chart = ChartFactory.createLineChart(title,xLabel,yLabel,dataset,PlotOrientation.VERTICAL,true,false,false);
        chartTheme.apply(chart);
        chart.getPlot().setBackgroundPaint(new Color(195, 195, 195));
        CategoryPlot plot = chart.getCategoryPlot();
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setDefaultShapesVisible(true);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(doubleChart ? 650 : 300,300));
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

        chartTheme.setExtraLargeFont(new Font(ThemeFonts.defaultFont, oldExtraLargeFont.getStyle(), oldExtraLargeFont.getSize()));
        chartTheme.setLargeFont(new Font(ThemeFonts.defaultFont, oldLargeFont.getStyle(), oldLargeFont.getSize()));
        chartTheme.setRegularFont(new Font(ThemeFonts.defaultFont, oldRegularFont.getStyle(), oldRegularFont.getSize()));
        chartTheme.setSmallFont(new Font(ThemeFonts.defaultFont, oldSmallFont.getStyle(), oldSmallFont.getSize()));
    }

    /**
     * Add a value to the chart.
     *
     * @param rowKey Statistic of the chart.
     * @param columnKey Column of the chart.
     * @param value Value of the column.
     */
    public void addValue(String rowKey, int columnKey, int value) {
        dataset.addValue(value, rowKey, Integer.toString(columnKey));
        if(dataset.getColumnCount() > 10){
            dataset.removeColumn(0);
        }
    }

}

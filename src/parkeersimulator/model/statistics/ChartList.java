package parkeersimulator.model.statistics;

import parkeersimulator.framework.Model;
import parkeersimulator.model.Clock;
import parkeersimulator.view.StatisticsChart;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class represents a list of all the StatisticCharts and its corresponding Statistic.
 *
 * @version 28.1.2019.
 */
public class ChartList extends Model {

    private LinkedHashMap<String, StatisticsChart> charts;
    private StatisticsList statisticsList;
    private HashMap<StatisticsChart,Statistic> updateDaily;
    private HashMap<StatisticsChart,Statistic> updateHourly;
    private Clock clock;

    public ChartList(StatisticsList statisticsList) {
        updateDaily = new HashMap<>();
        updateHourly = new HashMap<>();
        charts = new LinkedHashMap<>();
        this.statisticsList = statisticsList;
        clock = new Clock();
        fillChartList();
    }

    /**
     * Adds one new chart to the ChartList.
     *
     * @param id the id of the chart.
     * @param title the title of the chart.
     * @param xLabel the label that will be displayed on the x axis of the chart.
     * @param yLabel the label that will be displayed on the y axis of the chart.
     * @param statistic the Statistic object that will be linked to the chart.
     * @param updateDaily makes the chart add a data point once every 24 hours instead of once every hour.
     */
    private void addChart(String id,String title, String xLabel, String yLabel,Statistic statistic, Boolean updateDaily) {
        StatisticsChart chart = new StatisticsChart(title,xLabel,yLabel);
        charts.put(id, chart);
        if (updateDaily) {
            this.updateDaily.put(chart,statistic);
        } else {
            this.updateHourly.put(chart,statistic);
        }
    }

    /**
     * A method to add new charts to the simulation.
     */
    private void fillChartList()
    {
        addChart("profit.hourly","Hourly Profit","Hour", "Profit", statisticsList.getStatistic("profit.total"),false);
        addChart("profit.daily","Daily Profit","Day","Profit", statisticsList.getStatistic("profit.total") ,true);
    }

    /**
     * Adds a new data point to all the StatisticsCharts contained inside of updateHourly.
     */
    private void updateHourlyCharts()
    {
        for(Map.Entry<StatisticsChart,Statistic> entry : updateHourly.entrySet()) {
            Statistic statistic = entry.getValue();
            StatisticsChart chart = entry.getKey();
            chart.addValue(clock.getHour(), statistic.getPastHour());
        }
    }

    /**
     * Adds a new data point to all the StatisticsCharts contained inside of updateDaily.
     */
    private void updateDailyCharts()
    {
        for(Map.Entry<StatisticsChart,Statistic> entry : updateDaily.entrySet()) {
            Statistic statistic = entry.getValue();
            StatisticsChart chart = entry.getKey();
            chart.addValue(clock.getDay(), statistic.getPastDay());
        }
    }

    /**
     * Returns the StatisticsChart with the given id.
     *
     * @param id the id of the StatisticsChart.
     * @return the StatisticsChart linked to the given id.
     */
    public StatisticsChart getChart(String id)
    {
        return charts.get(id);
    }

    /**
     * Advances the time, ticks all the StatisticsCharts and checks whether they need to be updated with a new data point.
     */
    public void tick() {
        clock.advanceTime();
        updateViews();

        if (clock.getMinute() == 0 || clock.getMinute() % 10 == 0) {
            updateHourlyCharts();
            updateDailyCharts();
        }
    }

    /**
     * Resets the object to a state as if it was just initialized.
     * @param statisticsList the new StatisticsList to be linked to this object.
     */
    public void reset(StatisticsList statisticsList) {
        clock.reset();
        charts.clear();
        updateDaily.clear();
        updateHourly.clear();
        this.statisticsList = statisticsList;
        fillChartList();
    }
}

package parkeersimulator.model.statistics;

import parkeersimulator.framework.Model;
import parkeersimulator.model.Clock;
import parkeersimulator.view.statistics.StatisticsChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class represents a list of all the StatisticCharts and its corresponding Statistic.
 *
 * @version 28.01.2019.
 */
public class ChartList extends Model {

    private LinkedHashMap<String, StatisticsChart> charts;
    private StatisticsList statisticsList;
    private HashMap<Statistic, StatisticsChart> updateDaily;
    private HashMap<Statistic, StatisticsChart> updateHourly;
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
     * Adds one new chart to the ChartList with multiple statistics.
     *
     * @param id the id of the chart.
     * @param title the title of the chart.
     * @param xLabel the label that will be displayed on the x axis of the chart.
     * @param yLabel the label that will be displayed on the y axis of the chart.
     * @param statistics the Statistic object that will be linked to the chart.
     * @param updateDaily makes the chart add a data point once every 24 hours instead of once every hour.
     * @param doubleChart if the chart should have double width.
     */
    private void addChart(String id,String title, String xLabel, String yLabel, ArrayList<Statistic> statistics, Boolean updateDaily, boolean doubleChart) {
        StatisticsChart chart = new StatisticsChart(title, xLabel, yLabel, doubleChart);
        charts.put(id, chart);
        if (updateDaily) {
            for(Statistic statistic : statistics){
                this.updateDaily.put(statistic, chart);
            }
        } else {
            for(Statistic statistic : statistics){
                this.updateHourly.put(statistic, chart);
            }
        }
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
     * @param doubleChart if the chart should have double width.
     */
    private void addChart(String id,String title, String xLabel, String yLabel,Statistic statistic, Boolean updateDaily, boolean doubleChart) {
        StatisticsChart chart = new StatisticsChart(title,xLabel,yLabel, doubleChart);
        charts.put(id, chart);
        if (updateDaily) {
            this.updateDaily.put(statistic, chart);
        } else {
            this.updateHourly.put(statistic, chart);
        }
    }

    /**
     * A method to add new charts to the simulation.
     */
    private void fillChartList()
    {
        addChart("profit.hourly","Hourly Profit","Hour", "Profit", statisticsList.getStatistic("profit.total"),false, false);
        addChart("profit.daily","Daily Profit","Day","Profit", statisticsList.getStatistic("profit.total") ,true, false);

        ArrayList<Statistic> statistics = new ArrayList<>();
        statistics.add(statisticsList.getStatistic("cars.entered.adhoc"));
        statistics.add(statisticsList.getStatistic("cars.entered.pass"));
        statistics.add(statisticsList.getStatistic("cars.entered.reserved"));
        addChart("entering.all", "Cars entering", "Day", "Cars", statistics,true, true);

        updateHourlyCharts();
        updateDailyCharts();
    }

    /**
     * Adds a new data point to all the StatisticsCharts contained inside of updateHourly.
     */
    private void updateHourlyCharts()
    {
        for(Map.Entry<Statistic, StatisticsChart> entry : updateHourly.entrySet()) {
            Statistic statistic = entry.getKey();
            StatisticsChart chart = entry.getValue();
            chart.addValue(statistic.getId(), clock.getHour(), statistic.getPastHour());
        }
    }

    /**
     * Adds a new data point to all the StatisticsCharts contained inside of updateDaily.
     */
    private void updateDailyCharts()
    {
        for(Map.Entry<Statistic, StatisticsChart> entry : updateDaily.entrySet()) {
            Statistic statistic = entry.getKey();
            StatisticsChart chart = entry.getValue();
            chart.addValue(statistic.getId(), clock.getDay(), statistic.getPastDay());
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
     *
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

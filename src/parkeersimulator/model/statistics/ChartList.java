package parkeersimulator.model.statistics;

import parkeersimulator.framework.Model;
import parkeersimulator.model.Clock;
import parkeersimulator.view.StatisticsChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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

    private void addChart(String id,String title, String xLabel, String yLabel,Statistic statistic, Boolean updateDaily) {
        StatisticsChart chart = new StatisticsChart(title,xLabel,yLabel,updateDaily);
        charts.put(id, chart);
        if (updateDaily) {
            this.updateDaily.put(chart,statistic);
        } else {
            this.updateHourly.put(chart,statistic);
        }
    }

    public StatisticsChart getChart(String id)
    {
        return charts.get(id);
    }

    public void tick() {
        clock.advanceTime();
        for (StatisticsChart chart : charts.values()) {
            chart.tick();
        }
        updateViews();

        if (clock.getMinute() == 0) {
            updateHourlyCharts();
        }

        if (clock.getHour() == 0 && clock.getMinute() == 0) {
            updateDailyCharts();
        }
    }

    public void reset(StatisticsList statisticsList) {
        charts.clear();
        updateDaily.clear();
        updateHourly.clear();
        this.statisticsList = statisticsList;
        fillChartList();
    }

    private void fillChartList()
    {
        addChart("profit.hourly","Hourly Profit","Hour", "Profit",statisticsList.getStatistic("profit.total"),false);
        addChart("profit.daily","Daily Profit","Day","Profit",statisticsList.getStatistic("profit.total") ,true);
    }

    private void updateHourlyCharts()
    {
        for(Map.Entry<StatisticsChart,Statistic> entry : updateHourly.entrySet()) {
            Statistic statistic = entry.getValue();
            StatisticsChart chart = entry.getKey();
            chart.add(statistic.getPastHour());
        }
    }

    private void updateDailyCharts()
    {
        for(Map.Entry<StatisticsChart,Statistic> entry : updateDaily.entrySet()) {
            Statistic statistic = entry.getValue();
            StatisticsChart chart = entry.getKey();
            chart.add(statistic.getPastDay());
        }
    }
}

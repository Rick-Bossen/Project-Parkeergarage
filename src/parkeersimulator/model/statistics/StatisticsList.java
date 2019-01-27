package parkeersimulator.model.statistics;

import parkeersimulator.framework.Model;

import java.util.Iterator;
import java.util.LinkedHashMap;

public class StatisticsList extends Model {

    private LinkedHashMap<String, Statistic> statistics;

    public StatisticsList() {
        statistics = new LinkedHashMap<>();
        fillStatisticsList();
    }

    private void addStatistic(String id) {
        Statistic statistic = new Statistic();
        statistics.put(id, statistic);
    }

    public void add(String id, int amount) {
        Statistic statistic = statistics.get(id);
        statistic.add(amount);
    }

    public int getTotal(String id) {
        Statistic statistic = statistics.get(id);
        return statistic.getTotal();
    }

    public int getHourlyAvg(String id) {
        Statistic statistic = statistics.get(id);
        return statistic.getHourlyAvg();
    }

    public int getDailyAvg(String id) {
        Statistic statistic = statistics.get(id);
        return statistic.getDailyAvg();
    }

    public void tick() {
        for (Statistic statistic : statistics.values()) {
            statistic.tick();
        }
        updateViews();
    }

    public void reset() {
        statistics.clear();
        fillStatisticsList();
    }


    private void fillStatisticsList() {
        addStatistic("profit.total");
        addStatistic("profit.adhoc");
        addStatistic("profit.pass");
        addStatistic("profit.reserved");
        addStatistic("cars.entered");
        addStatistic("cars.missed");
    }

}

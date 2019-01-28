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

    public int getPastHour(String id) {
        Statistic statistic = statistics.get(id);
        return statistic.getPastHour();
    }

    public int getPastDay(String id) {
        Statistic statistic = statistics.get(id);
        return statistic.getPastDay();
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

    Statistic getStatistic(String id)
    {
        return statistics.get(id);
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

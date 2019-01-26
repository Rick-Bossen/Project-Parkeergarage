package parkeersimulator.model.statistics;

import parkeersimulator.framework.Model;

import java.util.LinkedHashMap;
import java.util.Iterator;

public class StatisticsList extends Model {

    private LinkedHashMap<String,Statistic> statistics;

    public StatisticsList()
    {
        statistics = new LinkedHashMap<>();
        fillStatisticsList();
    }

    public void addStatistic(String id)
    {
        Statistic statistic = new Statistic();
        statistics.put(id,statistic);
    }

    public void add(String id, int amount)
    {
        Statistic statistic = statistics.get(id);
        statistic.add(amount);
    }

    public int getTotal(String id)
    {
        Statistic statistic = statistics.get(id);
        System.out.println(id);
        return statistic.getTotal();
    }

    public int getHourlyAvg(String id)
    {
        Statistic statistic = statistics.get(id);
        return statistic.getHourlyAvg();
    }

    public int getDailyAvg(String id)
    {
        Statistic statistic = statistics.get(id);
        return statistic.getDailyAvg();
    }

    public void tick()
    {
        //TODO foreach?
        Iterator<Statistic> it = statistics.values().iterator();
        while (it.hasNext()) {
            Statistic statistic = it.next();
            statistic.tick();
        }
        updateViews();
    }

    public void reset()
    {
        statistics.clear();
        fillStatisticsList();
    }


    public void fillStatisticsList()
    {
        addStatistic("profit.total");
        addStatistic("profit.adhoc");
        addStatistic("profit.pass");
        addStatistic("profit.reserved");
        addStatistic("cars.entered");
        addStatistic("cars.missed");
    }

}

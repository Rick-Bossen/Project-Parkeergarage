package parkeersimulator.model.statistics;

import parkeersimulator.framework.Model;

import java.util.LinkedHashMap;

/**
 * A class that contains a list of all the tracked statistics linked to an unique id.
 *
 * @version 28.01.2019.
 */
public class StatisticsList extends Model {

    private LinkedHashMap<String, Statistic> statistics;

    public StatisticsList() {
        statistics = new LinkedHashMap<>();
        fillStatisticsList();
    }

    /**
     * Adds a new Statistic to the object.
     *
     * @param id the id given to the new Statistic.
     */
    private void addStatistic(String id) {
        Statistic statistic = new Statistic(id);
        statistics.put(id, statistic);
    }

    /**
     * Adds the given amount to the Statistic linked to the id.
     *
     * @param id the id of the Statistic.
     * @param amount the amount to add to the Statistic.
     */
    public void add(String id, int amount) {
        Statistic statistic = statistics.get(id);
        statistic.add(amount);
    }

    /**
     * Returns the total of the Statistic linked to the id.
     *
     * @param id the id of the Statistic.
     * @return the total of the Statistic linked to the id.
     */
    public int getTotal(String id) {
        Statistic statistic = statistics.get(id);
        return statistic.getTotal();
    }

    /**
     * Returns the sum of all the values added to the Statistic in the past hour.
     *
     * @param id the id of the Statistic.
     * @return the sum of all the values added to the Statistic in the past hour.
     */
    public int getPastHour(String id) {
        Statistic statistic = statistics.get(id);
        return statistic.getPastHour();
    }

    /**
     * Returns the sum of all the values added to the Statistic in the past day.
     *
     * @param id the id of the Statistic.
     * @return the sum of all the values added to the Statistic in the past day.
     */
    public int getPastDay(String id) {
        Statistic statistic = statistics.get(id);
        return statistic.getPastDay();
    }

    /**
     * Ticks all the Statistics and updates the view.
     */
    public void tick() {
        for (Statistic statistic : statistics.values()) {
            statistic.tick();
        }
        updateViews();
    }

    /**
     * Resets all the contained Statistics.
     */
    public void reset() {
        statistics.clear();
        fillStatisticsList();
    }

    /**
     * Returns th Statistic linked to the given id.
     *
     * @param id the id of the Statistic.
     * @return the Statistic linked to the id.
     */
    Statistic getStatistic(String id)
    {
        return statistics.get(id);
    }

    /**
     * A method to add new Statistics to the simulation.
     */
    private void fillStatisticsList() {
        addStatistic("profit.total");
        addStatistic("profit.adhoc");
        addStatistic("profit.pass");
        addStatistic("profit.reserved");
        addStatistic("cars.entered");
        addStatistic("cars.entered.adhoc");
        addStatistic("cars.entered.pass");
        addStatistic("cars.entered.reserved");
        addStatistic("cars.missed");
    }
}

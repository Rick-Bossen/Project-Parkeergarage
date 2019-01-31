package parkeersimulator.model.statistics;

import parkeersimulator.framework.Model;

import java.util.ArrayList;

/**
 * Class that contains all the advices and the parameters to decide whether to display them or not.
 *
 * @version 30.01.2019.
 */
public class Advice extends Model {

    private StatisticsList statisticsList;
    private String[] idList;
    private int tickCounter;
    private ArrayList<String> advices;

    public Advice(StatisticsList statisticsList)
    {
        idList = new String[] {"profit.total","cars.missed","cars.entered"};
        advices = new ArrayList<>();
        this.statisticsList = statisticsList;
    }

    /**
     * Increments the tickCounter by 1,
     * checks if the tickCounter has reached 30 and updates the advices if this is the case.
     */
    public void tick()
    {
        tickCounter++;
        if(tickCounter > 29) {
            updateAdvice();
            updateViews();
        }
    }

    /**
     * Resets the advices and assigns a nre StatisticsList to track.
     *
     * @param statisticsList the new StatisticsList to track.
     */
    public void reset(StatisticsList statisticsList)
    {
        advices.clear();
        this.statisticsList = statisticsList;
    }

    /**
     * Returns the ArrayList with all the advices.
     *
     * @return the ArrayList with all the advices.
     */
    public ArrayList<String> getAdvices()
    {
        return advices;
    }

    /**
     * Checks for every advice whether it needs to be displayed or not,
     * adds the advices that need to be displayed to the advices ArrayList.
     */
    private void updateAdvice() {
        advices.clear();
        for (String id : idList) {

            Statistic stat = statisticsList.getStatistic(id);

            if (id.equals("profit.total") && stat.getPastDay() < 20000) {
                advices.add("<html>* The profit of the last 24 hours is under €20000,<br> you may want to " +
                        "increase the prices.</html>");
            }

            if (id.equals("cars.missed") && stat.getPastHour() > 0) {
                advices.add("<html>* People are leaving because of long queue times,<br> you may want to increase " +
                        "the queue speed or capacity.</html>");
            }

            if (id.equals("cars.entered") && stat.getPastDay() < 2000) {
                advices.add("<html>* The amount of cars we've had in the past 24 hours is low,<br> you might " +
                        "want to find a way to attract more customers.</html>");
            }
        }
    }
}

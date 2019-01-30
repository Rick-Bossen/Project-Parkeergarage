package parkeersimulator.model.statistics;

import parkeersimulator.framework.Model;

import java.util.ArrayList;


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

    public void tick()
    {
        tickCounter++;
        if(tickCounter > 29) {
            updateAdvice();
            updateViews();
        }
    }

    public void reset(StatisticsList statisticsList)
    {
        advices.clear();
        this.statisticsList = statisticsList;
    }

    public ArrayList<String> getAdvices()
    {
        return advices;
    }

    private void updateAdvice() {
        advices.clear();
        for (String id : idList) {

            Statistic stat = statisticsList.getStatistic(id);

            if (id.equals("profit.total") && stat.getPastDay() < 20000) {
                advices.add("The profit of the last 24 hours is under €20000, you may want to " +
                        "increase the prices.");
            }

            if (id.equals("cars.missed") && stat.getPastHour() > 0) {
                advices.add("People are leaving because of long queue times, you may want to increase " +
                        "the queue speed or capacity.");
            }

            if (id.equals("cars.entered") && stat.getPastDay() < 2000) {
                advices.add("The amount of cars we've had in the past 24 hours is low, you might " +
                        "want to find a way to attract more customers.");
            }
        }
    }
}

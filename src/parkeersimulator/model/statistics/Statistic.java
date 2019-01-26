package parkeersimulator.model.statistics;

import parkeersimulator.model.Clock;
import parkeersimulator.framework.Model;
//TODO make averages only work over select amount of time
class Statistic extends Model {

    private int total;

    private Clock clock;
    private int ticks;

    Statistic()
    {
        clock = new Clock();
    }

    void tick()
    {
        clock.advanceTime();
        ticks++;
    }

    void add(int amount)
    {
        total += amount;
    }

    int getTotal() {
        return total;
    }

    int getHourlyAvg() {
        if (ticks == 0) {
            return 0;
        }
        return total / ticks * 60;
    }

    int getDailyAvg() {
        if (ticks == 0) {
            return 0;
        }
        return total / ticks * 60 * 24;
    }
}

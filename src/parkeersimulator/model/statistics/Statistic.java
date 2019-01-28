package parkeersimulator.model.statistics;

import parkeersimulator.framework.Model;
import parkeersimulator.model.Clock;

import java.util.ArrayList;

class Statistic extends Model {

    private int total;
    private ArrayList<Integer> pastHour;
    private ArrayList<Integer> pastDay;

    private Clock clock;
    private int ticks;
    private int lastTick;

    Statistic() {
        clock = new Clock();
        lastTick = 0;
        pastHour = new ArrayList<>();
        pastDay = new ArrayList<>();
    }

    void tick() {
        clock.advanceTime();
        ticks++;
        handleArrays();
    }

    private void handleArrays()
    {
        pastHour.add(lastTick);
        lastTick = 0;

        if (ticks > 60) {
            pastHour.remove(0);
        }

        if (clock.getMinute() == 59) {
            int total = 0;
            for (int value : pastHour) {
                total += value;
            }
            pastDay.add(total);

            if (pastDay.size() > 24) {
                pastDay.remove(0);
            }
        }
    }

    void add(int amount) {
        total += amount;
        lastTick += amount;
    }

    int getTotal() {
        return total;
    }

    int getHourAvg() {
        return average(pastHour);
    }

    int getDayAvg() {
        return average(pastDay);
    }

    private int average(ArrayList<Integer> arrayList)
    {
        int output = 0;
        if (arrayList.size() > 0) {
            for (int value : arrayList) {
                output += value;
            }
        }
        return output;
    }
}

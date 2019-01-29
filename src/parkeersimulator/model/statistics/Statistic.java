package parkeersimulator.model.statistics;

import parkeersimulator.framework.Model;
import parkeersimulator.model.Clock;

import java.util.ArrayList;

/**
 * A class that represents a single numeral statistic and the hourly and daily total amount of added values.
 *
 * @version 28.01.2019.
 */
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

    /**
     * Advances the time, increments tick and calls for the handleArrays() method.
     */
    void tick() {
        clock.advanceTime();
        ticks++;
        handleArrays();
    }

    /**
     * Adds new values to the arrays if needed and makes sure that the total amount of values does not exceed a certain
     * threshold.
     */
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

    /**
     * Calculates and returns the sum of all the values inside of a given ArrayList.
     *
     * @param arrayList the ArrayList containing all the values to calculate the sum of.
     * @return the total sum of all the values inside of the ArrayList.
     */
    private int sum(ArrayList<Integer> arrayList)
    {
        int output = 0;
        if (arrayList.size() > 0) {
            for (int value : arrayList) {
                output += value;
            }
        }
        return output;
    }

    /**
     * Adds a given number to the total and to the total of this tick.
     *
     * @param amount the given amount to add up to the total and total of this tick.
     */
    void add(int amount) {
        total += amount;
        lastTick += amount;
    }

    /**
     * Return the sum of all the added values.
     *
     * @return The sum of all added values.
     */
    int getTotal() {
        return total;
    }

    /**
     * Return the sum of all the added values from the past 60 ticks.
     *
     * @return The sum of all the added values from the past 60 ticks.
     */
    int getPastHour() {
        return sum(pastHour);
    }

    /**
     * Returns the sum of all the added values from the past 24 hours (the past 1440 ticks).
     *
     * @return The sum of all the added values from the past 24 hours (the past 1440 ticks).
     */
    int getPastDay() {
        return sum(pastDay);
    }

}

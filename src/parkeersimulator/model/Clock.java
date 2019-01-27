package parkeersimulator.model;

import parkeersimulator.framework.Model;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * This class represents a clock which keeps the time and day currently in the simulation.
 * It has the ability to advance the time.
 */
public class Clock extends Model {

    private int day;
    private int hour;
    private int minute;


    /**
     * Advance the clock by 1 minute.
     */
    public void advanceTime() {
        minute++;
        if (minute > 59) {
            minute -= 60;
            hour++;
        }
        if (hour > 23) {
            hour -= 24;
            day++;
        }
        updateViews();
    }

    /**
     * Get the current day
     *
     * @return Integer of the current day.
     */
    public int getDay() {
        return day;
    }

    /**
     * Get the current day of the week.
     *
     * @return Integer of the current day.
     */
    public int getDayOfWeek() {
        int dayOfWeek = day % 7;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        return dayOfWeek;
    }

    /**
     * Get the current hour
     *
     * @return Integer of the current hour.
     */
    public int getHour() {
        return hour;
    }

    /**
     * Get the current minute
     *
     * @return Integer of the current minute.
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Reset the clock to it's initial state
     */
    public void reset() {
        day = 1;
        hour = 0;
        minute = 0;
        updateViews();
    }

    /**
     * Returns a readable representation of the time.
     *
     * @return String of the current date/time.
     */
    public String toString() {
        String dayOfWeek = DayOfWeek.of(getDayOfWeek()).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        return String.format("%s, day %d - %02d:%02d", dayOfWeek, getDay(), getHour(), getMinute());
    }
}


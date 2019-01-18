package parkeersimulator.model;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * This class represents a clock which keeps the time and day currently in the simulation.
 * It has the ability to advance the time.
 */
public class Clock {

    private int day = 1;
    private int hour = 0;
    private int minute = 0;


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
        if (day > 7) {
            day -= 7;
        }

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
    }

    /**
     * Returns a readable representation of the time.
     *
     * @return String of the current date/time.
     */
    public String toString() {
        String dayOfWeek = DayOfWeek.of(day % 7).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        return String.format("%s, day %o - %02d:%02d", dayOfWeek, getDay(), getHour(), getMinute());
    }
}


package parkeersimulator.model;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

public class Clock {

    private int day = 1;
    private int hour = 0;
    private int minute = 0;


    public void advanceTime(){
        // Advance the time by one minute.
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

    public int getDay()
    {
        return day;
    }

    public int getHour()
    {
        return  hour;
    }

    public int getMinute()
    {
        return minute;
    }

    public String toString()
    {
        String dayOfWeek = DayOfWeek.of(day % 7).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        return String.format("%s, day %o - %02d:%02d", dayOfWeek, getDay(), getHour(), getMinute());
    }
}


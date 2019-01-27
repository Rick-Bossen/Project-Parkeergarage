package parkeersimulator.model;
import java.time.DayOfWeek;
import java.util.HashMap;

/**
 * This class represents an event for a custom amount of arrivals.
 *
 * @version 27.01.2019.
 */
public class Event {

    private HashMap<Integer, Integer[]> days;
    private int total;

    /** Create a new event.
     *
     * @param total total arrivals within the given timespan.
     */
    public Event(int total) {
        this.total = total;
        days = new HashMap<>();
    }

    /**
     * Add full day for the event.
     * @param day day of the event.
     */
    public void addDay(Integer day){
        addDay(day, 0, 24);
    }

    /**
     * Add day for the event.
     *
     * @param day day of the event.
     * @param startHour start hour of the event.
     * @param endHour end hour of the event.
     */
    public void addDay(Integer day, int startHour, int endHour){
        days.put(day, new Integer[]{startHour, endHour});
    }

    /**
     * Return if the event is currently ongoing.
     * @param clock clock
     * @return if is ongoing
     */
    public boolean isOngoing(Clock clock){
        if(days.get(clock.getDayOfWeek()) != null){
            Integer[] hours = days.get(clock.getDayOfWeek());
            return clock.getHour() >= hours[0] && clock.getHour() <= hours[1];
        }
        return false;
    }

    /**
     * Return the amount of hourly arrivals.
     *
     * @return hourly amount.
     */
    public int getHourlyAmount(int dayOfWeek){
        if(days.get(dayOfWeek) != null){
            Integer[] hours = days.get(dayOfWeek);
            return total / (hours[1] - hours[0] + 1);
        }
        return 0;
    }


}

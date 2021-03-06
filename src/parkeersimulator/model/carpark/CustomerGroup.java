package parkeersimulator.model.carpark;

import parkeersimulator.framework.Model;
import parkeersimulator.model.Clock;
import parkeersimulator.model.Event;
import parkeersimulator.model.car.Car;

import java.util.ArrayList;
import java.util.Random;

/**
 * Customer group for one specific entry type.
 * Used types: ad hoc and parking pass.
 *
 * @version 28.01.2019.
 */
public class CustomerGroup extends Model {

    private Class carType;
    private CarQueue entranceCarQueue;

    private int weekDayArrivals;
    private int weekendArrivals;

    private ArrayList<Event> events;

    CustomerGroup(Class carType, int weekDayArrivals, int weekendArrivals) {
        this.carType = carType;
        this.weekDayArrivals = weekDayArrivals;
        this.weekendArrivals = weekendArrivals;
        events = new ArrayList<>();
    }

    void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    /**
     * Get the entrance queue used by these customers.
     *
     * @return Car queue used by the customers.
     */
    public CarQueue getEntranceCarQueue() {
        return entranceCarQueue;
    }

    /**
     * Set the queue used for entrance by this type.
     *
     * @param queue Car queue the customers should use.
     */
    void setEntranceCarQueue(CarQueue queue) {
        this.entranceCarQueue = queue;
    }

    /**
     * Set new weekday arrivals.
     *
     * @param arrivals arrivals per weekday.
     */
    public void setWeekDayArrivals(int arrivals) {
        this.weekDayArrivals = arrivals;
    }

    /**
     * Set new weekend arrivals.
     *
     * @param arrivals arrivals per weekend.
     */
    public void setWeekendArrivals(int arrivals) {
        this.weekendArrivals = arrivals;
    }

    /**
     * Create a new car based on the car type.
     *
     * @return Car of the given type.
     */
    public Car getNewCar() {
        try {
            return (Car) carType.getConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns the number of car arrivals per hour depending on the day of the week and current events.
     *
     * @param clock the clock used to check the time.
     * @return the number of car arrivals per hour.
     */
    int getNumberOfCars(Clock clock){
        int averageNumber;
        if(clock.getDayOfWeek() < 6){
            averageNumber = weekDayArrivals;
        }else{
            averageNumber = weekendArrivals;
        }
        for (Event event : events) {
            if(event.isOngoing(clock)){
                averageNumber = event.getHourlyAmount();
            }
        }
        
        return calculateNumberOfCars(averageNumber);
    }

    /**
     * Generate a random number of cars that should enter on a given day.
     *
     * @param day To simulate the correct amount per day.
     * @return Amount of cars that should enter.
     */
    int getNumberOfCars(int day) {
        return calculateNumberOfCars(day < 6 ? weekDayArrivals : weekendArrivals);
    }

    private int calculateNumberOfCars(int averageNumber){
        Random random = new Random();
        double standardDeviation = averageNumber * 0.3;
        double numberOfCarsPerHour = averageNumber + random.nextGaussian() * standardDeviation;
        return (int) Math.round(numberOfCarsPerHour / 60);
    }

}

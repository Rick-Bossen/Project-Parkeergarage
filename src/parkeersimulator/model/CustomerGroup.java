package parkeersimulator.model;

import parkeersimulator.framework.Model;

import java.util.Random;

/**
 * Customer group for one specific entry type.
 * Used types: ad hoc and parking pass.
 */
public class CustomerGroup extends Model {

    private final Class carType;
    private CarQueue entranceCarQueue;

    private int weekDayArrivals;
    private int weekendArrivals;

    public CustomerGroup(Class carType, int weekDayArrivals, int weekendArrivals) {
        this.carType = carType;
        this.weekDayArrivals = weekDayArrivals;
        this.weekendArrivals = weekendArrivals;
    }

    /**
     * Get the entrance queue used by these customers.
     *
     * @return Car queue used by the customers
     */
    public CarQueue getEntranceCarQueue() {
        return entranceCarQueue;
    }

    /**
     * Set the queue used for entrance by this type.
     *
     * @param queue Car queue the customers should use.
     */
    public void setEntranceCarQueue(CarQueue queue) {
        this.entranceCarQueue = queue;
    }

    /**
     * Set new weekday arrivals
     * @param arrivals arrivals per weekday
     */
    public void setWeekDayArrivals(int arrivals){
        this.weekDayArrivals = arrivals;
    }

    /**
     * Set new weekend arrivals
     * @param arrivals arrivals per weekend
     */
    public void setWeekendArrivals(int arrivals){
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
     * Generate a random number of cars that should enter on a given day.
     *
     * @param day To simulate the correct amount per day.
     * @return Amount of cars that should enter.
     */
    public int getNumberOfCars(int day) {
        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = day < 6
                ? weekDayArrivals
                : weekendArrivals;

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.3;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        return (int) Math.round(numberOfCarsPerHour / 60);
    }

}

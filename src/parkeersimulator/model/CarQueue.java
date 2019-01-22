package parkeersimulator.model;

import parkeersimulator.framework.Model;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Represents a queue of cars (customers)
 * This contains all cars currently in the queue.
 */
public class CarQueue extends Model {

    private int speed;
    private final Queue<Car> queue = new LinkedList<>();

    /**
     * Initialize a new queue with a given speed.
     *
     * @param speed Speed of the queue.
     */
    public CarQueue(int speed) {
        this.speed = speed;
    }

    /**
     * Add a car to the queue.
     *
     * @param car Car that is added to the queue.
     * @return If the car is successfully added.
     */
    public void addCar(Car car) {
        queue.add(car);
    }

    /**
     * Remove all cars from the queue.
     */
    public void reset() {
        queue.clear();
    }

    /**
     * Remove the first car from the queue and return int.
     *
     * @return The first Car in the queue.
     */
    public Car removeCar() {
        return queue.poll();
    }

    /**
     * The amount of the cars in the queue.
     *
     * @return The amount of cars.
     */
    public int carsInQueue() {
        return queue.size();
    }

    /**
     * Set the speed of the queue.
     *
     * @param speed Integer representing the speed.
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Get the speed of the queue.
     *
     * @return Integer representing the speed.
     */
    public int getSpeed() {
        return speed;
    }
}

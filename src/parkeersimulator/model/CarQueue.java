package parkeersimulator.model;

import java.util.LinkedList;
import java.util.Queue;

public class CarQueue {

    private int speed;
    private Queue<Car> queue = new LinkedList<>();

    public CarQueue(int speed){
        this.speed = speed;
    }

    public boolean addCar(Car car) {
        return queue.add(car);
    }

    public Car removeCar() {
        return queue.poll();
    }

    public int carsInQueue(){
        return queue.size();
    }

    public int getSpeed() {
        return speed;
    }
}

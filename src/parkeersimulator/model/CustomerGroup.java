package parkeersimulator.model;

import java.util.Random;

public class CustomerGroup {

    private Class carType;
    private CarQueue entranceCarQueue;

    private int weekDayArrivals;
    private int weekendArrivals;

    public CustomerGroup(Class carType, int weekDayArrivals, int weekendArrivals) {
        this.carType = carType;
        this.weekDayArrivals = weekDayArrivals;
        this.weekendArrivals = weekendArrivals;
    }

    public void setEntranceCarQueue(CarQueue queue) {
        this.entranceCarQueue = queue;
    }

    public CarQueue getEntranceCarQueue() {
        return entranceCarQueue;
    }

    public Car getNewCar() {
        try{
            return (Car) carType.getConstructor().newInstance();
        }catch (Exception e){
            return null;
        }
    }

    public int getNumberOfCars(int day){
        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = day < 6
                ? weekDayArrivals
                : weekendArrivals;

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.3;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        return (int)Math.round(numberOfCarsPerHour / 60);
    }

}

package parkeersimulator.model;

import java.util.ArrayList;

/**
 * This class represents the whole car park containing the location of all cars and the statistics with it.
 *
 * @version 18.01.2019.
 */
public class CarPark {

    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;

    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfOpenSpots;
    private Car[][][] cars;

    private ArrayList<CustomerGroup> customerGroups;

    public CarPark(int numberOfFloors, int numberOfRows, int numberOfPlaces) {

        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfOpenSpots = numberOfFloors * numberOfRows * numberOfPlaces;
        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];

        paymentCarQueue = new CarQueue(Settings.get("queue.payment.speed"));
        exitCarQueue = new CarQueue(Settings.get("queue.exit.speed"));
        createGroups();
    }

    /**
     * Create the different customer groups used in the simulation.
     */
    private void createGroups() {
        this.customerGroups = new ArrayList<>();

        CustomerGroup adHoc = new CustomerGroup(AdHocCar.class, Settings.get("adhoc.arrivals.weekday"), Settings.get("adhoc.arrivals.weekend"));
        adHoc.setEntranceCarQueue(new CarQueue(Settings.get("queue.adhoc.speed")));
        customerGroups.add(adHoc);

        CustomerGroup parkingPass = new CustomerGroup(ParkingPassCar.class, Settings.get("pass.arrivals.weekday"), Settings.get("pass.arrivals.weekend"));
        parkingPass.setEntranceCarQueue(new CarQueue(Settings.get("queue.pass.speed")));
        customerGroups.add(parkingPass);
    }

    /**
     * Handle all cars that are ready to leave and sent them to either the payment or exit queue.
     */
    private void carsReadyToLeave() {
        // Add leaving cars to the payment queue.
        Car car = getFirstLeavingCar();
        while (car != null) {
            if (car.getHasToPay()) {
                car.setIsPaying(true);
                paymentCarQueue.addCar(car);
            } else {
                carLeavesSpot(car);
            }
            car = getFirstLeavingCar();
        }
    }

    /**
     * Handle all cars currently in the payment queue and let them leave once paid.
     */
    private void carsPaying() {
        // Let cars pay.
        int i = 0;
        while (paymentCarQueue.carsInQueue() > 0 && i < paymentCarQueue.getSpeed()) {
            Car car = paymentCarQueue.removeCar();
            // TODO Handle payment.
            carLeavesSpot(car);
            i++;
        }
    }

    /**
     * Let the cars leave the car park.
     */
    private void carsLeaving() {
        // Let cars leave.
        int i = 0;
        while (exitCarQueue.carsInQueue() > 0 && i < exitCarQueue.getSpeed()) {
            exitCarQueue.removeCar();
            i++;
        }
    }

    /**
     * Remove a car from it's current spot.
     *
     * @param car Car that leaves the car park.
     */
    private void carLeavesSpot(Car car) {
        removeCarAt(car.getLocation());
        exitCarQueue.addCar(car);
    }

    /**
     * Check if a given location is valid
     *
     * @param location Location to check
     * @return if the location is valid
     */
    private boolean locationIsValid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        return floor >= 0 && floor < numberOfFloors && row >= 0 && row <= numberOfRows && place >= 0 && place <= numberOfPlaces;
    }

    /**
     * Reset the simulation.
     */
    public void reset() {
        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
        paymentCarQueue.reset();
        exitCarQueue.reset();
        for (CustomerGroup group : customerGroups) {
            group.getEntranceCarQueue().reset();
        }
    }

    /**
     * Return the number of floors in the simulation.
     *
     * @return Integer of the number of floors.
     */
    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    /**
     * Return the number of rows per floor in the simulation.
     *
     * @return Integer of the number of rows.
     */
    public int getNumberOfRows() {
        return numberOfRows;
    }

    /**
     * Return the number of places per row per floor in the simulation.
     *
     * @return Integer of the number of places.
     */
    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    /**
     * Return the number of open spots in the simulation.
     *
     * @return Integer of the number of open spots.
     */
    public int getNumberOfOpenSpots() {
        return numberOfOpenSpots;
    }

    /**
     * Handle the entrance of all cars today.
     *
     * @param day Integer the current day.
     */
    public void handleEntrance(int day) {
        carsArriving(day);
        for (CustomerGroup group : customerGroups) {
            carsEntering(group.getEntranceCarQueue());
        }
    }

    /**
     * Handle the exit of cars in the car park.
     */
    public void handleExit() {
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }

    /**
     * Handle all arriving cars and add them to the entrance queue
     *
     * @param day Integer of the current day
     */
    public void carsArriving(int day) {
        for (CustomerGroup group : customerGroups) {
            for (int i = 0; i < group.getNumberOfCars(day); i++) {
                group.getEntranceCarQueue().addCar(group.getNewCar());
            }
        }
    }

    /**
     * Handle all cars entering the current queue and direct them to the correct parking spot.
     *
     * @param queue Car queue which has cars entering
     */
    public void carsEntering(CarQueue queue) {
        int i = 0;
        // Remove car from the front of the queue and assign to a parking space.
        while (queue.carsInQueue() > 0 && getNumberOfOpenSpots() > 0 && i < queue.getSpeed()) {
            Car car = queue.removeCar();
            Location freeLocation = getFirstFreeLocation();
            setCarAt(freeLocation, car);
            i++;
        }
    }

    /**
     * Get the car currently at that location.
     * Return null if no car is at that location.
     *
     * @param location Location that needs to be checked.
     * @return Car if currently at that location.
     */
    public Car getCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

    /**
     * Add a car in the car park if the location is available.
     *
     * @param location New Location of the car
     * @param car      Car that is currently entering the car park.
     * @return Boolean if the car could successfully enter.
     */
    public boolean setCarAt(Location location, Car car) {
        if (!locationIsValid(location)) {
            return false;
        }
        Car oldCar = getCarAt(location);
        if (oldCar == null) {
            cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
            car.setLocation(location);
            numberOfOpenSpots--;
            return true;
        }
        return false;
    }

    /**
     * Remove a car if it is present in the given location.
     *
     * @param location Location of the car.
     * @return
     */
    public Car removeCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        Car car = getCarAt(location);
        if (car == null) {
            return null;
        }
        cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
        car.setLocation(null);
        numberOfOpenSpots++;
        return car;
    }

    /**
     * Get the first unused location in the car park.
     *
     * @return Free Location in the car park if available.
     */
    public Location getFirstFreeLocation() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    if (getCarAt(location) == null) {
                        return location;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Return the first car that is due to leave.
     *
     * @return Car that is leaving.
     */
    public Car getFirstLeavingCar() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                        return car;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Advance the amount of minutes the cars have left in all cars currently present in the car park.
     */
    public void tick() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null) {
                        car.tick();
                    }
                }
            }
        }
    }
}

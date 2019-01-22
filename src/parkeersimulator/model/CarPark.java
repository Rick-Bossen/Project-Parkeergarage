package parkeersimulator.model;

import parkeersimulator.framework.Model;
import parkeersimulator.utility.Settings;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class represents the whole car park containing the location of all cars and the statistics with it.
 *
 * @version 18.01.2019.
 */
public class CarPark extends Model {

    private final CarQueue paymentCarQueue;
    private final CarQueue exitCarQueue;

    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfOpenSpots;
    private int numberOfParkingPassSpots;
    private Car[][][] cars;

    private ArrayList<CustomerGroup> customerGroups;
    private CustomerGroup reservations;
    private final ArrayList<ReservedAdHocCar> reservationCarList;
    private final ArrayList<Reservation> reservationList;


    public CarPark() {
        paymentCarQueue = new CarQueue(Settings.get("queue.payment.speed"));
        exitCarQueue = new CarQueue(Settings.get("queue.exit.speed"));
        reservationCarList = new ArrayList<>();
        reservationList = new ArrayList<>();
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

        reservations = new CustomerGroup(ReservedSpot.class, Settings.get("reserved.arrivals.weekday"), Settings.get("reserved.arrivals.weekend"));
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

    public void queueReservations(int day) {
        for (int i = 0; i < reservations.getNumberOfCars(day - 1); i++) {
            Reservation reservation = new Reservation();
            reservationList.add(reservation);
        }

        CustomerGroup parkingPassGroup = customerGroups.get(1);
        CarQueue queue = parkingPassGroup.getEntranceCarQueue();
        if(!reservationList.isEmpty()) {
            for (Reservation reservation : reservationList) {
                if (reservation.getTimeToReserve() == 30) {
                    ReservedSpot car = (ReservedSpot) reservations.getNewCar();
                    Location freeLocation = getFirstFreeLocation(car);

                    if (freeLocation != null) {
                        reservation.setLocation(freeLocation);
                        setCarAt(freeLocation, car);
                        ReservedAdHocCar reservedAdHocCar = new ReservedAdHocCar();
                        reservedAdHocCar.setId(reservation.getId());
                        reservedAdHocCar.setTimeUntilArrival();
                        reservationCarList.add(reservedAdHocCar);
                    }
                }
            }
        }

        if(!reservationCarList.isEmpty()) {
            for (ReservedAdHocCar car : reservationCarList) {
                if (car.getTimeUntilArrival() == 0) {
                    queue.addCar(car);
                }
            }
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
        Location location = car.getLocation();
        removeCarAt(car.getLocation());
        exitCarQueue.addCar(car);
        if(car instanceof ParkingPassCar && ((ParkingPassCar) car).isAtReservedSpot()) {
            setCarAt(location,new ParkingPassSpot());
        }
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
        return floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces;
    }

    /**
     * Set the size of the car park
     * @param numberOfFloors number of floors
     * @param numberOfRows number of rows
     * @param numberOfPlaces number of places
     */
    public void setSize(int numberOfFloors, int numberOfRows, int numberOfPlaces, int numberOfParkingPassSpots) {
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfParkingPassSpots = numberOfParkingPassSpots;
        this.numberOfOpenSpots = numberOfFloors * numberOfRows * numberOfPlaces;
        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
        setParkingPassSpots(numberOfParkingPassSpots);

        updateViews();
    }

    /**
     * Reset the simulation with a set number of floors.
     */
    public void reset(int numberOfFloors, int numberOfRows, int numberOfPlaces, int numberOfParkingPassSpots) {
        paymentCarQueue.reset();
        exitCarQueue.reset();
        reservationList.clear();
        reservationCarList.clear();
        for (CustomerGroup group : customerGroups) {
            group.getEntranceCarQueue().reset();
        }
        setSize(numberOfFloors, numberOfRows, numberOfPlaces, numberOfParkingPassSpots);
    }

    /**
     * Reset the simulation.
     */
    public void reset() {
        reset(numberOfFloors, numberOfRows, numberOfPlaces, numberOfParkingPassSpots);
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
    private int getNumberOfOpenSpots() {
        return numberOfOpenSpots;
    }

    /**
     * Return payment car queue
     * @return queue
     */
    public CarQueue getPaymentCarQueue(){
        return paymentCarQueue;
    }

    /**
     * Return payment car queue
     * @return queue
     */
    public CarQueue getExitCarQueue(){
        return exitCarQueue;
    }

    /**
     * Return all created customer groups.
     * @return customer groups.
     */
    public ArrayList<CustomerGroup> getCustomerGroups(){
        return customerGroups;
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
        updateViews();
    }

    /**
     * Handle the exit of cars in the car park.
     */
    public void handleExit() {
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
        updateViews();
    }

    /**
     * Handle all arriving cars and add them to the entrance queue
     *
     * @param day Integer of the current day
     */
    private void carsArriving(int day) {
        for (CustomerGroup group : customerGroups) {
            if(group.getEntranceCarQueue() != null) {
                int enteringCars = group.getNumberOfCars(day);
                for (int i = 0; i < enteringCars; i++) {
                    group.getEntranceCarQueue().addCar(group.getNewCar());
                }
            }
        }
    }

    /**
     * Handle all cars entering the current queue and direct them to the correct parking spot.
     *
     * @param queue Car queue which has cars entering
     */
    private void carsEntering(CarQueue queue) {
        int i = 0;
        // Remove car from the front of the queue and assign to a parking space.
        while (queue.carsInQueue() > 0 && getNumberOfOpenSpots() > 0 && i < queue.getSpeed()) {
            Car car = queue.removeCar();
            if(!(car instanceof ReservedAdHocCar)) {
                Location freeLocation = getFirstFreeLocation(car);
                setCarAt(freeLocation, car);
            } else {
                reservationCarList.remove(car);
                boolean found = false;
                for (int index = 0; index < reservationList.size() && !found; index++) {
                    Reservation reservation = reservationList.get(index);

                    String id = reservation.getId();
                    String carId = ((ReservedAdHocCar) car).getId();
                    if (id.equals(carId)) {
                        car.setLocation(reservation.getLocation());
                        if(getCarAt(car.getLocation()) instanceof ReservedSpot) {
                            setCarAt(car.getLocation(), car);
                            reservationList.remove(i);
                        } else {
                            Random r = new Random();
                            if (r.nextFloat() > 0.5f) {
                                Car newCar = new AdHocCar();
                                newCar.setLocation(getFirstFreeLocation(newCar));
                                newCar.setMinutesLeft(car.getMinutesLeft());
                                setCarAt(newCar.getLocation(),newCar);
                                reservationList.remove(i);
                            } else {
                                reservationList.remove(i);
                            }
                        }
                        found = true;
                    }
                }
            }

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
        if (locationIsValid(location)) {
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
    private void setCarAt(Location location, Car car) {
        if (locationIsValid(location)) {
            return;
        }
        Car oldCar = getCarAt(location);
        if (oldCar == null || oldCar instanceof ParkingPassSpot && car instanceof ParkingPassCar || oldCar instanceof ReservedSpot && car instanceof ReservedAdHocCar) {
            if (oldCar instanceof ParkingPassSpot) {
                ((ParkingPassCar) car).setAtReservedSpot(true);
            }
            cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
            car.setLocation(location);
            if(car instanceof ParkingPassCar || car instanceof  ParkingPassSpot || oldCar != null)
            numberOfOpenSpots--;
        }
    }

    /**
     * Remove a car if it is present in the given location.
     *
     * @param location Location of the car.
     * @return
     */
    private void removeCarAt(Location location) {
        if (locationIsValid(location)) {
            return;
        }
        Car car = getCarAt(location);
        if (car == null) {
            return;
        }
        cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
        car.setLocation(null);
        numberOfOpenSpots++;
    }

    /**
     * Get the first unused location in the car park.
     *
     * @return Free Location in the car park if available.
     */
    private Location getFirstFreeLocation(Car car) {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car currentCar = getCarAt(location);
                    if (currentCar == null || car instanceof ParkingPassCar && currentCar instanceof ParkingPassSpot) {
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
    private Car getFirstLeavingCar() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying() && !(car instanceof ParkingPassSpot)) {
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

        if(!reservationCarList.isEmpty()) {
            for(ReservedAdHocCar car : reservationCarList) {
                car.incrementArrivalTime();
            }
        }

        if(!reservationList.isEmpty()) {
            for (Reservation reservation : reservationList) {
                reservation.tick();
            }
        }
    }

    private void setParkingPassSpots(int amount)
    {
        int currentFloor = 0;
        int currentRow = 0;
        int currentPlace = 0;
        int totalPlacesSet = 0;

        if (amount > numberOfPlaces * numberOfRows * numberOfFloors) {
            return;
        }

        for (int i = 0; i < amount; i++) {
            if(currentPlace == numberOfPlaces) {
                currentRow++;
                currentPlace = 0;
                if(currentRow == numberOfRows){
                    currentFloor++;
                    currentRow = 0;
                }
            }
            cars[currentFloor][currentRow][currentPlace] = new ParkingPassSpot();
            cars[currentFloor][currentRow][currentPlace].setLocation(new Location(currentFloor,currentRow,currentPlace));
            currentPlace++;

        }
    }

}

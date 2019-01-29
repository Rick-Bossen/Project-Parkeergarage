package parkeersimulator.model.carpark;

import parkeersimulator.enums.settings.QueueSettings;
import parkeersimulator.enums.settings.SimulationSettings;
import parkeersimulator.framework.Model;
import parkeersimulator.model.Clock;
import parkeersimulator.model.Event;
import parkeersimulator.model.Location;
import parkeersimulator.model.Reservation;
import parkeersimulator.model.car.*;
import parkeersimulator.model.statistics.StatisticsList;
import parkeersimulator.utility.Settings;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class represents the whole car park containing the location of all cars and the statistics with it.
 *
 * @version 28.01.2019.
 */
public class CarPark extends Model {

    private StatisticsList statistics;

    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;

    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfOpenSpots;
    private int numberOfParkingPassSpots;
    private Car[][][] cars;

    private ArrayList<CustomerGroup> customerGroups;
    private CustomerGroup reservations;
    private ArrayList<ReservedAdHocCar> reservationCarList;
    private ArrayList<Reservation> reservationList;


    public CarPark(StatisticsList statistics) {
        paymentCarQueue = new CarQueue(QueueSettings.QUEUE_PAYMENT_SPEED.getValue());
        exitCarQueue = new CarQueue(QueueSettings.QUEUE_EXIT_SPEED.getValue());
        reservationCarList = new ArrayList<>();
        reservationList = new ArrayList<>();
        this.statistics = statistics;
        createGroups();
    }

    /**
     * Create the different customer groups used in the simulation.
     */
    private void createGroups() {
        ArrayList<Event> events = new ArrayList<>();
        Event theaterEvent = new Event(900);
        theaterEvent.addDay(DayOfWeek.FRIDAY.getValue());
        theaterEvent.addDay(DayOfWeek.SATURDAY.getValue(), 18, 24);
        theaterEvent.addDay(DayOfWeek.SUNDAY.getValue(), 12, 18);
        events.add(theaterEvent);

        Event lateOpening = new Event(600);
        lateOpening.addDay(DayOfWeek.THURSDAY.getValue(), 18, 24);
        events.add(lateOpening);
        this.customerGroups = new ArrayList<>();

        CustomerGroup adHoc = new CustomerGroup(AdHocCar.class, SimulationSettings.ADHOC_WEEKDAY.getValue(), SimulationSettings.ADHOC_WEEKEND.getValue());
        adHoc.setEntranceCarQueue(new CarQueue(QueueSettings.QUEUE_ADHOC_SPEED.getValue()));
        adHoc.setEvents(events);
        customerGroups.add(adHoc);

        CustomerGroup parkingPass = new CustomerGroup(ParkingPassCar.class, SimulationSettings.PASSHOLDERS_WEEKDAY.getValue(), SimulationSettings.PASSHOLDERS_WEEKEND.getValue());
        parkingPass.setEntranceCarQueue(new CarQueue(QueueSettings.QUEUE_PASSHOLDERS_SPEED.getValue()));
        customerGroups.add(parkingPass);

        reservations = new CustomerGroup(ReservedSpot.class, SimulationSettings.RESERVATIONS_WEEKDAY.getValue(), SimulationSettings.PASSHOLDERS_WEEKEND.getValue());
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
     * Adds new reservations to the simulation.
     *
     * @param day current day of the week.
     */
    public void queueReservations(int day) {
        for (int i = 0; i < reservations.getNumberOfCars(day + 1); i++) {
            Reservation reservation = new Reservation();
            reservationList.add(reservation);
            handlePayment("reserved");
        }

        CustomerGroup parkingPassGroup = customerGroups.get(1);
        CarQueue queue = parkingPassGroup.getEntranceCarQueue();
        if (!reservationList.isEmpty()) {
            for (Reservation reservation : reservationList) {
                if (reservation.getTimeToReserve() == 45) {
                    ReservedSpot car = (ReservedSpot) reservations.getNewCar();
                    Location freeLocation = getFirstFreeLocation(car);

                    if (freeLocation != null) {
                        reservation.setLocation(freeLocation);
                        setCarAt(freeLocation, car);
                        ReservedAdHocCar reservedAdHocCar = new ReservedAdHocCar();
                        reservedAdHocCar.setId(reservation.getId());
                        reservedAdHocCar.setTimeUntilArrival();
                        reservationCarList.add(reservedAdHocCar);
                    } else {
                        // No reason to continue if there is no free spot.
                        break;
                    }
                }
            }
        }

        if (!reservationCarList.isEmpty()) {
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
            handlePayment("adhoc");
            carLeavesSpot(car);
            i++;
        }
    }

    /**
     * Handle a payment for the specified car type.
     *
     * @param id the id of the car type.
     */
    private void handlePayment(String id) {
        int price = Settings.get("price." + id);
        statistics.add("profit.total", price);
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
        if (car instanceof ParkingPassCar) {
            setCarAt(location, new ParkingPassSpot());
        }
    }

    /**
     * Set the size of the car park.
     *
     * @param numberOfFloors number of floors.
     * @param numberOfRows   number of rows.
     * @param numberOfPlaces number of places.
     * @param numberOfParkingPassSpots number of parking pass spots.
     */
    private void setSize(int numberOfFloors, int numberOfRows, int numberOfPlaces, int numberOfParkingPassSpots) {
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
     *
     * @param numberOfFloors number of floors.
     * @param numberOfRows number of rows.
     * @param numberOfPlaces number of places.
     * @param numberOfParkingPassSpots number of parking pass spots.
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
     * Return payment car queue.
     *
     * @return queue.
     */
    public CarQueue getPaymentCarQueue() {
        return paymentCarQueue;
    }

    /**
     * Return payment car queue.
     *
     * @return queue.
     */
    public CarQueue getExitCarQueue() {
        return exitCarQueue;
    }

    /**
     * Return all created customer groups.
     *
     * @return customer groups.
     */
    public ArrayList<CustomerGroup> getCustomerGroups() {
        ArrayList<CustomerGroup> groups = (ArrayList<CustomerGroup>) customerGroups.clone();
        groups.add(reservations);
        return groups;
    }

    /**
     * Handle the entrance of all cars today.
     *
     * @param clock clock of the current date/time.
     */
    public void handleEntrance(Clock clock) {
        carsArriving(clock);
        for (CustomerGroup group : customerGroups) {
            carsEntering(group.getEntranceCarQueue());
        }
    }

    /**
     * Handle the exit of cars in the car park.
     */
    private void handleExit() {
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }

    /**
     * Handle all arriving cars and add them to the entrance queue.
     *
     * @param clock Integer of the current day.
     */
    private void carsArriving(Clock clock) {
        for (CustomerGroup group : customerGroups) {
            if (group.getEntranceCarQueue() != null) {
                int enteringCars = group.getNumberOfCars(clock);
                for (int i = 0; i < enteringCars; i++) {
                    group.getEntranceCarQueue().addCar(group.getNewCar());
                }
            }
        }
    }

    /**
     * Handle all cars entering the current queue and direct them to the correct parking spot.
     *
     * @param queue Car queue which has cars entering.
     */
    private void carsEntering(CarQueue queue) {
        Random r = new Random();
        int i = 0;
        // Remove car from the front of the queue and assign to a parking space.
        while (queue.carsInQueue() > 0 && numberOfOpenSpots > 0 && i < queue.getSpeed()) {
            Car car = queue.removeCar();

            if(car instanceof ReservedAdHocCar){
                statistics.add("cars.entered.reserved", 1);
                statistics.add("cars.entered", 1);
            }else if(car instanceof AdHocCar){
                statistics.add("cars.entered.adhoc", 1);
                statistics.add("cars.entered", 1);
            }else if(car instanceof ParkingPassCar){
                statistics.add("cars.entered.pass", 1);
                statistics.add("cars.entered", 1);
            }

            if (!(car instanceof ReservedAdHocCar)) {
                Location freeLocation = getFirstFreeLocation(car);
                if (freeLocation != null) {
                    setCarAt(freeLocation, car);
                }
            } else {
                reservationCarList.remove(car);
                boolean found = false;
                for (int index = 0; index < reservationList.size() && !found; index++) {
                    Reservation reservation = reservationList.get(index);

                    String id = reservation.getId();
                    String carId = ((ReservedAdHocCar) car).getId();
                    if (id.equals(carId)) {
                        car.setLocation(reservation.getLocation());
                        if (getCarAt(car.getLocation()) instanceof ReservedSpot) {
                            setCarAt(car.getLocation(), car);
                            reservationList.remove(i);
                        } else {
                            if (r.nextFloat() > 0.5f) {
                                Car newCar = new AdHocCar();
                                Location firstFreeLocation = getFirstFreeLocation(newCar);
                                if(firstFreeLocation != null) {
                                    newCar.setLocation(firstFreeLocation);
                                    newCar.setMinutesLeft(car.getMinutesLeft());
                                    setCarAt(newCar.getLocation(), newCar);
                                    reservationList.remove(i);
                                }
                            } else {
                                reservationList.remove(i);
                            }
                        }
                        found = true;
                    }
                }
            }

            // Car leaves because of the queue length.
            while (queue.carsInQueue() > 20 && r.nextFloat() > 0.6f) {
                queue.removeCar();
                statistics.add("cars.missed", 1);
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
        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

    /**
     * Add a car in the car park if the location is available.
     *
     * @param location New Location of the car.
     * @param car      Car that is currently entering the car park.
     */
    private void setCarAt(Location location, Car car) {
        Car oldCar = getCarAt(location);
        if (oldCar == null || oldCar instanceof ParkingPassSpot && car instanceof ParkingPassCar || oldCar instanceof ReservedSpot && car instanceof ReservedAdHocCar) {
            cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
            car.setLocation(location);
            if (oldCar == null) {
                numberOfOpenSpots--;
            }
        }
    }

    /**
     * Remove a car if it is present in the given location.
     *
     * @param location Location of the car.
     */
    private void removeCarAt(Location location) {
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
     * @param car the car that needs a free Location.
     * @return Free Location in the car park if available.
     */
    private Location getFirstFreeLocation(Car car) {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car currentCar = getCarAt(location);
                    if (car instanceof ParkingPassCar && currentCar instanceof ParkingPassSpot) {
                        return location;
                    }
                    if (currentCar == null && !(car instanceof ParkingPassCar)) {
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

        if (!reservationCarList.isEmpty()) {
            for (ReservedAdHocCar car : reservationCarList) {
                car.decrementArrivalTime();
            }
        }

        if (!reservationList.isEmpty()) {
            for (Reservation reservation : reservationList) {
                reservation.tick();
            }
        }

        handleExit();
    }

    /**
     * Sets the amount of parking spots reserved for pass holders only.
     *
     * @param amount the amount of spots to be reserved.
     */
    private void setParkingPassSpots(int amount) {
        int currentFloor = 0;
        int currentRow = 0;
        int currentPlace = 0;

        if (amount > numberOfPlaces * numberOfRows * numberOfFloors) {
            return;
        }

        for (int i = 0; i < amount; i++) {
            if (currentPlace == numberOfPlaces) {
                currentRow++;
                currentPlace = 0;
                if (currentRow == numberOfRows) {
                    currentFloor++;
                    currentRow = 0;
                }
            }
            cars[currentFloor][currentRow][currentPlace] = new ParkingPassSpot();
            cars[currentFloor][currentRow][currentPlace].setLocation(new Location(currentFloor, currentRow, currentPlace));
            currentPlace++;
        }
    }

    /**
     * Updates the view for the parking garage.
     */
    public void updateFloors() {
        updateViews();
    }

}

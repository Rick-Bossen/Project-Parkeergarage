package parkeersimulator.model;

/**
 * This class represents the location of the car in the car park.
 * It is based on that the car park has multiple floors.
 *
 * @version 18.01.2019
 */
public class Location {

    private int floor;
    private int row;
    private int place;

    /**
     * Constructor for objects of class Location
     */
    public Location(int floor, int row, int place) {
        this.floor = floor;
        this.row = row;
        this.place = place;
    }

    /**
     * Floor of the current location.
     *
     * @return Integer which represents the floor of the car park.
     */
    public int getFloor() {
        return floor;
    }

    /**
     * Row of the current location.
     *
     * @return Integer which represents the row of the car park.
     */
    public int getRow() {
        return row;
    }

    /**
     * Row of the current location.
     *
     * @return Integer which represents the row of the car park.
     */
    public int getPlace() {
        return place;
    }

}
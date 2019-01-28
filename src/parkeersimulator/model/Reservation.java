package parkeersimulator.model;

import java.util.Random;
import java.util.UUID;

/**
 * This class represents a single reservation.
 * Contains the Location, time until the reservation and an unique id.
 *
 * @version 22.01.2019.
 */
public class Reservation {

    private Location location;
    private int timeToReserve;
    private String id;

    public Reservation() {
        Random random = new Random();
        timeToReserve = 60 + (int) (random.nextFloat() * (1440 - 60));

        id = UUID.randomUUID().toString();
    }

    /**
     * Returns the Location of the reservation.
     *
     * @return the Location of the reservation.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets the Location for the reservation.
     *
     * @param location the Location to be set.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Returns the amount of ticks until the reservation.
     *
     * @return the amount of ticks until the reservation.
     */
    public int getTimeToReserve() {
        return timeToReserve;
    }

    /**
     * Returns the id of the reservation.
     *
     * @return the id of the reservation.
     */
    public String getId() {
        return id;
    }

    /**
     * Decrements the timeToReserve.
     */
    public void tick() {
        timeToReserve--;
    }
}

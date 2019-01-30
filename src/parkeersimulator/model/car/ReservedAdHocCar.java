package parkeersimulator.model.car;

import parkeersimulator.enums.theme.ThemeColors;

import java.util.Random;

/**
 * This class represents an AdHocCar that has been assigned to a specific reservation,
 * Contains all the AdHocCar data, the time until arrival and the reservation id.
 *
 * @version 28.01.2019.
 */
public class ReservedAdHocCar extends AdHocCar {

    private String id;
    private int timeUntilArrival;

    public ReservedAdHocCar() {
        super();
        setColor(ThemeColors.CAR_RESERVATION.getColor());
        setBackground(ThemeColors.CAR_RESERVATION_SPOT.getColor());
        this.setHasToPay(false);
    }

    /**
     * Returns the id of the linked reservation.
     *
     * @return the id of the linked reservation.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id that is linked to a reservation.
     *
     * @param id the id of the linked reservation.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets a random time of arrival between 5 and 55 ticks from the method call.
     */
    public void setTimeUntilArrival() {
        Random random = new Random();
        timeUntilArrival = (int) (5 + random.nextFloat() * 50);
    }

    /**
     * Returns the time until arrival.
     *
     * @return the time until arrival.
     */
    public int getTimeUntilArrival() {
        return timeUntilArrival;
    }

    /**
     * Decrements the time until arrival.
     */
    public void decrementArrivalTime() {
        timeUntilArrival--;
    }
}

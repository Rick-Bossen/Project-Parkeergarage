package parkeersimulator.model;

import java.awt.*;

/**
 * This class represents a car which contains a parking spot reserved for passholders only,
 * Contains the color of the car.
 *
 * @version 20.01.2019.
 */
public class ParkingPassSpot extends Car {

    private static final Color COLOR = Color.ORANGE;

    /**
     * Return the Color of the car.
     *
     * @return the Color of the car.
     */
    public Color getColor() {
        return COLOR;
    }
}

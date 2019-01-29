package parkeersimulator.model.car;

import parkeersimulator.model.car.Car;

import java.awt.*;

/**
 * This class represents a car which contains a parking spot reserved for passholders only,
 * Contains the color of the car.
 *
 * @version 27.01.2019.
 */
public class ParkingPassSpot extends Car {

    private static final Color COLOR = Color.ORANGE;

    @Override
    public Color getColor() {
        return COLOR;
    }
}

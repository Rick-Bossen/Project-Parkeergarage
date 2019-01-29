package parkeersimulator.model.car;

import parkeersimulator.model.car.Car;

import java.awt.*;

/**
 * This class represents a car which contains a parking spot reserved for ReservedAdHocCars only,
 * Contains the color of the car.
 *
 * @version 27.01.2019.
 */
public class ReservedSpot extends Car {

    private final Color COLOR = Color.green;

    public ReservedSpot() {
        setMinutesLeft(30);
    }

    @Override
    public Color getColor() {
        return COLOR;
    }
}

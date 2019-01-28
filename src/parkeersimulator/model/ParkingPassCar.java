package parkeersimulator.model;

import java.awt.*;
import java.util.Random;

/**
 * This class represents a car which contains a customer that has a parking pass,
 * Contains the color of the car.
 *
 * @version 13.01.2019
 */
public class ParkingPassCar extends Car {

    private static final Color COLOR = Color.blue;

    public ParkingPassCar() {
        Random random = new Random();
        int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(false);
    }

    /**
     * Return the color of the car.
     *
     * @return Color the color of the car.
     */
    public Color getColor() {
        return COLOR;
    }
}

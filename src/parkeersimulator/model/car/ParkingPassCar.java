package parkeersimulator.model.car;

import parkeersimulator.enums.theme.ThemeColors;

import java.util.Random;

/**
 * This class represents a car which contains a customer that has a parking pass,
 * Contains the color of the car.
 *
 * @version 28.01.2019.
 */
public class ParkingPassCar extends Car {

    public ParkingPassCar() {
        setColor(ThemeColors.CAR_PASSHOLDER.getColor());
        setBackground(ThemeColors.CAR_PASSHOLDER_SPOT.getColor());
        Random random = new Random();
        int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(false);
    }
}

package parkeersimulator.model.car;

import parkeersimulator.enums.theme.ThemeColors;

import java.util.Random;

/**
 * This class represents a car which contains a customer that pays ad hoc,
 * Contains the color of the car.
 *
 * @version 28.01.2019.
 */
public class AdHocCar extends Car {

    public AdHocCar() {
        Random random = new Random();
        setColor(ThemeColors.CAR_ADHOC.getColor());
        int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
    }

}

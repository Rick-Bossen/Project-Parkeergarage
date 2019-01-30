package parkeersimulator.model.car;

import parkeersimulator.enums.theme.ThemeColors;

/**
 * This class represents a car which contains a parking spot reserved for ReservedAdHocCars only,
 * Contains the color of the car.
 *
 * @version 27.01.2019.
 */
public class ReservedSpot extends Car {

    public ReservedSpot() {
        setColor(ThemeColors.CAR_RESERVATION_SPOT.getColor());
        setMinutesLeft(30);
    }

}

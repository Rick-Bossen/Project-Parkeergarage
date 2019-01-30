package parkeersimulator.model.car;

import parkeersimulator.enums.theme.ThemeColors;

/**
 * This class represents a car which contains a parking spot reserved for passholders only,
 * Contains the color of the car.
 *
 * @version 27.01.2019.
 */
public class ParkingPassSpot extends Car {

    public ParkingPassSpot(){
        setColor(ThemeColors.CAR_PASSHOLDER_SPOT.getColor());
    }

}

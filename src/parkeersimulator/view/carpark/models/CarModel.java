package parkeersimulator.view.carpark.models;

import java.awt.*;

/**
 * Represents the visual implementation of the car.
 */
abstract public class CarModel {

    /**
     * Return all colors used in the drawing.
     * @param color Base color of the car.
     * @return color.
     */
    abstract public Color[] getColors(Color color);

    /**
     * Return the mapping of the car.
     */
    abstract public int[][] getMapping();

}

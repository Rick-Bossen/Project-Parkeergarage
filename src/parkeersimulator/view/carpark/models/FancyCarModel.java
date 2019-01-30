package parkeersimulator.view.carpark.models;

import java.awt.*;

public class FancyCarModel extends CarModel {

    /**
     * Return all colors used in the drawing.
     * @param color Base color of the car.
     * @return color.
     */
    @Override
    public Color[] getColors(Color color) {
        return new Color[]{
                Color.white,
                color,
                color.darker(),
                Color.RED,
                Color.YELLOW,
                Color.BLACK.brighter(),
                Color.GRAY.darker().darker(),
                Color.GRAY.darker(),
                color.brighter()
        };
    }

    /**
     * Return the mapping of the car.
     */
    @Override
    public int[][] getMapping() {
        return new int[][]{
                new int[]{0, 0, 0, 0, 1, 5, 5, 5, 0, 0, 0, 0, 0, 0, 1, 5, 5, 5, 0, 0},
                new int[]{0, 0, 0, 0, 2, 2, 2, 2, 2, 6, 6, 6, 6, 1, 6, 1, 1, 4, 4, 0},
                new int[]{0, 0, 0, 3, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 6, 6, 2, 1, 4, 1},
                new int[]{0, 0, 0, 1, 7, 1, 1, 8, 1, 2, 7, 6, 1, 1, 7, 6, 2, 1, 2, 1},
                new int[]{0, 0, 0, 1, 6, 1, 8, 8, 1, 2, 7, 6, 1, 2, 6, 7, 2, 1, 2, 1},
                new int[]{0, 0, 0, 1, 7, 1, 1, 8, 1, 2, 6, 7, 1, 1, 6, 7, 2, 1, 2, 1},
                new int[]{0, 0, 0, 3, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 7, 6, 2, 1, 4, 1},
                new int[]{0, 0, 0, 0, 2, 2, 2, 2, 2, 6, 6, 6, 6, 1, 7, 1, 1, 4, 4, 0},
                new int[]{0, 0, 0, 0, 1, 5, 5, 5, 0, 0, 0, 0, 0, 0, 1, 5, 5, 5, 0, 0},
        };
    }
}

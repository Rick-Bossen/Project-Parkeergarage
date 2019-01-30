package parkeersimulator.view.carpark.models;

import java.awt.*;

public class DefaultCarModel extends CarModel {

    /**
     * Return all colors used in the drawing.
     * @param color Base color of the car.
     * @return color.
     */
    @Override
    public Color[] getColors(Color color) {
        return new Color[]{
                null,
                color,
                color.darker(),
                Color.gray.darker(),
                Color.black,
                Color.red,
                Color.yellow,
                color.brighter()
        };
    }

    /**
     * Return the mapping of the car.
     */
    @Override
    public int[][] getMapping() {
        return new int[][]{
                new int[]{0, 0, 0, 0, 4, 4, 4, 4, 0, 0, 0, 0, 2, 4, 4, 4, 0, 0, 0, 0, 0},
                new int[]{0, 0, 0, 2, 1, 1, 1, 1, 7, 1, 4, 3, 4, 1, 1, 1, 1, 1, 2, 0, 0},
                new int[]{0, 0, 0, 5, 7, 2, 2, 1, 2, 1, 4, 3, 3, 4, 1, 1, 1, 2, 1, 2, 0},
                new int[]{0, 0, 0, 2, 7, 2, 2, 7, 2, 1, 4, 4, 3, 3, 7, 1, 1, 1, 2, 6, 0},
                new int[]{0, 0, 0, 2, 1, 2, 2, 1, 2, 1, 4, 3, 3, 3, 7, 1, 1, 1, 2, 2, 0},
                new int[]{0, 0, 0, 2, 7, 2, 2, 7, 2, 1, 4, 3, 3, 3, 7, 1, 1, 1, 2, 6, 0},
                new int[]{0, 0, 0, 5, 7, 2, 2, 1, 2, 1, 4, 3, 3, 4, 1, 1, 1, 2, 1, 2, 0},
                new int[]{0, 0, 0, 2, 1, 1, 1, 1, 7, 1, 4, 3, 4, 1, 1, 1, 1, 1, 2, 0, 0},
                new int[]{0, 0, 0, 0, 4, 4, 4, 4, 0, 0, 0, 0, 2, 4, 4, 4, 0, 0, 0, 0, 0},
        };
    }
}

package parkeersimulator.view.carpark.models;

import java.awt.*;

public class RaceCarModel extends CarModel {

    /**
     * Return all colors used in the drawing.
     * @param color Base color of the car.
     * @return color.
     */
    @Override
    public Color[] getColors(Color color) {
        return new Color[]{
                Color.WHITE,
                color,
                color.darker(),
                Color.RED,
                Color.YELLOW,
                Color.BLACK.brighter(),
                Color.GRAY.darker().darker(),
                Color.GRAY.darker(),
                color.brighter(),
                color.brighter().brighter().brighter()
        };
    }

    /**
     * Return the mapping of the car.
     */
    @Override
    public int[][] getMapping() {
        return new int[][]{
                new int[]{0, 0, 0, 0, 1, 5, 5, 5, 0, 0, 0, 0, 0, 0, 1, 5, 5, 5, 0, 0},
                new int[]{0, 0, 0, 3, 2, 2, 2, 2, 2, 6, 6, 6, 6, 1, 6, 1, 1, 1, 4, 0},
                new int[]{0, 0, 0, 2, 1, 1, 7, 1, 1, 1, 1, 1, 1, 1, 6, 6, 2, 2, 1, 4},
                new int[]{0, 0, 0, 9, 9, 9, 6, 9, 9, 9, 9, 9, 9, 9, 6, 6, 9, 9, 9, 9},
                new int[]{0, 0, 0, 2, 1, 1, 7, 8, 1, 1, 1, 1, 1, 1, 6, 7, 2, 2, 2, 1},
                new int[]{0, 0, 0, 9, 9, 9, 6, 9, 9, 9, 9, 9, 9, 9, 6, 6, 9, 9, 9, 9},
                new int[]{0, 0, 0, 2, 1, 1, 7, 1, 1, 1, 1, 1, 1, 1, 7, 6, 2, 2, 1, 4},
                new int[]{0, 0, 0, 3, 2, 2, 2, 2, 2, 6, 6, 6, 6, 1, 7, 1, 1, 1, 4, 0},
                new int[]{0, 0, 0, 0, 1, 5, 5, 5, 0, 0, 0, 0, 0, 0, 1, 5, 5, 5, 0, 0},
        };
    }
}

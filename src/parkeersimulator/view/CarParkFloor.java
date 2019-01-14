package parkeersimulator.view;

import parkeersimulator.model.Car;
import parkeersimulator.model.CarPark;
import parkeersimulator.model.Location;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents one of the floors containing the parking
 * It has the ability to draw parking spots and cars accordingly
 *
 * @author M. Visser
 * @version 14.01.2019
 */
public class CarParkFloor extends JPanel {

    private int floor;
    private double factor;

    private final int CAR_WIDTH = 24;
    private final int CAR_HEIGHT = 13;
    private final int PARK_HEIGHT = 401;
    private final int PARK_WIDTH = 234;

    private Dimension size;
    private CarPark carPark;
    private Image carParkImage;


    public CarParkFloor(CarPark carPark, int floor) {
        super();

        this.carPark = carPark;
        this.floor = floor;
        size = new Dimension(0, 0);
        setFactor();
        setMinimumSize(new Dimension(200, 500));
    }

    /**
     * Overridden. Tell the GUI manager how big we would like to be.
     */
    public Dimension getPreferredSize() {
        return new Dimension(800, 500);
    }

    /**
     * Get the calculated horizontal starting point of the object
     * @param location Location of the car/place
     * @return x starting point
     */
    private int getX(Location location) {
        return useFactor(((int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * (CAR_WIDTH + 6));
    }

    /**
     * Get the calculated vertical starting point of the object
     * @param location Location of the car/place
     * @return y starting point
     */
    private int getY(Location location) {
        return 10 + useFactor(location.getPlace() * (CAR_HEIGHT - 1));
    }

    /**
     * Get the calculated horizontal offset of the first car/object
     * @return x offset
     */
    private int getOffsetX() {
        int fullWidth = getX(new Location(0, carPark.getNumberOfRows() - 1, 0)) + useFactor(CAR_WIDTH);
        return (size.width - fullWidth) / 2;
    }

    /**
     * Get the calculated vertical offset of the first car/object
     * @return y offset
     */
    private int getOffsetY() {
        int fullHeight = getY(new Location(0, 0, carPark.getNumberOfPlaces() - 1)) + useFactor(CAR_HEIGHT);
        return (size.height - fullHeight) / 2;
    }

    /**
     * Calculates the factor to scale the drawn images accordingly
     */
    private void setFactor() {
        factor =  Math.min((double) (size.width - 20) / PARK_WIDTH, (double) (size.height - 20) / PARK_HEIGHT);
    }

    /**
     * Short hand for casting the calculated int
     * @param original original number
     * @return resized number
     */
    private int useFactor(int original) {
        return (int)(original * factor);
    }

    /**
     * Draws the image on resize.
     * @param graphics image graphics used to draw the image
     */
    protected void paintComponent(Graphics graphics) {
        if (carParkImage == null) {
            return;
        }

        Dimension currentSize = getSize();
        if (size.equals(currentSize)) {
            graphics.drawImage(carParkImage, 0, 0, null);
        }
        else {
            // Rescale the previous image.
            graphics.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
        }
    }

    /**
     * Update the view.
     * Draws the base floor, parking spots and cars.
     */
    public void updateView()
    {
        // Create a new car park image if the size has changed.
        if (!size.equals(getSize())) {
            size = getSize();
            setFactor();
            carParkImage = createImage(size.width, size.height);
        }
        Graphics graphics = carParkImage.getGraphics();

        drawBase(graphics);

        for(int row = 0; row < carPark.getNumberOfRows(); row++) {
            for(int place = 0; place < carPark.getNumberOfPlaces(); place++) {
                Location location = new Location(floor, row, place);
                Car car = carPark.getCarAt(location);
                drawPlace(graphics, location);
                if(car != null){
                    drawCar(graphics, location, car.getColor());
                }
            }
        }
        repaint();
    }

    /**
     * Draw the base floor with a label
     * @param graphics image graphics used to draw the image
     */
    private void drawBase(Graphics graphics)
    {
        int baseWidth = getX(new Location(0, carPark.getNumberOfRows() - 1 , 0)) + useFactor(CAR_WIDTH) + 30;
        int baseHeight = getY(new Location(0, 0, carPark.getNumberOfPlaces() - 1)) + useFactor(CAR_HEIGHT) + 30;

        graphics.setColor(new Color(225, 225, 225));
        graphics.fillRect(
                getOffsetX() - 15,
                getOffsetY() - 15,
                baseWidth,
                baseHeight
        );

        graphics.setColor(new Color(45, 52, 54));
        graphics.drawRect(
                getOffsetX() - 15,
                getOffsetY() - 15,
                baseWidth,
                baseHeight
        );

        graphics.setFont(new Font("Dubai Light", -1, 16));
        graphics.setColor(Color.white);
        graphics.drawString("Floor: " + floor, getOffsetX(), getOffsetY() + 2);
    }

    /**
     * Paint a place on this car park view.
     *
     * @param graphics image graphics used to draw the image
     * @param location Location of the parking spot
     */
    private void drawPlace(Graphics graphics, Location location) {
        graphics.setColor(Color.white);
        int x = getOffsetX() + getX(location);
        int y = getOffsetY() + getY(location);

        boolean reverse = (location.getRow() + 1) % 2 == 0;
        graphics.fillRect(x, y, useFactor(CAR_WIDTH), 1);
        if(reverse){
            graphics.fillRect(x, y, 1, useFactor(CAR_HEIGHT));
        }else{
            graphics.fillRect(x + useFactor(CAR_WIDTH) - 1, y, 1, useFactor(CAR_HEIGHT));
        }
        graphics.fillRect(x, y + useFactor(CAR_HEIGHT) - 1, useFactor(CAR_WIDTH), 1);

    }

    /**
     *  Paint a car on this car park in a given color.
     *
     * @param graphics image graphics used to draw the image
     * @param location Location of the car
     * @param color Color of the car
     */
    private void drawCar(Graphics graphics, Location location, Color color) {
        graphics.setColor(color);

        if(factor < 1){
            int x = getOffsetX() + getX(location);
            int y = getOffsetY() + getY(location);

            graphics.fillRect(x, y+ 2, useFactor(CAR_WIDTH) - 2, useFactor(CAR_HEIGHT) - 4);
        }else {
            Color[] colors = new Color[]{
                    null,
                    color,
                    color.darker(),
                    Color.gray.darker(),
                    Color.black,
                    Color.red,
                    Color.yellow,
                    color.brighter()
            };
            int[][] mapping = new int[][]{
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
            int startX = 1 + getOffsetX() + getX(location);
            int y = 2 + getOffsetY() + getY(location);
            int x, timesX, timesY;
            boolean reverse = (location.getRow() + 1) % 2 == 0;
            for (int[] colorMap : mapping) {
                timesY = 1;
                for (int currentY = 1; currentY <= useFactor(timesY) - useFactor((timesY - 1)); currentY++) {
                    timesY++;
                    x = startX;
                    if (reverse) {
                        x += useFactor(CAR_WIDTH) - 2;
                    }
                    timesX = 0;
                    for (int colorNumber : colorMap) {
                        timesX++;
                        for (int currentX = 1; currentX <= useFactor(timesX) - useFactor((timesX - 1)); currentX++) {
                            if (colorNumber > 0) {
                                graphics.setColor(colors[colorNumber]);
                                graphics.fillRect(x, y, 1, 1);
                            }
                            if (!reverse) {
                                x++;
                            } else {
                                x--;
                            }
                        }
                    }
                    y++;
                }
            }
        }
    }

}

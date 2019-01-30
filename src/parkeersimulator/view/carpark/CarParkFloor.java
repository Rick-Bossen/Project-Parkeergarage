package parkeersimulator.view.carpark;

import parkeersimulator.enums.settings.GeneralSettings;
import parkeersimulator.enums.theme.ThemeColors;
import parkeersimulator.enums.theme.ThemeFonts;
import parkeersimulator.model.Location;
import parkeersimulator.model.car.Car;
import parkeersimulator.model.car.ParkingPassSpot;
import parkeersimulator.model.car.ReservedSpot;
import parkeersimulator.model.carpark.CarPark;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents one of the floors containing the parking,
 * It has the ability to draw parking spots and cars accordingly.
 *
 * @version 28.01.2019.
 */
class CarParkFloor extends JPanel {

    private final int CAR_WIDTH = 24;
    private final int CAR_HEIGHT = 13;
    private final int PARK_HEIGHT = 400;
    private final int PARK_WIDTH = 230;
    private int floor;
    private double factor;
    private Dimension size;
    private CarPark carPark;
    private Image carParkImage;

    private int[] drawLock = new int[3];


    CarParkFloor(CarPark carPark, int floor) {
        super();

        this.carPark = carPark;
        this.floor = floor;
        size = new Dimension(0, 0);
        drawLock[0] = 0;
        drawLock[1] = 0;
        drawLock[2] = 0;
        setFactor();
    }

    /**
     * Get the calculated horizontal starting point of the object.
     *
     * @param location Location of the car/place.
     * @return x starting point.
     */
    private int getX(Location location) {
        return useFactor(((int) Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * (CAR_WIDTH + 6));
    }

    /**
     * Get the calculated vertical starting point of the object.
     *
     * @param location Location of the car/place.
     * @return y starting point.
     */
    private int getY(Location location) {
        return 10 + useFactor(location.getPlace() * (CAR_HEIGHT - 1));
    }

    /**
     * Get the calculated horizontal offset of the first car/object.
     *
     * @return x offset.
     */
    private int getOffsetX() {
        int fullWidth = getX(new Location(0, carPark.getNumberOfRows() - 1, 0)) + useFactor(CAR_WIDTH);
        return (size.width - fullWidth) / 2;
    }

    /**
     * Get the calculated vertical offset of the first car/object.
     *
     * @return y offset.
     */
    private int getOffsetY() {
        int fullHeight = getY(new Location(0, 0, carPark.getNumberOfPlaces() - 1)) + useFactor(CAR_HEIGHT);
        return (size.height - fullHeight) / 2;
    }

    /**
     * Calculates the factor to scale the drawn images accordingly.
     */
    private void setFactor() {
        factor = Math.min((double) (size.width - 20) / (PARK_WIDTH / 6 * carPark.getNumberOfRows()), (double) (size.height - 20) / (PARK_HEIGHT / 30 * carPark.getNumberOfPlaces()));
    }

    /**
     * Short hand for casting the calculated int.
     *
     * @param original original number.
     * @return resized number.
     */
    private int useFactor(int original) {
        return (int) (original * factor);
    }

    /**
     * Draw the base floor with a label.
     *
     * @param graphics image graphics used to draw the image.
     */
    private void drawBase(Graphics graphics) {
        int baseWidth = getX(new Location(0, carPark.getNumberOfRows() - 1, 0)) + useFactor(CAR_WIDTH) + 30;
        int baseHeight = getY(new Location(0, 0, carPark.getNumberOfPlaces() - 1)) + useFactor(CAR_HEIGHT) + 30;

        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, getWidth(), getHeight());

        graphics.setColor(new Color(195, 195, 195));
        graphics.fillRect(
                getOffsetX() - 15,
                getOffsetY() - 15,
                baseWidth,
                baseHeight
        );

        graphics.setColor(ThemeColors.BACKGROUND_DARK.getColor());
        graphics.drawRect(
                getOffsetX() - 15,
                getOffsetY() - 15,
                baseWidth,
                baseHeight
        );

        graphics.setFont(ThemeFonts.MONOSPACED_LARGE.getFont());
        graphics.setColor(Color.white);
        graphics.drawString("Floor: " + floor, getOffsetX(), getOffsetY() + 2);
    }

    /**
     * Draw the amount of spots on the floor.
     *
     * @param graphics graphics to draw on.
     */
    private void drawSpotString(Graphics graphics){
        graphics.setColor(new Color(195, 195, 195));
        graphics.fillRect(getWidth() - getOffsetX() - 125, getOffsetY() - 11, 115, 18);
        graphics.setFont(ThemeFonts.MONOSPACED_LARGE.getFont());
        graphics.setColor(Color.white);
        String spotString = String.format("Spots:%3d/%3d", carPark.getNumberOfFilledSpotsForFloor(floor), carPark.getTotalNumberOfSpotsForFloor());
        graphics.drawString(spotString, getWidth() - getOffsetX() - 115, getOffsetY() + 2);
    }

    /**
     * Paint a place on this car park view.
     *
     * @param graphics image graphics used to draw the image.
     * @param location Location of the parking spot.
     */
    private void drawPlace(Graphics graphics, Location location) {
        graphics.setColor(Color.white);
        int x = getOffsetX() + getX(location);
        int y = getOffsetY() + getY(location);

        boolean reverse = (location.getRow() + 1) % 2 == 0;
        graphics.fillRect(x, y, useFactor(CAR_WIDTH), 1);
        if (reverse) {
            graphics.fillRect(x, y, 1, useFactor(CAR_HEIGHT));
        } else {
            graphics.fillRect(x + useFactor(CAR_WIDTH) - 1, y, 1, useFactor(CAR_HEIGHT));
        }
        graphics.fillRect(x, y + useFactor(CAR_HEIGHT) - 1, useFactor(CAR_WIDTH), 1);

    }

    /**
     * Draw rectangle in the location of the car.
     *
     * @param graphics Graphics to draw on.
     * @param location Location of the drawing.
     * @param color    Color of the rectangle.
     */
    private void drawRectangle(Graphics graphics, Location location, Color color) {
        graphics.setColor(color);
        boolean reverse = (location.getRow() + 1) % 2 == 0;
        int x = getOffsetX() + getX(location);
        int y = getOffsetY() + getY(location);


        if (!reverse) {
            graphics.fillRect(x, y + 1, useFactor(CAR_WIDTH) - 1, useFactor(CAR_HEIGHT) - 2);
        } else {
            graphics.fillRect(x + 1, y + 1, useFactor(CAR_WIDTH), useFactor(CAR_HEIGHT) - 2);
        }
    }

    /**
     * Paint a car on this car park in a given color.
     *
     * @param graphics image graphics used to draw the image.
     * @param car      current car.
     */
    private void drawCar(Graphics graphics, Car car) {
        Color color = car.getColor();
        Location location = car.getLocation();
        boolean reverse = (location.getRow() + 1) % 2 == 0;

        if (factor < 1 || car instanceof ParkingPassSpot || car instanceof ReservedSpot) {
            drawRectangle(graphics, location, color);
        } else {
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
            int y = useFactor(2) + getOffsetY() + getY(location);
            int x, timesX, timesY;
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

    /**
     * Draws the image on resize.
     *
     * @param graphics image graphics used to draw the image.
     */
    protected void paintComponent(Graphics graphics) {
        if (carParkImage == null) {
            return;
        }

        Dimension currentSize = getSize();
        if (size.equals(currentSize)) {
            graphics.drawImage(carParkImage, 0, 0, null);
        } else {
            // Rescale the previous image.
            graphics.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
        }
    }

    /**
     * Overridden. Tell the GUI manager how big we would like to be.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(GeneralSettings.WIDTH.getValue(), GeneralSettings.HEIGHT.getValue());
    }

    /**
     * Update the view,
     * Draws the base floor, parking spots and cars.
     */
    void updateView() {
        // Create a new car park image if the size has changed.
        boolean shouldRedraw = false;
        if (!size.equals(getSize())) {
            shouldRedraw = true;
            size = getSize();
            carParkImage = createImage(size.width, size.height);
        }
        if (drawLock[0] != carPark.getNumberOfFloors() || drawLock[1] != carPark.getNumberOfRows() || drawLock[2] != carPark.getNumberOfPlaces()) {
            drawLock[0] = carPark.getNumberOfFloors();
            drawLock[1] = carPark.getNumberOfRows();
            drawLock[2] = carPark.getNumberOfPlaces();
            shouldRedraw = true;
        }
        setFactor();
        Graphics graphics = carParkImage.getGraphics();

        if (shouldRedraw) {
            drawBase(graphics);
        }

        drawSpotString(graphics);

        for (int row = 0; row < carPark.getNumberOfRows(); row++) {
            for (int place = 0; place < carPark.getNumberOfPlaces(); place++) {
                Location location = new Location(floor, row, place);
                Car car = carPark.getCarAt(location);
                if (car != null) {
                    if (shouldRedraw || car.isFirstDraw()) {
                        drawRectangle(graphics, location, car.getBackground());
                        car.setFirstDraw(false);
                        drawCar(graphics, car);
                        drawPlace(graphics, location);
                    }
                } else {
                    drawRectangle(graphics, location, new Color(195, 195, 195));
                    drawPlace(graphics, location);
                }
            }
        }
        repaint();
    }

}

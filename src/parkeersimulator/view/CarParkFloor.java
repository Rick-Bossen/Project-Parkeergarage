package parkeersimulator.view;

import parkeersimulator.model.Car;
import parkeersimulator.model.CarPark;
import parkeersimulator.model.Location;

import javax.swing.*;
import java.awt.*;

public class CarParkFloor extends JPanel {

    private int floor;
    private Dimension size;
    private CarPark carPark;
    private Image carParkImage;

    public CarParkFloor(CarPark carPark, int floor) {
        super();

        this.carPark = carPark;
        this.floor = floor;
        size = new Dimension(0, 0);
        setMinimumSize(new Dimension(200, 500));
    }

    /**
     * Overridden. Tell the GUI manager how big we would like to be.
     */
    public Dimension getPreferredSize() {
        return new Dimension(800, 500);
    }

    protected void paintComponent(Graphics g) {
        if (carParkImage == null) {
            return;
        }

        Dimension currentSize = getSize();
        if (size.equals(currentSize)) {
            g.drawImage(carParkImage, 0, 0, null);
        }
        else {
            // Rescale the previous image.
            g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
        }
    }

    public void updateView()
    {
        // Create a new car park image if the size has changed.
        if (!size.equals(getSize())) {
            size = getSize();
            carParkImage = createImage(size.width, size.height);
        }
        Graphics graphics = carParkImage.getGraphics();

        if(floor == 1){
            graphics.setColor(Color.lightGray);
        }else{
            graphics.setColor(Color.GRAY);
        }
        graphics.fillRect(0, 0, size.width, size.height);


        for(int row = 0; row < carPark.getNumberOfRows(); row++) {
            for(int place = 0; place < carPark.getNumberOfPlaces(); place++) {
                Location location = new Location(floor, row, place);
                Car car = carPark.getCarAt(location);
                Color color = car == null ? Color.white : car.getColor();
                drawPlace(graphics, location, color);
            }
        }
        repaint();
    }

    /**
     * Paint a place on this car park view in a given color.
     */
    private void drawPlace(Graphics graphics, Location location, Color color) {
        graphics.setColor(color);

        graphics.fillRect(
                35 + ((int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 25,
                75 + location.getPlace() * 10,
                20 - 1,
                10 - 1);
    }
}

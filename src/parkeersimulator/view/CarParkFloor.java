package parkeersimulator.view;

import java.awt.*;

public class CarParkFloor extends GridBagView {

    public CarParkFloor(int floor) {
        super();
        setPosition(1 + floor, 1);
        setHorizontalPriority(1);
        setVerticalPriority(1);
        setBackground(Color.white);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Set the car park image here to update it
        super.paintComponent(g);
    }
}

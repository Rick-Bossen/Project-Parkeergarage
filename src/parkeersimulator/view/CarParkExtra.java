package parkeersimulator.view;

import java.awt.*;

public class CarParkExtra extends GridBagView {

    public CarParkExtra(int numberOfFloors) {
        super();
        setPosition(1, 2);
        setGridWidth(numberOfFloors);
        setBackground(new Color(45, 52, 54));
    }

}

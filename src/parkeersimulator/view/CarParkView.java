package parkeersimulator.view;

import java.awt.*;

/**
 * This class contains the whole car park view.
 */
public class CarParkView extends GridBagView {

    public CarParkView() {
        super();
        setPosition(1, 1);
        setHorizontalPriority(1);
        setVerticalPriority(1);
        setLayout(new GridLayout(1, 3));
    }

}

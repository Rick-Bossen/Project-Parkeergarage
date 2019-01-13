package parkeersimulator.view;

import java.awt.*;

public class TopBar extends GridBagView {

    public TopBar(int numberOfFloors) {
        super();
        setGridWidth(1 + numberOfFloors);
        setBackground(new Color(45, 52, 54));

        setUIComponents();
    }

    private void setUIComponents()
    {
        // @TODO Fill with the top bar items
    }

}

package parkeersimulator.model;

import java.awt.*;

public class ReservedSpot extends Car {

    private final Color COLOR = Color.green;

    public ReservedSpot() {
        setMinutesLeft(30);
    }

    public Color getColor() {
        return COLOR;
    }
}

package parkeersimulator.model;

import java.awt.Color;
import java.util.Random;

public class ReservedAdHocCar extends AdHocCar {

    public ReservedAdHocCar()
    {
        super();
        this.setHasToPay(false);
    }

    private static final Color COLOR = Color.magenta;
    private String id;
    private int timeUntilArrival;


    public Color getColor()
    {
        return COLOR;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }

    public void setTimeUntilArrival() {
        Random random = new Random();
        timeUntilArrival = (int) (5 + random.nextFloat() * 50);
    }

    public int getTimeUntilArrival()
    {
        return timeUntilArrival;
    }

    public void incrementArrivalTime()
    {
        timeUntilArrival--;
    }
}

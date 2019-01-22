package parkeersimulator.model;

import java.util.Random;
import java.util.UUID;

public class Reservation {

    private Location location;
    private int timeToReserve;
    private  String id;

    public Reservation()
    {
        Random random = new Random();
        timeToReserve = 60 + (int) (random.nextFloat() * (1440 - 60));

        id = UUID.randomUUID().toString();
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public int getTimeToReserve() {
        return timeToReserve;
    }

    public String getId() {
        return id;
    }

    public void tick()
    {
        timeToReserve--;
    }
}

package parkeersimulator.main;

import parkeersimulator.controller.*;
import parkeersimulator.model.*;

/**
 * This class represents the full parking simulation
 * It creates the simulation and the other components.
 *
 * @version 18.01.2019
 */
public class ParkingSimulation {

    public ParkingSimulation() {
        Clock clock = new Clock();
        CarPark carpark = new CarPark(3, 6, 30);
        new Simulator(clock, carpark);
    }

}

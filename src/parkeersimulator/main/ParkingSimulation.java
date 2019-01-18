package parkeersimulator.main;

import parkeersimulator.controller.Simulator;
import parkeersimulator.model.CarPark;
import parkeersimulator.model.Clock;
import parkeersimulator.model.Settings;

/**
 * This class represents the full parking simulation
 * It creates the simulation and the other components.
 *
 * @version 18.01.2019
 */
public class ParkingSimulation {

    public ParkingSimulation() {
        Clock clock = new Clock();
        CarPark carpark = new CarPark(Settings.get("carpark.floors"), Settings.get("carpark.rows"), Settings.get("carpark.places"));
        new Simulator(clock, carpark);
    }

}

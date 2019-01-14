package parkeersimulator.main;

import parkeersimulator.controller.*;
import parkeersimulator.model.*;

public class ParkingSimulation {

    public ParkingSimulation() {
        Clock clock = new Clock();
        Statistics stats = new Statistics();

        SimulatorModel simulatorModel = new SimulatorModel();
        CarPark carpark = new CarPark(3, 6, 30);
        Simulator simulator = new Simulator(clock, stats, simulatorModel, carpark);

        // @TODO Remove later
        simulator.run(10000);
    }

}

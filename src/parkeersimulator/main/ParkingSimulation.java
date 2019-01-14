package parkeersimulator.main;

import parkeersimulator.controller.*;
import parkeersimulator.model.*;

public class ParkingSimulation {

    public ParkingSimulation() {
        Clock clock = new Clock();

        SimulatorModel simulatorModel = new SimulatorModel();
        CarPark carpark = new CarPark(3, 6, 30);
        Simulator simulator = new Simulator(clock, simulatorModel, carpark);

        // @TODO Remove later
        simulator.run(10000);
    }

}

package parkeersimulator.main;
import parkeersimulator.view.*;
import parkeersimulator.controller.*;
import parkeersimulator.model.*;

import javax.swing.*;
import java.awt.*;

public class Main {

    public void testSim() {
        int numberOfFloors = 3;
        int numberOfRows = 6;
        int numberOfPlaces = 30;

        Clock clock = new Clock();
        Statistics stats = new Statistics();
        SimulatorModel simModel = new SimulatorModel();
        CarPark carpark = new CarPark(numberOfFloors, numberOfRows, numberOfPlaces);
        Simulator simulator = new Simulator(clock, stats, simModel, carpark);
        simulator.run(10000);
    }

}

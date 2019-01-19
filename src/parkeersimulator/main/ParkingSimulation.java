package parkeersimulator.main;

import parkeersimulator.controller.Simulator;
import parkeersimulator.model.CarPark;
import parkeersimulator.model.Clock;
import parkeersimulator.utility.Settings;
import parkeersimulator.view.CarParkControls;
import parkeersimulator.view.CarParkView;
import parkeersimulator.view.SideBar;
import parkeersimulator.view.TopBar;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents the full parking simulation
 * It creates the simulation and the other components.
 *
 * @version 18.01.2019
 */
public class ParkingSimulation {

    /**
     * Create a new Parking simulation and start it.
     */
    public ParkingSimulation() {

        JFrame window = new JFrame("Parking simulator");
        window.setLayout(new GridBagLayout());
        window.setPreferredSize(new Dimension(Settings.get("width"), Settings.get("height")));
        window.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - Settings.get("width")) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - Settings.get("height")) / 2);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the Models.
        Clock clock = new Clock();
        CarPark carPark = new CarPark();

        // Create the Views.
        TopBar topBar = new TopBar();
        SideBar sideBar = new SideBar();
        CarParkView carParkView = new CarParkView();
        CarParkControls carParkControls = new CarParkControls();

        // Create the Controllers.
        Simulator simulator = new Simulator(clock, carPark);
        carParkControls.setController(simulator);

        // Link the views to the Models.
        clock.addView(topBar);
        carPark.addView(carParkView);

        // Set the size of the car park
        carPark.setSize(Settings.get("carpark.floors"), Settings.get("carpark.rows"), Settings.get("carpark.places"));

        // Add the views to the window.
        window.add(topBar, topBar.getConstraints());
        window.add(sideBar, sideBar.getConstraints());
        window.add(carParkView, carParkView.getConstraints());
        window.add(carParkControls, carParkControls.getConstraints());

        window.pack();
        window.setVisible(true);
    }

    /**
     * Start a new parking simulation
     *
     * @param args String[] containing all running arguments
     */
    public static void main(String[] args) {
        new ParkingSimulation();
    }

}

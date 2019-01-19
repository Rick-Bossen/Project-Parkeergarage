package parkeersimulator.main;

import parkeersimulator.controller.Navigation;
import parkeersimulator.controller.Simulator;
import parkeersimulator.framework.View;
import parkeersimulator.model.CarPark;
import parkeersimulator.model.Clock;
import parkeersimulator.model.TabList;
import parkeersimulator.utility.Settings;
import parkeersimulator.view.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
        TabList tabList = new TabList();
        TopBar topBar = new TopBar();
        SideBar sideBar = new SideBar();
        CarParkView carParkView = new CarParkView();
        CarParkControls carParkControls = new CarParkControls();
        SettingsView settingsView = new SettingsView();

        // Create the Controllers.
        Navigation navigation = new Navigation(tabList);
        Simulator simulator = new Simulator(clock, carPark);
        carParkControls.setController(simulator);
        sideBar.setController(navigation);

        // Link the views to the Models.
        clock.addView(topBar);
        carPark.addView(carParkView);
        tabList.addView(sideBar);

        // Add the menu tabs
        ArrayList<View> carParkViews = new ArrayList<>();
        carParkViews.add(carParkView);
        carParkViews.add(carParkControls);
        tabList.addTabList("Home", carParkViews);
        tabList.addTabList("Settings", settingsView);

        tabList.setActiveTab("Home");

        // Set the size of the car park
        clock.reset();
        carPark.setSize(Settings.get("carpark.floors"), Settings.get("carpark.rows"), Settings.get("carpark.places"));

        // Add the views to the window.
        window.add(topBar, topBar.getConstraints());
        window.add(sideBar, sideBar.getConstraints());
        window.add(carParkView, carParkView.getConstraints());
        window.add(carParkControls, carParkControls.getConstraints());
        window.add(settingsView, settingsView.getConstraints());

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

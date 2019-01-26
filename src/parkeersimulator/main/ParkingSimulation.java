package parkeersimulator.main;

import parkeersimulator.controller.Navigation;
import parkeersimulator.controller.SettingManager;
import parkeersimulator.controller.Simulator;
import parkeersimulator.model.*;
import parkeersimulator.model.statistics.StatisticsList;
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
        StatisticsList statistics = new StatisticsList();
        CarPark carPark = new CarPark(statistics);
        SettingList settingList = new SettingList();

        // Create the Views.
        TabList tabList = new TabList();
        TopBar topBar = new TopBar();
        SideBar sideBar = new SideBar();
        CarParkView carParkView = new CarParkView();
        CarParkControls carParkControls = new CarParkControls();
        SettingView settingsView = new SettingView();
        SettingControls settingControls = new SettingControls(settingsView);
        StatisticsView statisticsView = new StatisticsView();

        // Create the Controllers.
        Navigation navigation = new Navigation(tabList);
        Simulator simulator = new Simulator(clock, carPark, statistics);
        SettingManager settingManager = new SettingManager(window, carPark, clock);
        carParkControls.setController(simulator);
        sideBar.setController(navigation);
        settingControls.setController(settingManager);

        // Link the views to the Models.
        clock.addView(topBar);
        carPark.addView(carParkView);
        tabList.addView(sideBar);
        settingList.addView(settingsView);
        statistics.addView(statisticsView);

        // Set the size of the car park
        clock.reset();
        carPark.setSize(Settings.get("carpark.floors"), Settings.get("carpark.rows"), Settings.get("carpark.places"), Settings.get("carpark.passPlaces"));
        settingList.fillCategories();

        // Add the views to the window.
        window.add(topBar, topBar.getConstraints());
        window.add(sideBar, sideBar.getConstraints());
        window.add(carParkView, carParkView.getConstraints());
        window.add(carParkControls, carParkControls.getConstraints());
        window.add(settingsView, settingsView.getConstraints());
        window.add(settingControls, settingControls.getConstraints());
        window.add(statisticsView, statisticsView.getConstraints());

        // Add scrollbar for panels.
        JScrollPane scrollPane = new JScrollPane(settingsView, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        window.add(scrollPane, settingsView.getConstraints());

        // Add the menu tabs
        ArrayList<JComponent> carParkViews = new ArrayList<>();
        carParkViews.add(carParkView);
        carParkViews.add(carParkControls);
        tabList.addTabList("Home", carParkViews);

        ArrayList<JComponent> settingViews = new ArrayList<>();
        settingViews.add(settingsView);
        settingViews.add(settingControls);
        settingViews.add(scrollPane);
        tabList.addTabList("Settings", settingViews);

        ArrayList<JComponent> statisticsViews = new ArrayList<>();
        statisticsViews.add(statisticsView);
        tabList.addTabList("StatisticsList", statisticsViews);

        tabList.setActiveTab("Home");

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

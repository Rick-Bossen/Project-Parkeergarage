package parkeersimulator.main;

import parkeersimulator.controller.Navigation;
import parkeersimulator.controller.SettingManager;
import parkeersimulator.controller.Simulator;
import parkeersimulator.enums.settings.GeneralSettings;
import parkeersimulator.enums.settings.SimulationPresetSettings;
import parkeersimulator.model.Clock;
import parkeersimulator.model.TabList;
import parkeersimulator.model.carpark.CarPark;
import parkeersimulator.model.settings.SettingList;
import parkeersimulator.model.statistics.Advice;
import parkeersimulator.model.statistics.ChartList;
import parkeersimulator.model.statistics.StatisticsList;
import parkeersimulator.view.about.AboutView;
import parkeersimulator.view.carpark.CarParkControls;
import parkeersimulator.view.carpark.CarParkView;
import parkeersimulator.view.gui.SideBar;
import parkeersimulator.view.gui.TopBar;
import parkeersimulator.view.settings.SettingControls;
import parkeersimulator.view.settings.SettingView;
import parkeersimulator.view.statistics.AdviceView;
import parkeersimulator.view.statistics.StatisticsView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * This class represents the full parking simulation,
 * It creates the simulation and the other components.
 *
 * @version 30.01.2019.
 */
class ParkingSimulation {

    /**
     * Create a new Parking simulation and start it.
     */
    private ParkingSimulation() {

        JFrame window = new JFrame("Parking simulator");
        window.setLayout(new GridBagLayout());
        window.setPreferredSize(new Dimension(GeneralSettings.WIDTH.getValue(), GeneralSettings.HEIGHT.getValue()));
        window.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - GeneralSettings.WIDTH.getValue()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - GeneralSettings.HEIGHT.getValue()) / 2);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the Models.
        Clock clock = new Clock();
        StatisticsList statistics = new StatisticsList();
        CarPark carPark = new CarPark(statistics);
        SettingList settingList = new SettingList();
        ChartList chartList = new ChartList(statistics);
        Advice advice = new Advice(statistics);

        // Create the Views.
        TabList tabList = new TabList();
        TopBar topBar = new TopBar();
        SideBar sideBar = new SideBar();
        CarParkView carParkView = new CarParkView();
        CarParkControls carParkControls = new CarParkControls();
        SettingView settingsView = new SettingView();
        SettingControls settingControls = new SettingControls(settingsView);
        AdviceView adviceView = new AdviceView();
        StatisticsView statisticsView = new StatisticsView(chartList);
        AboutView aboutView = new AboutView();

        // Create the Controllers.
        Navigation navigation = new Navigation(tabList);
        Simulator simulator = new Simulator(clock, carPark, statistics, chartList, statisticsView, advice);
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
        advice.addView(adviceView);

        // Set the size of the car park
        clock.reset();
        carPark.reset(
                SimulationPresetSettings.CARPARK_FLOORS.getValue(),
                SimulationPresetSettings.CARPARK_ROWS.getValue(),
                SimulationPresetSettings.CARPARK_PLACES.getValue(),
                SimulationPresetSettings.CARPARK_PASSHOLDER_PLACES.getValue()
        );
        settingList.fillCategories();

        // Add the views to the window.
        window.add(topBar, topBar.getConstraints());
        window.add(sideBar, sideBar.getConstraints());
        window.add(carParkView, carParkView.getConstraints());
        window.add(carParkControls, carParkControls.getConstraints());
        window.add(settingsView, settingsView.getConstraints());
        window.add(settingControls, settingControls.getConstraints());
        window.add(statisticsView, statisticsView.getConstraints());
        window.add(aboutView, aboutView.getConstraints());
        window.add(adviceView, adviceView.getConstraints());

        // Add scrollbar for panels.
        JScrollPane scrollPane = new JScrollPane(settingsView, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        window.add(scrollPane, settingsView.getConstraints());

        JScrollPane scrollPaneStatistics = new JScrollPane(statisticsView, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneStatistics.getVerticalScrollBar().setUnitIncrement(16);
        window.add(scrollPaneStatistics, statisticsView.getConstraints());

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
        statisticsViews.add(scrollPaneStatistics);
        tabList.addTabList("Results", statisticsViews);

        ArrayList<JComponent> adviceViews = new ArrayList<>();
        adviceViews.add(adviceView);
        tabList.addTabList("Advice", adviceViews);

        ArrayList<JComponent> aboutViews = new ArrayList<>();
        aboutViews.add(aboutView);
        tabList.addTabList("About", aboutViews);

        tabList.setActiveTab("Home");

        window.pack();
        window.setVisible(true);
    }

    /**
     * Start a new parking simulation.
     *
     * @param args String[] containing all running arguments.
     */
    public static void main(String[] args) {
        new ParkingSimulation();
    }

}

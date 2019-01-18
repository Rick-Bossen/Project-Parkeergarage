package parkeersimulator.controller;

import parkeersimulator.model.CarPark;
import parkeersimulator.model.Clock;
import parkeersimulator.view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

/**
 * This class represents the simulation itself.
 * <p>
 * It contains the models: Clock
 * It contains the views: CarPark, TopBar, CarParkFloor and CarParkView
 * <p>
 * This class also handles if the simulation is currently running or is halted.
 *
 * @version 18.01.2019
 */
public class Simulator {

    private Clock clock;
    private CarPark carPark;
    private TopBar topBar;
    private ArrayList<CarParkFloor> floors;
    private CarParkControls carParkControls;
    private JFrame mainFrame;
    private JPanel mainLayout;
    private CarParkView carParkView;
    private boolean halt = false;
    private boolean isRunning = false;

    public Simulator(Clock clock, CarPark carPark) {

        this.clock = clock;
        this.carPark = carPark;

        bootstrapFrame();
        updateViews();
    }

    /**
     * Runs the simulation for the specified amount of ticks
     *
     * @param ticks the total amount of ticks for the simulation to run
     */
    public void run(int ticks) {
        carParkControls.setButtonsEnabled(false);
        isRunning = true;
        new Timer(100, new ActionListener() {
            private int counter = 0;

            public void actionPerformed(ActionEvent e) {
                if (halt) {
                    halt = false;
                    ((Timer) e.getSource()).stop();
                    return;
                }

                tick();
                counter++;
                if (counter >= ticks) {
                    ((Timer) e.getSource()).stop();
                    carParkControls.setButtonsEnabled(true);
                    isRunning = false;
                }
            }

        }).start();
    }

    /**
     * Advances the entire simulation by one tick
     */
    private void tick() {
        clock.advanceTime();
        carPark.handleExit();
        updateViews();
        carPark.handleEntrance(clock.getDay());
    }

    /**
     * Generates a new JFrame containing all the GUI elements and a graphic of every parking floor
     */
    private void bootstrapFrame() {
        floors = new ArrayList<>();
        mainFrame = new JFrame("Parking Simulator");
        mainLayout = new JPanel();
        mainLayout.setLayout(new GridBagLayout());
        mainLayout.setPreferredSize(new Dimension(800, 500));
        mainFrame.setContentPane(mainLayout);
        mainFrame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - 800) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - 500) / 2);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        topBar = new TopBar();
        mainLayout.add(topBar, topBar.getConstraints());

        SideBar sideBar = new SideBar();
        mainLayout.add(sideBar, sideBar.getConstraints());

        carParkView = new CarParkView();
        mainLayout.add(carParkView, carParkView.getConstraints());

        for (int floor = 0; floor < carPark.getNumberOfFloors(); floor++) {
            CarParkFloor carParkFloor = new CarParkFloor(carPark, floor);
            // Fix for redrawing on resize.
            carParkFloor.addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent evt) {
                    Component floor = (Component) evt.getSource();
                    ((CarParkFloor) floor).updateView();

                }
            });
            floors.add(carParkFloor);
            carParkView.add(carParkFloor);
        }

        carParkControls = new CarParkControls(this);
        mainLayout.add(carParkControls, carParkControls.getConstraints());

        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    /**
     * Updates the GUI to match the data in the simulation
     */
    public void updateViews() {
        carPark.tick();
        topBar.setDateTimeLabelText(clock.toString());

        // Update the car park views.
        for (CarParkFloor floor : floors) {
            floor.updateView();
        }
    }

    /**
     * Resets the entire simulation and initializes a new simulation in the same window
     */
    public void resetSimulation() {
        if (isRunning) {
            isRunning = false;
            halt = true;
        }

        clock.reset();
        carPark.reset();
        updateViews();
        carParkControls.setButtonsEnabled(true);
    }

}

package parkeersimulator.controller;

import parkeersimulator.model.*;
import parkeersimulator.view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class Simulator {

    private Clock clock;
    private CarPark carPark;
    private TopBar topBar;
    private ArrayList<CarParkFloor> floors;
    private CarParkExtra carParkExtra;
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
     * @param ticks the total amount of ticks for the simulation to run
     */
    public void run(int ticks) {
        carParkExtra.setButtonsEnabled(false);
        isRunning = true;
        new Timer(100, new ActionListener() {
            private int counter = 0;

            public void actionPerformed(ActionEvent e) {
                if (halt) {
                    halt = false;
                    ((Timer)e.getSource()).stop();
                    return;}

                tick();
                counter++;
                if (counter >= ticks) {
                    ((Timer)e.getSource()).stop();
                    carParkExtra.setButtonsEnabled(true);
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
    private void bootstrapFrame()
    {
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

        generateCarParkView();

        carParkExtra = new CarParkExtra(this);
        mainLayout.add(carParkExtra, carParkExtra.getConstraints());

        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    /**
     * Updates the GUI to match the data in the simulation
     */
    private void updateViews(){
        carPark.tick();
        topBar.setDateTimeLabelText(clock.toString());

        // Update the car park views.
        for (CarParkFloor floor : floors){
            floor.updateView();
        }
    }

    /**
     * Resets the entire simulation and initializes a new simulation in the same window
     */
    public void resetSimulation()
    {
        int numberOfFloors = carPark.getNumberOfFloors();
        int numberOfRows = carPark.getNumberOfRows();
        int numberOfPlaces = carPark.getNumberOfPlaces();

        if (isRunning) {
            isRunning = false;
            halt = true;}

        deleteSimulation();
        newSimulation(numberOfFloors, numberOfRows, numberOfPlaces);
        mainLayout.remove(carParkView);
        generateCarParkView();

        mainFrame.pack();

        for (CarParkFloor floor : floors){
            floor.updateView();
        }

        topBar.setDateTimeLabelText(clock.toString());
        carParkExtra.setButtonsEnabled(true);
    }

    /**
     * Deletes the current simulation
     * Removes clock and carPark
     * clears the floors ArrayList
     */
    private void deleteSimulation()
    {
        clock = null;
        carPark = null;
        floors.clear();
    }

    /**
     * Initialises a new instance of Clock and CarPark
     * @param numberOfFloors The number of floors in the CarPark
     * @param numberOfRows The number of rows in the CarPark
     * @param numberOfPlaces The number of places in the CarPark
     */
    private void newSimulation(int numberOfFloors, int numberOfRows, int numberOfPlaces)
    {
            clock = new Clock();
            carPark = new CarPark(numberOfFloors, numberOfRows, numberOfPlaces);
    }


    /**
     * Generates the graphics for the floors of the parking lot
     */
    private void generateCarParkView()
    {
        carParkView = new CarParkView();
        mainLayout.add(carParkView, carParkView.getConstraints());

        for (int floor = 0; floor < carPark.getNumberOfFloors(); floor++){
            CarParkFloor carParkFloor = new CarParkFloor(carPark, floor);
            floors.add(carParkFloor);
            carParkView.add(carParkFloor);
        }
    }
}

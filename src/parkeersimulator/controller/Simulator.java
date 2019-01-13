package parkeersimulator.controller;

import parkeersimulator.model.*;
import parkeersimulator.view.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class Simulator {

    private Clock clock;
    private Statistics stats;
    private SimulatorModel sim;
    private CarPark carPark;
    private ArrayList<CarParkFloor> floors;

    public Simulator(Clock clock, Statistics stats, SimulatorModel sim, CarPark carPark) {

        this.clock = clock;
        this.stats = stats;
        this.sim = sim;
        this.carPark = carPark;

        bootstrapFrame();
        updateViews();
    }

    public void run(int ticks) {
        for (int i = 0; i < ticks; i++) {
            tick();
        }
    }

    public void tick() {

        clock.advanceTime();
        carPark.handleExit();
        updateViews();

        try {
            Thread.sleep(sim.getTickPause());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        carPark.handleEntrance(clock.getDay());
        stats.incrementTick();
    }

    private void bootstrapFrame()
    {
        floors = new ArrayList<>();
        JFrame mainFrame = new JFrame("Parkeersimulator");
        JPanel mainLayout = new JPanel();
        mainLayout.setLayout(new GridBagLayout());
        mainLayout.setPreferredSize(new Dimension(800, 500));
        mainFrame.setContentPane(mainLayout);
        mainFrame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - 800) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - 500) / 2);

        TopBar topBar = new TopBar();
        mainLayout.add(topBar, topBar.getConstraints());

        SideBar sideBar = new SideBar();
        mainLayout.add(sideBar, sideBar.getConstraints());

        CarParkView carParkView = new CarParkView();
        mainLayout.add(carParkView, carParkView.getConstraints());

        for (int floor = 0; floor < carPark.getNumberOfFloors(); floor++){
            CarParkFloor carParkFloor = new CarParkFloor(carPark, floor);
            floors.add(carParkFloor);
            carParkView.add(carParkFloor);
        }

        CarParkExtra carParkExtra = new CarParkExtra();
        mainLayout.add(carParkExtra, carParkExtra.getConstraints());

        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    private void updateViews(){
        carPark.tick();

        // Update the car park views.
        for (CarParkFloor floor : floors){
            floor.updateView();
        }
    }

}

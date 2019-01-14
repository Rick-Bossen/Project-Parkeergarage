package parkeersimulator.controller;

import parkeersimulator.model.*;
import parkeersimulator.view.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class Simulator {

    private Clock clock;
    private SimulatorModel sim;
    private CarPark carPark;
    private TopBar topBar;
    private ArrayList<CarParkFloor> floors;

    public Simulator(Clock clock, SimulatorModel sim, CarPark carPark) {

        this.clock = clock;
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
    }

    private void bootstrapFrame()
    {
        floors = new ArrayList<>();
        JFrame mainFrame = new JFrame("Parking Simulator");
        JPanel mainLayout = new JPanel();
        mainLayout.setLayout(new GridBagLayout());
        mainLayout.setPreferredSize(new Dimension(800, 500));
        mainFrame.setContentPane(mainLayout);
        mainFrame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - 800) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - 500) / 2);

        topBar = new TopBar();
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
        topBar.setDateTimeLabelText(clock.toString());

        // Update the car park views.
        for (CarParkFloor floor : floors){
            floor.updateView();
        }
    }

}

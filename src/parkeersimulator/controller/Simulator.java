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
        new Timer(100, new ActionListener() {
            private int counter;

            public void actionPerformed(ActionEvent e) {
                tick();
                counter++;
                if (counter >= ticks) {
                    ((Timer)e.getSource()).stop();
                }
            }
        }).start();
    }

    private void tick() {
        clock.advanceTime();
        carPark.handleExit();
        updateViews();
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
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        CarParkExtra carParkExtra = new CarParkExtra(this);
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

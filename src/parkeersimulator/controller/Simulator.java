package parkeersimulator.controller;

import parkeersimulator.framework.Controller;
import parkeersimulator.framework.View;
import parkeersimulator.model.CarPark;
import parkeersimulator.model.Clock;
import parkeersimulator.utility.Settings;
import parkeersimulator.view.CarParkControls;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represents the simulation itself.
 *
 * It contains the models: Clock
 * It contains the views: CarPark, TopBar, CarParkFloor and CarParkView
 *
 * This class also handles if the simulation is currently running or is halted.
 *
 * @version 18.01.2019
 */
public class Simulator extends Controller {

    private final Clock clock;
    private final CarPark carPark;


    public static final int RUN_ONCE = 1;
    public static final int RUN_THOUSAND_TIMES = 2;
    public static final int RESET = 3;

    private boolean halt = false;
    private boolean isRunning = false;

    public Simulator(Clock clock, CarPark carPark) {
        this.clock = clock;
        this.carPark = carPark;
    }

    /**
     * Runs the simulation for the specified amount of ticks
     *
     * @param ticks the total amount of ticks for the simulation to run
     */
    private void run(CarParkControls controls, int ticks) {
        controls.setButtonsEnabled(false);
        isRunning = true;
        new Timer(Settings.get("tickspeed"), new ActionListener() {
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
                    controls.setButtonsEnabled(true);
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
        carPark.tick();
        carPark.handleExit();
        carPark.queueReservations(clock.getDay());
        carPark.handleEntrance(clock.getDay());
    }

    /**
     * Resets the entire simulation and initializes a new simulation in the same window
     */
    private void reset(CarParkControls controls){
        if (isRunning) {
            isRunning = false;
            halt = true;
        }
        clock.reset();
        carPark.reset();
        controls.setButtonsEnabled(true);
    }

    @Override
    protected void event(View view, int eventId) {
        switch (eventId){
            case RUN_ONCE:
                run((CarParkControls) view, 1);
                break;
            case RUN_THOUSAND_TIMES:
                run((CarParkControls) view, 1000);
                break;
            case RESET:
                reset((CarParkControls) view);
                break;
        }
    }

}
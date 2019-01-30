package parkeersimulator.controller;

import parkeersimulator.enums.settings.SimulationSettings;
import parkeersimulator.framework.Controller;
import parkeersimulator.framework.View;
import parkeersimulator.model.Clock;
import parkeersimulator.model.carpark.CarPark;
import parkeersimulator.model.statistics.Advice;
import parkeersimulator.model.statistics.ChartList;
import parkeersimulator.model.statistics.StatisticsList;
import parkeersimulator.view.carpark.CarParkControls;
import parkeersimulator.view.statistics.AdvicePanel;
import parkeersimulator.view.statistics.StatisticsView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represents the simulation itself.
 * <p>
 * It contains the models: Clock.
 * It contains the views: CarPark, TopBar, CarParkFloor and CarParkView.
 * <p>
 * This class also handles if the simulation is currently running or is halted.
 *
 * @version 28.01.2019.
 */
public class Simulator extends Controller {

    public static final int RUN_ONE_HOUR = 1;
    public static final int RUN_ONE_DAY = 2;
    public static final int RUN_ONE_WEEK = 3;
    public static final int RUN_ONE_MONTH = 4;
    public static final int STOP = 5;
    public static final int RESET = 6;
    private static final int MAX_FRAMES_PER_SECOND = 30;
    private Clock clock;
    private CarPark carPark;
    private StatisticsList statisticsList;
    private ChartList chartList;
    private StatisticsView statisticsView;
    private Advice advice;
    private AdvicePanel advicePanel;
    private boolean halt = false;
    private boolean isRunning = false;

    public Simulator(Clock clock, CarPark carPark, StatisticsList statisticsList, ChartList chartList, StatisticsView statisticsView, Advice advice, AdvicePanel advicePanel) {
        this.clock = clock;
        this.carPark = carPark;
        this.statisticsList = statisticsList;
        this.chartList = chartList;
        this.statisticsView = statisticsView;
        this.advice = advice;
        this.advicePanel = advicePanel;
    }

    /**
     * Runs the simulation for the specified amount of ticks.
     *
     * @param controls the controls for the carPark.
     * @param ticks the total amount of ticks for the simulation to run.
     */
    private void run(CarParkControls controls, int ticks) {
        int tickspeed = SimulationSettings.TICKSPEED.getValue();
        controls.setControlsEnabled(false);
        isRunning = true;

        int showFramesAt = (tickspeed > 0 ? (1000 / tickspeed) : 1000) / MAX_FRAMES_PER_SECOND;

        new Timer(tickspeed, new ActionListener() {
            private int counter = 0;

            public void actionPerformed(ActionEvent e) {
                if (halt || counter >= ticks) {
                    halt = false;
                    isRunning = false;
                    controls.setControlsEnabled(true);
                    ((Timer) e.getSource()).stop();
                } else {
                    tick(counter == 0 || showFramesAt == 0 || counter % showFramesAt == 0 || (counter + 1) == ticks);
                    counter++;
                }
            }

        }).start();
    }

    /**
     * Advances the entire simulation by one tick.
     *
     * @param updateViews Should the floors be repainted.
     */
    private void tick(boolean updateViews) {
        clock.advanceTime();
        statisticsList.tick();
        chartList.tick();
        carPark.tick();
        carPark.queueReservations(clock.getDayOfWeek());
        carPark.handleEntrance(clock);
        advice.tick();
        if (updateViews) {
            carPark.updateFloors();
        }
    }

    /**
     * Stop current simulation from running.
     */
    private void stop() {
        if (isRunning) {
            isRunning = false;
            halt = true;
        }
    }

    /**
     * Resets the entire simulation and initializes a new simulation in the same window.
     */
    private void reset() {
        clock.reset();
        carPark.reset();
        statisticsList.reset();
        chartList.reset(statisticsList);
        advice.reset(statisticsList);
        statisticsView.reset(chartList,advicePanel);
        stop();
    }

    @Override
    protected void event(View view, int eventId) {
        switch (eventId) {
            case RUN_ONE_HOUR:
                run((CarParkControls) view, 60);
                break;
            case RUN_ONE_DAY:
                run((CarParkControls) view, 60 * 24);
                break;
            case RUN_ONE_WEEK:
                run((CarParkControls) view, 60 * 24 * 7);
                break;
            case RUN_ONE_MONTH:
                run((CarParkControls) view, 60 * 24 * 30);
                break;
            case STOP:
                stop();
                break;
            case RESET:
                reset();
                break;
        }
    }

}
package parkeersimulator.controller;

import parkeersimulator.model.*;


public class Simulator {

    private Clock clock;
    private Statistics stats;
    private SimulatorModel sim;
    private CarPark carPark;

    public Simulator(Clock clock, Statistics stats, SimulatorModel sim, CarPark carPark) {

        this.clock = clock;
        this.stats = stats;
        this.sim = sim;
        this.carPark = carPark;
    }

    public void run(int ticks) {
        for (int i = 0; i < ticks; i++) {
            tick();
        }
    }

    public void tick() {

        clock.advanceTime();
        carPark.handleExit();

        try {
            Thread.sleep(sim.getTickPause());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        carPark.handleEntrance(clock.getDay());
        stats.incrementTick();
    }

}

package parkeersimulator.model;

public class Statistics {

    private int currentTick = 0;


    public int getCurrentTick() {

        return currentTick;
    }

    public void incrementTick() {

        currentTick ++;
    }
}

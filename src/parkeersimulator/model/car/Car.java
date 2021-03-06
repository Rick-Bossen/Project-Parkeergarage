package parkeersimulator.model.car;

import parkeersimulator.framework.Model;
import parkeersimulator.model.Location;

import java.awt.*;

/**
 * This class represents a car with no entry type,
 * Has the properties location and payment process of the customer.
 *
 * @version 27.01.2019.
 */
public abstract class Car extends Model {

    private Location location;
    private int minutesLeft;
    private boolean isPaying;
    private boolean hasToPay;
    private boolean isFirstDraw = true;
    private Color color = Color.white;
    private Color background = new Color(195, 195, 195);

    /**
     * Return the location of the car.
     *
     * @return Location of the car.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Set the location of the car.
     *
     * @param location current Location of the car.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * The time the car has left.
     *
     * @return an Integer representing the amount of minutes the car can still park.
     */
    public int getMinutesLeft() {
        return minutesLeft;
    }

    /**
     * Set the time the car has left.
     *
     * @param minutesLeft an Integer representing the amount of minutes the car can still park.
     */
    public void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
    }

    /**
     * Get if the car is currently paying.
     *
     * @return boolean if the car is paying.
     */
    public boolean getIsPaying() {
        return isPaying;
    }

    /**
     * Set if the car is currently paying.
     *
     * @param isPaying boolean if the car is paying.
     */
    public void setIsPaying(boolean isPaying) {
        this.isPaying = isPaying;
    }

    /**
     * Return if the customer has to pay or if it is paying in an alternative way.
     *
     * @return boolean if the customer has to pay.
     */
    public boolean getHasToPay() {
        return hasToPay;
    }

    /**
     * Set if the customer still has to pay on leave.
     *
     * @param hasToPay boolean if the customer has to pay.
     */
    void setHasToPay(boolean hasToPay) {
        this.hasToPay = hasToPay;
    }

    /**
     * Remove a minute of the counter of how many minutes the car can park.
     */
    public void tick() {
        minutesLeft--;
    }

    /**
     * Set the background of the car.
     *
     * @param color the background color of the car.
     */
    protected void setBackground(Color color){
        this.background = color;
    }

    /**
     * Return the Background of the car.
     *
     * @return the Background of the car.
     */
    public Color getBackground(){
        return background;
    }

    /**
     * Set the color of the car.
     *
     * @param color the color of the car.
     */
    protected void setColor(Color color){
        this.color = color;
    }

    /**
     * Return the Color of the car.
     *
     * @return the Color of the car.
     */
    public Color getColor(){
        return color;
    }

    /**
     * Return if the car has been drawn before.
     *
     * @return If the car has been drawn before.
     */
    public boolean isFirstDraw() {
        return isFirstDraw;
    }

    /**
     * Set if the car has already been drawn.
     *
     * @param firstDraw If the car has been drawn.
     */
    public void setFirstDraw(boolean firstDraw) {
        this.isFirstDraw = firstDraw;
    }
}
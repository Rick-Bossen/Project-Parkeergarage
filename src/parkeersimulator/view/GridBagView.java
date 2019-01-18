package parkeersimulator.view;

import javax.swing.*;
import java.awt.*;

/**
 * Abstract class used by all views inside of a grid bag layout.
 */
abstract public class GridBagView extends JPanel {

    private GridBagConstraints constraints;

    public GridBagView() {
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
    }

    /**
     * Set the position in the layout.
     *
     * @param x Horizontal position
     * @param y Vertical position
     */
    protected void setPosition(int x, int y) {
        constraints.gridx = x;
        constraints.gridy = y;
    }

    /**
     * Set the amount of columns this element should span.
     *
     * @param gridWidth Amount of columns.
     */
    protected void setGridWidth(int gridWidth) {
        constraints.gridwidth = gridWidth;
    }

    /**
     * Set the amount of rows this element should span.
     *
     * @param gridHeight Amount of rows.
     */
    protected void setGridHeight(int gridHeight) {
        constraints.gridheight = gridHeight;
    }

    /**
     * Set the priority this element should take horizontally.
     *
     * @param priority Double representing the priority
     */
    protected void setHorizontalPriority(double priority) {
        constraints.weightx = priority;
    }

    /**
     * Set the priority this element should take vertically.
     *
     * @param priority Double representing the priority
     */
    protected void setVerticalPriority(double priority) {
        constraints.weighty = priority;
    }

    /**
     * Return the constraints used by this element.
     *
     * @return Grid bag constraints of this element.
     */
    public GridBagConstraints getConstraints() {
        return constraints;
    }

}

package parkeersimulator.framework;

import java.awt.*;

/**
 * Abstract class used by all views inside of a grid bag layout.
 */
public abstract class GridBagView extends View {

    private final GridBagConstraints constraints;

    protected GridBagView() {
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
     */
    protected void setGridWidth() {
        constraints.gridwidth = 2;
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
     */
    protected void setHorizontalPriority() {
        constraints.weightx = (double) 1;
    }

    /**
     * Set the priority this element should take vertically.
     *
     */
    protected void setVerticalPriority() {
        constraints.weighty = (double) 1;
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

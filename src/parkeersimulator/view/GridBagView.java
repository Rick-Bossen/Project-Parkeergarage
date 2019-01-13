package parkeersimulator.view;

import javax.swing.*;
import java.awt.*;

abstract public class GridBagView extends JPanel {

    private GridBagConstraints constraints;

    public GridBagView(){
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
    }

    public GridBagConstraints getConstraints(){
        return constraints;
    }

    protected void setPosition(int x, int y)
    {
        constraints.gridx = x;
        constraints.gridy = y;
    }

    protected void setGridWidth(int gridWidth) {
        System.out.println(constraints);
        constraints.gridwidth = gridWidth;
    }

    protected void setGridHeight(int gridHeight) {
        constraints.gridheight = gridHeight;
    }

    protected void setInsets(int insetTop, int insetLeft, int insetBottom, int insetRight) {
        constraints.insets = new Insets(insetTop, insetLeft, insetBottom, insetRight);
    }

    protected void setAnchor(int anchor) {
        constraints.anchor = anchor;
    }

    protected void setFill(int fill) {
        constraints.fill = fill;
    }

    protected void setHorizontalPriority(double priority)
    {
        constraints.weightx = priority;
    }

    protected void setVerticalPriority(double priority)
    {
        constraints.weighty = priority;
    }
}

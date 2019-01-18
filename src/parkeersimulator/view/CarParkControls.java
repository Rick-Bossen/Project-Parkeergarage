package parkeersimulator.view;

import parkeersimulator.controller.Simulator;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents the view containing the controls to control the car park.
 *
 * @version 18.01.2019
 */
public class CarParkControls extends GridBagView {

    private Simulator simulator;
    private JButton oneTickButton;
    private JButton thousandTickButton;

    public CarParkControls(Simulator simulator) {
        super();
        this.simulator = simulator;
        setPosition(1, 2);
        setBackground(new Color(45, 52, 54));
        setLayout(new GridBagLayout());
        setUIComponents();
    }

    /**
     * Create the UI components used in the current view
     */
    private void setUIComponents() {
        GridBagConstraints constraints = new GridBagConstraints();
        JLabel minutesLabel = new JLabel();
        minutesLabel.setFont(new Font("Dubai", -1, 12));
        minutesLabel.setForeground(Color.white);
        minutesLabel.setText("Minutes forward:");
        constraints.gridx = 0;
        constraints.insets = new Insets(10, 20, 10, 10);
        add(minutesLabel, constraints);

        oneTickButton = generateNewButton();
        oneTickButton.setText("1");
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.insets = new Insets(10, 10, 10, 10);
        add(oneTickButton, constraints);
        oneTickButton.addActionListener(e -> simulator.run(1));

        thousandTickButton = generateNewButton();
        thousandTickButton.setText("1000");
        constraints = new GridBagConstraints();
        constraints.gridx = 2;
        constraints.insets = new Insets(10, 10, 10, 10);
        add(thousandTickButton, constraints);
        thousandTickButton.addActionListener(e -> simulator.run(1000));

        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.gridx = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(spacer, constraints);

        JButton resetButton = generateNewButton();
        resetButton.setText("Reset simulation");
        constraints = new GridBagConstraints();
        constraints.gridx = 4;
        constraints.insets = new Insets(10, 10, 10, 10);
        add(resetButton, constraints);
        resetButton.addActionListener(e -> simulator.resetSimulation());
    }

    /**
     * Generate a new [basic] button
     *
     * @return new button
     */
    private JButton generateNewButton() {
        JButton button = new JButton();
        button.setFont(new Font("Dubai Light", -1, 14));
        button.setForeground(Color.white);
        button.setBackground(new Color(116, 185, 255));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        return button;
    }

    /**
     * Set all buttons to enabled or disabled.
     *
     * @param bool Boolean if the button should be enabled or not.
     */
    public void setButtonsEnabled(boolean bool) {
        oneTickButton.setEnabled(bool);
        thousandTickButton.setEnabled(bool);
    }

}
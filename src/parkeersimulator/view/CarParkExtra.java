package parkeersimulator.view;

import parkeersimulator.controller.Simulator;

import javax.swing.*;
import java.awt.*;

public class CarParkExtra extends GridBagView {

    private Simulator simulator;

    public CarParkExtra(Simulator simulator) {
        super();
        this.simulator = simulator;
        setPosition(1, 2);
        setBackground(new Color(45, 52, 54));
        setLayout(new GridBagLayout());
        setUIComponents();
    }

    private void setUIComponents()
    {
        GridBagConstraints constraints = new GridBagConstraints();
        JLabel minutesLabel = new JLabel();
        minutesLabel.setFont(new Font("Dubai", -1, 12));
        minutesLabel.setForeground(Color.white);
        minutesLabel.setText("Minutes forward:");
        constraints.gridx = 0;
        constraints.insets = new Insets(10, 20, 10, 10);
        add(minutesLabel, constraints);

        JButton oneTickButton = new JButton();
        oneTickButton.setFont(new Font("Dubai Light", -1, 14));
        oneTickButton.setForeground(Color.white);
        oneTickButton.setBackground(new Color(116, 185, 255));
        oneTickButton.setBorderPainted(false);
        oneTickButton.setFocusPainted(false);
        oneTickButton.setText("1");
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.insets = new Insets(10, 10, 10, 10);
        add(oneTickButton, constraints);
        oneTickButton.addActionListener(e -> simulator.run(1));

        JButton thousandTickButton = new JButton();
        thousandTickButton.setFont(new Font("Dubai Light", -1, 14));
        thousandTickButton.setForeground(Color.white);
        thousandTickButton.setBackground(new Color(116, 185, 255));
        thousandTickButton.setBorderPainted(false);
        thousandTickButton.setFocusPainted(false);
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

//        JButton resetButton = new JButton();
//        resetButton.setFont(new Font("Dubai Light", -1, 14));
//        resetButton.setForeground(Color.white);
//        resetButton.setBackground(new Color(116, 185, 255));
//        resetButton.setBorderPainted(false);
//        resetButton.setFocusPainted(false);
//        resetButton.setText("Reset simulation");
//        resetButton.setEnabled(false);
//        constraints = new GridBagConstraints();
//        constraints.gridx = 4;
//        constraints.insets = new Insets(10, 10, 10, 10);
//        add(resetButton, constraints);
    }

}

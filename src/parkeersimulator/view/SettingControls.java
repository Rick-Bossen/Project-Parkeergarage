package parkeersimulator.view;


import parkeersimulator.controller.SettingManager;
import parkeersimulator.controller.Simulator;
import parkeersimulator.framework.GridBagView;
import parkeersimulator.framework.Model;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents the view containing the controls to control the car park.
 *
 * @version 18.01.2019
 */
public class SettingControls extends GridBagView {

    private JButton defaultButton;
    private JButton saveButton;

    public SettingControls() {
        super();
        setPosition(1, 2);
        setBackground(new Color(45, 52, 54));
        setLayout(new GridBagLayout());
        setUIComponents();
    }

    /**
     * Create the UI components used in the current view
     */
    private void setUIComponents() {
        GridBagConstraints constraints;

        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(spacer, constraints);


        defaultButton = generateNewButton();
        defaultButton.setText("Reset to Default");
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.insets = new Insets(10, 10, 10, 10);
        add(defaultButton, constraints);
        defaultButton.addActionListener(e -> {
            int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to reset to default?", "Confirm", JOptionPane.YES_NO_OPTION);
            if(dialogResult == JOptionPane.YES_OPTION){
                sendEvent(SettingManager.RESET_TO_DEFAULT);
            }
        });

        saveButton = generateNewButton();
        saveButton.setText("Save settings");
        constraints = new GridBagConstraints();
        constraints.gridx = 2;
        constraints.insets = new Insets(10, 10, 10, 10);
        add(saveButton, constraints);
        saveButton.addActionListener(e -> {
            sendEvent(SettingManager.SAVE_SETTINGS);
            JOptionPane.showMessageDialog (null, "The settings have been saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
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

    @Override
    protected void update(Model model) {

    }
}

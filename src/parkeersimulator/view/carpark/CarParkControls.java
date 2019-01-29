package parkeersimulator.view.carpark;


import parkeersimulator.controller.Simulator;
import parkeersimulator.enums.theme.ThemeColors;
import parkeersimulator.enums.theme.ThemeFonts;
import parkeersimulator.framework.GridBagView;
import parkeersimulator.framework.Model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * This class represents the view containing the controls to control the car park.
 *
 * @version 28.01.2019.
 */
public class CarParkControls extends GridBagView {

    private ArrayList<JButton> controls;

    public CarParkControls() {
        super();
        setPosition(1, 2);
        setBackground(ThemeColors.BACKGROUND_DARK.getColor());
        setLayout(new GridBagLayout());
        controls = new ArrayList<>();
        setUIComponents();
    }

    /**
     * Create the UI components used in the current view.
     */
    private void setUIComponents() {
        GridBagConstraints constraints = new GridBagConstraints();
        JLabel minutesLabel = new JLabel();
        minutesLabel.setFont(ThemeFonts.SMALL_REGULAR.getFont());
        minutesLabel.setForeground(ThemeColors.FONT_LIGHT.getColor());
        minutesLabel.setText("Forward simulation:");
        constraints.gridx = 0;
        constraints.insets = new Insets(10, 20, 10, 10);
        add(minutesLabel, constraints);

        controls.add(createButton(1, "1 hour", Simulator.RUN_ONE_HOUR));
        controls.add(createButton(2, "1 day", Simulator.RUN_ONE_DAY));
        controls.add(createButton(3, "1 week", Simulator.RUN_ONE_WEEK));
        controls.add(createButton(4, "1 month", Simulator.RUN_ONE_MONTH));

        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.gridx = 5;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(spacer, constraints);

        createButton(7, "Stop", Simulator.STOP);
        createButton(8, "Reset", Simulator.RESET);

        setControlsEnabled(true);
    }

    /**
     * Add controller to the view with a given label and event.
     *
     * @param gridX   Horizontal position of the button.
     * @param label   Label of the button.
     * @param eventId Event id of the button. If event id = -1 no action will be bound.
     */
    private JButton createButton(int gridX, String label, int eventId) {
        JButton button = new JButton();
        button.setFont(ThemeFonts.NORMAL_REGULAR.getFont());
        button.setForeground(ThemeColors.FONT_LIGHT.getColor());
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setText(label);
        button.setBackground(ThemeColors.INTERACTION.getColor());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = gridX;
        constraints.insets = new Insets(10, 10, 10, 10);
        if (eventId >= 0) {
            button.addActionListener(e -> sendEvent(eventId));
        }
        add(button, constraints);
        return button;
    }

    /**
     * Set all Control type buttons to enabled or disabled.
     *
     * @param enabled Boolean if the button should be enabled or not.
     */
    public void setControlsEnabled(boolean enabled) {
        for (JButton button : controls) {
            button.setEnabled(enabled);
        }
    }

    @Override
    protected void update(Model model) {

    }
}

package parkeersimulator.view.gui;

import parkeersimulator.enums.theme.ThemeColors;
import parkeersimulator.enums.theme.ThemeFonts;
import parkeersimulator.framework.GridBagView;
import parkeersimulator.framework.Model;
import parkeersimulator.model.Clock;

import javax.swing.*;
import java.awt.*;

/**
 * Top bar containing the title and name.
 *
 * @version 28.01.2019.
 */
public class TopBar extends GridBagView {

    private JLabel dateTimeLabel;

    public TopBar() {
        super();
        setGridWidth(2);
        setBackground(ThemeColors.BACKGROUND_DARK.getColor());

        setLayout(new GridBagLayout());
        setUIComponents();
    }

    /**
     * Create labels contained in the top bar.
     */
    private void setUIComponents() {
        JLabel titleLabel = new JLabel();
        titleLabel.setFont(ThemeFonts.NORMAL_BOLD.getFont());
        titleLabel.setForeground(ThemeColors.FONT_LIGHT.getColor());
        titleLabel.setText("Parking Simulator");
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 15, 0, 0);
        add(titleLabel, constraints);

        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(spacer, constraints);

        dateTimeLabel = new JLabel();
        dateTimeLabel.setFont(ThemeFonts.NORMAL_REGULAR.getFont());
        dateTimeLabel.setForeground(ThemeColors.FONT_LIGHT.getColor());
        constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 0, 0, 15);
        add(dateTimeLabel, constraints);
    }

    @Override
    protected void update(Model model) {
        if (model instanceof Clock) {
            dateTimeLabel.setText(model.toString());
        }
    }

}

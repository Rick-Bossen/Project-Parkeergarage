package parkeersimulator.view;

import parkeersimulator.framework.GridBagView;
import parkeersimulator.framework.Model;

import java.awt.*;

/**
 * This class contains the whole settings page.
 */
public class SettingsView extends GridBagView {

    public SettingsView() {
        super();
        setPosition(1, 1);
        setHorizontalPriority(1);
        setVerticalPriority(1);
        setGridHeight(2);
        setBackground(Color.magenta);

    }

    @Override
    protected void update(Model model) {

    }
}

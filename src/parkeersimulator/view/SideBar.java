package parkeersimulator.view;

import parkeersimulator.framework.GridBagView;
import parkeersimulator.framework.Model;

import java.awt.*;

/**
 * Sidebar used to navigate the different views
 *
 * @version 13.01.2019
 */
public class SideBar extends GridBagView {

    public SideBar() {
        super();
        setPosition(0, 1);
        setGridHeight(2);
        setBackground(new Color(76, 83, 85));

        setUIComponents();
    }

    /**
     * Create the menu items of the sidebar
     */
    private void setUIComponents() {
        // @TODO Fill with the sidebar items
    }

    @Override
    protected void update(Model model) {

    }
}

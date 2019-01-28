package parkeersimulator.view;

import parkeersimulator.framework.GridBagView;
import parkeersimulator.framework.Model;
import parkeersimulator.model.TabList;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Sidebar used to navigate the different views.
 *
 * @version 13.01.2019.
 */
public class SideBar extends GridBagView {

    private ArrayList<JButton> menuLabels;

    public SideBar() {
        super();
        menuLabels = new ArrayList<>();
        setPosition(0, 1);
        setGridHeight(2);
        setBackground(new Color(76, 83, 85));
        setLayout(new GridBagLayout());
    }

    /**
     * Add menu item to sidebar.
     *
     * @param name label of the menu item.
     */
    private void addMenuItem(int number, String name) {
        GridBagConstraints constraints = new GridBagConstraints();
        JButton menuLabel = new JButton();
        JPanel separator = new JPanel();

        menuLabel.setFont(new Font("Dubai", -1, 14));
        menuLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        menuLabel.setText(name);
        menuLabel.setFocusPainted(false);
        menuLabel.setBorderPainted(false);
        menuLabel.setContentAreaFilled(false);

        constraints.gridx = 0;
        constraints.gridy = (number * 2) - 2;
        constraints.weightx = 1;
        constraints.insets = new Insets(number == 1 ? 30 : 5, 20, 0, 20);

        add(menuLabel, constraints);
        menuLabels.add(menuLabel);

        separator.setBackground(Color.white);
        separator.setPreferredSize(new Dimension(0, 1));
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = (number * 2) - 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(0, 15, 5, 15);

        add(separator, constraints);

        menuLabel.addActionListener(e -> sendEvent(number));
    }

    /**
     * Returns the text from a JLabel at the given index.
     *
     * @param index the index of the JLabel.
     * @return the text assigned to the JLabel.
     */
    public String getMenuText(int index) {
        return menuLabels.get(index).getText();
    }

    @Override
    protected void update(Model model) {
        if (model instanceof TabList) {
            TabList tabList = (TabList) model;
            if (menuLabels.size() <= 0) {
                String[] items = tabList.getMenuItems();
                for (int index = 0; index < items.length; index++) {
                    addMenuItem(index + 1, items[index]);
                }
                JPanel bottomSpacer = new JPanel();
                bottomSpacer.setOpaque(false);
                GridBagConstraints constraints = new GridBagConstraints();
                constraints.gridx = 0;
                constraints.gridy = items.length * 2;
                constraints.weighty = 1;
                constraints.fill = GridBagConstraints.VERTICAL;
                add(bottomSpacer, constraints);
            }

            for (JButton menuLabel : menuLabels) {
                if (menuLabel.getText().equals(tabList.getActiveTab())) {
                    menuLabel.setForeground(new Color(116, 185, 255));
                } else {
                    menuLabel.setForeground(Color.white);
                }
            }
        }
    }
}

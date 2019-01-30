package parkeersimulator.view.about;

import parkeersimulator.enums.theme.ThemeColors;
import parkeersimulator.enums.theme.ThemeFonts;
import parkeersimulator.framework.GridBagView;
import parkeersimulator.framework.Model;

import javax.swing.*;
import java.awt.*;

/**
 * This class contains the about page.
 *
 * @version 29.01.2019.
 */
public class AboutView extends GridBagView {

    public AboutView(){
        super();
        setPosition(1, 1);
        setHorizontalPriority(1);
        setVerticalPriority(1);
        setGridHeight(2);
        setBackground(ThemeColors.BACKGROUND_LIGHT.getColor());

        setLayout(new GridBagLayout());
        GridBagConstraints constraints;

        JPanel leftSpacer = new JPanel();
        leftSpacer.setOpaque(false);
        constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(leftSpacer, constraints);

        JPanel rightSpacer = new JPanel();
        rightSpacer.setOpaque(false);
        constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.gridx = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(rightSpacer, constraints);

        addItem(0, ThemeColors.CAR_ADHOC.getColor(), "Normal customers car");
        addItem(1, ThemeColors.CAR_PASSHOLDER.getColor(), "Pass holder car");
        addItem(2, ThemeColors.CAR_PASSHOLDER_SPOT.getColor(), "Reserved spot for pass holder");
        addItem(3, ThemeColors.CAR_RESERVATION.getColor(), "Reserved car");
        addItem(4, ThemeColors.CAR_RESERVATION_SPOT.getColor(), "Reserved spot");
    }

    /**
     * Add an new about item.
     *
     * @param gridY y position of the item.
     * @param color color of the item.
     * @param text text of the item.
     */
    private void addItem(int gridY, Color color, String text){
        GridBagConstraints constraints;

        JPanel colorBlock = new JPanel();
        colorBlock.setBackground(color);
        colorBlock.setOpaque(true);
        colorBlock.setSize(new Dimension(40, 30));
        colorBlock.setPreferredSize(new Dimension(20, 20));
        colorBlock.setBorder(BorderFactory.createLineBorder(Color.black));

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = gridY;
        constraints.insets = new Insets(10, 10, 10, 10);

        add(colorBlock, constraints);

        JLabel label = new JLabel();
        label.setText(text);
        label.setFont(ThemeFonts.LARGE_REGULAR.getFont());
        label.setForeground(ThemeColors.FONT_DARK.getColor());
        constraints = new GridBagConstraints();
        constraints.gridx = 2;
        constraints.gridy = gridY;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.WEST;

        add(label, constraints);
    }

    /**
     * Updates the model.
     *
     * @param model Model to use in the update.
     */
    protected void update(Model model) {

    }
}

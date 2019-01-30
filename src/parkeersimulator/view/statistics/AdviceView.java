package parkeersimulator.view.statistics;

import parkeersimulator.enums.theme.ThemeColors;
import parkeersimulator.enums.theme.ThemeFonts;
import parkeersimulator.framework.GridBagView;
import parkeersimulator.framework.Model;
import parkeersimulator.model.statistics.Advice;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Class that represents a JPanel which displays all the current advices.
 *
 * @version 30.01.2019.
 */
public class AdviceView extends GridBagView {

    public AdviceView()
    {
        super();
        setPosition(1, 1);
        setHorizontalPriority(1);
        setVerticalPriority(1);
        setGridHeight(2);
        setBorder(new EmptyBorder(20, 0, 40, 0));
        setBackground(ThemeColors.BACKGROUND_LIGHT.getColor());
        setBackground(ThemeColors.INTERACTION.getColor());
        setOpaque(true);

        setLayout(new GridBagLayout());
        createTopText();
        createLabel("* Not enough data yet.", 0);
    }

    /**
     * Creates the "Current advice:" text in the Advice View
     */
    private void createTopText()
    {
        JLabel label = new JLabel();
        label.setText("Current advice:");
        label.setFont(ThemeFonts.LARGE_BOLD.getFont());
        label.setForeground(ThemeColors.FONT_LIGHT.getColor());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 15, 0, 20);
        constraints.anchor = GridBagConstraints.WEST;
        add(label, constraints);
    }

    /**
     * Creates a new advice JLabel.
     *
     * @param text the text of the advice.
     * @param index the index used for the y coordinates.
     */
    private void createLabel(String text, int index) {
        JLabel label = new JLabel();
        label.setText(text);
        label.setFont(ThemeFonts.NORMAL_BOLD.getFont());
        label.setForeground(ThemeColors.FONT_LIGHT.getColor());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = index + 1;
        constraints.insets = new Insets(10, 15, 0, 20);
        constraints.anchor = GridBagConstraints.WEST;
        add(label, constraints);
    }


    @Override
    protected void update(Model model) {
        if (model instanceof Advice) {
            removeAll();
            createTopText();
            int i = 0;
            for (String advice : ((Advice) model).getAdvices()) {
                createLabel(advice,i);
                i++;
            }
        }
    }
}

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
public class AdvicePanel extends GridBagView {

    public AdvicePanel()
    {
        super();
        setPosition(1, 1);
        setHorizontalPriority(1);
        setVerticalPriority(1);
        setGridHeight(1);
        setBorder(new EmptyBorder(20, 0, 40, 0));
        setBackground(ThemeColors.BACKGROUND_LIGHT.getColor());

        setLayout(new GridBagLayout());
    }

    /**
     * Creates a new advice JLabel.
     *
     * @param text the text of the advice.
     * @param index the index used for the y coordinates.
     */
    private void CreateLabel(String text, int index) {
        JLabel label = new JLabel();
        label.setText("* " + text);
        label.setFont(ThemeFonts.NORMAL_BOLD.getFont());
        label.setForeground(ThemeColors.FONT_DARK.getColor());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = index;
        constraints.insets = new Insets(10, 15, 0, 20);
        constraints.anchor = GridBagConstraints.WEST;
        add(label, constraints);

    }


    @Override
    protected void update(Model model) {
        if (model instanceof Advice) {
            removeAll();
            int i = 0;
            for (String advice : ((Advice) model).getAdvices()) {
                CreateLabel(advice,i);
                i++;
            }
        }
    }
}

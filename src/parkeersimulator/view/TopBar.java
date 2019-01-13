package parkeersimulator.view;

import javax.swing.*;
import java.awt.*;

public class TopBar extends GridBagView {

    private JLabel titleLabel;
    private JLabel dateTimeLabel;
    private JPanel spacer;

    public TopBar() {
        super();
        setGridWidth(2);
        setBackground(new Color(45, 52, 54));

        setLayout(new GridBagLayout());
        setUIComponents();
    }

    private void setUIComponents()
    {
        titleLabel = new JLabel();
        titleLabel.setFont(new Font("Dubai", Font.BOLD, 14));
        titleLabel.setForeground(Color.white);
        titleLabel.setText("Parkeersimulator");
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 15, 0, 0);
        add(titleLabel, constraints);

        spacer = new JPanel();
        spacer.setOpaque(false);
        constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(spacer, constraints);

        dateTimeLabel = new JLabel();
        dateTimeLabel.setFont(new Font("Dubai Light", Font.BOLD, 14));
        dateTimeLabel.setForeground(Color.white);
        constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 0, 0, 15);
        add(dateTimeLabel, constraints);
    }

    public void setDateTimeLabelText(String text)
    {
        dateTimeLabel.setText(text);
    }

}

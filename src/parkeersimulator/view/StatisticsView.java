package parkeersimulator.view;

import parkeersimulator.framework.GridBagView;
import parkeersimulator.framework.Model;
import parkeersimulator.model.statistics.StatisticsList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

//TODO add more statistics (and graphs)
public class StatisticsView extends GridBagView {

    private JLabel total;
    private JLabel daily;
    private JLabel hourly;

    public StatisticsView() {
        super();
        setPosition(1, 1);
        setHorizontalPriority(1);
        setVerticalPriority(1);
        setGridHeight(1);
        setBorder(new EmptyBorder(20, 0, 40, 0));
        setBackground(new Color(215, 215, 215));

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

        generateLabels();
    }

    private JLabel generateLabel(String name) {
        JLabel label = new JLabel();
        label.setText(name);
        label.setFont(new Font("Dubai", Font.BOLD, 14));
        label.setForeground(Color.white);

        return label;
    }

    private void generateLabels() {
        GridBagConstraints constraints;

        JLabel totalLabel = generateLabel("Total: ");
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 15, 0, 20);
        constraints.anchor = GridBagConstraints.WEST;
        add(totalLabel, constraints);

        JLabel hourlyLabel = generateLabel("Past hour average: ");
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.insets = new Insets(10, 15, 0, 20);
        constraints.anchor = GridBagConstraints.WEST;
        add(hourlyLabel, constraints);

        JLabel dailyLabel = generateLabel("Past day average: ");
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.insets = new Insets(10, 15, 0, 20);
        constraints.anchor = GridBagConstraints.WEST;
        add(dailyLabel, constraints);

        total = generateLabel("0");
        constraints = new GridBagConstraints();
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 15, 0, 20);
        add(total, constraints);

        hourly = generateLabel("0");
        constraints = new GridBagConstraints();
        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.insets = new Insets(10, 15, 0, 20);
        add(hourly, constraints);

        daily = generateLabel("0");
        constraints = new GridBagConstraints();
        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.insets = new Insets(10, 15, 0, 20);
        add(daily, constraints);
    }

    private void generateCharts()
    {
        //StatisticsChart hourlyProfit = new StatisticsChart("Hourly Profit",);
    }

    @Override
    protected void update(Model model) {
        if (model instanceof StatisticsList) {
            StatisticsList statisticsList = (StatisticsList) model;
            total.setText(String.valueOf(statisticsList.getTotal("profit.total")));
            hourly.setText(String.valueOf(statisticsList.getHourAvg("profit.total")));
            daily.setText(String.valueOf(statisticsList.getDayAvg("profit.total")));
        }
    }
}

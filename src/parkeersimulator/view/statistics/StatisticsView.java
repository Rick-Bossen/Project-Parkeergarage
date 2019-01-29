package parkeersimulator.view.statistics;

import parkeersimulator.framework.GridBagView;
import parkeersimulator.framework.Model;
import parkeersimulator.model.statistics.ChartList;
import parkeersimulator.model.statistics.StatisticsList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

//TODO add more statistics

/**
 * This class contains the whole Statistics/Results page.
 *
 * @version 28.01.2019.
 */
public class StatisticsView extends GridBagView {

    private JLabel total;
    private JLabel daily;
    private JLabel hourly;

    public StatisticsView(ChartList charts) {
        super();
        setPosition(1, 1);
        setHorizontalPriority(1);
        setVerticalPriority(1);
        setGridHeight(1);
        setBorder(new EmptyBorder(20, 0, 40, 0));
        setBackground(Color.white);

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
        generateCharts(charts);
    }

    /**
     * Generates a new JLabel and styles it.
     *
     * @param text the text that the JLabel should display.
     * @return the generated JLabel.
     */
    private JLabel generateLabel(String text) {
        JLabel label = new JLabel();
        label.setText(text);
        label.setFont(new Font("Dubai", Font.BOLD, 14));
        label.setForeground(new Color(45, 52, 54));

        return label;
    }

    /**
     * Generates all the JLabels on the the view.
     */
    private void generateLabels() {
        GridBagConstraints constraints;

        JLabel totalLabel = generateLabel("Total profit: ");
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 15, 0, 20);
        constraints.anchor = GridBagConstraints.WEST;
        add(totalLabel, constraints);

        JLabel hourlyLabel = generateLabel("Past hour profit: ");
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.insets = new Insets(10, 15, 0, 20);
        constraints.anchor = GridBagConstraints.WEST;
        add(hourlyLabel, constraints);

        JLabel dailyLabel = generateLabel("Past day profit: ");
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

    /**
     * Retrieves all the needed charts from a ChartList and adds them to the view.
     *
     * @param charts the ChartList to retrieve the charts from.
     */
    private void generateCharts(ChartList charts)
    {
        GridBagConstraints constraints;

        StatisticsChart hourlyProfit = charts.getChart("profit.hourly");
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.insets = new Insets(10, 15, 0, 20);
        add(hourlyProfit, constraints);

        StatisticsChart dailyProfit = charts.getChart("profit.daily");
        constraints = new GridBagConstraints();
        constraints.gridx = 2;
        constraints.gridy = 3;
        constraints.insets = new Insets(10, 15, 0, 20);
        add(dailyProfit, constraints);
    }

    /**
     * Resets all the content of tie view and links a new ChartList to the charts.
     *
     * @param chartList the new ChartList to be used.
     */
    public void reset(ChartList chartList)
    {
        removeAll();
        generateLabels();
        generateCharts(chartList);
    }

    @Override
    protected void update(Model model) {
        if (model instanceof StatisticsList) {
            StatisticsList statisticsList = (StatisticsList) model;
            total.setText(String.valueOf(statisticsList.getTotal("profit.total")));
            hourly.setText(String.valueOf(statisticsList.getPastHour("profit.total")));
            daily.setText(String.valueOf(statisticsList.getPastDay("profit.total")));
        }
    }
}

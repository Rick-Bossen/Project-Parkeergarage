package parkeersimulator.view.statistics;

import parkeersimulator.enums.theme.ThemeColors;
import parkeersimulator.enums.theme.ThemeFonts;
import parkeersimulator.framework.GridBagView;
import parkeersimulator.framework.Model;
import parkeersimulator.model.statistics.ChartList;
import parkeersimulator.model.statistics.StatisticsList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;

//TODO add more statistics

/**
 * This class contains the whole Statistics/Results page.
 *
 * @version 28.01.2019.
 */
public class StatisticsView extends GridBagView {

    private HashMap<String, JLabel> labels;

    public StatisticsView(ChartList charts, AdvicePanel advicePanel) {
        super();
        labels = new HashMap<>();
        setPosition(1, 1);
        setHorizontalPriority(1);
        setVerticalPriority(1);
        setGridHeight(1);
        setBorder(new EmptyBorder(20, 0, 40, 0));
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

        generateLabels();
        generateCharts(charts);
        AddAdvicePanel(advicePanel);
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
        label.setFont(ThemeFonts.NORMAL_BOLD.getFont());
        label.setForeground(ThemeColors.FONT_DARK.getColor());

        return label;
    }

    /**
     * Generates all the JLabels on the the view.
     */
    private void generateLabels() {
        addStatisticField(0, "profit.total", "Total profit: ");
        addStatisticField(1, "profit.hourly", "Past hour profit: ");
        addStatisticField(2, "profit.daily", "Past day profit: ");
        addStatisticField(3, "cars.entered", "Total entered cars: ");
        addStatisticField(4, "cars.missed", "Total cars missed: ");
    }

    /**
     * Add a new field to the view.
     * @param gridY y position of the field.
     * @param id String containing the key of the field.
     * @param label text of the label.
     */
    private void addStatisticField(int gridY, String id, String label){
        JLabel textLabel = generateLabel(label);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = gridY;
        constraints.insets = new Insets(10, 15, 0, 20);
        constraints.anchor = GridBagConstraints.WEST;
        add(textLabel, constraints);

        JLabel field = generateLabel("0");
        constraints = new GridBagConstraints();
        constraints.gridx = 2;
        constraints.gridy = gridY;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 15, 0, 20);
        add(field, constraints);

        labels.put(id,field);
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
        constraints.gridy = labels.size();
        constraints.insets = new Insets(10, 15, 0, 20);
        add(hourlyProfit, constraints);

        StatisticsChart dailyProfit = charts.getChart("profit.daily");
        constraints = new GridBagConstraints();
        constraints.gridx = 2;
        constraints.gridy = labels.size();
        constraints.insets = new Insets(10, 15, 0, 20);
        add(dailyProfit, constraints);

        StatisticsChart dailyCars = charts.getChart("entering.all");
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = labels.size() + 1;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(10, 15, 0, 20);
        add(dailyCars, constraints);
    }

    private void AddAdvicePanel(AdvicePanel advicePanel)
    {
        GridBagConstraints constraints;

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = labels.size() + 2;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(10, 15, 0, 20);
        add(advicePanel, constraints);
    }

    /**
     * Resets all the content of tie view and links a new ChartList to the charts.
     *
     * @param chartList the new ChartList to be used.
     */
    public void reset(ChartList chartList, AdvicePanel advicePanel)
    {
        removeAll();
        generateLabels();
        generateCharts(chartList);
        AddAdvicePanel(advicePanel);
    }

    @Override
    protected void update(Model model) {
        if (model instanceof StatisticsList) {
            StatisticsList statisticsList = (StatisticsList) model;
            labels.get("profit.total").setText("€ " + statisticsList.getTotal("profit.total"));
            labels.get("profit.hourly").setText("€ " + statisticsList.getPastHour("profit.total"));
            labels.get("profit.daily").setText("€ " + statisticsList.getPastDay("profit.total"));
            labels.get("cars.entered").setText(Integer.toString(statisticsList.getTotal("cars.entered")));
            labels.get("cars.missed").setText(Integer.toString(statisticsList.getTotal("cars.missed")));
        }
    }
}

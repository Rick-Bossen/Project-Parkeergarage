package parkeersimulator.view.settings;

import parkeersimulator.enums.settings.SettingCategories;
import parkeersimulator.enums.theme.ThemeColors;
import parkeersimulator.enums.theme.ThemeFonts;
import parkeersimulator.framework.GridBagView;
import parkeersimulator.framework.Model;
import parkeersimulator.model.settings.SettingCategory;
import parkeersimulator.model.settings.SettingList;
import parkeersimulator.utility.Settings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This class contains the whole settings page.
 *
 * @version 28.01.2019.
 */
public class SettingView extends GridBagView {

    private NumberFormatter intFormatter;
    private HashMap<String, JFormattedTextField> fields;
    private HashMap<String, SettingCategory> categories;

    public SettingView() {
        super();
        fields = new HashMap<>();
        categories = new HashMap<>();
        setPosition(1, 1);
        setHorizontalPriority(1);
        setVerticalPriority(1);
        setGridHeight(1);
        setBorder(new EmptyBorder(20, 0, 40, 0));
        setBackground(ThemeColors.BACKGROUND_LIGHT.getColor());

        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(true);
        formatter.setCommitsOnValidEdit(true);
        this.intFormatter = formatter;

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
    }

    /**
     * Add a new category label to the field.
     *
     * @param index    index of the location x.
     * @param category Category.
     */
    private void addCategory(int index, SettingCategory category) {
        categories.put(category.getCategory(), category);
        JLabel categoryLabel = new JLabel();
        categoryLabel.setText(category.getCategory());
        categoryLabel.setFont(ThemeFonts.LARGE_BOLD.getFont());
        categoryLabel.setForeground(ThemeColors.FONT_DARK.getColor());
        categoryLabel.setPreferredSize(new Dimension(400, 25));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = index;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(20, 15, 0, 20);
        constraints.anchor = GridBagConstraints.WEST;
        add(categoryLabel, constraints);

        JSeparator categorySeparator = new JSeparator(SwingConstants.HORIZONTAL);
        categorySeparator.setBackground(ThemeColors.FONT_DARK.getColor());
        categorySeparator.setForeground(ThemeColors.FONT_DARK.getColor());
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = index + 1;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(0, 15, 10, 20);
        add(categorySeparator, constraints);
    }

    /**
     * Add a field to the view.
     *
     * @param index    location x of the items.
     * @param category Category of this item.
     * @param entry    Entry containing the name and label.
     */
    private void addField(int index, SettingCategory category, Map.Entry entry) {
        GridBagConstraints constraints;
        String name = (String) entry.getValue();
        String key = (String) entry.getKey();

        JLabel settingsLabel = new JLabel();
        settingsLabel.setText(name + ": ");
        settingsLabel.setFont(ThemeFonts.NORMAL_BOLD.getFont());
        settingsLabel.setForeground(ThemeColors.FONT_DARK.getColor());

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = index;
        constraints.insets = new Insets(10, 15, 0, 20);
        constraints.anchor = GridBagConstraints.WEST;
        add(settingsLabel, constraints);

        JFormattedTextField settingsField = new JFormattedTextField(intFormatter);
        settingsField.setFont(ThemeFonts.NORMAL_BOLD.getFont());
        settingsField.setValue(Settings.get(key));
        settingsField.setColumns(10);
        settingsField.addPropertyChangeListener(e -> categories.get(category.getCategory()).addValue(key, (int) settingsField.getValue()));

        constraints = new GridBagConstraints();
        constraints.gridx = 2;
        constraints.gridy = index;
        constraints.insets = new Insets(15, 0, 0, 15);

        fields.put(key, settingsField);
        add(settingsField, constraints);
    }

    /**
     * Retrieve all categories in the view.
     *
     * @return categories Categories in the view.
     */
    public Collection<SettingCategory> getCategories() {
        return categories.values();
    }

    /**
     * Update all categories in the list.
     *
     * @return the updated categories.
     */
    public ArrayList<SettingCategories> updateCategories() {
        ArrayList<SettingCategories> updatedCategories = new ArrayList<>();
        for (Map.Entry<String, JFormattedTextField> entry : fields.entrySet()) {
            entry.getValue().setValue(Settings.get(entry.getKey()));
        }
        for (Map.Entry<String, SettingCategory> categoryEntry : categories.entrySet()) {
            SettingCategory category = categoryEntry.getValue();
            if (category.update()) {
                updatedCategories.add(category.getCategoryType());
            }
        }
        return updatedCategories;
    }

    @Override
    protected void update(Model model) {
        if (model instanceof SettingList) {
            int index = 0;
            for (SettingCategory category : ((SettingList) model).getCategories()) {
                addCategory(index, category);
                index += 2;
                HashMap<String, String> settings = category.getSettings();
                for (Map.Entry<String, String> entry : settings.entrySet()) {
                    addField(index, category, entry);
                    index++;
                }
            }
        }
    }
}

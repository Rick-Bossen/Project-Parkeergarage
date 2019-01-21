package parkeersimulator.view;

import parkeersimulator.framework.GridBagView;
import parkeersimulator.framework.Model;
import parkeersimulator.model.SettingCategory;
import parkeersimulator.model.SettingList;
import parkeersimulator.utility.Settings;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;
import java.util.*;

/**
 * This class contains the whole settings page.
 */
public class SettingView extends GridBagView {

    private NumberFormatter intFormatter;
    private HashMap<String, JFormattedTextField> fields;
    private HashMap<String,SettingCategory> categories;

    public SettingView() {
        super();
        fields = new HashMap<>();
        categories = new HashMap<>();
        setPosition(1, 1);
        setHorizontalPriority(1);
        setVerticalPriority(1);
        setGridHeight(1);
        setBackground(new Color(195, 195, 195));

        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
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
     * Add a new category label to the field
     * @param index index of the location x
     * @param category Category
     */
    private void addCategory(int index, SettingCategory category){
        categories.put(category.getCategory(), category);
        JLabel categoryLabel = new JLabel();
        categoryLabel.setText(category.getCategory());
        categoryLabel.setFont(new Font("Dubai", Font.BOLD, 16));
        categoryLabel.setForeground(Color.white);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = index;
        constraints.insets = new Insets(15, 15, 0, 20);
        add(categoryLabel, constraints);
    }

    /**
     * Add a field to the view
     * @param index location x of the items
     * @param category Category of this item
     * @param entry Entry containing the name and label
     */
    private void addField(int index, SettingCategory category, Map.Entry entry){
        GridBagConstraints constraints;
        String name = (String) entry.getValue();
        String key = (String) entry.getKey();

        JLabel settingsLabel = new JLabel();
        settingsLabel.setText(name + ": ");
        settingsLabel.setFont(new Font("Dubai", Font.BOLD, 14));
        settingsLabel.setForeground(Color.white);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = index;
        constraints.insets = new Insets(15, 15, 0, 20);
        add(settingsLabel, constraints);

        JFormattedTextField settingsField = new JFormattedTextField(intFormatter);
        settingsField.setFont(new Font("Dubai", Font.BOLD, 14));
        settingsField.setValue(Settings.get(key));
        settingsField.setColumns(10);
        settingsField.setBorder(null);
        settingsField.addPropertyChangeListener(e -> categories.get(category.getCategory()).addValue(key, (int)settingsField.getValue()));

        constraints = new GridBagConstraints();
        constraints.gridx = 2;
        constraints.gridy = index;
        constraints.insets = new Insets(15, 0, 0, 15);

        fields.put(key, settingsField);
        add(settingsField, constraints);
    }

    /**
     * Retrieve all categories in the view.
     * @return categories Categories in the view.
     */
    public Collection<SettingCategory> getCategories(){
        return categories.values();
    }

    /**
     * Update all categories in the list.
     */
    public ArrayList<String> updateCategories(){
        ArrayList<String> updatedCategories = new ArrayList<>();
        Iterator settingsIterator = fields.entrySet().iterator();
        while (settingsIterator.hasNext()) {
            Map.Entry entry = (Map.Entry)settingsIterator.next();
            JFormattedTextField field = (JFormattedTextField) entry.getValue();
            String key = (String) entry.getKey();
            field.setValue(Settings.get(key));
        }
        Iterator categoryIterator = categories.entrySet().iterator();
        while (categoryIterator.hasNext()) {
            Map.Entry entry = (Map.Entry)categoryIterator.next();
            SettingCategory category = (SettingCategory) entry.getValue();
            if(category.update()){
                updatedCategories.add(category.getCategory());
            }
        }
        return updatedCategories;
    }

    @Override
    protected void update(Model model) {
        if(model instanceof SettingList){
            int index = 0;
            for (SettingCategory category : ((SettingList) model).getCategories()){
                addCategory(index, category);
                index++;
                HashMap<String, String> settings = category.getSettings();
                Iterator settingsIterator = settings.entrySet().iterator();
                while (settingsIterator.hasNext()) {
                    addField(index, category, (Map.Entry)settingsIterator.next());
                    index++;
                }
            }
        }
    }
}

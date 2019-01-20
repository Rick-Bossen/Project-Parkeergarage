package parkeersimulator.view;

import parkeersimulator.framework.GridBagView;
import parkeersimulator.framework.Model;
import parkeersimulator.utility.Settings;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This class contains the whole settings page.
 */
public class SettingView extends GridBagView {

    private NumberFormatter intFormatter;
    private HashMap<String, JFormattedTextField> fields;
    private HashMap<String, String> settings = new HashMap<>(){{
        put("width", "Width");
        put("height", "Height");
    }};

    public SettingView() {
        super();
        fields = new HashMap<>();
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
        constraints.gridheight = settings.size();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(leftSpacer, constraints);

        JPanel rightSpacer = new JPanel();
        rightSpacer.setOpaque(false);
        constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.gridx = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(rightSpacer, constraints);

        Iterator settingsIterator = settings.entrySet().iterator();
        int index = 0;
        while (settingsIterator.hasNext()) {
            addField(index, (Map.Entry)settingsIterator.next());
            index++;
        }
    }

    private void addField(int index, Map.Entry entry){
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
        settingsField.addPropertyChangeListener(e -> Settings.setTemporary(key, (int)settingsField.getValue()));

        constraints = new GridBagConstraints();
        constraints.gridx = 2;
        constraints.gridy = index;
        constraints.insets = new Insets(15, 0, 0, 15);

        fields.put(key, settingsField);
        add(settingsField, constraints);
    }

    @Override
    protected void update(Model model) {
        if(model instanceof Settings){
            Iterator settingsIterator = settings.entrySet().iterator();
            while (settingsIterator.hasNext()) {
                String key = (String)((Map.Entry)settingsIterator.next()).getKey();
                fields.get(key).setValue(Settings.get(key));
            }
        }
    }
}

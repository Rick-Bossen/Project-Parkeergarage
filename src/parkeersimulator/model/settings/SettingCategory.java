package parkeersimulator.model.settings;

import parkeersimulator.enums.settings.SettingCategories;
import parkeersimulator.enums.settings.SettingType;
import parkeersimulator.framework.Model;
import parkeersimulator.utility.Settings;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class represents a settings category in which all the current settings are contained,
 * current settings can be updated.
 *
 * @version 28.01.2019.
 */
public class SettingCategory extends Model {

    private SettingCategories categoryType;
    private HashMap<String, String> settings;
    private HashMap<String, Integer> oldValues;
    private HashMap<String, Integer> newValues;

    SettingCategory(SettingCategories categoryType) {
        this.categoryType = categoryType;
        settings = new LinkedHashMap<>();
        newValues = new HashMap<>();
        oldValues = new HashMap<>();

        for(SettingType settingType : categoryType.getSettings()){
            settings.put(settingType.toString(), settingType.getLabel());
            oldValues.put(settingType.toString(), Settings.get(settingType.toString()));
        }
    }

    /**
     * Add a new value to this category.
     *
     * @param key   Setting key.
     * @param value New value of the setting.
     */
    public void addValue(String key, int value) {
        if (settings.get(key) != null && oldValues.get(key) != value) {
            newValues.put(key, value);
        }
    }

    /**
     * Get category name.
     *
     * @return name.
     */
    public String getCategory() {
        return categoryType.getLabel();
    }

    /**
     * Get category id.
     *
     * @return name.
     */
    public SettingCategories getCategoryType() {
        return categoryType;
    }

    /**
     * Get all settings of this category.
     *
     * @return HashMap with all settings.
     */
    public HashMap<String, String> getSettings() {
        return settings;
    }

    /**
     * Get all new values of this category.
     *
     * @return HashMap with all changed values.
     */
    public HashMap<String, Integer> getValues() {
        return newValues;
    }

    /**
     * Reset all changed values when necessary.
     *
     * @return Returns boolean if this category's settings have been changed.
     */
    public boolean update() {
        boolean updated = false;
        newValues.clear();
        for (Map.Entry<String, Integer> entry : oldValues.entrySet()) {
            String key = entry.getKey();
            if (Settings.get(key) != entry.getValue()) {
                updated = true;
                oldValues.put(key, Settings.get(key));
            }
        }

        return updated;
    }

}

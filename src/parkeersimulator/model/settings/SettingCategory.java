package parkeersimulator.model.settings;

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

    private String category;
    private String categoryId;
    private HashMap<String, String> settings;
    private HashMap<String, Integer> oldValues;
    private HashMap<String, Integer> newValues;

    SettingCategory(String categoryId, String category) {
        this.categoryId = categoryId;
        this.category = category;
        settings = new LinkedHashMap<>();
        newValues = new HashMap<>();
        oldValues = new HashMap<>();
    }

    /**
     * Add a setting to this category.
     *
     * @param key   Key of the setting.
     * @param label Label of the setting.
     */
    void addSetting(String key, String label) {
        settings.put(key, label);
        oldValues.put(key, Settings.get(key));
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
        return category;
    }

    /**
     * Get category id.
     *
     * @return name.
     */
    public String getCategoryId() {
        return categoryId;
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
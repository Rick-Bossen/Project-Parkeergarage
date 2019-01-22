package parkeersimulator.model;

import parkeersimulator.framework.Model;
import parkeersimulator.utility.Settings;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SettingCategory extends Model {

    private final String category;
    private final String categoryId;
    private final HashMap<String, String> settings;
    private final HashMap<String, Integer> oldValues;
    private final HashMap<String, Integer> newValues;

    public SettingCategory(String categoryId, String category){
        this.categoryId = categoryId;
        this.category = category;
        settings = new LinkedHashMap<>();
        newValues = new HashMap<>();
        oldValues = new HashMap<>();
    }

    /**
     * Add a setting to this category.
     * @param key Key of the setting.
     * @param label Label of the setting.
     */
    public void addSetting(String key, String label){
        settings.put(key, label);
        oldValues.put(key,  Settings.get(key));
    }

    /**
     * Add a new value to this category
     * @param key Setting key
     * @param value New value of the setting
     */
    public void addValue(String key, int value){
        if(settings.get(key) != null && oldValues.get(key) != value){
            newValues.put(key, value);
        }
    }

    /**
     * Get category name
     * @return name
     */
    public String getCategory(){
        return category;
    }

    /**
     * Get category id
     * @return name
     */
    public String getCategoryId(){
        return categoryId;
    }

    /**
     * Get all settings of this category
     * @return HashMap with all settings
     */
    public HashMap<String, String> getSettings(){
        return settings;
    }

    /**
     * Get all new values of this category
     * @return HashMap with all changed values
     */
    public HashMap<String, Integer> getValues(){
        return newValues;
    }

    /**
     * Reset all changed values when necessary.
     * @return Returns boolean if this category's settings have been changed.
     */
    public boolean update(){
        boolean updated = false;
        newValues.clear();
        for (Map.Entry<String, Integer> stringIntegerEntry : oldValues.entrySet()) {
            Integer intValue = (Integer) ((Map.Entry) stringIntegerEntry).getValue();
            String key = (String) ((Map.Entry) stringIntegerEntry).getKey();
            if (Settings.get(key) != intValue) {
                updated = true;
                oldValues.put(key, Settings.get(key));
            }
        }

        return updated;
    }

}

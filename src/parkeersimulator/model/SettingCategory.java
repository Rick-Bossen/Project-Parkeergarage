package parkeersimulator.model;

import parkeersimulator.framework.Model;
import parkeersimulator.utility.Settings;

import java.util.HashMap;

public class SettingCategory extends Model {

    private String category;
    private HashMap<String, String> settings;
    private HashMap<String, Integer> values;

    public SettingCategory(String category){
        this.category = category;
        settings = new HashMap<>();
        values = new HashMap<>();
    }

    /**
     * Add a setting to this category.
     * @param key Key of the setting.
     * @param label Label of the setting.
     */
    public void addSetting(String key, String label){
        settings.put(key, label);
    }

    /**
     * Add a new value to this category
     * @param key Setting key
     * @param value New value of the setting
     */
    public void addValue(String key, int value){
        if(settings.get(key) != null && Settings.get(key) != value){
            values.put(key, value);
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
        return values;
    }

    /**
     * Reset all changed values when necessary.
     */
    public void reset(){
        values.clear();
    }

}

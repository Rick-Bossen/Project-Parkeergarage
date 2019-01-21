package parkeersimulator.model;

import parkeersimulator.framework.Model;
import parkeersimulator.utility.Settings;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SettingCategory extends Model {

    private String category;
    private HashMap<String, String> settings;
    private HashMap<String, Integer> oldValues;
    private HashMap<String, Integer> newValues;

    public SettingCategory(String category){
        this.category = category;
        settings = new HashMap<>();
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
        Iterator valueIterator = oldValues.entrySet().iterator();
        while (valueIterator.hasNext()) {
            Map.Entry entry = (Map.Entry)valueIterator.next();
            Integer intValue = (Integer) entry.getValue();
            String key = (String) entry.getKey();
            if(Settings.get(key) != intValue){
                updated = true;
                oldValues.put(key, Settings.get(key));
            }
        }

        return updated;
    }

}

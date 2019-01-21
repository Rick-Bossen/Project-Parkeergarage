package parkeersimulator.controller;

import parkeersimulator.framework.Controller;
import parkeersimulator.framework.View;
import parkeersimulator.model.SettingCategory;
import parkeersimulator.utility.Settings;
import parkeersimulator.view.SettingView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SettingManager extends Controller {

    public static final int RESET_TO_DEFAULT = 1;
    public static final int SAVE_SETTINGS = 3;

    @Override
    public boolean event(View view, int eventId) {
        Settings settings;
        switch (eventId){
            case RESET_TO_DEFAULT:
                settings = Settings.getInstance();
                settings.loadDefaultConfig();
                settings.saveConfig();
                ((SettingView) view).clearCategories();
                break;
            case SAVE_SETTINGS:
                settings = Settings.getInstance();
                for (SettingCategory category : ((SettingView) view).getCategories()){
                    HashMap<String, Integer> values = category.getValues();
                    Iterator valueIterator = values.entrySet().iterator();
                    while (valueIterator.hasNext()) {
                        Map.Entry entry = (Map.Entry)valueIterator.next();
                        Integer intValue = (Integer) entry.getValue();
                        String key = (String) entry.getKey();
                        Settings.set(key, intValue);
                    }
                    category.reset();
                }
                settings.saveConfig();
                ((SettingView) view).clearCategories();
                break;
        }
        return false;
    }
}

package parkeersimulator.controller;

import parkeersimulator.framework.Controller;
import parkeersimulator.framework.View;
import parkeersimulator.model.SettingCategory;
import parkeersimulator.utility.Settings;
import parkeersimulator.view.SettingView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SettingManager extends Controller {

    public static final int RESET_TO_DEFAULT = 1;
    public static final int SAVE_SETTINGS = 3;

    private JFrame window;

    public SettingManager(JFrame window){
        this.window = window;
    }

    @Override
    public boolean event(View view, int eventId) {
        Settings settings;
        ArrayList<String> updatedCategories = new ArrayList<>();
        switch (eventId){
            case RESET_TO_DEFAULT:
                settings = Settings.getInstance();
                settings.loadDefaultConfig();
                settings.saveConfig();
                updatedCategories = ((SettingView) view).updateCategories();
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
                }
                settings.saveConfig();
                updatedCategories = ((SettingView) view).updateCategories();
                break;
        }

        for(String category : updatedCategories){
            handleCategory(category);
        }

        return false;
    }

    public void handleCategory(String update){
        switch (update){
            case "General":
                window.setSize(new Dimension(Settings.get("width"), Settings.get("height")));
                window.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - Settings.get("width")) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - Settings.get("height")) / 2);
                break;
        }
    }
}

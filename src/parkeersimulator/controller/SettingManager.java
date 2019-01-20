package parkeersimulator.controller;

import parkeersimulator.framework.Controller;
import parkeersimulator.framework.View;
import parkeersimulator.utility.Settings;

public class SettingManager extends Controller {

    public static final int RESET_TO_DEFAULT = 1;
    public static final int SAVE_SETTINGS = 3;

    @Override
    protected boolean event(View view, int eventId) {
        Settings settings;
        switch (eventId){
            case RESET_TO_DEFAULT:
                settings = Settings.getInstance();
                settings.loadDefaultConfig();
                settings.saveConfig();
                break;
            case SAVE_SETTINGS:
                settings = Settings.getInstance();
                settings.loadTemporaryConfig();
                settings.saveConfig();
                break;
        }
        return false;
    }
}

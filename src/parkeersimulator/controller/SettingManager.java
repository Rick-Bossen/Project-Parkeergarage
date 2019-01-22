package parkeersimulator.controller;

import parkeersimulator.framework.Controller;
import parkeersimulator.framework.View;
import parkeersimulator.model.*;
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
    private CarPark carPark;
    private Clock clock;

    public SettingManager(JFrame window, CarPark carPark, Clock clock){
        this.window = window;
        this.carPark = carPark;
        this.clock = clock;
    }

    @Override
    protected boolean event(View view, int eventId) {
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
            case "general":
                window.setSize(new Dimension(Settings.get("width"), Settings.get("height")));
                window.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - Settings.get("width")) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - Settings.get("height")) / 2);
                break;
            case "queue":
                carPark.getPaymentCarQueue().setSpeed(Settings.get("queue.payment.speed"));
                carPark.getExitCarQueue().setSpeed(Settings.get("queue.exit.speed"));
                for(CustomerGroup group : carPark.getCustomerGroups()){
                    if(group.getNewCar() instanceof AdHocCar){
                        group.getEntranceCarQueue().setSpeed(Settings.get("queue.adhoc.speed"));
                    }else if(group.getNewCar() instanceof ParkingPassCar){
                        group.getEntranceCarQueue().setSpeed(Settings.get("queue.pass.speed"));
                    }
                }
                break;
            case "simulation.presets":
                carPark.reset(Settings.get("carpark.floors"), Settings.get("carpark.rows"), Settings.get("carpark.places"), Settings.get("carpark.passPlaces"));
                clock.reset();
                break;
            case "simulation":
                for(CustomerGroup group : carPark.getCustomerGroups()){
                    if(group.getNewCar() instanceof AdHocCar){
                        group.setWeekDayArrivals(Settings.get("adhoc.arrivals.weekday"));
                        group.setWeekendArrivals(Settings.get("adhoc.arrivals.weekend"));
                    }else if(group.getNewCar() instanceof ParkingPassCar){
                        group.setWeekDayArrivals(Settings.get("pass.arrivals.weekday"));
                        group.setWeekendArrivals(Settings.get("pass.arrivals.weekend"));
                    }
                }
                break;
        }
    }
}

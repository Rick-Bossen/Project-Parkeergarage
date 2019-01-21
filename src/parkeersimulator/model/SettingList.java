package parkeersimulator.model;

import parkeersimulator.framework.Model;

import java.util.ArrayList;

/**
 *  Index of all setting categories.
 */
public class SettingList extends Model {

    public ArrayList<SettingCategory> categories;

    public SettingList(){
        categories = new ArrayList<>();
    }

    /**
     * Will all categories with the information.
     */
    public void fillCategories(){
        SettingCategory generalCategory = new SettingCategory("general", "General");
        generalCategory.addSetting("width", "Width");
        generalCategory.addSetting("height", "Height");
        categories.add(generalCategory);

        SettingCategory speedCategory = new SettingCategory("queue", "Queue Speeds (Cars per Minute)");
        speedCategory.addSetting("queue.adhoc.speed", "Default entrance");
        speedCategory.addSetting("queue.pass.speed", "Pass entrance");
        speedCategory.addSetting("queue.exit.speed", "Default exit");
        speedCategory.addSetting("queue.payment.speed", "Payments");
        categories.add(speedCategory);

        SettingCategory simulationPresetCategory = new SettingCategory("simulation.presets", "Simulation presets (Restarted after change)");
        simulationPresetCategory.addSetting("carpark.floors", "Amount of floors");
        simulationPresetCategory.addSetting("carpark.rows", "Amount of rows");
        simulationPresetCategory.addSetting("carpark.places", "Amount of places");
        categories.add(simulationPresetCategory);

        SettingCategory simulationCategory = new SettingCategory("simulation", "Simulation");
        simulationCategory.addSetting("tickspeed", "Tick Speed");
        simulationCategory.addSetting("adhoc.arrivals.weekday", "Normal arrivals/weekday");
        simulationCategory.addSetting("adhoc.arrivals.weekend", "Normal arrivals/weekend");
        simulationCategory.addSetting("pass.arrivals.weekday", "Parking pass/weekday");
        simulationCategory.addSetting("pass.arrivals.weekend", "Parking pass/weekend");
        categories.add(simulationCategory);

        updateViews();
    }

    /**
     * Get all categories.
     * @return List of all categories.
     */
    public ArrayList<SettingCategory> getCategories(){
        return categories;
    }

}

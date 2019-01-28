package parkeersimulator.model.settings;

import parkeersimulator.framework.Model;

import java.util.ArrayList;

/**
 * Index of all setting categories.
 *
 * @version 28.01.2019.
 */
public class SettingList extends Model {

    private ArrayList<SettingCategory> categories;

    public SettingList() {
        categories = new ArrayList<>();
    }

    /**
     * Fill all categories with the information.
     */
    public void fillCategories() {
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

        SettingCategory priceCategory = new SettingCategory("price", "Prices (Price per car)");
        priceCategory.addSetting("price.adhoc", "Normal visitor price");
        priceCategory.addSetting("price.pass", "Daily membership fee");
        priceCategory.addSetting("price.reserved", "Reservation price");
        categories.add(priceCategory);

        SettingCategory simulationPresetCategory = new SettingCategory("simulation.presets", "Simulation presets (Restarted after change)");
        simulationPresetCategory.addSetting("carpark.floors", "Amount of floors");
        simulationPresetCategory.addSetting("carpark.rows", "Amount of rows");
        simulationPresetCategory.addSetting("carpark.places", "Amount of places");
        simulationPresetCategory.addSetting("carpark.passPlaces", "Reserved Pass places");
        categories.add(simulationPresetCategory);

        SettingCategory simulationCategory = new SettingCategory("simulation", "Simulation");
        simulationCategory.addSetting("tickspeed", "Tick Speed");
        simulationCategory.addSetting("adhoc.arrivals.weekday", "Normal arrivals/weekday");
        simulationCategory.addSetting("adhoc.arrivals.weekend", "Normal arrivals/weekend");
        simulationCategory.addSetting("pass.arrivals.weekday", "Parking pass/weekday");
        simulationCategory.addSetting("pass.arrivals.weekend", "Parking pass/weekend");
        simulationCategory.addSetting("reserved.arrivals.weekday", "Reservations/weekday");
        simulationCategory.addSetting("reserved.arrivals.weekend", "Reservations/weekend");
        categories.add(simulationCategory);

        updateViews();
    }

    /**
     * Get all categories.
     *
     * @return List of all categories.
     */
    public ArrayList<SettingCategory> getCategories() {
        return categories;
    }

}

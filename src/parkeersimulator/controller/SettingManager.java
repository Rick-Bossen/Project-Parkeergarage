package parkeersimulator.controller;

import parkeersimulator.enums.settings.*;
import parkeersimulator.framework.Controller;
import parkeersimulator.framework.View;
import parkeersimulator.model.Clock;
import parkeersimulator.model.car.AdHocCar;
import parkeersimulator.model.car.ParkingPassCar;
import parkeersimulator.model.car.ReservedSpot;
import parkeersimulator.model.carpark.CarPark;
import parkeersimulator.model.carpark.CustomerGroup;
import parkeersimulator.model.settings.SettingCategory;
import parkeersimulator.utility.Settings;
import parkeersimulator.view.settings.SettingView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the controller for the settings tab.
 *
 * @version 28.01.2019.
 */
public class SettingManager extends Controller {

    public static final int RESET_TO_DEFAULT = 1;
    public static final int SAVE_SETTINGS = 3;

    private JFrame window;
    private CarPark carPark;
    private Clock clock;

    public SettingManager(JFrame window, CarPark carPark, Clock clock) {
        this.window = window;
        this.carPark = carPark;
        this.clock = clock;
    }

    @Override
    protected void event(View view, int eventId) {
        Settings settings;
        ArrayList<SettingCategories> updatedCategories = new ArrayList<>();
        switch (eventId) {
            case RESET_TO_DEFAULT:
                settings = Settings.getInstance();
                settings.loadDefaultConfig();
                settings.saveConfig();
                updatedCategories = ((SettingView) view).updateCategories();
                break;
            case SAVE_SETTINGS:
                settings = Settings.getInstance();
                for (SettingCategory category : ((SettingView) view).getCategories()) {
                    HashMap<String, Integer> values = category.getValues();
                    for (Map.Entry<String, Integer> entry : values.entrySet()) {
                        Settings.set(entry.getKey(), entry.getValue());
                    }
                }
                settings.saveConfig();
                updatedCategories = ((SettingView) view).updateCategories();
                break;
        }

        for (SettingCategories category : updatedCategories) {
            handleCategory(category);
        }
    }

    /**
     * Handles all the different settings categories.
     *
     * @param update the category to handle.
     */
    private void handleCategory(SettingCategories update) {
        switch (update) {
            case GENERAL:
                int width = GeneralSettings.WIDTH.getValue();
                int height = GeneralSettings.HEIGHT.getValue();
                window.setSize(new Dimension(width, height));
                window.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2);
                break;
            case QUEUE:
                carPark.getPaymentCarQueue().setSpeed(QueueSettings.QUEUE_PAYMENT_SPEED.getValue());
                carPark.getExitCarQueue().setSpeed(QueueSettings.QUEUE_EXIT_SPEED.getValue());
                for (CustomerGroup group : carPark.getCustomerGroups()) {
                    if (group.getNewCar() instanceof AdHocCar) {
                        group.getEntranceCarQueue().setSpeed(QueueSettings.QUEUE_ADHOC_SPEED.getValue());
                    } else if (group.getNewCar() instanceof ParkingPassCar) {
                        group.getEntranceCarQueue().setSpeed(QueueSettings.QUEUE_PASSHOLDERS_SPEED.getValue());
                    }
                }
                break;
            case SIMULATION_PRESETS:
                carPark.reset(
                        SimulationPresetSettings.CARPARK_FLOORS.getValue(),
                        SimulationPresetSettings.CARPARK_ROWS.getValue(),
                        SimulationPresetSettings.CARPARK_PLACES.getValue(),
                        SimulationPresetSettings.CARPARK_PASSHOLDER_PLACES.getValue()
                );
                clock.reset();
                break;
            case SIMULATION:
                for (CustomerGroup group : carPark.getCustomerGroups()) {
                    if (group.getNewCar() instanceof AdHocCar) {
                        group.setWeekDayArrivals(SimulationSettings.ADHOC_WEEKDAY.getValue());
                        group.setWeekendArrivals(SimulationSettings.ADHOC_WEEKEND.getValue());
                    } else if (group.getNewCar() instanceof ParkingPassCar) {
                        group.setWeekDayArrivals(SimulationSettings.PASSHOLDERS_WEEKDAY.getValue());
                        group.setWeekendArrivals(SimulationSettings.PASSHOLDERS_WEEKEND.getValue());
                    } else if (group.getNewCar() instanceof ReservedSpot) {
                        group.setWeekDayArrivals(SimulationSettings.RESERVATIONS_WEEKDAY.getValue());
                        group.setWeekendArrivals(SimulationSettings.RESERVATIONS_WEEKEND.getValue());
                    }
                }
                break;
        }
    }
}

package parkeersimulator.enums.settings;

import parkeersimulator.utility.Settings;

/**
 * A settings as default in the default.properties file.
 * Contains the different settings of the category [simulation].
 */
public enum SimulationSettings implements SettingType {

    TICKSPEED ("tickspeed", "Tick speed"),
    ADHOC_WEEKDAY ("adhoc.arrivals.weekday", "Normal arrivals/weekday"),
    ADHOC_WEEKEND ("adhoc.arrivals.weekend", "Normal arrivals/weekend"),
    PASSHOLDERS_WEEKDAY ("pass.arrivals.weekday", "Passholders/weekday"),
    PASSHOLDERS_WEEKEND ("pass.arrivals.weekend", "Passholders/weekend"),
    RESERVATIONS_WEEKDAY ("reserved.arrivals.weekday", "Reservations/weekday"),
    RESERVATIONS_WEEKEND ("reserved.arrivals.weekend", "Reservations/weekend"),
    EVENT_THEATRE ("event.arrivals.theatre", "Event: Theatre"),
    EVENT_LATE_OPENING ("event.arrivals.lateOpening", "Event: Late opening");

    private final String name;
    private final String label;

    SimulationSettings(String name, String label) {
        this.name = name;
        this.label = label;
    }

    /**
     * Return the name of the settings.
     *
     * @return name.
     */
    public String toString() {
        return this.name;
    }

    /**
     * Return the label of the settings.
     *
     * @return label.
     */
    public String getLabel(){
        return this.label;
    }

    /**
     * Get the value of the given settings.
     *
     * @return setting value.
     */
    public int getValue(){
        return Settings.get(name);
    }

}

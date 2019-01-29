package parkeersimulator.enums.settings;

import parkeersimulator.utility.Settings;

/**
 * A settings as default in the default.properties file.
 * Contains the different settings of the category [simulation presets].
 */
public enum SimulationPresetSettings implements SettingType {

    CARPARK_FLOORS("carpark.floors", "Amount of floors"),
    CARPARK_ROWS("carpark.rows", "Amount of rows"),
    CARPARK_PLACES("carpark.places", "Amount of places"),
    CARPARK_PASSHOLDER_PLACES("carpark.passPlaces", "Reserved Pass places");

    private final String name;
    private final String label;

    SimulationPresetSettings(String name, String label) {
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
    public String getLabel() {
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
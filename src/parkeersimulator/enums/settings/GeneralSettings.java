package parkeersimulator.enums.settings;

import parkeersimulator.utility.Settings;

/**
 * A settings as default in the default.properties file.
 * Contains the different settings of the category [general].
 */
public enum GeneralSettings implements SettingType {

    WIDTH("width", "Width"),
    HEIGHT("height", "Height");

    private final String name;
    private final String label;

    GeneralSettings(String name, String label) {
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

package parkeersimulator.enums.settings;

import parkeersimulator.utility.Settings;

/**
 * A settings as default in the default.properties file.
 * Contains the different settings of the category [prices].
 */
public enum PriceSettings implements SettingType {

    PRICE_FOR_ADHOC("price.adhoc", "Normal visitor price"),
    PRICE_FOR_RESERVATIONS("price.reserved", "Daily membership fee"),
    PRICE_FOR_PASSHOLDERS("price.pass", "Reservation price");

    private final String name;
    private final String label;

    PriceSettings(String name, String label) {
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

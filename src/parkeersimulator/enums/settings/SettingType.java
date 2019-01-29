package parkeersimulator.enums.settings;

public interface SettingType {

    /**
     * Return the name of the settings.
     *
     * @return name.
     */
    String toString();

    String getLabel();

    int getValue();

}

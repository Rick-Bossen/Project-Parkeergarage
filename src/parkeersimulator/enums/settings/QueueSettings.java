package parkeersimulator.enums.settings;

import parkeersimulator.utility.Settings;

/**
 * A settings as default in the default.properties file.
 * Contains the different settings of the category [queue speeds].
 */
public enum QueueSettings implements SettingType {

    QUEUE_PAYMENT_SPEED("queue.payment.speed", "Default entrance"),
    QUEUE_EXIT_SPEED("queue.exit.speed", "Default exit"),
    QUEUE_ADHOC_SPEED("queue.adhoc.speed", "Pass entrance"),
    QUEUE_PASSHOLDERS_SPEED("queue.pass.speed", "Payments");

    private final String name;
    private final String label;

    QueueSettings(String name, String label) {
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

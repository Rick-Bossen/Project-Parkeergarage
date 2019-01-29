package parkeersimulator.enums.settings;

/**
 * Enum which represents all categories.
 */
public enum SettingCategories {

    GENERAL ("general", "General", GeneralSettings.values()),
    QUEUE ("queue", "Queue Speeds (Cars per Minute)", QueueSettings.values()),
    PRICES ("price", "Prices (Price per car)", PriceSettings.values()),
    SIMULATION_PRESETS ("simulation.presets", "Simulation presets (Restarted after change)", SimulationPresetSettings.values()),
    SIMULATION ("simulation", "Simulation", SimulationSettings.values());


    private final String category;
    private final String label;
    private final SettingType[] settings;

    SettingCategories(String category, String label, SettingType[] settings){
        this.category = category;
        this.label = label;
        this.settings = settings;
    }

    /**
     * Return the label of the category
     *
     * @return category
     */
    public String getLabel(){
        return this.label;
    }

    public SettingType[] getSettings(){
        return this.settings;
    }

    /**
     * Return the category.
     *
     * @return String category.
     */
    public String toString(){
        return this.category;
    }

}

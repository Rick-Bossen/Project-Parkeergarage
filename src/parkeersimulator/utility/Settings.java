package parkeersimulator.utility;

import parkeersimulator.enums.settings.SettingType;

import java.io.*;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Class which represents all user editable settings.
 * Note. All used settings need to be of the type Integer.
 *
 * @version 28.01.2019.
 */
public class Settings {

    private static Settings instance = new Settings();
    private final String defaultFile = "default.properties";
    private final String userFile = "user-config.properties";
    private Properties properties;

    /**
     * Create a new settings manager whilst load default and user config.
     */
    private Settings() {
        properties = new Properties();
        loadDefaultConfig();
        loadUserConfig();
        saveConfig();
    }

    /**
     * Return instance of the settings managers.
     *
     * @return Settings.
     */
    public static Settings getInstance() {
        return instance;
    }

    /**
     * Get the integer value of a setting based on the setting type.
     * If the key is not found return 0.
     *
     * @param settingType name of the setting.
     * @return value of the setting.
     */
    public static int get(SettingType settingType) {
        return get(settingType.toString());
    }

    /**
     * Get the integer value of a setting,
     * If the key is not found return 0.
     *
     * @param key name of the setting.
     * @return value of the setting.
     */
    public static int get(String key) {
        String value = instance.properties.getProperty(key);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    /**
     * Update a setting. Only allowed to update existing settings.
     *
     * @param key   Key of the setting.
     * @param value Value of the configuration.
     */
    public static void set(String key, int value) {
        if (instance.properties.getProperty(key) != null) {
            instance.properties.setProperty(key, Integer.toString(value));
        }
    }

    /**
     * Loads all user config,
     * Checks if the user config is present as default config and contains integer values.
     */
    private void loadUserConfig() {
        Properties userConfig = new Properties();
        try {
            InputStream config = new FileInputStream(userFile);
            userConfig.load(config);
            config.close();

            Enumeration keys = userConfig.propertyNames();
            while (keys.hasMoreElements()) {
                String key = (String) keys.nextElement();
                try {
                    // Key should already exist in the default config and be an integer.
                    String value = userConfig.getProperty(key);
                    Integer.parseInt(value);
                    if (properties.getProperty(key) != null) {
                        properties.setProperty(key, value);
                    }
                } catch (NumberFormatException e) {
                    // Not an integer. Only integers allowed
                }
            }
        } catch (IOException e) {
            // File does not exist yet.
        }
    }

    /**
     * Loads all predefined configuration.
     */
    public void loadDefaultConfig() {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream config = loader.getResourceAsStream(defaultFile);
            if (config != null) {
                properties.load(config);
                config.close();
            }
        } catch (IOException e) {
            // File does not exist yet.
        }
    }

    /**
     * Save the configuration into a user settings file.
     */
    public void saveConfig() {
        try {
            OutputStream config = new FileOutputStream(userFile);
            properties.store(config, null);
            config.close();
        } catch (IOException e) {
            // Unable to create file.
        }
    }

}

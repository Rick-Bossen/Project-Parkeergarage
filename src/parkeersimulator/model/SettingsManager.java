package parkeersimulator.model;

import java.io.*;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Class which represents all user editable settings.
 * Note. All used settings need to be of the type Integer.
 *
 * @version 18.01.2019
 */
public class SettingsManager {

    private final String defaultFile = "default.properties";
    private final String userFile = "user-config.properties";

    private Properties properties;

    public SettingsManager(){
        properties = new Properties();
        loadDefaultConfig();
        loadUserConfig();
        saveConfig();
    }

    /**
     * Loads all predefined configuration.
     */
    private void loadDefaultConfig()
    {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream config = loader.getResourceAsStream(defaultFile);
            if(config != null){
                properties.load(config);
                config.close();
            }
        } catch (IOException e) {
            // File does not exist yet.
        }
    }

    /**
     * Loads all user config.
     * Checks if the user config is present as default config and contains integer values.
     */
    private void loadUserConfig(){
        Properties userConfig = new Properties();
        try {
            InputStream config = new FileInputStream(userFile);
            userConfig.load(config);
            config.close();

            Enumeration keys = userConfig.propertyNames();
            while (keys.hasMoreElements()) {
                String key = (String)keys.nextElement();
                try{
                    // Key should already exist in the default config and be an integer.
                    String value = userConfig.getProperty(key);
                    Integer.parseInt(value);
                    if(properties.getProperty(key) != null){
                        properties.setProperty(key, value);
                    }
                }catch (NumberFormatException e){
                    // Not an integer. Only integers allowed
                }
            }
        } catch (IOException e) {
            // File does not exist yet.
        }
    }

    /**
     * Save the configuration into a user settings file.
     */
    private void saveConfig() {
        try {
            OutputStream config = new FileOutputStream(userFile);
            properties.store(config, null);
            config.close();
        } catch (IOException e) {
            // Unable to create file.
        }
    }

    /**
     * Get the integer value of a setting.
     *
     * @param key name of the setting.
     * @return value of the setting
     */
    public Integer getValue(String key){
        String value = properties.getProperty(key);
        if(value != null){
            try{
                return Integer.parseInt(value);
            }catch (NumberFormatException e){
                return null;
            }
        }
        return null;
    }


    /**
     * Update a setting. Only allowed to update existing settings.
     *
     * @param key Key of the setting
     * @param value Value of the configuration.
     * @return if the value was successfully saved.
     */
    public boolean setValue(String key, int value){
        if(properties.getProperty(key) != null){
            properties.setProperty(key, Integer.toString(value));
            return true;
        }
        return false;
    }

}

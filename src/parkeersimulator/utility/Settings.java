package parkeersimulator.utility;

import parkeersimulator.framework.Model;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

/**
 * Class which represents all user editable settings.
 * Note. All used settings need to be of the type Integer.
 *
 * @version 18.01.2019
 */
public class Settings extends Model {

    private final String defaultFile = "default.properties";
    private final String userFile = "user-config.properties";

    private Properties properties;
    private Properties temporaryProperties;

    private static Settings instance = new Settings();

    /**
     * Create a new settings manager whilst load default and user config.
     */
    private Settings(){
        properties = new Properties();
        temporaryProperties = new Properties();
        loadDefaultConfig();
        loadUserConfig();
        saveConfig();
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
     * Loads all edited user config.
     * Checks if the user config is present as default config and contains integer values.
     */
    public void loadTemporaryConfig(){
        System.out.println("IN");
        Enumeration names = temporaryProperties.propertyNames();
        while (names.hasMoreElements()) {
            String key = (String) names.nextElement();
            System.out.println(key + " = " + temporaryProperties.getProperty(key));
            properties.setProperty(key, temporaryProperties.getProperty(key));
        }
        temporaryProperties.clear();
    }

    /**
     * Loads all predefined configuration.
     */
    public void loadDefaultConfig() {
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
        updateViews();
    }

    /**
     * Return instance of the settings managers.
     * @return Settings
     */
    public static Settings getInstance(){
        return instance;
    }

    /**
     * Get the integer value of a setting.
     * If the key is not found return 0.
     *
     * @param key name of the setting.
     * @return value of the setting
     */
    public static int get(String key){
        String value = instance.properties.getProperty(key);
        if(value != null){
            try{
                return Integer.parseInt(value);
            }catch (NumberFormatException e){
                return 0;
            }
        }
        return 0;
    }


    /**
     * Update a setting. Only allowed to update existing settings.
     *
     * @param key Key of the setting
     * @param value Value of the configuration.
     * @return if the value was successfully saved.
     */
    public static boolean set(String key, int value){
        if(instance.properties.getProperty(key) != null){
            instance.properties.setProperty(key, Integer.toString(value));
            return true;
        }
        return false;
    }

    /**
     * Update a setting. Only allowed to update existing settings.
     *
     * @param key Key of the setting
     * @param value Value of the configuration.
     * @return if the value was successfully saved.
     */
    public static boolean setTemporary(String key, int value){
        instance.temporaryProperties.setProperty(key, Integer.toString(value));
        return true;
    }

}

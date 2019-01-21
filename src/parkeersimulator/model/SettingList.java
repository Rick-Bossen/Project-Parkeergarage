package parkeersimulator.model;

import parkeersimulator.framework.Model;

import java.util.ArrayList;

/**
 *  Index of all setting categories.
 */
public class SettingList extends Model {

    public ArrayList<SettingCategory> categories;

    public SettingList(){
        categories = new ArrayList<>();
    }

    /**
     * Will all categories with the information.
     */
    public void fillCategories(){
        SettingCategory generalCategory = new SettingCategory("General");
        generalCategory.addSetting("width", "Width");
        generalCategory.addSetting("height", "Height");
        categories.add(generalCategory);

        updateViews();
    }

    /**
     * Get all categories.
     * @return List of all categories.
     */
    public ArrayList<SettingCategory> getCategories(){
        return categories;
    }

}

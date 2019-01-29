package parkeersimulator.model.settings;

import parkeersimulator.enums.settings.SettingCategories;
import parkeersimulator.framework.Model;

import java.util.ArrayList;

/**
 * Index of all setting categories.
 *
 * @version 28.01.2019.
 */
public class SettingList extends Model {

    private ArrayList<SettingCategory> categories;

    public SettingList() {
        categories = new ArrayList<>();
    }

    /**
     * Fill all categories with the information.
     */
    public void fillCategories() {
        for(SettingCategories categoryType : SettingCategories.values()){
            categories.add(new SettingCategory(categoryType));
        }
        updateViews();
    }

    /**
     * Get all categories.
     *
     * @return List of all categories.
     */
    public ArrayList<SettingCategory> getCategories() {
        return categories;
    }

}

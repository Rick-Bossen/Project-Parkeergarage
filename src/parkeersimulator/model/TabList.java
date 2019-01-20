package parkeersimulator.model;

import parkeersimulator.framework.Model;
import parkeersimulator.framework.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TabList extends Model {

    private String activeTab;
    private HashMap<String, ArrayList<View>> list;

    public TabList(){
        list = new HashMap<>();
    }

    /**
     * Add menu item with one view.
     * @param name Name of the menu item
     * @param view View of the tab
     */
    public void addTabList(String name, View view){
        ArrayList<View> views = new ArrayList<>();
        views.add(view);
        addTabList(name, views);
    }

    /**
     * Add menu item with multiple views.
     * @param name Name of the menu item
     * @param views Views of the tab
     */
    public void addTabList(String name, ArrayList<View> views){
        list.put(name, views);
    }

    /**
     * Get a list of all menu items
     * @return list
     */
    public String[] getMenuItems(){
        String[] keys = new String[list.size()];
        int index = 0;
        for (String key : list.keySet()) {
            keys[index] = key;
            index++;
        }
        return keys;
    }

    /**
     * @return active tab
     */
    public String getActiveTab(){
        return activeTab;
    }

    public void setActiveTab(String activeTab) {
        if(list.get(activeTab) != null && (this.activeTab == null || !this.activeTab.equals(activeTab))) {
            this.activeTab = activeTab;
            updateViews();
            Iterator iterator = list.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry)iterator.next();
                for(View view : (ArrayList<View>)entry.getValue()){
                    view.setVisible(entry.getKey() == activeTab);
                }
            }

        }
    }
}

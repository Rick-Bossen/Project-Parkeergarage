package parkeersimulator.model;

import parkeersimulator.framework.Model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TabList extends Model {

    private String activeTab;
    private final HashMap<String, ArrayList<JComponent>> list;

    public TabList(){
        list = new HashMap<>();
    }

    /**
     * Add menu item with multiple views.
     * @param name Name of the menu item
     * @param views Views of the tab
     */
    public void addTabList(String name, ArrayList<JComponent> views){
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
            for (Map.Entry<String, ArrayList<JComponent>> stringArrayListEntry : list.entrySet()) {
                for (JComponent view : (ArrayList<JComponent>) ((Map.Entry) stringArrayListEntry).getValue()) {
                    view.setVisible(((Map.Entry) stringArrayListEntry).getKey() == activeTab);
                }
            }

        }
    }
}

package parkeersimulator.model;

import parkeersimulator.framework.Model;
import parkeersimulator.framework.View;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TabList extends Model {

    private String activeTab;
    private HashMap<String, ArrayList<JComponent>> list;

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
            Iterator iterator = list.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry)iterator.next();
                for(JComponent view : (ArrayList<JComponent>)entry.getValue()){
                    view.setVisible(entry.getKey() == activeTab);
                }
            }

        }
    }
}

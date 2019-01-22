package parkeersimulator.controller;

import parkeersimulator.framework.Controller;
import parkeersimulator.framework.View;
import parkeersimulator.model.TabList;
import parkeersimulator.view.SideBar;

public class Navigation extends Controller {

    private final TabList tabList;

    public Navigation(TabList tabList){
        this.tabList = tabList;
    }

    @Override
    protected void event(View view, int eventId) {
        if(view instanceof SideBar){
            tabList.setActiveTab(((SideBar)view).getMenuText(eventId - 1));
        }
    }

}

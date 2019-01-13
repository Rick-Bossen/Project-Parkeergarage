package parkeersimulator.main;
import parkeersimulator.view.*;

import javax.swing.*;
import java.awt.*;

public class Main {


    public void testLayout() {
        int numberOfFloors = 3;

        JFrame testFrame = new JFrame("Test");
        JPanel mainLayout = new JPanel();
        mainLayout.setLayout(new GridBagLayout());
        mainLayout.setPreferredSize(new Dimension(800, 500));
        testFrame.setContentPane(mainLayout);
        testFrame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - 800) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - 500) / 2);
        testFrame.pack();
        testFrame.setVisible(true);

        TopBar topBar = new TopBar(numberOfFloors);
        mainLayout.add(topBar, topBar.getConstraints());

        SideBar sideBar = new SideBar();
        mainLayout.add(sideBar, sideBar.getConstraints());

        for (int floor = 0; floor < numberOfFloors; floor++){
            CarParkFloor carParkFloor = new CarParkFloor(floor);
            mainLayout.add(carParkFloor, carParkFloor.getConstraints());
        }

        CarParkExtra carParkExtra = new CarParkExtra(numberOfFloors);
        mainLayout.add(carParkExtra, carParkExtra.getConstraints());
    }

}

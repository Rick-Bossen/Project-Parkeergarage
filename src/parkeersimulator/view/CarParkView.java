package parkeersimulator.view;

import parkeersimulator.framework.GridBagView;
import parkeersimulator.framework.Model;
import parkeersimulator.model.CarPark;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

/**
 * This class contains the whole car park view.
 */
public class CarParkView extends GridBagView {

    ArrayList<CarParkFloor> floors;

    public CarParkView() {
        super();
        setPosition(1, 1);
        setHorizontalPriority(1);
        setVerticalPriority(1);
        setLayout(new GridLayout(1, 3));
    }

    @Override
    protected void update(Model model) {
        if(model instanceof CarPark){
            CarPark carPark = (CarPark) model;
            if(floors == null || floors.size() != carPark.getNumberOfFloors()) {
                removeAll();
                floors = new ArrayList<>();
                for (int floor = 0; floor < carPark.getNumberOfFloors(); floor++) {
                    CarParkFloor carParkFloor = new CarParkFloor(carPark, floor);
                    // Fix for redrawing on resize.
                    carParkFloor.addComponentListener(new ComponentAdapter() {
                        public void componentResized(ComponentEvent evt) {
                            Component floor = (Component) evt.getSource();
                            ((CarParkFloor) floor).updateView();

                        }
                    });
                    add(carParkFloor);
                    floors.add(carParkFloor);
                }
            }else{
                for(CarParkFloor floor : floors){
                    floor.updateView();
                }
            }
        }
    }
}

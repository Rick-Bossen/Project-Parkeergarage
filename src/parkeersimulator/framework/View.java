package parkeersimulator.framework;

import javax.swing.*;

public abstract class View extends JPanel {

    protected Controller controller;

    /**
     * Attach controller to view
     * @param controller controller to use.
     */
    public void setController(Controller controller) {
        if (this.controller!=null) {
            throw new IllegalStateException("Controller already set.");
        }
        this.controller = controller;
    }

    /**
     * Notify controller of event, called by subclasses (concrete views)
     * @param eventId id of event
     */
    protected void sendEvent(int eventId) {
        if (controller == null) {
            throw new IllegalStateException("View does not have a controller.");
        }
        controller.event(this, eventId);
    }

    /**
     * Update handler
     * @param model Model to use in the update.
     */
    protected abstract void update(Model model);

}

package parkeersimulator.framework;

public abstract class Controller {

    /**
     * Eventhandler of controller
     * @param view View where event comes from
     * @param eventId id of event
     * @return true if event is handled
     */
    protected abstract void event(View view, int eventId);

}

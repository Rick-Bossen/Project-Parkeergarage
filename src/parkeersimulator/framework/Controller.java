package parkeersimulator.framework;

public abstract class Controller {

    /**
     * Eventhandler of controller
     *
     * @param view    View where event comes from
     * @param eventId id of event
     */
    protected abstract void event(View view, int eventId);

}

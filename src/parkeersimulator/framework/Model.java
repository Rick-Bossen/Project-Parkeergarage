package parkeersimulator.framework;

import java.util.ArrayList;

public abstract class Model {

    private ArrayList<View> views = new ArrayList<>();

    /**
     * Add views to model to be notified by model.
     *
     * @param view View to be added to list of models that will be notified.
     */
    public void addView(View view) {
        views.add(view);
    }

    /**
     * Update views. Called by subclasses (concrete models)
     */
    protected void updateViews() {
        for (View view : views) {
            view.update(this);
        }
    }

}

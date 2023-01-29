package com.nerpage.oca.pac.presenters;

import com.nerpage.oca.R;
import com.nerpage.oca.pac.Presenter;

public interface ExamplePresenter extends Presenter {
    /**
     * Returns the resource id of the layout this presenter describes
     * @return id
     */
    static int getDescribedLayoutId() {
        return R.layout.fragment_example;
    }

    /**
     * Changes the title text to a new value.
     * @param newTitle new {@code String} value for title
     */
    void setTitle(String newTitle);

    /**
     * Fetches text currently present in the input field.
     * @return the {@code String} text
     */
    String getInputFieldText();

    /**
     * Sets a new callback for notifying when the confirm button has been pressed.
     * @param newCallback new callback
     */
    void setOnConfirmButtonPressedCallback(Callback newCallback);

    /**
     * {@code POI} specifically designed to reference {@code ExamplePresenter}'s layout key views
     */
    enum POI implements Presenter.PointOfInterest {
        ROOT(R.id.root_example),
        TITLE(R.id.f_example_title),
        CONFIRM_BUTTON(R.id.f_example_confirmButton),
        INPUT_FIELD(R.id.f_example_inputText);

        private final int id;

        @Override
        public int getId() {
            return id;
        }

        POI(int id) {
            this.id = id;
        }
    }
}

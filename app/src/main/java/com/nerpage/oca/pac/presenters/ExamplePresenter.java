package com.nerpage.oca.pac.presenters;

import com.nerpage.oca.R;
import com.nerpage.oca.pac.Presenter;

public interface ExamplePresenter extends Presenter {
    static int getDescribedLayoutId() {
        return R.layout.fragment_example;
    }

    void setTitle(String newTitle);

    String getInputFieldText();

    void setOnConfirmButtonPressedCallback(Callback newCallback);

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

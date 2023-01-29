package com.nerpage.oca.fragments.presenters;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nerpage.oca.R;
import com.nerpage.oca.pac.AbstractPresenter;
import com.nerpage.oca.pac.Presenter;

/**
 * @see com.nerpage.oca.fragments.ExampleFragment
 */
public class ExamplePresenter extends AbstractPresenter {

    public ExamplePresenter(View root){
        super(root);
    }



    public enum POI implements Presenter.POI {
        ROOT(R.id.root_example),
        TITLE(R.id.f_example_title),
        CONFIRM_BUTTON(R.id.f_example_confirmButton),
        INPUT_FIELD(R.id.f_example_inputText)
        ;

        private final int id;

        @Override
        public int getId() {
            return id;
        }

        POI(int id) {
            this.id = id;
        }
    }



    public static int getDescribedLayoutId() {
        return R.layout.fragment_example;
    }



    public void setTitle(String newTitle){
        POI.TITLE
                .of(getRoot())
                .ifPresent(
                    view -> ((TextView)view).setText(newTitle)
                );
    }

    public String getInputFieldText(){
        return POI.INPUT_FIELD
                .of(getRoot())
                .map(
                    view -> ((EditText)view).getText().toString()
                ).orElse("");
    }

    public void setOnConfirmButtonPressedCallback(Callback newCallback){
        onConfirmButtonPressCallback = newCallback;
        POI.CONFIRM_BUTTON
                .of(getRoot())
                .ifPresent(
                        view -> ((Button)view).setOnClickListener(v->onConfirmButtonPressCallback.call())
                );
    }


    private Callback onConfirmButtonPressCallback;

}

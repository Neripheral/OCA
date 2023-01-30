package com.nerpage.oca.pac.presenters;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.nerpage.oca.pac.AbstractPresenter;
import com.nerpage.oca.pac.controllers.ExampleFragment;

/**
 * @see ExampleFragment
 */
public final class DefaultExamplePresenter extends AbstractPresenter implements ExamplePresenter {

    public DefaultExamplePresenter(View root){
        super(root);
    }


    @Override
    public void setTitle(String newTitle){
        POI.TITLE
                .of(getRoot())
                .ifPresent(
                    view -> ((TextView)view).setText(newTitle)
                );
    }

    @Override
    public String getInputFieldText(){
        return POI.INPUT_FIELD
                .of(getRoot())
                .map(
                    view -> ((EditText)view).getText().toString()
                ).orElse("");
    }

    @Override
    public void setOnConfirmButtonPressedCallback(Callback newCallback){
        onConfirmButtonPressCallback = newCallback;
        POI.CONFIRM_BUTTON
                .of(getRoot())
                .ifPresent(
                        view -> view.setOnClickListener(v->onConfirmButtonPressCallback.call())
                );
    }


    private Callback onConfirmButtonPressCallback;

    public static class DefaultExamplePresenterFactory implements Factory<ExamplePresenter> {
        @Override
        public ExamplePresenter createFor(View root) {
            return new DefaultExamplePresenter(root);
        }
    }

}

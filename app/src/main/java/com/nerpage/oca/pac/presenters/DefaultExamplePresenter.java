package com.nerpage.oca.pac.presenters;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.nerpage.oca.pac.AbstractPresenter;

/**
 * @see com.nerpage.oca.fragments.ExampleFragment
 */
public class DefaultExamplePresenter extends AbstractPresenter implements ExamplePresenter {

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

}

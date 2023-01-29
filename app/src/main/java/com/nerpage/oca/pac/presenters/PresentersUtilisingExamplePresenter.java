package com.nerpage.oca.pac.presenters;

import android.view.View;

import com.nerpage.oca.pac.AbstractPresenter;
import com.nerpage.oca.util.Presenters;

/**
 * @see com.nerpage.oca.fragments.ExampleFragment
 */
public class PresentersUtilisingExamplePresenter extends AbstractPresenter implements ExamplePresenter {

    public PresentersUtilisingExamplePresenter(View root){
        super(root);
    }


    @Override
    public void setTitle(String newTitle){
        Presenters.setText(POI.TITLE, getRoot(), newTitle);
    }

    @Override
    public String getInputFieldText(){
        return Presenters.getText(POI.INPUT_FIELD, getRoot());
    }

    @Override
    public void setOnConfirmButtonPressedCallback(Callback newCallback){
        onConfirmButtonPressCallback = newCallback;
        View view = Presenters.getView(POI.CONFIRM_BUTTON, getRoot());
        view.setOnClickListener(v->onConfirmButtonPressCallback.call());
    }


    private Callback onConfirmButtonPressCallback;

    public static class Factory implements AbstractFactory<ExamplePresenter>{
        @Override
        public ExamplePresenter createFor(View root) {
            return new PresentersUtilisingExamplePresenter(root);
        }
    }

}

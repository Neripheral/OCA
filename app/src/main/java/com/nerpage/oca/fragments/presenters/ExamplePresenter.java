package com.nerpage.oca.fragments.presenters;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nerpage.oca.R;
import com.nerpage.oca.fragments.PACFragment;
import com.nerpage.oca.fragments.Presenter;

/**
 * @see com.nerpage.oca.fragments.ExampleFragment
 */
public class ExamplePresenter extends Presenter {

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
        ((TextView)getRoot().findViewById(POI.TITLE.getId())).setText(newTitle);
    }

    public String getInputFieldText(){
        return ((EditText)getRoot().findViewById(POI.INPUT_FIELD.getId())).getText().toString();
    }

    public void setOnConfirmButtonPressedCallback(Callback newCallback){
        onConfirmButtonPressCallback = newCallback;
        ((Button)getRoot().findViewById(POI.CONFIRM_BUTTON.getId())).setOnClickListener(view -> onConfirmButtonPressCallback.call() );
    }


    private Callback onConfirmButtonPressCallback;

}

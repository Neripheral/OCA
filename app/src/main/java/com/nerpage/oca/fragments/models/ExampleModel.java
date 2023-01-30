package com.nerpage.oca.fragments.models;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.nerpage.oca.fragments.Model;

/**
 * @see com.nerpage.oca.fragments.ExampleFragment
 */
public class ExampleModel extends Model {
    public static final String INITIAL_TITLE = "Example Fragment";

    private final MutableLiveData<String> title = new MutableLiveData<>(INITIAL_TITLE);

    public String getTitle(){
        return title.getValue();
    }

    public void setTitle(String title){
        this.title.setValue(title);
    }

    public void setOnTitleChanged(@NonNull LifecycleOwner owner, @NonNull Observer<? super String> observer){
        title.observe(owner, observer);
    }
}

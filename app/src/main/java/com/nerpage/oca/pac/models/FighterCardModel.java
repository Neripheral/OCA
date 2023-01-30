package com.nerpage.oca.pac.models;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.nerpage.oca.pac.Model;

public class FighterCardModel extends Model {
    private MutableLiveData<String> title;
    public String getTitle() {
        return title.getValue();
    }
    public void setTitle(String title) {
        if(!getTitle().equals(title))
            this.title.setValue(title);
    }
    public void setOnTitleChanged(@NonNull LifecycleOwner owner, @NonNull Observer<? super String> observer){
        title.observe(owner, observer);
    }


    private MutableLiveData<String> currentBlood;
    public String getCurrentBlood() {
        return currentBlood.getValue();
    }
    public void setCurrentBlood(String currentBlood) {
        if(!getCurrentBlood().equals(currentBlood))
            this.currentBlood.setValue(currentBlood);
    }
    public void setOnCurrentBloodChanged(@NonNull LifecycleOwner owner, @NonNull Observer<? super String> observer){
        currentBlood.observe(owner, observer);
    }


    private MutableLiveData<String> maxBlood;
    public String getMaxBlood() {
        return maxBlood.getValue();
    }
    public void setMaxBlood(String maxBlood) {
        if(!getMaxBlood().equals(maxBlood))
            this.maxBlood.setValue(maxBlood);
    }
    public void setOnMaxBloodChanged(@NonNull LifecycleOwner owner, @NonNull Observer<? super String> observer){
        maxBlood.observe(owner, observer);
    }

}
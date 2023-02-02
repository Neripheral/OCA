package com.nerpage.ocaproc;

/**
 * This file has been automatically generated.
 */
public class ClassBuilder {
    private String preamble(){
        return "/**\r\n" +
                " * This file has been automatically generated.\r\n" +
                " */\r\n" +
                "\r\n" +
                "package com.nerpage.oca.pac.models;\r\n" +
                "\r\n";
    }

    private String imports(){
        return  "import androidx.annotation.NonNull;\r\n" +
                "import androidx.lifecycle.LifecycleOwner;\r\n" +
                "import androidx.lifecycle.MutableLiveData;\r\n" +
                "import androidx.lifecycle.Observer;\r\n" +
                "\r\n" +
                "import com.nerpage.oca.pac.Model;\r\n";
    }

    private String classOpen(String className){
        return "public class " + className + "Model extends Model {\r\n";
    }

    private String attribute(String type, String name, String defaultValue){
        return String.format(
                "private final MutableLiveData<%1$s> %2$s = new MutableLiveData<>(%3$s);\r\n" +
                "    public %1$s get%4$s() {\r\n" +
                "        return %2$s.getValue();\r\n" +
                "    }\r\n" +
                "    public void set%4$s(%1$s %2$s) {\r\n" +
                "        this.%2$s.setValue(%2$s);\r\n" +
                "    }\r\n" +
                "    public void setOn%4$sChanged(@NonNull LifecycleOwner owner, @NonNull Observer<? super %1$s> observer){\r\n" +
                "        %2$s.observe(owner, observer);\r\n" +
                "    }\r\n" +
                "\r\n",
            type,
            name,
            defaultValue,
            Character.toUpperCase(type.charAt(0)) + type.substring(1)
        );
    }

    private String classClose(){
        return "}";
    }
}

/*
package com.nerpage.oca.pac.models;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.nerpage.oca.pac.Model;

public class FighterCardModel extends Model {
    private final MutableLiveData<String> title = new MutableLiveData<>("enemy_name");
    public String getTitle() {
        return title.getValue();
    }
    public void setTitle(String title) {
        this.title.setValue(title);
    }
    public void setOnTitleChanged(@NonNull LifecycleOwner owner, @NonNull Observer<? super String> observer){
        title.observe(owner, observer);
    }


    private final MutableLiveData<String> currentBlood = new MutableLiveData<>("???");
    public String getCurrentBlood() {
        return currentBlood.getValue();
    }
    public void setCurrentBlood(String currentBlood) {
        this.currentBlood.setValue(currentBlood);
    }
    public void setOnCurrentBloodChanged(@NonNull LifecycleOwner owner, @NonNull Observer<? super String> observer){
        currentBlood.observe(owner, observer);
    }


    private final MutableLiveData<String> maxBlood = new MutableLiveData<>("???");
    public String getMaxBlood() {
        return maxBlood.getValue();
    }
    public void setMaxBlood(String maxBlood) {
        this.maxBlood.setValue(maxBlood);
    }
    public void setOnMaxBloodChanged(@NonNull LifecycleOwner owner, @NonNull Observer<? super String> observer){
        maxBlood.observe(owner, observer);
    }

}
 */
package com.nerpage.ocaproc;

import java.util.List;

/**
 * This file has been automatically generated.
 */
public class ClassBuilder {
    public static String build(String className, List<String> attributes){
        StringBuilder stringBuilder = new StringBuilder()
                .append(preamble())
                .append(imports())
                .append(classOpen(className));

        for(String a : attributes)
            stringBuilder.append(attribute(a));

        stringBuilder.append(classClose());
        return stringBuilder.toString();
    }

    private static String preamble(){
        return "/**\r\n" +
                " * This file has been automatically generated.\r\n" +
                " */\r\n" +
                "\r\n" +
                "package com.nerpage.oca.pac.models;\r\n" +
                "\r\n";
    }

    private static String imports(){
        return  "import androidx.annotation.NonNull;\r\n" +
                "import androidx.lifecycle.LifecycleOwner;\r\n" +
                "import androidx.lifecycle.MutableLiveData;\r\n" +
                "import androidx.lifecycle.Observer;\r\n" +
                "\r\n" +
                "import com.nerpage.oca.pac.Model;\r\n";
    }

    private static String classOpen(String className){
        return "public class " + className + " extends Model {\r\n";
    }

    private static String attribute(String attribute){
        String[] tmp = attribute.split(" ");
        if(tmp.length != 3)
            return "";

        String type = tmp[0];
        String name = tmp[1];
        String defaultValue = tmp[2];

        if(type.equals("Boolean"))
            return attributeAsText(type, name, defaultValue, "is");
        else
            return attributeAsText(type, name, defaultValue, "get");
    }

    private static String attributeAsText(String type, String name, String defaultValue, String getter){
        return String.format(
                "\tprivate final MutableLiveData<%1$s> %2$s = new MutableLiveData<>(%3$s);\r\n" +
                "\tpublic %1$s %5$s%4$s() {\r\n" +
                "\t    return %2$s.getValue();\r\n" +
                "\t}\r\n" +
                "\tpublic void set%4$s(%1$s %2$s) {\r\n" +
                "\t    this.%2$s.setValue(%2$s);\r\n" +
                "\t}\r\n" +
                "\tpublic void setOn%4$sChanged(@NonNull LifecycleOwner owner, @NonNull Observer<? super %1$s> observer){\r\n" +
                "\t    %2$s.observe(owner, observer);\r\n" +
                "\t}\r\n" +
                "\r\n",
            type,
            name,
            defaultValue,
            Character.toUpperCase(name.charAt(0)) + name.substring(1),
            getter
        );
    }

    private static String classClose(){
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
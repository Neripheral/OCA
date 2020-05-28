package com.nerpage.oca.classes;

import android.content.Context;
import android.util.Log;

import com.nerpage.oca.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ItemDatabase {
    private static Map<String, Class<Item>> itemList;

    public static Map<String, Class<Item>> getItemList(){
        return itemList;
    }

    public Class<Item> getItemById(String id){
        if(getItemList().containsKey(id))
            return getItemList().get(id);
        return null;
    }

    private static int getItemlistResourceId(){
        return R.raw.item_list;
    }

    private static List<String> findAllPossibleItemNames(Context context){
        List<String> namesList = new ArrayList<>();

        InputStream stream = context.getResources().openRawResource(getItemlistResourceId());
        InputStreamReader isReader = new InputStreamReader(stream);
        BufferedReader reader = new BufferedReader(isReader);
        StringBuffer sb = new StringBuffer();
        String str;
        try {
            while((str = reader.readLine())!= null){
                namesList.add("com.nerpage.oca.itemsdb." + str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return namesList;
    }

    private static List<Class<Item>> findAllPossibleItems(Context context) {
        List<Class<Item>> itemList = new ArrayList<>();
        List<String> itemNames = findAllPossibleItemNames(context);
        for(String itemName : itemNames){
            try {
                itemList.add((Class<Item>)Class.forName(itemName));
            } catch (ClassNotFoundException e) {
                Log.e("exception", "Class not found: " + itemName);
            }
        }
        return itemList;
    }

    private static Map<String, Class<Item>> mapItemsToIds(List<Class<Item>> itemClasses){
        Map<String, Class<Item>> map = new HashMap<>();
        for(Class<Item> itemClass : itemClasses){
            try{
                Item item = itemClass.newInstance();
                String id = item.getId();
                map.put(id, itemClass);
            }catch(Throwable e){
                Log.e("exception", e.getMessage());
            }
        }
        return map;
    }

    public static void init(Context context){
        itemList = mapItemsToIds(findAllPossibleItems(context));

    }
}

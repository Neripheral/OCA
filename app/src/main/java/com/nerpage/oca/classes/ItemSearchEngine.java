package com.nerpage.oca.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ItemSearchEngine {
    private static List<Item.Groupable> castToGroupable(List<Item> list){
        return (List<Item.Groupable>)(List<?>) list;
    }

    public static List<Item> find(List<Item> list, Predicate<Item> checker){
        List<Item> foundItems = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            Item currentItem = list.get(i);
            if(checker.test(currentItem)){
                foundItems.add(currentItem);
            }
        }
        return foundItems;
    }

    public static List<Item.Groupable> findEagerToAdd(List<Item> list, Item item){
        return castToGroupable(ItemSearchEngine.find(list, (Item currentItem) -> {
            if(currentItem instanceof Item.Groupable){
                return ((Item.Groupable)currentItem).eagerToAdd(item);
            }
            return false;
        }));
    }

    public static List<Item.Groupable> findAbleToAdd(List<Item> list, Item item){
        return castToGroupable(ItemSearchEngine.find(list, (Item currentItem) -> {
            if(currentItem instanceof Item.Groupable){
                return ((Item.Groupable)currentItem).canAdd(item);
            }
            return false;
        }));
    }

    public static List<Item.Groupable> findEagerToSubtract(List<Item> list, Item item){
        return castToGroupable(ItemSearchEngine.find(list, (Item currentItem) -> {
            if(currentItem instanceof Item.Groupable){
                return ((Item.Groupable)currentItem).eagerToSubtract(item);
            }
            return false;
        }));
    }

    public static List<Item.Groupable> findAbleToSubtract(List<Item> list, Item item){
        return castToGroupable(ItemSearchEngine.find(list, (Item currentItem) -> {
            if(currentItem instanceof Item.Groupable){
                return ((Item.Groupable)currentItem).canSubtract(item);
            }
            return false;
        }));
    }

    public static List<Item.Groupable> findById(List<Item> list, String id){
        return castToGroupable(ItemSearchEngine.find(list, (Item currentItem) -> {
            return currentItem.getId().equals(id);
        }));
    }
}

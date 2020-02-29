package com.example.oca.classes;

import android.content.Context;
import android.util.Log;

import com.example.oca.models.AttributeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PlayerCharacter {
    public final boolean GENDER_MALE = true;
    public final boolean GENDER_FEMALE = false;
    public final String DEFAULT_FILENAME = "savedCharacter.opc";
    public final int ATTRIBUTES_AMOUNT = 12;
    public final int ATTRIBUTES_POINTS = 20;
    public final int MAIN_ATTRIBUTE = -1;
    public final int MISSING_ATTRIBUTE = -2;
    public final int ATTRIBUTE_LOWER_LIMIT = 1;
    public final int ATTRIBUTE_UPPER_LIMIT = 9;
    public final int VITALITY = 0;
    public final int REFLEX = 1;
    public final int ORGANISM = 2;
    public final int CONDITION = 3;
    public final int STRENGTH = 4;
    public final int AGILITY = 5;
    public final int PSYCHE = 6;
    public final int DISCIPLINE = 7;
    public final int INTELLIGENCE = 8;
    public final int CHARISMA = 9;
    public final int EMPATHY = 10;
    public final int MANIPULATION = 11;


    private String name;
    private boolean gender;
    private int age;
    private String job;
    private int[] attributes = new int[ATTRIBUTES_AMOUNT];
    private boolean attributesCommitted = false;
    private Map<String, Skill> skills = new TreeMap<>();


    public PlayerCharacter(){}


    public String getName() {
        return name;
    }

    public PlayerCharacter setName(String name) {
        this.name = name;
        return this;
    }

    public boolean getGender() {
        return gender;
    }

    public PlayerCharacter setGender(boolean gender) {
        this.gender = gender;
        return this;
    }

    public int getAge() {
        return age;
    }

    public PlayerCharacter setAge(int age) {
        this.age = age;
        return this;
    }

    public String getJob() {
        return job;
    }

    public PlayerCharacter setJob(String job) {
        this.job = job;
        return this;
    }

    public int getAttribute(int pos){
        return attributes[pos];
    }

    public int getParentAttribute(int attribute){
        switch(attribute){
            case VITALITY:
            case CONDITION:
            case PSYCHE:
            case CHARISMA:
                return MAIN_ATTRIBUTE;

            case REFLEX:
            case ORGANISM:
                return VITALITY;

            case STRENGTH:
            case AGILITY:
                return CONDITION;

            case DISCIPLINE:
            case INTELLIGENCE:
                return PSYCHE;

            case EMPATHY:
            case MANIPULATION:
                return CHARISMA;
        }
        return MISSING_ATTRIBUTE;
    }

    public PlayerCharacter setAttribute(int pos, int number){
        if(number < ATTRIBUTE_LOWER_LIMIT)
            number = ATTRIBUTE_LOWER_LIMIT;
        if(number > ATTRIBUTE_UPPER_LIMIT)
            number = ATTRIBUTE_UPPER_LIMIT;
        this.attributes[pos] = number;
        return this;
    }

    public int getBalance(int attribute){
        int parent = getParentAttribute(attribute);
        parent = (parent == MAIN_ATTRIBUTE ? attribute : parent);
        int childSum = getAttribute(parent+1) + getAttribute(parent+2);
        int difference = 2 * getAttribute(parent) - childSum;
        return difference;
    }

    public int countCurrentMainAttributesAmount() {
        int netPoints = 0;
        for(int i = 0; i < attributes.length ; i++){
            if(getParentAttribute(i) == MAIN_ATTRIBUTE)
                netPoints += attributes[i];
        }
        return netPoints;
    }

    public boolean areAttributesCorrect(){
        if(countCurrentMainAttributesAmount() != ATTRIBUTES_POINTS)
            return false;
        for(int i = 0; i < ATTRIBUTES_AMOUNT ; i++){
            if(getParentAttribute(i) != MAIN_ATTRIBUTE && getBalance(i) != 0)
                return false;
        }
        return true;
    }

    public boolean areAttributesCommitted() {
        return attributesCommitted;
    }

    public PlayerCharacter setAttributesCommitted(boolean attributesCommitted) {
        this.attributesCommitted = attributesCommitted;
        return this;
    }

    public Map<String, Skill> getSkills(){
        return this.skills;
    }

    public Map<String, Skill> setSkills(Map<String, Skill> newMap){
        Map<String, Skill> old = this.getSkills();
        this.skills = newMap;
        return old;
    }

    public Skill getSkill(String id){
        return this.skills.get(id);
    }

    public Skill setSkill(String id, Skill skill){
        return this.skills.put(id, skill);
    }



    public String toJSON(){
        JSONObject in = new JSONObject();
        try{
            // put basic information to json object
            in.put("name", ""+this.getName());
            in.put("gender", this.getGender());
            in.put("age", this.getAge());
            in.put("job", ""+this.getJob());

            // put all of the attributes into the json object
            for(int i = 0; i < ATTRIBUTES_AMOUNT; i++)
                in.put("attribute"+i, getAttribute(i));

            // put all of the skills into the json object
            JSONArray skillsJSONArray = new JSONArray();
            for(Map.Entry<String, Skill> entry : skills.entrySet()){
                JSONObject skillInJSON = new JSONObject();
                skillInJSON.put("id", entry.getKey());
                skillInJSON.put("counter", entry.getValue().getCounter());
                skillsJSONArray.put(skillInJSON);
            }
            in.put("skills", skillsJSONArray);

            return in.toString();
        }catch(JSONException e){
            Log.e("Exception", "Parsing PlayerCharacter object to JSON failed: " + e.toString());
            return "";
        }
    }

    public PlayerCharacter initFromJSON(String sJson){
        try{
            // create new json object from string
            JSONObject obj = new JSONObject(sJson);

            // get all basic info data from this string
            this.setName(obj.getString("name"));
            this.setGender(obj.getBoolean("gender"));
            this.setAge(obj.getInt("age"));
            this.setJob(obj.getString("job"));

            // get all attributes from json string
            for(int i = 0; i < ATTRIBUTES_AMOUNT; i++)
                this.setAttribute(i, obj.getInt("attribute"+i));

            // get all skills from json string
            this.skills.clear();
            JSONArray skillsObj = obj.getJSONArray("skills");
            for(int i = 0; i < skillsObj.length(); i++){
                JSONObject skillObj = skillsObj.getJSONObject(i);
                String id = skillObj.getString("id");
                int counter = skillObj.getInt("counter");
                this.setSkill(id, new Skill(id, counter));
            }
            return this;
        }catch(JSONException e){
            Log.e("Exception", "JSON to PlayerCharacter parsing failed: " + e.toString());
            return this;
        }
    }

    public PlayerCharacter saveToFile(Context context){
        try{
            OutputStreamWriter writer = new OutputStreamWriter(context.openFileOutput(this.DEFAULT_FILENAME, Context.MODE_PRIVATE));
            writer.write(this.toJSON());
            writer.close();
            return this;
        }catch(IOException e){
            Log.e("Exception", "File write failed: " + e.toString());
            return this;
        }
    }

    public PlayerCharacter initFromFile(Context context){
        String json = "";
        try{
            InputStream inputStream = context.openFileInput(this.DEFAULT_FILENAME);
            if(inputStream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while((receiveString = bufferedReader.readLine()) != null){
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                json = stringBuilder.toString();
            }
        }catch(FileNotFoundException e){
            Log.e("Exception", "File not found: " + e.toString());
            return this;
        }catch(IOException e){
            Log.e("Exception", "File reading failed: " + e.toString());
            return this;
        }
        initFromJSON(json);
        return this;
    }
}

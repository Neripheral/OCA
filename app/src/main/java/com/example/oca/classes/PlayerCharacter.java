package com.example.oca.classes;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class PlayerCharacter {
    public final boolean GENDER_MALE = true;
    public final boolean GENDER_FEMALE = false;
    public final String DEFAULT_FILENAME = "savedCharacter.opc";
    public final int ATTRIBUTES_AMOUNT = 12;
    public final int VITALITY = 0;
    public final int REFLEX = 1;
    public final int ORGANISM = 2;
    public final int CONDITION = 3;
    public final int STRENGTH = 4;
    public final int AGILITY = 5;
    public final int PSYCHE = 6;
    public final int SCIENCE = 7;
    public final int INTELLIGENCE = 8;
    public final int CHARISMA = 9;
    public final int EMPATHY = 10;
    public final int MANIPULATION = 11;


    private String name;
    private boolean gender;
    private int age;
    private String job;
    private int[] attributes = new int[ATTRIBUTES_AMOUNT];
    private List<Skill> skills = new ArrayList<>();

    public class Skill{
        private String title;
        private int counter;

        public String getTitle() {
            return title;
        }

        public Skill setTitle(String title) {
            this.title = title;
            return this;
        }

        public int getCounter() {
            return counter;
        }

        public Skill setCounter(int counter) {
            this.counter = counter;
            return this;
        }

        public Skill(String title, int counter){
            this.setTitle(title);
            this.setCounter(counter);
        }
    }


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

    public PlayerCharacter setAttribute(int pos, int number){
        this.attributes[pos] = number;
        return this;
    }

    public List<Skill> getSkills(){
        return this.skills;
    }

    public Skill getSkill(int pos){ return this.skills.get(pos); }

    public PlayerCharacter setSkill(int pos, Skill skill){ this.skills.set(pos, skill); return this; }

    public PlayerCharacter addSkill(Skill skill){
        this.skills.add(skill);
        return this;
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
            for(int i = 0; i < this.skills.size(); i++){
                JSONObject skillInJSON = new JSONObject();
                skillInJSON.put("title", this.skills.get(i).title);
                skillInJSON.put("counter", this.skills.get(i).counter);
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
                String title = skillObj.getString("title");
                int counter = skillObj.getInt("counter");
                this.addSkill(new Skill(title, counter));
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

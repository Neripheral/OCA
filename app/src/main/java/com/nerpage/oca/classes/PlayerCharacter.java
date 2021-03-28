package com.nerpage.oca.classes;

import android.content.Context;
import android.util.Log;

import com.nerpage.oca.R;
import com.nerpage.oca.entities.Human;
import com.nerpage.oca.interfaces.Inventory;
import com.nerpage.oca.itemsdb.SmallSatchel;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PlayerCharacter extends Human {
    public final boolean GENDER_MALE = true;
    public final boolean GENDER_FEMALE = false;
    public final String DEFAULT_FILENAME = "savedCharacter.opc";
    public static final int ATTRIBUTES_AMOUNT = 12;
    public static final int ATTRIBUTES_POINTS = 20;
    public static final int MAIN_ATTRIBUTE = -1;
    public static final int MISSING_ATTRIBUTE = -2;
    public static final int ATTRIBUTE_LOWER_LIMIT = 1;
    public static final int ATTRIBUTE_UPPER_LIMIT = 9;
    public static final int VITALITY = 0;
    public static final int REFLEX = 1;
    public static final int ORGANISM = 2;
    public static final int CONDITION = 3;
    public static final int STRENGTH = 4;
    public static final int AGILITY = 5;
    public static final int PSYCHE = 6;
    public static final int DISCIPLINE = 7;
    public static final int INTELLIGENCE = 8;
    public static final int CHARISMA = 9;
    public static final int EMPATHY = 10;
    public static final int MANIPULATION = 11;
    public static final int MIN_SKILLLEVEL = 1;
    public static final int STARTING_SKILLPOINTS = 40;

    private String name = "";
    private boolean gender = GENDER_MALE;
    private int age = 30;
    private String job = "";
    private int[] attributes = new int[ATTRIBUTES_AMOUNT];
    private boolean attributesCommitted = false;
    private Map<String, Skill> skills = new TreeMap<>();
    private Item heldItem;

    public Item getHeldItem() {
        return heldItem;
    }

    public PlayerCharacter setHeldItem(Item heldItem) {
        this.heldItem = heldItem;
        return this;
    }

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

    public int getAttributeImageId(int attribute){
        switch(attribute){
            case VITALITY:
                return R.drawable.attribute_vitality;
            case REFLEX:
                return R.drawable.attribute_reflex;
            case ORGANISM:
                return R.drawable.attribute_organism;
            case CONDITION:
                return R.drawable.attribute_condition;
            case STRENGTH:
                return R.drawable.attribute_strength;
            case AGILITY:
                return R.drawable.attribute_agility;
            case PSYCHE:
                return R.drawable.attribute_psyche;
            case DISCIPLINE:
                return R.drawable.attribute_discipline;
            case INTELLIGENCE:
                return R.drawable.attribute_intelligence;
            case CHARISMA:
                return R.drawable.attribute_charisma;
            case EMPATHY:
                return R.drawable.attribute_empathy;
            case MANIPULATION:
                return R.drawable.attribute_manipulation;
        }
        return 0;
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


    public int getAttributesBalance(int attribute){
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
            if(getParentAttribute(i) != MAIN_ATTRIBUTE && getAttributesBalance(i) != 0)
                return false;
        }
        return true;
    }

    public String[] whyCannotCommitAttributes(Context context){
        List<String> reasons = new ArrayList<>();

        if(areAttributesCommitted())
            reasons.add(context.getString(R.string.reason_attributes_are_already_committed));

        int points = ATTRIBUTES_POINTS - countCurrentMainAttributesAmount();
        if(points > 0)
            reasons.add(context.getString(R.string.reason_attributes_points_remaining));
        if(points < 0)
            reasons.add(context.getString(R.string.reason_attributes_spent_too_many_points));

        int[] mainAttributes = {VITALITY, CONDITION, PSYCHE, CHARISMA};
        int[] mainAttributesName = {R.string.attribute_vitality, R.string.attribute_condition, R.string.attribute_psyche, R.string.attribute_charisma};
        for(int i = 0; i < mainAttributes.length; i++)
            if(getAttributesBalance(mainAttributes[i]) != 0)
                reasons.add(context.getString(R.string.reason_attribute_not_balanced, context.getString(mainAttributesName[i])));

        String[] toReturn = new String[reasons.size()];
        toReturn = reasons.toArray(toReturn);
        return toReturn;
    }

    public boolean areAttributesCommitted() {
        return attributesCommitted;
    }

    public PlayerCharacter setAttributesCommitted(boolean attributesCommitted) {
        this.attributesCommitted = attributesCommitted;
        return this;
    }

    public static Map<String, Skill> getPossibleSkills(){
        List<Skill> skills = new ArrayList<>();

        skills.add(new Skill("equestrianism", REFLEX));
        skills.add(new Skill("pickpocketing", REFLEX));
        skills.add(new Skill("eavesdropping", REFLEX));
        skills.add(new Skill("perceptivity", REFLEX));
        skills.add(new Skill("lockpicking", REFLEX));
        skills.add(new Skill("tracking", REFLEX));
        skills.add(new Skill("drinkHolding", ORGANISM));
        skills.add(new Skill("swimming", CONDITION));
        skills.add(new Skill("throwing", CONDITION));
        skills.add(new Skill("jumping", CONDITION));
        skills.add(new Skill("climbing", AGILITY));
        skills.add(new Skill("sneaking", AGILITY));
        skills.add(new Skill("painResistance", PSYCHE));
        skills.add(new Skill("goreTolerance", PSYCHE));
        skills.add(new Skill("languages", INTELLIGENCE));
        skills.add(new Skill("medicine", INTELLIGENCE));
        skills.add(new Skill("nature", INTELLIGENCE));
        skills.add(new Skill("navigation", INTELLIGENCE));
        skills.add(new Skill("politics", INTELLIGENCE));
        skills.add(new Skill("appraising", INTELLIGENCE));
        skills.add(new Skill("survival", INTELLIGENCE));
        skills.add(new Skill("physics", INTELLIGENCE));
        skills.add(new Skill("accounting", INTELLIGENCE));
        skills.add(new Skill("camouflage", INTELLIGENCE));
        skills.add(new Skill("mathematics", INTELLIGENCE));
        skills.add(new Skill("commanding", CHARISMA));
        skills.add(new Skill("flirting", CHARISMA));
        skills.add(new Skill("rhetoric", CHARISMA));
        skills.add(new Skill("intimidation", CHARISMA));
        skills.add(new Skill("arts", EMPATHY));
        skills.add(new Skill("teaching", EMPATHY));
        skills.add(new Skill("animalTaming", EMPATHY));
        skills.add(new Skill("gambling", MANIPULATION));
        skills.add(new Skill("impersonation", MANIPULATION));
        skills.add(new Skill("haggling", MANIPULATION));
        skills.add(new Skill("1hWeapons", CONDITION));
        skills.add(new Skill("2hWeapons", CONDITION));
        skills.add(new Skill("poleWeapons", CONDITION));
        skills.add(new Skill("brawling", CONDITION));
        skills.add(new Skill("archery", REFLEX));
        skills.add(new Skill("crossbows", REFLEX));
        skills.add(new Skill("pistols", REFLEX));
        skills.add(new Skill("rifles", REFLEX));

        Map<String, Skill> skillsMap = new HashMap<>();
        for(Skill skill : skills)
            skillsMap.put(skill.getId(), skill);
        return skillsMap;
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
        if(skills.containsKey(id))
            return this.skills.get(id);
        Log.e("debug", "Unable to get skill: \"" + id + "\"");
        return null;
    }

    public PlayerCharacter setSkillEx(String id, Skill skill){
        this.skills.put(id, skill);
        return this;
    }

    public PlayerCharacter setSkill(String id, int spentPoints){
        Skill skill = this.getSkill(id);
        if(skill != null)
            skill.setSpentPoints(spentPoints);
        return this;
    }

    public int getSkillsBalance(){
        int totalCount = 0;
        for(Map.Entry<String, Skill> skill : getSkills().entrySet()){
            totalCount += skill.getValue().getSpentPoints();
        }
        return totalCount;
    }

    public List<Inventory> getInventories(){
        List<Inventory> toReturn = new ArrayList<>();
        for(Item item : this.getEquipment().getSlots().values()){
            if(item instanceof Inventory){
                toReturn.add((Inventory)item);
            }
        }
        return toReturn;
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
            in.put("attributesCommitted", areAttributesCommitted());

            // put all of the skills into the json object
            JSONArray skillsJSONArray = new JSONArray();
            for(Map.Entry<String, Skill> entry : skills.entrySet()){
                JSONObject skillInJSON = new JSONObject();
                skillInJSON.put("id", entry.getKey());
                skillInJSON.put("counter", entry.getValue().getSpentPoints());
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
            this.setAttributesCommitted(obj.getBoolean("attributesCommitted"));

            // get all skills from json string
            JSONArray skillsObj = obj.getJSONArray("skills");
            for(int i = 0; i < skillsObj.length(); i++){
                JSONObject skillObj = skillsObj.getJSONObject(i);
                String id = skillObj.getString("id");
                int counter = skillObj.getInt("counter");
                this.setSkill(id, counter);
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

    public Item getItemInHands(){
        return this.getHeldItem();
    }

    public void equipInHands(Item item){
        this.setHeldItem(item);
    }

    public void unequipFromHands(){
        this.setHeldItem(null);
    }


    public PlayerCharacter(){
        for(int i = 0; i < ATTRIBUTES_AMOUNT; i++)
            attributes[i] = 5;
        setSkills(getPossibleSkills());
        getEquipment().equip(new SmallSatchel(), HumanEquipment.Slot.BACK);
    }
}

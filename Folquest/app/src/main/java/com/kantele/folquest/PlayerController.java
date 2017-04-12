package com.kantele.folquest;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;

/**
 * Created by Teemu on 23.3.2017.
 *
 * This class keeps track of different player stats, their inventory,
 * and handles some game functions
 */

public class PlayerController extends Application{

    private static final int HEAD = 0;
    private static final int TORSO = 1;
    private static final int BOTTOM = 2;
    private static final int FEET = 3;
    private static final int OTHER = 4;

    SharedPreferences sharedpreferences;

    public static final String Gold = "goldKey";
    public static final String Exp = "expKey";
    public static final String Level = "levelKey";
    public static final String ActiveQuests = "activeQuestKey";


    //Variables

    /**
     * Item variables start
     */

    ItemList itemList = new ItemList();

    public ArrayList<Item> ownedHeadItems = new ArrayList<>();
    public ArrayList<Item> ownedTorsoItems = new ArrayList<>();
    public ArrayList<Item> ownedBottomItems = new ArrayList<>();
    public ArrayList<Item> ownedFeetItems = new ArrayList<>();

    public Item equippedHeadItem;
    public Item equippedTorsoItem;
    public Item equippedBottomItem;
    public Item equippedFeetItem;
    /**
     *Item variables end
     */

    /**
     * PLAYER LEVEL CAPS
     */
    //Player levels 1-20        1,       2,      3,      4,      5,      6,      7,        8,       9,       10,     11,     12,     13,     14,     15,     16,     17,     18,     19,     20,
    int PlayerLevels[]   =  {   100,     200,    500,    750,    1000,   1500,   3000,     5000,    7500,    10000,  12500,  15000,  20000,  25000,  35000,  50000,  80000,  130000, 180000, 250000 }; //exp

    //TODO: GET PLAYERGOLD AND PLAYEREXP FROM A SAVED VALUE FROM A DATABASE DATABASE BASE

    long playerGold;
    long playerExp;
    long playerLvl;

    void loadSave() {
        sharedpreferences = getSharedPreferences("SavedPreferences", Context.MODE_PRIVATE);
        playerGold = sharedpreferences.getLong(Gold, 0);
        playerExp = sharedpreferences.getLong(Exp, 0);
        playerLvl = sharedpreferences.getLong(Level, 0);
        activeQuests = new ArrayList<Quest>();
        if(sharedpreferences.getStringSet(ActiveQuests,null) !=null ){
            for (String str : sharedpreferences.getStringSet(ActiveQuests, null))
                activeQuests.add(new Quest(str));
        }
    }



    //Quest tracking
    // TODO: GET ACTIVE QUESTS FROM A SAVE FILE
    static int maximumQuests = 3;
    public ArrayList<Quest> activeQuests = new ArrayList<>();
    public ArrayList<Quest> availableQuests = new ArrayList<>();

    //Methods
    public long getPlayerGold() { return playerGold; }

    public void setPlayerGold(long playerGold) { this.playerGold = playerGold; save(); }

    public long getPlayerExp() { return playerExp; }

    public void setPlayerExp(long playerExp) { this.playerExp = playerExp; save(); }

    //Methods for player leveling

    public long getPlayerLvl() { return playerLvl; }

    public void setPlayerLvl(long playerLvl) { this.playerLvl = playerLvl; }

    public long getPlayerLvlTargetExp() {
        return PlayerLevels[(int) getPlayerLvl()];
    }

    //CHECKS IF THE PLAYER IS ELIGIBLE FOR A NEW LEVEL AND CALCULATES THE OVERFLOW OF EXCESS EXP ADDING IT TO THE NEXT EXPTARGET
    public void checkForLeveling() {
        if(getPlayerExp() >= getPlayerLvlTargetExp()){
            int overflow = (int) (getPlayerExp()-getPlayerLvlTargetExp());

            setPlayerLvl(getPlayerLvl()+1);
            setPlayerExp(0 + overflow);

        } else {
            setPlayerLvl(getPlayerLvl());
            setPlayerExp(getPlayerExp());
        }
    }


    public void addQuest(Quest newQuest){
        if(activeQuests.size() < maximumQuests)
            activeQuests.add(newQuest);
    }

    public void checkProgress(){
        for(int i = 0; i < activeQuests.size() - 1; i++){
            //progressBarMainNäkymässä.progress = activeQuests.get(i).getProgress();
        }

    }

    public void completeQuest(int questIndex){
        this.playerExp =+ activeQuests.get(questIndex).getRewardExp();
        this.playerGold =+ activeQuests.get(questIndex).getRewardGold();

    }

    /**
     *Item Methods start
     */

    public Item getEquippedHeadItem() {
        return equippedHeadItem;
    }

    public void setEquippedHeadItem(Item equippedHeadItem) {
        this.equippedHeadItem = equippedHeadItem;
    }

    public Item getEquippedTorsoItem() {
        return equippedTorsoItem;
    }

    public void setEquippedTorsoItem(Item equippedTorsoItem) {
        this.equippedTorsoItem = equippedTorsoItem;
    }

    public Item getEquippedBottomItem() {
        return equippedBottomItem;
    }

    public void setEquippedBottomItem(Item equippedBottomItem) {
        this.equippedBottomItem = equippedBottomItem;
    }

    public Item getEquippedFeetItem() {
        return equippedFeetItem;
    }

    public void setEquippedFeetItem(Item equippedFeetItem) {
        this.equippedFeetItem = equippedFeetItem;
    }

    private boolean inList(Item item){
        if(item.getItemType() == HEAD) {
            for (Item itemInList : ownedHeadItems) {
                if (Objects.equals(item.getItemId(), itemInList.getItemId())) {
                    return true;
                }
            }
            return false;
        }
        if(item.getItemType() == TORSO) {
            for (Item itemInList : ownedTorsoItems) {
                if (Objects.equals(item.getItemId(), itemInList.getItemId())) {
                    return true;
                }
            }
            return false;
        }
        if(item.getItemType() == BOTTOM) {
            for (Item itemInList : ownedBottomItems) {
                if (Objects.equals(item.getItemId(), itemInList.getItemId())) {
                    return true;
                }
            }
            return false;
        }
        if(item.getItemType() == FEET) {
            for (Item itemInList : ownedFeetItems) {
                if (Objects.equals(item.getItemId(), itemInList.getItemId())) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public void addItem(Item item) {
        if(item.getItemType() == HEAD) {
            if(!inList(item)) {
                //Item is NOT the list, add it!
                ownedHeadItems.add(item);
            }
        }
        else if(item.getItemType() == TORSO) {
            if(!inList(item)) {
                //Item is NOT the list, add it!
                ownedTorsoItems.add(item);
            }
        }
        else if(item.getItemType() == BOTTOM) {
            if(!inList(item)) {
                //Item is NOT the list, add it!
                ownedBottomItems.add(item);
            }
        }
        else if(item.getItemType() == FEET) {
            if(!inList(item)) {
                //Item is NOT the list, add it!
                ownedFeetItems.add(item);
            }
        }
    }

    /* Adding defaults items when the game is started, these have to be in the database from the start! */
    public void addDefaultItems() {

        addItem(itemList.defaultHead);
        addItem(itemList.defaultTorso);
        addItem(itemList.defaultBottom);
        addItem(itemList.defaultFeet);
    }

    /**
     *Item Methods end
     */


    public void save(){
        long goldToSave = this.getPlayerGold();
        long expToSave = this.getPlayerExp();
        long levelToSave = this.getPlayerLvl();
        //TODO DO QUESTS AS STRING SET

        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putLong(Gold, goldToSave);
        editor.putLong(Exp, expToSave);
        editor.putLong(Level, levelToSave);

        if(activeQuests.size() > 0){
            Set<String> questsToSave = new HashSet<String>();
            for(int i = 0; i < activeQuests.size(); i++){
                questsToSave.add(activeQuests.get(i).toString());
            }

            editor.putStringSet(ActiveQuests, questsToSave);
        }

        editor.commit();

    }
}


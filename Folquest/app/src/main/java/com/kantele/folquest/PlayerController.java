package com.kantele.folquest;

import android.app.Application;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

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


    //Variables

    /**
     * Item variables start
     */

    public Boolean firstTimeState = false;

    ItemList itemList = new ItemList();

    public ArrayList<Item> ownedHeadItems = new ArrayList<>();
    public ArrayList<Item> ownedTorsoItems = new ArrayList<>();
    public ArrayList<Item> ownedBottomItems = new ArrayList<>();
    public ArrayList<Item> ownedFeetItems = new ArrayList<>();
    public ArrayList<Item> ownedAccessoryItems = new ArrayList<>();

    public Item equippedHeadItem;
    public Item equippedTorsoItem;
    public Item equippedBottomItem;
    public Item equippedFeetItem;
    public Item equippedAccessoryItem;
    /**
     *Item variables end
     */

    /**
     * PLAYER LEVEL CAPS
     */
    //Player levels 1-20        1,       2,      3,      4,      5,      6,      7,        8,       9,       10,     11,     12,     13,     14,     15,     16,     17,     18,     19,     20,
    int PlayerLevels[]   =  {   100,     200,    500,    750,    1000,   1500,   3000,     5000,    7500,    10000,  12500,  15000,  20000,  25000,  35000,  50000,  80000,  130000, 180000, 250000 }; //exp



    //TODO: GET PLAYERGOLD AND PLAYEREXP FROM A SAVED VALUE FROM A DATABASE DATABASE BASE

    boolean isBoy = false;

    long playerGold = 0;
    long playerExp = 0;
    long playerLvl = 0;

    //Quest tracking
    // TODO: GET ACTIVE QUESTS FROM A SAVE FILE
    static int maximumQuests = 3;
    public final ArrayList<Quest> activeQuests = new ArrayList<>();
    public final ArrayList<Quest> availableQuests = new ArrayList<>();

    //Methods
    public long getPlayerGold() { return playerGold; }

    public void setPlayerGold(long playerGold) { this.playerGold = playerGold; }

    public long getPlayerExp() { return playerExp; }

    public void setPlayerExp(long playerExp) { this.playerExp = playerExp; }

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

        // Save item to shared preferences

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

    public Item getEquippedAccessoryItem() {
        return equippedAccessoryItem;
    }

    public void setEquippedAccessoryItem(Item equippedAccessoryItem) {
        this.equippedAccessoryItem = equippedAccessoryItem;
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
        if(item.getItemType() == OTHER) {
            for (Item itemInList : ownedAccessoryItems) {
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
        else if(item.getItemType() == OTHER) {
            if(!inList(item)) {
                //Item is NOT the list, add it!
                ownedAccessoryItems.add(item);
            }
        }
    }

    /* Adding defaults items when the game is started, these have to be in the database from the start! */
    public void addDefaultItems() {

        addItem(itemList.defaultHead);
        addItem(itemList.defaultTorso);
        addItem(itemList.defaultBottom);
        addItem(itemList.defaultFeet);
        addItem(itemList.accessoryNone);
    }

    /**
     *Item Methods end
     */

    public Boolean getFirstTimeSavedState() {
        return firstTimeState;
    }

    public void setFirstTimeSavedState(Boolean state) {
        this.firstTimeState = state;
    }

    /**
     * Gender settings and avatar drawing
     */
    public void setPlayerGender(boolean gender) { this.isBoy = gender; }

    public boolean getPlayerGender() { return isBoy; }

    /**
     * Saving
     */



    public void save(){
        long goldToSave = this.getPlayerGold();
        long expToSave = this.getPlayerExp();

        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putLong(Gold, goldToSave);
        editor.putLong(Exp, expToSave);
        editor.commit();
        Toast.makeText(getBaseContext(),"Saved",Toast.LENGTH_LONG).show();

    }
}

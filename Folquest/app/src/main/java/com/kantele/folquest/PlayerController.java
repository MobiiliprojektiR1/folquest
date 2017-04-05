package com.kantele.folquest;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

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

    //TODO: GET PLAYERGOLD AND PLAYEREXP FROM A SAVED VALUE FROM A DATABASE DATABASE BASE

    long playerGold = 0;
    long playerExp = 0;

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
}

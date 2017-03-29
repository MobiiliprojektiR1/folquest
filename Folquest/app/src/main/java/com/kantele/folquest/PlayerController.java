package com.kantele.folquest;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by Teemu on 23.3.2017.
 *
 * This class keeps track of different player stats, their inventory,
 * and handles some game functions
 */

public class PlayerController extends Application{

    //Variables
    //TODO: GET PLAYERGOLD AND PLAYEREXP FROM A SAVED VALUE FROM A DATABASE DATABASE BASE
    long playerGold;
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
}

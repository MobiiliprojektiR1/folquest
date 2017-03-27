package com.kantele.folquest;

import java.util.ArrayList;

/**
 * Created by Teemu on 23.3.2017.
 *
 * This class keeps track of different player stats, their inventory,
 * and handles some game functions
 */

public class PlayerController {

    //Variables
    long playerGold;
    long playerExp;
    int maximumQuests = 3;
    ArrayList<Quest> activeQuests = new ArrayList<>();

    //Methods
    public long getPlayerGold() { return playerGold; }

    public void setPlayerGold(long playerGold) { this.playerGold = playerGold; }

    public long getPlayerExp() { return playerExp; }

    public void setPlayerExp(long playerExp) { this.playerExp = playerExp; }

    public void addQuest(Quest newQuest){
        if(activeQuests.size() <= maximumQuests)
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

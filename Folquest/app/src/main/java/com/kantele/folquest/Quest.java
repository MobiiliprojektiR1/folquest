package com.kantele.folquest;

/**
 * Created by Teemu on 22.3.2017.
 */

public class Quest{

    //Variables
    protected int requirement;
    protected int progress;
    protected int rewardGold;
    protected int rewardExp;
    protected String description;

    //Methods
    public Quest(int newRequirement, int newGold, int newExp, String newDescription){
        requirement = newRequirement;
        rewardGold = newGold;
        rewardExp = newExp;
        description = newDescription;

        progress = 0;
    }

    public int getRequirement(){
        return requirement;
    }

    public int getProgress(){
        return progress;
    }

    public int getRewardGold(){
        return rewardGold;
    }

    public int getRewardExp(){
        return rewardExp;
    }

    public String getDescription(){
        return description;
    }

}
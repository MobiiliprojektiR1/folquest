package com.kantele.folquest;

import java.io.Serializable;

/**
 * Created by Teemu on 22.3.2017.
 */

//Quest types and their different requirement values for each difficulty
enum questType{
    PUSHUPS,
    SITUPS,
    SQUATS,
    WALLSIT,
    DISTANCE,
    STEPS,
    WATER,
    CALORIES,
    REST
}

public class Quest implements Serializable{

    //Difficulty level 0-5         NOVICE,   EASY, NORMAL,   HARD, V.HARD,   EPIC
    int pushUpDifficulty[]   =  {      5,     15,     30,     60,    200,    500 }; //reps
    int sitUpDifficulty[]    =  {     10,     25,     50,    100,    500,   1000 }; //reps
    int squatsDifficulty[]   =  {     10,     25,     50,    100,    500,   1000 }; //reps
    int wallSitDifficulty[]  =  {     20,     60,    120,    300,   1500,   6000 }; //seconds
    int distanceDifficulty[] =  {   2000,   5000,  10000,  20000, 100000, 200000 }; //meters
    int stepsDifficulty[]    =  {   1500,   3000,   6000,  15000,  90000, 180000 }; //steps
    int waterDifficulty[]    =  {      1,      8,     24,     56,    112,    224 }; //glasses of water for a day/day/3 days/week/2 weeks/month
    int caloriesDifficulty[] =  {    500,   1500,   3000,   7000,  50000, 100000 }; //kilocalories burned
    int restDifficulty[]     =  {      1,      3,      7,     14,     30,    180 }; //sleep at least 8 hours a day for this many days

    //Rewards based on difficulty  NOVICE,   EASY, NORMAL,   HARD, V.HARD,   EPIC
    int expRewards[]         =  {     50,    200,    500,   1250,   8000,  20000 };
    int goldRewards[]        =  {     10,     50,    200,   1000,   4000,  20000 };

    //Variables
    protected questType type;
    protected int difficultyLevel;
    protected long requirement;
    protected int progress;
    protected long rewardGold;
    protected long rewardExp;
    protected String questText;
    protected String requirementUnit;
    protected String description;

    public boolean isQuestActive() {
        return questActive;
    }

    public void setQuestActive(boolean questActive) {
        this.questActive = questActive;
    }

    protected boolean questActive = false;


    //Methods
    public Quest(questType newType, int newDifficultyLevel){

        type = newType;
        difficultyLevel = newDifficultyLevel;
        rewardGold = goldRewards[newDifficultyLevel];
        rewardExp = expRewards[newDifficultyLevel];
        progress = 0;
        //TODO: make it change with quest type
        description = "Default description";

        switch (type){
            case PUSHUPS:
                requirement = pushUpDifficulty[difficultyLevel];
                questText = "Push up quest";
                requirementUnit  = " reps";
                break;
            case SITUPS:
                requirement = sitUpDifficulty[difficultyLevel];
                questText = "Sit up quest";
                requirementUnit  = " reps";
                break;
            case SQUATS:
                requirement = squatsDifficulty[difficultyLevel];
                questText = "Squat quest";
                requirementUnit  = " reps";
                break;
            case WALLSIT:
                requirement = wallSitDifficulty[difficultyLevel];
                questText = "Wall sit quest";
                requirementUnit  = " minutes";
                break;
            case DISTANCE:
                requirement = distanceDifficulty[difficultyLevel];
                questText = "Distance quest";
                requirementUnit  = " meters";
                break;
            case STEPS:
                requirement = stepsDifficulty[difficultyLevel];
                questText = "Step quest";
                requirementUnit  = " steps";
                break;
            case WATER:
                requirement = waterDifficulty[difficultyLevel];
                questText = "Water quest";
                //TODO: Requirement can be more than one day
                requirementUnit  = " glasses of water a day";
                break;
            case CALORIES:
                requirement = caloriesDifficulty[difficultyLevel];
                questText = "Calorie quest";
                requirementUnit  = " kilocalories to burn";
                break;
            case REST:
                requirement = restDifficulty[difficultyLevel];
                questText = "Rest quest";
                //TODO: Requirement can be more than one day
                requirementUnit  = " nights at least 8 hours of sleep";
                break;
        }
    }

    public long getRequirement() { return requirement; }

    public int getProgress(){
        return progress;
    }

    public void setProgress( int newProgress ) { this.progress = newProgress; }

    public long getRewardGold(){
        return rewardGold;
    }

    public long getRewardExp(){
        return rewardExp;
    }

    public String getDescription() {
        return description;
    }

    public String getQuestText(){
        return questText;
    }

    //We don't need this if we use adapter
    public String toString(){
        return type + "," + difficultyLevel + "," + progress + "," + description;
    }

    public Quest(String questAsString){
        String[] s = questAsString.split(",");
        type = questType.valueOf(s[0]);
        difficultyLevel = Integer.parseInt(s[1]);
        progress = Integer.parseInt(s[2]);
        description = s[3];

        rewardGold = goldRewards[difficultyLevel];
        rewardExp = expRewards[difficultyLevel];

        switch (type){
            case PUSHUPS:
                requirement = pushUpDifficulty[difficultyLevel];
                requirementUnit  = " reps";
                break;
            case SITUPS:
                requirement = sitUpDifficulty[difficultyLevel];
                requirementUnit  = " reps";
                break;
            case SQUATS:
                requirement = squatsDifficulty[difficultyLevel];
                requirementUnit  = " reps";
                break;
            case WALLSIT:
                requirement = wallSitDifficulty[difficultyLevel];
                requirementUnit  = " minutes";
                break;
            case DISTANCE:
                requirement = distanceDifficulty[difficultyLevel];
                requirementUnit  = " meters";
                break;
            case STEPS:
                requirement = stepsDifficulty[difficultyLevel];
                requirementUnit  = " steps";
                break;
            case WATER:
                requirement = waterDifficulty[difficultyLevel];
                //TODO: Requirement can be more than one day
                requirementUnit  = " glasses of water a day";
                break;
            case CALORIES:
                requirement = caloriesDifficulty[difficultyLevel];
                requirementUnit  = " kilocalories to burn";
                break;
            case REST:
                requirement = restDifficulty[difficultyLevel];
                //TODO: Requirement can be more than one day
                requirementUnit  = " nights at least 8 hours of sleep";
                break;
        }

    }

}
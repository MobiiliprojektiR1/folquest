package com.kantele.folquest;

/**
 * Created by Teemu on 22.3.2017.
 */

public class Quest{

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
        REST,
    }

    //Difficulty level 0-5        NOVICE,   EASY, NORMAL,   HARD, V.HARD,   EPIC
    int pushUpDifficulty[]   =  {      5,     15,     30,     60,    200,    500 }; //reps
    int sitUpDifficulty[]    =  {     10,     25,     50,    100,    500,   1000 }; //reps
    int squatsDifficulty[]   =  {     10,     25,     50,    100,    500,   1000 }; //reps
    int wallSitDifficulty[]  =  {     20,     60,    120,    300,   1500,   6000 }; //seconds
    int distanceDifficulty[] =  {   2000,   5000,  10000,  20000, 100000, 200000 }; //meters
    int stepsDifficulty[]    =  {   1500,   3000,   6000,  15000,  90000, 180000 }; //steps
    int waterDifficulty[]    =  {      1,      8,     24,     56,    112,    224 }; //glasses of water for a day/day/3 days/week/2 weeks/month
    int caloriesDifficulty[] =  {    500,   1500,   3000,   7000,  50000, 100000 }; //kilocalories burned
    int restDifficulty[]     =  {      1,      3,      7,     14,     30,    180 }; //sleep at least 8 hours a day for this many days

    //Rewards based on difficulty NOVICE,   EASY, NORMAL,   HARD, V.HARD,   EPIC
    int expRewards[]         =  {     50,    200,    500,   1250,   8000,  20000 };
    int goldRewards[]        =  {     10,     50,    200,   1000,   4000,  20000 };

    //Variables
    protected questType type;
    protected int difficultyLevel;
    protected int requirement;
    protected int progress;
    protected int rewardGold;
    protected int rewardExp;
    protected String description;

    //Methods
    public Quest(questType newType, int newDifficultyLevel, int newGold, int newExp, String newDescription){

        type = newType;
        difficultyLevel = newDifficultyLevel;
        rewardGold = newGold;
        rewardExp = newExp;
        description = newDescription;

        switch (type){
            case PUSHUPS:
                requirement = pushUpDifficulty[difficultyLevel];
                //description = res.strings.getByYabadabadoo;
                break;
            case SITUPS:
                requirement = sitUpDifficulty[difficultyLevel];
                break;
            case SQUATS:
                requirement = squatsDifficulty[difficultyLevel];
                break;
            case WALLSIT:
                requirement = wallSitDifficulty[difficultyLevel];
                break;
            case DISTANCE:
                requirement = distanceDifficulty[difficultyLevel];
                break;
            case STEPS:
                requirement = stepsDifficulty[difficultyLevel];
                break;
            case WATER:
                requirement = waterDifficulty[difficultyLevel];
                break;
            case CALORIES:
                requirement = caloriesDifficulty[difficultyLevel];
                break;
            case REST:
                requirement = restDifficulty[difficultyLevel];
                break;
        }

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
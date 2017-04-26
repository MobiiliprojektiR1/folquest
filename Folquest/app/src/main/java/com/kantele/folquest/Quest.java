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
    //WATER,
    CALORIES,
    //REST
}

public class Quest implements Serializable{


    //Difficulty level 0-5         NOVICE,   EASY, NORMAL,   HARD, V.HARD,   EPIC
    int pushUpDifficulty[]   =  {      5,     15,     30,     60,    200,    500 }; //reps
    int sitUpDifficulty[]    =  {     10,     25,     50,    100,    500,   1000 }; //reps
    int squatsDifficulty[]   =  {     10,     25,     50,    100,    500,   1000 }; //reps
    int wallSitDifficulty[]  =  {     20,     60,    120,    300,   1500,   6000 }; //seconds
    int distanceDifficulty[] =  {   2000,   5000,  10000,  20000, 100000, 200000 }; //meters
    int stepsDifficulty[]    =  {   1500,   3000,   6000,  15000,  90000, 180000 }; //steps
    //int waterDifficulty[]    =  {      1,      8,     24,     56,    112,    224 }; //glasses of water for a day/day/3 days/week/2 weeks/month
    int caloriesDifficulty[] =  {    500,   1500,   3000,   7000,  50000, 100000 }; //kilocalories burned
    //int restDifficulty[]     =  {      1,      3,      7,     14,     30,    180 }; //sleep at least 8 hours a day for this many days

    //Rewards based on difficulty  NOVICE,   EASY, NORMAL,   HARD, V.HARD,   EPIC
    int expRewards[]         =  {     50,    200,    500,   1250,   8000,  20000 };
    int goldRewards[]        =  {     10,     50,    200,   1000,   4000,  20000 };

    String distanceDesc[]     =   {     "There’s a huge boulder blocking the route. Push it away.",
                                        "A village drunk picks a fight with you.",
                                        "There’s a huge boulder blocking the route. Push it away."};

    String sitUpDesc[]      =   {   "You wake up in the middle of the forest. Get up.",
                                    "Someone cast a spell on you and you’re stuck in a swamp. Good luck. ",
                                    "Someone cast a spell on you and you’re stuck in a swamp. Good luck."};

    String pushUpDesc[]     =   {   "There’s a huge boulder blocking the route. Push it away.",
                                    "A village drunk picks a fight with you.",
                                    "A village drunk picks a fight with you." };

    String squatsDesc[]     =   {    "You enter a small cave in search of treasure. Who knows what you’ll find.",
                                    "You accidentally entered the wrong house. Sneak out. ",
                                    "You need to collect some ingredients for alchemy." };

    String wallsitDesc[]    =   {   "You’re trying to take a break from your forge and sit down a bit, but keep getting interrupted.",
                                    "Someone is trying to break through your door. Keep them out. ",
                                    "Someone is trying to break through your door. Keep them out. "};

    String stepsDesc[]      =   {   "You found a large tower with a long, spiraling staircase. Reach the top to earn a reward.",
                                    "Travel through the woods to improve your health.",
                                    "You’re climbing a steep mountain. There should be treasure waiting on top."};

    String caloriesDesc[]   =   {   "Your crush says you're fat, burn that fat!",
                                    "Your crush says you're fat, burn that fat!",
                                    "Your crush says you're fat, burn that fat!"};

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
        description = "Default description";

        switch (type){
            case PUSHUPS:
                requirement = pushUpDifficulty[difficultyLevel];
                questText = "Do ";
                requirementUnit  = " push ups";
                description = chooseDescription(pushUpDesc);
                break;
            case SITUPS:
                requirement = sitUpDifficulty[difficultyLevel];
                questText = "Do ";
                requirementUnit  = " sit ups";
                description = chooseDescription(sitUpDesc);
                break;
            case SQUATS:
                requirement = squatsDifficulty[difficultyLevel];
                questText = "Squat ";
                requirementUnit  = " times";
                description = chooseDescription(squatsDesc);
                break;
            case WALLSIT:
                requirement = wallSitDifficulty[difficultyLevel];
                questText = "Wall sit for ";
                requirementUnit  = " minutes";
                description = chooseDescription(wallsitDesc);
                break;
            case DISTANCE:
                requirement = distanceDifficulty[difficultyLevel];
                questText = "Travel for ";
                requirementUnit  = " meters";
                description = chooseDescription(distanceDesc);
                break;
            case STEPS:
                requirement = stepsDifficulty[difficultyLevel];
                questText = "Walk ";
                requirementUnit  = " steps";
                description = chooseDescription(stepsDesc);
                break;
            /*case WATER:
                requirement = waterDifficulty[difficultyLevel];
                questText = "Water quest";
                //Problem: Requirement can be more than one day
                requirementUnit  = " glasses of water a day";
                break;*/
            case CALORIES:
                requirement = caloriesDifficulty[difficultyLevel];
                questText = "Burn ";
                requirementUnit  = " kilocalories";
                description = chooseDescription(caloriesDesc);
                break;
            /*case REST:
                requirement = restDifficulty[difficultyLevel];
                questText = "Rest quest";
                //Problem: Requirement can be more than one day
                requirementUnit  = " nights at least 8 hours of sleep";
                break;*/
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

    public String questText() {
        return questText + requirement + requirementUnit;
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
            /*case WATER:
                requirement = waterDifficulty[difficultyLevel];
                //Problem: Requirement can be more than one day
                requirementUnit  = " glasses of water a day";
                break;*/
            case CALORIES:
                requirement = caloriesDifficulty[difficultyLevel];
                requirementUnit  = " kilocalories to burn";

                break;
            /*case REST:
                requirement = restDifficulty[difficultyLevel];
                //Problem: Requirement can be more than one day
                requirementUnit  = " nights at least 8 hours of sleep";
                break;*/
        }

    }

    private String chooseDescription(String[] type){
        double i = Math.random();
        if(i <= 0.33){
            System.out.println("alle 0.33");
            //TODO : index out of bound error koska descejä ei oo kaikille typeillä 3!
            return type[0];
        } else if(i >= 0.33 && i <= 0.66){
            System.out.println("yli 0.3 alle 0.6");
            return type[1];
        } else if(i >= 0.66 && i <= 1) {
            System.out.println("yli 0.66 alle 1");
            return type[2];
        } else {
            return "Just some casual working out to stay in shape!";
        }
    }

}
package com.kantele.folquest;

import android.graphics.Bitmap;

import com.kantele.folquest.Item;
import com.kantele.folquest.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

class ItemList {

    private static final int HEAD = 0;
    private static final int TORSO = 1;
    private static final int BOTTOM = 2;
    private static final int FEET = 3;
    private static final int OTHER = 4;

    public ArrayList<Item> allItems = new ArrayList<>();

    Item defaultHeadGirl = new Item(HEAD, "default_hair_woman", "Default Head", "Your default hair");
    Item defaultHeadBoy = new Item(HEAD, "default_hair_man", "Default Head", "Male default hair");
    Item defaultTorso = new Item(TORSO, "default_torso", "Dirty rags", "Your default torso");
    Item defaultBottom = new Item(BOTTOM, "default_bottom", "Default Pants", "Your default bottom");
    Item defaultFeet = new Item(FEET, "default_feet", "Boots", "Your default boots");

    //  Item                            Type    Id                  Name            Description
    //  Item exampleItem = new Item(    HEAD,   "headExample",      "Example",      "Example Description");

    // HEAD
    Item headBald   = new Item(HEAD, "head_bald", "Bald head", "Who needs helmet? or even hair");
    Item headBoy = new Item(HEAD, "default_hair_man", "Male Default Hair", "Nice and simple");
    Item headBunches = new Item(HEAD, "hair_bunches", "Ponytails", "Super cute and fearsome");

    // TORSO
    Item torsoBlueWoman = new Item(TORSO, "shirt_blue_woman", "Blue Knight Armor", "Nice armor");
    Item torsoBlueMan = new Item(TORSO, "shirt_man_blue", "Blue Knight Armor", "Nice armor");

    //BOTTOM
    Item bottomBlueTrousers = new Item(BOTTOM, "legs_blue", "Jeans", "Jeans.");

    // FEET
    Item feetBlackBoots = new Item(FEET, "feet_black_boots", "Black Boots", "Nice and casual");

    // ACCESSORIES
    Item accessorySword = new Item(OTHER, "accessory_sword", "Sword", "Slay those Dragon");
    Item accessoryNone = new Item(OTHER, "none", "No accessory", "No accessory");

}

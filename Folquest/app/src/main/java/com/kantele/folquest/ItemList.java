package com.kantele.folquest;

import android.graphics.Bitmap;

import com.kantele.folquest.Item;
import com.kantele.folquest.R;

import java.lang.reflect.Field;
import java.util.ArrayList;

class ItemList {

    private static final int HEAD = 0;
    private static final int TORSO = 1;
    private static final int BOTTOM = 2;
    private static final int FEET = 3;
    private static final int OTHER = 4;

    public static final ArrayList<Item> itemList = new ArrayList<>();

    Item defaultHead = new Item(HEAD, "default_hair_woman", "Default Head", "Your default hair");
    Item defaultTorso = new Item(TORSO, "default_torso", "Dirty rags", "Your default torso");
    Item defaultBottom = new Item(BOTTOM, "default_bottom", "Default Pants", "Your default bottom");
    Item defaultFeet = new Item(FEET, "default_feet", "Boots", "Your default boots");

    //  Item                            Type    Id                  Name            Description
    //  Item exampleItem = new Item(    HEAD,   "headExample",      "Example",      "Example Description");

    // HEAD
    Item headBald   = new Item(HEAD, "head_bald", "Bald head", "Who needs helmet? or even hair");
    Item headBoy = new Item(HEAD, "default_hair_man", "Male Default Hair", "Nice and simple");
    Item headBunches = new Item(HEAD, "hair_bunches", "Ponytails", "Super cute and fearsome");
    Item headVainamoinenGray = new Item(HEAD, "vainamoinen_long_gray", "Long hair", "For a true kantele player");
    Item headVainamoinenBrown = new Item(HEAD, "vainamoinen_long_brown", "Long hair", "For a true kantele player");
    Item headVainaGirlGray = new Item(HEAD, "vainamoinen_long_hair_gray", "Long hair", "For a true kantele player");
    Item headVainaGirlBrown = new Item(HEAD, "vainamoinen_long_hair_brown", "Long hair", "For a true kantele player");
    Item headLadyFinHair = new Item(HEAD, "ladyfin_hair", "Long hair with band", "Nice hair");

    // TORSO
    Item torsoBlueWoman = new Item(TORSO, "shirt_blue_woman", "Blue Knight Armor", "Nice armor");
    Item torsoBlueMan = new Item(TORSO, "shirt_man_blue", "Blue Knight Armor", "Nice armor");
    Item torsoFinguyShirt = new Item(TORSO, "finguy_shirt", "White tunic", "Nice tunic");
    Item torsoLadyfinDress = new Item(TORSO, "ladyfin_dress", "White dress", "Nice dress");

    //BOTTOM
    Item bottomBlueTrousers = new Item(BOTTOM, "legs_blue", "Jeans", "Jeans.");
    Item bottomFinguyPants = new Item(BOTTOM, "finguy_pants", "Pants", "Just pants.");
    Item bottomLadyfinPants = new Item(BOTTOM, "ladyfin_pants", "Pants", "Just pants. For women.");

    // FEET
    Item feetBlackBoots = new Item(FEET, "feet_black_boots", "Black Boots", "Nice and casual");
    Item feetFinguyShoes = new Item(FEET, "finguy_shoe", "Woodboots", "Wood and casual");
    Item feetLadyfinShoes = new Item(FEET, "ladyfin_shoe", "Woodshoes", "Nice and wood");

    // ACCESSORIES
    Item accessorySword = new Item(OTHER, "accessory_sword", "Sword", "Slay those Dragons");
    Item accessoryAxe = new Item(OTHER, "axe", "Axe", "Axe those Dragons");
    Item accessoryKantele = new Item(OTHER, "kantele", "Kantele", "Kantele those Dragons");
    Item accessoryNone = new Item(OTHER, "none", "No accessory", "No accessory");

}

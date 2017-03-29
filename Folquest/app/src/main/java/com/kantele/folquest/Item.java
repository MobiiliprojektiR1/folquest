package com.kantele.folquest;

/**
 * Folquest
 * Created by Janne ( ͡° ͜ʖ ͡°) on 23.3.2017.
 */

public class Item {

    int itemType;
    String itemId;
    String name;
    String description;
    int goldPrice;

    public Item(int itemType, String itemId, String itemName, String itemDescription) {
        this.itemType = itemType;
        this.itemId = itemId;
        name = itemName;
        description = itemDescription;
    }

    //private Image itemImage;


    public int getGoldPrice() {
        return goldPrice;
    }

    public void setGoldPrice(int goldPrice) {
        this.goldPrice = goldPrice;
    }

    public int getItemType() {
        return itemType;
    }

    public String getItemId() {
        return itemId;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}

class ItemList {

    private static final int HEAD = 0;
    private static final int TORSO = 1;
    private static final int BOTTOM = 2;
    private static final int OTHER = 3;

    Item defaultHead = new Item(HEAD, "defaultHead", "Default Head", "Your default hair");
    Item defaultTorso = new Item(TORSO, "defaultTorso", "Dirty rags", "Your default torso");
    Item defaultBottom = new Item(BOTTOM, "defaultBottom", "Bare feet", "Your default bottom");

    //  Item                            Type    Id                  Name            Description
    //  Item exampleItem = new Item(    HEAD,   "headExample",      "Example",      "Example Description");

    Item headBeanie = new Item(HEAD, "headBeanie", "Beanie", "It reads Nutcase");
    Item headHelmet = new Item(HEAD, "headHelmet", "Tough Helmet", "Forged deep in the mountain of forging");
    Item headBald   = new Item(HEAD, "headBald", "Bald head", "Who needs helmet? or even hair");
    Item headSpikyHair = new Item(HEAD, "headSpikyHair", "Spiky Hair", "For true protagonist");
    Item headTiara = new Item(HEAD, "headTiara", "Golden Tiara", "Belongs to a princess in a another castle");

    Item torsoShirt = new Item(TORSO, "torsoShirt", "Shirt", "Basic Shirt");
    Item torsoChainMail = new Item(TORSO, "torsoChainMail", "Chainmail", "Tough");
    Item torsoRobe = new Item(TORSO, "torsoRobe", "Orange Robe", "Munk robe" );
    Item torsoSpikyShirt = new Item(TORSO, "torsoSpikyShirt", "Spiky Shirt", "Spiky Shirt");
    Item torsoPinkDress = new Item(TORSO, "torsoPinkDress","Princess Dress", "pink dress");

    Item bottomPants = new Item(BOTTOM, "bottomPants", "Pants", "Pants");
    Item bottomSabatons  = new Item(BOTTOM, "bottomSabatons", "Knights boots", "Sabatons");
    Item bottomSandals = new Item(BOTTOM, "bottomSandals", "Sandals", "Socks and sandals");
    Item bottomSpikyLegs = new Item(BOTTOM, "bottomSpikyLegs", "Spiky leggings", "Yeah");
    Item bottomHighHeels = new Item(BOTTOM, "bottomHighHeels", "High Heels", "Also pink");

}


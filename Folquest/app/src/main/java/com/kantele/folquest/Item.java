package com.kantele.folquest;

import android.graphics.Bitmap;


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

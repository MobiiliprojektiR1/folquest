package com.kantele.folquest;

import android.app.Application;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by villemarttila on 13.4.2017.
 */


public class ShopController extends Application {

    public ArrayList<Item> shopHeadItems = new ArrayList<>();
    public ArrayList<Item> shopTorsoItems = new ArrayList<>();
    public ArrayList<Item> shopBottomItems = new ArrayList<>();
    public ArrayList<Item> shopFeetItems = new ArrayList<>();
    public ArrayList<Item> shopAccessoryItems = new ArrayList<>();

}

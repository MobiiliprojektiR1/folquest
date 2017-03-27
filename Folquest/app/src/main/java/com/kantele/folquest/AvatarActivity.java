package com.kantele.folquest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class AvatarActivity extends AppCompatActivity {

    private static final int BUTTON_HEAD_LEFT = 0;
    private static final int BUTTON_HEAD_RIGHT = 1;
    private static final int BUTTON_TORSO_LEFT = 2;
    private static final int BUTTON_TORSO_RIGHT = 3;
    private static final int BUTTON_BOTTOM_LEFT = 4;
    private static final int BUTTON_BOTTOM_RIGHT = 5;

    Button buttonHeadLeft;
    Button buttonHeadRight;
    Button buttonTorsoLeft;
    Button buttonTorsoRight;
    Button buttonBottomLeft;
    Button buttonBottomRight;

    TextView headItemText, torsoItemText, bottomItemText;

    Item equippedHeadItem;
    Item equippedTorsoItem;
    Item equippedBottomItem;

    int equippedHeadItemId = 0;
    int equippedTorsoItemId = 0;
    int equippedBottomItemId = 0;

    ArrayList<String> testItemsList = new ArrayList<>();

    ArrayList<Item> headItemsList = new ArrayList<>();
    ArrayList<Item> torsoItemsList = new ArrayList<>();
    ArrayList<Item> bottomItemsList = new ArrayList<>();

    ItemList itemList = new ItemList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);

        //Adapter for the inventory grid
        ArrayAdapter<String> inventoryGridAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, testItemsList);

        //Find initialize gridView
        GridView inventoryGridView = (GridView)findViewById(R.id.inventoryGridView);

        //populating ArrayList for demo purposes
        headItemsList.add(itemList.headBeanie);
        headItemsList.add(itemList.headBald);
        headItemsList.add(itemList.headHelmet);
        headItemsList.add(itemList.headSpikyHair);
        headItemsList.add(itemList.headTiara);

        torsoItemsList.add(itemList.torsoShirt);
        torsoItemsList.add(itemList.torsoChainMail);
        torsoItemsList.add(itemList.torsoRobe);
        torsoItemsList.add(itemList.torsoSpikyShirt);
        torsoItemsList.add(itemList.torsoPinkDress);

        bottomItemsList.add(itemList.bottomPants);
        bottomItemsList.add(itemList.bottomSabatons);
        bottomItemsList.add(itemList.bottomSandals);
        bottomItemsList.add(itemList.bottomSpikyLegs);
        bottomItemsList.add(itemList.bottomHighHeels);

        testItemsList.add("1");
        testItemsList.add("2");
        testItemsList.add("3");
        testItemsList.add("4");
        testItemsList.add("5");

        //set the equipped item to be the first in list
        // will be modified later
        equippedHeadItem = headItemsList.get(0);
        equippedTorsoItem = torsoItemsList.get(0);
        equippedBottomItem = bottomItemsList.get(0);

        // set the adapter for grid view
        inventoryGridView.setAdapter(inventoryGridAdapter);

        //initialize buttons
        buttonHeadLeft = (Button)findViewById(R.id.buttonHeadLeft);
        buttonHeadRight = (Button)findViewById(R.id.buttonHeadRight);

        buttonTorsoLeft = (Button)findViewById(R.id.buttonTorsoLeft);
        buttonTorsoRight = (Button)findViewById(R.id.buttonTorsoRight);

        buttonBottomLeft = (Button)findViewById(R.id.buttonBottomLeft);
        buttonBottomRight = (Button)findViewById(R.id.buttonBottomRight);

        headItemText = (TextView)findViewById(R.id.headItemText);
        torsoItemText = (TextView)findViewById(R.id.torsoItemText);
        bottomItemText = (TextView)findViewById(R.id.bottomItemText);

        buttonHeadLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAccessory(BUTTON_HEAD_LEFT);
            }
        });

        buttonHeadRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAccessory(BUTTON_HEAD_RIGHT);
            }
        });

        buttonTorsoLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAccessory(BUTTON_TORSO_LEFT);
            }
        });

        buttonTorsoRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAccessory(BUTTON_TORSO_RIGHT);
            }
        });

        buttonBottomLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAccessory(BUTTON_BOTTOM_LEFT);
            }
        });

        buttonBottomRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAccessory(BUTTON_BOTTOM_RIGHT);
            }
        });
    }


    public void changeAccessory(int accessorySlotToChange) {
        switch (accessorySlotToChange) {
            case BUTTON_HEAD_LEFT:
                if(equippedHeadItemId == 0) {
                    equippedHeadItem = headItemsList.get(headItemsList.size()-1);
                    equippedHeadItemId = headItemsList.size() -1;
                } else {
                    equippedHeadItem = headItemsList.get(equippedHeadItemId - 1);
                    equippedHeadItemId--;
                }
                //Toast.makeText(getApplicationContext(), "Head item: " + equippedHeadItem.getName() + " " + equippedHeadItem.getDescription(), Toast.LENGTH_SHORT).show();
                break;
            case BUTTON_HEAD_RIGHT:
                if(equippedHeadItemId == headItemsList.size() -1) {
                    equippedHeadItem = headItemsList.get(0);
                    equippedHeadItemId = 0;
                } else {
                    equippedHeadItem = headItemsList.get(equippedHeadItemId +1);
                    equippedHeadItemId++;
                }
                //Toast.makeText(getApplicationContext(), "Head item: " + equippedHeadItem.getName()+ " " + equippedHeadItem.getDescription(), Toast.LENGTH_SHORT).show();
                break;
            case BUTTON_TORSO_LEFT:
                if(equippedTorsoItemId == 0) {
                    equippedTorsoItem = torsoItemsList.get(torsoItemsList.size()-1);
                    equippedTorsoItemId = torsoItemsList.size()-1;
                } else {
                    equippedTorsoItem = torsoItemsList.get(equippedTorsoItemId - 1);
                    equippedTorsoItemId--;
                }
                //Toast.makeText(getApplicationContext(), "Torso item: " + equippedTorsoItem.getName() +"", Toast.LENGTH_SHORT).show();
                break;
            case BUTTON_TORSO_RIGHT:
                if(equippedTorsoItemId == torsoItemsList.size()-1) {
                    equippedTorsoItem = torsoItemsList.get(0);
                    equippedTorsoItemId = 0;
                } else {
                    equippedTorsoItem = torsoItemsList.get(equippedTorsoItemId + 1);
                    equippedTorsoItemId++;
                }
                //Toast.makeText(getApplicationContext(), "Torso item: " + equippedTorsoItem.getName() +"", Toast.LENGTH_SHORT).show();
                break;
            case BUTTON_BOTTOM_LEFT:
                if(equippedBottomItemId == 0) {
                    equippedBottomItem = bottomItemsList.get(bottomItemsList.size()-1);
                    equippedBottomItemId = bottomItemsList.size()-1;
                } else {
                    equippedBottomItem = bottomItemsList.get(equippedBottomItemId - 1);
                    equippedBottomItemId--;
                }
                //Toast.makeText(getApplicationContext(), "Bottom item: " + equippedBottomItem.getName() +"", Toast.LENGTH_SHORT).show();
                break;
            case BUTTON_BOTTOM_RIGHT:
                if(equippedBottomItemId == bottomItemsList.size()-1) {
                    equippedBottomItem = bottomItemsList.get(0);
                    equippedBottomItemId = 0;
                } else {
                    equippedBottomItem = bottomItemsList.get(equippedBottomItemId + 1);
                    equippedBottomItemId++;
                }
                //Toast.makeText(getApplicationContext(), "Bottom item: " + equippedBottomItem.getName() +"", Toast.LENGTH_SHORT).show();
                break;
        }
        headItemText.setText(equippedHeadItem.getName());
        torsoItemText.setText(equippedTorsoItem.getName());
        bottomItemText.setText(equippedBottomItem.getName());
    }
}

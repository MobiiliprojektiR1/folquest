package com.kantele.folquest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;

public class AvatarActivity extends AppCompatActivity {

    private static final int HEAD = 0;
    private static final int TORSO = 1;
    private static final int BOTTOM = 2;
    private static final int OTHER = 3;

    private static final int BUTTON_HEAD_LEFT = 0;
    private static final int BUTTON_HEAD_RIGHT = 1;
    private static final int BUTTON_TORSO_LEFT = 2;
    private static final int BUTTON_TORSO_RIGHT = 3;
    private static final int BUTTON_BOTTOM_LEFT = 4;
    private static final int BUTTON_BOTTOM_RIGHT = 5;
    private static final int BUTTON_HEAD_DIRECT = 6;
    private static final int BUTTON_TORSO_DIRECT = 7;
    private static final int BUTTON_BOTTOM_DIRECT = 8;

    Button buttonHeadLeft;
    Button buttonHeadRight;
    Button buttonTorsoLeft;
    Button buttonTorsoRight;
    Button buttonBottomLeft;
    Button buttonBottomRight;

    TextView headItemText, torsoItemText, bottomItemText;

    int equippedHeadItemId;
    int equippedTorsoItemId;
    int equippedBottomItemId;

    ItemList itemList = new ItemList();

    TabHost tabHost;
  
    Button buttonBack;

    PlayerController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Start the PLayerController
        controller = (PlayerController) getApplicationContext();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_avatar);

        //Find initialize gridView*/
        GridView inventoryHeadGridView = (GridView)findViewById(R.id.inventoryHeadGridView);
        GridView inventoryTorsoGridView = (GridView)findViewById(R.id.inventoryTorsoGridView);
        GridView inventoryBottomGridView = (GridView)findViewById(R.id.inventoryBottomGridView);

        //set the id in the list of the equipped items
        equippedHeadItemId = controller.ownedHeadItems.indexOf(controller.equippedHeadItem);
        equippedTorsoItemId = controller.ownedTorsoItems.indexOf(controller.equippedTorsoItem);
        equippedBottomItemId = controller.ownedBottomItems.indexOf(controller.equippedBottomItem);

        // set the adapters for grid views
        inventoryHeadGridView.setAdapter( new InventoryGridAdapter(this, controller.ownedHeadItems));
        inventoryTorsoGridView.setAdapter( new InventoryGridAdapter(this, controller.ownedTorsoItems));
        inventoryBottomGridView.setAdapter( new InventoryGridAdapter(this, controller.ownedBottomItems));


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


        //Tab initializing
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        //tab 1 - head items
        TabHost.TabSpec spec = tabHost.newTabSpec("Head");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Head");
        tabHost.addTab(spec);

        //tab 1 - torso items
        spec = tabHost.newTabSpec("Torso");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Torso");
        tabHost.addTab(spec);

        //tab 1 - bottom items
        spec = tabHost.newTabSpec("Bottom");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Bottom");
        tabHost.addTab(spec);

        buttonHeadLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAccessory(BUTTON_HEAD_LEFT,0);
            }
        });

        buttonHeadRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAccessory(BUTTON_HEAD_RIGHT,0);
            }
        });

        buttonTorsoLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAccessory(BUTTON_TORSO_LEFT,0);
            }
        });

        buttonTorsoRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAccessory(BUTTON_TORSO_RIGHT,0);
            }
        });

        buttonBottomLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAccessory(BUTTON_BOTTOM_LEFT,0);
            }
        });

        buttonBottomRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAccessory(BUTTON_BOTTOM_RIGHT, 0);
            }
        });

        inventoryHeadGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                changeAccessory(BUTTON_HEAD_DIRECT, position);
            }
        });
        inventoryTorsoGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                changeAccessory(BUTTON_TORSO_DIRECT, position);
            }
        });
        inventoryBottomGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                changeAccessory(BUTTON_BOTTOM_DIRECT, position);
            }
        });
      
      
        buttonBack = (Button) findViewById(R.id.buttonBack);

          buttonBack.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  AvatarActivity.super.finish();
              }
          });

        drawItems();
    }


    public void changeAccessory(int accessorySlotToChange, int position) {
        switch (accessorySlotToChange) {
            case BUTTON_HEAD_LEFT:
                if (equippedHeadItemId == 0) {
                    controller.equippedHeadItem = controller.ownedHeadItems.get(controller.ownedHeadItems.size() - 1);
                    equippedHeadItemId = controller.ownedHeadItems.size() - 1;
                } else {
                    controller.equippedHeadItem = controller.ownedHeadItems.get(equippedHeadItemId - 1);
                    equippedHeadItemId--;
                }
                break;
            case BUTTON_HEAD_RIGHT:
                if (equippedHeadItemId == controller.ownedHeadItems.size() - 1) {
                    controller.equippedHeadItem = controller.ownedHeadItems.get(0);
                    equippedHeadItemId = 0;
                } else {
                    controller.equippedHeadItem = controller.ownedHeadItems.get(equippedHeadItemId + 1);
                    equippedHeadItemId++;
                }
                break;
            case BUTTON_TORSO_LEFT:
                if (equippedTorsoItemId == 0) {
                    controller.equippedTorsoItem = controller.ownedTorsoItems.get(controller.ownedTorsoItems.size() - 1);
                    equippedTorsoItemId = controller.ownedTorsoItems.size() - 1;
                } else {
                    controller.equippedTorsoItem = controller.ownedTorsoItems.get(equippedTorsoItemId - 1);
                    equippedTorsoItemId--;
                }
                break;
            case BUTTON_TORSO_RIGHT:
                if (equippedTorsoItemId == controller.ownedTorsoItems.size() - 1) {
                    controller.equippedTorsoItem = controller.ownedTorsoItems.get(0);
                    equippedTorsoItemId = 0;
                } else {
                    controller.equippedTorsoItem = controller.ownedTorsoItems.get(equippedTorsoItemId + 1);
                    equippedTorsoItemId++;
                }
                break;
            case BUTTON_BOTTOM_LEFT:
                if (equippedBottomItemId == 0) {
                    controller.equippedBottomItem = controller.ownedBottomItems.get(controller.ownedBottomItems.size() - 1);
                    equippedBottomItemId = controller.ownedBottomItems.size() - 1;
                } else {
                    controller.equippedBottomItem = controller.ownedBottomItems.get(equippedBottomItemId - 1);
                    equippedBottomItemId--;
                }
                break;
            case BUTTON_BOTTOM_RIGHT:
                if (equippedBottomItemId == controller.ownedBottomItems.size() - 1) {
                    controller.equippedBottomItem = controller.ownedBottomItems.get(0);
                    equippedBottomItemId = 0;
                } else {
                    controller.equippedBottomItem = controller.ownedBottomItems.get(equippedBottomItemId + 1);
                    equippedBottomItemId++;
                }
                break;
            case BUTTON_HEAD_DIRECT:
                controller.equippedHeadItem = controller.ownedHeadItems.get(position);
                equippedBottomItemId = position;
                break;
            case BUTTON_TORSO_DIRECT:
                controller.equippedTorsoItem = controller.ownedTorsoItems.get(position);
                equippedTorsoItemId = position;
                break;
            case BUTTON_BOTTOM_DIRECT:
                controller.equippedBottomItem = controller.ownedBottomItems.get(position);
                equippedBottomItemId = position;
                break;
        }
        drawItems();
    }
    public void drawItems(){
        headItemText.setText(controller.equippedHeadItem.getName());
        torsoItemText.setText(controller.equippedTorsoItem.getName());
        bottomItemText.setText(controller.equippedBottomItem.getName());
    }
}

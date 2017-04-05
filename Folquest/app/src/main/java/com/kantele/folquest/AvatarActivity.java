package com.kantele.folquest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    Item equippedHeadItem;
    Item equippedTorsoItem;
    Item equippedBottomItem;

    int equippedHeadItemId = 0;
    int equippedTorsoItemId = 0;
    int equippedBottomItemId = 0;

    ArrayList<Item> headItemsList = new ArrayList<>();
    ArrayList<Item> torsoItemsList = new ArrayList<>();
    ArrayList<Item> bottomItemsList = new ArrayList<>();

    ItemList itemList = new ItemList();

    TabHost tabHost;
  
    Button buttonBack;
    Button buttonShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_avatar);



        //Adapter for the inventory grid
        /*ArrayAdapter<Item> inventoryGridAdapter = new ArrayAdapter<>(this, R.layout.inventory_grid_item, headItemsList);

        //Find initialize gridView*/
        GridView inventoryHeadGridView = (GridView)findViewById(R.id.inventoryHeadGridView);
        GridView inventoryTorsoGridView = (GridView)findViewById(R.id.inventoryTorsoGridView);
        GridView inventoryBottomGridView = (GridView)findViewById(R.id.inventoryBottomGridView);


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

        //set the equipped item to be the first in list
        // will be modified later
        equippedHeadItem = headItemsList.get(0);
        equippedTorsoItem = torsoItemsList.get(0);
        equippedBottomItem = bottomItemsList.get(0);

        // set the adapter for grid view
        //inventoryHeadGridView.setAdapter(inventoryGridAdapter);

        inventoryHeadGridView.setAdapter( new InventoryGridAdapter(this, headItemsList));
        inventoryTorsoGridView.setAdapter( new InventoryGridAdapter(this, torsoItemsList));
        inventoryBottomGridView.setAdapter( new InventoryGridAdapter(this, bottomItemsList));


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

        //tab 1
        TabHost.TabSpec spec = tabHost.newTabSpec("Head");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Head");
        tabHost.addTab(spec);

        //tab 1
        spec = tabHost.newTabSpec("Torso");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Torso");
        tabHost.addTab(spec);

        //tab 1
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
                //if tab is head, change head
                changeAccessory(BUTTON_HEAD_DIRECT, position);
            }
        });
        inventoryTorsoGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                //if tab is head, change head
                changeAccessory(BUTTON_TORSO_DIRECT, position);
            }
        });
        inventoryBottomGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                //if tab is head, change head
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

        buttonShop = (Button) findViewById(R.id.buttonShop);

        buttonShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AvatarActivity.this, ShopActivity.class);
                startActivity(intent);
            }
        });


    }


    public void changeAccessory(int accessorySlotToChange, int position) {
        switch (accessorySlotToChange) {
            case BUTTON_HEAD_LEFT:
                if (equippedHeadItemId == 0) {
                    equippedHeadItem = headItemsList.get(headItemsList.size() - 1);
                    equippedHeadItemId = headItemsList.size() - 1;
                } else {
                    equippedHeadItem = headItemsList.get(equippedHeadItemId - 1);
                    equippedHeadItemId--;
                }
                //Toast.makeText(getApplicationContext(), "Head item: " + equippedHeadItem.getName() + " " + equippedHeadItem.getDescription(), Toast.LENGTH_SHORT).show();
                break;
            case BUTTON_HEAD_RIGHT:
                if (equippedHeadItemId == headItemsList.size() - 1) {
                    equippedHeadItem = headItemsList.get(0);
                    equippedHeadItemId = 0;
                } else {
                    equippedHeadItem = headItemsList.get(equippedHeadItemId + 1);
                    equippedHeadItemId++;
                }
                //Toast.makeText(getApplicationContext(), "Head item: " + equippedHeadItem.getName()+ " " + equippedHeadItem.getDescription(), Toast.LENGTH_SHORT).show();
                break;
            case BUTTON_TORSO_LEFT:
                if (equippedTorsoItemId == 0) {
                    equippedTorsoItem = torsoItemsList.get(torsoItemsList.size() - 1);
                    equippedTorsoItemId = torsoItemsList.size() - 1;
                } else {
                    equippedTorsoItem = torsoItemsList.get(equippedTorsoItemId - 1);
                    equippedTorsoItemId--;
                }
                //Toast.makeText(getApplicationContext(), "Torso item: " + equippedTorsoItem.getName() +"", Toast.LENGTH_SHORT).show();
                break;
            case BUTTON_TORSO_RIGHT:
                if (equippedTorsoItemId == torsoItemsList.size() - 1) {
                    equippedTorsoItem = torsoItemsList.get(0);
                    equippedTorsoItemId = 0;
                } else {
                    equippedTorsoItem = torsoItemsList.get(equippedTorsoItemId + 1);
                    equippedTorsoItemId++;
                }
                //Toast.makeText(getApplicationContext(), "Torso item: " + equippedTorsoItem.getName() +"", Toast.LENGTH_SHORT).show();
                break;
            case BUTTON_BOTTOM_LEFT:
                if (equippedBottomItemId == 0) {
                    equippedBottomItem = bottomItemsList.get(bottomItemsList.size() - 1);
                    equippedBottomItemId = bottomItemsList.size() - 1;
                } else {
                    equippedBottomItem = bottomItemsList.get(equippedBottomItemId - 1);
                    equippedBottomItemId--;
                }
                //Toast.makeText(getApplicationContext(), "Bottom item: " + equippedBottomItem.getName() +"", Toast.LENGTH_SHORT).show();
                break;
            case BUTTON_BOTTOM_RIGHT:
                if (equippedBottomItemId == bottomItemsList.size() - 1) {
                    equippedBottomItem = bottomItemsList.get(0);
                    equippedBottomItemId = 0;
                } else {
                    equippedBottomItem = bottomItemsList.get(equippedBottomItemId + 1);
                    equippedBottomItemId++;
                }
                //Toast.makeText(getApplicationContext(), "Bottom item: " + equippedBottomItem.getName() +"", Toast.LENGTH_SHORT).show();
                break;
            case BUTTON_HEAD_DIRECT:
                equippedHeadItem = headItemsList.get(position);
                equippedBottomItemId = position;
                break;
            case BUTTON_TORSO_DIRECT:
                equippedTorsoItem = torsoItemsList.get(position);
                equippedTorsoItemId = position;
                break;
            case BUTTON_BOTTOM_DIRECT:
                equippedBottomItem = bottomItemsList.get(position);
                equippedBottomItemId = position;
                break;
        }
        headItemText.setText(equippedHeadItem.getName());
        torsoItemText.setText(equippedTorsoItem.getName());
        bottomItemText.setText(equippedBottomItem.getName());

    }
}

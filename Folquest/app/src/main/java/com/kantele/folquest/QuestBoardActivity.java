package com.kantele.folquest;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class QuestBoardActivity extends AppCompatActivity {

    Button buttonBack;
    Button buttonAvatar;
    Button buttonShop;
    //Button buttonQuest1;

    ListView questListView;
    PlayerController controller;

    TextView questBoardText, moneyAmountText, levelText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Start the PLayerController
        controller = (PlayerController) getApplicationContext();

        controller.createAvailableQuests();



        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_quest_board);

        buttonBack = (Button) findViewById(R.id.textViewLevel);
        buttonAvatar = (Button) findViewById(R.id.buttonAvatar);
        buttonShop = (Button) findViewById(R.id.buttonCoin);

        questListView = (ListView) findViewById(R.id.questListView);

        /*final ArrayAdapter<Quest> availableQuestsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, controller.availableQuests);
        questListView.setAdapter(availableQuestsAdapter);*/


        final QuestBoardAdapter questBoardAdapter = new QuestBoardAdapter(this, controller.availableQuests);
        questListView.setAdapter(questBoardAdapter);

        questListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                /*if(controller.activeQuests.size() < controller.maximumQuests) {
                    controller.addQuest(controller.availableQuests.get(position));
                    questBoardAdapter.remove(controller.availableQuests.get(position));
                    questListView.deferNotifyDataSetChanged();
                }else;*/

                /*if(questBoardAdapter.visibility){
                    questBoardAdapter.visibility = false;
                    questBoardAdapter.hideButtons(position);

                } else {
                    questBoardAdapter.visibility = true;
                    questBoardAdapter.hideButtons(position);

                }*/
                Log.d("QuestBoardActivity", position+"");
            }
        });



        // Change fonts
        Typeface labelFont = Typeface.createFromAsset(getAssets(), "fonts/LITHOSPRO-REGULAR.OTF");
        Typeface levelFont = Typeface.createFromAsset(getAssets(), "fonts/HARNGTON.TTF");
        Typeface basicFont = Typeface.createFromAsset(getAssets(), "fonts/MYRIADPRO-REGULAR.OTF");

        questBoardText = (TextView) findViewById(R.id.questBoardText);
        moneyAmountText = (TextView) findViewById(R.id.moneyAmountLabel);
        levelText = (TextView) findViewById(R.id.textViewLevel);

        questBoardText.setTypeface(labelFont);
        moneyAmountText.setTypeface(basicFont);
        levelText.setTypeface(levelFont);


        questListView.setDivider(null);
        questListView.setDividerHeight(0);

        //UPDATE PLAYER STATS TO THE TOP BAR
        long gold = controller.getPlayerGold();
        long level = controller.getPlayerLvl();

        moneyAmountText.setText("" + gold);
        levelText.setText("" + level);


        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuestBoardActivity.super.finish();
            }
        });

        buttonAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestBoardActivity.this, AvatarActivity.class);
                startActivity(intent);
            }
        });

        buttonShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestBoardActivity.this, ShopActivity.class);
                startActivity(intent);
            }
        });
    }

    public void removeQuest(Quest quest){
        //TODO: remove the quest by name or something, not possible by the position!! <- probably fixed
        controller.activeQuests.remove(quest);
        Log.d("QuestBoard", "Quest " + quest +"" + " removed!");
    }

    public void addQuest(int itemPosition) {

        if(controller.activeQuests.size() < controller.maximumQuests) {
            Log.d("QuestBoard", "Quest " + itemPosition+"" + " accepted!");
            controller.addQuest(controller.availableQuests.get(itemPosition));
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    }
}

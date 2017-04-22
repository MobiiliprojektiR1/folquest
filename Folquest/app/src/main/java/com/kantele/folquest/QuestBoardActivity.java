package com.kantele.folquest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class QuestBoardActivity extends AppCompatActivity {

    Button buttonBack;
    //Button buttonQuest1;

    ListView questListView;
    PlayerController controller;


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

        buttonBack = (Button) findViewById(R.id.buttonBack);
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
                Log.d("ListView", position+"");
            }
        });

        questListView.setDivider(null);
        questListView.setDividerHeight(0);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuestBoardActivity.super.finish();
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

    }
}

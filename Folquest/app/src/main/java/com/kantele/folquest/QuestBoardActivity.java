package com.kantele.folquest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
                Log.d("ListView", position+"");
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuestBoardActivity.super.finish();
            }
        });
    }

    public void addQuest(int itemPosition) {
        if(controller.activeQuests.size() < controller.maximumQuests) {
            Log.d("QUESTADAPTER", "Quest accepted!");
            controller.addQuest(controller.availableQuests.get(itemPosition));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}

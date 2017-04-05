package com.kantele.folquest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class QuestsActivity extends AppCompatActivity {

    Button buttonBack;
    Button buttonQuestBoard;
    ListView activeQuestListView;
    PlayerController controller;

    ArrayAdapter<Quest> activeQuestAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Start the PLayerController
        controller = (PlayerController) getApplicationContext();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_quests);

        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonQuestBoard = (Button) findViewById(R.id.buttonQuestBoard);
        activeQuestListView = (ListView) findViewById(R.id.activeQuestListView);
        activeQuestAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, controller.activeQuests);

        activeQuestListView.setAdapter(activeQuestAdapter);

        activeQuestListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                controller.setPlayerExp(controller.playerExp + controller.activeQuests.get(position).getRewardExp());
                activeQuestAdapter.remove(controller.activeQuests.get(position));
                activeQuestListView.deferNotifyDataSetChanged();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuestsActivity.super.finish();
            }
        });

        buttonQuestBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestsActivity.this, QuestBoardActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(controller.activeQuests.size() > 0){
            activeQuestAdapter.notifyDataSetChanged();
        }
    }
}
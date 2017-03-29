package com.kantele.folquest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class QuestsActivity extends AppCompatActivity {

    Button buttonBack;
    Button buttonQuestBoard;
    TextView textViewActiveQuest;

    //Start the PLayerController
    PlayerController controller = (PlayerController) getApplicationContext();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_quests);

        textViewActiveQuest = (TextView) findViewById(R.id.textViewActiveQuest1);
        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonQuestBoard = (Button) findViewById(R.id.buttonQuestBoard);



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
            textViewActiveQuest.setText(controller.activeQuests.get(0).getDescription() +
                    ", req:" + controller.activeQuests.get(0).getRequirement() +
                    ", gold:" + controller.activeQuests.get(0).getRewardGold() +
                    ", exp:" + controller.activeQuests.get(0).getRewardExp());
        }
    }
}
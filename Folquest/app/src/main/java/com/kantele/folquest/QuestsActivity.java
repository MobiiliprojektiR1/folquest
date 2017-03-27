package com.kantele.folquest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class QuestsActivity extends AppCompatActivity {

    Button buttonBack;
    Button buttonQuestBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_quests);

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
}
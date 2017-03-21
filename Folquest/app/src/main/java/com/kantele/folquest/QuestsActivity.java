package com.kantele.folquest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class QuestsActivity extends AppCompatActivity {

    Button buttonBack;
    Button buttonQuestBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

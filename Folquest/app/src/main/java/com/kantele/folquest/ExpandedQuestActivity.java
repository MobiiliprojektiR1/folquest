package com.kantele.folquest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ExpandedQuestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanded_quest);

        getIntent().getSerializableExtra("QuestClass");
    }
}
package com.kantele.folquest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {

    Button buttonBack;
    Button buttonMale;
    Button buttonFemale;

    //Start the PLayerController
    PlayerController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_settings);


        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonMale = (Button) findViewById(R.id.buttonMale);
        buttonFemale = (Button) findViewById(R.id.buttonFemale);


        controller = (PlayerController) getApplicationContext();

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsActivity.super.finish();
            }
        });

        buttonMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.setPlayerGender(true);
            }
        });

        buttonFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.setPlayerGender(false);
            }
        });
    }
}

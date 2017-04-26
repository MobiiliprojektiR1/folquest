package com.kantele.folquest;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

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


        Typeface basicFont = Typeface.createFromAsset(getAssets(), "fonts/MYRIADPRO-REGULAR.OTF");

        buttonBack = (Button) findViewById(R.id.textViewLevel);
        buttonMale = (Button) findViewById(R.id.buttonMale);
        buttonFemale = (Button) findViewById(R.id.buttonFemale);

        buttonMale.setTypeface(basicFont);
        buttonFemale.setTypeface(basicFont);
        buttonBack.setTypeface(basicFont);


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
                Toast toast = Toast.makeText(SettingsActivity.this, "You are a boy.", Toast.LENGTH_SHORT);
                View toastView = toast.getView();
                toastView.setMinimumWidth(800);
                toastView.setBackgroundColor(Color.parseColor("#e5d4b6"));
                toast.show();
            }
        });

        buttonFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.setPlayerGender(false);
                Toast toast = Toast.makeText(SettingsActivity.this, "You are a girl.", Toast.LENGTH_SHORT);
                View toastView = toast.getView();
                toastView.setMinimumWidth(800);
                toastView.setBackgroundColor(Color.parseColor("#e5d4b6"));
                toast.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Hide navigation bar! This goes to onResume!
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}

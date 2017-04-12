package com.kantele.folquest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FirstTimeLaunchActivity extends AppCompatActivity {

    Button buttonGirl, buttonBoy;

    PlayerController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Start the PLayerController
        controller = (PlayerController) getApplicationContext();

        setContentView(R.layout.activity_first_time_launch);

        buttonBoy = (Button) findViewById(R.id.buttonBoy);
        buttonGirl = (Button) findViewById(R.id.buttonGirl);

            // BUTTONS FUNCTIONALITY
            buttonBoy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    controller.setFirstTimeSavedState(false);
                    Intent intent = new Intent(FirstTimeLaunchActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            buttonGirl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    controller.setFirstTimeSavedState(false);
                    Intent intent = new Intent(FirstTimeLaunchActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });




    }
}

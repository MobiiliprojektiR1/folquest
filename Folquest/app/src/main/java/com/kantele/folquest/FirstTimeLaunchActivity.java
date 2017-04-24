package com.kantele.folquest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FirstTimeLaunchActivity extends AppCompatActivity {

    Button buttonGirl, buttonBoy, buttonContinue;
    TextView genderHolder;

    boolean genderChosen;

    PlayerController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Start the PLayerController
        controller = (PlayerController) getApplicationContext();

        setContentView(R.layout.activity_first_time_launch);

        genderChosen = false;

        buttonBoy = (Button) findViewById(R.id.buttonBoy);
        buttonGirl = (Button) findViewById(R.id.buttonGirl);
        buttonContinue = (Button) findViewById(R.id.buttonContinue);

        genderHolder = (TextView) findViewById(R.id.textViewGenderHolder);

        genderHolder.setText("Please choose your gender!");

        // BUTTONS FUNCTIONALITY
        buttonBoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.setPlayerGender(true);
                genderHolder.setText("You will start your game as a boy!");
                genderChosen = true;
            }
        });

        buttonGirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.setPlayerGender(false);
                genderHolder.setText("You will start your game as a girl!");
                genderChosen = true;
            }
        });

        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (genderChosen) {
                    controller.setFirstTimeSavedState(false);
                    controller.save();
                    Intent intent = new Intent(FirstTimeLaunchActivity.this, MainActivity.class);
                    startActivity(intent);
                } else if (!genderChosen) {
                    Toast.makeText(FirstTimeLaunchActivity.this, "You must choose a gender, don't get triggered!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

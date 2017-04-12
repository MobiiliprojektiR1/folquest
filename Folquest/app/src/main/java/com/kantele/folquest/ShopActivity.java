package com.kantele.folquest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

import static com.kantele.folquest.R.id.tabHost;

public class ShopActivity extends AppCompatActivity {

    Button buttonBack;
    PlayerController controller;
    TabHost tabHost;
    TextView moneyAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        //Start the PlayerController
        controller = (PlayerController) getApplicationContext();

        // Load current money
        moneyAmount = (TextView) findViewById(R.id.moneyTextView);
        moneyAmount.setText("Money: " + controller.getPlayerGold());

        buttonBack = (Button) findViewById(R.id.buttonBack);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopActivity.super.finish();
            }
        });

        //Tab initializing
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        //tab 1 - head items
        TabHost.TabSpec spec = tabHost.newTabSpec("Head");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Head");
        tabHost.addTab(spec);

        //tab 2 - torso items
        spec = tabHost.newTabSpec("Torso");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Torso");
        tabHost.addTab(spec);

        //tab 3 - bottom items
        spec = tabHost.newTabSpec("Bottom");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Bottom");
        tabHost.addTab(spec);
    }


}

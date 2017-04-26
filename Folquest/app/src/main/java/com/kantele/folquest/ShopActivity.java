package com.kantele.folquest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

public class ShopActivity extends AppCompatActivity {

    Button buttonBack;
    PlayerController controller;
    TabHost tabHost;
    TextView moneyAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_shop);

        //Start the PlayerController
        controller = (PlayerController) getApplicationContext();

        // Load current money
        moneyAmount = (TextView) findViewById(R.id.moneyTextView);
        moneyAmount.setText("Money: " + controller.getPlayerGold());

        buttonBack = (Button) findViewById(R.id.textViewLevel);

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
        TabHost.TabSpec spec = tabHost.newTabSpec("");
        spec.setContent(R.id.tab1);
        spec.setIndicator("", getResources().getDrawable(R.mipmap.accessory_sword));
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

        //tab 4 - feet items
        spec = tabHost.newTabSpec("Feet");
        spec.setContent(R.id.tab4);
        spec.setIndicator("Feet");
        tabHost.addTab(spec);

        //tab 5 - accessory items
        spec = tabHost.newTabSpec("Accessory");
        spec.setContent(R.id.tab5);
        spec.setIndicator("Accessory");
        tabHost.addTab(spec);
    }
}

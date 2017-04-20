package com.kantele.folquest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import static com.kantele.folquest.R.id.tabHost;

public class ShopActivity extends AppCompatActivity {

    Button buttonBack;
    PlayerController playerController;
    ShopController shopController;
    TabHost tabHost;
    TextView moneyAmount;
    ImageView itemImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        //Start the PlayerController
        playerController = (PlayerController) getApplicationContext();

        // Hide navbar and clock
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Load current money
        moneyAmount = (TextView) findViewById(R.id.moneyTextView);
        moneyAmount.setText("Money: " + controller.getPlayerGold());



        // Load test items


        // TAB BAR

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



        // BACK BUTTON FUNCTIONALITY
        buttonBack = (Button) findViewById(R.id.buttonBack);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopActivity.super.finish();
            }
        });
    }

    private class LoadShopItem extends AsyncTask<Object, Object, Item> {

        protected Item doInBackground(Object... params) {

        }

        @Override
        protected void onPostExecute(Item data) {
            super.onPostExecute(data);

        }
    }


}

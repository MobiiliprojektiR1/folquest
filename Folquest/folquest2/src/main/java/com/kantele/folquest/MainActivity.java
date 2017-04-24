package com.kantele.folquest;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

public class MainActivity extends Activity {

    TextView textViewStepsHolder, textViewSteps;
    TextView textViewKcalHolder, textViewKcal;
    TextView textViewDistHolder, textViewDist;


    SharedPreferences sharedpreferences;

    String steps, kcal, distance;

    public static final String STEPS = "stepsKey";
    public static final String KCAL = "kcalKey";
    public static final String DIST = "distKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                textViewStepsHolder = (TextView) findViewById(R.id.textViewStepsHolder);
                textViewSteps = (TextView) findViewById(R.id.textViewSteps);

                textViewKcalHolder = (TextView) findViewById(R.id.textViewKcalHolder);
                textViewKcal = (TextView) findViewById(R.id.textViewKcal);

                textViewDistHolder = (TextView) findViewById(R.id.textViewDistHolder);
                textViewDist = (TextView) findViewById(R.id.textViewDist);
            }
        });

        loadSave();

        // Register the local broadcast receiver
        registerBR();
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerBR();
    }

    private void registerBR() {
        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
        MessageReceiver messageReceiver = new MessageReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, messageFilter);
    }

    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra("data");
            // Display message in UI

            if (data != null) {
                String[] s = data.split(",");
                steps = String.valueOf(s[0]);
                kcal = String.valueOf(s[1]);
                distance = String.valueOf(s[2]);

                textViewSteps.setText(" " + steps);
                textViewKcal.setText(" " + kcal);
                textViewDist.setText("" + distance);

                save();
            }
        }
    }


    public void save(){
        String stepsToSave = steps;
        String kcalToSave = kcal;
        String distanceToSave = distance;

        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(STEPS, stepsToSave);
        editor.putString(KCAL, kcalToSave);
        editor.putString(DIST, distanceToSave);

        editor.commit();
    }

    public void loadSave() {
        sharedpreferences = getSharedPreferences("SavedPreferences", Context.MODE_PRIVATE);
        steps = sharedpreferences.getString(STEPS, "");
        kcal = sharedpreferences.getString(KCAL, "");
        distance = sharedpreferences.getString(DIST, "");

    }
}

package com.kantele.folquest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.result.DailyTotalResult;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    ItemList itemList = new ItemList();
    TextView avatarHeadTextView,avatarTorsoTextView,avatarBottomTextView;
    private static String TAG = "FIT:";
    long EXPERIENCE_CURRENT, EXPERIENCE_TARGET;

    Button buttonAvatar, buttonQuests, buttonSettings;

    TextView textViewExpCurrent, textViewExpTarget;

    TextView textViewStepsHolder, textViewSteps;
    TextView textViewKcalHolder, textViewKcal;
    TextView textViewDistHolder, textViewDist;

    Button buttonUpdate;

    // GOOGLE FIT

    GoogleApiClient apiClient;

    //Start the PLayerController
    PlayerController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        controller = (PlayerController) getApplicationContext();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        /* Adding defaults items when the game is started, these have to be in the database from the start! */
        controller.addDefaultItems();

        //adding some items for demo
        controller.addItem(itemList.headBald);
        controller.addItem(itemList.bottomHighHeels);
        controller.addItem(itemList.bottomSandals);
        controller.addItem(itemList.bottomPants);
        controller.addItem(itemList.torsoChainMail);
        controller.addItem(itemList.torsoPinkDress);

        /* Set the default items, this will be modified later */
        controller.setEquippedHeadItem(controller.ownedHeadItems.get(0));
        controller.setEquippedTorsoItem(controller.ownedTorsoItems.get(0));
        controller.setEquippedBottomItem(controller.ownedBottomItems.get(0));

        /* Text views for avatar items */
        avatarHeadTextView = (TextView)findViewById(R.id.avatarHeadTextView);
        avatarTorsoTextView = (TextView)findViewById(R.id.avatarTorsoTextView);
        avatarBottomTextView = (TextView)findViewById(R.id.avatarBottomTextView);

        /* Set texts for avatar item text views */
        avatarHeadTextView.setText(controller.equippedHeadItem.getName());
        avatarBottomTextView.setText(controller.equippedBottomItem.getName());
        avatarTorsoTextView.setText(controller.equippedTorsoItem.getName());

        buttonAvatar = (Button) findViewById(R.id.buttonAvatar);
        buttonQuests = (Button) findViewById(R.id.buttonQuests);
        buttonSettings = (Button) findViewById(R.id.buttonSettings);

        textViewExpCurrent = (TextView) findViewById(R.id.textViewExpCurrent);
        textViewExpTarget = (TextView) findViewById(R.id.textViewExpTarget);

        textViewStepsHolder = (TextView) findViewById(R.id.textViewStepsHolder);
        textViewSteps = (TextView) findViewById(R.id.textViewSteps);

        textViewKcalHolder = (TextView) findViewById(R.id.textViewKcalHolder);
        textViewKcal = (TextView) findViewById(R.id.textViewKcal);

        textViewDistHolder = (TextView) findViewById(R.id.textViewDistHolder);
        textViewDist = (TextView) findViewById(R.id.textViewDist);

        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);

        EXPERIENCE_CURRENT = controller.getPlayerExp();
        EXPERIENCE_TARGET = 3000;

        textViewExpCurrent.setText("" + EXPERIENCE_CURRENT);
        textViewExpTarget.setText("" + EXPERIENCE_TARGET);



        /* adapt the image to the size of the display */
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Bitmap bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(),R.drawable.maisema),size.x,size.y,true);

        /* fill the background ImageView with the resized image */
        ImageView iv_background = (ImageView) findViewById(R.id.iv_background);
        iv_background.setImageBitmap(bmp);





        // BUTTON FUNCTIONALITIES

        buttonAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AvatarActivity.class);
                startActivity(intent);
            }
        });

        buttonQuests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuestsActivity.class);
                startActivity(intent);
            }
        });

        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        //UPDATE DATA FROM GOOGLE FIT

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FetchStepsAsync().execute();
                //new FetchCalorieAsync().execute();
            }
        });


        // CREATE THE CONNECTION TO GOOGLE FIT

        buildFitnessClient();

        //PERMISSION REQUESTS
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        controller = (PlayerController) getApplicationContext();
        /* Set texts for avatar item text views */
        avatarHeadTextView.setText(controller.equippedHeadItem.getName());
        avatarBottomTextView.setText(controller.equippedBottomItem.getName());
        avatarTorsoTextView.setText(controller.equippedTorsoItem.getName());

        // This ensures that if the user denies the permissions then uses Settings to re-enable
        // them, the app will start working.
        buildFitnessClient();
    }

    /**
     *  Build a {@link GoogleApiClient} that will authenticate the user and allow the application
     *  to connect to Fitness APIs. The scopes included should match the scopes your app needs
     *  (see documentation for details). Authentication will occasionally fail intentionally,
     *  and in those cases, there will be a known resolution, which the OnConnectionFailedListener()
     *  can address. Examples of this include the user never having signed in before, or having
     *  multiple accounts on the device and needing to specify which account to use, etc.
     */
    private void buildFitnessClient() {
        if (apiClient == null) {
            apiClient = new GoogleApiClient.Builder(this)
                    .addApi(Fitness.HISTORY_API)
                    .addScope(new Scope(Scopes.FITNESS_LOCATION_READ_WRITE))
                    .addScope(new Scope(Scopes.FITNESS_BODY_READ_WRITE))
                    .addScope(new Scope(Scopes.FITNESS_NUTRITION_READ_WRITE))
                    .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
                    .addConnectionCallbacks(
                            new GoogleApiClient.ConnectionCallbacks() {
                                @Override
                                public void onConnected(Bundle bundle) {
                                    Log.i(TAG, "Connected!!!");
                                    // Now you can make calls to the Fitness APIs.
                                    //Async To fetch steps
                                }

                                @Override
                                public void onConnectionSuspended(int i) {
                                    // If your connection to the sensor gets lost at some point,
                                    // you'll be able to determine the reason and react to it here.
                                    if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST) {
                                        Log.i(TAG, "Connection lost.  Cause: Network Lost.");
                                    } else if (i
                                            == GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED) {
                                        Log.i(TAG,
                                                "Connection lost.  Reason: Service Disconnected");
                                    }
                                }
                            }
                    )
                    .enableAutoManage(this, 0, new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult result) {
                            Log.i(TAG, "Google Play services connection failed. Cause: " +
                                    result.toString());
                            Snackbar.make(
                                    MainActivity.this.findViewById(R.id.activity_main),
                                    "Exception while connecting to Google Play services: " +
                                            result.getErrorMessage(),
                                    Snackbar.LENGTH_INDEFINITE).show();
                        }
                    })
                    .build();
        }
    }


    private class FetchStepsAsync extends AsyncTask<Object, Object, int[]> {

        int[] data = new int[3];
        long totalCal = 0;
        long totalSteps = 0;
        long totalDist = 0;

        protected int[] doInBackground(Object... params) {

            PendingResult<DailyTotalResult> result = Fitness.HistoryApi.readDailyTotal(apiClient, DataType.TYPE_STEP_COUNT_DELTA);
            DailyTotalResult totalResult = result.await(30, TimeUnit.SECONDS);
            if (totalResult.getStatus().isSuccess()) {
                DataSet totalSet = totalResult.getTotal();
                if (totalSet != null) {
                    totalSteps = totalSet.isEmpty()
                            ? 0
                            : totalSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt();
                }
            } else {
                Log.w(TAG, "There was a problem getting the step count.");
            }

            PendingResult<DailyTotalResult> resultCal = Fitness.HistoryApi.readDailyTotal(apiClient, DataType.TYPE_CALORIES_EXPENDED);
            DailyTotalResult totalResultCal = resultCal.await(30, TimeUnit.SECONDS);
            if (totalResultCal.getStatus().isSuccess()) {
                DataSet totalSetCal = totalResultCal.getTotal();
                if (totalSetCal != null) {
                    totalCal = totalSetCal.isEmpty()
                            ? 0
                            : (long) totalSetCal.getDataPoints().get(0).getValue(Field.FIELD_CALORIES).asFloat();
                }
            } else {
                Log.w(TAG, "There was a problem getting the calories.");
            }

            PendingResult<DailyTotalResult> resultDist = Fitness.HistoryApi.readDailyTotal(apiClient, DataType.TYPE_DISTANCE_DELTA);
            DailyTotalResult totalResultDist = resultDist.await(30, TimeUnit.SECONDS);
            if (totalResultDist.getStatus().isSuccess()) {
                DataSet totalSetDist = totalResultDist.getTotal();
                if (totalSetDist != null) {
                    totalDist = totalSetDist.isEmpty()
                            ? 0
                            : (long) totalSetDist.getDataPoints().get(0).getValue(Field.FIELD_DISTANCE).asFloat();
                }
            } else {
                Log.w(TAG, "There was a problem getting the distance.");
            }



            data[0] = (int) totalSteps;
            data[1] = (int) totalCal;
            data[2] = (int) totalDist;

            return data;
        }

        @Override
        protected void onPostExecute(int[] aData) {
            super.onPostExecute(aData);

            //Total steps covered for that day
            Log.i(TAG, "Total steps: " + aData[0]);
            Log.i(TAG, "Total cals: " + aData[1]);
            Log.i(TAG, "Total distance: " + aData[2]);

            textViewSteps.setText("" + aData[0]);
            textViewKcal.setText("" + aData[1]);
            textViewDist.setText("" + aData[2]);

        }
    }
}

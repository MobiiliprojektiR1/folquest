package com.kantele.folquest;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    private static final int REQUEST_PERMISSIONS = 20;

    ItemList itemList = new ItemList();
    private static String TAG = "FIT";
    long EXPERIENCE_CURRENT, EXPERIENCE_TARGET;

    Button buttonAvatar, buttonQuests, buttonSettings;

    TextView textViewExpCurrent, textViewExpTarget, textViewLvl, textViewGold;

    TextView textViewStepsHolder, textViewSteps;
    TextView textViewKcalHolder, textViewKcal;
    TextView textViewDistHolder, textViewDist;

    Button buttonUpdate;

    ImageView headImageView, torsoImageView, bottomImageView, feetImageView;

    // GOOGLE FIT
    GoogleApiClient apiClient;

    //Start the PLayerController
    PlayerController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Start the PLayerController

        controller = (PlayerController) getApplicationContext();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_main);

        /* Adding defaults items when the game is started, these have to be in the database from the start! */
        controller.addDefaultItems();

        //adding some items for demo
        controller.addItem(itemList.headBald);
        //controller.addItem(itemList.bottomHighHeels);
        //controller.addItem(itemList.bottomSandals);
        controller.addItem(itemList.bottomTest);
        controller.addItem(itemList.torsoTest);
        //controller.addItem(itemList.torsoPinkDress);

        /* Set the default items, this will be modified later */
        controller.setEquippedHeadItem(controller.ownedHeadItems.get(0));
        controller.setEquippedTorsoItem(controller.ownedTorsoItems.get(0));
        controller.setEquippedBottomItem(controller.ownedBottomItems.get(0));
        controller.setEquippedFeetItem(controller.ownedFeetItems.get(0));

        //ImageViews for avatar items
        headImageView = (ImageView)findViewById(R.id.headImageView);
        torsoImageView = (ImageView)findViewById(R.id.torsoImageView);
        bottomImageView = (ImageView)findViewById(R.id.bottomImageView);
        feetImageView = (ImageView)findViewById(R.id.feetImageView);


        //Show the images of equipped items
        drawEquippedItems();

        buttonAvatar = (Button) findViewById(R.id.buttonAvatar);
        buttonQuests = (Button) findViewById(R.id.buttonQuests);
        buttonSettings = (Button) findViewById(R.id.buttonSettings);

        textViewExpCurrent = (TextView) findViewById(R.id.textViewExpCurrent);
        textViewExpTarget = (TextView) findViewById(R.id.textViewExpTarget);
        textViewLvl = (TextView) findViewById(R.id.textViewLevel);
        textViewGold = (TextView) findViewById(R.id.textViewGold);

        textViewStepsHolder = (TextView) findViewById(R.id.textViewStepsHolder);
        textViewSteps = (TextView) findViewById(R.id.textViewSteps);

        textViewKcalHolder = (TextView) findViewById(R.id.textViewKcalHolder);
        textViewKcal = (TextView) findViewById(R.id.textViewKcal);

        textViewDistHolder = (TextView) findViewById(R.id.textViewDistHolder);
        textViewDist = (TextView) findViewById(R.id.textViewDist);

        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);


        controller.checkForLeveling();

        EXPERIENCE_CURRENT = controller.getPlayerExp();
        EXPERIENCE_TARGET = controller.getPlayerLvlTargetExp();

        textViewExpCurrent.setText("" + EXPERIENCE_CURRENT);
        textViewExpTarget.setText("" + EXPERIENCE_TARGET);

        textViewLvl.setText("" + controller.getPlayerLvl());
        textViewGold.setText("" + controller.getPlayerGold());



        /* adapt the image to the size of the display */
        /*
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        Bitmap bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(),R.drawable.maisema),(size.x/2),(size.y/2), true);
        */

        /* fill the background ImageView with the resized image */
        /*
        ImageView iv_background = (ImageView) findViewById(R.id.iv_background);
        iv_background.setImageBitmap(bmp);
        */



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
                CheckPermissionsAndSyncData();

            }
        });


        // CREATE THE CONNECTION TO GOOGLE FIT
        buildFitnessClient();

        //PERMISSION REQUESTS ON LAUNCH
        CheckPermissionsAndSyncData();
    }

    public void CheckPermissionsAndSyncData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle("Location permission necessary");
                alertBuilder.setMessage("Folquest needs permission to access fine location in order to be able to sync your fitness data from Google Fit.\nThis is necessary for the progression of the game.");
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS);
                    }
                });

                AlertDialog alert = alertBuilder.create();
                alert.show();


                //ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS);
            } else {

                //Call whatever you want
                new FetchGoogleFitDataAsync().execute();
            }

        }
    }

    protected void drawEquippedItems() {
        //Set image for bottom item
        int resBottomID = getResources().getIdentifier(controller.equippedBottomItem.getItemId(), "mipmap", this.getPackageName());
        bottomImageView.setImageResource(resBottomID);

        //Set image for torso item
        int resTorsoID = getResources().getIdentifier(controller.equippedTorsoItem.getItemId(), "mipmap", this.getPackageName());
        torsoImageView.setImageResource(resTorsoID);

        //Set image for head item
        int resHeadID = getResources().getIdentifier(controller.equippedHeadItem.getItemId(), "mipmap", this.getPackageName());
        headImageView.setImageResource(resHeadID);

        //Set image for head item
        int resFeetID = getResources().getIdentifier(controller.equippedFeetItem.getItemId(), "mipmap", this.getPackageName());
        feetImageView.setImageResource(resFeetID);
    }


    @Override
    protected void onResume() {
        super.onResume();

        controller = (PlayerController) getApplicationContext();

        // Draw equipped items
        drawEquippedItems();


        // This ensures that if the user denies the permissions then uses Settings to re-enable
        // them, the app will start working.
        buildFitnessClient();

        controller.checkForLeveling();

        EXPERIENCE_CURRENT = controller.getPlayerExp();
        EXPERIENCE_TARGET = controller.getPlayerLvlTargetExp();

        textViewExpCurrent.setText("" + EXPERIENCE_CURRENT);
        textViewExpTarget.setText("" + EXPERIENCE_TARGET);

        textViewLvl.setText("" + controller.getPlayerLvl());
        textViewGold.setText("" + controller.getPlayerGold());

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
                                    Log.i(TAG, "Connected successfully!");
                                    // Now you can make calls to the Fitness APIs.
                                    //Async To fetch steps
                                }

                                @Override
                                public void onConnectionSuspended(int i) {
                                    // If your connection to the sensor gets lost at some point,
                                    // you'll be able to determine the reason and react to it here.
                                    if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST) {

                                        Log.i(TAG, "Connection lost.  Cause: Network Lost.");
                                    } else if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED) {

                                        Log.i(TAG, "Connection lost.  Reason: Service Disconnected");
                                    }
                                }
                            }
                    )
                    .enableAutoManage(this, 0, new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult result) {
                            Log.i(TAG, "Google Play services connection failed. Cause: " + result.toString());
                            Snackbar.make(MainActivity.this.findViewById(R.id.activity_main),
                                    "Exception while connecting to Google Play services: " +
                                            result.getErrorMessage(),
                                    Snackbar.LENGTH_SHORT).show();
                        }
                    })
                    .build();
        }
    }


    private class FetchGoogleFitDataAsync extends AsyncTask<Object, Object, int[]> {

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

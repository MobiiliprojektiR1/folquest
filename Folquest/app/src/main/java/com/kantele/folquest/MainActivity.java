package com.kantele.folquest;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final int REQUEST_PERMISSIONS = 20;

    ItemList itemList = new ItemList();
    private static String TAG = "FIT";
    long EXPERIENCE_CURRENT, EXPERIENCE_TARGET;

    ImageButton buttonAvatar, buttonQuests, buttonSettings;

    Button buttonQuestLeft, buttonQuestRight;

    TextView textViewExpCurrent, textViewExpTarget, textViewLvl, textViewGold;

    TextView textViewStepsHolder, textViewSteps;
    TextView textViewKcalHolder, textViewKcal;
    TextView textViewDistHolder, textViewDist;
    TextView questTextView;

    Button buttonUpdate;

    ImageView headImageView, torsoImageView, bottomImageView, feetImageView, accessoryImageView;
    ImageView characterImageView;

    // GOOGLE FIT
    GoogleApiClient apiClient;

    //Start the PLayerController
    PlayerController controller;

    Boolean isFirstTime;

    Boolean dialogShown = false;

    int shownQuestIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Start the PLayerController
        controller = (PlayerController) getApplicationContext();
        controller.loadSave();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Load First Time if this is first time playing
        isFirstTime = controller.getFirstTimeSavedState();

        if (isFirstTime) {
            Intent intent = new Intent(MainActivity.this, FirstTimeLaunchActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.activity_main);


        /* Adding defaults items when the game is started, these have to be in the database from the start! */
        controller.addDefaultItems();

        //Add items and equips the chosen ones
        addAndEquip();

        //ImageView for avatar
        characterImageView = (ImageView) findViewById(R.id.characterImageView);
        drawAvatar();

        //ImageViews for avatar items
        headImageView = (ImageView)findViewById(R.id.headImageView);
        torsoImageView = (ImageView)findViewById(R.id.torsoImageView);
        bottomImageView = (ImageView)findViewById(R.id.bottomImageView);
        feetImageView = (ImageView)findViewById(R.id.feetImageView);
        accessoryImageView = (ImageView)findViewById(R.id.accessoryImageView);

        //Show the images of equipped items
        drawEquippedItems();

        // UPDATE BG
        backgroundUpdate();


        //Setting Buttons and TextViews
        buttonAvatar = (ImageButton) findViewById(R.id.buttonAvatar);
        buttonQuests = (ImageButton) findViewById(R.id.buttonQuests);
        buttonSettings = (ImageButton) findViewById(R.id.buttonSettings);

        buttonQuestLeft = (Button) findViewById(R.id.buttonLeft);
        buttonQuestRight = (Button) findViewById(R.id.buttonRight);

        questTextView = (TextView) findViewById(R.id.activeQuestTextView);

        /*
        textViewExpCurrent = (TextView) findViewById(R.id.textViewExpCurrent);
        textViewExpTarget = (TextView) findViewById(R.id.textViewExpTarget);
        */

        textViewLvl = (TextView) findViewById(R.id.textViewLevel);
        textViewGold = (TextView) findViewById(R.id.textViewGold);

        textViewStepsHolder = (TextView) findViewById(R.id.textViewStepsHolder);
        textViewSteps = (TextView) findViewById(R.id.textViewSteps);




        controller.checkForLeveling();

        setPlayerStats();

        // Load custom fonts
        loadFonts();


        //BUTTON FUNCTIONALITIES
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

        questTextView.setText(controller.activeQuests.get(shownQuestIndex).toString());

        buttonQuestLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("QuestButtonRight", shownQuestIndex+"");
                if(shownQuestIndex > 0) {
                    questTextView.setText(controller.activeQuests.get(shownQuestIndex).toString());
                    shownQuestIndex--;
                } else if (shownQuestIndex == 0) {
                    shownQuestIndex = controller.activeQuests.size();
                    questTextView.setText(controller.activeQuests.get(shownQuestIndex).toString());
                }
            }
        });

        buttonQuestRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shownQuestIndex < controller.activeQuests.size()) {
                    Log.d("QuestButtonLeft", shownQuestIndex+"");
                    questTextView.setText(controller.activeQuests.get(shownQuestIndex).toString());
                    shownQuestIndex++;
                } else if(shownQuestIndex == controller.activeQuests.size()) {
                    shownQuestIndex = 0;
                    questTextView.setText(controller.activeQuests.get(shownQuestIndex).toString());
                }
            }
        });

        //CREATE THE CONNECTION TO GOOGLE FIT
        buildFitnessClient();

        //PERMISSION REQUESTS ON LAUNCH
 //       CheckPermissionsAndSyncData();
    }


    public void CheckPermissionsAndSyncData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                createAlertDialog();

                //ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS);
            } else {

                //Call whatever you want
                new FetchGoogleFitDataAsync().execute();
            }

        }

    }

    private void loadFonts() {

        Typeface basicFont = Typeface.createFromAsset(getAssets(), "fonts/MYRIADPRO-REGULAR.OTF");
        Typeface labelFont = Typeface.createFromAsset(getAssets(), "fonts/LITHOSPRO-REGULAR.OTF");

        textViewGold.setTypeface(basicFont);
        textViewSteps.setTypeface(basicFont);

        /*
        TextView tx = (TextView)findViewById(R.id.textview1);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/abc.ttf");

        tx.setTypeface(custom_font);
        */
    }

    private void createAlertDialog() {

        if (dialogShown == false) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setCancelable(true);
            alertBuilder.setTitle("Location permission necessary");
            alertBuilder.setMessage("Folquest needs permission to access fine location in order to be able to sync your fitness data from Google Fit.\nThis is necessary for the in game quests.");
            alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS);
                }
            });

            AlertDialog alert = alertBuilder.create();
            alert.show();
            dialogShown = true;
        }
    }


    private void addAndEquip() {
        //adding some items for demo

        // Head
        controller.addItem(itemList.headBald);
        controller.addItem(itemList.headBoy);
        controller.addItem(itemList.headBunches);

        controller.addItem(itemList.headLadyFinHair);
        controller.addItem(itemList.headVainaGirlBrown);
        controller.addItem(itemList.headVainaGirlGray);
        controller.addItem(itemList.headVainamoinenBrown);
        controller.addItem(itemList.headVainamoinenGray);

        // Torso
        controller.addItem(itemList.torsoBlueWoman);
        controller.addItem(itemList.torsoBlueMan);

        controller.addItem(itemList.torsoFinguyShirt);
        controller.addItem(itemList.torsoLadyfinDress);

        // Bottom
        controller.addItem(itemList.bottomBlueTrousers);

        controller.addItem(itemList.bottomLadyfinPants);
        controller.addItem(itemList.bottomFinguyPants);

        // Boots
        controller.addItem(itemList.feetBlackBoots);
        controller.addItem(itemList.feetFinguyShoes);
        controller.addItem(itemList.feetLadyfinShoes);
        
        // Accessories
        controller.addItem(itemList.accessoryNone);
        controller.addItem(itemList.accessorySword);
        controller.addItem(itemList.accessoryKantele);
        controller.addItem(itemList.accessoryAxe);
        
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

            //Set image for feet item
            int resFeetID = getResources().getIdentifier(controller.equippedFeetItem.getItemId(), "mipmap", this.getPackageName());
            feetImageView.setImageResource(resFeetID);

            //Set image for feet item
            int resAccessoryID = getResources().getIdentifier(controller.equippedAccessoryItem.getItemId(), "mipmap", this.getPackageName());
            accessoryImageView.setImageResource(resAccessoryID);

    }


    public void backgroundUpdate() {
        Activity activity = this;

        activity.runOnUiThread(new Runnable() {
            public void run() {

                /* adapt the image to the size of the display */
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);

                Bitmap bmp_morning = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                        getResources(),R.drawable.bg_morning),(size.x),(size.y), true);

                Bitmap bmp_day = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                        getResources(),R.drawable.bg_day),(size.x),(size.y), true);

                Bitmap bmp_evening = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                        getResources(),R.drawable.bg_evening),(size.x),(size.y), true);

                Bitmap bmp_night = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                        getResources(),R.drawable.bg_night),(size.x),(size.y), true);


                /* fill the background ImageView with the resized image */
                ImageView iv_background = (ImageView) findViewById(R.id.iv_background);

                // CHECK BG ACCORDING TO TIME! //
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);

                if (hour <= 6 ) {
                    iv_background.setImageBitmap(bmp_night);
                } else if (hour > 6 && hour < 11) {
                    iv_background.setImageBitmap(bmp_morning);
                } else if (hour >= 11 && hour < 18) {
                    iv_background.setImageBitmap(bmp_day);
                } else if (hour >= 18 && hour < 22) {
                    iv_background.setImageBitmap(bmp_evening);
                } else if (hour >= 22) {
                    iv_background.setImageBitmap(bmp_night);
                }
            }
        });
    }


    public void drawAvatar(){

        if (controller.getPlayerGender() == true) {
            characterImageView.setImageResource(R.drawable.mieshahmopohja);
        } else if (controller.getPlayerGender() == false) {
            characterImageView.setImageResource(R.drawable.naishahmopohja);
        }
    }

    private void setPlayerStats() {
        EXPERIENCE_CURRENT = controller.getPlayerExp();
        EXPERIENCE_TARGET = controller.getPlayerLvlTargetExp();

        /*
        textViewExpCurrent.setText("" + EXPERIENCE_CURRENT);
        textViewExpTarget.setText("" + EXPERIENCE_TARGET);
        */

        textViewLvl.setText("" + controller.getPlayerLvl());
        textViewGold.setText("" + controller.getPlayerGold());
    }


    @Override
    protected void onResume() {
        super.onResume();

        controller = (PlayerController) getApplicationContext();

        drawAvatar();

        // Draw equipped items
        drawEquippedItems();

        // Draw backgroud according to clock
        backgroundUpdate();


        // This ensures that if the user denies the permissions then uses Settings to re-enable
        // them, the app will start working.
        buildFitnessClient();

        CheckPermissionsAndSyncData();

        controller.checkForLeveling();

        setPlayerStats();

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
                                    // Async To fetch steps
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
                    .addApi(Wearable.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
    }

    // Connect to the data layer when the Activity starts
    @Override
    protected void onStart() {
        super.onStart();
        apiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    protected void onStop() {
        if (null != apiClient && apiClient.isConnected()) {
            apiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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

            textViewSteps.setText("Steps today: " + aData[0]);
            /*
            new SendToDataLayerThread("/data_path", "" + data[0] + ", " + data[1] + ", " + data[2]).start();

            textViewSteps.setText("" + aData[0]);
            textViewKcal.setText("" + aData[1]);
            textViewDist.setText("" + aData[2]);
            */

        }
    }


    class SendToDataLayerThread extends Thread {
        String path;
        String message;

        // Constructor to send a message to the data layer
        SendToDataLayerThread(String p, String msg) {
            path = p;
            message = msg;
        }

        public void run() {
            NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(apiClient).await();
            for (Node node : nodes.getNodes()) {
                MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(apiClient, node.getId(), path, message.getBytes()).await();
                if (result.getStatus().isSuccess()) {
                    Log.v("myTag", "Message: {" + message + "} sent to: " + node.getDisplayName());
                }
                else {
                    // Log an error
                    Log.v("myTag", "ERROR: failed to send Message");
                }
            }
        }
    }

}
















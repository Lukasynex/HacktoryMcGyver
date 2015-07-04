package testo.pl.hacktorymcgyver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class ResultActivity extends ActionBarActivity {

    private static final String ESTIMOTE_PROXIMITY_UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";

    private static final Region ALL_ESTIMOTE_BEACONS = new Region("regionId", null, null, null);

    private List<Record> list = new ArrayList<>();

    private Intent highScoreIntent;
    private static final int GREEN_BEACON_MAJOR = 16645;
    private static final int GREEN_BEACON_MINOR = 6563;
    private static final int PINK_BEACON_MAJOR = 29476;
    private static final int PINK_BEACON_MINOR = 59716;
    private boolean greenFlag = false;
    private boolean pinkFlag = false;
    public String greenBeaconDistance;
    public String pinkBeaconDistance;
    TextView greenResult;
    TextView pinkResult;
    Button button;


    public static String TAG = ResultActivity.class.getSimpleName();
    private BeaconManager beaconManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Context context = getApplicationContext();

        greenResult = (TextView) findViewById(R.id.green_score);
        pinkResult = (TextView) findViewById(R.id.pink_score);
        button = (Button) findViewById(R.id.button);

        greenBeaconDistance = "unknown";
        pinkBeaconDistance = "unknown";

        beaconManager = new BeaconManager(context);
        beaconManager.setForegroundScanPeriod(2000, 0);
        highScoreIntent = new Intent(this, HighscoreActivity.class);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Record> green = new ArrayList<>();
                List<Record> pink = new ArrayList<>();
                for (Record rec : list) {
                    if(rec.name.equals(Config.GREEN_BEACON_NAME))
                        green.add(rec);
                    else
                        pink.add(rec);

                }
                int glength = green.size()-1;
                int plength = pink.size()-1;
                if(plength>=0 ) {
                    Config.highScores.add(pink.get(plength));
                }
                if(glength>=0 ) {
                    Config.highScores.add(green.get(glength));
                }
                startActivity(highScoreIntent);
            }
        });

        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> beacons) {
                for (Beacon beacon : beacons) {
                    if ((beacon.getMajor() == GREEN_BEACON_MAJOR &&
                            beacon.getMinor() == GREEN_BEACON_MINOR) ||
                            (beacon.getMajor() == PINK_BEACON_MAJOR &&
                                    beacon.getMinor() == PINK_BEACON_MINOR)) {
                        // Log.d(TAG, "Catapulte beacons: " + beacon);
                        // Log.d(TAG, "Measured power  + RSSI" + beacon.getMeasuredPower() + " " + beacon.getRssi());
                        String uuid = beacon.getProximityUUID();
                        char firstChar = uuid.charAt(0);
                        // Log.e(TAG,"uuid: " + beacon.getProximityUUID().toString());
                        Log.e(TAG, "Pierwsza litera uuid: " + firstChar);

                        if (firstChar == '3') {
                            if (beacon.getMajor() == GREEN_BEACON_MAJOR) {
                                greenFlag = true;
                            }
                            if (beacon.getMajor() == PINK_BEACON_MAJOR) {
                                pinkFlag = true;
                            }

                        }

                        double accuracy = calculateAccuracy(beacon.getMeasuredPower(), beacon.getRssi());
                        // Log.d(TAG, "Distance: " + accuracy);
                        if (beacon.getMajor() == GREEN_BEACON_MAJOR) {

                            if (greenFlag) {
                                greenBeaconDistance = String.valueOf(round(accuracy, 2));
                                Log.d(TAG, "Zielony dystans: " + greenBeaconDistance);
                                greenResult.setText(greenBeaconDistance + " m");
                                list.add(new Record(Config.GREEN_BEACON_NAME, greenBeaconDistance));

                                greenFlag = false;
                            }


                        }
                        if (beacon.getMajor() == PINK_BEACON_MAJOR) {
                            if (pinkFlag) {
                                pinkBeaconDistance = String.valueOf(round(accuracy, 2));
                                Log.d(TAG, "Różowy dystans: " + pinkBeaconDistance);
                                pinkResult.setText(pinkBeaconDistance + " m");
                                list.add(new Record(Config.PINK_BEACON_NAME, pinkBeaconDistance));

                                pinkFlag = false;

                            }

                        }


                    }
                }


            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                try {
                    beaconManager.startRanging(ALL_ESTIMOTE_BEACONS);
                } catch (RemoteException e) {
                    Log.e(TAG, "Cannot start ranging", e);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS);
        } catch (RemoteException e) {
            Log.e(TAG, "Cannot stop but it does not matter now", e);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.disconnect();
    }

    protected static double calculateAccuracy(int txPower, double rssi) {
        if (rssi == 0) {
            return -1.0; // if we cannot determine accuracy, return -1.
        }

        double ratio = rssi * 1.0 / txPower;
        if (ratio < 1.0) {
            return Math.pow(ratio, 10);
        } else {
            double accuracy = (0.89976) * Math.pow(ratio, 7.7095) + 0.111;
            return accuracy;
        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}

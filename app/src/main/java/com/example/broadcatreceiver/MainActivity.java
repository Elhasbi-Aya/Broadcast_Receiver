package com.example.broadcatreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;
    private Button send;
    private TextView textBroadcastReceiver;
    private MyBroadcastBatteryLow myBroadcastBatteryLow;
    private MyBroadcastAppel myBroadcastAppel;
    IntentFilter filter;
    IntentFilter filter2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        send = (Button) findViewById(R.id.send);

        myBroadcastBatteryLow = new MyBroadcastBatteryLow();
        filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_LOW);
        myBroadcastAppel=new MyBroadcastAppel();
        filter2 = new IntentFilter();
        filter2.addAction("android.intent.action.PHONE_STATE");
        registerReceiver(new MyBroadcastAppel(), filter2);


        textBroadcastReceiver = (TextView) findViewById(R.id.broadcastText);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("FAKE_EVENT_INFO");
                sendBroadcast(intent);

            }
        });
    }

    public class MyBroadcastBatteryLow extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // votre code pour capter l'événement Batterie Faible et afficher un message dans le TextView

            textBroadcastReceiver.setText("battery faible");

        }
    }
    public class MyBroadcastAppel extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            String callState = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
            String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER); //TO DO: xxx

            if(callState.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                Date callStartTime = new Date();
                Log.d("MyBroadcastReceiver", "Incoming call from: " + number + callStartTime);
            }

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myBroadcastBatteryLow, filter);
        registerReceiver(myBroadcastAppel, filter2);
    }

    @Override
    protected void onPause() {

        super.onPause();
        unregisterReceiver(myBroadcastBatteryLow);
        unregisterReceiver(myBroadcastAppel);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE: {
            // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }
                else {

                }
                return;
            }
        }
    }
}
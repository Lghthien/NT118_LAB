package com.example.bai3;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_SEND_SMS = 1;

    ReentrantLock reentrantLock;
    Switch swAutoResponse;
    LinearLayout llButtons;
    Button btnSafe, btnMayday;
    EditText etPhoneNumber;
    ArrayList<String> requesters;
    ArrayAdapter<String> adapter;
    ListView lvMessages;
    BroadcastReceiver broadcastReceiver;
    public static boolean isRunning;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    final String AUTO_RESPONSE = "auto_response";

    private void checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    PERMISSION_REQUEST_SEND_SMS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_SEND_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            } else {
                // Permission denied, show a message or handle appropriately
            }
        }
    }

    private void findViewsByIds() {
        swAutoResponse = findViewById(R.id.sw_auto_response);
        llButtons = findViewById(R.id.ll_buttons);
        lvMessages = findViewById(R.id.lv_messages);
        btnSafe = findViewById(R.id.btn_safe);
        btnMayday = findViewById(R.id.btn_mayday);
        etPhoneNumber = findViewById(R.id.et_phone_number);
    }

    private void respond(String to, String response) {
        reentrantLock.lock();
        try {
            requesters.remove(to);
            adapter.notifyDataSetChanged();
        } finally {
            reentrantLock.unlock();
        }

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(to, null, response, null, null);
    }

    public void respond(boolean ok) {
        String okString = getString(R.string.i_am_safe_and_well_worry_not);
        String notOkString = getString(R.string.tell_my_mother_i_love_her);
        String outputString = ok ? okString : notOkString;

        String recipient = etPhoneNumber.getText().toString().trim();

        if (!recipient.isEmpty()) {
            respond(recipient, outputString);
        } else {
            ArrayList<String> requestersCopy = (ArrayList<String>) requesters.clone();
            for (String to : requestersCopy) {
                respond(to, outputString);
            }
        }
    }

    public void processReceiveAddresses(ArrayList<String> addresses) {
        for (String address : addresses) {
            if (!requesters.contains(address)) {
                reentrantLock.lock();
                try {
                    requesters.add(address);
                    adapter.notifyDataSetChanged();
                } finally {
                    reentrantLock.unlock();
                }
            }
        }

        if (swAutoResponse.isChecked()) {
            for (String address : addresses) {
                respond(address, getString(R.string.i_am_safe_and_well_worry_not));
            }
        }
    }

    private void handleOnClickListener() {
        btnSafe.setOnClickListener(view -> respond(true));
        btnMayday.setOnClickListener(view -> respond(false));

        swAutoResponse.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) llButtons.setVisibility(android.view.View.GONE);
            else llButtons.setVisibility(android.view.View.VISIBLE);

            editor.putBoolean(AUTO_RESPONSE, isChecked);
            editor.apply();
        });
    }

    private void initBroadcastReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ArrayList<String> addresses = intent.getStringArrayListExtra(SmsReceiver.SMS_MESSAGE_ADDRESS_KEY);
                processReceiveAddresses(addresses);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;

        if (broadcastReceiver == null) initBroadcastReceiver();

        IntentFilter intentFilter = new IntentFilter(SmsReceiver.SMS_FORWARD_BROADCAST_RECEIVER);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        isRunning = false;

        unregisterReceiver(broadcastReceiver);
    }

    private void initVariables() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        editor = sharedPreferences.edit();
        reentrantLock = new ReentrantLock();
        requesters = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, requesters);
        lvMessages.setAdapter(adapter);

        boolean autoResponse = sharedPreferences.getBoolean(AUTO_RESPONSE, false);
        swAutoResponse.setChecked(autoResponse);
        if (autoResponse) llButtons.setVisibility(android.view.View.GONE);

        initBroadcastReceiver();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewsByIds();
        initVariables();
        handleOnClickListener();

        checkAndRequestPermissions();
    }
}

// SmsReceiver.java
package com.example.bai3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import java.util.ArrayList;

public class SmsReceiver extends BroadcastReceiver {

    public static final String SMS_FORWARD_BROADCAST_RECEIVER = "sms_forward_broadcast_receiver";
    public static final String SMS_MESSAGE_ADDRESS_KEY = "sms_message_address_key";
    public static final String AUTO_RESPONSE_KEY = "auto_response";

    @Override
    public void onReceive(Context context, Intent intent) {
        String keyword = "are you ok?".toLowerCase();

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            if (pdus == null || pdus.length == 0) return;

            SmsMessage[] messages = new SmsMessage[pdus.length];
            ArrayList<String> addresses = new ArrayList<>();

            for (int i = 0; i < pdus.length; i++) {
                if (android.os.Build.VERSION.SDK_INT >= 23) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], "3gpp");
                } else {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
            }

            for (SmsMessage message : messages) {
                if (message.getMessageBody().toLowerCase().contains(keyword)) {
                    addresses.add(message.getOriginatingAddress());
                }
            }

            if (!addresses.isEmpty()) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("com.example.bai3", Context.MODE_PRIVATE);
                boolean autoResponseEnabled = sharedPreferences.getBoolean(AUTO_RESPONSE_KEY, false);

                if (autoResponseEnabled) {
                    SmsManager smsManager = SmsManager.getDefault();
                    for (String address : addresses) {
                        smsManager.sendTextMessage(address, null, context.getString(R.string.i_am_safe_and_well_worry_not), null, null);
                    }
                } else {
                    if (!MainActivity.isRunning) {
                        // Start MainActivity if not running
                        Intent startMainActivity = new Intent(context, MainActivity.class);
                        startMainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startMainActivity.putStringArrayListExtra(SMS_MESSAGE_ADDRESS_KEY, addresses);
                        context.startActivity(startMainActivity);
                    } else {
                        // Forward broadcast if MainActivity is running
                        Intent forwardBroadcast = new Intent(SMS_FORWARD_BROADCAST_RECEIVER);
                        forwardBroadcast.putStringArrayListExtra(SMS_MESSAGE_ADDRESS_KEY, addresses);
                        context.sendBroadcast(forwardBroadcast);
                    }
                }
            }
        }
    }
}
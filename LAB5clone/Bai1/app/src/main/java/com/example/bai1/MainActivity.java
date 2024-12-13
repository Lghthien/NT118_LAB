package com.example.bai1;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int SMS_PERMISSION_CODE = 100;
    private BroadcastReceiver broadcastReceiver;
    private IntentFilter filter;
    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvContent = findViewById(R.id.tv_content);

        // Kiểm tra quyền truy cập SMS
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, SMS_PERMISSION_CODE);
        } else {
            initBroadcastReceiver();
            registerReceiver(broadcastReceiver, filter);
        }
    }

    private void initBroadcastReceiver() {
        // Tạo bộ lọc để lắng nghe tin nhắn đến
        filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");

        // Tạo BroadcastReceiver
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                processReceive(context, intent);
            }
        };
    }

    public void processReceive(Context context, Intent intent) {
        Toast.makeText(context, getString(R.string.you_have_a_new_message), Toast.LENGTH_LONG).show();

        final String SMS_EXTRA = "pdus";
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            tvContent.setText(R.string.no_message_received);
            return;
        }

        Object[] messages = (Object[]) bundle.get(SMS_EXTRA);
        if (messages == null || messages.length == 0) {
            tvContent.setText(R.string.no_message_received);
            return;
        }

        StringBuilder smsBuilder = new StringBuilder();

        for (Object message : messages) {
            try {
                SmsMessage smsMessage;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    String format = bundle.getString("format");
                    smsMessage = SmsMessage.createFromPdu((byte[]) message, format);
                } else {
                    smsMessage = SmsMessage.createFromPdu((byte[]) message);
                }

                if (smsMessage != null) {
                    String msgBody = smsMessage.getMessageBody();
                    String address = smsMessage.getDisplayOriginatingAddress();
                    smsBuilder.append("From: ").append(address).append("\n").append(msgBody).append("\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (smsBuilder.length() > 0) {
            tvContent.setText(smsBuilder.toString());
        } else {
            tvContent.setText(R.string.no_message_received);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (broadcastReceiver == null) {
            initBroadcastReceiver();
        }
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initBroadcastReceiver();
                registerReceiver(broadcastReceiver, filter);
            } else {
                Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
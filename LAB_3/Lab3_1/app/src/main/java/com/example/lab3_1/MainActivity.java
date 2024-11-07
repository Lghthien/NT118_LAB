package com.example.lab3_1;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.lab3_1.DbAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DbAdapter dbAdapter;
    private List<String> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbAdapter = new DbAdapter(this);
        dbAdapter.open();
        dbAdapter.deleteAllUsers();

        for (int i = 0; i < 10; i++) {
            dbAdapter.createUser("Nguyễn Văn An " + i);
        }

        users = getData();
        showData();
    }

    private List<String> getData() {
        List<String> users = new ArrayList<>();
        Cursor cursor = dbAdapter.getAllUsers();

        while (cursor.moveToNext()) {
            int nameIndex = cursor.getColumnIndex(DbAdapter.KEY_NAME);
            if (nameIndex != -1) {
                users.add(cursor.getString(nameIndex));
            }
        }
        cursor.close();
        return users;
    }

    private void showData() {
        ListView lvUser = findViewById(R.id.lv_user);
        ArrayAdapter<String> userAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.item_user, users);
        lvUser.setAdapter(userAdapter);
    }
}

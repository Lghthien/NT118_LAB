package com.example.th1;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        }
        );
        TextView tv1 = (TextView) findViewById(R.id.tv1);
        ListView lvPerson = (ListView) findViewById(R.id.lv1);
        final String arr[] = {"Teo", "Ty", "Bin", "Bo"};
        ArrayAdapter<String> adapter = new
                ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, arr);
        lvPerson.setAdapter(adapter);

        lvPerson.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        tv1.setText("position :" + arg2 + "; value =" + arr[arg2]);
                    }
                });
    }
}
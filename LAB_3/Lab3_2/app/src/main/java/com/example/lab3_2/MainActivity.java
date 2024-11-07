package com.example.lab3_2;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseHandler db;
    private List<Contact> contactList;
    private ContactAdapter contactAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo cơ sở dữ liệu
        db = new DatabaseHandler(this);
        initializeDatabase();

        // Lấy danh sách liên hệ từ cơ sở dữ liệu
        contactList = db.getAllContacts();
        listView = findViewById(R.id.listViewContacts);
        contactAdapter = new ContactAdapter(this, contactList);
        listView.setAdapter(contactAdapter);

        // Thiết lập listener cho sự kiện long click
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleteContact(position);
                return true;
            }
        });
    }

    // Khởi tạo cơ sở dữ liệu với một số dữ liệu mẫu
    private void initializeDatabase() {
        try {
            db.deleteAllContacts(); // Xóa tất cả liên hệ hiện có
            db.addContact(new Contact("Ravi", "9100000000"));
            db.addContact(new Contact("Srinivas", "9199999999"));
            db.addContact(new Contact("Tommy", "9522222222"));
            db.addContact(new Contact("Karthik", "9533333333"));
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi khi khởi tạo cơ sở dữ liệu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // Xóa liên hệ
    private void deleteContact(int position) {
        if (position < 0 || position >= contactList.size()) {
            Toast.makeText(this, "Liên hệ không tồn tại.", Toast.LENGTH_SHORT).show();
            return;
        }

        Contact contact = contactList.get(position);
        try {
            db.deleteContact(contact); // Xóa liên hệ khỏi cơ sở dữ liệu
            contactList.remove(position); // Xóa liên hệ khỏi danh sách
            contactAdapter.notifyDataSetChanged(); // Cập nhật danh sách
            Toast.makeText(MainActivity.this, "Đã xóa: " + contact.getName(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Lỗi khi xóa liên hệ: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

package com.example.lab3_3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "studentsManager";
    private static final String TABLE_STUDENTS = "students";

    private static final String KEY_ID = "id";
    private static final String KEY_CODE = "code";
    private static final String KEY_NAME = "name";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_PHONE = "phone";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STUDENTS_TABLE = "CREATE TABLE " + TABLE_STUDENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_CODE + " TEXT,"
                + KEY_NAME + " TEXT,"
                + KEY_ADDRESS + " TEXT,"
                + KEY_PHONE + " TEXT)";
        db.execSQL(CREATE_STUDENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }

    public void addStudent(Student student) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(KEY_CODE, student.getCode());
            values.put(KEY_NAME, student.getName());
            values.put(KEY_ADDRESS, student.getAddress());
            values.put(KEY_PHONE, student.getPhone());
            db.insert(TABLE_STUDENTS, null, values);
        }
    }

    public void updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CODE, student.getCode());
        values.put(KEY_NAME, student.getName());
        values.put(KEY_ADDRESS, student.getAddress());
        values.put(KEY_PHONE, student.getPhone());

        db.update(TABLE_STUDENTS, values, KEY_ID + " = ?", new String[]{String.valueOf(student.getId())});
        db.close();
    }

    public Student getStudent(int id) {
        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor cursor = db.query(TABLE_STUDENTS, new String[]{KEY_ID, KEY_CODE, KEY_NAME, KEY_ADDRESS, KEY_PHONE},
                     KEY_ID + "=?", new String[]{String.valueOf(id)},
                     null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                return new Student(
                        cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(KEY_CODE)),
                        cursor.getString(cursor.getColumnIndex(KEY_NAME)),
                        cursor.getString(cursor.getColumnIndex(KEY_ADDRESS)),
                        cursor.getString(cursor.getColumnIndex(KEY_PHONE))
                );
            }
        }
        return null;
    }

    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_STUDENTS;

        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor cursor = db.rawQuery(selectQuery, null)) {
            if (cursor.moveToFirst()) {
                do {
                    Student student = new Student(
                            cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                            cursor.getString(cursor.getColumnIndex(KEY_CODE)),
                            cursor.getString(cursor.getColumnIndex(KEY_NAME)),
                            cursor.getString(cursor.getColumnIndex(KEY_ADDRESS)),
                            cursor.getString(cursor.getColumnIndex(KEY_PHONE))
                    );
                    studentList.add(student);
                } while (cursor.moveToNext());
            }
        }
        return studentList;
    }

    public void deleteStudent(Student student) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.delete(TABLE_STUDENTS, KEY_ID + " = ?", new String[]{String.valueOf(student.getId())});
        }
    }

}

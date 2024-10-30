package com.rashi.healthapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//public class DatabaseAdapter{
//    private static final String DB_NAME = "Database.db";
//    private static final String TABLE_NAME = "User_Table";
//    private static final int DB_VERSION = 1;
////    private static final String KEY_ID = "username";
////    private static final String NAME = "name";
////    private static final String EMAIL = "email";
//    private static final String TABLE_CREATE="create table User_Table(id integer primary key autoincrement, username text not null, name text, email text, phone text, password text not null);";
//    private final Context context;
//    private static SQLiteDatabase userDatabase;
//    private static MyDBHelper helper;
//    public DatabaseAdapter(Context context){
//        this.context = context;
//        helper = new MyDBHelper(context, DB_NAME, null, DB_VERSION);
//    }
//    private static class MyDBHelper extends SQLiteOpenHelper{
//        public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version){
//            super(context, name, cursorFactory, version);
//        }
//        @Override
//        public void onCreate(SQLiteDatabase db){
//            db.execSQL(TABLE_CREATE);
//        }
//        @Override
//        public void onUpgrade(SQLiteDatabase db, int old_version, int new_version){
//            Log.w("Updation","Database version is being updated");
//            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//            onCreate(db);
//        }
//        public DatabaseAdapter open(){
//            userDatabase =helper.getWritableDatabase();
//            return this;
//        }
//        public void close(){
//            userDatabase.close();
//        }
//    }
public class DatabaseAdapter extends SQLiteOpenHelper{
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "UserData";
    private static final String USER_INFO = "UserInfo";
    private static final String USER_STEPS = "UserSteps";

    public DatabaseAdapter(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        //String create_query = "CREATE TABLE "+USER_INFO+" (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, nickname TEXT, email TEXT, phone TEXT, password TEXT)";
        String create_query = "CREATE TABLE "+USER_STEPS+" (date TEXT PRIMARY KEY, steps INTEGER)";
        db.execSQL(create_query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+USER_STEPS);
        onCreate(db);
    }
    public void createTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        String create_query = "CREATE TABLE "+USER_STEPS+" (date TEXT PRIMARY KEY, steps INTEGER)";
        db.execSQL(create_query);
    }
    public void recordDay(String date, Integer steps){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date", date);
        values.put("steps", steps);
        db.insert(USER_STEPS, null, values);
    }
    public Cursor getMax(){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[]{"MAX(steps)"};
        return db.query(USER_STEPS, columns, null, null, null, null, null);
    }
    public Cursor getAvg(){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[]{"AVG(steps)"};
        return db.query(USER_STEPS, columns, null, null, null, null, null);
    }
    public void newUser(String username, String nickname, String email, String phone, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("nickname", nickname);
        values.put("email", email);
        values.put("password", password);
        values.put("phone", phone);
        db.insert(USER_INFO, null, values);
        db.close();

    }
    public Cursor getRecord(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns =new String[]{"password"};
        String selection = "email = ?" ;
        String[] selectionArgs = {email};
        return db.query(USER_INFO, columns , selection,selectionArgs, null, null, null);

    }

}
//
//}

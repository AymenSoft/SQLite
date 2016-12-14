package com.aymensoft.sqlite.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper{

    public static final String UsersTable ="userstable";

    public static final String UserID = "userid";
    public static final String UserName = "username";

    private static final String CreateUsersTable = "Create Table "+UsersTable+"("+
                                                    UserID+" INTEGER, "+
                                                    UserName+" STRING); ";



    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("onCreate", "1");
        db.execSQL(CreateUsersTable);
        Log.e("onCreate", "2");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int newVersion) {
        Log.e("onUpgrade", "1");
        db.execSQL("DROP TABLE IF EXISTS "+UsersTable+";");
        Log.e("onUpgrade", "2");
        onCreate(db);
        Log.e("onUpgrade", "3");
    }
}

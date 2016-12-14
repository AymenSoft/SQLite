package com.aymensoft.sqlite.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.aymensoft.sqlite.models.Users;

import java.util.ArrayList;
import java.util.List;

public class UsersDB {

    private static final int DB_Version = 1;
    private static final String DB_Name = "users.db";
    private SQLiteDatabase db;
    private DBHelper helper;

    public UsersDB(Context context){
        helper=new DBHelper(context,DB_Name,null,DB_Version);
    }

    public void open(){
        db=helper.getWritableDatabase();
    }

    public void close(){
        db.close();
    }

    public SQLiteDatabase getDb(){
        return db;
    }

    public long InsertTop(Users user){
        Cursor cursor = db.rawQuery("SELECT MAX ("+DBHelper.UserID+") FROM "+DBHelper.UsersTable,null);
        if (cursor.moveToFirst()){
            user.setUserid(cursor.getInt(0)+1);
        }else {
            user.setUserid(1);
        }

        ContentValues values = new ContentValues();
        values.put(DBHelper.UserID, user.getUserid());
        values.put(DBHelper.UserName, user.getUsername());
        return db.insert(DBHelper.UsersTable, null, values);
    }

    public long Update(Users user, int userid){
        ContentValues values = new ContentValues();
        values.put(DBHelper.UserName, user.getUsername());
        return db.update(DBHelper.UsersTable, values,
                        "`"+DBHelper.UserID+"`= ?",
                        new String[] {String.valueOf(userid)});
    }

    public long RemoveAll(){
        return db.delete(DBHelper.UsersTable, null, null);
    }

    public long RemoveById(int userid){
        return db.delete(DBHelper.UsersTable,
                        "`"+DBHelper.UserID+"`= ?",
                        new String[] {String.valueOf(userid)});
    }

    public List<Users> GetAll(){
        List<Users> users = new ArrayList<>();
        Cursor cursor = db.query(DBHelper.UsersTable, new String[]{"*"}, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do {
                Users user = new Users();
                user.setUserid(cursor.getInt(0));
                user.setUsername(cursor.getString(1));
                users.add(user);
            }while (cursor.moveToNext());
        }
        if (!cursor.isClosed() && cursor!=null){
            cursor.close();
        }
        return users;
    }

    public List<Users> GetByUserName(String username){
        List<Users> users = new ArrayList<>();
        String[] tableColumns = new String[] {"*"};
        String whereClause = "username = ?";
        String[] whereArgs = new String[] {username};
        Cursor cursor = db.query(DBHelper.UsersTable, tableColumns, whereClause, whereArgs, null, null, null);
        if (cursor.moveToFirst()){
            do {
                Users user = new Users();
                user.setUserid(cursor.getInt(0));
                user.setUsername(cursor.getString(1));
                users.add(user);
            }while (cursor.moveToNext());
        }
        if (!cursor.isClosed() && cursor!=null){
            cursor.close();
        }
        return users;
    }

}

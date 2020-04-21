package com.example.memoapp2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="App.db";
    public static final String TABLE_NAME="bucketlist";
    public static final String COL_1="ID";
    public static final String COL_2="TITLE";
    public static final String COL_3="DONE";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,TITLE TEXT,DONE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertItem(String title){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues item=new ContentValues();
        item.put(COL_2,title);
        item.put(COL_3,0);
        Log.d("TAG",item.toString());
        long result=db.insert(TABLE_NAME,null,item);
        if(result==-1)return false;
        else return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from "+TABLE_NAME,null);
        return cursor;
    }

    public boolean updateItem(String id,String title){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues item=new ContentValues();
        item.put(COL_1,id);
        item.put(COL_2,title);
        item.put(COL_3,0);
        int result=db.update(TABLE_NAME,item,"id =? ",new String[] {id});
        if(result==-1)return false;
        else return true;
    }

    public boolean deleteItem(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        int result=db.delete(TABLE_NAME,"id=?",new String[]{id});
        if(result==-1)return false;
        else return true;
    }
}

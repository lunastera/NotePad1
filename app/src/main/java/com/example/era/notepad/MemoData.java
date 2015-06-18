package com.example.era.notepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by era on 15/06/18.
 */
public class MemoData extends SQLiteOpenHelper {
    //データベースに必要な要素を宣言
    private static final String DB_NAME = "memodata.db";
    private static final int DB_VERSION = 1;
    public static String ID ="_id";
    public static String MEMO_TABLE = "mydata";
    public static String TITLE = "title";
    public static String MEMO ="memotest";

    public MemoData(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(
                "create table "+MEMO_TABLE+"("
                +ID+" integer primary key,"
                +TITLE+" text,"
                +MEMO+" text);"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL(
                "drop table if exists "+MEMO_TABLE
        );
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
    }
}

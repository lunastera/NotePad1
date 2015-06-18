package com.example.era.notepad;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by era on 15/06/18.
 */
public class MemoData extends SQLiteOpenHelper {
    //データベースに必要な要素を宣言
    private static final String DB_NAME = "memodata.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "mydata";
    private static final String TITLE = "title";
    private static final String MEMO ="memo";

    public void onCreate(SQLiteDatabase db){
        db.execSQL(
                "create table "+TABLE_NAME+"("
                +TITLE+" text,"
                +MEMO+" text);"
        );
    }
}

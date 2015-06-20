package com.example.era.notepad;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


public class MemoCreateActivity extends ActionBarActivity {

    private MemoData helper;
    private SQLiteDatabase db;
    private ContentValues values;
    private EditText addTitle;
    private EditText addText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_create);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //
        addTitle = (EditText)findViewById(R.id.addTitle);
        addText = (EditText)findViewById(R.id.addText);

        //
        helper = new MemoData(getApplicationContext());
        db = helper.getWritableDatabase();
        values = new ContentValues();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_memo_create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.save){
            values.put(MemoData.TITLE, addTitle.getText().toString());
            values.put(MemoData.MEMO, addText.getText().toString());
            db.insert(
                    MemoData.MEMO_TABLE,
                    null,
                    values
            );
            finish();
            return true;
        }else if(id==android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}

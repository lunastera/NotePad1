package com.example.era.notepad;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


public class MemoDetailActivity extends ActionBarActivity {
    private SQLiteDatabase db;
    private MemoData helper;
    private ContentValues values;
    private EditText saveTitle;
    private EditText saveText;
    public static String memoid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_detail);

        saveTitle = (EditText)findViewById(R.id.saveTitle);
        saveText = (EditText)findViewById(R.id.saveText);

        helper = new MemoData(getApplicationContext());
        values = new ContentValues();
        db = helper.getWritableDatabase();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_memo_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.save){
            values.put(MemoData.TITLE,saveTitle.getText().toString());
            values.put(MemoData.MEMO,saveText.getText().toString());
            db.update(
                    MemoData.MEMO_TABLE,
                    values,
                    MemoData.ID+"="+memoid,
                    null
            );
            finish();
            return true;
        }else if(id==android.R.id.home){
            finish();
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void onResume(){
        super.onResume();
        Intent i = getIntent();
        saveTitle.setText(i.getStringExtra("title"));
        saveText.setText(i.getStringExtra("memo"));
        memoid=i.getStringExtra("id");
    }
}

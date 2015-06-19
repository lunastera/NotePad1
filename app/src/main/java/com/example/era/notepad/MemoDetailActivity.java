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
    EditText saveTitle;
    EditText saveText;

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
                    null,null
            );
            Intent i = new Intent(MemoDetailActivity.this,MainActivity.class);
            startActivity(i);
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void OnResume(){
        super.onResume();
        Intent i = getIntent();
        saveTitle.setText(i.getStringExtra("title"));
        saveText.setText(i.getStringExtra("memo"));
    }
}

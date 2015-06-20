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
    //メモID照合用
    public static String memoId;

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
            // メモタイトルを保存するための箱に入れる
            values.put(MemoData.TITLE,saveTitle.getText().toString());
            // メモ内容を保存するための箱に入れる
            values.put(MemoData.MEMO,saveText.getText().toString());
            //データベースの更新　第一引数がテーブル名　第二引数が値　第3,4引数が条件指定
            db.update(
                    MemoData.MEMO_TABLE,
                    values,
                    MemoData.ID+"="+memoId,
                    null
            );
            //メインに帰る
            finish();
            return true;
        }else if(id==android.R.id.home){
            //ホーム押されたら画面終了
            finish();
        }

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void onResume(){
        super.onResume();
        Intent i = getIntent();
        //それぞれ送られてきたデータをセット、格納する。
        saveTitle.setText(i.getStringExtra("title"));
        saveText.setText(i.getStringExtra("memo"));
        memoId=i.getStringExtra("id");
    }
}

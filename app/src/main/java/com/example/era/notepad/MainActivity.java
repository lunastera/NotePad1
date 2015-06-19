package com.example.era.notepad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Intent;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    //データベースを扱う準備
    private SQLiteDatabase db;
    private MemoData helper;
    //リストビューにデータをセットする準備
    private ArrayList<String> arr;
    private ListView list;
    private ArrayAdapter<String> adapter;
    //データベースの参照位置を指定するカーソル
    private Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //listの関連付け
        list=(ListView)findViewById(R.id.list);

        //MemoDataインスタンス化
        helper = new MemoData(getApplicationContext());

        //MemoDataを読み書き可能で取得
        db = helper.getWritableDatabase();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.add){
            //メニュークリックで移動
            Intent i =new Intent(MainActivity.this,MemoCreateActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void setAdapter(){
        //一覧として表示するタイトルを格納するリスト
        arr = new ArrayList<>();
        //タイトルだけを全て抽出するクエリの発行
        c = db.query(
                MemoData.MEMO_TABLE,
                new String[]{MemoData.TITLE,MemoData.MEMO},
                null,null,null,null,null
        );

        //カーソルが最後に到達するまで（データベースの最後まで）ループ
        while(c.moveToNext()){
            //カーソルのいる場所にあるタイトルをListに格納
            arr.add(c.getString(c.getColumnIndexOrThrow(MemoData.TITLE)));
        }

        //リストビューにつけるアダプターの設定
        //上のループでできたデータをセット
        adapter = new ArrayAdapter<> (
                getApplicationContext(),
                R.layout.list_row,
                arr
        );

        list.setAdapter(adapter);

    }

    //他の画面から帰ってきたときに呼ばれるメソッド
    //onCreate　は最初に作成された１回のみ
    public void onResume(){
        super.onResume();
        setAdapter();       //さっきのsetAdapterメソッドを呼び出す
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                c.moveToPosition(position);
                Intent i = new Intent(MainActivity.this, MemoDetailActivity.class);
                i.putExtra("title", c.getString(c.getColumnIndex(MemoData.TITLE)));
                i.putExtra("memo", c.getString(c.getColumnIndex(MemoData.MEMO)));
                startActivity(i);
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                c.moveToPosition(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("メモの削除");
                builder.setMessage("このメモを削除してよろしいですか？");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.delete(
                                MemoData.MEMO_TABLE,
                                MemoData.ID,
                                new String[]{c.getString(c.getColumnIndex(MemoData.ID))}
                        );
                        setAdapter();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create().show();
                return true;
            }
        });
    }
}
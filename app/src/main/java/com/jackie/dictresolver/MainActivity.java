package com.jackie.dictresolver;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etWord;
    EditText etKey;
    EditText etDetail;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etWord = (EditText) findViewById(R.id.etWord);
        etDetail = (EditText) findViewById(R.id.etDetail);
        etKey = (EditText) findViewById(R.id.etKey);
        tvResult = (TextView) findViewById(R.id.tvResult);
        findViewById(R.id.btnAdd).setOnClickListener(this);
        findViewById(R.id.btnQuery).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ContentResolver contentResolver = getContentResolver();
        String word = etWord.getText().toString();
        String detail = etDetail.getText().toString();
        String key = etKey.getText().toString();
        switch (v.getId()) {
            case R.id.btnAdd:
                ContentValues values = new ContentValues();
                values.put(DictUtils.Word.WORD, word);
                values.put(DictUtils.Word.DETAIL, detail);
                contentResolver.insert(DictUtils.Word.WORD_DIR, values);
                Toast.makeText(MainActivity.this, "添加生词成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnQuery:
                Cursor cursor = contentResolver.query(
                        DictUtils.Word.WORD_DIR, null,
                        "word like ? or detail like ?",
                        new String[]{"%" + key+"%", "%" + key+"%"},
                        null);
                StringBuilder sb = new StringBuilder();
                while (cursor.moveToNext()) {
                    String result = null;
                    result = "单词："
                            + cursor.getString(cursor.getColumnIndex(DictUtils.Word.WORD))
                            + "\n释义:" + cursor.getString(cursor.getColumnIndex(DictUtils.Word.DETAIL))
                            + "\n";
                    sb.append(result);
                }
                tvResult.setText(sb);

        }

    }
}

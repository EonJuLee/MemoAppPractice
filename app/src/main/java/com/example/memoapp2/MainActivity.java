package com.example.memoapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    EditText editTitle, editUpdate;
    DatabaseHelper myHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        editTitle = (EditText) findViewById(R.id.editTitle);
        editUpdate = (EditText) findViewById(R.id.editUpdate);
        myHelper = new DatabaseHelper(MainActivity.this);

        viewData();
    }

    public void showMessage(String contents) {
        Toast.makeText(MainActivity.this, contents, Toast.LENGTH_SHORT).show();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAdd:
                addData();
                break;
            case R.id.btnUpdate:
                updateData();
                break;
            case R.id.btnDel:
                deleteData();
                break;
        }
        viewData();
    }

    public void addData() {
        String title = editTitle.getText().toString().trim();
        String id = searchData(title);
        if (!id.equals("")) {
            showMessage("이미 있는 항목입니다");
        } else {
            boolean isInserted = myHelper.insertItem(title);
            if (isInserted == true)
                showMessage("추가 완료");
            else
                showMessage("추가 실패");
        }
    }

    public void viewData() {
        Cursor cursor = myHelper.getAllData();
        if (cursor.getCount() == 0) {
            showMessage("데이터 없습니다");
        } else {
            StringBuffer buffer = new StringBuffer();
            while (cursor.moveToNext()) {
                buffer.append("ID : " + cursor.getString(0) + "\n");
                buffer.append("Title : " + cursor.getString(1) + "\n");
                buffer.append("Done : " + cursor.getString(2) + "\n");
            }
            textView.setText(buffer.toString());
        }
    }

    public String searchData(String title) {
        Cursor cursor = myHelper.getAllData();
        if (cursor.getCount() == 0) {
            return "";
        } else {
            while (cursor.moveToNext()) {
                if (cursor.getString(1).equals(title)) {
                    return cursor.getString(0);
                }
            }
        }
        return "";
    }

    public void updateData() {
        String title = editTitle.getText().toString().trim();
        String mtitle = editUpdate.getText().toString().trim();
        String id = searchData(title);
        String mid = searchData(mtitle);
        if (id.equals("")) {
            showMessage("해당 제목이 없습니다");
        } else if (!mid.equals("")) {
            showMessage("이미 있는 항목입니다");
        } else {
            boolean isUpdated = myHelper.updateItem(id, mtitle);
            if (isUpdated == true) {
                showMessage("변경 성공");
            } else {
                showMessage("변경 실패");
            }
        }
    }

    public void deleteData() {
        String title = editTitle.getText().toString().trim();
        String id = searchData(title);
        if (id.equals("")) {
            showMessage("해당 제목이 없습니다");
        }
        else {
            boolean isDeleted = myHelper.deleteItem(id);
            if(isDeleted==true){
                showMessage("삭제 성공");
            }
            else{
                showMessage("삭제 실패");
            }
        }
    }
}

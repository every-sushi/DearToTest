package com.ryungna.please;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class newMyDiary extends AppCompatActivity {
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd ");
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_my_diary);

        editText = (EditText) findViewById(R.id.DateText);
        editText.setText(getTime());

        //데이터베이스 저장
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference myRef=database.getReference("user");

        final Button btn = (Button)findViewById(R.id.inputfirebasebt);
        final EditText et1,et2;
        et1 = (EditText)findViewById(R.id.titleText);//일기제목
        et2 = (EditText)findViewById(R.id.contentbox);//일기내용
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                myRef.child("user").child("Groups").child("GroupDiary").child("Subject").setValue( et1.getText().toString());// 텍스트박스에 입력한 string값이 들어가야함
                myRef.child("user").child("Groups").child("GroupDiary").child("Contents").setValue( et2.getText().toString());// 텍스트박스에 입력한 string값
            }
        });

    }


    private String getTime() {
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }
}

package com.example.jesskim.finaltermproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class UpdateActivity extends AppCompatActivity {

    // 객체 선언
    TextView tv_current;
    Button btn_finish;
    Button btn_back1;

    EditText et_memo;
    EditText et_title;

    CheckBox cb_travel;
    CheckBox cb_meal;
    CheckBox cb_idea;
    CheckBox cb_rest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // Database 생성 ( DBManager.java로 DB관리 )
        final DBManager dbManager = new DBManager(getApplicationContext(), "Memo.db", null, 1);

        final double[] place;
        tv_current = (TextView) findViewById(R.id.tv_current);
        btn_finish = (Button) findViewById(R.id.btn_finish);
        btn_back1 = (Button) findViewById(R.id.btn_back1);
        et_memo = (EditText) findViewById(R.id.et_memo);
        et_title = (EditText) findViewById(R.id.et_title);

        cb_travel = (CheckBox) findViewById(R.id.cb_travel);
        cb_meal = (CheckBox) findViewById(R.id.cb_meal);
        cb_idea = (CheckBox) findViewById(R.id.cb_idea);
        cb_rest = (CheckBox) findViewById(R.id.cb_rest);


        Intent intent = getIntent();                            // 전달된 인텐트
        place = intent.getDoubleArrayExtra("Place");           // MainActivity에서 lng, lat 전달 받기

        tv_current.setText(place[0] + "\n" + place[1]);

        // 등록 완료 -> DB에 저장 ( 위치 기반 )
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = et_title.getText().toString();
                String today = today();
                String kind = kindData();
                String memo = et_memo.getText().toString();

                dbManager.insert("insert into MEMO_LIST values(null, '" + title + "', '" + today + "', '" + kind + "', '"
                        + memo + "', "
                        + place[0]
                        + ", "
                        + place[1]
                        + ");");

                Toast.makeText(getApplicationContext(), "저장 완료", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        // 등록 취소 -> 다시 MainActivity로 이동
        btn_back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // CheckBox 확인
    public String kindData() {
        if(cb_travel.isChecked())
            return "여행";
        else if(cb_meal.isChecked())
            return "맛집";
        else if(cb_idea.isChecked())
            return "아이디어";
        else
            return "기타";
    }

    // 오늘의 년, 월, 일, 시간 구하기
    public String today() {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        int year = cal.get(cal.YEAR);
        int month = cal.get(cal.MONTH) + 1;
        int date = cal.get(cal.DATE);

        int hour = cal.get(cal.HOUR_OF_DAY);
        int min = cal.get(cal.MINUTE);

        return year + "/" + month + "/" + date + " " + hour + ":" + min;
    }
}

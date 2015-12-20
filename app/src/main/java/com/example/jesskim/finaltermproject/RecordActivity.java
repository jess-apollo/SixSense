package com.example.jesskim.finaltermproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RecordActivity extends AppCompatActivity {

    // 객체 선언
    Button btn_all;
    Button btn_travel;
    Button btn_meal;
    Button btn_idea;
    Button btn_rest;
    Button btn_back2;

    EditText et_lng;
    EditText et_lat;

    TextView tv_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        // Database 생성 ( DBManager.java로 DB관리 )
        final DBManager dbManager = new DBManager(getApplicationContext(), "Memo.db", null, 1);

        btn_all = (Button) findViewById(R.id.btn_all);
        btn_travel = (Button) findViewById(R.id.btn_travel);
        btn_meal = (Button) findViewById(R.id.btn_meal);
        btn_idea = (Button) findViewById(R.id.btn_idea);
        btn_rest = (Button) findViewById(R.id.btn_rest);
        btn_back2 = (Button) findViewById(R.id.btn_back2);

        tv_data = (TextView) findViewById(R.id.tv_data);

        // 버튼 <전체> 클릭
        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_data.setText(dbManager.PrintData());
            }
        });

        // 버튼 <여행> 클릭
        btn_travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_data.setText(dbManager.PrintKind("여행"));
            }
        });

        // 버튼 <맛집> 클릭
        btn_meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_data.setText(dbManager.PrintKind("맛집"));
            }
        });

        // 버튼 <아이디어> 클릭
        btn_idea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_data.setText(dbManager.PrintKind("아이디어"));
            }
        });

        // 버튼 <기타> 클릭
        btn_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_data.setText(dbManager.PrintKind("기타"));
            }
        });

        // 돌아가기
        btn_back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

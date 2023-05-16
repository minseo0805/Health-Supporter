package com.example.healthsupporter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // current_time_textview는 메인 화면의 시간을 보여줄 TextView의 id. SimpleDateFormat 클래스는 시간을 원하는 형식으로 포맷하는데 사용"HH:mm:ss" 형식
        //Locale.KOREA는 한국 지역을 나타냄, new Date()는 현재 시각을 나타냄. 이 코드를 onCreate() 메서드 내에서 실행하면 액티비티가 생성될 때 현재 시각이 textView에 표시
        //System.currentTimeMillis() 메소드를 사용하여 현재 시간을 가져옴, System.currentTimeMillis()는 1970년 1월 1일부터 현재까지의 시간을 밀리초(ms) 단위로 반환
        TextView currentTime = findViewById(R.id.currentTime);
        long currentMillis = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA);
        String currentDateTime = dateFormat.format(new Date(currentMillis));
        currentTime.setText(currentDateTime);



        ImageButton imageButton = (ImageButton) findViewById(R.id.img_alarm);
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), timer.class);
                startActivity(intent);
            }
        });

        ImageButton img_goal = (ImageButton) findViewById(R.id.img_goal);
        img_goal.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Mypage.class);
                startActivity(intent);


            }
        });

        ImageButton img_recommend = (ImageButton) findViewById(R.id.img_recommend);
        img_recommend.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), add.class);
                startActivity(intent);



            }
        });




    }
}
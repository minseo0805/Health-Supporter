package com.example.healthsupporter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class add extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ImageButton imageButton = (ImageButton) findViewById(R.id.img_plus);
        imageButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Recommend.class);
                startActivity(intent);

            }
        });
        ImageButton img_alarm = (ImageButton) findViewById(R.id.img_alarm);
        img_alarm.setOnClickListener(new View.OnClickListener() {
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


        ImageButton image_main = (ImageButton) findViewById(R.id.img_main);
        image_main.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

}
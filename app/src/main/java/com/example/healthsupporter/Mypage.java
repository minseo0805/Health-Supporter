package com.example.healthsupporter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Mypage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        ImageButton imageButton = (ImageButton) findViewById(R.id.img_alarm);
        imageButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), timer.class);
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
        ImageButton image_recommend = (ImageButton) findViewById(R.id.img_recommend);
        image_recommend.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), add.class);
            startActivity(intent);


        });
    }
}
package com.example.healthsupporter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    private List<Exercise> exerciseList = new ArrayList<>();
    private ExerciseListAdapter exerciseListAdapter;
    private ListView exerciseListView;
    private TextView currentTime;

    private static final String BASE_URL = "http://your-backend-url.com"; // 백엔드의 기본 URL로 수정

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exerciseListView = findViewById(R.id.exerciseListView);
        exerciseListAdapter = new ExerciseListAdapter(this, exerciseList);
        exerciseListView.setAdapter(exerciseListAdapter);

        currentTime = findViewById(R.id.currentTime);
        long currentMillis = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA);
        String currentDateTime = dateFormat.format(new Date(currentMillis));
        currentTime.setText(currentDateTime);

        ImageButton imageButton = findViewById(R.id.img_alarm);
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), timer.class);
                startActivity(intent);
            }
        });

        ImageButton img_goal = findViewById(R.id.img_goal);
        img_goal.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Mypage.class);
                startActivity(intent);
            }
        });

        ImageButton img_recommend = findViewById(R.id.img_recommend);
        img_recommend.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Recommend.class);
                startActivity(intent);
            }
        });

        // 운동 데이터 가져오기
        new ExerciseRequestTask().execute("legs"); // 원하는 운동 부위를 전달하세요
    }

    private class ExerciseRequestTask extends AsyncTask<String, Void, List<Exercise>> {
        @Override
        protected List<Exercise> doInBackground(String... params) {
            //doInBackground 메서드에서 Retrofit을 초기화하고 API 요청을 보냄
            String bodyPart = params[0];
            List<Exercise> exerciseData = new ArrayList<>();
            //Retrofit2는 안드로이드 앱에서 HTTP 클라이언트를 쉽게 구현하기 위한 라이브러리
            //주로 RESTful API와 통신하는 데 사용되며, 서버로부터 데이터를 가져오거나 서버에 데이터를 전송하는 작업을 처리

            try {
                // Retrofit 객체 생성
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                // API 서비스 인터페이스 생성
                ExerciseApiService apiService = retrofit.create(ExerciseApiService.class);

                // API 요청 및 응답 처리
                Call<List<Exercise>> call = apiService.getExerciseData(bodyPart);
                Response<List<Exercise>> response = call.execute();

                if (response.isSuccessful()) {
                    exerciseData = response.body();
                } else {
                    // API 요청이 실패한 경우, 실패 처리를 수행할 수 있습니다.
                }
            } catch (IOException e) {
                e.printStackTrace();
                // 예외 처리를 수행할 수 있습니다.
            }

            return exerciseData;
        }

        @Override
        protected void onPostExecute(List<Exercise> exerciseData) {
            if (exerciseData != null) {
                exerciseList.addAll(exerciseData);
                exerciseListAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(MainActivity.this, "Failed to fetch exercise data", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

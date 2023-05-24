package com.example.healthsupporter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Recommend extends AppCompatActivity implements View.OnClickListener {

    private static final String BASE_URL = "http://your-nodejs-backend-url";// Node.js 백엔드 URL

    // Method to request exercise data from Node.js backend
    private void requestExerciseData(String bodyPart) {
        // Create an instance of AsyncTask to perform the network request in the background
        ExerciseRequestTask task = new ExerciseRequestTask();
        task.execute(bodyPart);
    }

    // AsyncTask to handle the exercise data request
    private class ExerciseRequestTask extends AsyncTask<String, Void, List<String>> {

        @Override
        protected List<String> doInBackground(String... params) {
            String bodyPart = params[0];
            List<String> exerciseData = new ArrayList<>();

            try {
                // Create Retrofit instance
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                // Create API service interface
                ExerciseDataService exerciseDataService = retrofit.create(ExerciseDataService.class);

                // Make API call to get exercise data
                Call<List<String>> call = exerciseDataService.getExerciseData(bodyPart);
                Response<List<String>> response = call.execute();

                if (response.isSuccessful()) {
                    exerciseData = response.body();
                } else {
                    // Handle error response
                    exerciseData.add("Failed to get exercise data");
                }
            } catch (IOException e) {
                e.printStackTrace();
                exerciseData.add("Network request failed");
            }

            return exerciseData;
        }



        public void execute(String bodyPart) {

        }
    }


    @Override
    public void onClick(View view) {
        // 다른 뷰들의 클릭 이벤트 처리
        if (view == mondayButton) {
            // 월요일 버튼 클릭 처리
            mondayButton.setSelected(true);
            tuesdayButton.setSelected(false);
            wednesdayButton.setSelected(false);
            thursdayButton.setSelected(false);
            fridayButton.setSelected(false);
            saturdayButton.setSelected(false);
            sundayButton.setSelected(false);
            Toast.makeText(this, "월요일 버튼이 클릭되었습니다", Toast.LENGTH_SHORT).show();
        } else if (view == tuesdayButton) {
            // 화요일 버튼 클릭 처리
            mondayButton.setSelected(false);
            tuesdayButton.setSelected(true);
            wednesdayButton.setSelected(false);
            thursdayButton.setSelected(false);
            fridayButton.setSelected(false);
            saturdayButton.setSelected(false);
            sundayButton.setSelected(false);
            Toast.makeText(this, "화요일 버튼이 클릭되었습니다", Toast.LENGTH_SHORT).show();
        } else if (view == wednesdayButton) {
            // 수요일 버튼 클릭 처리
            mondayButton.setSelected(false);
            tuesdayButton.setSelected(false);
            wednesdayButton.setSelected(true);
            thursdayButton.setSelected(false);
            fridayButton.setSelected(false);
            saturdayButton.setSelected(false);
            sundayButton.setSelected(false);
            Toast.makeText(this, "수요일 버튼이 클릭되었습니다", Toast.LENGTH_SHORT).show();
        } else if (view == thursdayButton) {
            // 목요일 버튼 클릭 처리
            mondayButton.setSelected(false);
            tuesdayButton.setSelected(false);
            wednesdayButton.setSelected(false);
            thursdayButton.setSelected(true);
            fridayButton.setSelected(false);
            saturdayButton.setSelected(false);
            sundayButton.setSelected(false);
            Toast.makeText(this, "목요일 버튼이 클릭되었습니다", Toast.LENGTH_SHORT).show();
        } else if (view == fridayButton) {
            // 금요일 버튼 클릭 처리
            mondayButton.setSelected(false);
            tuesdayButton.setSelected(false);
            wednesdayButton.setSelected(false);
            thursdayButton.setSelected(false);
            fridayButton.setSelected(true);
            saturdayButton.setSelected(false);
            sundayButton.setSelected(false);
            Toast.makeText(this, "금요일 버튼이 클릭되었습니다", Toast.LENGTH_SHORT).show();
        } else if (view == saturdayButton) {
            // 토요일 버튼 클릭 처리
            mondayButton.setSelected(false);
            tuesdayButton.setSelected(false);
            wednesdayButton.setSelected(false);
            thursdayButton.setSelected(false);
            fridayButton.setSelected(false);
            saturdayButton.setSelected(true);
            sundayButton.setSelected(false);
            Toast.makeText(this, "토요일 버튼이 클릭되었습니다", Toast.LENGTH_SHORT).show();
        } else if (view == sundayButton) {
            // 일요일 버튼 클릭 처리
            mondayButton.setSelected(false);
            tuesdayButton.setSelected(false);
            wednesdayButton.setSelected(false);
            thursdayButton.setSelected(false);
            fridayButton.setSelected(false);
            saturdayButton.setSelected(false);
            sundayButton.setSelected(true);
            Toast.makeText(this, "일요일 버튼이 클릭되었습니다", Toast.LENGTH_SHORT).show();
        } else if (view == addButton) {
            Toast.makeText(this, "해당 운동이 추가 되었습니다", Toast.LENGTH_SHORT).show();
            // 추가 버튼 클릭 처리
        } else if (view == cancelButton) {
            Toast.makeText(this, "해당 운동이 취소 되었습니다", Toast.LENGTH_SHORT).show();
            // 취소 버튼 클릭 처리

        }
    }

    private Button mondayButton, tuesdayButton, wednesdayButton, thursdayButton, fridayButton, saturdayButton, sundayButton;
    private CheckBox chestCheckbox, backCheckbox, shouldersCheckbox, legsCheckbox, absCheckbox, armsCheckbox;
    private Spinner exerciseSpinner, setsSpinner;
    private Button addButton, cancelButton;

    private List<String> dayList;
    private List<String> bodyPartList;
    private List<String> selectedExercises;
    private int sets;
    private List<Exercise> exerciseList;
//    private ExerciseListAdapter exerciseListAdapter;

    // Method to perform actions specific to the selected body part
    private void performBodyPartAction(String bodyPart) {
        // Request exercise data from Node.js backend
        // Retrofit 인스턴스 생성
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // ExerciseDataService 인터페이스 생성
        ExerciseDataService exerciseDataService = retrofit.create(ExerciseDataService.class);

        // 운동 데이터를 요청하는 API 호출
        Call<List<String>> call = exerciseDataService.getExerciseData(bodyPart);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> exerciseData = response.body();
                    // 운동 데이터를 사용하여 UI를 업데이트하는 작업을 수행합니다.
                    // 예: exerciseData를 ListView에 표시하거나, Spinner에 데이터를 설정하는 등
                } else {
                    // API 호출이 실패한 경우
                    Toast.makeText(Recommend.this, "운동 데이터를 가져오지 못했습니다", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
            // 네트워크 요청이 실패한 경우
            Toast.makeText(Recommend.this, "네트워크 요청에 실패했습니다", Toast.LENGTH_SHORT).show();
            }
        });
}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        // Initialize views
        mondayButton = findViewById(R.id.monday_button);
        tuesdayButton = findViewById(R.id.tuesday_button);
        wednesdayButton = findViewById(R.id.wednesday_button);
        thursdayButton = findViewById(R.id.thursday_button);
        fridayButton = findViewById(R.id.friday_button);
        saturdayButton = findViewById(R.id.saturday_button);
        sundayButton = findViewById(R.id.sunday_button);

        // Initialize buttons
        addButton = findViewById(R.id.add_button);
        cancelButton = findViewById(R.id.cancle_button);

        chestCheckbox = findViewById(R.id.chk_chest);
        backCheckbox = findViewById(R.id.chk_back);
        shouldersCheckbox = findViewById(R.id.chk_shoulder);
        legsCheckbox = findViewById(R.id.chk_leg);
        absCheckbox = findViewById(R.id.chk_abs);
        armsCheckbox = findViewById(R.id.chk_arm);

        exerciseSpinner = findViewById(R.id.exercise_spinner);
        setsSpinner = findViewById(R.id.sets_spinner);

        ListView exerciseListView = findViewById(R.id.exerciseListView);

        // Set click listeners
        mondayButton.setOnClickListener(this);
        tuesdayButton.setOnClickListener(this);
        wednesdayButton.setOnClickListener(this);
        thursdayButton.setOnClickListener(this);
        fridayButton.setOnClickListener(this);
        saturdayButton.setOnClickListener(this);
        sundayButton.setOnClickListener(this);

        addButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);


        // Initialize lists
        dayList = new ArrayList<>();
        bodyPartList = new ArrayList<>();
        selectedExercises = new ArrayList<>();

        // Setup exercise spinner
        ArrayAdapter<CharSequence> exerciseAdapter = ArrayAdapter.createFromResource(this,
                R.array.exercises_array, android.R.layout.simple_spinner_item);
        exerciseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exerciseSpinner.setAdapter(exerciseAdapter);

        // Setup sets spinner
        ArrayAdapter<CharSequence> setsAdapter = ArrayAdapter.createFromResource(this,
                R.array.sets_array, android.R.layout.simple_spinner_item);
        setsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setsSpinner.setAdapter(setsAdapter);

        // Perform body part action and request exercise data for the initial selected body part
        performBodyPartAction("Chest"); // 예시로 "Chest"를 선택한 상태로 초기화합니다. 원하는 부위로 변경해주세요.


        // 체크박스의 OnCheckedChangeListener를 초기화합니다
        CompoundButton.OnCheckedChangeListener bodyPartCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // 다른 부위별 체크박스의 선택을 해제합니다
                    chestCheckbox.setChecked(compoundButton == chestCheckbox);
                    backCheckbox.setChecked(compoundButton == backCheckbox);
                    shouldersCheckbox.setChecked(compoundButton == shouldersCheckbox);
                    legsCheckbox.setChecked(compoundButton == legsCheckbox);
                    absCheckbox.setChecked(compoundButton == absCheckbox);
                    armsCheckbox.setChecked(compoundButton == armsCheckbox);

                    String bodyPart = compoundButton.getText().toString();
                    bodyPartList.clear();
                    bodyPartList.add(bodyPart);
                    // 선택된 부위에 대한 동작을 수행하는 메서드를 호출합니다
                    performBodyPartAction(bodyPart);
                } else {
                    // 부위별 체크박스의 선택이 해제되었습니다
                    bodyPartList.remove(compoundButton.getText().toString());
                }
            }
        };

        // 각 부위별 체크박스에 OnCheckedChangeListener를 설정합니다
        chestCheckbox.setOnCheckedChangeListener(bodyPartCheckedChangeListener);
        backCheckbox.setOnCheckedChangeListener(bodyPartCheckedChangeListener);
        shouldersCheckbox.setOnCheckedChangeListener(bodyPartCheckedChangeListener);
        legsCheckbox.setOnCheckedChangeListener(bodyPartCheckedChangeListener);
        absCheckbox.setOnCheckedChangeListener(bodyPartCheckedChangeListener);
        armsCheckbox.setOnCheckedChangeListener(bodyPartCheckedChangeListener);
    }
}


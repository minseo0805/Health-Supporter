package com.example.healthsupporter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

    public class Recommend  extends AppCompatActivity {
        // 각 요일 버튼과 부위 체크박스를 저장할 리스트
        private List<Button> dayButtons = new ArrayList<>();
        //dayButtons: 각 요일 버튼을 저장하는 ArrayList
        private List<CheckBox> partCheckBoxes = new ArrayList<>();
        //partCheckBoxes: 각 부위 체크박스를 저장하는 ArrayList

        // 선택된 요일과 부위
        private String selectedDay = "";
        //selectedDay: 선택된 요일을 저장하는 String
        private List<String> selectedParts = new ArrayList<>();
        //selectedParts: 선택된 부위들을 저장하는 ArrayList

        // 각 부위별 운동 종목과 세트수
        private String[][] exerciseSets = {
                //exerciseSets: 각 부위별 운동 종목과 세트수를 저장하는 2차원 String 배열
                {"벤치프레스", "인클라인 벤치프레스", "딥스", "체스트프레스", "푸쉬업", "덤벨플라이"},
                {"랫풀다운", "풀업", "바벨로우", "시티드로우", "원암로우", "덤벨로우"},
                {"숄더프레스", "사이드 레터럴 레이즈", "프론트 레이즈", "벤트오버 레터럴 레이즈", "리어 델트 플라이", "케이블 페이스 풀"},
                {"스쿼트", "레그프레스", "런지", "스텝업", "레그익스텐션", "레그컬"},
                {"크런치", "리버스 크런치", "플랭크", "사이드 플랭크", "레그 레이즈", "버피"},
                {"바이셉 컬", "해머컬", "케이블 컬", "컨센트레이션 컬", "푸쉬업", "트라이셉스 익스텐션"}
        };
        private String[] selectedExercises = new String[6]; //selectedExercises: 선택된 운동 종목을 저장하는 String 배열
        private int[] selectedSets = new int[6]; //selectedSets: 선택된 세트수를 저장하는 int 배열

        // 메인화면에 추가된 운동 종목과 세트수를 저장할 리스트
        private List<String> addedExercises = new ArrayList<>(); //addedExercises: 메인화면에 추가된 운동 종목을 저장하는 ArrayList
        private List<Integer> addedSets = new ArrayList<>(); //addedSets: 메인화면에 추가된 세트수를 저장하는 ArrayList

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_recommend);

            // xml 파일에서 요일 버튼과 부위 체크박스를 찾아서 리스트에 추가
            dayButtons.add((Button) findViewById(R.id.monday_button));
            dayButtons.add((Button) findViewById(R.id.tuesday_button));
            dayButtons.add((Button) findViewById(R.id.wednesday_button));
            dayButtons.add((Button) findViewById(R.id.thursday_button));
            dayButtons.add((Button) findViewById(R.id.friday_button));
            dayButtons.add((Button) findViewById(R.id.saturday_button));
            dayButtons.add((Button) findViewById(R.id.sunday_button));

            partCheckBoxes.add((CheckBox) findViewById(R.id.chk_chest));
            partCheckBoxes.add((CheckBox) findViewById(R.id.chk_back));
            partCheckBoxes.add((CheckBox) findViewById(R.id.chk_shoulder));
            partCheckBoxes.add((CheckBox) findViewById(R.id.chk_leg));
            partCheckBoxes.add((CheckBox) findViewById(R.id.chk_abs));
            partCheckBoxes.add((CheckBox) findViewById(R.id.chk_arm));

            // 요일 버튼에 클릭 이벤트 추가
            for (Button button : dayButtons) {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 클릭된 버튼의 텍스트를 가져와서 selectedDay에 저장
                        selectedDay = button.getText().toString();
                        Toast.makeText(Recommend.this, selectedDay + " 선택됨", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            // 부위 체크박스에 체크 이벤트 추가
            for (CheckBox checkBox : partCheckBoxes) {
                checkBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        // 체크 상태에 따라 selectedParts 리스트에 추가 또는 제거
                        String part = checkBox.getText().toString();
                        if (isChecked) {
                            selectedParts.add(part);
                            Toast.makeText(Recommend.this, part + " 선택됨", Toast.LENGTH_SHORT).show();
                        } else {
                            selectedParts.remove(part);
                            Toast.makeText(Recommend.this, part + " 선택 해제됨", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            // 운동 종목 선택 스피너 설정
            Spinner exerciseSpinner = findViewById(R.id.exercise_spinner); //findViewById() 메서드를 사용하여 레이아웃에서 정의한 스피너 뷰를 가져옴
            // ArrayAdapter 클래스를 사용하여 운동 종목 선택 스피너와 세트수 선택 스피너에 대한 어댑터를 생성
            ArrayAdapter<String> exerciseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{});
            exerciseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            exerciseSpinner.setAdapter(exerciseAdapter);

            // 세트수 선택 스피너 설정
            Spinner setsSpinner = findViewById(R.id.sets_spinner);
            ArrayAdapter<Integer> setsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new Integer[]{});
            setsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            setsSpinner.setAdapter(setsAdapter);

            // 운동 추가 버튼에 클릭 이벤트 추가
            Button addButton = findViewById(R.id.add_button);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 운동 종목과 세트수를 선택하지 않았을 경우 메시지 출력
                    if (exerciseSpinner.getSelectedItemPosition() == 0 || setsSpinner.getSelectedItemPosition() == 0) {
                        Toast.makeText(Recommend.this, "운동 종목과 세트수를 선택하세요", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // 선택된 운동 종목과 세트수를 addedExercises와 addedSets 리스트에 추가
                    String exercise = exerciseSpinner.getSelectedItem().toString();
                    int sets = (int) setsSpinner.getSelectedItem();
                    addedExercises.add(exercise);
                    addedSets.add(sets);

                    // 선택된 운동 종목과 세트수를 토스트 메시지로 출력하고 스피너 초기화
                    Toast.makeText(Recommend.this, exercise + " " + sets + " 세트 추가됨", Toast.LENGTH_SHORT).show();
                    exerciseSpinner.setSelection(0);
                    setsSpinner.setSelection(0);
                }
            });
        }

        // 저장 버튼에 클릭 이벤트 추가
        public void onSaveButtonClick(View view) {
// 요일, 선택된 부위, 추가된 운동 종목과 세트수가 모두 존재해야 저장 가능
            if (selectedDay == null || selectedParts.isEmpty() || addedExercises.isEmpty() || addedSets.isEmpty()) {
                Toast.makeText(this, "모든 항목을 선택하세요", Toast.LENGTH_SHORT).show();
                return;
            }

            // 선택된 요일과 부위, 추가된 운동 종목과 세트수를 저장
            String parts = TextUtils.join(",", selectedParts);
            String exercises = TextUtils.join(",", addedExercises);
            String sets = TextUtils.join(",", addedSets);

            // SharedPreferences를 이용해 저장된 운동 기록 가져오기
            SharedPreferences sharedPreferences = getSharedPreferences("workout_record", MODE_PRIVATE);
            String savedRecord = sharedPreferences.getString(selectedDay, "");

            // 새로운 운동 기록을 생성하고 SharedPreferences에 저장
            String newRecord = parts + "|" + exercises + "|" + sets;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(selectedDay, savedRecord + "\n" + newRecord);
            editor.apply();

            // 저장 완료 메시지 출력 후 액티비티 종료
            Toast.makeText(this, selectedDay + " 운동 기록 저장 완료", Toast.LENGTH_SHORT).show();
            finish();


        }
    }

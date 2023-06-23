package com.example.healthsupporter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recommend extends AppCompatActivity implements View.OnClickListener {

    private static final String BASE_URL = "http://210.123.255.113:3000/exercise/info";// Node.js 백엔드 URL

    // Declare exerciseContentMap as a member variable
    private Map<String, String> exerciseContentMap = new HashMap<>();
    private List<String> exerciseNames = new ArrayList<>();



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
            Toast.makeText(this, "운동이 추가되었습니다", Toast.LENGTH_SHORT).show();
            // Get the selected body part checkboxes
            boolean chkChest = chestCheckbox.isChecked();
            boolean chkBack = backCheckbox.isChecked();
            boolean chkShoulder = shouldersCheckbox.isChecked();
            boolean chkLeg = legsCheckbox.isChecked();
            boolean chkAbs = absCheckbox.isChecked();
            boolean chkArm = armsCheckbox.isChecked();




            ExerciseRequest exerciseRequest = new ExerciseRequest(chkChest, chkBack, chkShoulder, chkLeg, chkAbs, chkArm,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                String state = jsonResponse.getString("state");

                                if (state.equals("success")) {
                                    JSONArray messageArray = jsonResponse.getJSONArray("message");

                                    for (int i = 0; i < messageArray.length(); i++) {
                                        JSONObject exerciseObj = messageArray.getJSONObject(i);
                                        String content = exerciseObj.getString("content");
                                        exerciseNames.add(content); // Update this line

                                    }


                                    ArrayAdapter<String> exerciseAdapter = new ArrayAdapter<>(Recommend.this,
                                            android.R.layout.simple_spinner_item, exerciseNames);
                                    exerciseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    exerciseSpinner.setAdapter(exerciseAdapter);

                                    if (!exerciseNames.isEmpty()) {
                                        String initialExercise = exerciseNames.get(0);
                                        textView.setText(exerciseContentMap.get(initialExercise));
                                    }
                                } else {
                                    String errorMessage = jsonResponse.getString("message");
                                    Toast.makeText(Recommend.this, errorMessage, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Recommend.this, "Error retrieving exercise data", Toast.LENGTH_SHORT).show();
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(exerciseRequest);





            // 추가 버튼 클릭 처리
        } else if (view == cancelButton) {
            // Handle Cancel button click
            Toast.makeText(this, "운동이 취소되었습니다", Toast.LENGTH_SHORT).show();

            // Reset checkboxes
            chestCheckbox.setChecked(false);
            backCheckbox.setChecked(false);
            shouldersCheckbox.setChecked(false);
            legsCheckbox.setChecked(false);
            absCheckbox.setChecked(false);
            armsCheckbox.setChecked(false);

            // Clear exercise data
            exerciseNames.clear();
            exerciseContentMap.clear();

            // Clear and update exercise spinner
            ArrayAdapter<String> exerciseAdapter = new ArrayAdapter<>(Recommend.this,
                    android.R.layout.simple_spinner_item, exerciseNames);
            exerciseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            exerciseSpinner.setAdapter(exerciseAdapter);

            if (!exerciseNames.isEmpty()) {
                String initialExercise = exerciseNames.get(0);
                textView.setText(exerciseContentMap.get(initialExercise));
            }
        } else if (view == cancelButton) {
            // Handle Cancel button click
            Toast.makeText(this, "운동이 삭제되었습니다", Toast.LENGTH_SHORT).show();

            // Reset selected exercises
            selectedExercises.clear();

            // Reset text view
            textView.setText("");


        }

        else if (view == registerButton) {
            // Handle Register button click
            Toast.makeText(this, "운동이 등록되었습니다", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Recommend.this, add.class);
            startActivity(intent);

            // Get the selected day
            String selectedDay = "";
            if (mondayButton.isSelected()) {
                selectedDay = "mon";
            } else if (tuesdayButton.isSelected()) {
                selectedDay = "tue";
            } else if (wednesdayButton.isSelected()) {
                selectedDay = "wed";
            } else if (thursdayButton.isSelected()) {
                selectedDay = "thu";
            } else if (fridayButton.isSelected()) {
                selectedDay = "fri";
            } else if (saturdayButton.isSelected()) {
                selectedDay = "sat";
            } else if (sundayButton.isSelected()) {
                selectedDay = "sun";
            }

            // Get the selected exercise
            String selectedExercise = exerciseSpinner.getSelectedItem().toString();
            Log.d("exercise", selectedExercise);

            // Get the sets
            int sets = Integer.parseInt(setsEditText.getText().toString());

            // Get the reps
            int reps = Integer.parseInt(repsEditText.getText().toString());

            // Create a JSON object to hold the data
            JSONObject requestData = new JSONObject();
            try {
                requestData.put("day", selectedDay);
                requestData.put("exercise", selectedExercise);
                requestData.put("sets", sets);
                requestData.put("reps", reps);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Get the token from wherever it is stored
            String token = GlobalVariables.getToken();

            // Set the request headers
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", token);

            // Send the data to the Node.js backend
            // (You need to replace BASE_URL with your actual backend URL)
            String url = "http://210.123.255.113:3000/schedule/add/";
            JsonObjectRequest registerRequest = new JsonObjectRequest(Request.Method.POST, url, requestData,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            // Handle the response from the backend
                            try {
                                String state = response.getString("state");
                                String message = response.getString("message");
                                Log.d("Response", response.toString());
                                if (state.equals("success")) {
                                    Toast.makeText(Recommend.this, message, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Recommend.this, "Registration failed: " + message, Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle the error
                            Toast.makeText(Recommend.this, "Error registering exercise: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return headers;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(registerRequest);
        }

        // Handle star_button click
        if (view == star_Button) {
            // Get the token from wherever it is stored
            String token = GlobalVariables.getToken();

            // Set the request headers
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", token);

            // Send the GET request to the server
            String url = "http://210.123.255.113:3000/schedule/recommand/";
            JsonObjectRequest recommendRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Handle the response from the backend
                            try {
                                String state = response.getString("state");
                                String message = response.getString("message");
                                Log.d("Response", response.toString());
                                if (state.equals("success")) {
                                    if (message.equals("search_failed")) {
                                        // Handle case when no similar exercise found
                                        Toast.makeText(Recommend.this, "유사한 운동 루틴을 가진 사용자가 없습니다", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Process the recommended data here
                                        Toast.makeText(Recommend.this, "추천 운동은: " + message, Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(Recommend.this, "Recommendation failed: " + message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                    };},
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle the error
                            Toast.makeText(Recommend.this, "Error recommending exercises: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return headers;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(recommendRequest);
        }




    }



    private Button mondayButton, tuesdayButton, wednesdayButton, thursdayButton, fridayButton, saturdayButton, sundayButton;
    private CheckBox chestCheckbox, backCheckbox, shouldersCheckbox, legsCheckbox, absCheckbox, armsCheckbox;
    private Spinner exerciseSpinner;
    private Button addButton, cancelButton, registerButton;

    private EditText setsEditText, repsEditText;

    private TextView resultTextView;
    private TextView textView; // textView 변수 추가

    private ImageButton star_Button;

    private List<String> dayList;
    private List<String> bodyPartList;
    private List<String> selectedExercises;
    private int sets;
    private int reps;




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
        star_Button = findViewById(R.id.star_button);
        registerButton = findViewById(R.id.register_button);

        chestCheckbox = findViewById(R.id.chk_chest);
        backCheckbox = findViewById(R.id.chk_back);
        shouldersCheckbox = findViewById(R.id.chk_shoulder);
        legsCheckbox = findViewById(R.id.chk_leg);
        absCheckbox = findViewById(R.id.chk_abs);
        armsCheckbox = findViewById(R.id.chk_arm);

        exerciseSpinner = findViewById(R.id.exercise_spinner);
        setsEditText = findViewById(R.id.setsEditText);
        repsEditText = findViewById(R.id.repsEditText);


        textView = findViewById(R.id.textView); // textView 변수 초기화

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
        star_Button.setOnClickListener(this);
        registerButton.setOnClickListener(this);




        // Initialize lists
        dayList = new ArrayList<>();
        bodyPartList = new ArrayList<>();
        selectedExercises = new ArrayList<>();

        // Setup exercise spinner
        ArrayAdapter<CharSequence> exerciseAdapter = ArrayAdapter.createFromResource(this,
                R.array.exercises_array, android.R.layout.simple_spinner_item);
        exerciseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exerciseSpinner.setAdapter(exerciseAdapter);




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



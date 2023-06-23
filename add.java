package com.example.healthsupporter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

import java.util.HashMap;
import java.util.Map;

public class add extends AppCompatActivity {

    private TextView addedExerciseMon;
    private TextView addedExerciseTue;
    private TextView addedExerciseWed;
    private TextView addedExerciseThu;
    private TextView addedExerciseFri;
    private TextView addedExerciseSat;
    private TextView addedExerciseSun;

    private Button monDeleteButton;
    private Button tueDeleteButton;
    private Button wedDeleteButton;
    private Button thuDeleteButton;
    private Button friDeleteButton;
    private Button satDeleteButton;
    private Button sunDeleteButton;

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        addedExerciseMon = findViewById(R.id.addedExerciseMon);
        addedExerciseTue = findViewById(R.id.addedExerciseTue);
        addedExerciseWed = findViewById(R.id.addedExerciseWen);
        addedExerciseThu = findViewById(R.id.addedExerciseThur);
        addedExerciseFri = findViewById(R.id.addedExerciseFri);
        addedExerciseSat = findViewById(R.id.addedExerciseSat);
        addedExerciseSun = findViewById(R.id.addedExerciseSun);

        monDeleteButton = findViewById(R.id.Mondel_Btn);
        tueDeleteButton = findViewById(R.id.Tuedel_Btn);
        wedDeleteButton = findViewById(R.id.Wendel_Btn);
        thuDeleteButton = findViewById(R.id.Thurdel_Btn);
        friDeleteButton = findViewById(R.id.Fridel_Btn);
        satDeleteButton = findViewById(R.id.Satdel_Btn);
        sunDeleteButton = findViewById(R.id.Sundel_Btn);

        ImageButton img_goal = findViewById(R.id.img_goal);
        img_goal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Mypage.class);
                startActivity(intent);
            }
        });

        ImageButton img_plus = findViewById(R.id.img_plus);
        img_plus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Recommend.class);
                startActivity(intent);
            }
        });

        ImageButton imageButton = findViewById(R.id.img_alarm);
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), timer.class);
                startActivity(intent);
            }
        });

        ImageButton image_main = findViewById(R.id.img_main);
        image_main.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        sendRequest();


        // Get the token from wherever it is stored
        token = GlobalVariables.getToken();


        // Set click listeners for delete buttons
        monDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String day = "mon";
                String exercise = addedExerciseMon.getText().toString().trim();
                if (!exercise.isEmpty()) {
                    deleteExercise(day);
                }
            }
        });

        tueDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String day = "tue";
                String exercise = addedExerciseTue.getText().toString().trim();
                if (!exercise.isEmpty()) {
                    deleteExercise(day);
                }
            }
        });

        wedDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String day = "wed";
                String exercise = addedExerciseWed.getText().toString().trim();
                if (!exercise.isEmpty()) {
                    deleteExercise(day);
                }
            }
        });

        thuDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String day = "thu";
                String exercise = addedExerciseThu.getText().toString().trim();
                if (!exercise.isEmpty()) {
                    deleteExercise(day);
                }
            }
        });

        friDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String day = "fri";
                String exercise = addedExerciseFri.getText().toString().trim();
                if (!exercise.isEmpty()) {
                    deleteExercise(day);
                }
            }
        });

        satDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String day = "sat";
                String exercise = addedExerciseSat.getText().toString().trim();
                if (!exercise.isEmpty()) {
                    deleteExercise(day);;
                }
            }
        });

        sunDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String day = "sun";
                String exercise = addedExerciseSun.getText().toString().trim();
                if (!exercise.isEmpty()) {
                    deleteExercise(day);
                }
            }
        });
    }

    private void sendRequest() {
        String tokenUrl = "http://210.123.255.113:3000/schedule/info";
        JsonObjectRequest tokenRequest = new JsonObjectRequest(Request.Method.GET, tokenUrl, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String state = response.getString("state");
                            if (state.equals("success")) {
                                JSONArray exerciseArray = response.getJSONArray("message");
                                setupPage(exerciseArray);  // Pass the exercise data to the setupPage method
                            } else {
                                String errorMessage = response.getString("message");
                                Toast.makeText(add.this, "Failed to retrieve exercise data: " + errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(add.this, "Error retrieving exercise data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", token); // Include the token in the headers
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(tokenRequest);
    }


    private void requestToken() {
        sendRequest();
    }


    private void setupPage(JSONArray exerciseArray) {
        // Recommend 클래스로부터 추가된 운동 데이터 전달 받기
        Intent intent = getIntent();
        String addedExerciseMonText = intent.getStringExtra("addedExerciseMon");
        String addedExerciseTueText = intent.getStringExtra("addedExerciseTue");
        String addedExerciseWedText = intent.getStringExtra("addedExerciseWed");
        String addedExerciseThuText = intent.getStringExtra("addedExerciseThu");
        String addedExerciseFriText = intent.getStringExtra("addedExerciseFri");
        String addedExerciseSatText = intent.getStringExtra("addedExerciseSat");
        String addedExerciseSunText = intent.getStringExtra("addedExerciseSun");

        // 변수 초기화
        addedExerciseMonText = (addedExerciseMonText != null) ? addedExerciseMonText : "";
        addedExerciseTueText = (addedExerciseTueText != null) ? addedExerciseTueText : "";
        addedExerciseWedText = (addedExerciseWedText != null) ? addedExerciseWedText : "";
        addedExerciseThuText = (addedExerciseThuText != null) ? addedExerciseThuText : "";
        addedExerciseFriText = (addedExerciseFriText != null) ? addedExerciseFriText : "";
        addedExerciseSatText = (addedExerciseSatText != null) ? addedExerciseSatText : "";
        addedExerciseSunText = (addedExerciseSunText != null) ? addedExerciseSunText : "";

        // 운동 데이터 표시
        addedExerciseMon.setText(addedExerciseMonText);
        addedExerciseTue.setText(addedExerciseTueText);
        addedExerciseWed.setText(addedExerciseWedText);
        addedExerciseThu.setText(addedExerciseThuText);
        addedExerciseFri.setText(addedExerciseFriText);
        addedExerciseSat.setText(addedExerciseSatText);
        addedExerciseSun.setText(addedExerciseSunText);

        // Set the exercise data from the response
        for (int i = 0; i < exerciseArray.length(); i++) {
            try {
                JSONObject exerciseObj = exerciseArray.getJSONObject(i);
                String day = exerciseObj.getString("day");
                String exercise = exerciseObj.getString("exercise");

                // Append the exercise text to the respective TextView based on the day
                switch (day) {
                    case "mon":
                        if (!addedExerciseMonText.isEmpty()) {
                            addedExerciseMonText += ", ";
                        }
                        addedExerciseMonText += exercise;
                        addedExerciseMon.setText(addedExerciseMonText);
                        break;
                    case "tue":
                        if (!addedExerciseTueText.isEmpty()) {
                            addedExerciseTueText += ", ";
                        }
                        addedExerciseTueText += exercise;
                        addedExerciseTue.setText(addedExerciseTueText);
                        break;
                    case "wed":
                        if (!addedExerciseWedText.isEmpty()) {
                            addedExerciseWedText += ", ";
                        }
                        addedExerciseWedText += exercise;
                        addedExerciseWed.setText(addedExerciseWedText);
                        break;
                    case "thu":
                        if (!addedExerciseThuText.isEmpty()) {
                            addedExerciseThuText += ", ";
                        }
                        addedExerciseThuText += exercise;
                        addedExerciseThu.setText(addedExerciseThuText);
                        break;
                    case "fri":
                        if (!addedExerciseFriText.isEmpty()) {
                            addedExerciseFriText += ", ";
                        }
                        addedExerciseFriText += exercise;
                        addedExerciseFri.setText(addedExerciseFriText);
                        break;
                    case "sat":
                        if (!addedExerciseSatText.isEmpty()) {
                            addedExerciseSatText += ", ";
                        }
                        addedExerciseSatText += exercise;
                        addedExerciseSat.setText(addedExerciseSatText);
                        break;
                    case "sun":
                        if (!addedExerciseSunText.isEmpty()) {
                            addedExerciseSunText += ", ";
                        }
                        addedExerciseSunText += exercise;
                        addedExerciseSun.setText(addedExerciseSunText);
                        break;
                }

                // Save the updated exercise data for the respective day
                intent.putExtra("addedExerciseMon", addedExerciseMonText);
                intent.putExtra("addedExerciseTue", addedExerciseTueText);
                intent.putExtra("addedExerciseWed", addedExerciseWedText);
                intent.putExtra("addedExerciseThu", addedExerciseThuText);
                intent.putExtra("addedExerciseFri", addedExerciseFriText);
                intent.putExtra("addedExerciseSat", addedExerciseSatText);
                intent.putExtra("addedExerciseSun", addedExerciseSunText);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private void deleteExercise(String day) {
        String deleteUrl = "http://210.123.255.113:3000/schedule/delete";
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("day", day);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest deleteRequest = new JsonObjectRequest(Request.Method.POST, deleteUrl, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String state = response.getString("state");
                            String message = response.getString("message");
                            if (state.equals("success")) {
                                Toast.makeText(add.this, "운동이 삭제되었습니다.: " + message, Toast.LENGTH_SHORT).show();

                                // Remove the exercise text from the respective TextView based on the day
                                switch (day) {
                                    case "mon":
                                        addedExerciseMon.setText("");
                                        break;
                                    case "tue":
                                        addedExerciseTue.setText("");
                                        break;
                                    case "wed":
                                        addedExerciseWed.setText("");
                                        break;
                                    case "thu":
                                        addedExerciseThu.setText("");
                                        break;
                                    case "fri":
                                        addedExerciseFri.setText("");
                                        break;
                                    case "sat":
                                        addedExerciseSat.setText("");
                                        break;
                                    case "sun":
                                        addedExerciseSun.setText("");
                                        break;
                                }
                            } else {
                                Toast.makeText(add.this, "운동 삭제 실패: " + message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(add.this, "운동 삭제 오류: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", token); // Include the token in the headers
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(deleteRequest);
    }
}



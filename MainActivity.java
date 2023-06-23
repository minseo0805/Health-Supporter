package com.example.healthsupporter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private List<Exercise> exerciseList = new ArrayList<>();
    private ExerciseListAdapter exerciseListAdapter;
    private ListView exerciseListView;
    private TextView currentTime;

    private static final String BASE_URL = "http://210.123.255.113:3000/main/info"; // Backend server URL
    private String token;

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
                Intent intent = new Intent(getApplicationContext(), add.class);
                startActivity(intent);
            }
        });

        // Get the token from wherever it is stored
        token = GlobalVariables.getToken();

        // Send request to retrieve exercise data
        sendRequest();
    }

    private void sendRequest() {
        JsonObjectRequest tokenRequest = new JsonObjectRequest(Request.Method.GET, BASE_URL, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String state = response.getString("state");
                            if (state.equals("success")) {
                                JSONArray exerciseArray = response.getJSONArray("message");
                                setupExerciseList(exerciseArray);  // Pass the exercise data to the setupExerciseList method
                            } else {
                                String errorMessage = response.getString("message");
                                Toast.makeText(MainActivity.this, "Failed to retrieve exercise data: " + errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error retrieving exercise data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void setupExerciseList(JSONArray exerciseArray) {
        exerciseList.clear();

        for (int i = 0; i < exerciseArray.length(); i++) {
            try {
                JSONObject exerciseObj = exerciseArray.getJSONObject(i);
                String exercise = exerciseObj.getString("exercise");
                int sets = exerciseObj.getInt("sets");
                int reps = exerciseObj.getInt("reps");
                Exercise exerciseItem = new Exercise(exercise, sets, reps);
                exerciseList.add(exerciseItem);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        exerciseListAdapter.notifyDataSetChanged(); // Notify the adapter about the data change
    }

}
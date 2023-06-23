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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Mypage extends AppCompatActivity {

    private TextView achievementTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);


        // 로그아웃 버튼 클릭 이벤트 처리
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 로그아웃 기능 구현
                logout();
            }
        });

        // 회원 탈퇴 버튼 클릭 이벤트 처리
        Button withdrawalButton = findViewById(R.id.withdrawalButton);
        withdrawalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원 탈퇴 기능 구현
                withdraw();
            }
        });

        ImageButton imageButton = findViewById(R.id.img_alarm);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), timer.class);
                startActivity(intent);
            }
        });

        ImageButton image_main = findViewById(R.id.img_main);
        image_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        ImageButton image_recommend = findViewById(R.id.img_recommend);
        image_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), add.class);
                startActivity(intent);
            }
        });
    }

    private void logout() {
        // Clear the global variable upon logout
        GlobalVariables.clear();

        // Move to the login screen
        Intent intent = new Intent(Mypage.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    // 회원 탈퇴 기능 구현
    private void withdraw() {
        // 회원 탈퇴 처리를 위한 API 엔드포인트 URL (실제 서버의 URL로 수정해야 합니다.)
        String withdrawalUrl = "http://210.123.255.113:3000/user/delete-account";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, withdrawalUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String state = response.getString("state");
                            if (state.equals("success")) {
                                // 회원 탈퇴 성공한 경우
                                // 예시로 회원 탈퇴 완료 화면(Withdrawal 클래스)으로 이동
                                Intent intent = new Intent(Mypage.this, Withdrawal.class);
                                startActivity(intent);
                                finish();
                            } else if (state.equals("fail")) {
                                // 회원 탈퇴 요청 실패한 경우
                                Toast.makeText(Mypage.this, "회원 탈퇴에 실패했습니다", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // 회원 탈퇴 요청 실패한 경우
                        Toast.makeText(Mypage.this, "회원 탈퇴에 실패했습니다", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // Override getHeaders() to include your headers
                Map<String, String> headers = new HashMap<>();
                String token = GlobalVariables.getToken(); // Get the token from GlobalVariables class
                headers.put("Authorization", token); // Include "Bearer" prefix before the token
                return headers;
            }
        };

        // Volley 라이브러리를 사용하여 요청을 큐에 추가합니다.
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}


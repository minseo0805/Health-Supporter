package com.example.healthsupporter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    private EditText et_Id, et_Password;
    private Button btn_login, btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_Id = findViewById(R.id.et_Id);
        et_Password = findViewById(R.id.et_Password);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = et_Id.getText().toString();
                String userPassword = et_Password.getText().toString();

                // 아이디와 비밀번호를 입력하지 않았을 경우 처리
                if (userId.isEmpty() || userPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 입력해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String state = jsonObject.getString("state");
                            if (state.equals("success")) {
                                // 로그인 성공
                                String message = jsonObject.getString("message");
                                if (message.equals("sign_in_fail")) {
                                    Toast.makeText(getApplicationContext(), "ID/PW 정보가 잘못되었습니다", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    GlobalVariables.setToken(message);

                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(), "로그인에 성공했습니다", Toast.LENGTH_SHORT).show();
                                }

                            } else if (state.equals("fail")) {
                                // 로그인 실패
                                String errorMessage = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다 오류 메시지: " + errorMessage, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(userId, userPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Login.this);
                queue.add(loginRequest);
            }
        });





    }
}

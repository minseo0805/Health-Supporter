package com.example.healthsupporter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private EditText et_Id, et_Password, et_Name, et_Age;
    private Button btn_register, btn_doublecheck;

    private boolean idChecked = false; // 아이디 중복 확인 상태

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // EditText 필드 찾기
        et_Id = findViewById(R.id.et_Id);
        et_Password = findViewById(R.id.et_Password);
        et_Name = findViewById(R.id.et_Name);
        et_Age = findViewById(R.id.et_Age);

        // 중복확인 버튼 클릭시 수행
        btn_doublecheck = findViewById(R.id.btn_doublecheck);
        btn_doublecheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = et_Id.getText().toString().trim();
                if (userId.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "아이디를 입력해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                CheckRequest checkRequest = new CheckRequest(userId, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String state = jsonObject.getString("state");
                            String message = jsonObject.getString("message");

                            if (state.equals("success")) {
                                if (message.equals("unavailable_id")) {
                                    Toast.makeText(getApplicationContext(), "해당 아이디가 이미 존재합니다", Toast.LENGTH_SHORT).show();
                                    idChecked = false;
                                } else if (message.equals("available_id")) {
                                    Toast.makeText(getApplicationContext(), "해당 아이디를 사용할 수 있습니다", Toast.LENGTH_SHORT).show();
                                    idChecked = true;
                                }
                            } else if (state.equals("fail")) {
                                Toast.makeText(getApplicationContext(), "아이디 중복 확인에 실패했습니다", Toast.LENGTH_SHORT).show();
                                idChecked = false;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "서버 연결 실패", Toast.LENGTH_SHORT).show();
                        idChecked = false;
                    }
                });

                RequestQueue queue = Volley.newRequestQueue(Register.this);
                queue.add(checkRequest);
            }
        });

        // 회원가입 버튼 클릭 시 수행
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = et_Id.getText().toString().trim();
                String userPassword = et_Password.getText().toString();
                String userName = et_Name.getText().toString();
                String userAge = et_Age.getText().toString();

                if (userId.isEmpty() || userPassword.isEmpty() || userName.isEmpty() || userAge.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "모든 값을 입력해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!idChecked) {
                    Toast.makeText(getApplicationContext(), "먼저 아이디 중복 확인을 해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                String url = "http://210.123.255.113:3000/user/create-account";
                StringRequest registerRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String state = jsonObject.getString("state");
                            String message = jsonObject.getString("message");

                            if (state.equals("success")) {
                                Toast.makeText(getApplicationContext(), "회원가입이 성공적으로 완료되었습니다", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Register.this, Login.class);
                                startActivity(intent);
                            }
                            else if (state.equals("fail")) {
                                // 로그인 실패
                                String errorMessage = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다. 오류 메시지: " + errorMessage, Toast.LENGTH_SHORT).show();
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "서버 연결 실패", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("userId", userId);
                        params.put("userPassword", userPassword);
                        params.put("userName", userName);
                        params.put("userAge", userAge);
                        return params;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(Register.this);
                queue.add(registerRequest);
            }
        });
    }
}




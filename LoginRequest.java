package com.example.healthsupporter;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
//    StringRequest는 서버에 요청을 보내고, 서버에서 응답을 받을 때 문자열 데이터를 처리할 수 있도록 하는 Volley 라이브러리의 기본 클래스 중 하나
    //서버 URL 설정
    final static private String URL =  "http://210.123.255.113:3000/auth/login";
    private Map<String, String> map;

    public LoginRequest(String userId, String userPassword, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put( "userId", userId);
        map.put("userPassword", userPassword);

    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError{
        return map;
    }
}

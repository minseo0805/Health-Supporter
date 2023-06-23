package com.example.healthsupporter;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    //StringRequest는 서버에 요청을 보내고, 서버에서 응답을 받을 때 문자열 데이터를 처리할 수 있도록 하는 Volley 라이브러리의 기본 클래스 중 하나
    //서버 URL 설정
    final static private String URL =  "http://210.123.255.113:3000/user/create-account";


    private Map<String, String> map; //Hash맵

    public RegisterRequest(String userId, String userPassword, String userName, int userAge, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null); //서버 전송 방식 post


        map = new HashMap<>();
        map.put( "userId", userId); //key값
        map.put("userPassword", userPassword);
        map.put("userName", userName);
        map.put("userAge", userAge +"");

    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError{
      return map;
    }
}

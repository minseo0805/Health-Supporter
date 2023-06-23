package com.example.healthsupporter;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CheckRequest extends StringRequest {
    // Set server URL
    final static private String URL = "http://210.123.255.113:3000/user/check-id";

    private Map<String, String> params; // Parameters for the request

    public CheckRequest(String userId, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.GET, URL + "?userId=" + userId, listener, errorListener);

        // Initialize the parameters
        params = new HashMap<>();
    }

    @Override
    protected Map<String, String> getParams() {
        return params;
    }
}

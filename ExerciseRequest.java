package com.example.healthsupporter;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ExerciseRequest extends StringRequest {
    // Set server URL
    final static private String URL = "http://210.123.255.113:3000/exercise/info";

    private Map<String, String> values; // Values for the request

    public ExerciseRequest(boolean chkChest, boolean chkBack, boolean chkShoulder, boolean chkLeg, boolean chkAbs, boolean chkArm, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.GET, buildURL(chkChest, chkBack, chkShoulder, chkLeg, chkAbs, chkArm), listener, errorListener);

        // Initialize the values
        values = new HashMap<>();
    }

    private static String buildURL(boolean chkChest, boolean chkBack, boolean chkShoulder, boolean chkLeg, boolean chkAbs, boolean chkArm) {
        StringBuilder urlBuilder = new StringBuilder("http://210.123.255.113:3000/exercise/info?");

        if (chkAbs) {
            urlBuilder.append("value=abs&");
        }
        if (chkArm) {
            urlBuilder.append("value=arm&");
        }
        if (chkBack) {
            urlBuilder.append("value=back&");
        }
        if (chkChest) {
            urlBuilder.append("value=chest&");
        }
        if (chkLeg) {
            urlBuilder.append("value=leg&");
        }
        if (chkShoulder) {
            urlBuilder.append("value=shoulder&");
        }

        return urlBuilder.toString();
    }



    @Override
    protected Map<String, String> getParams() {
        return values;
    }
}

package com.developeremon.ourheros;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiCalling {

    static String api_url = "";

    void JsonArrayMethod(){

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("","");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        jsonArray.put(jsonArray);


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, api_url, jsonArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {



            }
        });







    }





}

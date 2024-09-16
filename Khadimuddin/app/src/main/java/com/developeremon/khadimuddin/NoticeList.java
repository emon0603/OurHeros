package com.developeremon.khadimuddin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.developeremon.khadimuddin.Admin.EncryptDecrypt;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class NoticeList extends AppCompatActivity {

    ImageView noticeadd;
    ListView noticelist;
    TextView noinfoTv;
    RelativeLayout noticelistlay;

    HashMap<String,String> hashMap = new HashMap<>();
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_list);

        noticelist = findViewById(R.id.noticelist);
        noinfoTv = findViewById(R.id.noinfoTv);
        noticelistlay = findViewById(R.id.noticelistlay);



       /* if (arrayList.size() > 0) {
            JsonarrayRequet();
            noinfoTv.setVisibility(View.GONE);
        } else {
            noinfoTv.setVisibility(View.VISIBLE);
            noticelistlay.setVisibility(View.GONE);
        }*/


        JsonarrayRequet();


    }

    public class NoticeAdapter extends BaseAdapter {

        TextView datetv, noticetv;


        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View myview = inflater.inflate(R.layout.notice_list_item_onlyshow, parent, false);
            datetv = myview.findViewById(R.id.datetv);
            noticetv = myview.findViewById(R.id.noticetv);
            RelativeLayout listlay = myview.findViewById(R.id.listlay);

            hashMap = arrayList.get(position);
            String id = hashMap.get("id");
            String date = hashMap.get("date");
            String notice = hashMap.get("notice");

            datetv.setText(date);
            noticetv.setText(notice);

            listlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Notice_Details_View.detail_date = date;
                    Notice_Details_View.details_notice = notice;
                    startActivity(new Intent(NoticeList.this,Notice_Details_View.class));
                }
            });


            return myview;
        }
    }

    private void JsonarrayRequet(){
        String url = MainActivity.volley_url+"shownotice.php";

        JSONArray jsonArray = new JSONArray();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("key", EncryptDecrypt.Key);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        jsonArray.put(jsonObject);


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, jsonArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int x=0; x<response.length(); x++){

                    try {
                        JSONObject jsonObject = response.getJSONObject(x);
                        String id = jsonObject.getString("id");
                        String date = jsonObject.getString("date");
                        String notice = jsonObject.getString("notice");

                        hashMap = new HashMap<>();
                        hashMap.put("id", id);
                        hashMap.put("date", date);
                        hashMap.put("notice", notice);
                        arrayList.add(hashMap);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }

                //----------------------------------------------------------------
                if (arrayList.size() > 0 ){
                    NoticeAdapter noticeAdapter = new NoticeAdapter();
                    noticelist.setAdapter(noticeAdapter);
                }
                //----------------------------------------------------------------


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

    }


}
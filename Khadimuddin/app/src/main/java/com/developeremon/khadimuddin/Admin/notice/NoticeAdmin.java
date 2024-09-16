package com.developeremon.khadimuddin.Admin.notice;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.developeremon.khadimuddin.Admin.EncryptDecrypt;
import com.developeremon.khadimuddin.MainActivity;
import com.developeremon.khadimuddin.Notice_Details_View;
import com.developeremon.khadimuddin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NoticeAdmin extends AppCompatActivity {

    ImageView noticeadd;
    ListView noticelist;
    TextView noinfoTv;
    RelativeLayout noticelistlay;

    HashMap<String,String> hashMap = new HashMap<>();
    ArrayList <HashMap<String,String>> arrayList = new ArrayList<>();
    SharedPreferences sharedPreferences;
    private boolean dataFetched = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_admin);

        noticeadd = findViewById(R.id.noticeadd);
        noticelist = findViewById(R.id.noticelist);
        noinfoTv = findViewById(R.id.noinfoTv);
        noticelistlay = findViewById(R.id.noticelistlay);






      /* if (arrayList.size() > 0) {
            noticelistlay.setVisibility(View.VISIBLE);
            noinfoTv.setVisibility(View.GONE);
        } else {
            noinfoTv.setVisibility(View.VISIBLE);
            noticelistlay.setVisibility(View.GONE);
        }
*/





        noticeadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notice_Write.edit_Request= "";
                startActivity(new Intent(NoticeAdmin.this, Notice_Write.class));
            }
        });
    }

    private class NoticeAdapter extends BaseAdapter{

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
            View myview = inflater.inflate(R.layout.notice_list_item, parent, false);
            datetv = myview.findViewById(R.id.datetv);
            noticetv = myview.findViewById(R.id.noticetv);
            RelativeLayout listlay = myview.findViewById(R.id.listlay);
            ImageView morebt = myview.findViewById(R.id.morebt);

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
                    startActivity(new Intent(NoticeAdmin.this,Notice_Details_View.class));
                }
            });

            PopupMenu popupMenu = new PopupMenu(NoticeAdmin.this, morebt);
            popupMenu.getMenuInflater().inflate(R.menu.popup_item, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {

                    if (menuItem.getItemId() == R.id.editbt){

                        sharedPreferences = getSharedPreferences(String.valueOf(R.string.app_name),MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.putString("id", id);
                        myEdit.putString("notice", notice);
                        myEdit.commit();
                        Notice_Write.edit_Request = "edit";

                        startActivity(new Intent(NoticeAdmin.this, Notice_Write.class));


                    } else {

                        new AlertDialog.Builder(NoticeAdmin.this)
                                .setTitle("Alert ")
                                .setMessage("আপনি নিশ্চিত হলে হ্যাঁ বাটনে চাপ দিন")
                                .setIcon(R.drawable.alert_icon)
                                .setCancelable(true)
                                .setNegativeButton("না ধন্যবাদ", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                })
                                .setPositiveButton("হ্যাঁ", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        DeleteRequestMethod(id);

                                    }
                                })
                                .show();

                    }


                    return false;
                }
            });

            morebt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupMenu.show();
                }
            });





            return myview;
        }
    }

    private void JsonarrayRequet(){
        String url = MainActivity.volley_url + "shownotice.php";

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

    private void DeleteRequestMethod(String id){

        String url = MainActivity.volley_url+ "delete.php" ;


        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();


        StringRequest deleterequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                if (response.contains("Data Delete Successful")){
                    Toast.makeText(NoticeAdmin.this, response, Toast.LENGTH_SHORT).show();
                    //JsonarrayRequet();
                    onResume();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //chechtv.setText(error.getMessage());
                Toast.makeText(NoticeAdmin.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String,String>();

                try {
                    params.put("id", id);
                    params.put("key", EncryptDecrypt.Key);

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(deleterequest);


    }//---------


    @Override
    protected void onResume() {
        super.onResume();
        arrayList.clear();
        JsonarrayRequet();
    }



}
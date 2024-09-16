package com.developeremon.khadimuddin.Admin.notice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.developeremon.khadimuddin.Admin.EncryptDecrypt;
import com.developeremon.khadimuddin.DatePicker.DatePick;
import com.developeremon.khadimuddin.MainActivity;
import com.developeremon.khadimuddin.R;

import java.util.HashMap;
import java.util.Map;

public class Notice_Write extends AppCompatActivity {

    EditText ednotice;
    ImageView sentnoticebt;
    SharedPreferences sharedPreferences;
    public static String edit_Request = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_write);

        ednotice = findViewById(R.id.ednotice);
        sentnoticebt = findViewById(R.id.sentnoticebt);

        if (edit_Request.contains("edit")){

            sharedPreferences = getSharedPreferences(String.valueOf(R.string.app_name),MODE_PRIVATE);
            String editnotice = sharedPreferences.getString("notice","");
            String id = sharedPreferences.getString("id","");
            ednotice.setText(editnotice);

        }



        sentnoticebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (edit_Request.contains("edit")){
                    Notice_Edit();
                } else {
                    StringRequestMethod();
                }

            }
        });

    }

    private void StringRequestMethod(){

        String url = MainActivity.volley_url+"notice.php" ;
        String inputData = ednotice.getText().toString();

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                if (response.contains("Data Successfully Insert")){
                    ednotice.setText(null);
                    Toast.makeText(Notice_Write.this, response, Toast.LENGTH_SHORT).show();
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("new_data", inputData);
                    setResult(RESULT_OK, resultIntent);
                    finish(); // Back to First Activity

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //chechtv.setText(error.getMessage());
                Toast.makeText(Notice_Write.this, error.getMessage(), Toast.LENGTH_SHORT).show();


            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String,String>();

                try {

                    params.put("date", DatePick.getEnglishinbangla());
                    params.put("notice", EncryptDecrypt.EncryptMethod(ednotice.getText().toString(), "!bLooD#DonaTi%oN" ) );
                    params.put("key", EncryptDecrypt.Key);

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }


                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }//---------


    private void Notice_Edit(){

        String url = MainActivity.volley_url+ "edit.php" ;
        String inputData = ednotice.getText().toString();

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();


        String id = sharedPreferences.getString("id","");



        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                if (response.contains("Data Updated Successful")){
                    ednotice.setText(null);
                    Toast.makeText(Notice_Write.this, response, Toast.LENGTH_SHORT).show();
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("new_data", inputData);
                    setResult(RESULT_OK, resultIntent);
                    finish(); // Back to First Activity

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //chechtv.setText(error.getMessage());
                Toast.makeText(Notice_Write.this, error.getMessage(), Toast.LENGTH_SHORT).show();


            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String,String>();

                try {

                    params.put("id", id);
                    params.put("notice", EncryptDecrypt.EncryptMethod(ednotice.getText().toString(), "!bLooD#DonaTi%oN" ) );
                    params.put("key", EncryptDecrypt.Key);

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }






}
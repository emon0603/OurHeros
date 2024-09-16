package com.developeremon.khadimuddin.Admin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import com.developeremon.khadimuddin.Admin.notice.NoticeAdmin;
import com.developeremon.khadimuddin.MainActivity;
import com.developeremon.khadimuddin.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    TextInputEditText edpass;
    TextView chechtv;
    Button loginbt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        edpass=  findViewById(R.id.Authorization_key);
        chechtv = findViewById(R.id.chechtv);
        loginbt = findViewById(R.id.loginbt);

        try {
            EncryptDecrypt.Key = EncryptDecrypt.EncryptMethod("JuBO_SongO@Emon","!WB$OC@PkE@m#O&N");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }



        loginbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               StringRequestMethod();
            }
        });




    }



        private void StringRequestMethod(){

            String url = MainActivity.volley_url +"login.php" ;

            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please Wait");
            progressDialog.show();


            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if (response.contains("Main Admin Login") ) {

                        progressDialog.dismiss();
                        startActivity(new Intent(Login.this, Main_Admin_Dashboard.class));
                        finish();
                        //chechtv.setText(response);
                        //Toast.makeText(Login.this,response, Toast.LENGTH_SHORT).show();


                    } else if (response.contains("Notice Admin Login")) {
                        startActivity(new Intent(Login.this, NoticeAdmin.class));
                        finish();
                    } else {
                        Toast.makeText(Login.this,response, Toast.LENGTH_SHORT).show();
                        chechtv.setText(response);
                        progressDialog.dismiss();

                    }



                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    progressDialog.dismiss();


                    new AlertDialog.Builder(Login.this)
                            .setTitle("সার্ভারের কাজ চলমান ")
                            .setMessage("সাময়িক অসুবিধার জন্য আমরা আন্তরিকভাবে দুঃখিত।")
                            .setIcon(R.drawable.server_working_icon)
                            .setCancelable(true)
                            .setNegativeButton("ওকে", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .show();

                    //chechtv.setText(error.getMessage());
                    //Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();


                }
            }){

                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String,String>();

                    try {

                        //params.put("pass",EncryptDecrypt.EncryptMethod(edpass.toString(), ""));
                        params.put("pass", EncryptDecrypt.EncryptMethod(edpass.getText().toString(), "!bLooD#DonaTi%oN" ) );
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






}
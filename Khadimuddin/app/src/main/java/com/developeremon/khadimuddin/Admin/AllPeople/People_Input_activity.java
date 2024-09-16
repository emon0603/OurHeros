package com.developeremon.khadimuddin.Admin.AllPeople;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.developeremon.khadimuddin.MainActivity;
import com.developeremon.khadimuddin.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class People_Input_activity extends AppCompatActivity {


    EditText edname,edfathername,ednumber;
    ImageView profilepicbt;
    Button inputbt;
    SharedPreferences sharedPreferences;
    public static String people_edit_Request = "";

    public static String ednames = "";
    public static String edfathers = "";
    public static String ednums = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.people_input_activity);

        edname = findViewById(R.id.edname);
        edfathername = findViewById(R.id.edfathername);
        ednumber = findViewById(R.id.ednumber);
        inputbt = findViewById(R.id.inputbt);
        profilepicbt = findViewById(R.id.profile_image);

        sharedPreferences = getSharedPreferences(String.valueOf(R.string.app_name),MODE_PRIVATE);

        profilepicbt.setOnClickListener(view -> {
            ImagechooserMethod();
        });




        if (people_edit_Request.contains("people_edit")){


            String name = sharedPreferences.getString("name","");
            String fathername = sharedPreferences.getString("fathername","");
            String num = sharedPreferences.getString("num","");
            String image = sharedPreferences.getString("image","");

            edname.setText(name);
            edfathername.setText(fathername);
            ednumber.setText(num);
            Picasso.get().load(image).into(profilepicbt);

        }



        inputbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (people_edit_Request.contains("people_edit")){
                    People_Edit();
                } else {
                    StringRequestMethod(ImageBase64string());
                }

            }
        });

    } //---- finish oncreate



    private void StringRequestMethod(String image){

        String url = MainActivity.volley_url+"addpeople.php" ;
        String inputData = edname.getText().toString();

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                if (response.contains("New Member Successfully Added")){
                    Toast.makeText(People_Input_activity.this, response, Toast.LENGTH_SHORT).show();
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("new_data", inputData);
                    setResult(RESULT_OK, resultIntent);
                    finish(); // Back to First Activity

                }
                Toast.makeText(People_Input_activity.this, response, Toast.LENGTH_SHORT).show();



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //chechtv.setText(error.getMessage());
                Toast.makeText(People_Input_activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();


            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String,String>();

                try {



                    params.put("image", image);
                    params.put("name", EncryptDecrypt.EncryptMethod(edname.getText().toString(), "!bLooD#DonaTi%oN" ) );
                    params.put("fathername", EncryptDecrypt.EncryptMethod(edfathername.getText().toString(), "!bLooD#DonaTi%oN" ) );
                    params.put("num", EncryptDecrypt.EncryptMethod(ednumber.getText().toString(), "!bLooD#DonaTi%oN" ) );
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


    private void People_Edit(){

        String url = MainActivity.volley_url+ "people_edit.php" ;
        String inputData = edname.getText().toString();

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();


        String id = sharedPreferences.getString("id","");



        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                if (response.contains("Data Updated Successful")){
                    edname.setText(null);
                    Toast.makeText(People_Input_activity.this, response, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(People_Input_activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();


            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String,String>();

                try {

                    params.put("id", id);
                    params.put("name", EncryptDecrypt.EncryptMethod(edname.getText().toString(), "!bLooD#DonaTi%oN" ) );
                    params.put("fathername", EncryptDecrypt.EncryptMethod(edfathername.getText().toString(), "!bLooD#DonaTi%oN" ) );
                    params.put("num", EncryptDecrypt.EncryptMethod(ednumber.getText().toString(), "!bLooD#DonaTi%oN" ) );
                    params.put("image", ImageBase64string() );
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


    public String ImageBase64string(){

        //------------------------------------------------------------------------------
        BitmapDrawable bitmapDrawable = (BitmapDrawable) profilepicbt.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);

        byte[] imagebyte = byteArrayOutputStream.toByteArray();
        String imagebase64String = Base64.encodeToString(imagebyte,Base64.DEFAULT);

        //===================================================================================

        return imagebase64String;
    }

    public void ImagechooserMethod(){
        ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent(new Function1<Intent, Unit>() {
                    @Override
                    public Unit invoke(Intent intent) {
                        launcher.launch(intent);
                        return null;
                    }
                });

    }


    ActivityResultLauncher launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {

                    if (o.getResultCode() == Activity.RESULT_OK){

                          Intent intent = o.getData();
                          Uri uri = intent.getData();

                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            profilepicbt.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
            });



}
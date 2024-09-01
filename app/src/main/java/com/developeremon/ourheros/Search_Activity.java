package com.developeremon.ourheros;

import static com.developeremon.ourheros.List_Fargment.api_url;
import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.Lottie;
import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Search_Activity extends AppCompatActivity {

    EditText searchbt;
    ImageView resetbt, backbt;
    ListView searchlist;
    LinearLayout itemainlay;
    com.airbnb.lottie.LottieAnimationView lottieAnimationView;
    HashMap<String,String> hashMap ;
    ArrayList< HashMap<String,String> > arrayList = new ArrayList<>();
    MyAdapterall myadapter = new MyAdapterall(arrayList);





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        resetbt = findViewById(R.id.reset_search_text);
        backbt = findViewById(R.id.backbt);
        searchbt = findViewById(R.id.searchbt);
        searchlist = findViewById(R.id.searchlist);
        lottieAnimationView = findViewById(R.id.no_data);

        //------------------------------------------------------------------



        //=====================================================
        JsonArrayMethod();
        searchlist.setVisibility(View.GONE);


        //=====================================================



        //JsonArrayMethod();
        lottieAnimationView.setVisibility(View.VISIBLE);


        ///-------------------------------------------------------------


        backbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //===============================================================================================================
        searchbt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing before text changed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do nothing during text changed



                if (s.length() > 0){
                    fileList(s.toString());
                    lottieAnimationView.setVisibility(View.GONE);
                    searchlist.setVisibility(View.VISIBLE);

                } else {
                    lottieAnimationView.setVisibility(View.VISIBLE);
                    searchlist.setVisibility(View.GONE);
                }






            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    resetbt.setVisibility(ImageView.VISIBLE);

                } else {
                    resetbt.setVisibility(ImageView.GONE);
                }
            }
        });
        //---------------------------------------------------------
        resetbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchbt.setText(null);
            }

        });

        //===================================================================

    }//---- Oncreate Finish


    //---------------------------------------------------------------------------------------

    class MyAdapterall extends BaseAdapter {

        LinearLayout searchlistbt;
        ImageView imagetv;
        TextView nametv, profetv,nameofversitytv,dateofdeadtv,idnumber;


        ArrayList<HashMap<String,String>> myarrayList;
        public MyAdapterall(ArrayList<HashMap<String, String>> arrayList) {
            this.myarrayList = arrayList;
        }



        public void setFilteredList(ArrayList<HashMap<String, String>> filteredList) {
            this.myarrayList = filteredList;
            notifyDataSetChanged();
        }





        @Override
        public int getCount() {
            return myarrayList.size();
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
        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View myview = layoutInflater.inflate(R.layout.search_item, viewGroup,false);

            searchlistbt = myview.findViewById(R.id.searchlistbt);
            imagetv = myview.findViewById(R.id.imageView);
            nametv = myview.findViewById(R.id.textView);
            profetv = myview.findViewById(R.id.textView2);
            nameofversitytv = myview.findViewById(R.id.textView3);
            itemainlay = myview.findViewById(R.id.itemainlay);




            HashMap <String,String> hashMap = myarrayList.get(i);

            String id = hashMap.get("id");
            String name = hashMap.get("name");
            String profession = hashMap.get("profession");
            String versityname = hashMap.get("versityname");
            String datedead = hashMap.get("datedead");
            String age = hashMap.get("age");
            String birthday = hashMap.get("birthday");
            String placeofbirth = hashMap.get("placeofbirth");
            String jevabehoyeche = hashMap.get("jevabehoyeche");
            String biography = hashMap.get("biography");


            String image = hashMap.get("image");


            nametv.setText(name);
            profetv.setText(profession);
            nameofversitytv.setText(versityname);



            Picasso.get().load(image).into(imagetv);

            //Toast.makeText(getContext(), birthday, Toast.LENGTH_SHORT).show();






            //-------------------------------------------------------------------------------
            searchlistbt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    startActivity(new Intent(getApplicationContext(), View_Details.class ));
                    View_Details.sname = name;
                    View_Details.sprofe = profession;
                    View_Details.sversity = versityname;
                    View_Details.sdead = datedead;
                    View_Details.sage = age ;
                    View_Details.sbirthday = birthday ;
                    View_Details.sbirthdayplace = placeofbirth ;
                    View_Details.sjevabehoyeche = jevabehoyeche ;
                    View_Details.sbiography = biography ;
                    View_Details.scover_pic = image ;






                }
            });




            return myview;

        }
    }
    //-----------------------------------------------------------------------------

    public void JsonArrayMethod(){



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, List_Fargment.api_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                Log.d("serverRes", response.toString());

                try {

                    JSONArray jsonArray = response.getJSONArray("users");

                    for (int x=0; x<jsonArray.length(); x++ ) {

                        JSONObject jsonObject = jsonArray.getJSONObject(x);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String profession = jsonObject.getString("profession");
                        String versityname = jsonObject.getString("versityname");
                        String datedead = jsonObject.getString("datedead");
                        String age = jsonObject.getString("age");
                        String birthday = jsonObject.getString("birthday");
                        String placeofbirth = jsonObject.getString("placeofbirth");
                        String jevabehoyeche = jsonObject.getString("jevabehoyeche");
                        String biography = jsonObject.getString("biography");
                        String image = jsonObject.getString("image");

                        hashMap = new HashMap<>();
                        hashMap.put("id", id);
                        hashMap.put("name",name);
                        hashMap.put("profession",profession);
                        hashMap.put("versityname",versityname);
                        hashMap.put("datedead",datedead);
                        hashMap.put("age",age);
                        hashMap.put("birthday",birthday);
                        hashMap.put("placeofbirth",placeofbirth);
                        hashMap.put("jevabehoyeche",jevabehoyeche);
                        hashMap.put("biography",biography);
                        hashMap.put("image",image);
                        arrayList.add(hashMap);


                        //Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();

                    }

                    searchlist.setAdapter(myadapter);








                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();



            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);




    }
    //------------------------------------------------------------------------


    //-----------------------------------------------------------------------------


    private void fileList(String newText) {
        ArrayList<HashMap<String, String>> arrayList1 = new ArrayList<>();
        for (HashMap<String, String> detailsItem : arrayList) {
            if (detailsItem.get("name").toLowerCase().contains(newText.toLowerCase())) {
                arrayList1.add(detailsItem);
            }
        }
        if (arrayList1.isEmpty()) {
            Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        } else {
            myadapter.setFilteredList(arrayList1);
        }
    }





    //---------------------------------------------------------------------------------
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();

    }
    //=================================================================
}
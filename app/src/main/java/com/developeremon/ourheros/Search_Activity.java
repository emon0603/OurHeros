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


        hashMap = new HashMap<>();
        hashMap.put("name", "Emon");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "Sabbir");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "Siam");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "RAkibul");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "Jubayer");
        arrayList.add(hashMap);



        searchlist.setAdapter(myadapter);

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

                if (s.length() > 0) {

                    fileList(s.toString());


                } else {
                    lottieAnimationView.setVisibility(View.GONE);
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

        TextView nametv;


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
        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View myview = layoutInflater.inflate(R.layout.search_item, viewGroup,false);

            nametv = myview.findViewById(R.id.itemTextView);

            HashMap <String,String> hashMap = myarrayList.get(i);

            String name = hashMap.get("name");


            nametv.setText(name);


            //Toast.makeText(getContext(), birthday, Toast.LENGTH_SHORT).show();






            //-------------------------------------------------------------------------------



            return myview;
        }
    }


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
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

    private ArrayAdapter<String> adapter;
    private List<String> itemList = new ArrayList<>();
    private List<String> filteredItemList = new ArrayList<>();
    private RequestQueue requestQueue;






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

        requestQueue = Volley.newRequestQueue(this);
        fetchInitialData();

        adapter = new ArrayAdapter<>(this, R.layout.search_item, R.id.itemTextView, filteredItemList);
        searchlist.setAdapter(adapter);




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

                    filterData(s.toString());


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

    //-----------------------------------------------------------------------------



    private void fetchInitialData() {
        String url = api_url; // Replace with your URL

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Assuming the JSONArray is inside the JSONObject under the key "items"
                            JSONArray jsonArray = response.getJSONArray("users");
                            fetchItemDetails(jsonArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    private void fetchItemDetails(final JSONArray jsonArray) {
        // Here we assume each item in the JSONArray is an object containing an "id" or similar to fetch details
        String url = api_url; // Replace with your URL

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parseJsonData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
    }

    private void parseJsonData(JSONArray jsonArray) {
        itemList.clear();
        filteredItemList.clear();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String itemName = jsonObject.getString("name"); // Replace "name" with your JSON field
                itemList.add(itemName);
                filteredItemList.add(itemName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void filterData(String query) {
        filteredItemList.clear();
        if (query.isEmpty()) {
            filteredItemList.addAll(itemList);
        } else {
            for (String item : itemList) {
                if (item.toLowerCase().contains(query.toLowerCase())) {
                    filteredItemList.add(item);
                }
            }
        }
        adapter.notifyDataSetChanged();
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
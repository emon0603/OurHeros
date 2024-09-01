package com.developeremon.ourheros;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.HashMap;

public class List_Fargment extends Fragment {

    GridView listview;
    HashMap<String, String> hashMap;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

    public static String api_url = "http://192.168.1.100/Shohid/data.json";
    private LottieAnimationView progress_loading;

    private int previousDataCount = 0;
    private int lastDataId = 0;

    TextView textView5;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View ViewList = inflater.inflate(R.layout.list__fargment, container, false);
        listview = ViewList.findViewById(R.id.listview);
        progress_loading = ViewList.findViewById(R.id.progress_loading);



        JsonArrayMethod();


        return ViewList;
    }


    //--------------------------------------------------------------------------------------------
    class MyAdapter extends BaseAdapter {

        MaterialCardView detailsbt;
        ImageView imagetv;
        TextView idnumber, nametv, profetv, nameofversitytv, dateofdeadtv;


        @Override
        public int getCount() {
            return 20;
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
            View myview = layoutInflater.inflate(R.layout.recyclear_item, viewGroup, false);

            idnumber = myview.findViewById(R.id.idnumber);
            detailsbt = myview.findViewById(R.id.detailsbt);
            imagetv = myview.findViewById(R.id.imagetv);
            nametv = myview.findViewById(R.id.nametv);
            profetv = myview.findViewById(R.id.profetv);
            nameofversitytv = myview.findViewById(R.id.nameofversitytv);
            dateofdeadtv = myview.findViewById(R.id.dateofdeadtv);


            HashMap<String, String> hashMap = arrayList.get(i);

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
            dateofdeadtv.setText(datedead);
            idnumber.setText(id);


            Picasso.get().load(image).into(imagetv);

            //Toast.makeText(getContext(), birthday, Toast.LENGTH_SHORT).show();


            //-------------------------------------------------------------------------------
            detailsbt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    startActivity(new Intent(getContext(), View_Details.class));
                    View_Details.sname = name;
                    View_Details.sprofe = profession;
                    View_Details.sversity = versityname;
                    View_Details.sdead = datedead;
                    View_Details.sage = age;
                    View_Details.sbirthday = birthday;
                    View_Details.sbirthdayplace = placeofbirth;
                    View_Details.sjevabehoyeche = jevabehoyeche;
                    View_Details.sbiography = biography;
                    View_Details.scover_pic = image;


                }
            });


            return myview;
        }
    }

    //--------------------------------------------------------------------------------------------

    //------------------------------------------------------------------------

    public void JsonArrayMethod() {


        progress_loading.setVisibility(View.VISIBLE);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, api_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progress_loading.setVisibility(View.GONE);
                Log.d("serverRes", response.toString());

                try {

                    JSONArray jsonArray = response.getJSONArray("users");
                    int newDataCount = 0;



                    for (int x = 0; x < jsonArray.length(); x++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(x);
                        int itemId = jsonObject.getInt("id");



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
                        hashMap.put("name", name);
                        hashMap.put("profession", profession);
                        hashMap.put("versityname", versityname);
                        hashMap.put("datedead", datedead);
                        hashMap.put("age", age);
                        hashMap.put("birthday", birthday);
                        hashMap.put("placeofbirth", placeofbirth);
                        hashMap.put("jevabehoyeche", jevabehoyeche);
                        hashMap.put("biography", biography);
                        hashMap.put("image", image);
                        arrayList.add(hashMap);




                        // Toast.makeText(getContext(), , Toast.LENGTH_SHORT).show();

                    }





                    MyAdapter myAdapter = new MyAdapter();
                    listview.setAdapter(myAdapter);


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);


    }


    private void showNotification(int newDataCount) {
        // Notification তৈরি করা
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "my_channel_01")
                .setSmallIcon(R.drawable.baseline_notifications_24) // আপনার আইকন এখানে ব্যবহার করুন
                .setContentTitle("New Data Available")
                .setContentText("You have " + newDataCount + " new items.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true); // ক্লিক করলে নোটিফিকেশন স্বয়ংক্রিয়ভাবে মুছে যাবে

        // Notification প্রদর্শন করা
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(1, builder.build());
    }






    //------------------------------------------------------------------------






}
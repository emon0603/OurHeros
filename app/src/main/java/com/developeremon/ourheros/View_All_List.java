package com.developeremon.ourheros;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;
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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class View_All_List extends AppCompatActivity {

    GridView listViewall;
    private LottieAnimationView progress_loading;

    HashMap<String,String> hashMapall ;
    ArrayList< HashMap<String,String> > arrayListall = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_list);

        listViewall = findViewById(R.id.listviewall);
        progress_loading = findViewById(R.id.progress_loading2);


        JsonArrayMethod();



    }


    class MyAdapterall extends BaseAdapter {

        MaterialCardView detailsbt;
        ImageView imagetv;
        TextView nametv, profetv,nameofversitytv,dateofdeadtv,idnumber;


        @Override
        public int getCount() {
            return arrayListall.size();
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
            View myview = layoutInflater.inflate(R.layout.recyclear_item, viewGroup,false);

            detailsbt = myview.findViewById(R.id.detailsbt);
            imagetv = myview.findViewById(R.id.imagetv);
            nametv = myview.findViewById(R.id.nametv);
            profetv = myview.findViewById(R.id.profetv);
            nameofversitytv = myview.findViewById(R.id.nameofversitytv);
            dateofdeadtv = myview.findViewById(R.id.dateofdeadtv);
            idnumber = myview.findViewById(R.id.idnumber);


            HashMap <String,String> hashMap = arrayListall.get(i);

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

    //--------------------------------------------------------------------------------------------




    //------------------------------------------------------------------------

    public void JsonArrayMethod(){




        progress_loading.setVisibility(View.VISIBLE);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, List_Fargment.api_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progress_loading.setVisibility(View.GONE);
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

                        hashMapall = new HashMap<>();
                        hashMapall.put("id", id);
                        hashMapall.put("name",name);
                        hashMapall.put("profession",profession);
                        hashMapall.put("versityname",versityname);
                        hashMapall.put("datedead",datedead);
                        hashMapall.put("age",age);
                        hashMapall.put("birthday",birthday);
                        hashMapall.put("placeofbirth",placeofbirth);
                        hashMapall.put("jevabehoyeche",jevabehoyeche);
                        hashMapall.put("biography",biography);
                        hashMapall.put("image",image);
                        arrayListall.add(hashMapall);


                        //Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();

                    }

                    MyAdapterall myAdapterall = new MyAdapterall();
                    listViewall.setAdapter(myAdapterall);








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


}
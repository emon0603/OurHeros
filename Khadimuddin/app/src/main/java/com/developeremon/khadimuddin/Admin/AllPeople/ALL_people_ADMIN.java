package com.developeremon.khadimuddin.Admin.AllPeople;

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
import com.developeremon.khadimuddin.People_details;
import com.developeremon.khadimuddin.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ALL_people_ADMIN extends AppCompatActivity {


    ImageView peopleadd;
    ListView peoplelist;
    TextView noinfoTv;
    RelativeLayout noticelistlay;

    HashMap<String,String> hashMappeople = new HashMap<>();
    ArrayList<HashMap<String,String>> arrayListpeople = new ArrayList<>();
    SharedPreferences sharedPreferences;
    private boolean dataFetched = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_people_admin);


        peopleadd = findViewById(R.id.peopleadd);
        peoplelist = findViewById(R.id.peoplelist);
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





        peopleadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                People_Input_activity.people_edit_Request= "";
                startActivity(new Intent(ALL_people_ADMIN.this, People_Input_activity.class));
            }
        });
    }

    //----------------------------------------------------------------

    private class PeopleAdapter extends BaseAdapter {

        TextView idtv, nametv, numtv;
        CircleImageView profile_image;


        @Override
        public int getCount() {
            return arrayListpeople.size();
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
            View myview = inflater.inflate(R.layout.people_item_layout, parent, false);

            idtv = myview.findViewById(R.id.idtv);
            nametv = myview.findViewById(R.id.nametv);
            numtv = myview.findViewById(R.id.numtv);
            profile_image = myview.findViewById(R.id.profile_image);



            RelativeLayout listlay = myview.findViewById(R.id.peoplelay);
            ImageView morebt = myview.findViewById(R.id.morebt);

            hashMappeople = arrayListpeople.get(position);
            String id = hashMappeople.get("id");
            String name = hashMappeople.get("name");
            String fathername = hashMappeople.get("fathername");
            String num = hashMappeople.get("num");
            String image = hashMappeople.get("image");

            idtv.setText("ID:\n"+id);
            nametv.setText(name );
            numtv.setText(num);
            Picasso.get().load(image).into(profile_image);







            listlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    startActivity(new Intent(ALL_people_ADMIN.this, People_details.class));
                }
            });

            PopupMenu popupMenu = new PopupMenu(ALL_people_ADMIN.this, morebt);
            popupMenu.getMenuInflater().inflate(R.menu.popup_item, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {

                    if (menuItem.getItemId() == R.id.editbt){



                        sharedPreferences = getSharedPreferences(String.valueOf(R.string.app_name),MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.putString("id", id);
                        myEdit.putString("name", name);
                        myEdit.putString("fathername", fathername);
                        myEdit.putString("num", num);
                        myEdit.putString("image", image);
                        myEdit.commit();

                        People_Input_activity.people_edit_Request = "people_edit";
                        startActivity(new Intent(ALL_people_ADMIN.this, People_Input_activity.class));


                    } else {

                        new AlertDialog.Builder(ALL_people_ADMIN.this)
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
        String url = MainActivity.volley_url + "show_all_people.php";

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
                        String name = jsonObject.getString("name");
                        String fathername = jsonObject.getString("fathername");
                        String num = jsonObject.getString("num");
                        String image = jsonObject.getString("image");

                        hashMappeople = new HashMap<>();
                        hashMappeople.put("id", id);
                        hashMappeople.put("name", name);
                        hashMappeople.put("fathername", fathername);
                        hashMappeople.put("num", num);
                        hashMappeople.put("image", image);
                        arrayListpeople.add(hashMappeople);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }

                //----------------------------------------------------------------
                if (arrayListpeople.size() > 0 ){
                    PeopleAdapter peopleAdapter = new PeopleAdapter();
                    peoplelist.setAdapter(peopleAdapter);
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

        String url = MainActivity.volley_url+ "people_delete.php" ;


        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();


        StringRequest deleterequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                if (response.contains("Data Delete Successful")){
                    Toast.makeText(ALL_people_ADMIN.this, response, Toast.LENGTH_SHORT).show();
                    //JsonarrayRequet();
                    onResume();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //chechtv.setText(error.getMessage());
                Toast.makeText(ALL_people_ADMIN.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
        arrayListpeople.clear();
        JsonarrayRequet();
    }

    //----------------------------------------------------------------



}
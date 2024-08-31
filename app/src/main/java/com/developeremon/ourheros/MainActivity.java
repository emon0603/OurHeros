package com.developeremon.ourheros;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    MaterialToolbar materialToolbar;
    NavigationView navigationView;
    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;
    private static final String URL = "YOUR_SERVER_URL"; // এখানে আপনার সার্ভার URL দিন
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_laymain);
        materialToolbar = findViewById(R.id.metarialtoolbar);
        navigationView = findViewById(R.id.navi_view);
        bottomNavigationView = findViewById(R.id.bottomnavi);



        requestQueue = Volley.newRequestQueue(this);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fram_lay, new Home() );
        fragmentTransaction.commit();


        //---------------- DrawerLayout ------------------------------
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, materialToolbar, R.string.opendrawer, R.string.closedrawer);
        drawerLayout.addDrawerListener(toggle);
        //---------------------------------------------------------

        materialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.notification) {

                    Toast.makeText(getApplicationContext(), "Notification", Toast.LENGTH_SHORT).show();


                }


                return true;
            }
        });






        //---------------------- NavigationView --------------------------
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.addinfobt) {

                    drawerLayout.closeDrawer(GravityCompat.START);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fram_lay, new WebWiew() );
                    fragmentTransaction.commit();
                    WebWiew.URL = "https://docs.google.com/forms/d/1J3cGmZu5IZ29bjzB9EM79poZSLkltBlSkWn0QFoi7kY/edit";
                    //Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();


                } else if(item.getItemId() == R.id.Sharebt){

                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this amazing app I found!\n"+"https://drive.google.com/drive/u/0/folders/1UR5Ru-v5pHqtdlA6zxSvLBcMmsrgAPhf");
                    startActivity(Intent.createChooser(shareIntent, "Share via"));

                } else if (item.getItemId()== R.id.ContactUs){



                } else  {

                    drawerLayout.closeDrawer(GravityCompat.START);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fram_lay, new WebWiew() );
                    fragmentTransaction.commit();
                    WebWiew.URL = "https://sites.google.com/view/cartoonduniya-privacy/home";

                }



                return true;
            }
        });

        //---------------------------------------------------------


        //---------------------Toolbar----------------------------


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.Home){





                } else if (item.getItemId() == R.id.Search){

                    startActivity(new Intent(MainActivity.this, Search_Activity.class));
                    finish();

                } else {
                    Toast.makeText(MainActivity.this, "profile", Toast.LENGTH_SHORT).show();
                    //bottomNavigationView.getOrCreateBadge(R.id.profile).clearNumber();

                }


                return true;
            }
        });

    }//---- Oncreate Finish------






    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
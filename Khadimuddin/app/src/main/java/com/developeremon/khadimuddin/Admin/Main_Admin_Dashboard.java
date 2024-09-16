package com.developeremon.khadimuddin.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.developeremon.khadimuddin.Admin.AllPeople.ALL_people_ADMIN;
import com.developeremon.khadimuddin.Admin.Expence.Expence_Activity;
import com.developeremon.khadimuddin.R;

public class Main_Admin_Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin_dashboard);

    }


    public void GotoAdd_ALL_People(View view) {
        startActivity(new Intent(Main_Admin_Dashboard.this, ALL_people_ADMIN.class));
    }

    public void Gotoexpenceactivity(View view) {
        startActivity(new Intent(Main_Admin_Dashboard.this, Expence_Activity.class));
    }
}
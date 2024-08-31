package com.developeremon.ourheros;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

public class View_Details extends AppCompatActivity {

    EditText commented;
    ImageView sendbt,cover_pic;
    TextView tvname,tvprofe,
            tvversity,tvdead,tvage,tvbirthday,tvbirthdayplace,tvjevabehoyeche,tvbiography;

    public static String sname = "";
    public static String sprofe = "";
    public static String sversity = "";
    public static String sdead = "";
    public static String sage = "";
    public static String sbirthday = "";
    public static String sbirthdayplace = "";
    public static String sjevabehoyeche = "";
    public static String sbiography = "";
    public static String scover_pic = "";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);


        //----------------------------------------------
        commented = findViewById(R.id.commented);
        sendbt = findViewById(R.id.sendbt);
        cover_pic = findViewById(R.id.cover_pic);
        tvname = findViewById(R.id.tvname);
        tvprofe = findViewById(R.id.tvprofe);
        tvversity = findViewById(R.id.tvversity);
        tvdead = findViewById(R.id.tvdead);
        tvage = findViewById(R.id.tvage);
        tvbirthday = findViewById(R.id.tvbirthday);
        tvbirthdayplace = findViewById(R.id.tvbirthdayplace);
        tvjevabehoyeche = findViewById(R.id.tvjevabehoyeche);
        tvbiography = findViewById(R.id.tvbiography);

        //--------------------------------------------------

        tvname.setText(sname);
        tvprofe.setText(sprofe);
        tvversity.setText(sversity);
        tvdead.setText("মৃত্যু তারিখ : "+sdead);
        tvage.setText("বয়স : "+sage);
        tvbirthday.setText("জন্ম তারিখ : "+sbirthday);
        tvbirthdayplace.setText("জন্মস্থান : "+sbirthdayplace);
        tvjevabehoyeche.setText("যেভাবে শহীদ হয়েছেন :\n\n"+sjevabehoyeche);
        tvbiography.setText("ব্যক্তিগত জীবন :\n\n"+sbiography);
        Picasso.get().load(scover_pic).into(cover_pic);






        //===============================================================================================================
        commented.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing before text changed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do nothing during text changed
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    sendbt.setVisibility(ImageView.VISIBLE);
                } else {
                    sendbt.setVisibility(ImageView.GONE);
                }
            }
        });
        //---------------------------------------------------------




    }
}
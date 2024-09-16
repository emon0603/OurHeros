package com.developeremon.khadimuddin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.developeremon.khadimuddin.Admin.Login;
import com.developeremon.khadimuddin.DatePicker.DatePick;

public class MainActivity extends AppCompatActivity {
    TextView datetv;
    ImageView adminbt;
    public static String volley_url = "http://192.168.0.104/Mosjid/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datetv = findViewById(R.id.datetv);
        adminbt = findViewById(R.id.adminbt);

        DatePickerMethod();


    }

    private void DatePickerMethod(){
        String arabicDate = DatePick.getBengaliHijriDate();
        String englishDate = DatePick.getEnglishDate();
        String banglaFullDate = DatePick.getBanglafullDate();
        datetv.setText(arabicDate+"\n"+englishDate+ "\nবাংলা " + banglaFullDate);
    }


    public void GoToAdminActivity(View view) {
        startActivity(new Intent(MainActivity.this, Login.class));
    }

    public void GotoNoticeBoard(View view) {
        startActivity(new Intent(MainActivity.this, NoticeList.class));

    }

    public void Gotoquran(View view) {

        startActivity(new Intent(MainActivity.this, Quran_PDF.class));

    }

    public void GoToAllPeopleActivity(View view) {

        startActivity(new Intent(MainActivity.this, People_Activity.class));

    }



    //-----------------------------------------------------------------------------
}
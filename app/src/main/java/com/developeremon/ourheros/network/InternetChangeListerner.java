package com.developeremon.ourheros.network;


import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

import com.developeremon.ourheros.R;

public class InternetChangeListerner extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View layout_diolog = LayoutInflater.from(context).inflate(R.layout.no_internet, null);
        builder.setView(layout_diolog);

        AppCompatButton btnretry = layout_diolog.findViewById(R.id.retrybt);

        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.setCancelable(false);

        dialog.getWindow().setGravity(Gravity.CENTER);

        btnretry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                onReceive(context,intent);
            }
        });


    }
}

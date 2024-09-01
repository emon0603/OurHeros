package com.developeremon.ourheros;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

public class Common_internet {

    public static boolean isConnected(Context context) {


        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {

            NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
            if (networkInfos != null) {


                for (int x = 0; x < networkInfos.length; x++) {

                    if (networkInfos[x].getState() == NetworkInfo.State.CONNECTED) ;

                    return true;


                }

            }


        }


        return false;
    }


}

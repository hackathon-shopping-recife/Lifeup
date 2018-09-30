package com.lifeup.recpontos.util;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class Util {

    public static boolean isConnected(Activity activity){
        ConnectivityManager connMgr = (ConnectivityManager) activity.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            return true;
        }else{
            Toast.makeText(activity, "EITA! Verifique sua conexão e tente novamente!", Toast.LENGTH_LONG).show();
            return false;
        }
    }

}

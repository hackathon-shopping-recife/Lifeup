package com.lifeup.recpontos.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.lifeup.recpontos.R;
import com.lifeup.recpontos.util.Constant;

import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {


    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.generateToken();

    }

    private void generateToken() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constant.LOGIN, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(Constant.TOKEN_, null);
        if (token == null) {
            AsyncTask asyncTask = new AsyncTask() {
                private boolean ok = false;

                /**
                 * THIS STEP GETS THE GOOGLE API CODE
                 * @param params
                 * @return
                 */
                @Override
                protected Object doInBackground(Object[] params) {
                    try {
                        InstanceID instanceID = InstanceID.getInstance(SplashActivity.this);
                        token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                                GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

                        if (token != null) {
                            Constant.TOKEN_ = token;
                        }

                        this.ok = true;
                    } catch (Exception e) {
                        this.ok = false;
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Object o) {
                    super.onPostExecute(o);
                    if (token != null) {
                        Log.d("TOKEN: ", token);
                    }else{
                        Log.d("TOKEN: ", "NULL");
                    }

                    Constant.TOKEN_ = token;
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    // close splash activity
                    finish();

                }
            };

            asyncTask.execute(null, null, null);
        }
    }
}

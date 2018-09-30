package com.lifeup.recpontos.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.lifeup.recpontos.R;
import com.lifeup.recpontos.model.request.Request;
import com.lifeup.recpontos.ui.fragments.Discount;
import com.lifeup.recpontos.ui.fragments.Home;
import com.lifeup.recpontos.ui.fragments.Purchase;
import com.lifeup.recpontos.util.Constant;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity{

    private  FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private RelativeLayout home;
    private Fragment discount;
    private Fragment purchase;
    private CallbackManager callbackManager;
    private AccessToken accessToken;
    private FrameLayout frameLayoutMain;
    private LoginButton loginButton;
    private TextView pontos;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_main);
        callbackManager = CallbackManager.Factory.create();
        home = findViewById(R.id.home_layout);
        frameLayoutMain = findViewById(R.id.frameLayoutMain);
        pontos = findViewById(R.id.pontos);
        Request.getInstance().getTotalValue(MainActivity.this, pontos);


        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        this.addCommitFragments();

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("public_profile"));
            }
        });

        loginButton.setReadPermissions("email");

        // Callback registration
        loginButton.registerCallback(getCallbackManager(), new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        boolean isLoggedIn = getAccessToken() != null && !getAccessToken().isExpired();

        accessToken = AccessToken.getCurrentAccessToken();
        navigation.setSelectedItemId(R.id.navigation_dashboard);
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    private void addCommitFragments(){
        //home = new Home();
        //fragmentTransaction.add(R.id.frameLayoutMain, home, Constant.HOME);
        discount = new Discount();
        fragmentTransaction.add(R.id.frameLayoutMain, discount, Constant.DISCOUNT);
        purchase = new Purchase();
        fragmentTransaction.add(R.id.frameLayoutMain, purchase, Constant.PURCHASE);

        fragmentTransaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getCallbackManager().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    home.setVisibility(View.VISIBLE);
                    frameLayoutMain.setVisibility(View.GONE);
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.hide(discount);
                    fragmentTransaction.hide(purchase);
                    fragmentTransaction.commit();
                    //animateTextView(0, 450, pontos);
                    return true;
                case R.id.navigation_dashboard:
                    home.setVisibility(View.GONE);
                    frameLayoutMain.setVisibility(View.VISIBLE);
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.hide(purchase);
                    fragmentTransaction.show(discount);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_notifications:
                    home.setVisibility(View.GONE);
                    frameLayoutMain.setVisibility(View.VISIBLE);
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.hide(discount);
                    fragmentTransaction.show(purchase);
                    fragmentTransaction.commit();
                    return true;
                case R.id.menu_item_share:
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    String texto = "Olá, o Shopping recife está com a ótima iniciativa de trocar pontos arrecadados no app por descontos e várias promoções que podem ser vistos também no App! Venha aproveitar dessa novidade, baixe o RECOIN!!! Instale o app: ";
                    sendIntent.putExtra(Intent.EXTRA_TEXT, texto);
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                    return true;
            }
            return false;
        }
    };

    public void animateTextView(int initialValue, int finalValue, final TextView textview) {
        DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator(0.2f);
        int start = Math.min(initialValue, finalValue);
        int end = Math.max(initialValue, finalValue);
        int difference = Math.abs(finalValue - initialValue);
        Handler handler = new Handler();
        for (int count = start; count <= end; count++) {
            int time = Math.round(decelerateInterpolator.getInterpolation((((float) count) / difference)) * 50) * count;
            final int finalCount = ((initialValue > finalValue) ? initialValue - count : count);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textview.setText(String.valueOf(finalCount));
                }
            }, time);
        }
    }

    public CallbackManager getCallbackManager() {
        return callbackManager;
    }

    public void setCallbackManager(CallbackManager callbackManager) {
        this.callbackManager = callbackManager;
    }
}

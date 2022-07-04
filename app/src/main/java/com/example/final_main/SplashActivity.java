package com.example.final_main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.database.core.Context;

public class SplashActivity extends AppCompatActivity {
    Handler handler;
    private SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String FIRSTNAME = "firstName";
    public static final String LASTNAME = "lastName";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String ISLOGGEDIN = "isLoggedIn";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
                String isLoggedIn = sharedPreferences.getString(ISLOGGEDIN,"");
                if(isLoggedIn.equals("true")){
                    Intent intent = new Intent(SplashActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },2000);
    }
}
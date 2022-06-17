package com.example.final_main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private landingFragment lf;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        lf = new landingFragment();
        transaction.replace(R.id.flMain, lf);
        transaction.addToBackStack(null);
        transaction.commit();

    }

}
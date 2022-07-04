package com.example.final_main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private LogInFragment lif;
    private SignUpFragment suf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        lif = new LogInFragment();
        transaction.replace(R.id.flMain, lif);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            lif = new LogInFragment();
            transaction.replace(R.id.flMain, lif);
            transaction.commit();
        }
    }
}
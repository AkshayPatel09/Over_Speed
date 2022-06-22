package com.example.final_main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final_main.ui.gallery.GalleryFragment;
import com.example.final_main.ui.slideshow.SlideshowFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.final_main.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;
    private Historyfragment historyfragment;
    private GalleryFragment galleryFragment; // (Settings Fragment)
    private SlideshowFragment slideshowFragment; // (Contact us Fragment)
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

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferences = getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);

        String firstName = sharedPreferences.getString(FIRSTNAME,"");
        String lastName = sharedPreferences.getString(LASTNAME,"");
        String email = sharedPreferences.getString(EMAIL,"");

        setSupportActionBar(binding.appBarHome.toolbar);
        binding.appBarHome.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;


        View headerView = navigationView.getHeaderView(0);
        // Get Username and Email TextViews
        TextView userName = headerView.findViewById(R.id.userName);
        TextView userEmail = headerView.findViewById(R.id.userEmail);
        // set user name and email
        userName.setText(firstName+" "+lastName);
        userEmail.setText(email);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_history, R.id.nav_settings, R.id.nav_contactus)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId() == R.id.nav_history)
                {
                    Toast.makeText(HomeActivity.this, "History!!", Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction = HomeActivity.this.getSupportFragmentManager().beginTransaction();
                    historyfragment = new Historyfragment();
                    transaction.replace(R.id.flNav, historyfragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                else if(item.getItemId() == R.id.nav_settings)
                {
                    Toast.makeText(HomeActivity.this, "settings!!", Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction = HomeActivity.this.getSupportFragmentManager().beginTransaction();
                    galleryFragment = new GalleryFragment();
                    transaction.replace(R.id.flNav, galleryFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                else if(item.getItemId() == R.id.nav_contactus)
                {
                    Toast.makeText(HomeActivity.this, "contactus", Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction = HomeActivity.this.getSupportFragmentManager().beginTransaction();
                    slideshowFragment = new SlideshowFragment();
                    transaction.replace(R.id.flNav, slideshowFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                }
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
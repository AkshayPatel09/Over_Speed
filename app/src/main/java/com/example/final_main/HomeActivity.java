package com.example.final_main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final_main.ui.gallery.GalleryFragment;
import com.example.final_main.ui.home.HomeFragment;
import com.example.final_main.ui.slideshow.SlideshowFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
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
    private HomeFragment homeFragment;
    private Historyfragment historyfragment;

    private ContactUsFragment contactUsFragment;
    private EditProfileFragment editProfileFragment;
    private AboutusFragment aboutusFragment;
    private PrivacyPolicyFragment privacyPolicyFragment;
    private TermsandConditionFragment termsandConditionFragment;
    private SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs";
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
        sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);

        String firstName = sharedPreferences.getString(FIRSTNAME, "");
        String lastName = sharedPreferences.getString(LASTNAME, "");
        String email = sharedPreferences.getString(EMAIL, "");

        setSupportActionBar(binding.appBarHome.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;


        View headerView = navigationView.getHeaderView(0);
        // Get Username and Email TextViews
        TextView userName = headerView.findViewById(R.id.userName);
        TextView userEmail = headerView.findViewById(R.id.userEmail);
        // set user name and email
        userName.setText(firstName + " " + lastName);
        userEmail.setText(email);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_history, R.id.nav_contactus,R.id.nav_termsandcondition,R.id.nav_aboutus,R.id.nav_editprofile,R.id.nav_logOut,
                R.id.nav_notification,R.id.changePassword,R.id.nav_privacypolicy)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flNav, new Historyfragment()).commit();
            navigationView.setCheckedItem(R.id.nav_history);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.nav_history) {

                    //Toast.makeText(HomeActivity.this, "History!!", Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction = HomeActivity.this.getSupportFragmentManager().beginTransaction();
                    navigationView.setCheckedItem(R.id.nav_history);
                    historyfragment = new Historyfragment();
                    transaction.replace(R.id.flNav, historyfragment);
//                    transaction.addToBackStack(null);
                    transaction.commit();
                    drawer.closeDrawers();
                } else if (item.getItemId() == R.id.nav_contactus) {
                    //getSupportActionBar().setTitle("your");
                    //Toast.makeText(HomeActivity.this, "contactus", Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction = HomeActivity.this.getSupportFragmentManager().beginTransaction();
                    navigationView.setCheckedItem(R.id.nav_contactus);
                    contactUsFragment = new ContactUsFragment();
                    transaction.replace(R.id.flNav, contactUsFragment);
//                    transaction.addToBackStack(null);
                    transaction.commit();
                    drawer.closeDrawers();

                } else if (item.getItemId() == R.id.nav_editprofile) {
                    //Toast.makeText(HomeActivity.this, "Edit Profile", Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction = HomeActivity.this.getSupportFragmentManager().beginTransaction();
                    navigationView.setCheckedItem(R.id.nav_editprofile);
                    editProfileFragment = new EditProfileFragment();
                    transaction.replace(R.id.flNav, editProfileFragment);
//                    transaction.addToBackStack(null);
                    transaction.commit();
                    drawer.closeDrawers();
                } else if (item.getItemId() == R.id.nav_aboutus) {
                    //Toast.makeText(HomeActivity.this, "About us", Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction = HomeActivity.this.getSupportFragmentManager().beginTransaction();
                    navigationView.setCheckedItem(R.id.nav_aboutus);
                    aboutusFragment = new AboutusFragment();
                    transaction.replace(R.id.flNav, aboutusFragment);
//                    transaction.addToBackStack(null);
                    transaction.commit();
                    drawer.closeDrawers();

                } else if (item.getItemId() == R.id.nav_privacypolicy) {
                    //Toast.makeText(HomeActivity.this, "Privacy policy", Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction = HomeActivity.this.getSupportFragmentManager().beginTransaction();
                    navigationView.setCheckedItem(R.id.nav_privacypolicy);
                    privacyPolicyFragment = new PrivacyPolicyFragment();
                    transaction.replace(R.id.flNav, privacyPolicyFragment);
//                    transaction.addToBackStack(null);
                    transaction.commit();
                    drawer.closeDrawers();
                } else if (item.getItemId() == R.id.nav_termsandcondition) {
                    //Toast.makeText(HomeActivity.this, "Terms and Conditions", Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction = HomeActivity.this.getSupportFragmentManager().beginTransaction();
                    navigationView.setCheckedItem(R.id.nav_termsandcondition);
                    termsandConditionFragment = new TermsandConditionFragment();
                    transaction.replace(R.id.flNav, termsandConditionFragment);
//                    transaction.addToBackStack(null);
                    transaction.commit();
                    drawer.closeDrawers();
                } else if (item.getItemId() == R.id.nav_logOut) {
                    //Toast.makeText(HomeActivity.this, "Logout!!", Toast.LENGTH_SHORT).show();
                    logOut();
                    drawer.closeDrawers();
                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else if (item.getItemId() == R.id.changePassword) {
                    FragmentTransaction transaction = HomeActivity.this.getSupportFragmentManager().beginTransaction();
                    navigationView.setCheckedItem(R.id.changePassword);
                    ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
                    transaction.replace(R.id.flNav, changePasswordFragment);
//                    transaction.addToBackStack(null);
                    transaction.commit();
                    drawer.closeDrawers();
                } else if (item.getItemId() == R.id.nav_notification) {
                    FragmentTransaction transaction = HomeActivity.this.getSupportFragmentManager().beginTransaction();
                    navigationView.setCheckedItem(R.id.nav_notification);
                    NotificationFragment notificationFragment = new NotificationFragment();
                    transaction.replace(R.id.flNav, notificationFragment);
                    transaction.commit();
                    drawer.closeDrawers();
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    private void logOut() {
        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FIRSTNAME, "");
        editor.putString(LASTNAME, "");
        editor.putString(EMAIL, "");
        editor.putString(PHONE, "");
        editor.putString(ISLOGGEDIN, "false");
        editor.commit();
    }
}
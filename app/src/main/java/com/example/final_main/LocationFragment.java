package com.example.final_main;


import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LocationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationFragment extends Fragment {

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String NOTIFICATION = "notification";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    int PERMISSION_ID = 44;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FusedLocationProviderClient mFusedLocationClient;
    private AppCompatTextView lat, log, speed;
    private GoogleMap gMap;
    private Marker marker;
    private SharedPreferences sharedPreferences;
    private String notification;
    private MaterialButton stopTracking;
    private boolean notificationFlag=false;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private HistoryData historyData;
    private LocationCallback mLocationCallback = new LocationCallback() {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
//            Toast.makeText(getActivity(), "/////", Toast.LENGTH_SHORT).show();
            LatLng latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

            MarkerOptions markerOptions = new MarkerOptions().position(latLng);
            if (marker == null) {
                marker = gMap.addMarker(markerOptions);
//                marker.setIcon(BitmapDescriptorFactory.f);
                gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f));
            } else {
                marker.setPosition(latLng);
                gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f));
            }

            lat.setText("Latitude: " + mLastLocation.getLatitude() + "");
            log.setText("Longitude: " + mLastLocation.getLongitude() + "");
            speed.setText("Speed: " + Integer.toString((int) mLastLocation.getSpeed()) + " Kmph");
//            Toast.makeText(getActivity(), notification, Toast.LENGTH_SHORT).show();

            if ((int) mLastLocation.getSpeed() >= 0  && notification.equals("true") && notificationFlag==false) {
                historyData = new HistoryData();
                historyData.setYourSpeed(Integer.toString((int) mLastLocation.getSpeed()));
                historyData.setSpeedLimit(Integer.toString(40));
                historyData.setLatitude(Double.toString(mLastLocation.getLatitude()));
                historyData.setLongitude(Double.toString(mLastLocation.getLongitude()));
                historyData.setDate(java.time.LocalDate.now().toString());
                SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss z");
                Date date = new Date();
                historyData.setTime(sd.format(date));
                sd.setTimeZone(TimeZone.getTimeZone("IST"));
                notificationFlag=true;
                addNotification();
                addDataToFireBase(historyData,sharedPreferences.getString(LogInFragment.KEY,""));
                Toast.makeText(getActivity(), "data saved!!", Toast.LENGTH_SHORT).show();
            }else if((int) mLastLocation.getSpeed() >= 0  && notification.equals("false") && notificationFlag==false){
                historyData = new HistoryData();
                historyData.setYourSpeed(Integer.toString((int) mLastLocation.getSpeed()));
                historyData.setSpeedLimit(Integer.toString(40));
                historyData.setLatitude(Double.toString(mLastLocation.getLatitude()));
                historyData.setLongitude(Double.toString(mLastLocation.getLongitude()));
                historyData.setDate(java.time.LocalDate.now().toString());
                SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss z");
                Date date = new Date();
                historyData.setTime(sd.format(date));
                sd.setTimeZone(TimeZone.getTimeZone("IST"));
                notificationFlag=true;
                addDataToFireBase(historyData,sharedPreferences.getString(LogInFragment.KEY,""));
                Toast.makeText(getActivity(), "data saved!!", Toast.LENGTH_SHORT).show();
            }

        }
    };


    public LocationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LocationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocationFragment newInstance(String param1, String param2) {
        LocationFragment fragment = new LocationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        lat = view.findViewById(R.id.lat);
        log = view.findViewById(R.id.log);
        speed = view.findViewById(R.id.speed);
        stopTracking = view.findViewById(R.id.stopTracking);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("HistoryArr");
        sharedPreferences = getActivity().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        notification = sharedPreferences.getString(NOTIFICATION, "");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getActivity().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        SupportMapFragment supportMapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                gMap = googleMap;

            }
        });
        getLastLocation();

        stopTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDestroy();
                FragmentManager transaction = getActivity().getSupportFragmentManager();
                transaction.popBackStack();
            }
        });

        return view;
    }

    private void addDataToFireBase(HistoryData historyData, String key) {

        databaseReference.child(key).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    List<HistoryData> x = (List<HistoryData>) task.getResult().getValue();
                    if(x==null){
                        List<HistoryData> historyArr = new ArrayList<HistoryData>();
                        historyArr.add(historyData);
                        databaseReference.child(key).setValue(historyArr);
                    }else {
                        x.add(historyData);
                        databaseReference.child(key).setValue(x);
                    }

                    Toast.makeText(getActivity(), String.valueOf(task.getResult().getValue()), Toast.LENGTH_SHORT).show();
//                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });
        Toast.makeText(getActivity(), key, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
    }

    private void stopLocationUpdates(){
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                requestNewLocationData();
//                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Location> task) {
//                        Location location = task.getResult();
//                        if (location == null) {
//                            requestNewLocationData();
//                        } else {
//
//                            lat.setText(location.getLatitude() + "");
//                            log.setText(location.getLongitude() + "");
//                            Toast.makeText(getActivity(), "...", Toast.LENGTH_SHORT).show();
//                            requestNewLocationData();
//
//                        }
//                    }
//                });
            } else {
                Toast.makeText(getActivity(), "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
//            Toast.makeText(getActivity(), "requesting permissions!!!", Toast.LENGTH_SHORT).show();
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(1000);
//        mLocationRequest.setNumUpdates(5);
        mLocationRequest.setNumUpdates(Integer.MAX_VALUE);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }


    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getActivity(), "My Notification")
                        .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                        .setContentTitle("Go Slow!!")
                        .setContentText("You are over speeding!!").setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getActivity());
        managerCompat.notify(1, builder.build());
    }
}
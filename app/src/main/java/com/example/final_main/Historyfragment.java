package com.example.final_main;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.final_main.databinding.ActivityHomeBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Historyfragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Historyfragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SharedPreferences sharedPreferences;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String NOTIFICATION = "notification";
    public static final String KEY = "key";
    private String key;
    private List<HistoryData> historyArr;
    private RecyclerView historyList;
    private AppCompatTextView noData;


    public Historyfragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Historyfragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Historyfragment newInstance(String param1, String param2) {
        Historyfragment fragment = new Historyfragment();
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
        View view = inflater.inflate(R.layout.fragment_historyfragment, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("HistoryArr");
        sharedPreferences = getActivity().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        key = sharedPreferences.getString(KEY, "");
        historyList = view.findViewById(R.id.historyList);
        noData = view.findViewById(R.id.noHistoryData);
        //Toast.makeText(getActivity(), key, Toast.LENGTH_SHORT).show();

        databaseReference.child(key).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    historyArr = (List<HistoryData>) task.getResult().getValue();
                    showHistory(historyArr);
//                    Toast.makeText(getActivity(), Integer.toString(historyArr.size()), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getActivity(), String.valueOf(task.getResult().getValue()), Toast.LENGTH_SHORT).show();
                    //Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                LocationFragment locationFragment = new LocationFragment();
                transaction.replace(R.id.flNav, locationFragment).addToBackStack("History").commit();
            }
        });

        return view;
    }

    private void showHistory(List<HistoryData> historyArr) {
        if (historyArr != null) {
            MyAdapter myAdapter = new MyAdapter(getActivity(), historyArr, historyArr.size());
            historyList.setAdapter(myAdapter);
            historyList.setLayoutManager(new LinearLayoutManager(getActivity()));
        } else {
            noData.setText("No Previous Records !!");
        }
    }


}
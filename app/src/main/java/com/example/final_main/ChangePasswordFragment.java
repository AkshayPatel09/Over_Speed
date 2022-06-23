package com.example.final_main;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangePasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangePasswordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextInputEditText oldPassword,newPassword,confirmNewPassword;
    private MaterialButton changePasswordBtn;
    private SharedPreferences sharedPreferences;
    public static final String PASSWORD = "password";
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String EMAIL = "email";
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment changePasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangePasswordFragment newInstance(String param1, String param2) {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
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
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        oldPassword = view.findViewById(R.id.changePasswordOld);
        newPassword = view.findViewById(R.id.changePasswordNew);
        confirmNewPassword = view.findViewById(R.id.changePasswordNewRe);
        changePasswordBtn = view.findViewById(R.id.changePasswordBtn);
        sharedPreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UserInfo");
        String curPassword = sharedPreferences.getString(PASSWORD,"");
        String curEmail = sharedPreferences.getString(EMAIL,"");

        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String regexPassword =  "^(?=.*[0-9])"
                        + "(?=.*[a-z])(?=.*[A-Z])"
                        + "(?=.*[@#$%^&+=])"
                        +  "(?=\\S+$).{8,12}$";;
                Pattern patternPassword= Pattern.compile(regexPassword);
                Matcher passwordMatcher = patternPassword.matcher(newPassword.getText().toString());
                if(TextUtils.isEmpty(oldPassword.getText().toString()) || TextUtils.isEmpty(newPassword.getText().toString()) || TextUtils.isEmpty(confirmNewPassword.getText().toString())){
                    Toast.makeText(getActivity(), "Please fill all the fields!!", Toast.LENGTH_SHORT).show();
                }else if(!oldPassword.getText().toString().equals(curPassword)){
                    Toast.makeText(getActivity(), "Old Password is incorrect!!", Toast.LENGTH_SHORT).show();
                }else if(!passwordMatcher.matches()){
                    Toast.makeText(getActivity(), "Password format is incorrect!!", Toast.LENGTH_SHORT).show();
                    newPassword.setError("Password format is incorrect!!");
                }else if(!newPassword.getText().toString().equals(confirmNewPassword.getText().toString())){
                    Toast.makeText(getActivity(), "Confirm Password is not matching!!", Toast.LENGTH_SHORT).show();
                    confirmNewPassword.setError("Confirm Password is not matching!!");
                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(PASSWORD,newPassword.getText().toString());
                    editor.commit();
                    Query query = databaseReference.orderByChild("email").equalTo(curEmail);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                for (DataSnapshot messageSnapshot: snapshot.getChildren()) {
                                    String uId = messageSnapshot.getKey();
                                    HashMap hashMap = new HashMap();
                                    hashMap.put("password",newPassword.getText().toString());


                                    databaseReference.child(uId).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                                        @Override
                                        public void onSuccess(Object o) {
                                            Toast.makeText(getActivity(),"Your password is successfully updated!!",Toast.LENGTH_SHORT).show();
                                            oldPassword.setText("");
                                            newPassword.setText("");
                                            confirmNewPassword.setText("");
                                        }
                                    });
                                }
                            } else{
                                Toast.makeText(getActivity(), "Account doesn't exist!!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });

                }
            }
        });

        return view;
    }
}
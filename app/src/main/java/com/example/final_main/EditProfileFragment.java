package com.example.final_main;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final_main.databinding.ActivityHomeBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
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
 * Use the {@link EditProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String FIRSTNAME = "firstName";
    public static final String LASTNAME = "lastName";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    private EditText fName,lName,phone;
    private Button btnApplyChanges;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    UserInfo userInfo;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProfileFragment newInstance(String param1, String param2) {
        EditProfileFragment fragment = new EditProfileFragment();
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
        View v = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        sharedPreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String firstName = sharedPreferences.getString(FIRSTNAME,"");
        String lastName = sharedPreferences.getString(LASTNAME,"");
        String email = sharedPreferences.getString(EMAIL,"");
        String phn = sharedPreferences.getString(PHONE,"");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UserInfo");
        userInfo = new UserInfo();


        fName = v.findViewById(R.id.edtfirstName);
        lName = v.findViewById(R.id.edtlastName);
        phone = v.findViewById(R.id.edtphone);
        btnApplyChanges = v.findViewById(R.id.btnapply);

        fName.setText(firstName);
        lName.setText(lastName);
        phone.setText(phn);

        btnApplyChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String regexPhone = "(0/91)?[7-9][0-9]{9}";
                Pattern patternPhone= Pattern.compile(regexPhone);
                Matcher phoneMatcher = patternPhone.matcher(phone.getText().toString());

                if(fName.getText().toString().length()==0 || lName.getText().toString().length()==0 || phone.getText().toString().length()==0){
                    Toast.makeText(getActivity(), "Please fill all the fields!!", Toast.LENGTH_SHORT).show();
                }
                else if(!phoneMatcher.matches()){
                    Toast.makeText(getActivity(), "Phone number format is incorrect!!", Toast.LENGTH_SHORT).show();
                    phone.setError("Phone number format is incorrect!!");
                }
                else
                {
                    Query query = databaseReference.orderByChild("email").equalTo(email);
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                for (DataSnapshot messageSnapshot: snapshot.getChildren()) {
                                    String uId = messageSnapshot.getKey();
                                    HashMap hashMap = new HashMap();
                                    hashMap.put("firstName",fName.getText().toString());
                                    hashMap.put("lastName",lName.getText().toString());
                                    hashMap.put("phone",phone.getText().toString());
                                    databaseReference.child(uId).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                                        @Override
                                        public void onSuccess(Object o) {
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString(FIRSTNAME,fName.getText().toString());
                                            editor.putString(LASTNAME,lName.getText().toString());
                                            editor.putString(PHONE,phone.getText().toString());
                                            editor.commit();
                                            Toast.makeText(getActivity(),"Your Data is Successfully updated",Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getActivity(),HomeActivity.class);
                                            startActivity(intent);
                                            getActivity().finish();
                                        }
                                    });

                                }
                            }
                            else{
                                Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });


        return v;
    }
}
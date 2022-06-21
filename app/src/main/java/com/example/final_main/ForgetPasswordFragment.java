package com.example.final_main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForgetPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForgetPasswordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private TextView email;
    private Button sendLink;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    public ForgetPasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForgetPasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForgetPasswordFragment newInstance(String param1, String param2) {
        ForgetPasswordFragment fragment = new ForgetPasswordFragment();
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
        View v = inflater.inflate(R.layout.fragment_forget_password, container, false);
        email = v.findViewById(R.id.forgetPasswordEmail);
        sendLink = v.findViewById(R.id.sendLink);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UserInfo");

        sendLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(email.getText().toString())) {
                    Toast.makeText(getActivity(), "Please enter email!!", Toast.LENGTH_SHORT).show();
                    email.setError("Please enter email!!");
                } else {
                    Query query = databaseReference.orderByChild("email").equalTo(email.getText().toString());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                                    String dbEmail = (String) messageSnapshot.child("email").getValue();
                                    if (dbEmail.equals(email.getText().toString())) {
                                        Toast.makeText(getActivity(), "Link have been send to your email!!", Toast.LENGTH_SHORT).show();
                                        ActionCodeSettings actionCodeSettings =
                                                ActionCodeSettings.newBuilder()
                                                        // URL you want to redirect back to. The domain (www.example.com) for this
                                                        // URL must be whitelisted in the Firebase Console.
                                                        .setUrl("https://www.finalmain-3615a.firebaseapp.com/verify?uid=WvDqFct5h0ckJzhcARCorAt0aIj2")
                                                        // This must be true
                                                        .setHandleCodeInApp(true)
                                                        .setAndroidPackageName(
                                                                "com.example.final_main",
                                                                true, /* installIfNotAvailable */
                                                                "8"    /* minimumVersion */)
                                                        .build();
                                                    FirebaseAuth auth = FirebaseAuth.getInstance();
                                                    auth.sendSignInLinkToEmail(email.getText().toString(), actionCodeSettings)
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        Toast.makeText(getActivity(), "Link send!!!", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            });
                                        /**
                                         *
                                         */
                                    }

                                }
                            } else {
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

        return v;
    }
}
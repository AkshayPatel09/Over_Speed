package com.example.final_main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class LogInFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText EdtEmail;
    private EditText EdtPassword;

    private Button BtnLogin;
    private TextView NewAccount;
    private String Email;
    private String Password;

    private SignUpFragment signUpFragment;

    public LogInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LogInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LogInFragment newInstance(String param1, String param2) {
        LogInFragment fragment = new LogInFragment();
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

         View view =inflater.inflate(R.layout.fragment_log_in, container, false);
         EdtEmail = view.findViewById(R.id.edt_email);
         EdtPassword = view.findViewById(R.id.edt_pass);
         BtnLogin = view.findViewById(R.id.login_btn);
         NewAccount = view.findViewById(R.id.new_acc);

         Email = EdtEmail.getText().toString();
         Password = EdtPassword.getText().toString();


         NewAccount.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                 signUpFragment = new SignUpFragment();
                 transaction.replace(R.id.flMain, signUpFragment);
                 transaction.addToBackStack(null);
                 transaction.commit();
             }
         });
         return view;
    }

}
package com.example.mangareader;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AccountFragment extends Fragment {

    Button loginBtn, signup;
    Fragment fragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_account, container, false);

        loginBtn = view.findViewById(R.id.frag_btn_login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new LoginFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft  = fm.beginTransaction();
                ft.replace(R.id.fragment_login_signup, fragment);
                ft.commit();
            }
        });

        signup = view.findViewById(R.id.frag_btn_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button login = view.findViewById(R.id.loginBtn);
                login.setVisibility(View.INVISIBLE);
                fragment = new SignupFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft  = fm.beginTransaction();
                ft.replace(R.id.fragment_login_signup, fragment);
                ft.commit();
            }
        });

        return view;
    }
}

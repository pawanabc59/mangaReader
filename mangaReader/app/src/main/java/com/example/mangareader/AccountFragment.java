package com.example.mangareader;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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
        signup = view.findViewById(R.id.frag_btn_signup);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

                loginBtn.setBackground(getActivity().getDrawable(R.drawable.btn_gradient_style2));
                signup.setBackground(getActivity().getDrawable(R.drawable.btn_default_style));
                fragment = new LoginFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft  = fm.beginTransaction();
                ft.replace(R.id.fragment_login_signup, fragment);
                ft.commit();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                Button login = view.findViewById(R.id.loginBtn);
                login.setVisibility(View.INVISIBLE);
                loginBtn.setBackground(getActivity().getDrawable(R.drawable.btn_default_style));
                signup.setBackground(getActivity().getDrawable(R.drawable.btn_gradient_style2));
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

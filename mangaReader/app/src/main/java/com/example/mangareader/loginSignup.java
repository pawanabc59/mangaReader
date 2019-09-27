package com.example.mangareader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class loginSignup extends AppCompatActivity {

//    Delete this file afterwards. No use of this file this file's content is similar to account fragment file since instead of activity we are using fragments.

    Button login, signup;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        login = findViewById(R.id.frag_btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_SHORT).show();
//                fragment = new LoginFragment();
//                FragmentManager fm = getSupportFragmentManager();
//                FragmentTransaction ft  = fm.beginTransaction();
//                ft.replace(R.id.fragment_login_signup, fragment);
//                ft.commit();
            }
        });

        signup = findViewById(R.id.frag_btn_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "clicked 1", Toast.LENGTH_SHORT).show();

//                login.setVisibility(View.INVISIBLE);
//                fragment = new SignupFragment();
//                FragmentManager fm = getSupportFragmentManager();
//                FragmentTransaction ft  = fm.beginTransaction();
//                ft.replace(R.id.fragment_login_signup, fragment);
//                ft.commit();
            }
        });

    }

}

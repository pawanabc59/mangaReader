package com.example.mangareader.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.mangareader.ConnectionManager;
import com.example.mangareader.R;
import com.example.mangareader.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.mangareader.Constants.url_login;

public class LoginFragment extends Fragment {

    private EditText email, password;
    private Button btn_login;
    private ProgressBar login_loading;

    SessionManager sessionManager;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        sessionManager = new SessionManager(getContext());

        email = view.findViewById(R.id.login_email);
        password = view.findViewById(R.id.login_password);
        btn_login = view.findViewById(R.id.loginBtn);
        login_loading = view.findViewById(R.id.login_loading);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String memail = email.getText().toString().trim();
                String mpassword = password.getText().toString().trim();

                if (memail.isEmpty()){
                    email.setError("Please insert email");
                }
                else if (mpassword.isEmpty()){
                    password.setError("Please insert password");
                }
                else if (!memail.matches(emailPattern)){
                    email.setError("Please provide valid email address");
                }
                else if (mpassword.length()<6){
                    password.setError("Password is too short");
                }
                else{
                    Login(memail, mpassword);
                }
            }
        });

        return view;

    }

    private void Login(final String email, final String password) {

        login_loading.setVisibility(View.VISIBLE);
        btn_login.setVisibility(View.GONE);

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("email_id", email);
            jsonObject.put("password", password);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        ConnectionManager.sendData(jsonObject.toString(), requestQueue, url_login, new ConnectionManager.VolleyCallback() {
            @Override
            public void onSuccessResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONObject user = jsonObject.getJSONObject("user");
                    String profile_image = user.getString("profile_picture");

                    if (success.equals("true")){
                        Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                        login_loading.setVisibility(View.GONE);
                        btn_login.setVisibility(View.VISIBLE);

//                                System.out.println(profile_image);

                        sessionManager.createSession(email, profile_image);

                        Fragment fragment = new ProfileFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.account_fragment, fragment);
                        fragmentTransaction.commit();
                    }

                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Either username or password is wrong! Try again. ", Toast.LENGTH_SHORT).show();
                    Fragment fragment = new AccountFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.account_fragment, fragment);
                    fragmentTransaction.commit();
//                    e.printStackTrace();
                    System.out.println(e.toString());
                }

            }

            @Override
            public void onErrorResponse(VolleyError error) {

//                Toast.makeText(getContext(), "Login Error : " +error.toString(), Toast.LENGTH_SHORT).show();
                login_loading.setVisibility(View.GONE);
                btn_login.setVisibility(View.VISIBLE);
                new AlertDialog.Builder(getContext())
                        .setTitle("Server error!")
                        .setMessage("Some issues with server has occurred, Please try again later.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).show();

            }
        });


//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_login,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            String success = jsonObject.getString("success");
//                            JSONObject user = jsonObject.getJSONObject("user");
//                            String profile_image = user.getString("profile_picture");
//
//                            if (success.equals("true")){
//                                Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
//                                login_loading.setVisibility(View.GONE);
//                                btn_login.setVisibility(View.VISIBLE);
//
////                                System.out.println(profile_image);
//
//                                sessionManager.createSession(email, profile_image);
//
//                                Fragment fragment = new downloadFragment();
//                                FragmentManager fragmentManager = getFragmentManager();
//                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                                fragmentTransaction.replace(R.id.account_fragment, fragment);
//                                fragmentTransaction.commit();
//                            }
//
//                        } catch (JSONException e) {
//                            Toast.makeText(getContext(), "Login Failed : "+e, Toast.LENGTH_SHORT).show();
//                            Fragment fragment = new AccountFragment();
//                            FragmentManager fragmentManager = getFragmentManager();
//                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                            fragmentTransaction.replace(R.id.account_fragment, fragment);
//                            fragmentTransaction.commit();
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getContext(), "Login Error : " +error.toString(), Toast.LENGTH_SHORT).show();
//                        login_loading.setVisibility(View.GONE);
//                        btn_login.setVisibility(View.VISIBLE);
//                    }
//                })
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("email_id", email);
//                params.put("password", password);
//                return params;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//        requestQueue.add(stringRequest);

    }

}

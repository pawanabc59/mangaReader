package com.example.mangareader;

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

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.mangareader.Constants.server_url;

public class SignupFragment extends Fragment {

    private EditText semail, spassword, sc_password;
    private Button btnsignup;
    private ProgressBar loading;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

//    public boolean isLogin = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        semail = view.findViewById(R.id.sign_email);
        spassword = view.findViewById(R.id.sign_password);
        sc_password = view.findViewById(R.id.sign_confirm_password);
        loading = view.findViewById(R.id.loading);
        btnsignup = view.findViewById(R.id.signupBtn);

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = semail.getText().toString().trim();
                String password = spassword.getText().toString().trim();
                String c_password = sc_password.getText().toString().trim();

                if (email.isEmpty()){
                    semail.setError("Please insert email");
                }
                else if (password.isEmpty()){
                    spassword.setError("Please insert password");
                }
                else if (c_password.isEmpty()){
                    sc_password.setError("Please insert confirm password");
                }
                else if (!email.matches(emailPattern)){
                    semail.setError("Please enter valid email address");
                }
                else if (password.length()<6){
                    spassword.setError("Password length should be minimum of 6 digits");
                }
                else{
                    if (password.equals(c_password)) {
                        Register(email, password);
                    }
                    else{
                        Toast.makeText(getContext(),"Password does not matched", Toast.LENGTH_LONG).show();
                    }
                }

//                Register();
            }
        });

        return view;
    }

    private void Register(final String email, final String password){
        loading.setVisibility(View.VISIBLE);
        btnsignup.setVisibility(View.GONE);

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email_id", email);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ConnectionManager.sendData(jsonObject.toString(), requestQueue, server_url, new ConnectionManager.VolleyCallback() {
            @Override
            public void onSuccessResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if (success.equals("true")){
                        Toast.makeText(getContext(), "Register Success", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getContext(), MainActivity.class);
////                        isLogin = true;
//                        startActivity(intent);

                        Fragment fragment = new AccountFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.account_fragment, fragment);
                        fragmentTransaction.commit();

                        loading.setVisibility(View.GONE);
                        btnsignup.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Registration Failed : "+e, Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    btnsignup.setVisibility(View.VISIBLE);
//                            e.printStackTrace();
                    System.out.println(e.toString());
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

//                Toast.makeText(getContext(), "Register failed "+ error.toString(), Toast.LENGTH_SHORT).show();
                loading.setVisibility(View.GONE);
                btnsignup.setVisibility(View.VISIBLE);
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

//        final String email = this.email.getText().toString().trim();
//        final String password = this.password.getText().toString().trim();

//        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            String success = jsonObject.getString("success");
//
//                            if (success.equals("true")){
//                                Toast.makeText(getContext(), "Register Success", Toast.LENGTH_SHORT).show();
////                        Intent intent = new Intent(getContext(), MainActivity.class);
//////                        isLogin = true;
////                        startActivity(intent);
//
//                                Fragment fragment = new AccountFragment();
//                                FragmentManager fragmentManager = getFragmentManager();
//                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                                fragmentTransaction.replace(R.id.account_fragment, fragment);
//                                fragmentTransaction.commit();
//
//                                loading.setVisibility(View.GONE);
//                                btnsignup.setVisibility(View.VISIBLE);
//                            }
//
//                        } catch (JSONException e) {
//                            Toast.makeText(getContext(), "Registration Failed : "+e, Toast.LENGTH_SHORT).show();
//                            loading.setVisibility(View.GONE);
//                            btnsignup.setVisibility(View.VISIBLE);
////                            e.printStackTrace();
//                        }
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getContext(), "Register failed "+ error.toString(), Toast.LENGTH_SHORT).show();
//                        loading.setVisibility(View.GONE);
//                        btnsignup.setVisibility(View.VISIBLE);
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
////        You need to send response from the server because string request work on onResponse method other wise the android will return the volley timeout error
//
//        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//        requestQueue.add(stringRequest);

//        This is to make sure that the two data doesn't get stored in the database at a time
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                6000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
//        );

    }

}

package com.example.mangareader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.SwitchCompat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import static com.example.mangareader.Constants.main_path;
import static com.example.mangareader.Constants.url_upload;

public class ProfileFragment extends Fragment {



    TextView to_show_email;
    String email;
    Button logout, btn_photo, btnabout_us;
    SessionManager sessionManager;
    ImageView profile_image;
    private Bitmap bitmap;
    private SwitchCompat night_mode;
    Context contextThemeWrapper;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sessionManager = new SessionManager(getContext());
        if (sessionManager.loadNightModeState()==true){
            contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.darktheme);
//            container.setTheme(R.style.darktheme);
        }
        else{
            contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme);
//            setTheme(R.style.AppTheme);
        }

        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);

        // Inflate the layout for this fragment
        View view = localInflater.inflate(R.layout.fragment_profile, container, false);

        to_show_email = view.findViewById(R.id.edit_email);
        logout = view.findViewById(R.id.logout);
        btn_photo = view.findViewById(R.id.btn_photo);
        profile_image = view.findViewById(R.id.profile_image);
        btnabout_us = view.findViewById(R.id.btnabout_us);
        night_mode = view.findViewById(R.id.night_switch);

        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetails();
        String mEmail = user.get(sessionManager.EMAIL);
        String profile_photo_link = user.get(sessionManager.PROFILE_PHOTO);

        System.out.println("mEmail : "+mEmail+" profile photo : "+profile_photo_link);
        
        email = mEmail;

        to_show_email.setText(mEmail);

        Picasso.get().load(main_path+profile_photo_link).into(profile_image);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logout();
            }
        });

        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
//                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                getActivity().startActivityForResult(galleryIntent, 1);
            }
        });

        btnabout_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DeveloperProfileActivity.class);
                startActivity(intent);
            }
        });

//        sessionManager.setNightModeState(true);
//        night_mode.setEnabled(false);
//        if (sessionManager.loadNightModeState()==true){
//            sessionManager.setNightModeState(false);
//            night_mode.setChecked(false);
////            recreate();
//        }
//        else {
//            sessionManager.setNightModeState(true);
//            night_mode.setChecked(true);
////            recreate();
//        }

        if (sessionManager.loadNightModeState()==true){
            night_mode.setChecked(true);
        }
        else {
            night_mode.setChecked(false);
        }

        night_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    sessionManager.setNightModeState(true);
                    night_mode.setChecked(true);

                }
                else {
                    sessionManager.setNightModeState(false);
                    night_mode.setChecked(false);

                }

                recreateApp();
            }
        });

        return view;
    }

    public void recreateApp(){
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null){
//            Uri selectedImage = data.getData();
//            profile_image.setImageURI(selectedImage);
//        }
//    }
    
    private void chooseFile(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        Intent intent = new Intent();
//        intent.setType("images/*");
//        intent.setAction(Intent.ACTION_PICK);
        getActivity().startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null){
            Uri filepath = data.getData();
//            profile_image.setImageURI(filepath);
            try{
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filepath );
                profile_image.setImageBitmap(bitmap);
//                profile_image.setImageURI(filepath);

//                Zoro image link : https://tinyurl.com/y3tdn867

//                Picasso.get().load(filepath).into(profile_image);
            }
            catch (IOException e){
                e.printStackTrace();
            }

            UploadPicture(email, getStringImage(bitmap));
        }
    }

    private void UploadPicture(final String email, final String photo) {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Uploading...");
        progressDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email_id", email);
            jsonObject.put("profile_picture", photo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ConnectionManager.sendData(jsonObject.toString(), requestQueue, url_upload, new ConnectionManager.VolleyCallback() {
            @Override
            public void onSuccessResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if (success.equals("true")){
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Image Uploaded Successfully ", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Error while uploading Image", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Error : "+e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "Try Again " + error.toString(), Toast.LENGTH_SHORT).show();

            }
        });

//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_upload,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            String success = jsonObject.getString("success");
//
//                            if (success.equals("true")){
//                                progressDialog.dismiss();
//                                Toast.makeText(getContext(), "Image Uploaded Successfully ", Toast.LENGTH_SHORT).show();
//                            }
//                            else{
//                                progressDialog.dismiss();
//                                Toast.makeText(getContext(), "Error while uploading Image", Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e) {
//                            progressDialog.dismiss();
//                            Toast.makeText(getContext(), "Error : "+e, Toast.LENGTH_SHORT).show();
//                            e.printStackTrace();
//                        }
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        progressDialog.dismiss();
//                        Toast.makeText(getContext(), "Try Again " + error.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                })
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("email_id", email);
//                params.put("profile_picture", photo);
//                return params;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//        requestQueue.add(stringRequest);

    }
    
    public String getStringImage(Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        
        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT);
        
        return encodedImage;
    }

}

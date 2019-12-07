package com.example.mangareader;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import static com.example.mangareader.Constants.main_path;
import static com.example.mangareader.Constants.url_add_favourites;
import static com.example.mangareader.Constants.url_get_user_rating;
import static com.example.mangareader.Constants.url_get_user_rating_book;
import static com.example.mangareader.Constants.url_set_user_rating;
import static com.example.mangareader.Constants.url_set_user_rating_book;

public class PDFViewer extends AppCompatActivity {

    TextView pdfPageNumber, txtchapter_rating;
    PDFView pdfView;
    Toolbar toolbar;
    FloatingActionButton btnrating;
    private ProgressBar pdf_loading;
    private RatingBar  chapterRating;
    SessionManager sessionManager;
    String manga_url = main_path;
    float rating_count = 0;
    float get_rating;
    int chapter_id;
    String type,book_id;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);

        sessionManager = new SessionManager(getApplicationContext());

        HashMap<String, String> user = sessionManager.getUserDetails();
        final String email = user.get(sessionManager.EMAIL);

        Intent intent = getIntent();
        String chapter_name =  intent.getExtras().getString("Chapter_Name");
        String chapter_path = intent.getExtras().getString("Chapter_path");
        type = intent.getExtras().getString("type");
        if (type.equals("manga")){
            chapter_id = intent.getExtras().getInt("chapter_id");
        }
        else {
            book_id = intent.getExtras().getString("book_id");
        }

//        System.out.println("book path is : " +chapter_path);

        pdfView = (PDFView) findViewById(R.id.pdfview);



        toolbar = (Toolbar) findViewById(R.id.pdfToolbar);
        pdf_loading = findViewById(R.id.pdf_loading);
        pdfPageNumber = findViewById(R.id.pdfPageNumber);
        btnrating = findViewById(R.id.btnrating);
//        chapterRating = findViewById(R.id.chapter_rating);
//        txtchapter_rating = findViewById(R.id.txtchapter_rating);

        toolbar.setTitle(chapter_name);

//        This is the function read PDF from Assets
//        pdfView.fromAsset(chapter_name+".pdf").load();

//      This is function read PDF from URL or any url direct PDF from internet
//        new RetrievePDFStream().execute("https://tinyurl.com/y5cof43q");

//        chapterRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                txtchapter_rating.setText(""+rating);
//            }
//        });

        if(sessionManager.isLoggin()) {
            btnrating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    System.out.println("Type : "+type);
                    System.out.println("book Id : "+book_id);
                    if (type.equals("manga")) {
                        rating_count = get_user_rating(email, chapter_id);
                        System.out.println("It comes to manga section");
                    }
                    else {
                        rating_count = get_user_rating_book(email, book_id);
                        System.out.println("It comes to book section");
                    }

                    final AlertDialog.Builder alert = new AlertDialog.Builder(PDFViewer.this);
                    View mView = getLayoutInflater().inflate(R.layout.rating_dialogue, null);

                    Button btnCancel = mView.findViewById(R.id.btnrating_cancel);
                    Button btnRate = mView.findViewById(R.id.btnrating_rate);
                    RatingBar ratingBar = mView.findViewById(R.id.chapter_rating);
                    final TextView txtRating = mView.findViewById(R.id.txtchapter_rating);

//                try to put this line below to check if the rating not showing first time problem is there or not.
                    alert.setView(mView);

                    final AlertDialog alertDialog = alert.create();

                    if (rating_count == 0) {
                        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                            @Override
                            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                                txtRating.setText("Rating : " + rating);
                                rating_count = rating;
                            }
                        });

                        btnRate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (type.equals("manga")) {
                                    upload_user_rating(email, chapter_id, rating_count);
                                    System.out.println("It comes to manga add rating");
                                }
                                else {
                                    upload_user_rating_book(email, book_id, rating_count);
                                    System.out.println("It comes to book add rating");
                                }
                                Toast.makeText(PDFViewer.this, "Rating is done!", Toast.LENGTH_SHORT);
                                alertDialog.dismiss();
                            }
                        });

                    } else {
                        ratingBar.setRating(rating_count);
                        ratingBar.setEnabled(false);
                        txtRating.setText("Rating : " + rating_count);
                    }

                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });

                    alertDialog.show();

                }
            });
        }

        new RetrievePDFStream().execute(manga_url+chapter_path);

    }

    private void upload_user_rating(String email,int chapter_id,float rating_count){

        System.out.println("email_id : "+email+" chapter_id : "+chapter_id+" rating : "+rating_count);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("email_id", email);
            jsonObject.put("chapter_id", chapter_id);
            jsonObject.put("rating", rating_count);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ConnectionManager.sendData(jsonObject.toString(), requestQueue, url_set_user_rating, new ConnectionManager.VolleyCallback() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onSuccessResponse(String response) {
                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    String success = jsonObject1.getString("success");

                    if (success.equals("true")){
                        Toast.makeText(getApplicationContext(),"Rating Added", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
//                    e.printStackTrace();
                    System.out.println(e.toString());
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

//                Toast.makeText(getApplicationContext(),"Some Error has Come : " + error, Toast.LENGTH_SHORT).show();
                new android.app.AlertDialog.Builder(getApplicationContext())
                        .setTitle("Server error!")
                        .setMessage("Some issues with server has occurred, Please try again later.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).show();

            }
        });

    }

    private void upload_user_rating_book(String email,String book_id,float rating_count){

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("email_id", email);
            jsonObject.put("book_id", book_id);
            jsonObject.put("rating", rating_count);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ConnectionManager.sendData(jsonObject.toString(), requestQueue, url_set_user_rating_book, new ConnectionManager.VolleyCallback() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onSuccessResponse(String response) {
                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    String success = jsonObject1.getString("success");

                    if (success.equals("true")){
                        Toast.makeText(getApplicationContext(),"Rating Added", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
//                    e.printStackTrace();
                    System.out.println(e.toString());
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

//                Toast.makeText(getApplicationContext(),"Some Error has Come : " + error, Toast.LENGTH_SHORT).show();
                new android.app.AlertDialog.Builder(getApplicationContext())
                        .setTitle("Server error!")
                        .setMessage("Some issues with server has occurred, Please try again later.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).show();

            }
        });

    }

    private float get_user_rating(String email,int chapter_id){

        System.out.println("email_id : "+email+" chapter_id : "+chapter_id);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("email_id", email);
            jsonObject.put("chapter_id", chapter_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ConnectionManager.sendData(jsonObject.toString(), requestQueue, url_get_user_rating, new ConnectionManager.VolleyCallback() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onSuccessResponse(String response) {
                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    String success = jsonObject1.getString("success");
//                    JSONObject jsonObject2 = jsonObject1.getJSONObject("rating");
                    JSONArray jsonArray = jsonObject1.getJSONArray("rating");

                    if (success.equals("true")){
                        if (jsonArray.length() != 0) {
                            Toast.makeText(getApplicationContext(), "Already rated", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                                get_rating = Float.parseFloat(jsonObject2.getString("rating"));
                            }
                        }
                        else{
                            get_rating = 0;
                        }
                    }

                } catch (JSONException e) {
//                    e.printStackTrace();
                    get_rating = 0;
                    System.out.println(e.toString());
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

//                Toast.makeText(getApplicationContext(),"Some Error has Come : " + error, Toast.LENGTH_SHORT).show();
                new android.app.AlertDialog.Builder(getApplicationContext())
                        .setTitle("Server error!")
                        .setMessage("Some issues with server has occurred, Please try again later.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).show();

            }
        });

        return get_rating;
    }

    private float get_user_rating_book(String email,String book_id){

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("email_id", email);
            jsonObject.put("book_id", book_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ConnectionManager.sendData(jsonObject.toString(), requestQueue, url_get_user_rating_book, new ConnectionManager.VolleyCallback() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onSuccessResponse(String response) {
                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    String success = jsonObject1.getString("success");
//                    JSONObject jsonObject2 = jsonObject1.getJSONObject("rating");
                    JSONArray jsonArray = jsonObject1.getJSONArray("rating");

                    if (success.equals("true")){
                        if (jsonArray.length() != 0) {
                            Toast.makeText(getApplicationContext(), "Already rated", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                                get_rating = Float.parseFloat(jsonObject2.getString("rating"));
                            }
                        }
                        else{
                            get_rating = 0;
                        }
                    }

                } catch (JSONException e) {
//                    e.printStackTrace();
                    get_rating = 0;
                    System.out.println(e.toString());
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

//                Toast.makeText(getApplicationContext(),"Some Error has Come : " + error, Toast.LENGTH_SHORT).show();
                new android.app.AlertDialog.Builder(getApplicationContext())
                        .setTitle("Server error!")
                        .setMessage("Some issues with server has occurred, Please try again later.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).show();

            }
        });

        return get_rating;
    }

    class RetrievePDFStream extends AsyncTask<String, Void, InputStream>{

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try{
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                if (urlConnection.getResponseCode() == 200){
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            }
            catch (IOException e){
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdf_loading.setVisibility(View.GONE);
            pdfView.fromStream(inputStream).onPageChange(new OnPageChangeListener() {
                @Override
                public void onPageChanged(int page, int pageCount) {
                    pdfPageNumber.setText(page+1+"/"+pageCount+"  ");
                }
            }).load();
        }
    }
}

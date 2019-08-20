package com.example.mangareader;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.mangareader.Constants.url_add_favourites;
import static com.example.mangareader.Constants.url_get_chapters;
import static com.example.mangareader.Constants.url_remove_favourites;

public class comic_activity extends AppCompatActivity {

    private TextView tvtitle, tvcategory, tvrating;
    private ImageView img;
    private RatingBar comic_rating;
    private String manga_id;
    private FloatingActionButton btnFavourite, btnRemoveFavourite;
    private ExpandableTextView tvdescription;
    SessionManager sessionManager;
    RecyclerView recyclerView;
    ArrayList<chapterModel> chapterList;



//    Toolbar comicNameToolbar;

    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_activity);

        sessionManager = new SessionManager(getApplicationContext());
//        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetails();
        final String mEmail = user.get(sessionManager.EMAIL);

        tvtitle = (TextView) findViewById(R.id.txttitle);
        tvcategory = (TextView) findViewById(R.id.txtcategory);
        tvdescription = (ExpandableTextView) findViewById(R.id.txtdescription);
        img = (ImageView) findViewById(R.id.comicThumbnail);
        btnFavourite = findViewById(R.id.btnFavourite);
        btnRemoveFavourite = findViewById(R.id.btnRemoveFavourite);

        tvrating = findViewById(R.id.ratings);
        comic_rating = findViewById(R.id.comic_rating);

        comic_rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                tvrating.setText("Rating : "+rating);
            }
        });


//        comicNameToolbar = findViewById(R.id.comic_name_toolbar);

//        Recieve data
        Intent intent = getIntent();
        String Title =  intent.getExtras().getString("Title");
        String Description = intent.getExtras().getString("Description");
        String image = intent.getExtras().getString("Thumbnail");
        String Manga_id = intent.getExtras().getString("Manga_id");
        String favourite = intent.getExtras().getString("favourite");

        System.out.println("Comic Activity : "+favourite);

        manga_id = Manga_id;
//        comicNameToolbar.setTitle(Title);

//        Setting values
        tvtitle.setText(Title);
        tvdescription.setText("Description : \n"+Description);
//        img.setImageResource(image);
        Picasso.get().load(image).into(img);

        if (favourite.equals("TRUE")){

            btnRemoveFavourite.setVisibility(View.VISIBLE);
            btnFavourite.setVisibility(View.GONE);
        }
        else{

            btnFavourite.setVisibility(View.VISIBLE);
            btnRemoveFavourite.setVisibility(View.GONE);
        }

        if (sessionManager.isLoggin()){
            btnFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Floating Button is clicked", Toast.LENGTH_SHORT).show();
                    addFavourite(mEmail, manga_id);
                }
            });
        }
        else{
            btnFavourite.setEnabled(false);
            Toast.makeText(getApplicationContext(), "You need to login to add the mangas to your favorite list", Toast.LENGTH_LONG).show();
        }

        btnRemoveFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFavourite(mEmail, manga_id);
            }
        });

        recyclerView = findViewById(R.id.chapter_list);
        chapterList = new ArrayList<>();

//        chapterList.add(new chapterModel("Chapter 1"));
//        chapterList.add(new chapterModel("Chapter 2"));
//        chapterList.add(new chapterModel("Chapter 3"));
//        chapterList.add(new chapterModel("Chapter 4"));
//        chapterList.add(new chapterModel("Chapter 5"));
//        chapterList.add(new chapterModel("Chapter 6"));
//        chapterList.add(new chapterModel("Chapter 7"));
//        chapterList.add(new chapterModel("Chapter 8"));
//        chapterList.add(new chapterModel("Chapter 9"));
//        chapterList.add(new chapterModel("Chapter 10"));
//        chapterList.add(new chapterModel("Chapter 11"));
//        chapterList.add(new chapterModel("Chapter 12"));
//        chapterList.add(new chapterModel("Chapter 13"));
//        chapterList.add(new chapterModel("Chapter 14"));
//        chapterList.add(new chapterModel("Chapter 15"));
//        chapterList.add(new chapterModel("Chapter 16"));

//        chapterList.add(new chapterModel("Chapter 2 - Kaido"));

        getChapters(mEmail);


    }

    private void addFavourite(String mEmail, String manga_id){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("email_id", mEmail);
            jsonObject.put("manga_id", manga_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ConnectionManager.sendData(jsonObject.toString(), requestQueue, url_add_favourites, new ConnectionManager.VolleyCallback() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onSuccessResponse(String response) {
                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    String success = jsonObject1.getString("success");

                    if (success.equals("true")){
                        btnFavourite.setVisibility(View.GONE);
                        btnRemoveFavourite.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(),"Added to favourites", Toast.LENGTH_SHORT).show();
//                        recreate();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Some Error has Come : " + error, Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void removeFavourite(String mEmail, String manga_id){

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email_id", mEmail);
            jsonObject.put("manga_id", manga_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ConnectionManager.sendData(jsonObject.toString(), requestQueue, url_remove_favourites, new ConnectionManager.VolleyCallback() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onSuccessResponse(String response) {
                try {
                    JSONObject jsonObject1 = new JSONObject(response);

                    String success = jsonObject1.getString("success");
                    if (success.equals("true")){
                        btnRemoveFavourite.setVisibility(View.GONE);
                        btnFavourite.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(),"Removed from  favourites", Toast.LENGTH_SHORT).show();
//                        recreate();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Some Error has Come : " + error, Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void getChapters(String email){

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("email_id", email);
            jsonObject.put("manga_id", manga_id);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        ConnectionManager.sendData(jsonObject.toString(), requestQueue, url_get_chapters, new ConnectionManager.VolleyCallback() {
            @Override
            public void onSuccessResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("chapters");

                    System.out.println("chapters Array: " +jsonArray);

                    if (success.equals("true")){

                        for (int i = 0; i < jsonArray.length(); i++ ){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String chapter_name = jsonObject1.getString("title");
                            String chapter_number = jsonObject1.getString("chapter_no");
                            String chapter_id = jsonObject1.getString("chapter_id");
                            String chapter_path = jsonObject1.getString("chapter_path");


//                            System.out.println("title : "+chapter_name+" chapter no : "+chapter_number);

                            chapterList.add(new chapterModel("Chapter "+chapter_number+" - "+chapter_name, chapter_path));

                            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

                            RecyclerView.LayoutManager rvLiLayoutManager = layoutManager;

                            chapterAdapter adapter = new chapterAdapter(getApplicationContext(), chapterList);

                            recyclerView.setLayoutManager(rvLiLayoutManager);
                            recyclerView.setAdapter(adapter);
                        }

                    }
                    else if (success.equals("false")){
                        Toast.makeText(getApplicationContext(), "Chapter Not found ", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Chapter Error : " +e.toString(), Toast.LENGTH_SHORT).show();
                    System.out.println(e.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Chapter Loading Error : " +error.toString(), Toast.LENGTH_SHORT).show();

            }
        });

//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_get_chapters,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            String success = jsonObject.getString("success");
//                            JSONArray jsonArray = jsonObject.getJSONArray("chapters");
//
//                            System.out.println("chapters Array: " +jsonArray);
//
//                            if (success.equals("true")){
//
//                                for (int i = 0; i < jsonArray.length(); i++ ){
//                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//
//                                    String chapter_name = jsonObject1.getString("title");
//                                    String chapter_number = jsonObject1.getString("chapter_no");
//
//                                    System.out.println("title : "+chapter_name+" chapter no : "+chapter_number);
//
//                                    chapterList.add(new chapterModel("Chapter "+chapter_number+" - "+chapter_name));
//
//                                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//
//                                    RecyclerView.LayoutManager rvLiLayoutManager = layoutManager;
//
//                                    chapterAdapter adapter = new chapterAdapter(getApplicationContext(), chapterList);
//
//                                    recyclerView.setLayoutManager(rvLiLayoutManager);
//                                    recyclerView.setAdapter(adapter);
//                                }
//
//                            }
//                            else if (success.equals("false")){
//                                Toast.makeText(getApplicationContext(), "Chapter Not found ", Toast.LENGTH_SHORT).show();
//                            }
//
//                        } catch (JSONException e) {
//                            Toast.makeText(getApplicationContext(), "Chapter Error : " +e.toString(), Toast.LENGTH_SHORT).show();
//                            System.out.println(e.toString());
//                            e.printStackTrace();
//                        }
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(), "Chapter Loading Error : " +error.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                })
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("manga_id", manga_id);
//                return params;
//            }
//        };

//        requestQueue.add(stringRequest);

    }

}

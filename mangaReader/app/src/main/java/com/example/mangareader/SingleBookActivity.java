package com.example.mangareader;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class SingleBookActivity extends AppCompatActivity {

    private LayoutInflater layoutInflater;
    private View view;

    private TextView book_tvtitle, book_tvcategory, book_tvrating;
    private ImageView book_img, book_backgroundImage;
    private RatingBar book_rating;
    private String book_manga_id;
    private FloatingActionButton book_btnFavourite, book_btnRemoveFavourite;
    private ExpandableTextView book_tvdescription;
    private Button book_read_now;
    SessionManager sessionManager;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = new SessionManager(getApplicationContext());
        if (sessionManager.loadNightModeState()==true){
            setTheme(R.style.darktheme);
        }
        else{
            setTheme(R.style.AppTheme);
        }

        setContentView(R.layout.activity_single_book);

        HashMap<String, String> user = sessionManager.getUserDetails();
        final String mEmail = user.get(sessionManager.EMAIL);

        book_tvtitle = (TextView) findViewById(R.id.book_txttitle);
        book_tvcategory = (TextView) findViewById(R.id.book_txtcategory);
        book_tvdescription = (ExpandableTextView) findViewById(R.id.book_txtdescription);
        book_img = (ImageView) findViewById(R.id.book_Thumbnail);
        book_btnFavourite = findViewById(R.id.book_btnFavourite);
        book_btnRemoveFavourite = findViewById(R.id.book_btnRemoveFavourite);
        book_backgroundImage = findViewById(R.id.book_backgroundImage);

        book_tvrating = findViewById(R.id.book_ratings);
        book_rating = findViewById(R.id.book_ratingbar);
        book_read_now = findViewById(R.id.book_read_now);

        book_rating.setRating((float) 4.5);
        book_tvrating.setText("Rating : 4.5");
//        book_rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){
//
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                tvrating.setText("Rating : "+rating);
//            }
//        });


//        comicNameToolbar = findViewById(R.id.comic_name_toolbar);

//        Recieve data
        final Intent intent = getIntent();
        final String Title =  intent.getExtras().getString("Title");
        String Description = intent.getExtras().getString("Description");
        String image = intent.getExtras().getString("Thumbnail");
        String Manga_id = intent.getExtras().getString("Manga_id");
        String favourite = intent.getExtras().getString("favourite");

//        System.out.println("Comic Activity : "+favourite);

//        book_manga_id = Manga_id;
//        comicNameToolbar.setTitle(Title);

//        Setting values
        book_tvtitle.setText(Title);
        book_tvdescription.setText(Description);
//        img.setImageResource(image);

        Picasso.get().load(image).resize(10,10).into(book_backgroundImage);

        Picasso.get().load(image).into(book_img);

        if (favourite.equals("TRUE")){

            book_btnRemoveFavourite.setVisibility(View.VISIBLE);
            book_btnFavourite.setVisibility(View.GONE);
        }
        else{

            book_btnFavourite.setVisibility(View.VISIBLE);
            book_btnRemoveFavourite.setVisibility(View.GONE);
        }

        if (sessionManager.isLoggin()){
            book_btnFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Floating Button is clicked", Toast.LENGTH_SHORT).show();
//                    addFavourite(mEmail, manga_id);
                }
            });
        }
        else{
            book_btnFavourite.setEnabled(false);
            Toast.makeText(getApplicationContext(), "You need to login to add the mangas to your favorite list", Toast.LENGTH_LONG).show();
        }

        book_btnRemoveFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                removeFavourite(mEmail, manga_id);
            }
        });

        book_read_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), PDFViewer.class);
                intent1.putExtra("Chapter_Name", Title);
                intent1.putExtra("Chapter_path", "/chapters/one_piece_1.pdf");
                startActivity(intent1);
            }
        });


    }
}

package com.example.mangareader;

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

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.mangareader.Constants.main_path;

public class PDFViewer extends AppCompatActivity {

    TextView pdfPageNumber, txtchapter_rating;
    PDFView pdfView;
    Toolbar toolbar;
    FloatingActionButton btnrating;
    private ProgressBar pdf_loading;
    private RatingBar  chapterRating;
    String manga_url = main_path;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);

        Intent intent = getIntent();
        String chapter_name =  intent.getExtras().getString("Chapter_Name");
        String chapter_path = intent.getExtras().getString("Chapter_path");

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

        btnrating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alert = new AlertDialog.Builder(PDFViewer.this);
                View mView =getLayoutInflater().inflate(R.layout.rating_dialogue, null);

                Button btnCancel = mView.findViewById(R.id.btnrating_cancel);
                Button btnRate = mView.findViewById(R.id.btnrating_rate);
                RatingBar ratingBar = mView.findViewById(R.id.chapter_rating);
                final TextView txtRating = mView.findViewById(R.id.txtchapter_rating);

                alert.setView(mView);

                final AlertDialog alertDialog = alert.create();

                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        txtRating.setText("Rating : "+rating);
                    }
                });

                btnRate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(PDFViewer.this,"Rating is done!", Toast.LENGTH_SHORT);
                        alertDialog.dismiss();
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();

            }
        });

        new RetrievePDFStream().execute(manga_url+chapter_path);

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

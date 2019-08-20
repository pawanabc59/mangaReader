package com.example.mangareader;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toolbar;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.mangareader.Constants.main_path;

public class PDFViewer extends AppCompatActivity {

    PDFView pdfView;
    Toolbar toolbar;
    private ProgressBar pdf_loading;
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
        toolbar.setTitle(chapter_name);

//        This is the function read PDF from Assets
//        pdfView.fromAsset(chapter_name+".pdf").load();

//      This is function read PDF from URL or any url direct PDF from internet
//        new RetrievePDFStream().execute("https://tinyurl.com/y5cof43q");

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
            pdfView.fromStream(inputStream).load();
        }
    }
}

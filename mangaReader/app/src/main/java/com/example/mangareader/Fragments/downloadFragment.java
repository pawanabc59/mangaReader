package com.example.mangareader.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.mangareader.R;
import com.example.mangareader.SessionManager;

public class downloadFragment extends Fragment {

    Toolbar downloadToolbar;
    FloatingActionButton btnupload_pdf;
    TextView show_filepath;
    SessionManager sessionManager;
    Context contextThemeWrapper;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

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

        View view = localInflater.inflate(R.layout.fragment_download, container, false);
//        View view =  inflater.inflate(R.layout.fragment_download, container, false);

//        downloadToolbar = view.findViewById(R.id.downloadToolbar);
//        downloadToolbar.setTitle("My Documents");

        btnupload_pdf = view.findViewById(R.id.btnupload_pdf);
        show_filepath = view.findViewById(R.id.show_filepath);

        btnupload_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                getActivity().startActivityForResult(intent, 123);

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123 && resultCode == Activity.RESULT_OK && data != null){
            String path = data.getData().getPath();
            show_filepath.setText(path);
        }

    }
}

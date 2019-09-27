package com.example.mangareader;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.mangareader.Constants.main_path;

public class BookListFragment extends Fragment {

    SessionManager sessionManager;
    Context contextThemeWrapper;
    String static_url = main_path;
    List<comic> lstBooks;
    RecyclerView myrecyclerview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

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

        View view = localInflater.inflate(R.layout.fragment_book_list, container, false);

        Toolbar toolbar = view.findViewById(R.id.book_list_toolbar);
        toolbar.setTitle("All Books");

        HashMap<String, String> user = sessionManager.getUserDetails();
        String email = user.get(sessionManager.EMAIL);
        String profile_photo_link = user.get(sessionManager.PROFILE_PHOTO);

        lstBooks = new ArrayList<>();

        lstBooks.add(new comic("One", "Fun", "Fun and adventure", main_path+"/covers/onepiece.jpeg", "1", "TRUE"));
        lstBooks.add(new comic("One", "Fun", "Fun and adventure", main_path+"/covers/onepiece.jpeg", "1", "TRUE"));
        lstBooks.add(new comic("One", "Fun", "Fun and adventure", main_path+"/covers/onepiece.jpeg", "1", "TRUE"));
        lstBooks.add(new comic("One", "Fun", "Fun and adventure", main_path+"/covers/onepiece.jpeg", "1", "TRUE"));
        lstBooks.add(new comic("One", "Fun", "Fun and adventure", main_path+"/covers/onepiece.jpeg", "1", "TRUE"));
        lstBooks.add(new comic("One", "Fun", "Fun and adventure", main_path+"/covers/onepiece.jpeg", "1", "TRUE"));
        lstBooks.add(new comic("One", "Fun", "Fun and adventure", main_path+"/covers/onepiece.jpeg", "1", "TRUE"));
        lstBooks.add(new comic("One", "Fun", "Fun and adventure", main_path+"/covers/onepiece.jpeg", "1", "TRUE"));


        myrecyclerview = view.findViewById(R.id.book_list_recyclerview);
        BookAdapter myAdapter = new BookAdapter(getContext(), lstBooks);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);
//        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        myrecyclerview.setLayoutManager(manager);
        myrecyclerview.setAdapter(myAdapter);

        return view;
    }

}

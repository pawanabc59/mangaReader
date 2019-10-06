package com.example.mangareader;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.mangareader.Constants.main_path;
import static com.example.mangareader.Constants.url_get_favourite_book;
import static com.example.mangareader.Constants.url_get_favourite_manga;

public class FavouriteBookFragment extends Fragment {

    String static_url = main_path;
    Context thisContext;
    List<comic> lstBook;
    RecyclerView favourite_book_recyclerview;
    SessionManager sessionManager;
    Context contextThemeWrapper;

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

        View  view = localInflater.inflate(R.layout.fragment_favourite_book, container, false);

        HashMap<String, String> user = sessionManager.getUserDetails();
        String email = user.get(sessionManager.EMAIL);

        lstBook = new ArrayList<>();

        showFavouriteBooks(email);

        return view;
    }

    private void showFavouriteBooks(String email){

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JSONObject jsonObject = new JSONObject();

        try{
            jsonObject.put("email_id", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ConnectionManager.sendData(jsonObject.toString(), requestQueue, url_get_favourite_book, new ConnectionManager.VolleyCallback() {
            @Override
            public void onSuccessResponse(String response) {
                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    String success = jsonObject1.getString("success");
                    JSONArray jsonArray = jsonObject1.getJSONArray("favourite_books");

                    if (success.equals("true")){
                        Toast.makeText(getContext(),"Favourites Books", Toast.LENGTH_SHORT).show();

                        for (int i = 0 ; i < jsonArray.length(); i++){
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                            String title = jsonObject2.getString("book_title");
                            String cover_photo = jsonObject2.getString("book_cover_picture");
                            String description = jsonObject2.getString("book_description");
                            String manga_id = jsonObject2.getString("book_id");
                            String favourite = "TRUE";

                            String thumbnail = static_url+cover_photo;

                            lstBook.add(new comic(title, "Fun", description, thumbnail, manga_id, favourite));

                            favourite_book_recyclerview = getActivity().findViewById(R.id.favourite_book_recyclerview);
                            RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getContext(), lstBook);
                            GridLayoutManager manager = new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);
//        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
                            favourite_book_recyclerview.setLayoutManager(manager);
                            favourite_book_recyclerview.setAdapter(myAdapter);

                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Books not Found : " + e, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "Book Loading Error : "+error, Toast.LENGTH_SHORT).show();

            }
        });


    }

}

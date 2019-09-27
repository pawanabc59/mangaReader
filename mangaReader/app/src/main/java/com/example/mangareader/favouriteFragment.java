package com.example.mangareader;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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
import static com.example.mangareader.Constants.url_get_favourite_manga;

public class favouriteFragment extends Fragment {


    String static_url = main_path;
    Context thisContext;
    List<comic> lstComic;
    RecyclerView myrecyclerview;
    SessionManager sessionManager;
    Toolbar fav_Toolbar;
    Context contextThemeWrapper;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        thisContext = container.getContext();
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

        View view = localInflater.inflate(R.layout.fragment_favourite, container, false);

//        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

//        fav_Toolbar = view.findViewById(R.id.fav_Toolbar);
//        fav_Toolbar.setTitle("Favourites");


        HashMap<String, String> user = sessionManager.getUserDetails();
        String email = user.get(sessionManager.EMAIL);


        lstComic = new ArrayList<>();
//        lstComic.add(new comic("One Piece", "Category Comic", "Description Comic", R.drawable.one_piece));
//        lstComic.add(new comic("One Piece", "Category Comic", "Description Comic", R.drawable.one_piece));
//        lstComic.add(new comic("One Piece", "Category Comic", "Description Comic", R.drawable.one_piece));
//        lstComic.add(new comic("One Piece", "Category Comic", "Description Comic", R.drawable.one_piece));
//        lstComic.add(new comic("One Piece", "Category Comic", "Description Comic", R.drawable.one_piece));
//        lstComic.add(new comic("One Piece", "Category Comic", "Description Comic", R.drawable.one_piece));
//        lstComic.add(new comic("One Piece", "Category Comic", "Description Comic", R.drawable.one_piece));
//        lstComic.add(new comic("One Piece", "Category Comic", "Description Comic", R.drawable.one_piece));
//        lstComic.add(new comic("One Piece", "Category Comic", "Description Comic", R.drawable.one_piece));
//        lstComic.add(new comic("One Piece", "Category Comic", "Description Comic", R.drawable.one_piece));
//        lstComic.add(new comic("One Piece", "Category Comic", "Description Comic", R.drawable.one_piece));
//        lstComic.add(new comic("One Piece", "Category Comic", "Description Comic", R.drawable.one_piece));

        showFavouriteManga(email);

        return view;
    }

    private void showFavouriteManga(String email){

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JSONObject jsonObject = new JSONObject();

        try{
            jsonObject.put("email_id", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ConnectionManager.sendData(jsonObject.toString(), requestQueue, url_get_favourite_manga, new ConnectionManager.VolleyCallback() {
            @Override
            public void onSuccessResponse(String response) {
                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    String success = jsonObject1.getString("success");
                    JSONArray jsonArray = jsonObject1.getJSONArray("favourites");

                    if (success.equals("true")){
                        Toast.makeText(getContext(),"Favourites Mangas", Toast.LENGTH_SHORT).show();

                        for (int i = 0 ; i < jsonArray.length(); i++){
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                            String title = jsonObject2.getString("title");
                            String cover_photo = jsonObject2.getString("cover_picture");
                            String description = jsonObject2.getString("description");
                            String manga_id = jsonObject2.getString("manga_id");
                            String favourite = "TRUE";

                            String thumbnail = static_url+cover_photo;

                            lstComic.add(new comic(title, "Fun", description, thumbnail, manga_id, favourite));

                            myrecyclerview = getActivity().findViewById(R.id.recyclerId);
                            RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getContext(), lstComic);
                            GridLayoutManager manager = new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);
//        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
                            myrecyclerview.setLayoutManager(manager);
                            myrecyclerview.setAdapter(myAdapter);

                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Mangas not Found : " + e, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "Manga Loading Error : "+error, Toast.LENGTH_SHORT).show();

            }
        });


    }

}

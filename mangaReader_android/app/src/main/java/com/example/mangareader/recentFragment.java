package com.example.mangareader;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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

import static com.example.mangareader.Constants.main_path;
import static com.example.mangareader.Constants.url_recent_mangas;

public class recentFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<comicModel> comicList;
    SessionManager sessionManager;
    String email;
    Toolbar recentToolbar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_recent, container, false);

        sessionManager = new SessionManager(getContext());

        HashMap<String, String> user = sessionManager.getUserDetails();
        email = user.get(sessionManager.EMAIL);

        recyclerView = view.findViewById(R.id.rv);
        recentToolbar = view.findViewById(R.id.recentToolbar);
        recentToolbar.setTitle("Recent");

        comicList = new ArrayList<>();
//        comicList.add(new comicModel(R.drawable.one_piece, "One Piece", "Action"));
//        comicList.add(new comicModel(R.drawable.one_piece, "One Piece", "Action"));
//        comicList.add(new comicModel(R.drawable.one_piece, "One Piece", "Action"));
//        comicList.add(new comicModel(R.drawable.one_piece, "One Piece", "Action"));
//        comicList.add(new comicModel(R.drawable.one_piece, "One Piece", "Action"));
//        comicList.add(new comicModel(R.drawable.one_piece, "One Piece", "Action"));
//        comicList.add(new comicModel(R.drawable.one_piece, "One Piece", "Action"));
//        comicList.add(new comicModel(R.drawable.one_piece, "One Piece", "Action"));
//        comicList.add(new comicModel(R.drawable.one_piece, "One Piece", "Action"));

        recentMangas();

        return view;
    }

    private void recentMangas(){

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        final JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("email_id", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ConnectionManager.sendData(jsonObject.toString(), requestQueue, url_recent_mangas, new ConnectionManager.VolleyCallback() {
            @Override
            public void onSuccessResponse(String response) {
                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    JSONArray jsonArray = jsonObject1.getJSONArray("recents");
//System.out.println(jsonArray);
//System.out.println(jsonObject1.getString("success"));
                    String success = jsonObject1.getString("success");
                    if (success.equals("true")){
                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                            String title = jsonObject2.getString("title");
                            String cover_photo = jsonObject2.getString("cover_picture");
                            String description = jsonObject2.getString("description");
                            String id_manga = jsonObject2.getString("manga_id");
//                            String favourite = jsonObject1.getString("favourite");

                            String cover_photo_path = main_path+cover_photo;

                            System.out.println("title : "+title+" cover_photo : "+cover_photo_path+ " description : "+description+ " id_manga : "+id_manga);

//                            you have to change here
                            String favourite = "TRUE";

                            comicList.add(new comicModel(cover_photo_path, title, description, id_manga, favourite));

                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                            RecyclerView.LayoutManager rvLiLayoutManager = layoutManager;

                            recyclerView.setLayoutManager(rvLiLayoutManager);

                            comicAdapter adapter = new comicAdapter(getContext(),comicList);
                            recyclerView.setAdapter(adapter);

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error Loading Recent Mangas : "+error, Toast.LENGTH_SHORT).show();
            }
        });

    }

}

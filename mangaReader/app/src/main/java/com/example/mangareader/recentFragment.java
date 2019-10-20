package com.example.mangareader;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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

    ImageView luffy_image;
    TextView luffy_text;
    RecyclerView recyclerView;
    comicAdapter adapter;
    ArrayList<comicModel> comicList;
    SessionManager sessionManager;
    String email;
    Toolbar recentToolbar;
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

        View view = localInflater.inflate(R.layout.fragment_recent, container, false);

//        View view =  inflater.inflate(R.layout.fragment_recent, container, false);

        recyclerView = view.findViewById(R.id.rv);
        luffy_image = view.findViewById(R.id.luffy_image);
        luffy_text = view.findViewById(R.id.luffy_text);

        if (!sessionManager.isLoggin()){

            recyclerView.setVisibility(View.GONE);
            luffy_image.setVisibility(View.VISIBLE);
            luffy_text.setVisibility(View.VISIBLE);

            luffy_text.setText("You need to login to see what you were reading.\nNo recent found!!!");

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("OOPS!!!")
                    .setMessage("You need to login to see which manga you have recently read")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
//                            Do what you want after OK Button is clicked
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }


        HashMap<String, String> user = sessionManager.getUserDetails();
        email = user.get(sessionManager.EMAIL);

//        recentToolbar = view.findViewById(R.id.recentToolbar);
//        recentToolbar.setTitle("Recent");

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

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView.LayoutManager rvLiLayoutManager = layoutManager;

        adapter = new comicAdapter(getContext(),comicList);
        recyclerView.setLayoutManager(rvLiLayoutManager);
        recyclerView.setAdapter(adapter);

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

                        if (jsonArray.length()==0){
                            recyclerView.setVisibility(View.GONE);
                            luffy_image.setVisibility(View.VISIBLE);
                            luffy_text.setVisibility(View.VISIBLE);

                            luffy_text.setText("No manga has been read now.\n Read manga to see which manga you have read recently");
                        }
                        else {

                            recyclerView.setVisibility(View.VISIBLE);
                            luffy_image.setVisibility(View.GONE);
                            luffy_text.setVisibility(View.GONE);

                            for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                                    String title = jsonObject2.getString("title");
                                    String cover_photo = jsonObject2.getString("cover_picture");
                                    String description = jsonObject2.getString("description");
                                    String id_manga = jsonObject2.getString("manga_id");
//                            String favourite = jsonObject1.getString("favourite");

                                    String cover_photo_path = main_path + cover_photo;

                                    System.out.println("title : " + title + " cover_photo : " + cover_photo_path + " description : " + description + " id_manga : " + id_manga);

//                            you have to change here
                                    String favourite = "TRUE";

                                    comicList.add(new comicModel(cover_photo_path, title, description, id_manga, favourite));
                                }

                            adapter.notifyDataSetChanged();
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

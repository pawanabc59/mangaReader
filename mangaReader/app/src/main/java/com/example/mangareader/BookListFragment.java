package com.example.mangareader;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

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
import static com.example.mangareader.Constants.url_get_book;

public class BookListFragment extends Fragment {

    SessionManager sessionManager;
    Context contextThemeWrapper;
    String static_url = main_path;
    List<BookModel> lstBooks;
    RecyclerView myrecyclerview;
    BookAdapter myAdapter;
    String email;

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
        email = user.get(SessionManager.EMAIL);
        String profile_photo_link = user.get(sessionManager.PROFILE_PHOTO);

        lstBooks = new ArrayList<>();

//        lstBooks.add(new comic("One", "Fun", "Fun and adventure", main_path+"/covers/onepiece.jpeg", "1", "TRUE"));
//        lstBooks.add(new comic("One", "Fun", "Fun and adventure", main_path+"/covers/onepiece.jpeg", "1", "TRUE"));
//        lstBooks.add(new comic("One", "Fun", "Fun and adventure", main_path+"/covers/onepiece.jpeg", "1", "TRUE"));
//        lstBooks.add(new comic("One", "Fun", "Fun and adventure", main_path+"/covers/onepiece.jpeg", "1", "TRUE"));
//        lstBooks.add(new comic("One", "Fun", "Fun and adventure", main_path+"/covers/onepiece.jpeg", "1", "TRUE"));
//        lstBooks.add(new comic("One", "Fun", "Fun and adventure", main_path+"/covers/onepiece.jpeg", "1", "TRUE"));
//        lstBooks.add(new comic("One", "Fun", "Fun and adventure", main_path+"/covers/onepiece.jpeg", "1", "TRUE"));
//        lstBooks.add(new comic("One", "Fun", "Fun and adventure", main_path+"/covers/onepiece.jpeg", "1", "TRUE"));

        getBooks();

        myrecyclerview = view.findViewById(R.id.book_list_recyclerview);
        myAdapter = new BookAdapter(getContext(), lstBooks);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);
//        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        myrecyclerview.setLayoutManager(manager);
        myrecyclerview.setAdapter(myAdapter);

        return view;
    }

    private void getBooks(){
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("email_id",email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        ConnectionManager.sendData(jsonObject.toString(), requestQueue, url_get_book, new ConnectionManager.VolleyCallback() {
            @Override
            public void onSuccessResponse(String response) {
                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    String success = jsonObject1.getString("success");

                    JSONArray jsonArray = jsonObject1.getJSONArray("books");

                    if (success.equals("true")){

//                        Don't put toast at run time because if user moves to fast then the app will crash
//                        Toast.makeText(getContext(),"All Books", Toast.LENGTH_SHORT).show();

                        for (int i = 0 ; i < jsonArray.length(); i++){
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                            String title = jsonObject2.getString("book_title");
                            String cover_photo = jsonObject2.getString("book_cover_picture");
                            String description = jsonObject2.getString("book_description");
                            String book_id = jsonObject2.getString("book_id");
                            String book_path = jsonObject2.getString("book_path");
                            String favourite = jsonObject2.getString("favourite_book");
//                            String favourite = "FALSE";

                            String thumbnail = main_path+cover_photo;

                            lstBooks.add(new BookModel(title, "Fun", description, thumbnail, book_id, favourite, book_path));

                        }
                        myAdapter.notifyDataSetChanged();


                    }

                } catch (JSONException e) {
//                    e.printStackTrace();
                    System.out.println(e.toString());
//                    Toast.makeText(getContext(), "Books not Found : " + e, Toast.LENGTH_SHORT).show();
                    new AlertDialog.Builder(getContext())
                            .setTitle("Books Not Found")
                            .setMessage("Sorry No books Found.")
                            .setPositiveButton("Close App", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }).show();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getContext(), "Books Loading Error : "+error, Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(getContext())
                        .setTitle("Server error!")
                        .setMessage("Some issues with server has occurred, Please try again later.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).show();
            }
        });
    }

}

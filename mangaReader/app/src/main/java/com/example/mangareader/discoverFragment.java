package com.example.mangareader;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.mangareader.Constants.main_path;
import static com.example.mangareader.Constants.url_get_manga;

//import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

public class discoverFragment extends Fragment {

    ViewPager viewPager;
    RecyclerView popular_recyclerview, recyclerId_reading, recyclerId_recommended, recyclerId_hot_update;
    RecyclerViewAdapter hotAdapter, recommendedAdapter,myAdapter,readingAdapter;

    //    private AutoScrollViewPager viewPager;
    private String manga_id;
    LinearLayout sliderDotspanel;
    private int dotscount;
    SessionManager sessionManager;
    String email;
    Context contextThemeWrapper;
    private ImageView [] dots;
    CarouselView carouselView;
    List<comic> hot_updates_comics, recommended_comics, popular_comics, quick_read_comics;

//    private Button addCardFavourite, removeCardFavourite;
    private Timer timer;
    private int current_position = 0;

//    private int[] mImages = new int[]{
//            R.drawable.one_piece2, R.drawable.hunter_x_hunter, R.drawable.death_note, R.drawable.detective_conan, R.drawable.full_metal
//    };
//
//    private String[] mImageTitle = new String[]{
//            "One Piece","Hunter x Hunter","Death Note","Detective Conan","Full Metal Alchemist: Brotherhood"
//    };

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

        View view = localInflater.inflate(R.layout.fragment_discover, container, false);


        viewPager = view.findViewById(R.id.image_slider_viewPager);

        HashMap<String, String> user = sessionManager.getUserDetails();
        email = user.get(SessionManager.EMAIL);

//        image Carousel
//        carouselView = view.findViewById(R.id.image_carousel);
//        carouselView.setImageListener(new ImageListener() {
//            @Override
//            public void setImageForPosition(int position, ImageView imageView) {
//                imageView.setImageResource(mImages[position]);
//            }
//        });
//        carouselView.setImageClickListener(new ImageClickListener() {
//            @Override
//            public void onClick(int position) {
//                Toast.makeText(getContext(), mImageTitle[position],Toast.LENGTH_SHORT).show();
//            }
//        });

        sliderDotspanel = view.findViewById(R.id.Sliderdots);

//        addCardFavourite = view.findViewById(R.id.btnCard_add_favourite);
//        removeCardFavourite = view.findViewById(R.id.btnCard_remove_favourite);

        imageSliderAdapter imageSliderAdapter = new imageSliderAdapter(getContext());
        viewPager.setAdapter(imageSliderAdapter);

//        viewPager.setInterval(2000);
//        viewPager.startAutoScroll();

//        createSlideShow();

        dotscount = imageSliderAdapter.getCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++){
            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8,0,8,0);

            sliderDotspanel.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                int j;
                for (j = 0; j < dotscount; j++){
                    dots[j].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));
                }

                dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 2000);


//
        hot_updates_comics = new ArrayList<>();
        recommended_comics = new ArrayList<>();
        popular_comics = new ArrayList<>();
        quick_read_comics = new ArrayList<>();
//        lstComic.add(new comic("one piece", "Fun", "fun", "http://192.168.0.106:5656/covers/onepiece.jpeg"));
//        lstComic.add(new comic("One Piece", "Category Comic", "Description Comic", R.drawable.one_piece));
//        lstComic.add(new comic("One Piece", "Category Comic", "Description Comic", R.drawable.one_piece));
//        lstComic.add(new comic("One Piece", "Category Comic", "Description Comic", R.drawable.one_piece));
//        lstComic.add(new comic("One Piece", "Category Comic", "Description Comic", R.drawable.one_piece));
//        lstComic.add(new comic("One Piece", "Category Comic", "Description Comic", R.drawable.one_piece));
//        lstComic.add(new comic("One Piece", "Category Comic", "Description Comic", R.drawable.one_piece));
//        lstComic.add(new comic("One Piece", "Category Comic", "Description Comic", R.drawable.one_piece));
//        lstComic.add(new comic("One Piece", "Category Comic", "Description Comic", R.drawable.one_piece));
//        lstComic.add(new comic("One Piece", "Category Comic", "Description Comic", R.drawable.one_piece));

        getManga();

        recyclerId_hot_update = view.findViewById(R.id.recyclerId_hot_update);
        hotAdapter = new RecyclerViewAdapter(getContext(), hot_updates_comics);
        LinearLayoutManager hotManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerId_hot_update.setLayoutManager(hotManager);
        recyclerId_hot_update.setAdapter(hotAdapter);

        recyclerId_recommended = view.findViewById(R.id.recyclerId_recommended);
        recommendedAdapter = new RecyclerViewAdapter(getContext(), recommended_comics);
        LinearLayoutManager recommendedManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerId_recommended.setLayoutManager(recommendedManager);
        recyclerId_recommended.setAdapter(recommendedAdapter);

        popular_recyclerview = view.findViewById(R.id.recyclerId_popular);
        myAdapter = new RecyclerViewAdapter(getContext(), popular_comics);
//        To show horizontal scroll view
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        popular_recyclerview.setLayoutManager(manager);
        popular_recyclerview.setAdapter(myAdapter);

        recyclerId_reading = view.findViewById(R.id.recyclerId_reading);
        readingAdapter = new RecyclerViewAdapter(getContext(), quick_read_comics);
        LinearLayoutManager readingManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerId_reading.setLayoutManager(readingManager);
        recyclerId_reading.setAdapter(readingAdapter);

//        Toast.makeText(getContext(), "Here you come again", Toast.LENGTH_LONG).show();

        return view;
    }

    private void getManga(){

        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("email_id",email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        ConnectionManager.sendData(jsonObject.toString(), requestQueue, url_get_manga, new ConnectionManager.VolleyCallback() {
            @Override
            public void onSuccessResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray hot_updates = jsonObject.getJSONArray("hot_updates");
                    JSONArray recommended = jsonObject.getJSONArray("recommended");
                    JSONArray popular = jsonObject.getJSONArray("popular");
                    JSONArray quick_reads = jsonObject.getJSONArray("quick_reads");

//                    System.out.println("Mangas Array: " +jsonArray);

                    if (success.equals("true")){
System.out.println(hot_updates);
//                        System.out.println(recommended);
//                        System.out.println(popular);

                        for (int i = 0; i < hot_updates.length(); i++ ){
                            JSONObject jsonObject1 = hot_updates.getJSONObject(i);

                            String title = jsonObject1.getString("title");
                            String cover_photo = jsonObject1.getString("cover_picture");
                            String description = jsonObject1.getString("description");
                            String id_manga = jsonObject1.getString("manga_id");
                            String favourite = jsonObject1.getString("favourite");

                            System.out.println("Favorites : "+favourite);


                            manga_id = id_manga;

                            comic comic = new comic(title, "Fun", description, main_path+cover_photo, manga_id, favourite);
//                            System.out.println("title : "+title+" cover_photo : "+cover_photo+" description : "+description);

//                                    lstComic.add(new comic(title, "Fun", description, "http://192.168.0.106"+cover_photo));
                            hot_updates_comics.add(comic);

                        }

                        hotAdapter.notifyDataSetChanged();

                        for (int i = 0; i<recommended.length(); i++){
                            JSONObject jsonObject1 = recommended.getJSONObject(i);

                            String title = jsonObject1.getString("title");
                            String cover_photo = jsonObject1.getString("cover_picture");
                            String description = jsonObject1.getString("description");
                            String id_manga = jsonObject1.getString("manga_id");
                            String favourite = jsonObject1.getString("favourite");

                            System.out.println("Favorites : "+favourite);


                            manga_id = id_manga;

                            comic comic = new comic(title, "Fun", description, main_path+cover_photo, manga_id, favourite);

                            recommended_comics.add(comic);

                        }

                        recommendedAdapter.notifyDataSetChanged();

                        for (int i = 0; i<popular.length(); i++){
                            JSONObject jsonObject1 = popular.getJSONObject(i);

                            String title = jsonObject1.getString("title");
                            String cover_photo = jsonObject1.getString("cover_picture");
                            String description = jsonObject1.getString("description");
                            String id_manga = jsonObject1.getString("manga_id");
                            String favourite = jsonObject1.getString("favourite");

                            System.out.println("Favorites : "+favourite);


                            manga_id = id_manga;

                            comic comic = new comic(title, "Fun", description, main_path+cover_photo, manga_id, favourite);

                            popular_comics.add(comic);

                        }
                        myAdapter.notifyDataSetChanged();

                        for (int i = 0; i<quick_reads.length(); i++){
                            JSONObject jsonObject1 = quick_reads.getJSONObject(i);

                            String title = jsonObject1.getString("title");
                            String cover_photo = jsonObject1.getString("cover_picture");
                            String description = jsonObject1.getString("description");
                            String id_manga = jsonObject1.getString("manga_id");
                            String favourite = jsonObject1.getString("favourite");
                            System.out.println("Favorites : "+favourite);


                            manga_id = id_manga;

                            comic comic = new comic(title, "Fun", description, main_path+cover_photo, manga_id, favourite);

                            quick_read_comics.add(comic);

                        }
                        readingAdapter.notifyDataSetChanged();

                    }
                    else if (success.equals("false")){
                        Toast.makeText(getContext(), "Mangas Not found ", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Manga Error : " +e.toString(), Toast.LENGTH_SHORT).show();
                    System.out.println(e.toString());
//                    e.printStackTrace();
                }

            }

            @Override
            public void onErrorResponse(VolleyError error) {

//                Toast.makeText(getContext(), "Manga Loading Error : " +error.toString(), Toast.LENGTH_SHORT).show();
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



//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_get_manga,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            String success = jsonObject.getString("success");
//                            JSONArray jsonArray = jsonObject.getJSONArray("mangas");
//
//                            System.out.println("Mangas Array: " +jsonArray);
//
//                            if (success.equals("true")){
//
//                                for (int i = 0; i < jsonArray.length(); i++ ){
//                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//
//                                    String title = jsonObject1.getString("title");
//                                    String cover_photo = jsonObject1.getString("cover_picture");
//                                    String description = jsonObject1.getString("description");
//                                    String id_manga = jsonObject1.getString("manga_id");
//
//                                    manga_id = id_manga;
//
//                                    comic comic = new comic(title, "Fun", description, "http://192.168.0.106:5656"+cover_photo, manga_id);
//                                    System.out.println("title : "+title+" cover_photo : "+cover_photo+" description : "+description);
//
////                                    lstComic.add(new comic(title, "Fun", description, "http://192.168.0.106"+cover_photo));
//                                    lstComic.add(comic);
//
//                                    popular_recyclerview = getActivity().findViewById(R.id.recyclerId_popular);
//                                    RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getContext(), lstComic);
////        To show horizontal scroll view
//                                    LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
//                                    popular_recyclerview.setLayoutManager(manager);
//                                    popular_recyclerview.setAdapter(myAdapter);
//
//                                    recyclerId_reading = getActivity().findViewById(R.id.recyclerId_reading);
//                                    RecyclerViewAdapter readingAdapter = new RecyclerViewAdapter(getContext(), lstComic);
//                                    LinearLayoutManager readingManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
//                                    recyclerId_reading.setLayoutManager(readingManager);
//                                    recyclerId_reading.setAdapter(readingAdapter);
//
//                                    recyclerId_recommended = getActivity().findViewById(R.id.recyclerId_recommended);
//                                    RecyclerViewAdapter recommendedAdapter = new RecyclerViewAdapter(getContext(), lstComic);
//                                    LinearLayoutManager recommendedManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
//                                    recyclerId_recommended.setLayoutManager(recommendedManager);
//                                    recyclerId_recommended.setAdapter(recommendedAdapter);
//
//                                    recyclerId_hot_update = getActivity().findViewById(R.id.recyclerId_hot_update);
//                                    RecyclerViewAdapter hotAdapter = new RecyclerViewAdapter(getContext(), lstComic);
//                                    LinearLayoutManager hotManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
//                                    recyclerId_hot_update.setLayoutManager(hotManager);
//                                    recyclerId_hot_update.setAdapter(hotAdapter);
//
//                                }
//
//                            }
//                            else if (success.equals("false")){
//                                Toast.makeText(getContext(), "Mangas Not found ", Toast.LENGTH_SHORT).show();
//                            }
//
//                        } catch (JSONException e) {
//                            Toast.makeText(getContext(), "Manga Error : " +e.toString(), Toast.LENGTH_SHORT).show();
//                            System.out.println(e.toString());
//                            e.printStackTrace();
//                        }
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
////                        Toast.makeText(getContext(), "Manga Loading Error : " +error.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                })
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                return super.getParams();
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//        requestQueue.add(stringRequest);

    }

    public class MyTimerTask extends TimerTask{
        @Override
        public void run() {

//            If we don't implement this code then while changing the tab the app crashes because there will be null object for runOnThread method.
            if (getActivity() == null){
                return;
            }

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (viewPager.getCurrentItem() == 0){
                        viewPager.setCurrentItem(1);
                    } else if (viewPager.getCurrentItem() == 1){
                        viewPager.setCurrentItem(2);
                    } else if (viewPager.getCurrentItem() == 2){
                        viewPager.setCurrentItem(3);
                    } else if (viewPager.getCurrentItem() == 3){
                        viewPager.setCurrentItem(4);
                    } else{
                        viewPager.setCurrentItem(0);
                    }

                }
            });

        }
    }

    private void createSlideShow(){

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (current_position == dotscount) {
                    current_position = 0;
                }
                viewPager.setCurrentItem(current_position++, true);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 250, 2500);

    }

}

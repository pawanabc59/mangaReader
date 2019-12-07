package com.example.mangareader;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.mangareader.Fragments.BookListFragment;
import com.example.mangareader.Fragments.FavouriteBookFragment;

public class BookActivity extends AppCompatActivity {
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = new SessionManager(getApplicationContext());
        if (sessionManager.loadNightModeState() == true) {

            setTheme(R.style.darktheme);
        } else {
            setTheme(R.style.AppTheme);
        }

        setContentView(R.layout.activity_book);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        sessionManager.checkLogin();

        BottomNavigationView bottomNav = findViewById(R.id.book_bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.book_fragment_container, new BookListFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.book_nav_list:
                            selectedFragment = new BookListFragment();
                            break;
                        case R.id.book_nav_favourite:
                            selectedFragment = new FavouriteBookFragment();
                            break;

                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.book_fragment_container, selectedFragment).commit();


                    return true;
                }
            };

}

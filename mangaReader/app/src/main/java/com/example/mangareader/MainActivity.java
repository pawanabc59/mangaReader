package com.example.mangareader;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

import static com.example.mangareader.Constants.main_path;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //    Button secondActivity;
    SessionManager sessionManager;
    private DrawerLayout drawerLayout;
    private SwitchCompat switchCompat;
    ImageView nav_profile_photo;
    TextView nav_username;

    //private Switch switchCompat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = new SessionManager(getApplicationContext());
        if (sessionManager.loadNightModeState() == true) {

            setTheme(R.style.darktheme);
        } else {
            setTheme(R.style.AppTheme);
        }

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetails();
        String mEmail = user.get(sessionManager.EMAIL);
        String profile_photo_link = user.get(sessionManager.PROFILE_PHOTO);

//        RelativeLayout relativeLayout = findViewById(R.id.layout_activity_main);
        NavigationView navigationView1 = findViewById(R.id.nav_view);
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.nav_header, navigationView1, false);

        nav_profile_photo = view.findViewById(R.id.nav_profile_photo);
        nav_username = view.findViewById(R.id.nav_username);

        Picasso.get().load(main_path + profile_photo_link).error(R.drawable.luffy_icon).into(nav_profile_photo);
        nav_username.setText(mEmail);

        navigationView1.addView(view);

//        secondActivity = findViewById(R.id.goToSecond);

//        secondActivity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent second = new Intent(getApplicationContext(), loginSignup.class);
//                startActivity(second);
//                finish();
//            }
//
//        });

//        sessionManager.checkLogin();

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new discoverFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.nav_discover:
                            selectedFragment = new discoverFragment();
                            break;
                        case R.id.nav_favourite:
                            selectedFragment = new favouriteFragment();
                            break;
                        case R.id.nav_recent:
                            selectedFragment = new recentFragment();
                            break;
                        case R.id.nav_download:
                            selectedFragment = new downloadFragment();
                            break;
                        case R.id.nav_account:
                            if (sessionManager.isLoggin()) {
                                selectedFragment = new ProfileFragment();
                            } else {
                                selectedFragment = new AccountFragment();
                                break;
                            }
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();


                    return true;
                }
            };


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.profile_fragment);
//        fragment.onActivityResult(requestCode, resultCode, data);
//    }

    //    TO send the image or data to respected fragment which has called for result. This works fine for now.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_comics:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_books:
                Intent intent3 = new Intent(getApplicationContext(), BookActivity.class);
                startActivity(intent3);
                break;
            case R.id.nav_night_mode:
//                sessionManager.setNightModeState(true);
//                LayoutInflater layoutInflater1 = getLayoutInflater();
//                LinearLayout linearLayout = findViewById(R.id.layout_switch_id);
//                View view = layoutInflater1.inflate(R.layout.layout_switch, linearLayout, false);

//                switchCompat = findViewById(R.id.drawer_night_switch);
//                if (sessionManager.loadNightModeState() == true) {
////                    switchCompat.setChecked(true);
//                } else {
////                    switchCompat.setChecked(false);
//                }

                if (sessionManager.loadNightModeState() == true) {
                    sessionManager.setNightModeState(false);
//                    switchCompat.setChecked(false);

                } else {
                    sessionManager.setNightModeState(true);
//                    switchCompat.setChecked(true);

                }

//                switchCompat = view.findViewById(R.id.drawer_night_switch);
//                switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                        if (b){
//                            sessionManager.setNightModeState(true);
//                            switchCompat.setChecked(true);
////                            recreate();
//                        }
//                        else {
//                            sessionManager.setNightModeState(false);
//                            switchCompat.setChecked(false);
////                            recreate();
//                        }
//                    }
//                });

//                linearLayout.addView(view);

                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);

                break;
            case R.id.nav_about_us:
                Intent intent2 = new Intent(getApplicationContext(), DeveloperProfileActivity.class);
                startActivity(intent2);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}

package com.datn.finhome.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.datn.finhome.R;
import com.datn.finhome.Views.Fragment.HomeFragment;
import com.datn.finhome.Views.Fragment.AccountViewFragment;
import com.datn.finhome.Views.Fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainMenuActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    FrameLayout fragmentContainer;
    HomeFragment HomeView;
    AccountViewFragment AccountView;
    SearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        initControl();
        HomeView = new HomeFragment();
        setFragment(HomeView);

    }

    private void initControl(){
        fragmentContainer = findViewById(R.id.fragment_container);
        bottomNavigation = findViewById(R.id.bottom_navigation);

        bottomNavigation.setOnNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();

            switch (id) {
                case R.id.nav_home:
                    HomeView = new HomeFragment();
                    setFragment(HomeView);
                    return true;
                case R.id.nav_account:
                    AccountView = new AccountViewFragment();
                    setFragment(AccountView);
                    return true;

                case R.id.nav_search:
                    searchFragment = new SearchFragment();
                    setFragment(searchFragment);
                    return true;
                default:
                    return false;
            }
        });
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }
}
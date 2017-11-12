package com.ryungna.please;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class navigation extends AppCompatActivity {

    long lastPressed;
    Fragment fragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new fragMe();
                    switchFragment(fragment);

                    return true;

                case R.id.navigation_dashboard:
                    fragment = new fragGroup();
                    switchFragment(fragment);


                    return true;

                case R.id.navigation_notifications:
                    fragment = new fragFriends();
                    switchFragment(fragment);
                    return true;
            }

            return false;
        }


    };
    public void switchFragment(Fragment fragment){ //프래그먼트 바뀌는거
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content,fragment);
        transaction.commit();

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragMe fragment = new fragMe(); //처음에 fragMe
        fragmentTransaction.add(R.id.content, fragment);
        fragmentTransaction.commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }


    /****************************/







    /////
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastPressed < 1500) {
            finish();
        }
        Toast.makeText(this,"한번더 누르면 종료",Toast.LENGTH_SHORT).show();
        lastPressed = System.currentTimeMillis();
    }


}

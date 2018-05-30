package iot.e1m4.com.greenright;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;


import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import info.addon.SessionManager;
import info.app.AppConfig;

public class MainActivity extends AppCompatActivity {
    FragmentTransaction transaction;

    WalkFragment mWalkFragment = new WalkFragment();
    MapsFragment mMapsFragment = new MapsFragment();
    HomeFragment mHomeFragment = new HomeFragment();


    private SessionManager sessionManager;

    PointFragment mPointFragment= new PointFragment();
    DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionManager = new SessionManager(this);

        //tool bar 관련
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        //actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //actionBar.setCustomView(R.layout.custom_bar);
        //actionBar.setBackgroundDrawable(new ColorDrawable(0xFFffffff));

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);

        //navigation view 관련
        mDrawerLayout=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.navigationView);

        //user id 입력하는 곳 = nav_header_userId


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                item.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id=item.getItemId();

                transaction=getSupportFragmentManager().beginTransaction();
                switch(id){
                    case R.id.first_navigation_item:
                        Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.second_navigation_item:
                        transaction.replace(R.id.contentContainer, mPointFragment).commit();
                        break;
                    case R.id.third_navigation_item:
                        Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                        break;
                }

                return true;
            }
        });

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                transaction=getSupportFragmentManager().beginTransaction();
                if(tabId==R.id.tab_co2){
                    transaction.replace(R.id.contentContainer, mWalkFragment);
                    //transaction.addToBackStack(null);
                    transaction.commit();
                    return;
                }else if(tabId==R.id.tab_cup){
                    transaction.replace(R.id.contentContainer, mMapsFragment);

                    transaction.commit();
                    return;
                }else if(tabId==R.id.tab_main){
                    transaction.replace(R.id.contentContainer, mHomeFragment);

                    transaction.commit();
                    return;
                }
            }
        });

        bottomBar.setOnTabReselectListener(
                new OnTabReselectListener() {
                    @Override
                    public void onTabReSelected(int tabId) {
                        transaction=getSupportFragmentManager().beginTransaction();
                        if(tabId==R.id.tab_co2){
                            transaction.replace(R.id.contentContainer, mWalkFragment);
                            //transaction.addToBackStack(null);
                            transaction.commit();
                            return;
                        }else if(tabId==R.id.tab_cup){
                            transaction.replace(R.id.contentContainer, mMapsFragment);

                            transaction.commit();
                            return;
                        }else if(tabId==R.id.tab_main){
                            transaction.replace(R.id.contentContainer, mHomeFragment);
                            transaction.commit();
                            return;
                        }else if (tabId== R.id.tab_green_market) {
                            startService(new Intent(MainActivity.this, DayResetService.class));
                            Toast.makeText(MainActivity.this, "체크", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
        );


        Intent intent1 = new Intent(this, BeaconListenerService.class);
        startService(intent1);
        if (!sessionManager.isDayChecked(AppConfig.DISTANCE_CHECK_ID)) {
            Intent intent2 = new Intent(this, DistanceListenerService.class);
            startService(intent2);
            Toast.makeText(MainActivity.this, "시작", Toast.LENGTH_SHORT).show();
            sessionManager.setDayChecked(AppConfig.DISTANCE_CHECK_ID, true);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        switch (id){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getIntent().getAction() == "NOTIFICATION"){
            transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentContainer, new MapsFragment()).commit();
            return;
        }
    }
}



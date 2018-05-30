package iot.e1m4.com.greenright;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionManager = new SessionManager(this);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.custom_bar);
        actionBar.setBackgroundDrawable(new ColorDrawable(0xFFffffff));
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);

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
    protected void onResume() {
        super.onResume();
        if(getIntent().getAction() == "NOTIFICATION"){
            transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentContainer, new MapsFragment()).commit();
            return;
        }
    }
}


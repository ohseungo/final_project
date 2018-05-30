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
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity {
    FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.custom_bar);
        actionBar.setBackgroundDrawable(new ColorDrawable(0xFFffffff));
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);

        transaction=getSupportFragmentManager().beginTransaction();

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if(tabId==R.id.tab_co2){
                    transaction.replace(R.id.contentContainer, new WalkFragment()).commit();
                    return;
                }else if(tabId==R.id.tab_cup){
                    transaction.replace(R.id.contentContainer, new MapsFragment()).commit();
                    return;
                }else if(tabId==R.id.tab_main){
                    transaction.replace(R.id.contentContainer, new HomeFragment()).commit();
                }

            }
        });
        Intent intent = new Intent(this, BeaconListenerService.class);
        startService(intent);

    }



    @Override
    protected void onResume() {
        super.onResume();
        if(getIntent().getAction() == "NOTIFICATION"){
            transaction.replace(R.id.contentContainer, new MapsFragment()).commit();
            return;
        }
    }
}


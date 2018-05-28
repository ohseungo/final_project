package iot.e1m4.com.greenright;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.custom_bar);
        actionBar.setBackgroundDrawable(new ColorDrawable(0xFFffffff));
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);

        //각 버튼 눌렀을 때 이동하는 페이지
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                if(tabId==R.id.tab_barcode){
                    transaction.replace(R.id.contentContainer, new FragmentIntro1()).commit();
                }


            }
        });

    }
}

package iot.e1m4.com.greenright;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
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
        actionBar.setBackgroundDrawable(new ColorDrawable(0xFFa3c9c7));
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);

        //각 버튼 눌렀을 때 이동하는 페이지
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
             switch (tabId){
                   case R.id.tab_co2:
                       break;
                   case R.id.tab_cup:
                       break;
                 case R.id.tab_barcode:
                     break;
                   case R.id.tab_green_market:
                       break;
                   case R.id.tab_setting:
                       break;
               }


            }
        });

    }
}

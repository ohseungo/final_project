package iot.e1m4.com.greenright;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import info.addon.DayResetManager;
import info.addon.SessionManager;
import info.app.AppConfig;
import info.app.AppController;

public class MainActivity extends AppCompatActivity {
    FragmentTransaction transaction;

    WalkFragment mWalkFragment;
    MapsFragment mMapsFragment;
    HomeFragment mHomeFragment;
    SaveFragment mSaveFragment;
    MarketFragment mMarketFragment;
    MypageFragment mMypageFragment;


    private SessionManager sessionManager;

    PointFragment mPointFragment= new PointFragment();
    DrawerLayout mDrawerLayout;
    View tempView;
    NavigationView mNagivationView;


    TextView userHead;

    private final String TAG = getClass().getSimpleName();

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

        for (String key : sessionManager.getPref().getAll().keySet()) {
            Log.e("TAG", key + " " + sessionManager.getPref().getAll().get(key));
        }

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);

        mWalkFragment = new WalkFragment();
        mMapsFragment = new MapsFragment();
        mHomeFragment = new HomeFragment();
        mSaveFragment = new SaveFragment();
        mMarketFragment = new MarketFragment();
        mMypageFragment = new MypageFragment();

        //navigation view 관련
        mDrawerLayout=findViewById(R.id.drawer_layout);
        mNagivationView =findViewById(R.id.navigationView);
        NavigationView navigationView=findViewById(R.id.navigationView);

        //user id 입력하는 곳 = nav_header_userId

        DayResetManager.setDayResetAlarm(this);
        ///알람 시작

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                item.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id=item.getItemId();

                transaction=getSupportFragmentManager().beginTransaction();
                switch(id){
                    case R.id.first_navigation_item: //내정보 표시
                        transaction.replace(R.id.contentContainer, mMypageFragment).commit();
                        break;

                    case R.id.second_navigation_item: //포인트 표시
                        transaction.replace(R.id.contentContainer, mPointFragment).commit();
                        break;
                    case R.id.third_navigation_item: //로그아웃
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("로그아웃하시겠습니까?")
                                .setCancelable(false)
                                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        logout();
                                        return;
                                    }
                                }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        }).create().show();

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
                }else if(tabId==R.id.tab_cup){
                    transaction.replace(R.id.contentContainer, mMapsFragment);

                }else if(tabId==R.id.tab_main){
                    transaction.replace(R.id.contentContainer, mHomeFragment);

                }else if(tabId==R.id.tab_barcode){
                    transaction.replace(R.id.contentContainer,mSaveFragment);

                }else if(tabId==R.id.tab_green_market){
                    transaction.replace(R.id.contentContainer, mMarketFragment);

                }
                //transaction.addToBackStack(null);
                transaction.commit();
                return;
            }
        });

        bottomBar.setOnTabReselectListener(
                new OnTabReselectListener() {
                    @Override
                    public void onTabReSelected(int tabId) {
                        transaction=getSupportFragmentManager().beginTransaction();
                        if(tabId==R.id.tab_co2){
                            transaction.replace(R.id.contentContainer, mWalkFragment);

                        }else if(tabId==R.id.tab_cup){
                            transaction.replace(R.id.contentContainer, mMapsFragment);
                        }else if(tabId==R.id.tab_main){
                            /*PointManager.addPointData(sessionManager.getUserId(), 100,
                                    2, "테스트", MainActivity.this);
                            Toast.makeText(MainActivity.this, "포인트 추가", Toast.LENGTH_SHORT).show();*/
/*
                            Intent intent = new Intent(MainActivity.this,DayResetService.class);
                            startService(intent);
*/

                          /*  Intent intent = new Intent(MainActivity.this, PopUpVideo.class);
                            startActivity(intent);*/
                            transaction.replace(R.id.contentContainer, mHomeFragment);

                        }else if (tabId== R.id.tab_green_market) {
                            /////////테스트////////////////
                            transaction.replace(R.id.contentContainer, mMarketFragment);
                        }
                        transaction.commit();
                        return;
                    }
                }
        );


        Intent intent1 = new Intent(this, BeaconListenerService.class);
        startService(intent1);


        tempView = mNagivationView.getHeaderView(0);
        userHead = tempView.findViewById(R.id.nav_header_userId);
        userUpdate();
    }

    private void userUpdate() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.FIND_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //////////////성공//////////////////////
                        try {
                            userHead.setText(URLDecoder.decode(new JSONObject(response).getString("userName")
                            , "UTF-8")+ " 님 안녕하세요");
                            return;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                     public void onErrorResponse(VolleyError error) {
                    ///////////////////실패/////////////////////////////////
                    Toast.makeText(MainActivity.this, "회원 정보 요청 중 문제 발생", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId", sessionManager.getUserId());
                return params;
            }
        };
        stringRequest.setTag(TAG);
        AppController.getInstance().
                addToRequestQueue(stringRequest);
        return;
    }

    private void logout() {
        sessionManager.setLogin(false, null);
        startActivity(new Intent(this, IntroLoginActivity.class));
        finish();
        return;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    ////???//
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
        if(getIntent().getAction()!= null){
            if(getIntent().getAction().equals(BeaconListenerService.NOTIFICATION_ID)) { //알림 타고 들어왔을 경우
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.contentContainer, new PointFragment()).commit();
                return;
            }
            if(getIntent().getAction().equals(BeaconListenerService.NOTIFICATION_ID_VIDEO)) { //비디오 알림 타고 들어왔을 경우
                Intent intent = new Intent(MainActivity.this, PopUpVideo.class);
                startActivity(intent);
                return;
            }

        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(getIntent().getAction()!= null){
            if(getIntent().getAction().equals(BeaconListenerService.NOTIFICATION_ID)) { //알림 타고 들어왔을 경우
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.contentContainer, new PointFragment()).commit();
                return;
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent distanceListenerIntent = new Intent(this, DistanceListenerService.class);
        startService(distanceListenerIntent);

    }


    ////////////////////////백버튼 인터페이스///////////////////////////
    public interface onKeyBackPressedListener {
        public void onBack();
    }
    private onKeyBackPressedListener mOnKeyBackPressedListener;

    public void setmOnKeyBackPressedListener(onKeyBackPressedListener listener) {
        mOnKeyBackPressedListener = listener;
    }

    @Override
    public void onBackPressed() {
        if (mOnKeyBackPressedListener != null) {
            mOnKeyBackPressedListener.onBack();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        AppController.getInstance().cancelPendingRequests(TAG);

    }


}









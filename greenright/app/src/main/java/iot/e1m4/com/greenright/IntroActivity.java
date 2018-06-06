package iot.e1m4.com.greenright;

import android.content.Intent;
import android.media.MediaCas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import info.addon.SessionManager;

public class IntroActivity extends AppCompatActivity {

    private static final int TOTAL_PAGES = 3;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro1);

        sessionManager = new SessionManager(getApplicationContext());
        if (sessionManager.isFirstOn()) {
            Intent intent=new Intent(IntroActivity.this, IntroLoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        sessionManager.setFirstOn();
        mPager = (ViewPager) findViewById(R.id.intro_pager);
        mPager.setPageTransformer(true, new DepthPageTransformer());
        mPagerAdapter = new IntroPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        //getSupportActionBar().hide();
        final ImageView introIndicator1 = (ImageView) findViewById(R.id.intro1);
        final ImageView introIndicator2 = (ImageView) findViewById(R.id.intro2);
        final ImageView introIndicator3 = (ImageView) findViewById(R.id.intro3);
        final TextView btnIntroSkip = (TextView) findViewById(R.id.intro_skip);

        btnIntroSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(IntroActivity.this, IntroLoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    introIndicator1.setImageResource(R.drawable.intro_dot_selected);
                    introIndicator2.setImageResource(R.drawable.intro_dot_default);
                    introIndicator3.setImageResource(R.drawable.intro_dot_default);
                } else if (position == 1) {
                    introIndicator1.setImageResource(R.drawable.intro_dot_default);
                    introIndicator2.setImageResource(R.drawable.intro_dot_selected);
                    introIndicator3.setImageResource(R.drawable.intro_dot_default);
                } else if (position == 2) {
                    introIndicator1.setImageResource(R.drawable.intro_dot_default);
                    introIndicator2.setImageResource(R.drawable.intro_dot_default);
                    introIndicator3.setImageResource(R.drawable.intro_dot_selected);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private class IntroPagerAdapter extends FragmentStatePagerAdapter {
        public IntroPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FragmentIntro1();
                case 1:
                    return new FragmentIntro2();
                case 2:
                    return new FragmentIntro3();
                default:
                    break;
            }
            return null;
        }
        @Override
        public int getCount() {
            return TOTAL_PAGES;
        }
    }

    private class DepthPageTransformer implements ViewPager.PageTransformer {

        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
}

package iot.e1m4.com.greenright;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.widget.Toast;
import android.widget.VideoView;

import info.addon.PointManager;
import info.addon.SessionManager;

public class PopUpVideo extends Activity{
    private VideoView mVideoView;
    private SessionManager sessionManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_view);

  /*      DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * 0.8), (int) (height * 0.6));*/
        sessionManager = new SessionManager(this);
        mVideoView= findViewById(R.id.popUpVideoView);

        String url = "android.resource://" + getPackageName() + "/" + R.raw.testvid;
        Uri uri = Uri.parse(url);
        mVideoView.setVideoURI(uri);
        mVideoView.requestFocus();
        mVideoView.start();
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(PopUpVideo.this, "15 포인트 추가!", Toast.LENGTH_SHORT).show();
                PointManager.addPointData(sessionManager.getUserId(), 15, 4, "환경 영상 시청",
                        PopUpVideo.this);
                Intent intent = new Intent(PopUpVideo.this, MainActivity.class).setAction(BeaconListenerService.NOTIFICATION_ID);
                startActivity(intent);
                finish();
                return;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

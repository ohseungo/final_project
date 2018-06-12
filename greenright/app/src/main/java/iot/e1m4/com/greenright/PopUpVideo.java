package iot.e1m4.com.greenright;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import info.app.AppController;

public class PopUpVideo extends Activity{
    private VideoView mVideoView;
    private SessionManager sessionManager;
    private final String TAG = getClass().getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_view);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("그린 영상을 시청하시겠습니까? \n 영상 시청시 15포인트 적립!")
                .setCancelable(false)
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showVideo();
                        return;
                    }
                }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

            AlertDialog dialog = builder.create();
            dialog.show();

    }

    private void showVideo() {
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
                        PopUpVideo.this, TAG);
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

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        AppController.getInstance().cancelPendingRequests(TAG);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

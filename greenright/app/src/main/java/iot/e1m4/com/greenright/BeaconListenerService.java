package iot.e1m4.com.greenright;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.estimote.proximity_sdk.proximity.ProximityAttachment;
import com.estimote.proximity_sdk.proximity.ProximityObserver;
import com.estimote.proximity_sdk.proximity.ProximityObserverBuilder;
import com.estimote.proximity_sdk.proximity.ProximityZone;

import info.addon.PointManager;
import info.addon.SessionManager;
import info.app.AppController;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class BeaconListenerService extends Service {

    private ProximityObserver proximityObserver;

    private NotificationManager notificationManager;
    private Notification inNotification;
    private Notification outNotification;
    private Notification videoNotification;

    public static String NOTIFICATION_ID = "beacon_listen_notification";
    public static String NOTIFICATION_ID_VIDEO = "video_notification";

    private final String TAG = getClass().getSimpleName();
    SessionManager sessionManager;
    @Override
    public void onCreate() {
        super.onCreate();
        this.notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        this.inNotification = buildNotification("버스 이용", "대중교통 이용으로 50포인트가 추가되었습니다!"
                                                ,NOTIFICATION_ID, MainActivity.class);
        this.videoNotification = buildNotification("영상 감지", "영상을 시청하면 50포인트 추가!"
                                                ,NOTIFICATION_ID_VIDEO, PopUpVideo.class);
        //this.outNotification = buildNotification("bye", "bye bye");
        sessionManager = new SessionManager(this);
    }


    private Notification buildNotification(String title, String text, String action, Class destination) {
        Notification notification = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(PendingIntent.getActivity( this, 0,
                        new Intent( this, destination).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                                                                .setAction(action),
                        PendingIntent.FLAG_UPDATE_CURRENT))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build();


        return notification;
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.proximityObserver =
                new ProximityObserverBuilder(getApplicationContext(), AppController.getInstance().getCloudCredentials())
                .withOnErrorAction(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        return null;
                    }
                }).withEstimoteSecureMonitoringDisabled()
                        .withTelemetryReportingDisabled().withBalancedPowerMode().build();

        checkProximity();
        return super.onStartCommand(intent, flags, startId);
    }

    private ProximityZone buildProximityzone(String key, String value, Notification notification) {
        return null;
    }
    public void checkProximity() {
        ProximityZone zone1 = this.proximityObserver.zoneBuilder()
                .forAttachmentKeyAndValue("ohseungo-s-notification-g6m", "example-proximity-zone")
                .inCustomRange(3.0)
                .withOnEnterAction(new Function1<ProximityAttachment, Unit>() {
                    @Override
                    public Unit invoke(ProximityAttachment proximityAttachment) {
                        if (sessionManager.getUserId() == null) {
                            //로그아웃 중지 상태면 중지한다
                            stopSelf();
                            return null;
                        }
                        if (!sessionManager.isBeaconChecked("ohseungo-s-notification-g6m")) {
                            if(AppController.isActivityVisible()) {
                                ///대중교통 / 앱 켜져있음
                                Log.e(TAG, "비콘감지1");

                            }else{

                                Log.e(TAG, "비콘감지 꺼져있을떄1");
                            }
                            notificationManager.notify(1, inNotification);
                            PointManager.addPointData(sessionManager.getUserId(),
                                    50, 3, "대중교통 이용",
                                    BeaconListenerService.this, TAG);
                            sessionManager.setBeaconChecked("ohseungo-s-notification-g6m");
                        }
                        return null;
                    }
                }).withOnExitAction(new Function1<ProximityAttachment, Unit>() {
                    @Override
                    public Unit invoke(ProximityAttachment proximityAttachment) {
                        notificationManager.notify(1, outNotification);
                        return null;
                    }
                }).create();

        ProximityZone zone2 = this.proximityObserver.zoneBuilder()
                .forAttachmentKeyAndValue("viewVideo", "video_sample")
                .inCustomRange(2.0)
                .withOnEnterAction(new Function1<ProximityAttachment, Unit>() {
                    @Override
                    public Unit invoke(ProximityAttachment proximityAttachment) {
                        if (sessionManager.getUserId() == null) {
                            //로그아웃 중지 상태면 중지한다
                            stopSelf();
                            return null;
                        }
                        if (!sessionManager.isBeaconChecked("viewVideo")) {
                            if(AppController.isActivityVisible()) {
                                //앱이 켜져 있을 때
                                Log.e(TAG, "비콘감지2");
                                Intent intent = new Intent(BeaconListenerService.this, PopUpVideo.class)
                                            .setAction(NOTIFICATION_ID_VIDEO).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }else {
                                notificationManager.notify(2, videoNotification);
                                Log.e(TAG, "비콘감지2 꺼져있을때");
                            }
                            sessionManager.setBeaconChecked("viewVideo");
                        }
                        return null;
                    }
                }).withOnExitAction(new Function1<ProximityAttachment, Unit>() {
                    @Override
                    public Unit invoke(ProximityAttachment proximityAttachment) {
                        notificationManager.notify(2, outNotification);
                        return null;
                    }
                }).create();
        //method 화??

        this.proximityObserver.addProximityZone(zone1);
        this.proximityObserver.addProximityZone(zone2);
        proximityObserver.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppController.getInstance().cancelPendingRequests(TAG);
    }
}

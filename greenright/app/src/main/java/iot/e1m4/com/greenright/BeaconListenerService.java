package iot.e1m4.com.greenright;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;


import com.estimote.proximity_sdk.proximity.ProximityAttachment;
import com.estimote.proximity_sdk.proximity.ProximityObserver;
import com.estimote.proximity_sdk.proximity.ProximityObserverBuilder;
import com.estimote.proximity_sdk.proximity.ProximityZone;

import info.app.AppController;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class BeaconListenerService extends Service {

    private ProximityObserver proximityObserver;

    private NotificationManager notificationManager;
    private Notification inNotification;
    private Notification outNotification;


    public BeaconListenerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        this.inNotification = buildNotification("hello", "helloooooo");
        //this.outNotification = buildNotification("bye", "bye bye");
    }


    private Notification buildNotification(String title, String text) {
        Notification notification = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(PendingIntent.getActivity( this, 0,
                        new Intent( this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                                                                .setAction("NOTIFICATION"),
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

    public void checkProximity() {
        ProximityZone zone1 = this.proximityObserver.zoneBuilder()
                .forAttachmentKeyAndValue("ohseungo-s-notification-g6m", "example-proximity-zone")
                .inCustomRange(3.0)
                .withOnEnterAction(new Function1<ProximityAttachment, Unit>() {
                    @Override
                    public Unit invoke(ProximityAttachment proximityAttachment) {
                        notificationManager.notify(1, inNotification);
                        return null;
                    }
                }).withOnExitAction(new Function1<ProximityAttachment, Unit>() {
                    @Override
                    public Unit invoke(ProximityAttachment proximityAttachment) {
                        notificationManager.notify(1, outNotification);
                        return null;
                    }
                }).create();
        this.proximityObserver.addProximityZone(zone1);
        proximityObserver.start();
    }

}

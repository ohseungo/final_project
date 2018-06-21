package iot.e1m4.com.greenright;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import info.addon.PointManager;
import info.addon.SessionManager;

public class DistanceListenerService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    private LocationManager mLocationManager;
    private LocationListener mLocationListner;
    private SessionManager sessionManager;

    private NotificationManager mNotificationManager;
    private Notification mNotification;
    private final String TAG = getClass().getSimpleName();

    private double mySpeed, maxSpeed;
    private Location prevLoc;
    private double currDis;
    private double totalDis;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sessionManager = new SessionManager(this);
        maxSpeed = mySpeed = 0;
        totalDis = sessionManager.getDistanceDayChecked(sessionManager.getUserId());
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListner = new SpeedoActionListener();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Toast.makeText(DistanceListenerService.this, "안됨", Toast.LENGTH_SHORT).show();
            //permission 없음
        }else {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListner);
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListner);
        }
        //Toast.makeText(this, "거리 리스너 시작\n" +
         //       totalDis, Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }




    private class SpeedoActionListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                mySpeed = location.getSpeed();
                if (mySpeed > maxSpeed) {
                    maxSpeed = mySpeed;
                }
                if (prevLoc !=null) {
                    currDis =prevLoc.distanceTo(location);
                }
                prevLoc = location;
                if (mySpeed>0 && mySpeed <4.5) {
                    totalDis += currDis;
                    sessionManager.setDistanceDayChecked(sessionManager.getUserId(), totalDis);
                }

              /*  ///////////////테스트
                if (totalDis >= 50 ) { ///////////일정 거리 이상이면 포인트 갱신
                   // PointManager.addPointData(sessionManager.getUserId(), 10,
                     //   2, "50미터 달성", DistanceListenerService.this, TAG);
                  *//*  mNotification = buildNotification("걷기 달성!", "50m 걸었어요! 야호!");
                    mNotification.notify();*//*
                }
*/


                ///거리 측정 메소드

            }else {
                //문제 발생
                Toast.makeText(DistanceListenerService.this, "location null", Toast.LENGTH_SHORT).show();
            }
        }


        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
           // Toast.makeText(DistanceListenerService.this, "거리 측정 안됨", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
            //Toast.makeText(DistanceListenerService.this, "거리 측정 가능", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub

        }

        private Notification buildNotification(String title, String text) {
            Notification notification = new NotificationCompat.Builder(getApplicationContext())
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setContentIntent(PendingIntent.getActivity( DistanceListenerService.this, 0,
                            new Intent( DistanceListenerService.this, MainActivity.class).
                                    addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                    .setAction("NOTIFICATION"),
                            PendingIntent.FLAG_UPDATE_CURRENT))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .build();

            return notification;
        }
    }//locationlistner end
}

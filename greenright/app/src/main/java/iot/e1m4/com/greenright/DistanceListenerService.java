package iot.e1m4.com.greenright;

import android.Manifest;
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
import android.widget.Toast;

import info.addon.SessionManager;
import info.app.AppConfig;

public class DistanceListenerService extends Service {
    public DistanceListenerService() {
    }



    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private LocationManager lm;
    private LocationListener ll;
    private SessionManager sessionManager;

    double mySpeed, maxSpeed;
    Location prevLoc;
    double currDis;
    double totalDis;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sessionManager = new SessionManager(this);
        maxSpeed = mySpeed = 0;
        totalDis = sessionManager.getDistanceDayChecked(AppConfig.DISTANCE_CHECK_DISTANCE);

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        ll = new SpeedoActionListener();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(DistanceListenerService.this, "안됨", Toast.LENGTH_SHORT).show();
        }else {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, ll);
        }
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
            totalDis+=currDis;
            sessionManager.setDistanceDayChecked(AppConfig.DISTANCE_CHECK_DISTANCE, totalDis);
            }else {
                Toast.makeText(DistanceListenerService.this, "location null", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(DistanceListenerService.this, "현재 속도 " + mySpeed
                                                        + "이동 거리 " + totalDis, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
            Toast.makeText(DistanceListenerService.this, "안됨", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
            Toast.makeText(DistanceListenerService.this, "시작", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub

        }
    }


}

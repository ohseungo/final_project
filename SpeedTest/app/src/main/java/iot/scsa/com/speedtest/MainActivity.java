package iot.scsa.com.speedtest;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends Activity {


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        tv= (TextView) findViewById(R.id.textView);


        maxSpeed = mySpeed = 0;
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        ll = new SpeedoActionListener();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "안됨", Toast.LENGTH_SHORT).show();
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, ll);
    }

    private TextView tv;
    private LocationManager lm;
    private LocationListener ll;
    double mySpeed, maxSpeed;
    Location prevLoc;
    double currDis;
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

                tv.setText("\nCurrent Speed : " + mySpeed + " m/s, Max Speed : "
                        + maxSpeed + " m/s"
                        + "curr distance : " + currDis);
            }else {
                Toast.makeText(MainActivity.this, "location null", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(MainActivity.this, "받음", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
            Toast.makeText(MainActivity.this, "안됨", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
            Toast.makeText(MainActivity.this, "시작", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub

        }
    }
}

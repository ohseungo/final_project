package iot.e1m4.com.greenright;

import android.app.Service;
import android.content.Intent;
import android.media.MediaCas;
import android.os.IBinder;
import android.widget.Toast;

import info.addon.SessionManager;
import info.app.AppController;

public class DayResetService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    private SessionManager sessionManager;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sessionManager = new SessionManager(this);
        //Toast.makeText(this, sessionManager.getDistanceDayChecked() + " " +sessionManager.getUserId(), Toast.LENGTH_SHORT).show();

        if (sessionManager.getUserId()!= null) {
            addDistanceData(sessionManager.getUserId(), sessionManager.getDistanceDayChecked());
        }
        sessionManager.dayReset(); //리셋한다
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    private void addDistanceData(String userId, double stepCount){


    }

}

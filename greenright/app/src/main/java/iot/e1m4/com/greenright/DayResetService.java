package iot.e1m4.com.greenright;

import android.app.Service;
import android.content.Intent;
import android.media.MediaCas;
import android.os.IBinder;

import info.addon.SessionManager;
import info.app.AppController;

public class DayResetService extends Service {
    public DayResetService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    private SessionManager sessionManager;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sessionManager = new SessionManager(this);
        sessionManager.dayReset(); //리셋한다
        stopSelf();

        return super.onStartCommand(intent, flags, startId);
    }
}

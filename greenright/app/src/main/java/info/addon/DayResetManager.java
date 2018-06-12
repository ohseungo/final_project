package info.addon;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

import iot.e1m4.com.greenright.DayResetService;

/**
 * 하루 지나는 순간 (23시 59분 59초때) DayResetService 를 불러와 거리 및 다른 데이터를 리셋, DB에 업데이트한다
 */
public class DayResetManager {

    public static void setDayResetAlarm(Context context){

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        PendingIntent pi = PendingIntent.getService(context, Calendar.DATE,
                new Intent(context, DayResetService.class),PendingIntent.FLAG_UPDATE_CURRENT);
        //하루에 한번만 등록하면 됨
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pi);
        Log.e("DayReset", "알람시작");
    }
}

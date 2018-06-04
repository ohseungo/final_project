package info.addon;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaCas;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Map;

import info.app.AppConfig;
import iot.e1m4.com.greenright.MainActivity;

public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF = "greenRight";
    private static final String KEY_LOGGEDIN = "isLoggedIn";
    private static final String KEY_USERID = "userId";
    private static final String KEY_SHOWN = "firstOn";

    private static final String KEY_ALARM_BASE_REQUEST_CODE = "base_request";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF, PRIVATE_MODE);
        editor = pref.edit();
    }

    public SharedPreferences getPref() {
        return pref;
    }

    public void setLogin(boolean isLoggedIn, String userId) {
        if (isLoggedIn) {
            editor.putBoolean(KEY_LOGGEDIN, true);
            editor.putString(KEY_USERID, userId);
        }else {
            editor.putBoolean(KEY_LOGGEDIN, false);
            editor.putString(KEY_USERID, null);
        }
        // commit changes
        editor.commit();

        //  Log.d(TAG, "User login session modified!");
    }

    public void setFirstOn() {
        editor.putBoolean(KEY_SHOWN, true);
        editor.commit();
    }

    public void setDayChecked(String id, boolean check) {
        editor.putBoolean(id, check);
        editor.commit();
    }

    public void setDistanceDayChecked(String userId, double distance) {
        editor.putFloat(userId+AppConfig.DISTANCE_CHECK_DISTANCE, (float) distance);
        editor.commit();
    }


    public void dayReset(){
        String userId = getUserId();
        boolean isLoggedIn = isLoggedIn();
        editor.clear().commit();

        ///////// 데이터 이외의 회원 정보 관련 데이터는 유지한다 ////////
        editor.putBoolean(KEY_SHOWN, true).commit();
        editor.putBoolean(KEY_LOGGEDIN,isLoggedIn).commit();
        editor.putString(KEY_USERID, userId).commit();

        return;
    }


    public int getRequestCode(){
        if (pref.getInt(getUserId(), 0 ) ==0) { //한 아이디에 설정이 안되어있을 경우 부여 한다
            editor.putInt(getUserId(), pref.getInt(KEY_ALARM_BASE_REQUEST_CODE, 0));
            editor.putInt(KEY_ALARM_BASE_REQUEST_CODE, pref.getInt(KEY_ALARM_BASE_REQUEST_CODE,1)+1);
            //값하나씩 부여하면서 +1 저장 (1부터 시작해서 1,2,3....)

        }
        editor.commit();
        return pref.getInt(getUserId(), 0);

    }


    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_LOGGEDIN, false);
    }
    public String getUserId(){
        return pref.getString(KEY_USERID, null);
    }
    public boolean isFirstOn() {return pref.getBoolean(KEY_SHOWN, false);}
    public boolean isDayChecked(String id) {return pref.getBoolean(id, false);}
    public double getDistanceDayChecked(String userId) {
        return (double) pref.getFloat(userId + AppConfig.DISTANCE_CHECK_DISTANCE, 0);}
}

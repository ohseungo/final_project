package info.addon;

import android.content.Context;
import android.content.SharedPreferences;

import info.app.AppConfig;

public class SessionManager {

    private static String TAG = SessionManager.class.getSimpleName();

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    Context mContext;

    private final int PRIVATE_MODE = 0;

    private static final String PREF = "greenRight";
    private static final String KEY_LOGGEDIN = "isLoggedIn";
    private static final String KEY_USERID = "userId";
    private static final String KEY_SHOWN = "firstOn";

    private static final String KEY_ALARM_BASE_REQUEST_CODE = "base_request";

    public SessionManager(Context context) {
        this.mContext = context;
        pref = mContext.getSharedPreferences(PREF, PRIVATE_MODE);
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
        editor.commit();
    }
    /*
      설치후 한번 켜지면 무조건 true
     */
    public void setFirstOn() {
        editor.putBoolean(KEY_SHOWN, true);
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


    public void setBeaconChecked(String beaconKey) {
        editor.putBoolean(beaconKey, true).commit();
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_LOGGEDIN, false);
    }
    public String getUserId(){
        return pref.getString(KEY_USERID, null);
    }
    public boolean isFirstOn() {return pref.getBoolean(KEY_SHOWN, false);}
    public double getDistanceDayChecked(String userId) {
        double result = (int) (pref.getFloat(userId + AppConfig.DISTANCE_CHECK_DISTANCE, 0)/100);
        return result/10;
    }
    public boolean isBeaconChecked(String beaconKey) {return pref.getBoolean(beaconKey, false);}
}

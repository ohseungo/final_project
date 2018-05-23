package info.addon;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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
    private static final String PREF_LOGIN = "greenRightLogin";

    private static final String KEY_LOGGEDIN = "isLoggedIn";

    private static final String KEY_USERID = "userId";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_LOGIN, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn,String userId) {
        if (isLoggedIn) {
            editor.putBoolean(KEY_LOGGEDIN, true);
            editor.putString(KEY_USERID, userId);
        }else {
            editor.clear();
        }
        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_LOGGEDIN, false);
    }

    public String getUserId(){
        return pref.getString(KEY_USERID, null);
    }
}

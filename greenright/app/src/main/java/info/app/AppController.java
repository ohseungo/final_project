package info.app;

import android.app.Application;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.estimote.proximity_sdk.proximity.EstimoteCloudCredentials;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import info.addon.SessionManager;
import iot.e1m4.com.greenright.MainActivity;

public class AppController extends Application{

    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private static AppController mInstance;

    private static boolean activityVisible;
    private EstimoteCloudCredentials cloudCredentials;
    private SessionManager mSessionManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mSessionManager = new SessionManager(getApplicationContext());
        cloudCredentials=
                new EstimoteCloudCredentials("greenright-4ib", "cb3db31dc517d40658e80e5ee3170f24");
    }

    public EstimoteCloudCredentials getCloudCredentials() {
        return cloudCredentials;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }



}

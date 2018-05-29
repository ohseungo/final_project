package info.app;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.estimote.proximity_sdk.proximity.EstimoteCloudCredentials;

public class AppController extends Application{

    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private static AppController mInstance;


    private EstimoteCloudCredentials cloudCredentials;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
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
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}

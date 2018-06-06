package iot.e1m4.com.greenright;

import android.app.Service;
import android.content.Intent;
import android.media.MediaCas;
import android.os.IBinder;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import info.addon.PointManager;
import info.addon.SessionManager;
import info.app.AppConfig;
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
        /*
         이미 거리 저장된 아이디 전부 저장
         */
        for(String key: sessionManager.getPref().getAll().keySet()){
            Toast.makeText(DayResetService.this, key + " " + sessionManager.getPref().getAll().get(key), Toast.LENGTH_SHORT).show();
            if (key.contains(AppConfig.DISTANCE_CHECK_DISTANCE)) {

                addDistanceData(key.replace(AppConfig.DISTANCE_CHECK_DISTANCE, ""),
                         sessionManager.getPref().getAll().get(key));
            }
            }

        sessionManager.dayReset(); //리셋한다
        stopSelf();
        return super.onStartCommand(intent, flags, startId);

    }

    ///거리 데이터 db에 올리기
    private void addDistanceData(final String userId, final Object walkDistance){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.ADD_WALK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(DayResetService.this, response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DayResetService.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId", userId);
                params.put("walkDistance", String.valueOf(walkDistance));
                return params;
            }
        };

        AppController.getInstance().
                addToRequestQueue(stringRequest);

    }

}

package info.addon;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import info.app.AppConfig;
import info.app.AppController;

public class PointManager {

    private static String TAG = PointManager.class.getSimpleName();
    private static PointManager mInstance;
    public static PointManager getInstance() {
        if (mInstance == null ){
            mInstance = new PointManager();
        }
        return mInstance;
    }

    public static boolean addPointData(final String userId, final int pointValue,
                                       final int pointType, final String pointContent, final Context context, String tag) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.ADD_POINT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("true")){
                           Log.e(TAG, "포인트 성공");
                        }else {
                            Log.e(TAG,"포인트 이력 추가 실패" );
                        }
                        return;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ///////////////////실패/////////////////////////////////
                        Log.e(TAG,"포인트 이력 추가 실패" );

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                     params.put("userId", userId);
                     params.put("greenPointValue", String.valueOf(pointValue));
                     params.put("greenPointType", String.valueOf(pointType));
                    params.put("greenPointContent",pointContent);

                return params;
            }
        };
        stringRequest.setTag(tag);
        AppController.getInstance().
                addToRequestQueue(stringRequest);
        return true;
    }


}

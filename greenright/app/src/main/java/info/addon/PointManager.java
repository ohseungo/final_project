package info.addon;

import android.content.Context;
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

    private PointManager() {
    }

    private static PointManager mInstance;

    public static PointManager getInstance() {
        if (mInstance == null ){
            mInstance = new PointManager();
        }
        return mInstance;
    }

    public static boolean addPointData(final String userId, final int pointValue,
                                       final int pointType, final String pointContent, final Context context) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.ADD_POINT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("true")){
                            Toast.makeText(context, "성공", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "실패", Toast.LENGTH_SHORT).show();
                        }
                        return;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ///////////////////실패/////////////////////////////////
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId", userId);
                params.put("greenPointValue", String.valueOf(pointValue));
                params.put("greenPointType", String.valueOf(pointType));
                try {
                    params.put("greenPointContent", URLEncoder.encode(pointContent, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return params;
            }
        };
        AppController.getInstance().
                addToRequestQueue(stringRequest);
        return true;
    }


}

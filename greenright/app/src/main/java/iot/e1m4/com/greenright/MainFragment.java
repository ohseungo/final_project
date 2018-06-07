package iot.e1m4.com.greenright;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import info.addon.SessionManager;
import info.app.AppConfig;
import info.app.AppController;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    SessionManager sessionManager;
    TextView userHead;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_main, container, false);
        // Inflate the layout for this fragment
        sessionManager = new SessionManager(getActivity());
        userHead = layout.findViewById(R.id.mainUser);
        userUpdate();

        return layout;
    }

    private void userUpdate() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.FIND_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //////////////성공//////////////////////
                        try {
                            Toast.makeText(getActivity(), "성공", Toast.LENGTH_SHORT).show();
                            userHead.setText(URLDecoder.decode(new JSONObject(response).getString("userName")
                                    , "UTF-8"));
                            return;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ///////////////////실패/////////////////////////////////
                        Toast.makeText(getActivity(), "실패", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId", sessionManager.getUserId());
                return params;
            }
        };
        AppController.getInstance().
                addToRequestQueue(stringRequest);
        return;
        View layout=inflater.inflate(R.layout.fragment_main, container, false);
        airBtn=layout.findViewById(R.id.airBtn);
        airBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stationName="역삼동";
                getWeather(stationName);

            }
        });
        return layout;
    }

    public static  void getWeather(String name){

>>>>>>> origin/feature/new_view
    }

}

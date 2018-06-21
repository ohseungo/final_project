package iot.e1m4.com.greenright;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import info.addon.SessionManager;
import info.app.AppConfig;
import info.app.AppController;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();
    private ProgressDialog pDialog;
    TextView mTotalPoint;
    SessionManager sessionManager;
    TextView userHead;
    TextView totalCup;

    ImageView news1;
    ImageView news2;
    ImageView news3;

    private static Typeface typeface;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_home, container, false);

        if(typeface == null) {
            typeface = Typeface.createFromAsset(getActivity().getAssets(),
                    "fonts/nanumsquareB.otf");
        }
        setGlobalFont(layout);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        sessionManager = new SessionManager(getActivity());
        //sessionManager.setDistanceDayChecked(sessionManager.getUserId(), 2320);
        mTotalPoint = layout.findViewById(R.id.pointTv);
        getTotalPoint(sessionManager.getUserId());
        userHead = layout.findViewById(R.id.mainUser);
        totalCup = layout.findViewById(R.id.totalCup);
        userUpdate();
        getTotalDisp(sessionManager.getUserId());
        news1 = layout.findViewById(R.id.news1);
        news2 = layout.findViewById(R.id.news2);
        news3 = layout.findViewById(R.id.news3);
        marketListUpdate();

        return layout;
    }

    private void getTotalDisp(final String userId) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.VIEW_POINT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject object;
                            int count = 0;
                            for (int i =0; i<jsonArray.length(); i++) {
                                object = jsonArray.getJSONObject(i);
                                if (object.getString("greenPointType").equals("1")) count++;
                            }
                            totalCup.setText(String.valueOf(count));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId", userId);
                return params;
            }


        };
        stringRequest.setTag(TAG);
        AppController.getInstance().
                addToRequestQueue(stringRequest);

    }

    private void getTotalPoint(final String userId) {
        pDialog.setMessage("포인트 정보를 받아오는 중...");
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.TOTAL_POINT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideDialog();
                        if (response ==null || response.equals("")) response = "0";
                        mTotalPoint.setText("P " + response);
                        return;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId", userId);
                return params;
            }
        };
        stringRequest.setTag(TAG);
        AppController.getInstance().
                addToRequestQueue(stringRequest);
    }


    private void userUpdate() {
        pDialog.setMessage("회원 정보를 받아오는 중...");
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.FIND_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //////////////성공//////////////////////
                        hideDialog();
                        try {
                            userHead.setText(new JSONObject(response).getString("userName"));
                            return;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ///////////////////실패/////////////////////////////////
                        hideDialog();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId", sessionManager.getUserId());
                return params;
            }
        };
        stringRequest.setTag(TAG);
        AppController.getInstance().
                addToRequestQueue(stringRequest);
        return;
    }

    private MarketTask mTask;
    private void marketListUpdate() {
        pDialog.setMessage("상품 정보를 받아오는 중...");
        showDialog();
        StringRequest  stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_PRODUCT_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            mTask = new MarketTask();
                            mTask.execute(jsonArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ///리스트 가져오기 실패

            }
        });
        stringRequest.setTag(TAG);
        AppController.getInstance().
                addToRequestQueue(stringRequest);
    }


    class MarketTask extends AsyncTask<JSONArray, Object, Void> {
        Bitmap[] bm = new Bitmap[3];

        @Override
        protected Void doInBackground(JSONArray... jsonArrays) {
            try {
                JSONArray jArray = jsonArrays[0];

                for (int i=0; i<3; i++){
                    if (i+1 > jArray.length()) {
                        bm[i] = null;
                        continue;
                    }
                    if (jArray.getJSONObject(i).getString("productImage") == null ||
                            jArray.getJSONObject(i).getString("productImage").equals("null") ||
                            jArray.getJSONObject(i).getString("productImage").trim().equals("")) {
                        bm[i] = null ;
                    }
                    else {

                        try (InputStream is =new URL(AppConfig.REQUEST_URL + "/images/product/"
                                + jArray.getJSONObject(i).getString("productImage")).openStream()) {
                            bm[i] = BitmapFactory.decodeStream(is);
                        }
                    }
                    publishProgress(bm);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Object... objects) {
            super.onProgressUpdate(objects);
            news1.setImageBitmap(bm[0]);
            news2.setImageBitmap(bm[1]);
            news3.setImageBitmap(bm[2]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            hideDialog();
        }
    }



    @Override
    public void onStop() {
        super.onStop();
        AppController.getInstance().cancelPendingRequests(TAG);
    }

    private void setGlobalFont(View view) {
        if(view != null) {
            if(view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup)view;
                int vgCnt = viewGroup.getChildCount();
                for(int i = 0; i<vgCnt; i++) {
                    View v = viewGroup.getChildAt(i);
                    if(v instanceof TextView) {
                        ((TextView) v).setTypeface(typeface);
                    }
                    setGlobalFont(v);
                }
            }
        }
    }

    private void showDialog() {
        if (!pDialog.isShowing()){
            pDialog.show();
        }
    }


    private void hideDialog() {
        if (pDialog.isShowing()){
            pDialog.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideDialog();
    }


}

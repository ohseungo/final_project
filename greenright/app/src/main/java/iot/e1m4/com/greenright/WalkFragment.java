package iot.e1m4.com.greenright;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
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
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.roughike.bottombar.BottomBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.addon.SessionManager;
import info.app.AppConfig;
import info.app.AppController;


public class WalkFragment extends Fragment implements MainActivity.onKeyBackPressedListener {

    private LineChart lineChart;
    private SessionManager sessionManager;
    private final String TAG = getClass().getSimpleName();
    private static Typeface typeface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(getActivity());

    }
    private TextView currDis;
    private TextView goalDis;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View layout=inflater.inflate(R.layout.fragment_walk, container, false);

        if(typeface == null) {
            typeface = Typeface.createFromAsset(getActivity().getAssets(),
                    "fonts/yoon350.ttf");
        }
        setGlobalFont(layout);

        currDis = layout.findViewById(R.id.currTv);
        goalDis = layout.findViewById(R.id.goalTv);
        currDis.setText(  sessionManager.getDistance(sessionManager.getUserId()) +
                "km");
        goalDis.setText(5 + "km");
        getLineData(layout, sessionManager.getUserId());


        return layout;
    }

    private void getLineData(final View layout, final String userId) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.VIEW_WALK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        getLineChart(response, layout);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


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


    private void getLineChart(String response, View layout) {
        List<Entry> entries=new ArrayList<>();
        lineChart=layout.findViewById(R.id.chart);

            try {
                JSONArray jArray = new JSONArray(response);
                for (int i = 0; i < jArray.length(); i++) {
                /*Toast.makeText(getActivity(),(float) jArray.getJSONObject(i).getDouble("walkDistance") + ""
                        , Toast.LENGTH_SHORT).show();*/
                    entries.add(new Entry(i , (float) jArray.getJSONObject(i).getDouble("walkDistance")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        entries.add(new Entry(6, (int) sessionManager.getDistance(sessionManager.getUserId())));
        LineDataSet lineDataSet=new LineDataSet(entries,"이동거리");
        lineDataSet.setLineWidth(2);
        lineDataSet.setCircleRadius(6);
        lineDataSet.setCircleColor(Color.parseColor("#ff5778"));
        lineDataSet.setColor(Color.parseColor("#ff5778"));
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setDrawHighlightIndicators(false);
        lineDataSet.setDrawValues(false);


        ArrayList<String> labels=new ArrayList<>();
        labels.add("6일 전");
        labels.add("5일 전");
        labels.add("4일 전");
        labels.add("3일 전");
        labels.add("2일 전");
        labels.add("1일 전");


        LineData lineData=new LineData(lineDataSet);
        lineChart.setData(lineData);

        //x축 y축 설정
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(6);

        xAxis.setValueFormatter(new MyXAxisValueFormatter(labels));

        YAxis yLAxis = lineChart.getAxisLeft();
        yLAxis.setTextColor(Color.BLACK);

        yLAxis.setAxisMinimum(0);
        yLAxis.setAxisMaximum(10);
        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);

        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.animateY(2, Easing.EasingOption.EaseInCubic);
        lineChart.animateX(1);
        lineChart.invalidate();
    }



    public class MyXAxisValueFormatter implements IAxisValueFormatter {

        private List mValues;

        public MyXAxisValueFormatter(List<String> values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            // "value" represents the position of the label on the axis (x or y)
            try{
                int index=(int) value;
                return (String) mValues.get(index);
            } catch(Exception e){
                    return "";
            }

        }

        public int getDecimalDigits() { return 0; }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity)context).setmOnKeyBackPressedListener(this);
    }

    @Override
    public void onBack() {
        BottomBar bottomBar = getActivity().findViewById(R.id.bottomBar);
        bottomBar.selectTabAtPosition(0);

    }

    @Override
    public void onStop() {
        super.onStop();
        AppController.getInstance().cancelPendingRequests(TAG);
    }

    private void setGlobalFont(View view) {
        if (view != null) {
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                int vgCnt = viewGroup.getChildCount();
                for (int i = 0; i < vgCnt; i++) {
                    View v = viewGroup.getChildAt(i);
                    if (v instanceof TextView) {
                        ((TextView) v).setTypeface(typeface);
                    }
                    setGlobalFont(v);
                }
            }
        }
    }
}

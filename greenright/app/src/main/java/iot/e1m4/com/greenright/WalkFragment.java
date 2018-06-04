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


public class WalkFragment extends Fragment {

    private LineChart lineChart;
    private SessionManager sessionManager;
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

        currDis = layout.findViewById(R.id.currTv);
        goalDis = layout.findViewById(R.id.goalTv);
        currDis.setText(sessionManager.getDistanceDayChecked(sessionManager.getUserId()) + "km");
        goalDis.setText(10 + "km");
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

        AppController.getInstance().
                addToRequestQueue(stringRequest);
    }


    private void getLineChart(String response, View layout) {

        lineChart=layout.findViewById(R.id.chart);
        //데이터 넣기 걷기..데이터
        List<Entry> entries=new ArrayList<>();
        try {
            JSONArray jArray = new JSONArray(response);
            for (int i =0; i<jArray.length(); i++){
                entries.add(new Entry(i+1, (float) jArray.getJSONObject(i).getDouble("walkDistance")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        LineDataSet lineDataSet=new LineDataSet(entries,"이동거리");
        lineDataSet.setLineWidth(2);
        lineDataSet.setCircleRadius(6);
        lineDataSet.setCircleColor(Color.parseColor("#FFfcd868"));
        lineDataSet.setColor(Color.parseColor("#FFfcd868"));
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
        yLAxis.setAxisMaximum(200);
        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);

        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.animateY(40, Easing.EasingOption.EaseInCubic);
        lineChart.animateX(1);
        lineChart.invalidate();
    }

    private List<Entry> getLineList(final String userId){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.VIEW_POINT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject object;

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

        AppController.getInstance().
                addToRequestQueue(stringRequest);
        return null;
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
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
}

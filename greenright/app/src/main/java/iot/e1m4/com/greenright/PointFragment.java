package iot.e1m4.com.greenright;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.roughike.bottombar.BottomBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.addon.SessionManager;
import info.app.AppConfig;
import info.app.AppController;


/**
 * A simple {@link Fragment} subclass.
 */
public class PointFragment extends Fragment implements MainActivity.onKeyBackPressedListener{


    private final String TAG = getClass().getSimpleName();

    PieChart pieChart;

    android.widget.ListView mListView=null;
    ListViewAdapter mAdapter=null;
    private static Typeface typeface;

    SessionManager mSessionManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout=inflater.inflate(R.layout.fragment_point, container, false);

        if(typeface == null) {
            typeface = Typeface.createFromAsset(getActivity().getAssets(),
                    "fonts/yoon350.ttf");
        }
        setGlobalFont(layout);
        pieChart = layout.findViewById(R.id.piechart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        mSessionManager = new SessionManager(getActivity());

        makePieEntry(mSessionManager.getUserId());

/////////////////////////////////////////
        //list view 관련
        mListView=layout.findViewById(R.id.mList);
        mAdapter=new ListViewAdapter(getActivity());
        mListView.setAdapter(mAdapter);
////////////////////////////////////////////////////////////////////////////
        //list view 아이템 추가는 이곳에서
        getPointList(mSessionManager.getUserId());


        return layout;
    }

    private String[] pointType = {null, "일회용 수거", "걷기", "대중교통 이용", "환경 프로모션"};
    private void makePieEntry(final String userId) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.VIEW_POINT_BY_TYPE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject object;
                            ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

                            for (int i =0; i<jsonArray.length(); i++) {
                                object = jsonArray.getJSONObject(i);
                                if (Integer.parseInt(object.getString("greenPointType")) <0) continue;
                                yValues.add(new PieEntry(Integer.parseInt(object.getString("greenPointValue")),
                                       pointType[Integer.parseInt(object.getString("greenPointType"))]));
                            }

                            com.github.mikephil.charting.components.Legend l = pieChart.getLegend();
                            l.setVerticalAlignment(com.github.mikephil.charting.components.Legend.LegendVerticalAlignment.TOP);
                            l.setHorizontalAlignment(com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.RIGHT);
                            l.setOrientation(com.github.mikephil.charting.components.Legend.LegendOrientation.VERTICAL);
                            l.setDrawInside(false);
                            l.setXEntrySpace(7f);
                            l.setYEntrySpace(0f);
                            l.setYOffset(0f);

                            // entry label styling
                            pieChart.setEntryLabelColor(Color.WHITE);

                            pieChart.setEntryLabelTextSize(12f);


                            pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic); //애니메이션

                            PieDataSet dataSet = new PieDataSet(yValues,"적립 내역");
                            dataSet.setSliceSpace(3f);
                            dataSet.setSelectionShift(5f);
                            dataSet.setColors(ColorTemplate.PASTEL_COLORS);

                            PieData data = new PieData((dataSet));
                            data.setValueTextSize(10f);
                            data.setValueTextColor(Color.BLACK);

                            pieChart.setData(data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "에러!", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "에러어", Toast.LENGTH_SHORT).show();

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
        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();



    }

    private List<JSONObject> mPointList;
    private Drawable img;
    private void getPointList(final String userId) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.VIEW_POINT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject object;


                            L: for (int i =0; i<jsonArray.length(); i++) {
                                object = jsonArray.getJSONObject(i);
                                try {

                                    switch(object.getInt("greenPointType")){
                                        case 1:
                                            img=getResources().getDrawable(R.drawable.ic_recycle);
                                            break;
                                        case 2:
                                            img=getResources().getDrawable(R.drawable.ic_walking);
                                            break;
                                        case 3:
                                            img=getResources().getDrawable(R.drawable.ic_bus);
                                            break;
                                        case 4:
                                            img=getResources().getDrawable(R.drawable.ic_video);
                                            break;
                                        case 5:
                                            img=getResources().getDrawable(R.drawable.ic_tumbler);
                                            break;
                                            default:
                                                img=getResources().getDrawable(R.drawable.ic_star);
                                                continue L;

                                    }
                                    mAdapter.addItem(img,
                                            URLDecoder.decode(object.getString("greenPointContent"),"UTF-8") ,
                                            object.getString("greenPointDate") ,
                                            object.getString("greenPointValue") + " point");
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }
                            mAdapter.notifyDataSetChanged();
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

    private class ViewHolder{
        public android.widget.ImageView mIcon;
        public android.widget.TextView mText;
        public android.widget.TextView mDate;
        public android.widget.TextView mPoint;
}

    private class ListViewAdapter extends BaseAdapter {
    private Context mContext = null;
    private ArrayList<ListData> mListData = new ArrayList<ListData>();

    public ListViewAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){


            holder=new ViewHolder();

            LayoutInflater inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.listview_item, null);

            holder.mIcon=convertView.findViewById(R.id.mImage);
            holder.mText=convertView.findViewById(R.id.mText);
            holder.mDate=convertView.findViewById(R.id.mDate);
            holder.mPoint=convertView.findViewById(R.id.mPoint);

            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        ListData mData=mListData.get(position);

        if(mData.mIcon!=null){
            holder.mIcon.setVisibility(View.VISIBLE);
            holder.mIcon.setImageDrawable(mData.mIcon);
        }else{
            holder.mIcon.setVisibility(View.GONE);
        }
        holder.mText.setText(mData.mTilte);
        holder.mDate.setText(mData.mDate);
        holder.mPoint.setText(mData.mPoint);

        return convertView;
    }

        public void addItem(Drawable icon, String mTitle, String mDate, String mPoint){
            ListData addInfo = null;
            addInfo = new ListData();
            addInfo.mIcon = icon;
            addInfo.mDate = mDate;
            addInfo.mTilte=mTitle;
            addInfo.mPoint=mPoint;

            mListData.add(addInfo);
        }

        public void remove(int position){
            mListData.remove(position);
            dataChange();
        }
        public void dataChange(){
            mAdapter.notifyDataSetChanged();
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


}

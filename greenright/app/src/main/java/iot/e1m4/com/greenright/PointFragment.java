package iot.e1m4.com.greenright;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class PointFragment extends Fragment {


    public PointFragment() {
        // Required empty public constructor
    }

    PieChart pieChart;

    android.widget.ListView mListView=null;
    ListViewAdapter mAdapter=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout=inflater.inflate(R.layout.fragment_point, container, false);

        pieChart = layout.findViewById(R.id.piechart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();
/////////////////////////////////
        yValues.add(new PieEntry(34f,"컵수거"));
        yValues.add(new PieEntry(23f,"걷기"));
        yValues.add(new PieEntry(14f,"대중교통"));
        yValues.add(new PieEntry(35f,"그린영상"));
        yValues.add(new PieEntry(40f,"텀블러"));


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
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);

        //list view 관련
        mListView=layout.findViewById(R.id.mList);
        mAdapter=new ListViewAdapter(getActivity());
        mListView.setAdapter(mAdapter);
////////////////////////////////////////////////////////////////////////////
        //list view 아이템 추가는 이곳에서
        mAdapter.addItem(getResources().getDrawable(R.drawable.ic_star),"컵 수거함 적립 완료","2018-05-31","50point");
        mAdapter.addItem(getResources().getDrawable(R.drawable.ic_star),"컵 수거함 적립 완료","2018-05-31","50point");
        mAdapter.addItem(getResources().getDrawable(R.drawable.ic_star),"컵 수거함 적립 완료","2018-05-31","50point");
        mAdapter.addItem(getResources().getDrawable(R.drawable.ic_star),"컵 수거함 적립 완료","2018-05-31","50point");
        mAdapter.addItem(getResources().getDrawable(R.drawable.ic_star),"컵 수거함 적립 완료","2018-05-31","50point");
        mAdapter.addItem(getResources().getDrawable(R.drawable.ic_star),"컵 수거함 적립 완료","2018-05-31","50point");
        mAdapter.addItem(getResources().getDrawable(R.drawable.ic_star),"컵 수거함 적립 완료","2018-05-31","50point");
        mAdapter.addItem(getResources().getDrawable(R.drawable.ic_star),"컵 수거함 적립 완료","2018-05-31","50point");
        mAdapter.addItem(getResources().getDrawable(R.drawable.ic_star),"컵 수거함 적립 완료","2018-05-31","50point");



        return layout;
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
        holder.mDate.setText(mData.mDate);

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


}

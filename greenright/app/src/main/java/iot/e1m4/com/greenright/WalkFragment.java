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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WalkFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WalkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WalkFragment extends Fragment {

    private LineChart lineChart;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View layout=inflater.inflate(R.layout.fragment_walk, container, false);

        lineChart=layout.findViewById(R.id.chart);

        //데이터 넣기 걷기..데이터
        List<Entry> entries=new ArrayList<>();
        entries.add(new Entry(1, 1000));
        entries.add(new Entry(2, 2500));
        entries.add(new Entry(3, 1300));
        entries.add(new Entry(4, 4200));
        entries.add(new Entry(5, 7500));

        LineDataSet lineDataSet=new LineDataSet(entries,"걸음 수");
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
        yLAxis.setAxisMaximum(10000);
        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);

        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.animateY(2000, Easing.EasingOption.EaseInCubic);
        lineChart.animateX(1);
        lineChart.invalidate();
        return layout;
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

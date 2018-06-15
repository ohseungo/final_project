package iot.e1m4.com.greenright;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.roughike.bottombar.BottomBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import info.addon.SessionManager;
import info.app.AppConfig;
import info.app.AppController;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentPointFragment extends Fragment implements MainActivity.onKeyBackPressedListener {
    private final String TAG = getClass().getSimpleName();

    Button mPointSaveBtn;
    Button mMarketBtn;
    ListView mListView=null;
    ListViewAdapter mAdapter=null;
    TextView mCurrPoint;
    SessionManager sessionManager;
    private static Typeface typeface;
    ProgressDialog pDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.fragment_current_point, container, false);

        if(typeface == null) {
            typeface = Typeface.createFromAsset(getActivity().getAssets(),
                    "fonts/nanumsquareB.otf");
        }
        setGlobalFont(layout);

        sessionManager = new SessionManager(getActivity());
        pDialog = new ProgressDialog(getActivity());
        mCurrPoint = layout.findViewById(R.id.currPoint);


        //포인트 사용 내역 리스트
        mListView=layout.findViewById(R.id.pointUseList);
        mAdapter=new ListViewAdapter(getActivity());
        mListView.setAdapter(mAdapter);

        getPointList(sessionManager.getUserId());



        //버튼 이동
        mPointSaveBtn=layout.findViewById(R.id.pointSaveBtn);
        mPointSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.contentContainer,new PointFragment()).commit();
            }
        });

        mMarketBtn=layout.findViewById(R.id.shopBtn);
        mMarketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.contentContainer, new MarketFragment()).commit();
            }
        });
        return layout;
    }


    private void getPointList(final String userId) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.VIEW_POINT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject object;
                            int total = 0;
                            for (int i =0; i<jsonArray.length(); i++) {
                                object = jsonArray.getJSONObject(i);
                                total += Integer.parseInt(object.getString("greenPointValue"));
                                if(Integer.parseInt(object.getString("greenPointType"))>0) continue;
                                mAdapter.addItem(object.getString("greenPointContent") ,
                                        (Integer.parseInt(object.getString("greenPointValue")) *(-1)) + " point",
                                            object.getString("greenPointDate")
                                            );
                            }
                            mCurrPoint.setText("P " +total);
                            mAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "포인트 갱신 실패");

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
        public TextView mUseTitle;
        public TextView mUsePoint;
        public  TextView mUseDay;
    }

    public class ListViewAdapter extends BaseAdapter{

        private Context mContext=null;
        private ArrayList<UsePoint> mListData=new ArrayList<UsePoint>();

        public ListViewAdapter(Context mContext){
            super();
            this.mContext=mContext;
        }

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int i) {
            return mListData.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;

            if(view==null){
                holder=new ViewHolder();
                LayoutInflater inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view=inflater.inflate(R.layout.pointuselist, null);

                holder.mUsePoint=view.findViewById(R.id.usePoint);
                holder.mUseTitle=view.findViewById(R.id.useTitle);
                holder.mUseDay=view.findViewById(R.id.useDay);

                view.setTag(holder);
            }else{
                holder= (ViewHolder) view.getTag();
            }

            UsePoint mData=mListData.get(i);

            holder.mUseTitle.setText(mData.getUseTitle());
            holder.mUsePoint.setText(mData.getUsePoint());
            holder.mUseDay.setText(mData.getUseDay());
            return view;
        }
        public void addItem(String mUseTitle,String mUsePoint, String mUseDay){
            UsePoint addInfo = null;
            addInfo = new UsePoint();

            addInfo.setUseTitle(mUseTitle);
            addInfo.setUsePoint(mUsePoint);
            addInfo.setUseDay(mUseDay);
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
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity)context).setmOnKeyBackPressedListener(this);
    }

    @Override
    public void onBack() {
        getFragmentManager().beginTransaction().replace(R.id.contentContainer,new MypageFragment()).commit();
        return;
    }

    @Override
    public void onStop() {
        super.onStop();
        AppController.getInstance().cancelPendingRequests(TAG);
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
}

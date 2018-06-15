package iot.e1m4.com.greenright;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import info.addon.SessionManager;
import info.app.AppConfig;
import info.app.AppController;


public class CurrentOrderFragment extends Fragment implements MainActivity.onKeyBackPressedListener{


    ListView mListView=null;
    ListViewAdapter mAdapter=null;
    private static Typeface typeface;
    private SessionManager sessionManager;

    private final String TAG = getClass().getSimpleName();
    private String[] purchaseStatus = {"배송완료", "주문확인", "입금확인", "배송중"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.fragment_current_order, container, false);
        mListView=layout.findViewById(R.id.orderList);
        mAdapter=new ListViewAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        sessionManager = new SessionManager(getActivity());

        getOrderList(sessionManager.getUserId());
        return layout;
    }


    private void getOrderList(final String userId) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_ORDER_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject object;
                            for (int i =0; i<jsonArray.length(); i++) {
                                object = jsonArray.getJSONObject(i);
                                mAdapter.addItem(object.getString("purchaseDate"),
                                        "(총 "+ object.getString("purchaseCount")+ "개 품목)",
                                        object.getString("productName"),
                                        object.getString("purchaseId"),
                                        object.getString("productValue"),
                                        purchaseStatus[Integer.parseInt(object.getString("purchaseStatus"))]
                                );

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
                        Log.e(TAG, "주문 목록 실패");
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
        public TextView mOrderDate;
        public TextView mOrderNum;
        public TextView mOrderItem;
        public TextView mOrderCnt;
        public TextView mOrderPrice;
        public TextView mOrderState;
    }

    public class ListViewAdapter extends BaseAdapter {

        private Context mContext=null;
        private ArrayList<Order> mListData=new ArrayList<Order>();

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
                view=inflater.inflate(R.layout.orderlist, null);

               holder.mOrderDate=view.findViewById(R.id.orderDate);
               holder.mOrderCnt=view.findViewById(R.id.orderCnt);
               holder.mOrderItem=view.findViewById(R.id.orderItem);
               holder.mOrderNum=view.findViewById(R.id.orderNum);
               holder.mOrderPrice=view.findViewById(R.id.orderPrice);
               holder.mOrderState=view.findViewById(R.id.orderState);

                view.setTag(holder);
            }else{
                holder= (ViewHolder) view.getTag();
            }

            Order mData=mListData.get(i);

            holder.mOrderDate.setText(mData.getOrderDate());
            holder.mOrderCnt.setText(mData.getOrderCnt());
            holder.mOrderItem.setText(mData.getOrderItem());
            holder.mOrderNum.setText(mData.getOrderNum());
            holder.mOrderPrice.setText(mData.getOrderPrice());
            holder.mOrderState.setText(mData.getOrderState());

            return view;
        }
        public void addItem(String mOrderDate, String mOrderCnt, String mOdrderItem, String mOrderNum, String mOrderPrice, String mOrderState){
            Order addInfo = null;
            addInfo = new Order();

            addInfo.setOrderDate(mOrderDate);
            addInfo.setOrderCnt(mOrderCnt);
            addInfo.setOrderItem(mOdrderItem);
            addInfo.setOrderNum(mOrderNum);
            addInfo.setOrderPrice(mOrderPrice);
            addInfo.setOrderState(mOrderState);
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

}

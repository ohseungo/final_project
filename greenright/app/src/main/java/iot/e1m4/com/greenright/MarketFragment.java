package iot.e1m4.com.greenright;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.roughike.bottombar.BottomBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import info.app.AppConfig;
import info.app.AppController;


/**
 * A simple {@link Fragment} subclass.
 */
public class MarketFragment extends Fragment implements MainActivity.onKeyBackPressedListener {

    private final String TAG = getClass().getSimpleName();

    private ListView mListView = null;
    private ListViewAdapter mAdapter = null;
    private static Typeface typeface;

    private ProgressDialog pDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) context).setmOnKeyBackPressedListener(this);
    }

    @Override
    public void onBack() {
        BottomBar bottomBar = getActivity().findViewById(R.id.bottomBar);
        bottomBar.selectTabAtPosition(0);

    }

    private OrderFragment mOrderFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_market, container, false);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        if (typeface == null) {
            typeface = Typeface.createFromAsset(getActivity().getAssets(),
                    "fonts/yoon350.ttf");
        }
        setGlobalFont(layout);
        mListView = layout.findViewById(R.id.productList);

        mAdapter = new ListViewAdapter(getActivity());
        mListView.setAdapter(mAdapter);

        //데이터 입력은 이곳에서~~~~
        marketListUpdate();

        //주문 화면으로 이동
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Product mData = mAdapter.mListData.get(position);

                mOrderFragment = new OrderFragment();
                Bundle bundle = new Bundle();
                PaymentInfo paymentInfo = new PaymentInfo();

                paymentInfo.setProductId(mData.getpId());
                paymentInfo.setProductName(mData.getpName1());
                paymentInfo.setProductValue(mData.getPrice1());
                paymentInfo.setCompId(mData.getpCompanyId());
                //bundle.putString("productId", mData.getpId());
                bundle.putParcelable("PaymentInfo", paymentInfo);
                mOrderFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.contentContainer, mOrderFragment, "ORDER_PAGE").commit();

            }
        });

        return layout;
    }


    class MarketTask extends AsyncTask<JSONArray, Object, Void> {
        Bitmap bm;

        @Override
        protected Void doInBackground(JSONArray... jsonArrays) {
            try {
                JSONArray jArray = jsonArrays[0];
                for (int i = 0; i < jArray.length(); i++) {
                    if (jArray.getJSONObject(i).getString("productImage") == null ||
                            jArray.getJSONObject(i).getString("productImage").equals("null") ||
                            jArray.getJSONObject(i).getString("productImage").trim().equals(""))
                        bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_shopping_bag);
                    else {
                        try (InputStream is = new URL(AppConfig.REQUEST_URL + "/images/product/"
                                + jArray.getJSONObject(i).getString("productImage")).openStream()) {
                            bm = BitmapFactory.decodeStream(is);
                        }
                    }
                    publishProgress(jsonArrays[0].getJSONObject(i), bm);
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
            super.onProgressUpdate(objects[0]);
            JSONObject object = (JSONObject) objects[0];
            Bitmap bm = (Bitmap) objects[1];
            try {
                mAdapter.addItem(new BitmapDrawable(getResources(), bm),
                        object.getString("productName"),
                        object.getString("compName"),
                        object.getString("productValue")
                        , object.getString("productId")
                        , object.getString("compId"));
                mAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            hideDialog();
        }
    }

    private MarketTask mTask;

    private void marketListUpdate() {
        pDialog.setMessage("상품 정보를 받아오는 중...");
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_PRODUCT_LIST,
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

    public class ViewHolder {
        public ImageView pImage1;
        public TextView pName1;
        public TextView company;
        public TextView price1;
    }

    public class ListViewAdapter extends BaseAdapter {

        private Context mContext = null;
        private ArrayList<Product> mListData = new ArrayList<Product>();

        public ListViewAdapter(Context mContext) {
            super();
            this.mContext = mContext;
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
            if (view == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.productlist_item, null);

                holder.pImage1 = view.findViewById(R.id.pImage1);
                holder.pName1 = view.findViewById(R.id.pname1);
                holder.company = view.findViewById(R.id.company);
                holder.price1 = view.findViewById(R.id.price1);

                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            Product mData = mListData.get(i);

            if (mData.getDrawable1() != null) {
                holder.pImage1.setVisibility(View.VISIBLE);
                holder.pImage1.setImageDrawable(mData.getDrawable1());
            } else {
                holder.pImage1.setVisibility(View.GONE);
            }

            holder.pName1.setText(mData.getpName1());
            holder.company.setText(mData.getCompany());
            holder.price1.setText(String.format("%,d", Integer.parseInt(mData.getPrice1())) + "원");


            return view;

        }

        public void addItem(Drawable image1, String mName1, String mCompany, String mPrice1, String mPid, String mCompId) {
            Product addInfo = null;
            addInfo = new Product();
            addInfo.setDrawable1(image1);
            addInfo.setpName1(mName1);
            addInfo.setCompany(mCompany);
            addInfo.setPrice1(mPrice1);
            addInfo.setpId(mPid);
            addInfo.setpCompanyId(mCompId);

            mListData.add(addInfo);
        }

        public void remove(int position) {
            mListData.remove(position);
            dataChange();
        }

        public void dataChange() {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTask != null) mTask.cancel(true);
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

    private void showDialog() {
        if (!pDialog.isShowing()) {
            pDialog.show();
        }
    }


    private void hideDialog() {
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }
}

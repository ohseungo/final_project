package iot.e1m4.com.greenright;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
public class MarketFragment extends Fragment{


    private ListView mListView=null;
    private ListViewAdapter mAdapter=null;

    public MarketFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        return;
    }

    private OrderFragment mOrderFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout=inflater.inflate(R.layout.fragment_market, container, false);
        mListView=layout.findViewById(R.id.productList);

        mAdapter=new ListViewAdapter(getActivity());
        mListView.setAdapter(mAdapter);

        //데이터 입력은 이곳에서~~~~
        marketListUpdate();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                Product mData = mAdapter.mListData.get(position);
                /*Bundle extras=new Bundle();
                extras.putString("pname",mData.getpName1());
                extras.putString("company",mData.getCompany());
                extras.putString("price",mData.getPrice1());

                Intent intent = new Intent(getActivity(), OrderFragment.class);
                intent.putExtras(extras);
                startActivity(intent);*/
                //Toast.makeText(getActivity(), mData.getpId() +"", Toast.LENGTH_SHORT).show();
                mOrderFragment = new OrderFragment();
                Bundle bundle = new Bundle();
                bundle.putString("productId", mData.getpId());
                mOrderFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.contentContainer,mOrderFragment).commit();

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
            for (int i=0; i<jArray.length(); i++){
                if (jArray.getJSONObject(i).getString("productImage") == null ||
                        jArray.getJSONObject(i).getString("productImage").equals("null") ||
                jArray.getJSONObject(i).getString("productImage").trim().equals("")) bm = null;
                else {
                 /*   Log.e("확인", AppConfig.REQUEST_URL + "/images/product/"
                            +jsonArrays[0].getJSONObject(i).getString("productImage"));*/
                 try (InputStream is =new URL(AppConfig.REQUEST_URL + "/images/product/"
                         + jArray.getJSONObject(i).getString("productImage")).openStream()) {
                     bm = BitmapFactory.decodeStream(is);
                     publishProgress(jsonArrays[0].getJSONObject(i), bm);
                 }
                }
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
                        object.getString("productValue"), object.getString("productId"));
                mAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private MarketTask mTask;
    private void marketListUpdate() {
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
        AppController.getInstance().
                addToRequestQueue(stringRequest);

    }

    public class ViewHolder{

        public ImageView pImage1;
        public TextView pName1;
        public TextView company;
        public TextView price1;


    }

    public class ListViewAdapter extends BaseAdapter{

        private Context mContext=null;
        private ArrayList<Product> mListData=new ArrayList<Product>();

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
            if(view == null){
                holder=new ViewHolder();

                LayoutInflater inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view=inflater.inflate(R.layout.productlist_item, null);

                holder.pImage1=view.findViewById(R.id.pImage1);
                holder.pName1=view.findViewById(R.id.pname1);
                holder.company=view.findViewById(R.id.company);
                holder.price1=view.findViewById(R.id.price1);

                view.setTag(holder);
            }else{
                holder= (ViewHolder) view.getTag();
            }

            Product mData=mListData.get(i);

            if(mData.getDrawable1()!=null){
                holder.pImage1.setVisibility(View.VISIBLE);
                holder.pImage1.setImageDrawable(mData.getDrawable1());
            }else{
                holder.pImage1.setVisibility(View.GONE);
            }

            holder.pName1.setText(mData.getpName1());
            holder.company.setText(mData.getCompany());
            holder.price1.setText(mData.getPrice1());


            return view;

        }
        public void addItem(Drawable image1,String mName1,String mCompany, String mPrice1, String mPid){
            Product addInfo = null;
            addInfo = new Product();
            addInfo.setDrawable1(image1);
            addInfo.setpName1(mName1);
            addInfo.setCompany(mCompany);
            addInfo.setPrice1(mPrice1);
            addInfo.setpId(mPid);

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
    public void onDestroy() {
        super.onDestroy();
        if (mTask !=null)  mTask.cancel(true);
        }
}

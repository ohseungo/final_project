package iot.e1m4.com.greenright;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import info.app.AppConfig;
import info.app.AppController;


/**
 * A simple {@link Fragment} subclass.
 */
public class MarketFragment extends Fragment {


    private ListView mListView=null;
    private ListViewAdapter mAdapter=null;
    private static Typeface typeface;

    public MarketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout=inflater.inflate(R.layout.fragment_market, container, false);

        if(typeface == null) {
            typeface = Typeface.createFromAsset(getActivity().getAssets(),
                    "fonts/yoon350.ttf");
        }
        setGlobalFont(layout);
        mListView=layout.findViewById(R.id.productList);

        mAdapter=new ListViewAdapter(getActivity());
        mListView.setAdapter(mAdapter);

        //데이터 입력은 이곳에서~~~~
        marketListUpdate();
      /*  mAdapter.addItem(getResources().getDrawable(R.drawable.p1),"체리열매 발아키트","[커피팟]","12,000");
        mAdapter.addItem(getResources().getDrawable(R.drawable.p2),"토트백","[FREiTAG]","229,000");
        mAdapter.addItem(getResources().getDrawable(R.drawable.p3),"데일리 썸머 파우치 3size","[Dadume]","22,000");
        mAdapter.addItem(getResources().getDrawable(R.drawable.p4),"빈티지 가랜드","[Dadume]","16,000");
        mAdapter.addItem(getResources().getDrawable(R.drawable.p5),"점퍼 us슬리브 스트라이프 스웻 티셔츠","[RE:CODE]","98,000");
        mAdapter.addItem(getResources().getDrawable(R.drawable.p6),"pet bag 프린트 점퍼 푸푸 가방","[RE:CODE]","49,000");
*/
      //주문 화면으로 이동
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
                Toast.makeText(getActivity(), mData.getpName1().getBytes() +"", Toast.LENGTH_SHORT).show();
                getFragmentManager().beginTransaction().replace(R.id.contentContainer,new OrderFragment()).commit();

            }
        });

        return layout;
    }

    private void marketListUpdate() {
        StringRequest  stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_STORE_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject object;
                            for (int i =0; i<jsonArray.length(); i++) {
                                object = jsonArray.getJSONObject(i);
                                mAdapter.addItem(getResources().getDrawable(R.drawable.p1),
                                        object.getString("productName"),
                                        object.getString("compName"),
                                        object.getString("productValue"));
                            }

                            mAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "오류", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ///리스트 가져오기 실패
                Toast.makeText(getContext(), "실패", Toast.LENGTH_SHORT).show();
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
        public void addItem(Drawable image1,String mName1,String mCompany, String mPrice1){
            Product addInfo = null;
            addInfo = new Product();

            addInfo.setDrawable1(image1);
            addInfo.setpName1(mName1);
            addInfo.setCompany(mCompany);
            addInfo.setPrice1(mPrice1);

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

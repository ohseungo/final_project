package iot.e1m4.com.greenright;


import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MarketFragment extends Fragment {


    private ListView mListView=null;
    private ListViewAdapter mAdapter=null;

    public MarketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout=inflater.inflate(R.layout.fragment_market, container, false);
        mListView=layout.findViewById(R.id.productList);

        mAdapter=new ListViewAdapter(getActivity());
        mListView.setAdapter(mAdapter);

        //데이터 입력은 이곳에서~~~~

        mAdapter.addItem(getResources().getDrawable(R.drawable.p1),"체리열매 발아키트","[커피팟]","12,000");
        mAdapter.addItem(getResources().getDrawable(R.drawable.p2),"토트백","[FREiTAG]","229,000");
        mAdapter.addItem(getResources().getDrawable(R.drawable.p3),"데일리 썸머 파우치 3size","[Dadume]","22,000");
        mAdapter.addItem(getResources().getDrawable(R.drawable.p4),"빈티지 가랜드","[Dadume]","16,000");
        mAdapter.addItem(getResources().getDrawable(R.drawable.p5),"점퍼 슬리브 스트라이프 스웻 티셔츠","[RE:CODE]","98,000");
        mAdapter.addItem(getResources().getDrawable(R.drawable.p6),"pet bag 프린트 점퍼 푸푸 가방","[RE:CODE]","49,000");

      //주문 화면으로 이동
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                Product mData = mAdapter.mListData.get(position);
                Bundle extras=new Bundle();
                extras.putString("pname",mData.getpName1());
                extras.putString("company",mData.getCompany());
                extras.putString("price",mData.getPrice1());

                Intent intent = new Intent(getActivity(), OrderFragment.class);
                intent.putExtras(extras);
                startActivity(intent);

            }
        });

        return layout;
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
}

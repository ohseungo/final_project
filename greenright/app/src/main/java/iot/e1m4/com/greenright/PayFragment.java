package iot.e1m4.com.greenright;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 */
public class PayFragment extends Fragment {

    private Button cardBtn;
    private Button accountBtn;
    private Button simplePayBtn;

    private ViewPager pager;

    private Fragment cardFragment;
    private Fragment accountFragment;
    private Fragment simplePayFragment;

    FragmentTransaction transaction;

    public PayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout=inflater.inflate(R.layout.fragment_pay, container, false);

        //각 버튼 눌렀을 때 옮겨지는 fragment 연결
        cardFragment=new CardFragment();
        accountFragment=new AccountFragment();
        simplePayFragment=new SimplePayFragment();

        transaction=getActivity().getSupportFragmentManager().beginTransaction();

        cardBtn=layout.findViewById(R.id.cardBtn);
        cardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(0);
                cardBtn.setBackgroundColor(getResources().getColor(R.color.orange));
                accountBtn.setBackgroundColor(getResources().getColor(R.color.basic));
                simplePayBtn.setBackgroundColor(getResources().getColor(R.color.basic));
                cardBtn.setTextColor(getResources().getColor(R.color.white));
                accountBtn.setTextColor(getResources().getColor(R.color.black));
                simplePayBtn.setTextColor(getResources().getColor(R.color.black));
            }
        });
        accountBtn=layout.findViewById(R.id.accountBtn);
        accountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               pager.setCurrentItem(1);
                cardBtn.setBackgroundColor(getResources().getColor(R.color.basic));
                accountBtn.setBackgroundColor(getResources().getColor(R.color.orange));
                simplePayBtn.setBackgroundColor(getResources().getColor(R.color.basic));
                cardBtn.setTextColor(getResources().getColor(R.color.black));
                accountBtn.setTextColor(getResources().getColor(R.color.white));
                simplePayBtn.setTextColor(getResources().getColor(R.color.black));
            }

        });
        simplePayBtn=layout.findViewById(R.id.simplePayBtn);
        simplePayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(2);
                cardBtn.setBackgroundColor(getResources().getColor(R.color.basic));
                accountBtn.setBackgroundColor(getResources().getColor(R.color.basic));
                simplePayBtn.setBackgroundColor(getResources().getColor(R.color.orange));
                cardBtn.setTextColor(getResources().getColor(R.color.black));
                accountBtn.setTextColor(getResources().getColor(R.color.black));
                simplePayBtn.setTextColor(getResources().getColor(R.color.white));
            }
        });

        pager=layout.findViewById(R.id.pager);
        pager.setAdapter(new PagerAdapter(getActivity().getSupportFragmentManager(),3));

   /*   상품금액 넣는 텍스트뷰:orderPrice
        배송비 넣는 텍스트뷰:deliveryPrice
        총 결제 금액 넣는 텍스트뷰:finalPrice */

        return layout;
    }
    private class PagerAdapter extends FragmentStatePagerAdapter{

        private int PAGE_NUMBER;
        public PagerAdapter(FragmentManager fm, int count){
            super(fm);
            PAGE_NUMBER=count;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return cardFragment;
                case 1:
                    return accountFragment;
                case 2:
                    return simplePayFragment;
                    default:
                        return null;
            }
        }

        @Override
        public int getCount() {
            return PAGE_NUMBER;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }

   /* private class PagerAdapter extends FragmentPagerAdapter{

        private int PAGE_NUMBER;
        public PagerAdapter(FragmentManager fm, int count){
            super(fm);
            PAGE_NUMBER=count;
        }


        //해당되는 fragment리턴
        @Override
        public Fragment getItem(int position) {
            if(position==0){
                return cardFragment;
            }else if(position==1){
                return accountFragment;
            }else{
                return simplePayFragment;
            }
        }

        @Override
        public int getCount() {
            return PAGE_NUMBER;
        }
    }*/

}

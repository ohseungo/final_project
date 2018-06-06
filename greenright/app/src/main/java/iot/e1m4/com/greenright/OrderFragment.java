package iot.e1m4.com.greenright;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {

    FragmentTransaction transaction;
    Button payBtn;

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout=inflater.inflate(R.layout.fragment_order, container, false);

        //주문자 정보와 동일 체크 버튼:isEqualCheck
        payBtn=layout.findViewById(R.id.orderTitle);
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.contentContainer,new PayFragment()).commit();
            }
        });
        return layout;
    }


}

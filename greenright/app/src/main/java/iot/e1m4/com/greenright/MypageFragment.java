package iot.e1m4.com.greenright;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class MypageFragment extends Fragment {



    Button mPointBtn;
    Button mOrderBtn;
    Button mUpdateBtn;
    Fragment mCurrentPointFragment=new CurrentPointFragment();
    Fragment mCurrentOrderFragment=new CurrentOrderFragment();
    Fragment mEditFragment=new EditFragment();
    public MypageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View layout=inflater.inflate(R.layout.fragment_mypage, container, false);
        mPointBtn=layout.findViewById(R.id.pointBtn);
        mPointBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.contentContainer,mCurrentPointFragment).commit();
            }
        });
        mOrderBtn=layout.findViewById(R.id.orderBtn);
        mOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.contentContainer,mCurrentOrderFragment).commit();
            }
        });
        mUpdateBtn=layout.findViewById(R.id.updateBtn);
        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.contentContainer,mEditFragment).commit();
            }
        });
        return layout;
    }



}

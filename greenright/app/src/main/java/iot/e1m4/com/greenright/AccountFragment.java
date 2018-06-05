package iot.e1m4.com.greenright;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {


    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout=inflater.inflate(R.layout.fragment_account, container, false);

        //스피너 생성 및 어댑터 연결
        String[]str=getResources().getStringArray(R.array.spinnerArray3);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,str);

        Spinner spinner=layout.findViewById(R.id.spinner);

        spinner.setAdapter(adapter);


        return layout;
    }

}

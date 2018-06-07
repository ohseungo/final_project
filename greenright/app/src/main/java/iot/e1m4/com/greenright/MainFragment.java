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
public class MainFragment extends Fragment {

    Button airBtn;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout=inflater.inflate(R.layout.fragment_main, container, false);
        airBtn=layout.findViewById(R.id.airBtn);
        airBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stationName="역삼동";
                getWeather(stationName);

            }
        });
        return layout;
    }

    public static  void getWeather(String name){

    }

}

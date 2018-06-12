package iot.e1m4.com.greenright;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import info.addon.SessionManager;
import info.app.AppConfig;
import info.app.AppController;

import android.widget.TextView;



/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment implements MainActivity.onKeyBackPressedListener {
    private final String TAG = getClass().getSimpleName();
    FragmentTransaction transaction;
    Button payBtn;
    private static Typeface typeface;

    EditText mOrderName;
    EditText mOrderPhone;
    EditText mOrderEmail;
    EditText mDeliveryName;
    EditText mDeliveryPhone;
    EditText mDeliveryAddress;

    CheckBox mCheckBox;

    SessionManager mSessionManager;
    PayFragment mPayFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout=inflater.inflate(R.layout.fragment_order, container, false);
        mSessionManager = new SessionManager(getActivity());

        if(typeface == null) {
            typeface = Typeface.createFromAsset(getActivity().getAssets(),
                    "fonts/yoon350.ttf");
        }
        setGlobalFont(layout);

        //주문자 정보와 동일 체크 버튼:isEqualCheck
        Toast.makeText(getActivity(), getArguments().getString("productId"), Toast.LENGTH_SHORT).show();

        payBtn=layout.findViewById(R.id.orderTitle);
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPayFragment= new PayFragment();
                Bundle args = new Bundle();
                ////필요한 정보 넣기
                /////
                mPayFragment.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.contentContainer,mPayFragment).commit();
            }
        });

        mOrderName=layout.findViewById(R.id.orderName);
        mOrderPhone=layout.findViewById(R.id.orderPhone);
        mOrderEmail=layout.findViewById(R.id.orderEmail);

        mDeliveryName = layout.findViewById(R.id.deliveryName);
        mDeliveryPhone = layout.findViewById(R.id.deliveryPhone);
        mDeliveryAddress = layout.findViewById(R.id.deliveryAddress);

        mCheckBox = layout.findViewById(R.id.isEqualCheck);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mDeliveryName.setText(mOrderName.getText());
                    mDeliveryPhone.setText(mOrderPhone.getText());
                    return;
                }
            }
        });
        userUpdate();

        return layout;
    }


    private void userUpdate() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.FIND_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //////////////성공//////////////////////
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            mOrderName.setText(jsonObject.getString("userName"));
                            mOrderEmail.setText(jsonObject.getString("userEmail"));
                            mOrderPhone.setText(jsonObject.getString("userPhone"));
                            return;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ///////////////////실패/////////////////////////////////
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId", mSessionManager.getUserId());
                return params;
            }
        };
        stringRequest.setTag(TAG);
        AppController.getInstance().
                addToRequestQueue(stringRequest);
        return;
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
        getFragmentManager().beginTransaction().replace(R.id.contentContainer,new MarketFragment()).commit();
        return;
    }
}

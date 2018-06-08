package iot.e1m4.com.greenright;


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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import info.addon.SessionManager;
import info.app.AppConfig;
import info.app.AppController;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {

    FragmentTransaction transaction;
    Button payBtn;

    EditText mOrderName;
    EditText mOrderPhone;
    EditText mOrderEmail;
    EditText mDeliveryName;
    EditText mDeliveryPhone;
    EditText mDeliveryAddress;

    CheckBox mCheckBox;

    SessionManager mSessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout=inflater.inflate(R.layout.fragment_order, container, false);
        mSessionManager = new SessionManager(getActivity());
        //주문자 정보와 동일 체크 버튼:isEqualCheck
        Toast.makeText(getActivity(), getArguments().getString("productId"), Toast.LENGTH_SHORT).show();

        payBtn=layout.findViewById(R.id.orderTitle);
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.contentContainer,new PayFragment()).commit();
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
        AppController.getInstance().
                addToRequestQueue(stringRequest);
        return;
    }


}

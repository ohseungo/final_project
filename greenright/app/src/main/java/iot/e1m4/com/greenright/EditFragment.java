package iot.e1m4.com.greenright;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import info.addon.SessionManager;
import info.app.AppConfig;
import info.app.AppController;


public class EditFragment extends Fragment {


    private final String TAG = getClass().getSimpleName();

    SessionManager sessionManager;
    EditText nameEdit;
    EditText passEdit;
    EditText passConfirmEdit;
    EditText emailEdit;
    EditText phoneEdit;
    EditText carEdit;

    TextView editUserId;
    Button editBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sessionManager = new SessionManager(getActivity());
        View layout = inflater.inflate(R.layout.fragment_edit, container, false);
        nameEdit = layout.findViewById(R.id.nameEdit);
        passEdit = layout.findViewById(R.id.passEdit);
        passConfirmEdit = layout.findViewById(R.id.passConfirmEdit);
        emailEdit = layout.findViewById(R.id.emailEdit);
        phoneEdit = layout.findViewById(R.id.phoneEdit);
        carEdit = layout.findViewById(R.id.carEdit);
        editUserId = layout.findViewById(R.id.editUserId);
        userUpdate();

        editBtn = layout.findViewById(R.id.editBtn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = nameEdit.getText().toString().trim();
                String password = passEdit.getText().toString().trim();
                String userEmail = emailEdit.getText().toString().trim();
                String userPhone = phoneEdit.getText().toString().trim();
                String userCar = carEdit.getText().toString().trim();
                if (!passEdit.getText().toString().equals(passConfirmEdit.getText().toString())) {
                    Toast.makeText(getActivity(), "비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (userName.isEmpty() ||
                        password.isEmpty() ||
                        passConfirmEdit.getText().toString().isEmpty() ||
                        userEmail.isEmpty() ||
                       userPhone.isEmpty() ){
                    Toast.makeText(getActivity(), "필수 사항을 채워주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                userInfoChange(sessionManager.getUserId(), password,   userEmail, userName, userPhone, userCar);
                return;
            }
        });

        return layout;
    }


    private void userUpdate() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.FIND_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //////////////성공//////////////////////
                        try {
                            JSONObject object = new JSONObject(response);
                            editUserId.setText(object.getString("userId"));
                            nameEdit.setText(object.getString("userName"));
                            emailEdit.setText(object.getString("userEmail"));
                            phoneEdit.setText(object.getString("userPhone"));
                            if (object.getString("userCar")!= null) {
                                carEdit.setText(object.getString("userCar"));
                            }
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
                params.put("userId", sessionManager.getUserId());
                return params;
            }
        };
        stringRequest.setTag(TAG);
        AppController.getInstance().
                addToRequestQueue(stringRequest);
        return;
    }


    private void userInfoChange(final String userId, final String password, final String userEmail,
                          final String userName, final String userPhone, final String userCar) {
        final StringRequest stringRequest = new StringRequest(Request.Method.POST,
                AppConfig.URL_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //가입 성공시
                        if (response.equals("true")){
                            getFragmentManager().beginTransaction().replace(R.id.contentContainer, new HomeFragment()).commit();
                            return;
                        }else{
                            Toast.makeText(getActivity(), "헐", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //실패
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> user = new HashMap<>();
                    user.put("userId", userId);
                    user.put("password", password);
                    user.put("userEmail", userEmail);
                    user.put("userName", userName);
                    user.put("userPhone", userPhone);
                    user.put("userCar", userCar);
                return user;
            }
        };

        stringRequest.setTag(TAG);
        AppController.getInstance().
                addToRequestQueue(stringRequest);
    }

    @Override
    public void onStop() {
        super.onStop();
        AppController.getInstance().cancelPendingRequests(TAG);
    }
}

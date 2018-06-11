package iot.e1m4.com.greenright;

import android.content.Intent;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import info.addon.SessionManager;
import info.app.AppConfig;
import info.app.AppController;

public class RegisterActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();
    private EditText idEt;
    private EditText pwEt;
    private EditText pwChEt;
    private EditText emailEt;
    private EditText nameEt;
    private EditText phoneEt;
    private EditText carEt;

    private Button regIdCheckBut;
    private TextView regIdCheck;
    private boolean idCheck = false;

    private SessionManager mSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        idEt = findViewById(R.id.register_id_edit);
        pwEt = findViewById(R.id.register_pw_edit);
        pwChEt = findViewById(R.id.register_pw_check_edit);
        emailEt = findViewById(R.id.register_email_edit);
        nameEt = findViewById(R.id.register_name_edit);
        phoneEt = findViewById(R.id.register_phone_edit);
        carEt = findViewById(R.id.register_car_edit);

        regIdCheckBut = findViewById(R.id.register_id_check);
        regIdCheck = findViewById(R.id.ispossible);

        idEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                regIdCheckBut.setEnabled(true);
                regIdCheck.setText("");
                idCheck = false;
                return;
            }
        });

        mSessionManager = new SessionManager(this);
    }

    public void checkIdDup(View view) {
        if (idEt.getText().toString().trim().equals("")) {
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                AppConfig.FIND_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.equals("") || response == null) { //없을 경우
                            regIdCheckBut.setEnabled(false);
                            regIdCheck.setText("사용 가능");
                            idCheck = true;
                            return;
                        }else {
                            regIdCheckBut.setEnabled(true);
                            regIdCheck.setText("사용 불가능");
                            idCheck = false;
                            return;
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> user = new HashMap<>();
                user.put("userId", idEt.getText().toString());
                return user;
            }
        };
        stringRequest.setTag(TAG);
        //요청 서버로 보낸다
        AppController.getInstance().
                addToRequestQueue(stringRequest);
    }

    /**
     * 회원가입 버튼 온클릭 이벤트
     *
     * @param view
     */
    public void userRegister(View view) {
        String userId = idEt.getText().toString().trim();
        String password = pwEt.getText().toString().trim();
        String passwordCheck = pwChEt.getText().toString().trim();
        String userEmail = emailEt.getText().toString().trim();
        String userName = nameEt.getText().toString().trim();
        String userPhone = phoneEt.getText().toString().trim();
        String userCar = carEt.getText().toString().trim();


        if (userId.isEmpty() || password.isEmpty() || passwordCheck.isEmpty() ||
                userEmail.isEmpty() || userName.isEmpty() || userPhone.isEmpty()) {
            Toast.makeText(getApplicationContext(), "필수 사항을 모두 입력해주세요!", Toast.LENGTH_SHORT).show();
            pwEt.setText("");
            pwChEt.setText("");
            return;
        }
        if (!idCheck) {
            Toast.makeText(getApplicationContext(), "종복체크를 해주세요", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(passwordCheck)) {
            Toast.makeText(getApplicationContext(), "패스워드가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
            pwEt.requestFocus();
            pwEt.setText("");
            pwChEt.setText("");
            return;
        }

        register(userId, password, userEmail, userName, userPhone, userCar);
        return;
    }


    /*
    회원가입 구현 메소드
     */
    private void register(final String userId, final String password, final String userEmail,
                          final String userName, final String userPhone, final String userCar) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //가입 성공시
                        if (response.equals("true")){
                             Toast.makeText(getApplicationContext(), "가입되셨습니다!", Toast.LENGTH_SHORT).show();
                             mSessionManager.setLogin(true, userId);
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(getApplicationContext(), "가입 중 문제가 발생하였습니다", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //가입실패
                Log.e(TAG, error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> user = new HashMap<>();
                try {
                user.put("userId", userId);
                user.put("password", password);
                user.put("userEmail", userEmail);
                user.put("userName", URLEncoder.encode(userName,"UTF-8"));
                user.put("userPhone", userPhone);
                user.put("userCar", userCar);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return user;
            }
        };
        stringRequest.setTag(TAG);
        //요청 서버로 보낸다
        AppController.getInstance().
                addToRequestQueue(stringRequest);
    }

    @Override
    protected void onStop() {
        super.onStop();
        AppController.getInstance().cancelPendingRequests(TAG);
    }
}

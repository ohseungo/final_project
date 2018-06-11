package iot.e1m4.com.greenright;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import static com.android.volley.Request.*;

public class LoginActivity extends BaseActivity {
    private SessionManager session;

    private EditText idTv;
    private EditText pwTv;

    private TextView tv;

    private ProgressDialog pDialog;

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //tv = findViewById(R.id.textView);
        idTv = findViewById(R.id.userid);
        pwTv = findViewById(R.id.password);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        session = new SessionManager(getApplicationContext());

      /*  if (session.isLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }*/

        idTv.requestFocus();
    }

    /**
     * 로그인 버튼 온클릭이벤트
     * @param view
     */
    public void loginBtn(View view) {
        String userId = idTv.getText().toString().trim();
        String password = pwTv.getText().toString().trim();

        if (userId.isEmpty()) {
            Toast.makeText(getApplicationContext(), "빈 칸을 채워주세요", Toast.LENGTH_SHORT).show();
            idTv.requestFocus();
            return;
        }if (password.isEmpty()){
            Toast.makeText(getApplicationContext(), "빈 칸을 채워주세요", Toast.LENGTH_SHORT).show();
            pwTv.requestFocus();
            return;
        }else {
            login(userId, password);
        }



    }


    /**
     * 로그인 구현 메서드
     * @param userId
     * @param password
     */
    private void login(final String userId, final String password) {
        pDialog.setMessage("로그인 중입니다");
        showDialog();

        StringRequest stringRequest;
        stringRequest = new StringRequest(Method.POST,
                AppConfig.URL_LOGIN,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        hideDialog();
                        if (response.equals("true")) {
                            //로그인 성공했을때
                            session.setLogin(true, userId);
                            finish();
                            return;
                        }else{
                            //로그인 틀렸을때
                            Toast.makeText(getApplicationContext(), "아이디 혹은 비밀번호가 틀렸습니다", Toast.LENGTH_SHORT).show();
                            idTv.setText("");
                            pwTv.setText("");
                            idTv.requestFocus();
                            return;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_SHORT).show();
                        hideDialog();
                    }
                }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("userId", userId);
                params.put("password", password);
                return params;
            }
        };

        stringRequest.setTag(TAG);
        AppController.getInstance().
                addToRequestQueue(stringRequest);

    }

    /**
     * 로그인 다이얼로그 표시
     */
    private void showDialog() {
        if (!pDialog.isShowing()){
            pDialog.show();
        }
    }

    /**
     * 로그인 다이얼로그 끝냄
     */
    private void hideDialog() {
        if (pDialog.isShowing()){
            pDialog.dismiss();
        }
    }

    /**
     * 회원가입 버튼 온클릭 이벤트
     * @param view
     */
    public void goToRegister(View view) {
        //회원가입 하러 가자
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        AppController.getInstance().cancelPendingRequests(TAG);
    }
}
